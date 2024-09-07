package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Preference;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumPreferance.Music;
import am.greenlight.greenlight.model.enumPreferance.Smoke;
import am.greenlight.greenlight.model.enumPreferance.Speak;
import am.greenlight.greenlight.model.enumPreferance.WithAnimals;
import am.greenlight.greenlight.repository.PreferenceRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class PreferenceServiceTest {

    @Mock
    private PreferenceRepo preferenceRepo;

    @Mock
    private Preference preference;


    @InjectMocks
    private PreferenceService preferenceService;

    public PreferenceServiceTest() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void save_Ok() {
        User user = new User();
        user.setPreference(Preference.builder().id(12)
                .smoke(Smoke.AGAINST)
                .withAnimals(WithAnimals.AGAINST)
                .speak(Speak.AGAINST)
                .music(Music.AGAINST)
                .build());
        given(preferenceRepo.save(preference)).willReturn(preference);
        user = preferenceService.save(preference, user);
        assertThat(user).isNotNull();
        assertThat(user).isEqualTo(user);
        assertThat(user.getPreference()).isNotNull();
    }

    @Test
    void getOne_Ok() {
        given(preferenceRepo.getOne(11L)).willReturn(preference);
        preference = preferenceService.getOne(11);
        assertThat(preference).isNotNull();
    }

    @Test
    void getOne_Null() {
        given(preferenceRepo.getOne(11L)).willReturn(null);
        preference = preferenceService.getOne(11);
        assertThat(preference).isNull();
    }
}