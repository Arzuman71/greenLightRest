package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.ItemSearchDto;
import am.greenlight.greenlight.dto.ItemSearchResDto;
import am.greenlight.greenlight.service.ItemService;
import am.greenlight.greenlight.service.MainService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final MainService mainService;
    private final ItemService itemService;

    @PostMapping("/")
    public ResponseEntity<List<ItemSearchResDto>> items(@RequestBody ItemSearchDto itemSearchDto) {
        log.info("searching outset {}, end {}, type {}", itemSearchDto.getOutset(), itemSearchDto.getEnd(), itemSearchDto.getType());
        List<ItemSearchResDto> itemsDto = itemService.itemSearch(itemSearchDto);

        return ResponseEntity.ok(itemsDto);
    }

    //petq e poxel tip@ zaprosi rexuest param
    @GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@PathVariable("name") String imageName) {

        return mainService.getImageOrNull(imageName);
    }

}
