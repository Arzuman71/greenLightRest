package am.greenlight.greenlight.service;


import am.greenlight.greenlight.dto.ItemSearchDto;
import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepo itemRepo;

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


    public List<Item> itemSearch(ItemSearchDto itemSearchDto) {
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

        return itemRepo.itemSearch(itemSearchDto.getOutset(), itemSearchDto.getEnd(),
                itemSearchDto.getType(), from, itemSearchDto.getDateTo());
    }
}
