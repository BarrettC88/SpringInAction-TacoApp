package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	/*@Autowired
	DataSource dataSource;*/
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		//Handling users in Memory
		/*auth.inMemoryAuthentication().withUser("buzz").password("infinity").authorities("ROLE_USER").and().
		withUser("woody").password("bullseye").authorities("ROLE_USER");*/
		
		//Handling auth through JDBC
		/*auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username, password, enabled from Users " +
			"where username=?")
		.authoritiesByUsernameQuery(
		"select username, authority from UserAuthorities " +
		"where username=?").passwordEncoder(new Pbkdf2PasswordEncoder("53cr3t"));*/
		
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
	      .authorizeRequests()
	        .antMatchers(HttpMethod.OPTIONS).permitAll() // needed for Angular/CORS
	        .antMatchers(HttpMethod.POST, "/api/ingredients").permitAll()
	        .antMatchers("/design", "/orders/**")
	            .permitAll()
	            //.access("hasRole('ROLE_USER')")
	        .antMatchers(HttpMethod.PATCH, "/ingredients").permitAll()
	        .antMatchers("/**").access("permitAll")
	        
	      .and()
	        .formLogin()
	          .loginPage("/login")
	          
	      .and()
	        .httpBasic()
	          .realmName("Taco Cloud")
	          
	      .and()
	        .logout()
	          .logoutSuccessUrl("/")
	          
	      .and()
	        .csrf()
	          .ignoringAntMatchers("/h2-console/**", "/ingredients/**", "/design", "/orders/**", "/api/**")

	      // Allow pages to be loaded in frames from the same origin; needed for H2-Console
	      .and()  
	        .headers()
	          .frameOptions()
	            .sameOrigin()
	      ;
		
		/*http.authorizeRequests().
		antMatchers("/orders").
		hasRole("USER").
		antMatchers("/**").permitAll().and().
		formLogin().
		loginPage("/login").and().logout().logoutSuccessUrl("/");
		
		http.csrf().disable();
        http.headers().frameOptions().disable();*/
	}
	
	@Bean
	public PasswordEncoder encoder() {
		return new Pbkdf2PasswordEncoder("53cr3t");
	}
	
	
}
