package gift.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OptionRequestDto(
        @NotBlank(message = "옵션명은 필수 입력값입니다.")
        String name,

        @NotNull(message = "가격은 필수 입력값입니다.")
        @Min(value = 1, message = "수량은 1개 이상으로 등록해주세요.")
        @Max(value = 99999999, message = "수량은 1억개 미만으로 등록해주세요.")
        Integer quantity
) {
}
