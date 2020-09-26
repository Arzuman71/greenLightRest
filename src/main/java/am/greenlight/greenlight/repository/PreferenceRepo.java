package am.greenlight.greenlight.repository;


import am.greenlight.greenlight.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepo extends JpaRepository<Preference,Long> {
}
