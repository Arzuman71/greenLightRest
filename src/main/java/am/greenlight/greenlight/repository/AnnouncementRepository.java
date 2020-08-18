package am.greenlight.greenlight.repository;

import am.greenlight.greenlight.model.Announcement;
import am.greenlight.greenlight.model.enumForUser.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {

    List<Announcement> findAllByUserId(long id);


    List<Announcement> findAllByUserIdAndState(long id, State state);

}
