package am.greenlight.greenlight.service;


import am.greenlight.greenlight.dto.ItemSearchDto;
import am.greenlight.greenlight.dto.ItemSearchResDto;
import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public Item save(Item item) {
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


    public List<ItemSearchResDto> itemSearch(ItemSearchDto itemSearchDto) {
        LocalDateTime from = LocalDateTime.now();

        if (itemSearchDto.getOutset().equals("")) {
            itemSearchDto.setOutset("_");
        }
        if (itemSearchDto.getEnd().equals("")) {
            itemSearchDto.setEnd("_");
        }
        if (itemSearchDto.getDateFrom() != null) {
            from = itemSearchDto.getDateFrom().atStartOfDay();
        }
        itemSearchDto.setDateTo(from.plusDays(1));

        List<Item> items = itemRepo.itemSearch(itemSearchDto.getOutset(),
                itemSearchDto.getEnd(),
                itemSearchDto.getType(),
                from, itemSearchDto.getDateTo());

        return getItemDtoFromItem(items);

    }

    private List<ItemSearchResDto> getItemDtoFromItem(List<Item> items) {
        List<ItemSearchResDto> itemDto = new ArrayList<>();
        items.forEach(i -> {
            String picUrl = "17.png";

            if (i.getCar() != null && i.getCar().getPicUrl() != null) {
                picUrl = i.getCar().getPicUrl();
            }
            itemDto.add(ItemSearchResDto.builder()
                    .picture(picUrl)
                    .name(i.getUser().getName())
                    .surname(i.getUser().getSurname())
                    .outset(i.getOutset())
                    .end(i.getEnd())
                    .Rating(ratingService.findAllByToId(i.getUser().getId()))
                    .build());
        });
        return itemDto;
    }

}
