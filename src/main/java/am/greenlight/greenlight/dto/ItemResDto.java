package am.greenlight.greenlight.dto;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.Preference;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForCar.CarType;
import am.greenlight.greenlight.model.enumForCar.Color;
import am.greenlight.greenlight.model.enumForItem.Type;
import am.greenlight.greenlight.model.enumForUser.Gender;
import am.greenlight.greenlight.model.enumPreferance.Music;
import am.greenlight.greenlight.model.enumPreferance.Smoke;
import am.greenlight.greenlight.model.enumPreferance.Speak;
import am.greenlight.greenlight.model.enumPreferance.WithAnimals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
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

    private UserResDto user;
    private CarRes car;
    private double ratingSum;
}
