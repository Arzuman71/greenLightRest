package am.greenlight.greenlight.service;

import am.greenlight.greenlight.dto.RatingRequestDto;
import am.greenlight.greenlight.model.Rating;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.repository.RatingRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {
    private final RatingRepo ratingRepository;
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(RatingService.class);


    public void addOrChangeRating(User user, RatingRequestDto ratingReq) {

        long fromId = user.getId();
        Rating rating = getByToIdAndFromId(ratingReq.getToId(), fromId);

        if (rating == null) {
            rating = new Rating();
            User userTo = userService.getOne(ratingReq.getToId());
            rating.setTo(userTo);
            rating.setFrom(user);
            log.info("user with email - {} add rating on - user with email {}", user.getEmail(), userTo.getEmail());
        }
        rating.setNumber(ratingReq.getNumber());
        log.info("user with email - {} change rating on - user with email {}", user.getEmail(), rating.getTo().getEmail());

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
