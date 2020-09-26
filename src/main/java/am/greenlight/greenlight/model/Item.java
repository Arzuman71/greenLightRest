package am.greenlight.greenlight.model;

import am.greenlight.greenlight.model.enumForItem.Type;
import am.greenlight.greenlight.model.enumForUser.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue()
    private long id;
    private String fromIs;
    @Column(name = "where_is")
    private String whereIs;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deadline;
    private String price;
    @ManyToOne
    private User user;
    @ManyToOne
    private Car car;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    private int numberOfPassengers;
    @Enumerated(value = EnumType.STRING)
    private Status status = Status.ACTIVE;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime createdDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime deletedDate;


    // for the future
    // private ParcelType parcelType;
}
