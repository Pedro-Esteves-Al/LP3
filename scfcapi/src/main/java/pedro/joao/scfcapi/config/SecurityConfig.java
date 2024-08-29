package pedro.joao.scfcapi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import pedro.joao.scfcapi.security.JwtAuthFilter;
import pedro.joao.scfcapi.security.JwtService;
import pedro.joao.scfcapi.service.UsuarioService;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private JwtService jwtService;
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public OncePerRequestFilter jwtFilter(){
        return new JwtAuthFilter(jwtService, usuarioService);
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
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/alunos/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/alunosAulasTeoricas/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/alunosExamesPraticos/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/alunosExamesTeoricos/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/alunosSimulados/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/aulasPraticas/**")
                        .hasAnyRole("ADMIN","USER")
                    .antMatchers( "/api/v1/aulasTeoricas/**")
                        .hasAnyRole("ADMIN","USER")
                    .antMatchers( "/api/v1/categorias/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/contratos/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/examesPraticos/**")
                        .hasAnyRole("ADMIN","USER")
                    .antMatchers( "/api/v1/examesteoricos/**")
                        .hasAnyRole("ADMIN","USER")
                    .antMatchers( "/api/v1/instrutores/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/instrutoresExamesPraticos/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/simulados/**")
                        .hasAnyRole("ADMIN","USER")
                    .antMatchers( "/api/v1/veiculos/**")
                        .hasAnyRole("ADMIN")
                    .antMatchers( "/api/v1/veiculosExamesPraticos/**")
                        .hasAnyRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
}