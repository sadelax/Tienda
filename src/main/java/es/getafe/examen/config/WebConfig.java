package es.getafe.examen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import es.getafe.examen.vista.Controller;

@Configuration
@EnableWebMvc
public class WebConfig {
	
	public HttpRequestHandler miController() {
		return new Controller();
	}

}
