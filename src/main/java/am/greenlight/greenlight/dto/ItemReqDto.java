package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.enumForItem.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemReqDto {

    private long id;
    @NotBlank(message = "fromIs is required")
    private String from;
    @NotBlank(message = "whereIs is required")
    private String where;
    @NotNull(message = "Parameter Date Unadjusted can not be blank or null")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;
    @NotBlank(message = "announcementType is required")
    private Type type;
    private double price;
    private long carId;
    private int numberOfPassengers;

}
