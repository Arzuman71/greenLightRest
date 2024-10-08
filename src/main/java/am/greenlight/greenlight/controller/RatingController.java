package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.RatingRequestDto;
import am.greenlight.greenlight.model.Rating;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.RatingService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;


    @PostMapping()
    public ResponseEntity<String>  addOrChangeRating(@AuthenticationPrincipal CurrentUser currentUser,
                                                     @RequestBody RatingRequestDto ratingReq) {
        ratingService.addOrChangeRating(currentUser.getUser(),ratingReq);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("{toId}")
    public ResponseEntity<Double> findAllByToId(@PathVariable("toId") int id) {
        double rating = ratingService.findAllByToId(id);
            return ResponseEntity.ok(rating);

    }
}
