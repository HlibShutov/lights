package po.lights;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LightsApplication {

	public static void main(String[] args) {
        java.util.TimeZone.setDefault(java.util.TimeZone.getTimeZone("Europe/Warsaw"));
		SpringApplication.run(LightsApplication.class, args);
	}

}
