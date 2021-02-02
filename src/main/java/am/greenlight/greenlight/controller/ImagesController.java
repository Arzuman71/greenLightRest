package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.service.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class ImagesController {
    private final ImagesService imagesService;


    @PostMapping(path = "car/picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> savePicture(@RequestParam("carId") int id,
                                              @RequestParam(value = "image") MultipartFile file) {

        imagesService.save(id, file);
        return ResponseEntity.ok().body("Ok");
    }

    @PutMapping(path = "car/picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> changePicture(@RequestParam("pictureId") int id,
                                                @RequestParam(value = "image") MultipartFile file) {

         imagesService.change(id, file);
        return ResponseEntity.ok().body("Ok");
    }


}
