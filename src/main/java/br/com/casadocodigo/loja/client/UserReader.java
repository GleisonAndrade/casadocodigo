package br.com.casadocodigo.loja.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import javax.batch.api.chunk.ItemReader;
import javax.inject.Named;

@Named
public class UserReader implements ItemReader {
	private BufferedReader reader;

	@Override
	public Serializable checkpointInfo() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws Exception {
		reader.close();
	}

	@Override
	public void open(Serializable checkpoint) throws Exception {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("/META-INF/import-user.csv");
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		this.reader = new BufferedReader(inputStreamReader);

	}

	@Override
	public Object readItem() throws Exception {
		return reader.readLine();
	}

}
