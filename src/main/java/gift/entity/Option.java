package gift.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Min(1)
    @Max(99_999_999)
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "productId")
    private Product product;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    protected Option() {}

    public Option(String name, Integer quantity,  Product product) {
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public Option(Long id, String name, Integer quantity, Product product) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.product = product;
    }

    public Option updateTo(String name, Integer quantity) {
        return new Option(this.id, name, quantity, this.product);
    }

}
