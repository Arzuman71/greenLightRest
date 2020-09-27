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
    @Mock
    Rating rating;
    private RatingService ratingService;


    RatingServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.ratingService = new RatingService(ratingRepo);
    }


    @Test
    void ratingById_Ok() {
        List<Rating> list = new ArrayList<>();
        given(ratingRepo.findAllByToId(22)).willReturn(list);
        double sum = ratingService.findAllByToId(22);
        assertThat(sum).isNotNull();
    }


    @Test
    void save_Ok() {
        given(ratingRepo.save(rating)).willReturn(rating);
        Rating res = ratingService.save(rating);
        assertThat(res).isNotNull();
    }

    @Test
    void getByToIdAndFromId_Ok() {
        given(ratingRepo.getByToIdAndFromId(11, 11)).willReturn(rating);
        rating = ratingService.getByToIdAndFromId(11, 11);
        assertThat(rating).isNotNull();
    }

    @Test
    void getByToIdAndFromId_Null() {
        given(ratingRepo.getByToIdAndFromId(11, 11)).willReturn(null);
        rating = ratingService.getByToIdAndFromId(11, 11);
        assertThat(rating).isNull();
    }

}