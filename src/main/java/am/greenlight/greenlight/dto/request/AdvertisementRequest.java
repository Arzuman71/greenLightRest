package am.greenlight.greenlight.dto.request;

import am.greenlight.greenlight.model.enumPreferance.Music;
import am.greenlight.greenlight.model.enumPreferance.Smoke;
import am.greenlight.greenlight.model.enumPreferance.Speak;
import am.greenlight.greenlight.model.enumPreferance.WithAnimals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdvertisementRequest {

    private long id;

    private Speak speak;

    private Smoke smoke;

    private WithAnimals withAnimals;

    private Music music;
}
