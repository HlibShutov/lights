import java.util.UUID;

public class Switch {
    private UUID uuid;
    private boolean enabled;

    public Switch(UUID uuid, boolean enabled) {
        this.uuid = uuid;
        this.enabled = enabled;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
