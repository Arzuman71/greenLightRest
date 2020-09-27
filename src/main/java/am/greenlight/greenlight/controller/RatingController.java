package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.RatingRequestDto;
import am.greenlight.greenlight.model.Rating;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.RatingService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;


    @PostMapping("/rating")
    public ResponseEntity<String> addRating(@AuthenticationPrincipal CurrentUser currentUser,
                                            @ModelAttribute RatingRequestDto ratingReq) {

        long fromId = currentUser.getUser().getId();
        Rating rating = ratingService.getByToIdAndFromId(ratingReq.getToId(), fromId);

        if (rating == null) {
            rating = new Rating();
            rating.setTo(userService.getOne(ratingReq.getToId()));
            rating.setFrom(currentUser.getUser());
        }
        rating.setNumber(ratingReq.getNumber());
        ratingService.save(rating);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/rating/{id}")
    public ResponseEntity<Double> findAllByToId(@PathVariable("id") long id) {
        double rating = ratingService.findAllByToId(id);
            return ResponseEntity.ok(rating);

    }
}
