package am.greenlight.greenlight.repository;

import am.greenlight.greenlight.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdvertisementRepo extends JpaRepository<Advertisement,Long> {
}
