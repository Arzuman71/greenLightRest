package am.greenlight.greenlight.repository;

import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.enumForItem.Type;
import am.greenlight.greenlight.model.enumForUser.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {


    List<Item> findAllByUserIdAndStatus(long id, Status status);

    @Query("SELECT i FROM Item i" +
            " WHERE i.outset = ?1 AND i.end = ?2 AND" +
            " i.type = ?3 AND i.startDate >= ?4 AND i.startDate <= ?5")
    Page<Item> itemSearch(String outset, String end, Type type, LocalDateTime Date1, LocalDateTime Date2, PageRequest pageRequest);

}
