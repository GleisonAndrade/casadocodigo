package br.com.casadocodigo.loja.infra;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class FileSaver {
	@Inject
	private HttpServletRequest request;
	@Inject
	private AmazonS3Client s3;
	private static final String CONTENT_DISPOSITION = "content-disposition";
	private static final String CONTENT_LENGTH = "content-length";
	private static final String FILENAME_KEY = "filename=";
	
	/*public String write(String basePath, Part part) {
		String serverPath = request.getServletContext().getRealPath("\\" + basePath);
		String fileName = extractFilename(part.getHeader(CONTENT_DISPOSITION));
		try {
			part.write(serverPath+"/"+fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return basePath+"\\"+fileName;
	}*/

	public String write(String basePath, Part part) {
		String header = part.getHeader(CONTENT_DISPOSITION);
		//String length = part.getHeader(CONTENT_LENGTH);
		String filename = extractFilename(header);
		try {
			//request.getRequestClientOptions().setReadLimit(Integer.parseInt(length));
			//part.getInputStream().close();
			PutObjectRequest putObjectRequest = new PutObjectRequest("casadocodigo", filename, part.getInputStream(), new ObjectMetadata());
			//putObjectRequest.getRequestClientOptions().setReadLimit(8000);
			s3.putObject(putObjectRequest);
			//s3.putObject("casadocodigo",filename, part.getInputStream(), new ObjectMetadata());
			return "http://localhost:9444/s3/casadocodigo/"+filename+"?noAuth=true";
		}catch (AmazonClientException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	protected String extractFilename(String value) {
		int start = value.indexOf(FILENAME_KEY);
		String filename = value.substring(start+FILENAME_KEY.length());
		return filename.replaceAll("\"", "");
	}
}
