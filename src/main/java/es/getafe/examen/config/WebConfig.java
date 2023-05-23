package es.getafe.examen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import es.getafe.examen.vista.Controller;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	@Bean("/home/*")
	public HttpRequestHandler miController() {
		return new Controller();
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/home/login");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/recursos/**").addResourceLocations("WEB-INF/recursos/");
	}

}
