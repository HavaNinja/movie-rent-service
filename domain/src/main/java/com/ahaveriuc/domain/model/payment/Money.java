package com.ahaveriuc.domain.model.payment;

import java.math.BigDecimal;


public record Money(BigDecimal amount, Currency currency) {
}
