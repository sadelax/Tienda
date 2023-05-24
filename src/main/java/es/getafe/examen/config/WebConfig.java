package es.getafe.examen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@ComponentScan("es.getafe.examen.vista.controllers")
public class WebConfig implements WebMvcConfigurer {

//	@Bean("/home/*")
//	public HttpRequestHandler miController() {
//		return new Controller();
//	}

	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver vR = new InternalResourceViewResolver();
		vR.setPrefix("/WEB-INF/vistas/");
		vR.setSuffix(".jsp");
		return vR;
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
