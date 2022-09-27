package modelo;

import controlador.ControladorProducto;
import modelo.RepositorioProducto;
import modelo.Producto;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import vista.VistaInventario;

@SpringBootApplication
public class InventarioAppApplication {

    @Autowired
    RepositorioProducto repositorioProducto;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(InventarioAppApplication.class);
        builder.headless(false);
        ConfigurableApplicationContext context = builder.run(args);
        
    }
    
    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            final Log logger = LogFactory.getLog(getClass());
            ControladorProducto controlador = new ControladorProducto(repositorioProducto,new VistaInventario());
        };
    }

}
