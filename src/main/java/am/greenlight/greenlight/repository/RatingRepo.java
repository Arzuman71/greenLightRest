package am.greenlight.greenlight.repository;

import am.greenlight.greenlight.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;

public interface RatingRepo extends JpaRepository<Rating, Long> {

    List<Rating> findAllByToId(long id);

    Rating getByToIdAndFromId(long toId, long fromId);

}
