package am.greenlight.greenlight.service;


import am.greenlight.greenlight.dto.ItemReqDto;
import am.greenlight.greenlight.dto.ItemSearchDto;
import am.greenlight.greenlight.dto.ItemSearchResDto;
import am.greenlight.greenlight.model.Car;
import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.User;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepo itemRepo;
    private final RatingService ratingService;
    private final ModelMapper modelMapper;
    private static final Logger log = LoggerFactory.getLogger(ItemService.class);

    public Item save(ItemReqDto itemDto, User user, Car car) {
        Item item = modelMapper.map(itemDto, Item.class);
        item.setUser(user);
        item.setCar(car);
        log.info("user with email - {} save item", user.getEmail());
        return itemRepo.save(item);
    }

    public Item changeStatus(Item item, Status status) {

        item.setStatus(status);
//        log.info("user with email - {} changed item status- {}", item.getUser().getEmail(), status);
        return itemRepo.save(item);
    }

    public Page<Item> findAll(PageRequest pageRequest) {
        return itemRepo.findAll(pageRequest);
    }

    public List<Item> findAll() {
        return itemRepo.findAll();
    }

    public Item getOne(long id) {
        return itemRepo.getOne(id);
    }

    public Optional<Item> findById(long id) {
        return itemRepo.findById(id);
    }

    public List<Item> findAllByUserIdAndStatus(long id, Status state) {
        return itemRepo.findAllByUserIdAndStatus(id, state);
    }


    public Page<ItemSearchResDto> itemSearch(ItemSearchDto itemSearchDto, Pageable pageable) {
        LocalDateTime from = LocalDateTime.now();

        if (itemSearchDto.getDateFrom() != null) {
            from = itemSearchDto.getDateFrom().atStartOfDay();
        }
        itemSearchDto.setDateTo(from.plusDays(1));

        Page<Item> items = itemRepo.itemSearch(itemSearchDto.getOutset(),
                itemSearchDto.getEnd(), itemSearchDto.getType(),
                from, itemSearchDto.getDateTo(), pageable);
        log.info("searching outset {}, end {}, type {}", itemSearchDto.getOutset(), itemSearchDto.getEnd(), itemSearchDto.getType());

        return new PageImpl(getItemDtoFromItem(items), pageable, items.getTotalElements());

    }

    private List<ItemSearchResDto> getItemDtoFromItem(Page<Item> items) {
        List<ItemSearchResDto> itemDto = new ArrayList<>();
        items.forEach(i -> {
            itemDto.add(ItemSearchResDto.builder()
                    .id(i.getId())
                    .picture(i.getCar().getPicUrl())
                    .name(i.getUser().getName())
                    .surname(i.getUser().getSurname())
                    .outset(i.getOutset())
                    .end(i.getEnd())
                    .rating(ratingService.findAllByToId(i.getUser().getId()))
                    .build());
        });
        return itemDto;
    }
}
