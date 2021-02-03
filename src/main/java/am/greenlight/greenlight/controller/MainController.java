package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.ItemSearchDto;
import am.greenlight.greenlight.dto.ItemSearchResDto;
import am.greenlight.greenlight.service.ItemService;
import am.greenlight.greenlight.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final ItemService itemService;

    @PostMapping("/")
    public ResponseEntity<List<ItemSearchResDto>> items(@RequestBody ItemSearchDto itemSearchDto) {
        List<ItemSearchResDto> itemsDto = itemService.itemSearch(itemSearchDto);

        return ResponseEntity.ok(itemsDto);
    }

    //petq e poxel tip@ zaprosi rexuest param
    @GetMapping(value = "/user/avatar/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getUserAvatar(@PathVariable("name") String imageName) {

        return mainService.getUserAvatarOrNull(imageName);
    }

    @GetMapping(value = "/car/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getCarImage(@PathVariable("name") String imageName) {

        return mainService.getCarImageOrNull(imageName);
    }

    @GetMapping(value = "/advertisement/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getAdvertisementImageImage(@PathVariable("name") String imageName) {

        return mainService.getAdvertisementImageOrNull(imageName);
    }

}
