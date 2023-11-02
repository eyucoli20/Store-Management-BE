package StoreManagement.storeManagement;

import StoreManagement.storeManagement.dto.StoreRegistrationReq;
import StoreManagement.storeManagement.dto.StoreResponse;
import StoreManagement.storeManagement.dto.StoreUpdateReq;
import StoreManagement.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/stores")
@Tag(name = "Stores API.")
public class StoreController {
    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @PostMapping
    public ResponseEntity<StoreResponse> createStore(@RequestBody @Valid StoreRegistrationReq registrationReq) {
        StoreResponse createdStore = storeService.createStore(registrationReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStore);
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponse> updateStore(
            @PathVariable Long storeId, @RequestBody @Valid StoreUpdateReq updateReq) {
        return ResponseEntity.ok(storeService.updateStore(storeId, updateReq));
    }

    @GetMapping
    public ResponseEntity<List<StoreResponse>> getAllStores() {
        return ResponseEntity.ok(storeService.getAllStores());
    }

    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponse> getStoreById(@PathVariable Long storeId) {
        return ResponseEntity.ok(storeService.getStoreById(storeId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<StoreResponse>> searchStores(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(storeService.searchStoresByNameOrLocation(query));
    }

    @GetMapping("/by-opening-date")
    public ResponseEntity<List<StoreResponse>> getStoresByOpeningDate(@RequestParam("date") LocalDate openingDate) {
        return ResponseEntity.ok(storeService.getStoresByOpeningDate(openingDate));
    }

    @GetMapping("/by-date-range")
    public ResponseEntity<List<StoreResponse>> getStoresByDateRange(
            @RequestParam("start") LocalDate startDate,
            @RequestParam("end") LocalDate endDate) {
        return ResponseEntity.ok(storeService.getStoresByDateRange(startDate, endDate));
    }

    @GetMapping("/by-store-type")
    public ResponseEntity<List<StoreResponse>> getStoresByStoreType(@RequestParam("storeType") String storeType) {
        String pattern = "RETAIL|WHOLESALE|ONLINE";
        Pattern regex = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regex.matcher(storeType);
        if (matcher.matches())
            return ResponseEntity.ok(storeService.getStoresByStoreType(StoreType.valueOf(storeType.toUpperCase())));
        else
            return ResponseEntity.ok(Collections.emptyList());
    }

    @DeleteMapping({"/{storeId}"})
    public ResponseEntity<ApiResponse> deleteStore(@PathVariable Long storeId) {
        storeService.deleteStore(storeId);
        return ApiResponse.success("Store deleted successfully");
    }
}

