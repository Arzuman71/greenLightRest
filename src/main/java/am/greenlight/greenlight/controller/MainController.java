package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.ItemSearchDto;
import am.greenlight.greenlight.dto.ItemSearchResDto;
import am.greenlight.greenlight.service.ItemService;
import am.greenlight.greenlight.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {
    private final MainService mainService;
    private final ItemService itemService;

    @PostMapping()
    public Page<ItemSearchResDto> items(@RequestBody ItemSearchDto itemSearchDto, Pageable pageable) {
        Page<ItemSearchResDto> itemsDto = itemService.itemSearch(itemSearchDto, pageable);

        return itemsDto;
    }

    @GetMapping(value = "/image/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getCarImage(@PathVariable("name") String imageName) {

        return mainService.getBytes(imageName);
    }

}
