package po.lights.services;

import org.springframework.stereotype.Service;
import po.lights.models.Switch;
import po.lights.repositories.PendingSwitchStorage;
import po.lights.repositories.SwitchRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class LightsService {
    private final SwitchRepository switchRepository;
    private final MQTTService mqttService;
    private final PendingSwitchStorage pendingSwitchStorage;

    public LightsService(SwitchRepository switchRepository, MQTTService mqttService, PendingSwitchStorage pendingSwitchStorage) {
        this.switchRepository = switchRepository;
        this.mqttService = mqttService;
        this.pendingSwitchStorage = pendingSwitchStorage;
    }

    public UUID createSwitch(Switch lighSwitch) {
        UUID uuid = UUID.randomUUID();
        lighSwitch.setSwitchId(uuid);
        lighSwitch.setEnabled(false);
        mqttService.publish("switch/create/request", uuid.toString());
        pendingSwitchStorage.add(lighSwitch);
        return uuid;
    }

    public boolean toggleSwitch(UUID uuid) {
        Switch lightSwitch = switchRepository.findById(uuid).orElseThrow();
        mqttService.publish("switch/toggle", uuid.toString());
        lightSwitch.setEnabled(!lightSwitch.isEnabled());
        if (lightSwitch.getLastEnabled() == null) lightSwitch.setLastEnabled(LocalDateTime.now());
        if (lightSwitch.getTotalEnabled() == null) lightSwitch.setTotalEnabled(Duration.ZERO);
        if (!lightSwitch.isEnabled()) {
            LocalDateTime now = LocalDateTime.now();
            Duration newDuration = Duration.between(lightSwitch.getLastEnabled(), now);
            lightSwitch.setTotalEnabled(lightSwitch.getTotalEnabled().plus(newDuration));
            lightSwitch.setLastEnabled(LocalDateTime.now());
        }
        switchRepository.save(lightSwitch);
        return lightSwitch.isEnabled();
    }

    public Duration statsSwitch(UUID uuid) {
        Switch lightSwitch = switchRepository.findById(uuid).orElseThrow();
        return lightSwitch.getTotalEnabled();
    }
}
