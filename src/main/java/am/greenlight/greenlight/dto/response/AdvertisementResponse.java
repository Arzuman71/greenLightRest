package am.greenlight.greenlight.dto.response;

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
public class AdvertisementResponse {
    private long id;

    private Speak speak;

    private Smoke smoke;

    private WithAnimals withAnimals;

    private Music music;
}
