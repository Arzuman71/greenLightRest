package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForItem.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemResDto {
    private String from;
    private String where;
    private LocalDateTime deadline;
    private String price;
    @ManyToOne
    private User user;
    @ManyToOne
    private Car car;
    @Enumerated(value = EnumType.STRING)
    private Type announcementType;
    private int numberOfPassengers;
    private LocalDateTime createdDate;
}
