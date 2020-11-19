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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;
    private final CarService carService;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<Object> save(@Valid @RequestBody ItemReqDto itemReqDto, BindingResult result,
                                       @AuthenticationPrincipal CurrentUser currentUser) {

        if (!result.hasErrors()) {
            Optional<Car> car = carService.findById(itemReqDto.getCarId());
            Item item = modelMapper.map(itemReqDto, Item.class);
            item.setCar(null);
            if (car.isPresent() && (car.get().getUser().equals(currentUser.getUser()))) {
                item.setCar(car.get());
            }
            item.setUser(currentUser.getUser());
            item = itemService.save(item);
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");

    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Item> findById(@PathVariable("itemId") Long id) {
     //   ItemResDto itemResDto = new ItemResDto();
        Optional<Item> item = itemService.findById(id);
     //   if (item.isPresent()) {
      //      itemResDto = modelMapper.map(item, ItemResDto.class);
     //   }
        return ResponseEntity.ok(item.get());
    }

    @GetMapping("active")
    public ResponseEntity<List<Item>> getItemsActive(@AuthenticationPrincipal CurrentUser currentUser) {
        long id = currentUser.getUser().getId();
        List<Item> items = itemService.findAllByUserIdAndStatus(id, Status.ACTIVE);
        return ResponseEntity.ok(items);
    }

    @GetMapping("archived")
    public ResponseEntity<List<Item>> getItemsArchived(@AuthenticationPrincipal CurrentUser currentUser) {
        long id = currentUser.getUser().getId();
        List<Item> items = itemService.findAllByUserIdAndStatus(id, Status.ARCHIVED);
        return ResponseEntity.ok(items);
    }

    //ok
    @PutMapping("change")
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
    @DeleteMapping("{itemId}")
    public ResponseEntity.BodyBuilder delete(
            @PathVariable("itemId") long id,
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