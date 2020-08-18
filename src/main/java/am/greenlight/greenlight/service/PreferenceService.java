package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Preference;
import am.greenlight.greenlight.repository.PreferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepository PrefRepository;


    public Preference save(Preference newPreference, Preference preference) {

        if (newPreference.getSpeak() == null) {
            newPreference.setSpeak(preference.getSpeak());
        }
        if (newPreference.getSmoke() == null) {
            newPreference.setSmoke(preference.getSmoke());
        }
        if (newPreference.getWithAnimals() == null) {
            newPreference.setWithAnimals(preference.getWithAnimals());
        }
        if (newPreference.getMusic() == null) {
            newPreference.setMusic(preference.getMusic());
        }
        return PrefRepository.save(newPreference);
    }


    public void deleteById(long prefId) {
        if (prefId != 1) {
            PrefRepository.deleteById(prefId);
        }
    }

    public Preference getOne(long prefId) {

        return PrefRepository.getOne(prefId);

    }

}
