package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.enumForCar.CarType;
import am.greenlight.greenlight.model.enumForCar.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarRequestDto {

    @NotBlank(message = "Type is required")
    private CarType carType;
    @NotBlank(message = "Brand is required")
    private String carBrand;
    @NotBlank(message = "Model is required")
    private String carModel;
    @NotBlank(message = "Number is required")
    private String carNumber;
    @NotBlank(message = "color is required")
    private Color color;
    @NotNull(message = "Parameter Date Unadjusted can not be blank or null")
    private int year;
    private String picCar;
}
