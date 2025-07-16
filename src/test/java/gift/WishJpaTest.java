package gift;

import gift.entity.Member;
import gift.entity.Product;
import gift.entity.Wish;
import gift.repository.WishRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class WishJpaTest {

    @Autowired
    private WishRepository wishRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("회원 ID로 위시리스트 조회 성공")
    void 위시리스트를_회원_id로_조회() {
        Member member = new Member("홍길동", "hong@email.com", "password");
        Product product = new Product("아이스 아메리카노", 4500, "ice_americano.jpg");

        entityManager.persist(member);
        entityManager.persist(product);
        entityManager.persist(new Wish(member, product));
        entityManager.flush();

        List<Wish> found = wishRepository.findByMemberId(member.getId());

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getProduct().getName()).isEqualTo("아이스 아메리카노");
    }

    @Test
    void 위시를_삭제한다() {
        Member member = new Member("홍길동", "hong@email.com", "password");
        Product product = new Product("콜드브루", 4800, "coldbrew.jpg");

        entityManager.persist(member);
        entityManager.persist(product);
        Wish wish = new Wish(member, product);
        entityManager.persist(wish);
        entityManager.flush();

        wishRepository.delete(wish);
        entityManager.flush();
        entityManager.clear();

        List<Wish> result = wishRepository.findByMemberId(member.getId());
        assertThat(result).isEmpty();
    }
}