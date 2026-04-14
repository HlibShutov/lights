package po.lights.services;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import po.lights.dto.SwitchCreateResponse;
import tools.jackson.databind.ObjectMapper;

@Service
public class MQTTService {
    public MQTTService(SwitchApprovalListener switchApprovalListener) {
        this.switchApprovalListener = switchApprovalListener;
    }

    private final SwitchApprovalListener switchApprovalListener;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${mqtt.broker}")
    private String broker;

    @Value("${mqtt.client-id}")
    private String clientId;

    private MqttClient client;

    @PostConstruct
    public void init() throws MqttException {
        client = new MqttClient(broker, clientId, new MemoryPersistence());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);

        client.connect(options);
        client.subscribe("switch/create/response", this::handleMessage);
    }

    public void publish(String topic, String payload) {
        try {
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1);
            client.publish(topic, message);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleMessage(String topic, MqttMessage message) {
        String payload = new String(message.getPayload());
        if (topic.equals("switch/create/response")) {
            SwitchCreateResponse response = objectMapper.readValue(payload, SwitchCreateResponse.class);
            switchApprovalListener.onApproval(
                response.getSwitchId(),
                response.isApproved()
            );
        }
    }
}