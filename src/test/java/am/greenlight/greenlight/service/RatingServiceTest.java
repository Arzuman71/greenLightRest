package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Rating;
import am.greenlight.greenlight.repository.RatingRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class RatingServiceTest {

    @Mock
    private RatingRepo ratingRepo;
    private RatingService ratingService;


    RatingServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.ratingService = new RatingService(ratingRepo);
    }


    @Test
    void ratingById() {
        List<Rating> list = new ArrayList<>();
        given(ratingRepo.findAllByToId(22)).willReturn(list);
        double sum = ratingService.findAllByToId(22);
        assertThat(sum).isNotNull();
    }

    @Test
    void save() {
    }

    @Test
    void getByToIdAndFromId() {
    }

}