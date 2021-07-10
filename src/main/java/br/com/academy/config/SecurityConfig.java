package br.com.academy.config;

import br.com.academy.service.UsersDetailsService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@SuppressWarnings("java:S5344")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsersDetailsService usersDetailsService;

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
        log.info("Password encoded {} ", passwordEncoder.encode("academy"));

        //  Usuário vindo da memória
        auth.inMemoryAuthentication()
                .withUser("daniel")
                .password(passwordEncoder.encode("academy"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("clarice")
                .password(passwordEncoder.encode("academy"))
                .roles("USER");

        //  Usuário vindo do Banco de dados
        auth.userDetailsService(usersDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
