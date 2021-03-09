package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.response.AdvertisementResponse;
import am.greenlight.greenlight.mapper.AdvertisementMapper;
import am.greenlight.greenlight.model.Advertisement;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @GetMapping("/advertisements")
    public List<AdvertisementResponse> findAll() {
        List<AdvertisementResponse> Responses = advertisementService.findAll();

        return Responses;
    }

    //dto 2
    @PostMapping("/advertisement")
    public ResponseEntity<AdvertisementResponse> save(@AuthenticationPrincipal CurrentUser currentUser,
                                                      @RequestBody Advertisement advertisement,
                                                      @RequestParam("image") MultipartFile file) {
        advertisement.setUser(currentUser.getUser());
        AdvertisementResponse response = advertisementService.save(advertisement, file);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/advertisement/{id}")
    public void delete(@PathVariable("id") int id) {

        Advertisement advertisement = advertisementService.getOne(id);
        advertisement.setStatus(Status.ARCHIVED);
        advertisementService.save(advertisement);
    }

}
