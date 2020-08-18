package am.greenlight.greenlight.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingRequestDto {

    @Size(min = 1, max = 5, message = "rating length must be at least 1 and more than 5 characters")
    private double number;

}
