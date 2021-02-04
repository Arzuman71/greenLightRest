package am.greenlight.greenlight.controller;

import am.greenlight.greenlight.dto.ItemReqDto;
import am.greenlight.greenlight.dto.ItemResDto;
import am.greenlight.greenlight.dto.ItemResDtoForMe;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.security.CurrentUser;
import am.greenlight.greenlight.service.CarService;
import am.greenlight.greenlight.service.ItemService;
import am.greenlight.greenlight.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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
    private final RatingService ratingService;

    @PostMapping("")
    public ResponseEntity<ItemReqDto> save(@Valid @RequestBody ItemReqDto itemDto, BindingResult result,
                                           @AuthenticationPrincipal CurrentUser currentUser) {

        if (!result.hasErrors()) {
            User user = currentUser.getUser();
            Optional<Car> car = carService.findById(itemDto.getCarId());
            if (car.isPresent() && (car.get().getUser().equals(user))) {
                Item item = itemService.save(itemDto, user, car.get());
                return ResponseEntity.ok(itemDto);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(itemDto);

    }

    //todo tests
    @PutMapping("/change")
    public ResponseEntity<ItemReqDto> changeItem(@Valid @RequestBody ItemReqDto itemDto, BindingResult result,
                                                 @AuthenticationPrincipal CurrentUser currentUser) {

        if (!result.hasErrors()) {
            User user = currentUser.getUser();
            Optional<Car> car = carService.findById(itemDto.getCarId());
            if (car.isPresent() && (car.get().getUser().equals(user))) {
                itemService.save(itemDto, user, car.get());
                return ResponseEntity.ok(itemDto);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(itemDto);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResDto> findById(@PathVariable("itemId") Long id) {
        Optional<Item> item = itemService.findById(id);
        ItemResDto itemResDto = new ItemResDto();
        if (item.isPresent()) {
            modelMapper.map(item.get(), itemResDto);
            double rating = ratingService.findAllByToId(itemResDto.getUser().getId());
            itemResDto.setRatingSum(rating);
        }
        return ResponseEntity.ok(itemResDto);
    }

    //ToDo test
    @GetMapping("/my/{id}")
    public ResponseEntity<ItemResDtoForMe> myItemById(@PathVariable("id") Long id) {
        Optional<Item> item = itemService.findById(id);
        ItemResDtoForMe itemDto = new ItemResDtoForMe();
        item.ifPresent(value -> modelMapper.map(value, itemDto));
        return ResponseEntity.ok(itemDto);
    }


    @GetMapping("/active")
    public ResponseEntity<List<ItemResDtoForMe>> getItemsActive(@AuthenticationPrincipal CurrentUser currentUser) {
        List<ItemResDtoForMe> itemsDto = new ArrayList();
        long id = currentUser.getUser().getId();
        List<Item> items = itemService.findAllByUserIdAndStatus(id, Status.ACTIVE);
        items.forEach(i -> itemsDto.add(modelMapper.map(i, ItemResDtoForMe.class)));
        return ResponseEntity.ok(itemsDto);
    }

    @GetMapping("/archived")
    public ResponseEntity<List<ItemResDtoForMe>> getItemsArchived(@AuthenticationPrincipal CurrentUser currentUser) {
        List<ItemResDtoForMe> itemsDto = new ArrayList();
        long id = currentUser.getUser().getId();
        List<Item> items = itemService.findAllByUserIdAndStatus(id, Status.ARCHIVED);
        items.forEach(i -> itemsDto.add(modelMapper.map(i, ItemResDtoForMe.class)));
        return ResponseEntity.ok(itemsDto);
    }


    @PutMapping("/change/active/{id}")
    public ResponseEntity<String> changeStatus(@PathVariable("id") long id,
                                               @AuthenticationPrincipal CurrentUser currentUser) {

        Item item = itemService.getOne(id);
        if (item.getUser().equals(currentUser.getUser())) {

            itemService.save(item, Status.ACTIVE);
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }

    @PutMapping("/change/archived/{id}")
    public ResponseEntity<String> changeStatusArchived(@PathVariable("id") long id,
                                                       @AuthenticationPrincipal CurrentUser currentUser) {
        Item item = itemService.getOne(id);
        if (item.getUser().equals(currentUser.getUser())) {
            itemService.save(item, Status.ARCHIVED);
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") long id,
                                         @AuthenticationPrincipal CurrentUser currentUser) {
        Item item = itemService.getOne(id);
        if (item.getUser().equals(currentUser.getUser())) {
            itemService.save(item, Status.DELETED);
            return ResponseEntity.ok("Ok");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("UNAUTHORIZED");
    }


}