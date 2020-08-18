package am.greenlight.greenlight.repository;


import am.greenlight.greenlight.model.Preference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreferenceRepository extends JpaRepository<Preference,Long> {
}
