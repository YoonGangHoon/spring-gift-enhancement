package gift.service;

import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.ProductNotExistException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

    private final ProductRepository productRepository;
    private final OptionRepository optionRepository;
    public OptionService(ProductRepository productRepository, OptionRepository optionRepository) {
        this.productRepository = productRepository;
        this.optionRepository = optionRepository;
    }

    public OptionResponseDto create(Long productId, OptionRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException(productId));

        Option option = new Option(requestDto.name(), requestDto.quantity(), product);
        Option newOption = optionRepository.save(option);
        
        return new OptionResponseDto(newOption.getId(), newOption.getName(), newOption.getQuantity());
    }
}
