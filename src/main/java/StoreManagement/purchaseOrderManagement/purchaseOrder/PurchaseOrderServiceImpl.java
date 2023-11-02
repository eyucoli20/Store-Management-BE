package StoreManagement.purchaseOrderManagement.purchaseOrder;

import StoreManagement.exceptions.customExceptions.BadRequestException;
import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import StoreManagement.inventoryManagement.StoreInventoryService;
import StoreManagement.itemManagement.item.Item;
import StoreManagement.itemManagement.item.ItemService;
import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderMapper;
import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderRequest;
import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderResponse;
import StoreManagement.purchaseOrderManagement.purchaseOrder.dto.PurchaseOrderUpdateReq;
import StoreManagement.purchaseOrderManagement.supplier.Supplier;
import StoreManagement.storeManagement.Store;
import StoreManagement.storeManagement.StoreService;
import StoreManagement.utils.CurrentlyLoggedInUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PurchaseOrderServiceImpl implements PurchaseOrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final CurrentlyLoggedInUser loggedInUser;
    private final StoreService storeService;
    private final ItemService itemService;
    private final StoreInventoryService storeInventoryService;

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STORE_MANAGER')")
    public PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest purchaseOrderRequest) {
        // Retrieve the store and item based on the request.
        Store store = storeService.utilGetStoreById(purchaseOrderRequest.getStoreId());
        Item item = itemService.utilGetItemById(purchaseOrderRequest.getItemId());

        // Ensure that a supplier is assigned to the item's category.
        Supplier supplier = item.getCategory().getSupplier();
        if (supplier == null)
            throw new BadRequestException("No supplier is assigned to this item's category");

        // Create a new purchase order.
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setStore(store);
        purchaseOrder.setItem(item);
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setQuantity(purchaseOrderRequest.getQuantity());
        purchaseOrder.setOrderedBy(loggedInUser.getUser());
        purchaseOrder.setOrderNumber(generateUniquePurchaseOrderNumber());
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        // Save the purchase order in the repository.
        purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        return PurchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STORE_MANAGER')")
    public PurchaseOrderResponse updatePurchaseOrder(Long orderId, PurchaseOrderUpdateReq updateRequest) {
        PurchaseOrder purchaseOrder = utilGetPurchaseOrderById(orderId);

        // Check if the purchase order is in a modifiable state (e.g., PENDING).
        if (purchaseOrder.getPurchaseOrderStatus() != PurchaseOrderStatus.PENDING)
            throw new BadRequestException("Unable to update the purchase order as it is not in the PENDING state.");

        // Check if the new quantity is different from the current quantity.
        if (updateRequest.getQuantity() != null && !updateRequest.getQuantity().equals(purchaseOrder.getQuantity())) {
            purchaseOrder.setQuantity(updateRequest.getQuantity());
            purchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        }

        return PurchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
    }

    @Override
    @Transactional
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STORE_MANAGER')")
    public PurchaseOrderResponse updatePurchaseOrderStatus(Long orderId, String status) {
        // Check if the provided status is valid
        if (!("PENDING".equals(status) || "APPROVED".equals(status) || "DELIVERED".equals(status)))
            throw new BadRequestException("Invalid status. Status should be one of: PENDING, APPROVED, DELIVERED");

        PurchaseOrder purchaseOrder = utilGetPurchaseOrderById(orderId);
        // Check if the purchase order is already delivered
        if (purchaseOrder.getPurchaseOrderStatus() == PurchaseOrderStatus.DELIVERED)
            throw new BadRequestException("This purchase order has already been marked as delivered.");

        PurchaseOrderStatus newStatus = PurchaseOrderStatus.getEnum(status.toUpperCase());

        if (purchaseOrder.getPurchaseOrderStatus() != newStatus) {
            purchaseOrder.setPurchaseOrderStatus(newStatus);
            purchaseOrder = purchaseOrderRepository.save(purchaseOrder);

            if (newStatus == PurchaseOrderStatus.DELIVERED)
                updateStoreInventory(purchaseOrder);
        }
        return PurchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
    }

    private void updateStoreInventory(PurchaseOrder purchaseOrder) {
        Long storeId = purchaseOrder.getStore().getStoreId();
        Long itemId = purchaseOrder.getItem().getItemId();
        Integer quantity = purchaseOrder.getQuantity();

        storeInventoryService.adjustInventoryQuantityAfterPurchaseOrder(storeId, itemId, quantity);
    }

    @Override
    public List<PurchaseOrderResponse> getAllPurchaseOrders() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();

        return purchaseOrders.stream()
                .map(PurchaseOrderMapper::toPurchaseOrderResponse)
                .toList();
    }

    @Override
    public PurchaseOrderResponse getPurchaseOrderById(Long orderId) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found with ID: " + orderId));

        return PurchaseOrderMapper.toPurchaseOrderResponse(purchaseOrder);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STORE_MANAGER')")
    public void deletePurchaseOrder(Long orderId) {
        PurchaseOrder existingPurchaseOrder = utilGetPurchaseOrderById(orderId);
        purchaseOrderRepository.delete(existingPurchaseOrder);
    }

    private PurchaseOrder utilGetPurchaseOrderById(Long orderId) {
        return purchaseOrderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase Order not found with ID: " + orderId));
    }

    private String generateUniquePurchaseOrderNumber() {
        // Create a prefix for the purchase order number, "PO"
        String prefix = "PO";

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        String uniqueIdentifier = UUID.randomUUID().toString().replaceAll("-", "");

        // Combine the prefix, formatted date and time, and unique identifier to create the purchase order number
        String purchaseOrderNumber = prefix + formattedDateTime + uniqueIdentifier;

        return purchaseOrderNumber;
    }

}
