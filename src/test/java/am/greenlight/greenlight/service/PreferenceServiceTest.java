package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Preference;
import am.greenlight.greenlight.repository.PreferenceRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class PreferenceServiceTest {

    @Mock
    private PreferenceRepo preferenceRepo;

    @Mock
    private Preference preference;


    private PreferenceService preferenceService;

    public PreferenceServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.preferenceService = new PreferenceService(preferenceRepo);
    }

    @Test
    void save_Ok() {
        given(preferenceRepo.save(preference)).willReturn(preference);
        preference = preferenceService.save(preference, preference);
        assertThat(preference).isNotNull();
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