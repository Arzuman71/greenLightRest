package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Rating;
import am.greenlight.greenlight.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepository ratingRepository;

    public double RatingById(long id) {
        double sum = 0;
        int size = 0;
        List<Rating> all = ratingRepository.findAllByToId(id);
        for (Rating rating : all) {
            size++;
            sum += rating.getNumber();
        }
        return sum / size;
    }

    public void save(Rating rating) {

        ratingRepository.save(rating);

    }

    public Rating getByToIdAndFromId(long toId, long fromId) {

        return ratingRepository.getByToIdAndFromId(toId, fromId);

    }

}
