package br.com.casadocodigo.loja.client;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.batch.api.chunk.ItemWriter;
import javax.batch.runtime.context.JobContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import br.com.casadocodigo.loja.daos.SystemUserDAO;
import br.com.casadocodigo.loja.models.SystemUser;
import br.com.casadocodigo.loja.models.validation.groups.BuyerGroup;

@Named
public class UserWriter implements ItemWriter {
	@Inject
	private SystemUserDAO systemUserDAO;
	@Inject
	private JobContext jobContext;
	@Inject
	private Validator validator;

	@Override
	public Serializable checkpointInfo() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void open(Serializable arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void writeItems(List<Object> items) throws Exception {
		for (Object item : items) {
			SystemUser user = (SystemUser) item;
			Set<ConstraintViolation<SystemUser>> errors = validator.validate(user,BuyerGroup.class);
			if (errors.isEmpty()) {
				systemUserDAO.save(user);
				System.out.println(user.getFirstName()+"("+user.getEmail()+") salvo com sucesso.");
			} else {
				for (ConstraintViolation<SystemUser> error : errors) {
					System.out.println(error.getPropertyPath()+": "+error.getMessage());
				}
			}
			System.out.println();
		}
	}
	
}
