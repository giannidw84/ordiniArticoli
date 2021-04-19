package eu.winwinit.bcc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import eu.winwinit.bcc.security.JWTAuthenticationEntryPoint;
import eu.winwinit.bcc.security.JwtAuthenticationFilter;
import eu.winwinit.bcc.service.UtenteService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JWTAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UtenteService utenteService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                                   "/configuration/ui",
                                   "/swagger-resources/**",
                                   "/configuration/security",
                                   "/swagger-ui/*",
                                   "/webjars/**");
    }
    
    // Autenticazione e autorizzazione con JWTs
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests().antMatchers(
                            "/api/v1/login",
                            "/api/v1/articoli",
                            "/api/v2/**",
                            "/api/v1/checkToken"
//                            "/v2/api-docs",
//                            "/configuration/ui",
//                            "/swagger-resources",
//                            "/configuration/security"
//                            "/swagger-ui.html",
//                            "/webjars/**",
//                            "/swagger-resources/**",
//                            "/configuration/**"
                    ).permitAll()
                    .anyRequest().fullyAuthenticated()                   
                    .and().formLogin().loginPage("/login").permitAll()
                    .and().logout().permitAll();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

//    @Override
//    public void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("user")
//                .password("{noop}password")
//                .roles(SecurityConstants.ROLE_USER);
////        Alternativamente si possono usare le authorities (permessi)
////                .authorities("ReadOnly")
//        auth.inMemoryAuthentication()
//                    .withUser("admin")
//                .password("{noop}password")
//                .roles(SecurityConstants.ROLE_USER, SecurityConstants.ROLE_ADMIN);
////        Alternativamente si possono usare le authorities (permessi)
////                .authorities("ReadOnly", "ReadWrite")
//        auth.inMemoryAuthentication()
//                .withUser("guest")
//                .password("{noop}password")
//                .roles(SecurityConstants.ROLE_GUEST);
//    }
    
    @Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
    
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(utenteService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }    
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(authenticationProvider());
    }

}
