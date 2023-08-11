package com.lisnenko.tasktracker.security;

import com.lisnenko.tasktracker.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    //beans
    //bcrypt bean definition
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder(); }


    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth =new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);//set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests(
                configurer->
                        configurer
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/employees/").hasRole("EMPLOYEE")
                                .requestMatchers("/employees/leaders/**").hasRole("MANAGER")
                                .requestMatchers("/employees/systems/**").hasRole("ADMIN")
                                .requestMatchers("/register/**").permitAll()
                                .anyRequest().authenticated())
                .formLogin(form->
                        form
                                .loginPage("/showMyLoginPage")
                                .loginProcessingUrl("/authenticateTheUser")
                                .permitAll())
                .logout(logoutConfigurer ->
                        logoutConfigurer
                                .permitAll()
                                .logoutSuccessUrl("/"))
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied"));

        return httpSecurity.build();
    }

//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(configurer ->
//                    configurer
//                            .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
//                            .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
//                            .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
//                            .requestMatchers(HttpMethod.PUT, "/api/employees/").hasRole("MANAGER")
//                            .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
//        );
//
//        // use HTTP basic authentication
//        http.httpBasic(Customizer.withDefaults());
//
//        // disable Cross Site Request Forgery (CSRF)
//        // in general, not required for stateless REST APIs that use POST, PUT, DELETE, and/or PATCH
//        http.csrf(csrf -> csrf.disable());
//
//        return http.build();
//    }
//

    // add support for JDBC ... no more hardcode users
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
//
//        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//
//        // define query to retrieve a user by username
//        jdbcUserDetailsManager.setUsersByUsernameQuery(
//                "select user_id, pw, active from members where user_id=?"
//        );
//
//        // define query to retrieve the authorities/roles by username
//        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
//                "select user_id, role from roles where user_id=?"
//        );
//
//        return jdbcUserDetailsManager;
//    }


//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//
//        UserDetails john = User.builder()
//                .username("john")
//                .password("{noop}test123")
//                .roles("EMPLOYEE")
//                .build();
//
//        UserDetails mary = User.builder()
//                .username("mary")
//                .password("{noop}test123")
//                .roles("EMPLOYEE", "MANAGER")
//                .build();
//
//        UserDetails susan = User.builder()
//                .username("susan")
//                .password("{noop}test123")
//                .roles("EMPLOYEE", "MANAGER", "ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(john, mary, susan);
//    }



}
