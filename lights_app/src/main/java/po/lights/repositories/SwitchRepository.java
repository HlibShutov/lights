package po.lights.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import po.lights.models.Switch;

import java.util.UUID;

public interface SwitchRepository extends JpaRepository<Switch, UUID> {

}
