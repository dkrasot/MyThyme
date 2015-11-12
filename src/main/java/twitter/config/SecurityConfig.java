package twitter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import javax.sql.DataSource;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//was DELETED temporary
//    public class SecurityWebInitializer extends AbstractSecurityWebApplicationInitializer {
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login")
                .and()
                .logout().logoutSuccessUrl("/login").logoutUrl("/signout")
//                .and()
//                .csrf().csrfTokenRepository(csrfTokenRepository()) //trying to add CSRF support
//                .and().csrf().disable()
                .and()
                .rememberMe()
                    .tokenRepository(new InMemoryTokenRepositoryImpl())
                    .tokenValiditySeconds(2419200) // 2 weeks in cookies
                    .key("twitterKey")
                .and()
                .httpBasic().realmName("Twitter")
                .and()
                .authorizeRequests()
                    .antMatchers("/").authenticated()
                    .antMatchers("/user/me").authenticated()
                    .antMatchers(HttpMethod.POST, "/tweets").authenticated()
                    //.antMatchers("/admin").access("isAuthenticated() and principal.username=='admin'")
//                    .antMatchers("/fileupload")//.hasRole("ADMIN")
//                                                .access("hasRole('ROLE_ADMIN') and hasIpAddress('192.168.1.2')")//example with SpEL
                    .anyRequest().permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("password").roles("USER","ADMIN") //OR
//                                                        .authorities("ROLE_USER", "ROLE_ADMIN");
        ;
//        auth.jdbcAuthentication().dataSource( add there ref to @Autowired DataSource ds)
//            .usersByUsernameQuery("select username, password, true from Users where username=?")
//            .authoritiesByUsernameQuery("select username, 'ROLE_USER' from Users where username=?")
//            .passwordEncoder(new StandardPasswordEncoder("53cr3t"));

    }


    @Bean//(name = "myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    //trying to add CSRF support
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf");
        return repository;
    }
}

// @EnableWebMvcSecurity - is deprecated - change to @EnableWebSecurity


// problem with CSRF; java.lang.ClassNotFoundException: org.springframework.security.web.access.expression.WebSecurityExpressionHandler