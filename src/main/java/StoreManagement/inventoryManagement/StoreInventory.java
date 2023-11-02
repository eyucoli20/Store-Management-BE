package StoreManagement.inventoryManagement;

import StoreManagement.itemManagement.item.Item;
import StoreManagement.storeManagement.Store;
import StoreManagement.userManagement.user.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Entity
@Table(name = "store_inventory")
@SQLDelete(sql = "UPDATE store_inventory SET deleted = true WHERE store_inventory_id=?")
@Where(clause = "deleted=false")
@Data
public class StoreInventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_inventory_id")
    private Long storeInventoryId;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;

    @Column(name = "min_threshold")
    private int minThreshold;

    @Column(name = "max_threshold")
    private int maxThreshold;

    @ManyToOne
    @JoinColumn(name = "added_by")
    private Users addedBy;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;
}
