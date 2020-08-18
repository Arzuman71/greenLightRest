package am.greenlight.greenlight.model;

import am.greenlight.greenlight.model.enumForCar.CarType;
import am.greenlight.greenlight.model.enumForCar.Color;
import am.greenlight.greenlight.model.enumForUser.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity()
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue()
    private long id;
    @Enumerated(value = EnumType.STRING)
    private CarType carType;
    private String carBrand;
    private String carModel;
    private String carNumber;
    @Enumerated(value = EnumType.STRING)
    private Color color;
    // @DateTimeFormat(pattern = "yyyy-MM-dd")
    private int year;
    private String picCar;
    @ManyToOne
    private User user;
    @Enumerated(value = EnumType.STRING)
    private State state;

}
