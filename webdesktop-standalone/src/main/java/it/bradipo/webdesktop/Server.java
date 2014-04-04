package it.bradipo.webdesktop;

import it.bradipo.webdesktop.datagram.StorageVideoOut;
import it.bradipo.webdesktop.datagram.UDPServer;
import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.handler.ProxyHandler;
import it.bradipo.webdesktop.util.HandlerManager;
import it.bradipo.webdesktop.util.Util;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.Map.Entry;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.BasicAuthenticator;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class Server {
	
	public HttpServer server;
	public UDPServer udpServer;
	
	boolean enableHttps;
	boolean enableAutentication;
	
	private int portDatagram = -1;

	public Server(int serverPort,boolean enableAutentication,boolean enableHttps) throws Exception {
		this(serverPort, enableAutentication, enableHttps, -1);
	}

	public Server(int serverPort,boolean enableAutentication,boolean enableHttps,int portDatagram) throws Exception {
		this.enableHttps=enableHttps;
		this.enableAutentication=enableAutentication;
		this.portDatagram=portDatagram;
		this.server = (enableHttps)?HttpServerProvider.provider().createHttpsServer(new InetSocketAddress(serverPort), 0):HttpServerProvider.provider().createHttpServer(new InetSocketAddress(serverPort), 0);
		if(portDatagram>0){
			udpServer = new UDPServer(portDatagram);
		}
		init();
	}
	
	
	private void init() throws Exception{
		if(enableHttps){
			
			SSLContext sslContext = initSSLContext();
			
			((HttpsServer)this.server).setHttpsConfigurator(new HttpsConfigurator( sslContext )
            {
                public void configure ( HttpsParameters params )
                {
                    try
                    {
                        // initialise the SSL context
                        SSLContext c = SSLContext.getDefault ();
                        SSLEngine engine = c.createSSLEngine ();
                        params.setNeedClientAuth ( false );
                        params.setCipherSuites ( engine.getEnabledCipherSuites () );
                        params.setProtocols ( engine.getEnabledProtocols () );

                        // get the default parameters
                        SSLParameters defaultSSLParameters = c.getDefaultSSLParameters ();
                        params.setSSLParameters ( defaultSSLParameters );
                    }
                    catch ( Exception ex )
                    {
                    	ex.printStackTrace();
                    }
                }
            }); 
		}
		
		
		
        
		HttpContext ctx = this.server.createContext("/",new ProxyHandler(HandlerManager.getDefault()));
		setAutenticator(ctx);
		
		if(portDatagram!=-1){
			ctx = this.server.createContext("/screen",new ProxyHandler(HandlerManager.getDatagram()));
		}else{
			ctx = this.server.createContext("/screen",new ProxyHandler(HandlerManager.getStream()));
		}
		setAutenticator(ctx);
		
		setAutenticator(ctx);
        for(Entry<String, HttpHandler> entry : HandlerManager.getHttpHandlers()){
			ctx = this.server.createContext(entry.getKey(), new ProxyHandler(entry.getValue()));
			setAutenticator(ctx);
		}
		
		
	}


	private void setAutenticator(HttpContext ctx) {
		if(enableAutentication){
			ctx.setAuthenticator (getAuthenticator());
		}
	}
	
	private void setFilter(HttpContext ctx) {
		ctx.getFilters().add(new Filter(){

			@Override
			public String description() {
				return "sicurezza";
			}

			@Override
			public void doFilter(HttpExchange exchange, Chain chain)throws IOException {
				String uuid = exchange.getRequestHeaders().getFirst("uiid");
				if(uuid!=null){
					chain.doFilter(exchange);
				}else{
					
				}
				exchange.getResponseHeaders().set("uuid", uuid);
			}
			
		});
	}
	
	public void start(){
		server.start();
		udpServer.start();
	}
	
	public void stop(){
		server.stop(0);
		udpServer.shutdown();
	}
	
	
	
	public Authenticator getAuthenticator(){
		String realm = "webdesktop@bradipo.it";
		if(portDatagram!=-1){
			realm= "Inserisci uuid nel campo username";
		}
		return new BasicAuthenticator (realm) {
            public boolean checkCredentials (String username, String pw) {
            	if(portDatagram!=-1){
            		return StorageVideoOut.exist(username); 
            	}else{
            		return "webdesktop".equals(username) && "webdesktop".equals(pw);
            	}
            }
        };
	}
	//e' stato generato il keystore con il comando 
	//keytool -genkey -alias alias -keypass webdesktop -keystore bradipo.keystore -storepass webdesktop
	//con i seguenti dati
	//Il dato CN=Giancarlo Compagno, OU=IT, O=www.bradipo.it, L=Palermo, ST=PA, C=IT Þ corretto?
	public SSLContext initSSLContext() throws Exception{
		SSLContext sslContext = SSLContext.getInstance ( "TLS" );
        // initialise the keystore
        char[] password = "webdesktop".toCharArray ();
        KeyStore ks = KeyStore.getInstance ( "JKS" );
        InputStream fis = Util.loadInputStream( "/bradipo.keystore" );
        ks.load ( fis, password );
        // setup the key manager factory
        KeyManagerFactory kmf = KeyManagerFactory.getInstance ( "SunX509" );
        kmf.init ( ks, password );
        // setup the trust manager factory
        TrustManagerFactory tmf = TrustManagerFactory.getInstance ( "SunX509" );
        tmf.init ( ks );
        // setup the HTTPS context and parameters
        sslContext.init ( kmf.getKeyManagers (), tmf.getTrustManagers (), null );
        
        return sslContext;
        
	}
	
	
	public static void main(String[] args) throws Exception {
		
		Server server = new Server(6060,true,true);
		server.start();
		System.out.println("in attesa");
	}
	
	
}
