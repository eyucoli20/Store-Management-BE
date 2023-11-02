package StoreManagement.purchaseOrderManagement.supplier;

import StoreManagement.itemManagement.category.Category;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "suppliers")
@SQLDelete(sql = "UPDATE suppliers SET deleted = true WHERE supplier_id=?")
@Where(clause = "deleted=false")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(nullable = false)
    private String supplierName;

    @Column(nullable = false)
    private String supplierAddress;

    @OneToMany(mappedBy = "supplier")
    private List<Category> categories;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @JsonIgnore
    private boolean deleted = Boolean.FALSE;
}
