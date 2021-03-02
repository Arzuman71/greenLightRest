package am.greenlight.greenlight.model;

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
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue()
    private long id;

    @Column
    private double number;

    @ManyToOne
    private User to;

    @ManyToOne
    private User from;
}
