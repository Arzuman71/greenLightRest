package am.greenlight.greenlight.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity()
@Table(name = "massage")
public class Massage {
    @Id
    @GeneratedValue()
    private long id;
    private String massage;
    private Date createdDate;
    @ManyToOne
    private User user;
}
