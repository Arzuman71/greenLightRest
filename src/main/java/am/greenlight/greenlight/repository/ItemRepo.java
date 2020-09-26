package am.greenlight.greenlight.repository;

import am.greenlight.greenlight.model.Item;
import am.greenlight.greenlight.model.enumForUser.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {


    List<Item> findAllByUserIdAndStatus(long id, Status status);

}
