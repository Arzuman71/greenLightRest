package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.enumForCar.CarType;
import am.greenlight.greenlight.model.enumForCar.Color;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarRequestDto {
    private long id;
    private CarType carType;
    @NotBlank(message = "Brand is required")
    private String carBrand;
    @NotBlank(message = "Model is required")
    private String carModel;
    @NotBlank(message = "Number is required")
    private String carNumber;
    private Color color;
    @NotNull(message = "Parameter Date Unadjusted can not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate year;
    private String picCar;
}
