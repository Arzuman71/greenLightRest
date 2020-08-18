package am.greenlight.greenlight.model.forTheFuture;

import am.greenlight.greenlight.model.Car;
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
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue()
    private long id;
    private String name;

    @ManyToOne
    private Car car;
}
