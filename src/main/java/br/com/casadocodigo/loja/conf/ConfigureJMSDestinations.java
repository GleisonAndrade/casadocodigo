package br.com.casadocodigo.loja.conf;

import javax.jms.JMSDestinationDefinition;

@JMSDestinationDefinition(name="java:/jms/topics/checkoutsTopic",
		interfaceName="java.jms.Topic")
public class ConfigureJMSDestinations {
}
