package br.com.casadocodigo.loja.client;

import javax.batch.api.Batchlet;
import javax.batch.runtime.BatchStatus;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.casadocodigo.loja.infra.MailSender;

@Named
public class EnviarEmail implements Batchlet{
	@Inject
	private MailSender sender;

	@Override
	public String process() throws Exception {
		sender.send("admin@casadocodigo.com.br",
				"valney.gama@gmail.com",
				"Batch Concluido!", 
				"Seu batch foi finalziado com sucesso! Com 1 chunk e 1 batchlet");
		System.out.println("Email enviado");
		return BatchStatus.COMPLETED.toString();
	}

	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	

}
