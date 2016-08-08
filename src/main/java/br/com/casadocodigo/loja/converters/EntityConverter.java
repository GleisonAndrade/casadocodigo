package br.com.casadocodigo.loja.converters;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.convert.IntegerConverter;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;

import org.picketbox.util.StringUtil;

@FacesConverter("entityConverter")
public class EntityConverter implements Converter {

	@PersistenceContext
	private EntityManager entityManager;
	private IntegerConverter integerConverter = new IntegerConverter();
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {	
		/* 
		if (StringUtil.isNullOrEmpty(value)) {
			return null;
		}
		UISelectItems uiSelectItems = (UISelectItems) component.getChildren().get(0);
		Collection<?> objects = (Collection<?>) uiSelectItems.getValue();
		return objects.stream().filter(
					(e) -> {return getAsString(context, component, e).equals(value); } 
				).findFirst().get();
		*/
		String[] values = value.split(":");
		try {
			Class<?> className = Class.forName(values[0]);
			Integer id = (Integer) integerConverter.getAsObject(context, component, values[1]);
			return entityManager.find(className, id);
		} catch (ClassNotFoundException e) {
			return null;
		}
		
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		/*
		Field idField = findIdField(value);
		return getIdValue(value, idField);
		*/
		Object idObject = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(value);
		String id = integerConverter.getAsString(context, component, idObject);
		String className = value.getClass().getName();
		return className+":"+id;
		
	}
	
	private Field findIdField(Object value) {
		return Arrays.stream(value.getClass().getDeclaredFields()).filter(
				(field) -> field.isAnnotationPresent(Id.class)).findFirst().get();
	}
	
	private String getIdValue(Object value, Field idField) {
		Field field;
		try {
			field = value.getClass().getDeclaredField(idField.getName());
			field.setAccessible(true);
			return field.get(value).toString();
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
