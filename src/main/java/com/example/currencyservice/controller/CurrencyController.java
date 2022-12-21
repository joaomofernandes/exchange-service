package com.example.currencyservice.controller;

import com.example.currencyservice.model.Currency;
import com.example.currencyservice.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@RestController
@RequestMapping("currency-service")
public class CurrencyController {

    @Autowired
    private Environment environment;
    @Autowired
    private CurrencyRepository repository;

    @GetMapping(value = "/{amount}/{from}/{to}")
    public Currency getCurrency(
            @PathVariable("amount")BigDecimal amount,
            @PathVariable("from")String from,
            @PathVariable("to")String to){
        Currency currency = repository.findByFromAndTo(from, to);
        if (currency == null) throw new RuntimeException("Currency unsupported");
        BigDecimal conversionFactor = currency.getConversionFactor();
        BigDecimal convertedValue = conversionFactor.multiply(amount);
        currency.setConvertedValue(convertedValue.setScale(2, RoundingMode.CEILING));
        String port= environment.getProperty("local.server.port");
        currency.setEnvironment(port);
        return currency;
    }
}
