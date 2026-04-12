package po.lights.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import po.lights.models.Switch;

import java.util.UUID;

@Controller
public class LightsController {
    @PostMapping(value = "/switch", produces = "application/json")
    public UUID createProduct(@RequestBody Switch lightSwitch) {
        return null;
    }
}