package po.lights.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Data
@Getter
@Setter
public class Switch {
    @Id
    private UUID switchId;
    private String name;
    private boolean enabled;
}
