package po.lights.services;

import org.springframework.stereotype.Service;
import po.lights.models.Switch;
import po.lights.repositories.PendingSwitchStorage;
import po.lights.repositories.SwitchRepository;

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
        mqttService.publish("switch/create/request", uuid.toString());
        pendingSwitchStorage.add(lighSwitch);
        return uuid;
    }

    public boolean toggleSwitch(UUID uuid) {
        Switch lightSwitch = switchRepository.findById(uuid).orElseThrow();
        lightSwitch.setEnabled(!lightSwitch.isEnabled());
        switchRepository.save(lightSwitch);
        return lightSwitch.isEnabled();
    }
}
