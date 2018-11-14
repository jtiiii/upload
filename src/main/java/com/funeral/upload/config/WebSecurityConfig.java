package com.funeral.upload.config;

//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author FuneralObjects
 * CreateTime 2018/11/11 11:17 PM
 */
//@EnableWebSecurity
public class WebSecurityConfig implements WebMvcConfigurer {

//    @Bean
//    public UserDetailsService userDetailsService() throws Exception{
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        manager.createUser(User.withUsername("admin").passwordEncoder(encoder::encode).password("test").roles("USER").build());
//        return manager;
//    }
}
