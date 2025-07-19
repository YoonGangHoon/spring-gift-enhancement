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

    protected Option() {}

    public Option(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }
}
