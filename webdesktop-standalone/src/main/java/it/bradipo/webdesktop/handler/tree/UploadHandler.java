package it.bradipo.webdesktop.handler.tree;

import it.bradipo.webdesktop.handler.AbstractHttpHandler;
import it.bradipo.webdesktop.multipart.Multipart;
import it.bradipo.webdesktop.multipart.Part;
import it.bradipo.webdesktop.multipart.ValueParser;

import java.awt.Robot;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

public class UploadHandler extends TreePageHandler{

	public UploadHandler(String hostName, Robot robot, boolean readOnly) {
		super(hostName, robot, readOnly);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(HttpExchange exchange) throws IOException {
		elaboraMimeType(exchange);
		super.handle(exchange);
	}

	private void elaboraMimeType(HttpExchange exchange) throws IOException,UnsupportedEncodingException, FileNotFoundException {
		Headers reqHeaders = exchange.getRequestHeaders();
		String contentType = reqHeaders.getFirst("Content-Type");
		if (!contentType.startsWith("multipart/")) {
			throw new IOException("Multipart content required");
		}
		Map<String,String> parms = ValueParser.parse(contentType);
		String encoding = parms.get("charset");
		if (encoding == null) {
			encoding = "ISO-8859-1";
		}
		byte boundary[] = parms.get("boundary").getBytes(encoding);
		InputStream in = exchange.getRequestBody();
		try{
			Multipart mp = new Multipart(in, encoding, boundary);
			Part part = mp.nextPart();
			InputStream body = null;
			String directory = null;
			String fileName = null;
			while (part != null) {
				InputStream is = part.getBody();
				try {
					String cd = part.getFirstValue("content-disposition");
					Map<String,String> cdParms = ValueParser.parse(cd);
					String fieldName = cdParms.get("name");
					if (fieldName.equals("directory")) {
						ByteArrayOutputStream ba = new ByteArrayOutputStream();
						writeResources(part.getBody(), ba);
						directory = new String(ba.toByteArray());
					}else if (fieldName.equals("file")) {
						fileName = cdParms.get("filename");
						body = part.getBody();
					}
				} finally {
					is.close();
				}
				part = mp.nextPart();
			}
			writeResources(in, new FileOutputStream(new File(new File(directory),fileName)));
		}finally {
			in.close();
		}
	}
}
