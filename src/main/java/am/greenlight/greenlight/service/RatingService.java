package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Rating;
import am.greenlight.greenlight.repository.RatingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepo ratingRepository;

    public double findAllByToId(long id) {

        List<Rating> all = ratingRepository.findAllByToId(id);
        return all.stream().mapToDouble(Rating::getNumber).average().orElse(3);
    }

    public void save(Rating rating) {

        ratingRepository.save(rating);

    }

    public Rating getByToIdAndFromId(long toId, long fromId) {

        return ratingRepository.getByToIdAndFromId(toId, fromId);

    }

}
