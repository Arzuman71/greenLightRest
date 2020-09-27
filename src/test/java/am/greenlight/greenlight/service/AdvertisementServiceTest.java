package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Advertisement;
import am.greenlight.greenlight.repository.AdvertisementRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class AdvertisementServiceTest {

    @Mock
    AdvertisementRepo advertisementRepo;
    @Mock
    Advertisement advertisement;
    @Mock
    List<Advertisement> advertisements;
    @Mock
    private MockMultipartFile file;
    AdvertisementService advertisementService;

    AdvertisementServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.advertisementService = new AdvertisementService(advertisementRepo);
    }

    @Test
    void findAll_Ok() {
        given(advertisementRepo.findAll()).willReturn(advertisements);
        advertisements = advertisementService.findAll();
        assertThat(advertisements).isNotNull();
    }

    @Test
    void save_Ok() {
        given(advertisementRepo.save(advertisement)).willReturn(advertisement);
        advertisement = advertisementService.save(advertisement);
        assertThat(advertisement).isNotNull();
    }

    @Test
    void SaveWithFile_Ok() {
        given(advertisementRepo.save(advertisement)).willReturn(advertisement);
        advertisement = advertisementService.save(advertisement, file);
        assertThat(advertisement).isNotNull();
    }

    @Test
    void getOne_Ok() {
        given(advertisementRepo.getOne(11L)).willReturn(advertisement);
        advertisement = advertisementService.getOne(11);
        assertThat(advertisement).isNotNull();
    }
}