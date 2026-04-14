package po.lights.repositories;

import org.springframework.stereotype.Component;
import po.lights.models.Switch;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PendingSwitchStorage {

    private final Map<UUID, Switch> pending = new ConcurrentHashMap<>();

    public void add(Switch lightSwitch) {
        pending.put(lightSwitch.getSwitchId(), lightSwitch);
    }

    public Switch get(UUID id) {
        return pending.get(id);
    }

    public Switch remove(UUID id) {
        return pending.remove(id);
    }
}
