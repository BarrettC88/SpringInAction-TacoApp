package tacos.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
	
	@Bean
	public PasswordEncoder encoder() {
		return new Pbkdf2PasswordEncoder("53cr3t");
	}
	
	
}
