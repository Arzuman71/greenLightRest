package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.enumForItem.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemResDto {
    private long id;
    private String outset;
    private String end;
    private LocalDateTime startDate;
    private String price;
    private Type type;
    private int numberOfPassengers;
    private LocalDateTime createdDate;
    private String parcelType;

    private UserResDto user;
    private CarRes car;
    private double ratingSum;
}
