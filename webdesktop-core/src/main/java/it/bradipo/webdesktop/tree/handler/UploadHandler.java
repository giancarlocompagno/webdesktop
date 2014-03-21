package it.bradipo.webdesktop.tree.handler;

import static it.bradipo.webdesktop.util.Util.writeResources;
import it.bradipo.webdesktop.http.IHttpHeaderRequest;
import it.bradipo.webdesktop.http.IHttpRequest;
import it.bradipo.webdesktop.http.IHttpResponse;
import it.bradipo.webdesktop.http.multipart.Multipart;
import it.bradipo.webdesktop.http.multipart.Part;
import it.bradipo.webdesktop.http.multipart.ValueParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class UploadHandler extends TreePageHandler{

	public UploadHandler() {
		super();
	}

	@Override
	public void handle(IHttpRequest request,IHttpResponse response) throws IOException {
		elaboraMimeType(request,response);
		super.handle(request,response);
	}

	private void elaboraMimeType(IHttpRequest request,IHttpResponse response) throws IOException,UnsupportedEncodingException, FileNotFoundException {
		IHttpHeaderRequest reqHeaders = request.getHttpHeaderRequest();
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
		InputStream in = request.getInputStream();
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
