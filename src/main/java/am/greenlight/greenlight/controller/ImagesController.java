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
@RequestMapping("/api/cars")
public class ImagesController {
    private final ImagesService imagesService;


    @PostMapping(path = "/picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void savePicture(@RequestParam("carId") int id,
                            @RequestParam(value = "image") MultipartFile file) {

        imagesService.save(id, file);
    }

    @PutMapping(path = "/picture",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void changePicture(@RequestParam("pictureId") int id,
                              @RequestParam(value = "image") MultipartFile file) {

        imagesService.change(id, file);
    }


    @DeleteMapping(path = "/picture/{id}")
    public void deletePicture(@PathVariable("id") int id) {

        imagesService.deleteById(id);
    }


}
