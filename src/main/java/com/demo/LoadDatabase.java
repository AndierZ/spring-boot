package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ItemRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Item("Pen", 1.2)));
            log.info("Preloading " + repository.save(new Item("Apple", 0.23)));
        };
    }
}
