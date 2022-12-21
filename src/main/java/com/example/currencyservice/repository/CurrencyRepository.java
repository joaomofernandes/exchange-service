package com.example.currencyservice.repository;

import com.example.currencyservice.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Currency findByFromAndTo(String from, String to);
}
