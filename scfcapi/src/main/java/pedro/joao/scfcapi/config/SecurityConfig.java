package pedro.joao.scfcapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pedro.joao.scfcapi.service.UsuarioService;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( "/api/v1/usuarios/**")
                .permitAll()
                .antMatchers( "/api/v1/alunos/**")
                .permitAll()
                .antMatchers( "/api/v1/alunosAulasTeoricas/**")
                .permitAll()
                .antMatchers( "/api/v1/alunosExamesPraticos/**")
                .permitAll()
                .antMatchers( "/api/v1/alunosExamesTeoricos/**")
                .permitAll()
                .antMatchers( "/api/v1/alunosSimulados/**")
                .permitAll()
                .antMatchers( "/api/v1/aulasPraticas/**")
                .permitAll()
                .antMatchers( "/api/v1/aulasTeoricas/**")
                .permitAll()
                .antMatchers( "/api/v1/categorias/**")
                .permitAll()
                .antMatchers( "/api/v1/contratos/**")
                .permitAll()
                .antMatchers( "/api/v1/examesPraticos/**")
                .permitAll()
                .antMatchers( "/api/v1/examesteoricos/**")
                .permitAll()
                .antMatchers( "/api/v1/instrutores/**")
                .permitAll()
                .antMatchers( "/api/v1/instrutoresExamesPraticos/**")
                .permitAll()
                .antMatchers( "/api/v1/simulados/**")
                .permitAll()
                .antMatchers( "/api/v1/veiculos/**")
                .permitAll()
                .antMatchers( "/api/v1/veiculosExamesPraticos/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }
}