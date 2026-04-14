import org.eclipse.paho.client.mqttv3.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LightsController {

    private static final String BROKER = "tcp://localhost:1883";
    private static final String CLIENT_ID = "light-controller";
    private static Map<UUID, Switch> lights = new HashMap();
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws Exception {
        MqttClient client = new MqttClient(BROKER, CLIENT_ID);

        MqttConnectOptions options = new MqttConnectOptions();
        options.setAutomaticReconnect(true);
        options.setCleanSession(true);

        client.connect(options);

        client.subscribe("switch/create/request", (topic, msg) -> {
            String payload = new String(msg.getPayload());
            UUID uuid = UUID.fromString(payload);

            Switch lightSwitch = new Switch(uuid, false);
            lights.put(uuid, lightSwitch);


            SwitchCreateResponse response = new SwitchCreateResponse();
            response.setSwitchId(uuid.toString());
            response.setApproved(true);
            String json = objectMapper.writeValueAsString(response);

            MqttMessage message = new MqttMessage(json.getBytes());
            message.setQos(1);
            client.publish("switch/create/response", message);
        });
    }
}