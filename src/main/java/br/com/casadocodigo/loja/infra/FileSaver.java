package br.com.casadocodigo.loja.infra;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

public abstract class FileSaver {
	@Inject
	private HttpServletRequest request;
	private static final String CONTENT_DISPOSITION = "content-disposition";
	private static final String FILENAME_KEY = "filename=";
	
	@Inject
	private AmazonS3Client s3;
	
	/*public String write(String basePath, Part part) {
		String serverPath = request.getServletContext().getRealPath("/" + basePath);
		String fileName = extractFilename(part.getHeader(CONTENT_DISPOSITION));
		try {
			part.write(serverPath+"/"+fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return basePath+"/"+fileName;
	}*/

	public String write(String basePath, Part part) {
		String filename = extractFilename(part.getHeader(CONTENT_DISPOSITION));
		try {
			s3.putObject("casadocodigo",filename, part.getInputStream(), new ObjectMetadata());
			return "http://localhost:9444/s3/casadocodigo/"+filename+"?noAuth=true";
		}catch (AmazonClientException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String extractFilename(String value) {
		int start = value.indexOf(FILENAME_KEY);
		String filename = value.substring(start+FILENAME_KEY.length());
		return filename;
	}
}
