package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.Rating;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.RatingService;
import am.greenlight.greenlight.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;
    private final UserService userService;


    @GetMapping("/rating/add")
    public String addRating(ModelMap model,
                            @RequestParam("number") String number,
                            @RequestParam("toId") long toId,
                            @RequestParam("announcementId") long announcementId,
                            @AuthenticationPrincipal CurrentUser currentUser) {

        long fromId = currentUser.getUser().getId();
        Rating rating = ratingService.getByToIdAndFromId(toId, fromId);

        if (rating == null) {
            rating = new Rating();
            rating.setTo(userService.getOne(toId));
            rating.setFrom(currentUser.getUser());
        }
        rating.setNumber(Long.parseLong(number));
        ratingService.save(rating);

        return "redirect:/announcement/details?id=" + announcementId;

    }

}
