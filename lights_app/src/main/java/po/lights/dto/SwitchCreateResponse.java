package po.lights.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SwitchCreateResponse {
    private String switchId;
    private boolean approved;
}
