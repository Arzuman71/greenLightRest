package am.greenlight.greenlight.service;


import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepo itemRepo;

    public Item save(Item announcement) {
        return itemRepo.save(announcement);
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

    public List<Item> findAllByUserIdAndState(long id, Status state) {
        return itemRepo.findAllByUserIdAndStatus(id, state);
    }


}
