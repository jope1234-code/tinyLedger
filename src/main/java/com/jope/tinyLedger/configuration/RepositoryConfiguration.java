package com.jope.tinyLedger.configuration;

import com.jope.tinyLedger.controller.TinyLedgerController;
import com.jope.tinyLedger.service.TinyLedgerService;
import com.jope.tinyLedger.service.TinyLedgerServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


@Configuration
public class RepositoryConfiguration {

    @Bean
    @Primary
    public TinyLedgerService tinyLedgerService(){
        return new TinyLedgerServiceImpl();
    }

    @Bean
    public TinyLedgerController tinyLedgerController(TinyLedgerService service){
        return new TinyLedgerController(service);
    }


}
