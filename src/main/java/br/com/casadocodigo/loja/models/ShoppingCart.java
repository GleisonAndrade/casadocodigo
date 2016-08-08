package br.com.casadocodigo.loja.models;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArrayBuilder;

@Named
@SessionScoped
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1339514573168812603L;

	private Map<ShoppingItem, Integer> items = new LinkedHashMap<>();

	public void add(ShoppingItem item) {
		items.put(item, getQuantity(item) + 1);
	}

	public void remove(ShoppingItem item) {
		items.remove(item);
	}

	public Collection<ShoppingItem> getList() {
		return new ArrayList<>(items.keySet());
	}

	public Integer getQuantity() {
		return items.values().stream().reduce(0, (next, accumulator) -> next + accumulator);
	}

	public int getQuantity(ShoppingItem item) {
		if (!items.containsKey(item)) {
			items.put(item, 0);
		}

		return items.get(item);
	}

	public BigDecimal getTotal() {
		return items.keySet().stream().map(item -> getTotal(item))
				.reduce(BigDecimal.ZERO,
						(next, acuumulator) -> next.add(acuumulator)
				);
	}

	public BigDecimal getTotal(ShoppingItem item) {
		return item.getTotal(getQuantity(item));
	}
	
	public String toJson() {
		JsonArrayBuilder itens = Json.createArrayBuilder();
		for (ShoppingItem item : getList()) {
			itens.add(Json.createObjectBuilder()
					.add("title", item.getBook().getTitle())
					.add("price", item.getBook().getPrice())
					.add("quantity",getQuantity(item))
					.add("sum", getTotal(item))
					);
		}
		return itens.build().toString();
	}
}