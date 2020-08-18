package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.enumForAnnouncement.AnnouncementType;
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
public class AnnouncementRequestDto {
    @NotBlank(message = "fromIs is required")
    private String fromIs;
    @NotBlank(message = "whereIs is required")
    private String whereIs;
    @NotNull(message = "Parameter Date Unadjusted can not be blank or null")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;
    @NotBlank(message = "announcementType is required")
    private AnnouncementType announcementType;
    private double price;
    private Car car;
    private int numberOfPassengers;

}
