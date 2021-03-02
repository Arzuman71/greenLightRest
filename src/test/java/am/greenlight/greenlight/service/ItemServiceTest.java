package am.greenlight.greenlight.service;

import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.enumForUser.Status;
import am.greenlight.greenlight.repository.ItemRepo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

class ItemServiceTest {
    @Mock
    private ItemRepo itemRepo;
    @Mock
    private Item item;
    @Mock
    private List<Item> items;
    @Mock
    private PageRequest pageRequest;
    @Mock
    private Page<Item> itemPage;
    @Mock
    private ModelMapper modelMapper;

    private ItemService itemService;
    private RatingService ratingService;


    ItemServiceTest() {
        MockitoAnnotations.initMocks(this);
        this.itemService = new ItemService(itemRepo, ratingService, modelMapper);
    }

    @Test
    void save_Ok() {
        given(itemRepo.save(item)).willReturn(item);
        item = itemService.changeStatus(item, Status.ACTIVE);
        assertThat(item).isNotNull();
    }

    @Test
    void findAll_Ok() {
        given(itemRepo.findAll()).willReturn(items);
        items = itemService.findAll();
        assertThat(items).isNotNull();
    }

    @Test
    void findAllPage_Ok() {
        given(itemRepo.findAll(pageRequest)).willReturn(itemPage);
        itemPage = itemService.findAll(pageRequest);
        assertThat(itemPage).isNotNull();

    }

    @Test
    void getOne_Ok() {
        given(itemRepo.getOne(11L)).willReturn(item);
        item = itemService.getOne(11L);
        assertThat(item).isNotNull();
    }

    @Test
    void findAllByUserIdAndState() {
        given(itemRepo.findAllByUserIdAndStatus(11, Status.ARCHIVED)).willReturn(items);
        items = itemService.findAllByUserIdAndStatus(11, Status.ARCHIVED);
        assertThat(items).isNotNull();
    }
}