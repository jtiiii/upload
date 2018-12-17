package com.funeral.upload;

import com.funeral.upload.config.SpringWebConfig;
import com.funeral.upload.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.context.WebApplicationContext;

/**
 * Spring-Boot 服务启动及其部分配置
 * @author FuneralObjects
 */
@SpringBootApplication
@ComponentScans( value = {
		@ComponentScan( "com.funeral.upload.service" ),
		@ComponentScan( "com.funeral.upload.aspect" ),
		@ComponentScan( "com.funeral.upload.security" ),
		@ComponentScan(basePackageClasses = { SpringWebConfig.class, WebSecurityConfig.class })
})
public class UploadApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run( UploadApplication.class, args );
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(UploadApplication.class);
	}
}
