package com.example.furniture_factory.configurations;


import com.example.furniture_factory.model.employee.Employee;
import com.example.furniture_factory.model.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    EmployeeRepo employees;

    @Bean
    @Scope("prototype")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/User").hasRole("USER")
                        .requestMatchers(/*"/*"*/"/Admin").hasRole("ADMIN")
                        .requestMatchers(/*"/*"*/"/Brigadier").hasRole("BRIGADIER")
                        .requestMatchers("/*").hasRole("IT")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }

    @Bean
    @Scope("prototype")
    public UserDetailsService userDetailsService() {
            UserDetails user1 =
                User.withDefaultPasswordEncoder()
                        .username("u")
                        .password("p")
                        .roles("USER")
                        .build();

        List<UserDetails> list=new ArrayList<>();
        list.add(user1);

        for (Employee employee : employees.findAll()) {
            System.out.println(employee.getLogin());
            System.out.println(employee.getPassword());
            System.out.println(employee.getRole().toString());
            UserDetails user = User.withDefaultPasswordEncoder()
                    .username(employee.getLogin())
                    .password(employee.getPassword())
                    .roles(employee.getRole().toString())
                    .build();
            list.add(user);
        }
        return new InMemoryUserDetailsManager(list);
    }



}
