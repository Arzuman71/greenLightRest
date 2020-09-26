package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.Advertisement;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping("/advertisements")
    public ResponseEntity<List<Advertisement>> findAll() {
        List<Advertisement> advertisements = advertisementService.findAll();
        return ResponseEntity.ok(advertisements);
    }


    @PostMapping("/advertisement")
    public ResponseEntity.BodyBuilder save( @ModelAttribute Advertisement advertisement,
                             @AuthenticationPrincipal CurrentUser currentUser,
                             @RequestParam("image") MultipartFile file) {
        advertisement.setUser(currentUser.getUser());
        advertisementService.save(advertisement, file);
        return ResponseEntity.ok();
    }

    @DeleteMapping("/advertisement/{id}")
    public ResponseEntity.BodyBuilder delete(@PathVariable("id") int id) {
        Advertisement advertisement = advertisementService.getOne(id);
        advertisement.setStatus(Status.ARCHIVED);
        advertisementService.save(advertisement);
        return ResponseEntity.ok();

    }

}
