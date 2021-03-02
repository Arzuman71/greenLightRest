package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Preference;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.repository.PreferenceRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreferenceService {

    private final PreferenceRepo PrefRepository;
    private static final Logger log = LoggerFactory.getLogger(PreferenceService.class);



    public User save(Preference newPreference, User user) {
        Preference preference = user.getPreference();

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
        if (preference.getId() != 1) {
            newPreference.setId(preference.getId());
        }
        user.setPreference(PrefRepository.save(newPreference));
        log.info("user with email - {} changed its reference" , user.getEmail());
        return user;
    }


    public Preference getOne(long prefId) {

        return PrefRepository.getOne(prefId);

    }

}
