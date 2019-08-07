package edu.mum.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("buyer").password("buyer").roles("BUYER")
                .and()
                .withUser("seller").password("seller").roles("SELLER")
                .and()
                .withUser("admin").password("admin").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // ignored homepage, account area from authentication.
        http.authorizeRequests()
                .antMatchers("/", "/account/**").permitAll()
                .antMatchers("/admin/**", "/buyer/**", "/seller/**").permitAll()
//                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/seller/**").hasRole("SELLER")
//                .antMatchers("/buyer/**").hasRole("BUYER")
                .and()
                .formLogin();
    }
}
