package am.greenlight.greenlight.service;

import am.greenlight.greenlight.dto.RatingRequestDto;
import am.greenlight.greenlight.model.Rating;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.repository.RatingRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepo ratingRepository;
    private final UserService userService;


    public void addOrChangeRating(User user, RatingRequestDto ratingReq) {
        long fromId = user.getId();
        Rating rating = getByToIdAndFromId(ratingReq.getToId(), fromId);

        if (rating == null) {
            rating = new Rating();
            rating.setTo(userService.getOne(ratingReq.getToId()));
            rating.setFrom(user);
        }
        rating.setNumber(ratingReq.getNumber());
        save(rating);
    }

    public double findAllByToId(long id) {
        List<Rating> all = ratingRepository.findAllByToId(id);
        return all.stream().mapToDouble(Rating::getNumber).average().orElse(3);
    }

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }

    public Rating getByToIdAndFromId(long toId, long fromId) {

        return ratingRepository.getByToIdAndFromId(toId, fromId);
    }
}
