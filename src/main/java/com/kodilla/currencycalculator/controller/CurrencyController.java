package com.kodilla.currencycalculator.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kodilla.currencycalculator.domain.Currency;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.math.MathContext;

@Controller
@Slf4j
@RequestMapping("/")
@AllArgsConstructor
public class CurrencyController {
    private ObjectMapper objectMapper;
    private BigDecimal usdToPln = new BigDecimal("0.0");

    public CurrencyController() {
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping
    public ModelAndView show() {
        return new ModelAndView("index", "value", usdToPln);
    }

    @PostMapping("convert")
    public ModelAndView memorize(@RequestParam("value") BigDecimal value, RedirectAttributes redirect) {
        String url = "https://api.nbp.pl/api/exchangerates/rates/a/usd/?format=json";
        MathContext mathContext = new MathContext(4);

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(url, String.class);
        log.info("Current result from NBP API: {}", result);

        try {
            Currency requestedUsd = objectMapper.readValue(result, Currency.class);
            BigDecimal avgUsdRate = BigDecimal.valueOf(requestedUsd.getRates().get(0).getMid());
            usdToPln = value.multiply(avgUsdRate, mathContext);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/");
    }
}
