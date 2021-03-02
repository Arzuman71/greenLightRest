package am.greenlight.greenlight.model;

import am.greenlight.greenlight.model.enumPreferance.Music;
import am.greenlight.greenlight.model.enumPreferance.Smoke;
import am.greenlight.greenlight.model.enumPreferance.Speak;
import am.greenlight.greenlight.model.enumPreferance.WithAnimals;
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
@Table(name = "preference")
public class Preference {
    @Id
    @GeneratedValue()
    private long id;

    @Enumerated(value = EnumType.STRING)
    private Speak speak;

    @Enumerated(value = EnumType.STRING)
    private Smoke smoke;

    @Enumerated(value = EnumType.STRING)
    private WithAnimals withAnimals;

    @Enumerated(value = EnumType.STRING)
    private Music music;
}
