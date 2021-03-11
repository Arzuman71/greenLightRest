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


    @PostMapping(path = "/car/picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void savePicture(@RequestParam("carId") int id,
                            @RequestParam(value = "image") MultipartFile file) {

        imagesService.save(id, file);
    }

    @PutMapping(path = "/car/picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void changePicture(@RequestParam("pictureId") int id,
                              @RequestParam(value = "image") MultipartFile file) {

        imagesService.change(id, file);
    }


    @DeleteMapping(path = "/car/picture{pictureId}")
    public void deletePicture(@PathVariable("pictureId") int id) {

        imagesService.deleteById(id);
    }


}
