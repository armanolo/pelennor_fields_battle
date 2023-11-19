package es.mmm.managerartillery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestEndorstationApplication {

    public static void main(String[] args) {
        SpringApplication.from(ManagerArtilleryApplication::main).with(TestEndorstationApplication.class).run(args);
    }

}
