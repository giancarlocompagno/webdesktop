package it.bradipo.webdesktop.http;

import java.io.IOException;

public abstract class AbstractHttpResponse implements IHttpResponse{

	public AbstractHttpResponse() {
		super();
	}

	@Override
	public void sendOK() throws IOException {
		send( "<esito>ok</esito>", "text/html");
	}

	@Override
	public void sendHTML(String content) throws IOException {
		send( 200, content, new String[]{"text/html"});
	}

	@Override
	public void send(String content, String... contentType) throws IOException {
		send(200, content, contentType);
	}

	@Override
	public void send(int responseCode, String content, String... contentType)
			throws IOException {
				send(responseCode, content.getBytes(),contentType);
			}

	@Override
	public void send(byte content[], String... contentType) throws IOException {
		send(200, content, contentType);
	}

}