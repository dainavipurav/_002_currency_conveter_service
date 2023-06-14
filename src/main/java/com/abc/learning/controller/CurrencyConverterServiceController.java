package com.abc.learning.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.abc.learning.beans.CurrencyConverterBean;

@RestController
public class CurrencyConverterServiceController {

	@GetMapping("/convert-currency/from/{from}/to/{to}/quantity/{quantity}")
	CurrencyConverterBean convertCurrency(@PathVariable String from, @PathVariable String to,
			@PathVariable BigDecimal quantity) {

		Map<String, String> requestParameters = new HashMap<>();
		requestParameters.put("from", from);
		requestParameters.put("to", to);

		CurrencyConverterBean restTemplateRespose = new RestTemplate()
				.getForEntity("http://localhost:9000/convert-currency/from/{from}/to/{to}", CurrencyConverterBean.class,
						requestParameters)
				.getBody();

		System.out.println("Response : " + restTemplateRespose.getTotalAmount());

		CurrencyConverterBean currencyConverterBean = new CurrencyConverterBean(restTemplateRespose.getId(), from, to,
				restTemplateRespose.getConversionMultiple(), quantity,
				quantity.multiply(restTemplateRespose.getConversionMultiple()));

		return currencyConverterBean;
	}
}
