package pl.marketapi.domain.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column
    @NotBlank(message = "Product name should not be empty.")
    private String name;

    @Column
    @NotBlank(message = "Description should not be empty.")
    private String description;

    @Column
    @NotNull
    private int amount;

    @Column
    private String imageUrl;

    @Column
    @NotNull
    private Double price;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Column
    @UpdateTimestamp
    private Timestamp updatedAt;

    public void reduceAvailableAmount(int number) {
        this.amount -= number;
    }
}
