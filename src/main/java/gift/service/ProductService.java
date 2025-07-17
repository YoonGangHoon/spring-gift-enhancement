package gift.service;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.exception.ProductNotExistException;
import gift.repository.ProductRepository;
import gift.util.PagingUtils;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto create(ProductRequestDto requestDto) {
        Product product = new Product(requestDto.name(), requestDto.price(), requestDto.imageUrl());
        Product newProduct = productRepository.save(product);

        return new ProductResponseDto(newProduct.getId(), newProduct.getName(), newProduct.getPrice(), newProduct.getImageUrl());
    }

    public ProductResponseDto find(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException(productId));

        return new ProductResponseDto(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    @Transactional
    public ProductResponseDto update(Long productId, ProductRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException(productId));

        product.renameTo(requestDto.name());
        product.changePrice(requestDto.price());
        product.changeImage(requestDto.imageUrl());

        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

    public void delete(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException(productId));
        productRepository.delete(product);
    }

    public List<ProductResponseDto> getAllProducts(int page, int size, String sort) {

        Pageable pageable = PagingUtils.createPageable(page, size, sort);

        return productRepository.findAll(pageable)
                .stream()
                .map(p -> new ProductResponseDto(p.getId(), p.getName(), p.getPrice(), p.getImageUrl()))
                .toList();
    }
}
