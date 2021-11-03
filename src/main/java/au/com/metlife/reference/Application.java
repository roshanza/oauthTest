package au.com.metlife.reference;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCaching
@EntityScan
public class Application {
	private static final Logger LOGGER=LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	
	
	  @Bean public ModelMapper modelMapper() {
		  
		  ModelMapper modelMapper=new ModelMapper(); 
		  modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT)			  ;
		  modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
	  
		  return modelMapper;
	  }
	 
	
	
	 
}
