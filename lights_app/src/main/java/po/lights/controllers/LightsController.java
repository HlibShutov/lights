package po.lights.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import po.lights.models.Switch;
import po.lights.services.LightsService;

import java.time.Duration;
import java.util.UUID;

@RestController
public class LightsController {
    public LightsController(LightsService lightsService) {
        this.lightsService = lightsService;
    }

    private final LightsService lightsService;

    @PostMapping(value = "/switch", produces = "application/json")
    public String createSwitch(@RequestBody Switch lightSwitch) {
        return lightsService.createSwitch(lightSwitch).toString();
    }

    @PatchMapping(value = "/switch/{uuid}", produces = "application/json")
    public boolean toggleSwitch(@PathVariable UUID uuid) {
        return lightsService.toggleSwitch(uuid);
    }

    @GetMapping(value = "/switch/{uuid}", produces = "application/json")
    public long statsSwitch(@PathVariable UUID uuid) {
        return lightsService.statsSwitch(uuid).getSeconds();
    }
}