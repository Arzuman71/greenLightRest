package am.greenlight.greenlight.model.forTheFuture;

import am.greenlight.greenlight.model.Announcement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity()
@Table(name = "transportation")
public class Transportation {
    @Id
    @GeneratedValue()
    private long id;
    @Enumerated(value = EnumType.STRING)
    private ParcelType parcelType;
    private double price;
    @OneToMany
    private List<Announcement> announcements;

}
