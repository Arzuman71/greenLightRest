package am.greenlight.greenlight.repository;


import am.greenlight.greenlight.model.Images;
import am.greenlight.greenlight.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagesRepo extends JpaRepository<Images, Long> {

}
