package com.kodilla.currencycalculator.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rates {
    private String no;
    private String effectiveDate;
    private Double mid;

    @Override
    public String toString() {
        return "Rates{" +
                "mid=" + mid +
                '}';
    }
}
