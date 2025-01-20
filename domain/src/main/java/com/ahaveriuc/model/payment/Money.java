package com.ahaveriuc.model.payment;

import java.math.BigDecimal;


public record Money(BigDecimal amount, Currency currency) {
}
