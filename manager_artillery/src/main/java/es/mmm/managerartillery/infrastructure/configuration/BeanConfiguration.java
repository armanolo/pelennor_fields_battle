package es.mmm.managerartillery.infrastructure.configuration;

import es.mmm.managerartillery.ManagerArtilleryApplication;
import es.mmm.managerartillery.domain.repository.FireAction;
import es.mmm.managerartillery.domain.repository.GetCannonAvailable;
import es.mmm.managerartillery.domain.service.FireService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan(basePackageClasses = ManagerArtilleryApplication.class)
public class BeanConfiguration {

    @Bean
    FireService fireService(GetCannonAvailable getCannon, FireAction fireAction){
        return new FireService(getCannon, fireAction);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
