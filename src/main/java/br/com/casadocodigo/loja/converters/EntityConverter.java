package br.com.casadocodigo.loja.converters;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.Id;

import org.picketbox.util.StringUtil;

@FacesConverter("entityConverter")
public class EntityConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (StringUtil.isNullOrEmpty(value)) {
			return null;
		}
		UISelectItems uiSelectItems = (UISelectItems) component.getChildren().get(0);
		Collection<?> objects = (Collection<?>) uiSelectItems.getValue();
		return objects.stream().filter(
					(e) -> {return getAsString(context, component, e).equals(value); } 
				).findFirst().get();
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Field idField = findIdField(value);
		return getIdValue(value, idField);
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
