package br.com.casadocodigo.loja.client;

import java.util.StringTokenizer;

import javax.batch.api.chunk.ItemProcessor;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.casadocodigo.loja.models.SystemUser;

@Named
public class UserProcessor implements ItemProcessor {

	@Inject
	private JobContext jobContext;
	
	@Override
	public Object processItem(Object item) throws Exception {
		String line = item.toString();
		StringTokenizer token = new StringTokenizer(line, ";");
		SystemUser user = new SystemUser();
		user.setEmail(token.nextToken());
		user.setFirstName(token.nextToken());
		user.setLastName(token.nextToken());
		user.setSocialId(token.nextToken());
		user.setAddress(token.nextToken());
		user.setCity(token.nextToken());
		user.setState(token.nextToken());
		user.setPhone(token.nextToken());
		user.setPostalCode(token.nextToken());
		user.setCountry(token.nextToken());
		return user;
	}
	
}
