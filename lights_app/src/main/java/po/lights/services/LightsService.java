package po.lights.services;

import org.springframework.stereotype.Service;
import po.lights.models.Switch;
import po.lights.repositories.SwitchRepository;

import java.util.UUID;

@Service
public class LightsService {
    public LightsService(SwitchRepository switchRepository) {
        this.switchRepository = switchRepository;
    }

    private final SwitchRepository switchRepository;

    public UUID createSwitch(Switch lighSwitch) {
        UUID uuid = UUID.randomUUID();
        lighSwitch.setSwitchId(uuid);
        switchRepository.save(lighSwitch);
        return uuid;
    }

    public boolean toggleSwitch(UUID uuid) {
        Switch lightSwitch = switchRepository.findById(uuid).orElseThrow();
        lightSwitch.setEnabled(!lightSwitch.isEnabled());
        switchRepository.save(lightSwitch);
        return lightSwitch.isEnabled();
    }
}
