package am.greenlight.greenlight.model;

import am.greenlight.greenlight.model.enumForCar.CarType;
import am.greenlight.greenlight.model.enumForCar.Color;
import am.greenlight.greenlight.model.enumForUser.Status;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity()
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue()
    private long id;

    @Column
    private String carBrand;

    @Enumerated(value = EnumType.STRING)
    private CarType carType;

    @Column
    private String carNumber;

    @Column
    private String picUrl;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate year;

    @Column
    private String carModel;

    @ManyToOne
    private User user;

    @Enumerated(value = EnumType.STRING)
    private Color color;

    @OneToMany(mappedBy = "car", fetch = FetchType.EAGER)
    private Set<Images> images;

    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime updatedDate;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deletedDate;

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carBrand='" + carBrand + '\'' +
                ", carType=" + carType +
                ", carNumber='" + carNumber + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", year=" + year +
                ", carModel='" + carModel + '\'' +
                ", user=" + user +
                ", color=" + color +
                ", images=" + images +
                ", status=" + status +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", deletedDate=" + deletedDate +
                '}';
    }
}
