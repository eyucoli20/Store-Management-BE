package StoreManagement.inventoryManagement;

import StoreManagement.inventoryManagement.dto.ItemSaleFromInventoryReq;
import StoreManagement.inventoryManagement.dto.StoreInventoryReq;
import StoreManagement.inventoryManagement.dto.StoreInventoryResponse;
import StoreManagement.inventoryManagement.dto.StoreInventoryUpdateReq;
import StoreManagement.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/store-inventory")
@Tag(name = "Store Inventory API.")
public class StoreInventoryController {
    private final StoreInventoryService storeInventoryService;

    public StoreInventoryController(StoreInventoryService storeInventoryService) {
        this.storeInventoryService = storeInventoryService;
    }

    @GetMapping
    public ResponseEntity<List<StoreInventoryResponse>> getAllStoreInventory() {
        return ResponseEntity.ok(storeInventoryService.getAllStoreInventory());
    }

    @GetMapping("/by-store/{storeId}")
    public ResponseEntity<List<StoreInventoryResponse>> getInventoriesByStore(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeInventoryService.getInventoriesByStore(storeId));
    }

    @GetMapping("/{storeInventoryId}")
    public ResponseEntity<StoreInventoryResponse> getStoreInventoryById(@PathVariable Long storeInventoryId) {
        return ResponseEntity.ok(storeInventoryService.getStoreInventoryById(storeInventoryId));
    }

    @PostMapping
    public ResponseEntity<StoreInventoryResponse> createStoreInventory(@RequestBody @Valid StoreInventoryReq storeInventoryReq) {
        return ResponseEntity.status(HttpStatus.CREATED).body(storeInventoryService.createStoreInventory(storeInventoryReq));
    }

    @PutMapping("/{storeInventoryId}")
    public ResponseEntity<StoreInventoryResponse> updateStoreInventory(@PathVariable Long storeInventoryId, @RequestBody @Valid StoreInventoryUpdateReq updateReq) {
        return ResponseEntity.ok(storeInventoryService.adjustInventoryQuantityAfterPurchaseOrder(storeInventoryId, updateReq));
    }

    @PutMapping("/{storeInventoryId}/process-item-sale")
    public ResponseEntity<StoreInventoryResponse> processItemSaleFromInventory(@PathVariable Long storeInventoryId, @RequestBody @Valid ItemSaleFromInventoryReq itemSaleFromInventoryReq) {
        return ResponseEntity.ok(storeInventoryService.processItemSaleFromInventory(storeInventoryId, itemSaleFromInventoryReq));
    }


    @DeleteMapping("/{itemId}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable Long itemId) {
        storeInventoryService.deleteStoreInventory(itemId);
        return ApiResponse.success("Store Inventory deleted successfully");
    }
}
