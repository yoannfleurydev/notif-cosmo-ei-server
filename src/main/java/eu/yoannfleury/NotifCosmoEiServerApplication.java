package eu.yoannfleury;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration
@ComponentScan
@Configuration
@SpringBootApplication
public class NotifCosmoEiServerApplication {
	@Bean
	public FilterRegistrationBean jwtFilter() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setFilter(new JwtFilter());

		// FireWall
		registrationBean.addUrlPatterns("/user/role/*");

		return registrationBean;
	}

	public static void main(String[] args) {
		SpringApplication.run(NotifCosmoEiServerApplication.class, args);
	}
}
