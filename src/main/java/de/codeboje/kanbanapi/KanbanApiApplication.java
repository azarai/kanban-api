package de.codeboje.kanbanapi;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableJdbcHttpSession
@EnableSpringDataWebSupport
@EnableSwagger2
public class KanbanApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanApiApplication.class, args);
	}

	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}
	
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .apiInfo(apiInfo() )
          .select()                                  
          .apis(RequestHandlerSelectors.basePackage("de.codeboje.kanbanapi"))              
          .paths(PathSelectors.any())                          
          .build();                                           
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Kanban API", 
          "Kanban API as a backend for testing UIs",
          "v1",
          "Free to use", 
          new Contact("Jens Boje", "codeboje.de", "info@codeboje.de"), 
          "Free to us", 
          "Free to usL",
          Collections.emptyList());
   }

}
