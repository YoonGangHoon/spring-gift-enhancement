package gift.service;

import gift.dto.OptionRequestDto;
import gift.dto.OptionResponseDto;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.ProductNotExistException;
import gift.repository.OptionRepository;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<OptionResponseDto> find(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException(productId));

        List<Option> options = optionRepository.findAllByProduct(product);
        return options.stream()
                .map(option -> new OptionResponseDto(
                        option.getId(),
                        option.getName(),
                        option.getQuantity()))
                .collect(Collectors.toList());
    }

    @Transactional
    public OptionResponseDto update(Long productId, Long optionId, OptionRequestDto requestDto) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotExistException(productId));

        Option option = optionRepository.findById(optionId).get();
        Option updatedOption = option.updateTo(requestDto.name(), requestDto.quantity());

        return new OptionResponseDto(
                updatedOption.getId(),
                updatedOption.getName(),
                updatedOption.getQuantity()
        );
    }

    public void delete(Long productId, Long optionId) {
        optionRepository.deleteById(optionId);
    }


}
