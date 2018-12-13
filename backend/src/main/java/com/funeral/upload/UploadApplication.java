package com.funeral.upload;

import com.funeral.upload.config.SpringWebConfig;
import com.funeral.upload.config.WebSecurityConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

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
public class UploadApplication {
	public static void main(String[] args) {
		SpringApplication.run( UploadApplication.class, args );
	}

}
