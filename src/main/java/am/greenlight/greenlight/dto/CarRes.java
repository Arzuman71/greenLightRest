package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.enumForCar.CarType;
import am.greenlight.greenlight.model.enumForCar.Color;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRes {
    private long id;
    private String carBrand;
    private CarType carType;
    private String carModel;
    private String carNumber;
    private Color color;
    private LocalDate year;
    private String picUrl;
}
