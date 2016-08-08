package br.com.casadocodigo.loja.services;

import java.math.BigDecimal;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;

import br.com.casadocodigo.loja.models.PaymentData;

public class PaymentGateway {

	private static final String URI_TO_PAY = "http://book-payment.herokuapp.com/payment";
	
	public String pay(BigDecimal total) {
		Client client = ClientBuilder.newClient();
		PaymentData paymentData = new PaymentData(total);
		Entity<PaymentData> json = Entity.json(paymentData);
		return client.target(URI_TO_PAY).request().post(json,String.class);
		
	}
}
