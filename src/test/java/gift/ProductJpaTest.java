package gift;

import gift.entity.Product;
import gift.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductJpaTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void 상품_저장_및_조회_성공() {
        Product product = new Product("아이스 아메리카노", 4500, "ice_americano.jpg");
        productRepository.save(product);

        Product found = productRepository.findById(product.getId()).orElseThrow();
        assertThat(found.getName()).isEqualTo("아이스 아메리카노");
        assertThat(found.getPrice()).isEqualTo(4500);
    }

    @Test
    void 상품_가격_수정_성공() {
        Product product = new Product("아이스 아메리카노", 4500, "ice_americano.jpg");
        productRepository.save(product);

        Product found = productRepository.findById(product.getId()).orElseThrow();
        found.renameTo("아이스 아메리카노");
        found.changePrice(4000);
        found.changeImage("ice_americano.jpg");

        entityManager.flush();
        entityManager.clear();

        Product updated = productRepository.findById(product.getId()).orElseThrow();
        assertThat(updated.getPrice()).isEqualTo(4000);
    }
}