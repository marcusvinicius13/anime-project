package br.com.academy.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true) // Fala pro Spring habilitar a parte de prePort
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
        *   Essa configuração é essencial para obrigar os clientes que fazem requisições http, para esse back,
        *   tem que mandar o token gerado pelo backend.
        *   csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) : configura o cors
        * */
        http.csrf().disable() //    Troca essa linha pela linha de cima
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("marcus")
                .password(passwordEncoder.encode("academy"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("vinicius")
                .password(passwordEncoder.encode("academy"))
                .roles("USER");
    }
}


to continue https://www.youtube.com/watch?v=NZHLNeoUYWM&list=PL62G310vn6nFBIxp6ZwGnm8xMcGE3VA5H&index=43