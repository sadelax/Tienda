package es.getafe.examen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"es.getafe.examen.vista.actions", "es.getafe.examen.negocio", "es.getafe.examen.persistencia"})
public class AppConfig {

}
