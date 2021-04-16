package eu.winwinit.bcc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@ComponentScan("eu.winwinit.bcc.*")
@EntityScan("eu.winwinit.bcc.entities")
@PropertySources(
		{
		@PropertySource(value={"file:${bcc-anagrafiche.properties}"}),
		@PropertySource(value={"classpath:application.properties"})
		})
public class TomcatApplication extends SpringBootServletInitializer {
    
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
	public static void main(String[] args) {
        SpringApplication.run(TomcatApplication.class, args);
    }
}
