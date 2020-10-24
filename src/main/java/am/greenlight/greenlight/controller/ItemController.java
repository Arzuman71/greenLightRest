package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.ItemReqDto;
import am.greenlight.greenlight.dto.ItemResDto;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.CarService;
import am.greenlight.greenlight.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final CarService carService;
    private final ModelMapper modelMapper;


    @PostMapping("/item")
    public ResponseEntity<Object> save(@ModelAttribute ItemReqDto itemReqDto,
                                       @AuthenticationPrincipal CurrentUser currentUser) {
        Car car = null;
        long carId = itemReqDto.getCarId();
        if (carId != 0) {
            car = carService.getOne(carId);
            if (!(car.getUser().equals(currentUser.getUser()))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
            }
        }
        Item item = modelMapper.map(itemReqDto, Item.class);
        item.setCar(car);
        item = itemService.save(item);
        return ResponseEntity.ok(item);
    }

    //ok
    @GetMapping("/item/{id}")
    public ResponseEntity<ItemResDto> findById(@PathVariable("id") Long id) {
        ItemResDto itemResDto = new ItemResDto();
        Optional<Item> item = itemService.findById(id);
        if (item.isPresent()) {
            itemResDto = modelMapper.map(item, ItemResDto.class);
        }
        return ResponseEntity.ok(itemResDto);
    }

    @GetMapping("/item/active")
    public ResponseEntity<List<Item>> getItemsActive(@AuthenticationPrincipal CurrentUser currentUser) {
        long id = currentUser.getUser().getId();
        List<Item> items = itemService.findAllByUserIdAndStatus(id, Status.ACTIVE);

        return ResponseEntity.ok(items);
    }

    @GetMapping("/item/archived")
    public ResponseEntity<List<Item>> getItemsArchived(@AuthenticationPrincipal CurrentUser currentUser) {
        long id = currentUser.getUser().getId();
        List<Item> items = itemService.findAllByUserIdAndStatus(id, Status.ARCHIVED);

        return ResponseEntity.ok(items);
    }


    //ok
    @PutMapping("/item/change")
    public ResponseEntity<ItemReqDto> changeItem(@RequestBody ItemReqDto itemReqDto,
                                                 @AuthenticationPrincipal CurrentUser currentUser) {
        long carId = itemReqDto.getCarId();
        if (carId != 0) {
            Car car = carService.getOne(carId);
            if (!(car.getUser().equals(currentUser.getUser()))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(itemReqDto);
            }
        }
        Item announcement = modelMapper.map(itemReqDto, Item.class);
        itemService.save(announcement);
        return ResponseEntity.ok(itemReqDto);
    }

    //getOne or findById
    @DeleteMapping("/item/{id}")
    public ResponseEntity.BodyBuilder delete(
            @PathVariable("id") long id,
            CurrentUser currentUser) {

        Item item = itemService.getOne(id);
        if (!(item.getUser().equals(currentUser.getUser()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
        }
        item.setStatus(Status.DELETED);
        itemService.save(item);
        return ResponseEntity.ok();
    }


}