package com.funeral.upload;

import com.funeral.upload.config.SpringWebConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Spring-Boot 服务启动及其部分配置
 * @author FuneralObjects
 */
@SpringBootApplication
@ComponentScans( value = {
		@ComponentScan( "com.funeral.upload.service.**" ),
		@ComponentScan( "com.funeral.upload.aspect" ),
		@ComponentScan(basePackageClasses = {SpringWebConfig.class})
})
public class UploadApplication {
	public static void main(String[] args) {
		SpringApplication.run(UploadApplication.class, args);
	}


	@Bean
	public CorsFilter corsFilter(){
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedMethod("*");
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		source.registerCorsConfiguration("/**",configuration);
		return new CorsFilter(source);
	}

}
