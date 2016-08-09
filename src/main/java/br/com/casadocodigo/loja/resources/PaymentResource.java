package br.com.casadocodigo.loja.resources;

import java.math.BigDecimal;
import java.net.URI;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import br.com.casadocodigo.loja.daos.CheckoutDAO;
import br.com.casadocodigo.loja.infra.MailSender;
import br.com.casadocodigo.loja.models.Checkout;
import br.com.casadocodigo.loja.services.PaymentGateway;

@Path("payment")
public class PaymentResource {
	
	@Inject
	private CheckoutDAO checkoutDAO;
	@Inject
	private PaymentGateway paymentGateway;
	@Inject 
	private ServletContext servletContext;
	@Inject 
	private MailSender mailSender;
	@Resource(name="java:comp/DefaultManagedExecutorService")
	private ManagedExecutorService managedExecutorService;
	
	@POST
	public void pay(@Suspended final AsyncResponse ar, @QueryParam("uuid") String uuid) {
		String contextPath = servletContext.getContextPath();
		Checkout checkout = checkoutDAO.findByUuid(uuid);
		
		managedExecutorService.submit(() -> {
			BigDecimal total = checkout.getTotal();
			
			try {
				paymentGateway.pay(total);
				mailSender.send(
						"sac@casadocodigo.com.br", 
						checkout.getBuyer().getEmail(), 
						"Nova compra efetuada", 
						"Compra efetuada com sucesso! Guarde o ID de compra: "+uuid);
				
				URI redirectURI = UriBuilder.fromUri(contextPath+"/index.xhtml")
						.queryParam("msg","Compra realizada com sucesso").build();
				
				Response response = Response.seeOther(redirectURI).build();
				
				ar.resume(response);
			} catch (Exception e) {
				ar.resume(new WebApplicationException(e));
			}
		});
	}
}
