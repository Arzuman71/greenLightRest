package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.ItemSearchDto;
import am.greenlight.greenlight.dto.PageResDto;
import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.service.ItemService;
import am.greenlight.greenlight.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;
    private final ItemService itemService;
    private PageResDto pageResDto = new PageResDto();


    @GetMapping("/")
    public ResponseEntity<PageResDto> items(ItemSearchDto itemSearchDto,
                                               @RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequest pageRequest = PageRequest.of(page - 1, size, Sort.by(Sort.Order.desc("updatedDate")));
        Page<Item> items = itemService.itemSearch(pageRequest, itemSearchDto);
        pageResDto.setItems(items);
        int totalPages = items.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            pageResDto.setPageNumbers(pageNumbers);
        }

        return ResponseEntity.ok(pageResDto);
    }


    @GetMapping(value = "/image", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("name") String imageName) {

        return mainService.getImageOrNull(imageName);
    }

}
