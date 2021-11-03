package au.com.metlife.reference.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String issuerUri;

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    String jwksUri;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
		
		/*
		 * http .authorizeRequests(authorizeRequests -> authorizeRequests
		 * .antMatchers("/services").authenticated()
		 * 
		 * ) .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer .jwt(jwt
		 * -> jwt.decoder(JwtDecoders.fromIssuerLocation(issuerUri) ) ) );
		 */
		 
        
		
		  http.authorizeRequests() .antMatchers("/api/public").permitAll()
		  .antMatchers("/services/private").authenticated()
		  //.antMatchers("/services").authenticated()
		  .antMatchers("/api/private-scoped").hasAuthority("SCOPE_read:messages")
		  //.antMatchers("/api/*").hasAuthority("SCOPE_fund_read")
		  .antMatchers("/api/secure/**").hasAuthority("SCOPE_fund_read") 
		  
		  .antMatchers(HttpMethod.POST,"/api/admin/**").hasAuthority("SCOPE_fund_read")
		  //.antMatchers(HttpMethod.POST,"/api/admin/**").hasAuthority("SCOPE_admin")
		  .and().cors() .and().oauth2ResourceServer().jwt() .jwkSetUri(jwksUri);
		  
		  ;
		 
		 
		  http.cors().and().csrf().disable();    	
        
      //.hasAuthority("SCOPE_read:messages")
    }
    
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}