package com.victor.server_report;

import com.victor.server_report.enumeration.Status;
import com.victor.server_report.model.Server;
import com.victor.server_report.repository.ServerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

import static com.victor.server_report.enumeration.Status.SERVER_UP;

@SpringBootApplication
public class ServerReportApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerReportApplication.class, args);
	}




	// running this before the app starts or so
	@Bean
	CommandLineRunner run(ServerRepository serverRepository){
		return args -> {
			serverRepository.save(new Server(
					null,
					"56.78.123.6",
					"Hillary's",
					"16 GB",
					"Email Server",
					"http://localhost:8080/api/image/server_1.png",
					SERVER_UP
			));

			serverRepository.save(new Server(
					null,
					"192.168.1.145",
					"Fedora Linux",
					"16 GB",
					"Home PC",
					"http://localhost:8080/api/image/server_2.png",
					SERVER_UP
			));

			serverRepository.save(new Server(
					null,
					"8.8.8.8",
					"Google's DNS",
					"16 GB",
					"DNS Server",
					"http://localhost:8080/api/image/server_3.png",
					SERVER_UP
			));
		};
	}


	// CORS configuration to avoid issues with the front end calls from a different address
	// can this be done from annotation on the application class?
	@Bean
	public CorsFilter corsFilter(){
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000","http://localhost:4200")); // React or Angular applications
		corsConfiguration.setAllowedHeaders(Arrays.asList(
				"Origin",
				"Access-Control-Allow-Origin",
				"Content-Type",
				"Accept",
				"Jwt-Token",
				"Authorization",
				"Origin, Accept",
				"X-Requested-With",
				"Access-Control-Request-Method",
				"Access-Control-Request-Headers"
		));
		corsConfiguration.setExposedHeaders(Arrays.asList(
				"Origin",
				"Access-Control-Allow-Origin",
				"Content-Type",
				"Accept",
				"Jwt-Token",
				"Authorization",
				"Access-Control-Allow-Origin",
				"Access-Control-Allow-Credentials",
				"Filename"
		));
		corsConfiguration.setAllowedMethods(Arrays.asList(
				"GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"
		));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);

	}
}
