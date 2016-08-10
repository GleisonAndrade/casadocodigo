package br.com.casadocodigo.loja.managedbeans.admin;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@SessionScoped
public class I18nBean implements Serializable {
	private Locale locale;
	
	@Inject
	private FacesContext context;
	
	@PostConstruct
	private void loadDefaultLocale() {
		if (locale==null) {
			this.locale = context.getApplication().getDefaultLocale();
		}
	}
	
	public String changeLocale(String language) {
		this.locale = new Locale(language);
		System.out.println("changing language to: "+language);
		return "/index.xhtml?faces-redirect=true";
	}
	public Locale getLocale() {
		return locale;
	}
}
