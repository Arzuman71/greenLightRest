package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.ItemSearchDto;
import am.greenlight.greenlight.model.Item;
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
    public ResponseEntity<List<Item>> items(@RequestBody ItemSearchDto itemSearchDto) {
        List<Item> items = itemService.itemSearch(itemSearchDto);
        return ResponseEntity.ok(items);
    }


    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) {

        return mainService.getImageOrNull(imageName);
    }

}
