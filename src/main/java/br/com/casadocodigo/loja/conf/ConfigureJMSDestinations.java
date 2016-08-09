package br.com.casadocodigo.loja.conf;

import javax.ejb.Stateless;
import javax.jms.JMSDestinationDefinition;

@Stateless
@JMSDestinationDefinition(name="java:/jms/topics/checkoutsTopic",
		interfaceName="javax.jms.Topic")
public class ConfigureJMSDestinations {
}
