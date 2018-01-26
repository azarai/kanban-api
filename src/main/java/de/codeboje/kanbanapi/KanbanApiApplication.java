package de.codeboje.kanbanapi;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableJdbcHttpSession
public class KanbanApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(KanbanApiApplication.class, args);
	}
	
	
//	@Bean
//	public EmbeddedDatabase dataSource() {
//		return new EmbeddedDatabaseBuilder() 
//				.setType(EmbeddedDatabaseType.H2)
//				.addScript("org/springframework/session/jdbc/schema-h2.sql").build();
//	}


	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken();
	}
}
