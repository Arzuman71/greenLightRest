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
    @GeneratedValue
    private long id;

    @Column
    private String outset;

    @Column
    private String end;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startDate;

    @Column
    private String price;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @Column
    private String parcelType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Car car;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Column
    private int numberOfPassengers;

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


}
