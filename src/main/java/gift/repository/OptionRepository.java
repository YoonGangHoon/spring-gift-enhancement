package gift.repository;

import gift.entity.Option;
import gift.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findAllByProduct(Product product);
}
