package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.enumForItem.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemReqDto {

    private long id;
    @NotBlank(message = "outset is required")
    private String outset;
    @NotBlank(message = "end is required")
    private String end;
    @NotNull(message = "Parameter Date Unadjusted can not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;
    private Type type;
    private String price;
    @NotBlank(message = "car is required")
    private long carId;
    private int numberOfPassengers;

}
