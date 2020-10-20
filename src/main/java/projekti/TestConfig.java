package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import projekti.services.CustomUserDetailsService;

@Profile("test")
@Configuration
@EnableWebSecurity
public class TestConfig extends WebSecurityConfigurerAdapter {
    
  @Autowired
  private CustomUserDetailsService userDetailsService;

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.headers().frameOptions().sameOrigin();
    
    http.authorizeRequests()
      .anyRequest().permitAll();
    
    http.formLogin()
      .loginPage("/login")
      .defaultSuccessUrl("/wall")
      .failureUrl("/login?error")
      .and()
      .logout()
      .deleteCookies("JSESSIONID", "XSRF-TOKEN")
      .clearAuthentication(true)
      .invalidateHttpSession(true)
      .and()
      .userDetailsService(userDetailsService);
  }

  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}