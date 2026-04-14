package po.lights.services;

import org.springframework.stereotype.Service;
import po.lights.models.Switch;
import po.lights.repositories.PendingSwitchStorage;
import po.lights.repositories.SwitchRepository;

import java.util.UUID;

@Service
public class SwitchApprovalListener {

    private final PendingSwitchStorage pendingSwitchStore;
    private final SwitchRepository switchRepository;

    public SwitchApprovalListener(
            PendingSwitchStorage pendingSwitchStore,
            SwitchRepository switchRepository) {
        this.pendingSwitchStore = pendingSwitchStore;
        this.switchRepository = switchRepository;
    }

    public void onApproval(String switchId, boolean approved) {
        UUID uuid = UUID.fromString(switchId);

        Switch pending = pendingSwitchStore.get(uuid);

        if (pending == null) return;

        if (approved) {
            switchRepository.save(pending);
        }

        pendingSwitchStore.remove(uuid);
    }
}

