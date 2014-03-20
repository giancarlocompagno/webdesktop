package it.bradipo.webdesktop;

import it.bradipo.webdesktop.handler.HttpHandler;
import it.bradipo.webdesktop.handler.ProxyHandler;
import it.bradipo.webdesktop.handler.util.HandlerManager;
import it.bradipo.webdesktop.handler.util.Util;

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
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

public class Server {
	
	public HttpServer server;
	
	boolean enableHttps;
	boolean enableAutentication;


	public Server(int serverPort,boolean enableAutentication,boolean enableHttps) throws Exception {
		this.enableHttps=enableHttps;
		this.enableAutentication=enableAutentication;
		this.server = (enableHttps)?HttpServerProvider.provider().createHttpsServer(new InetSocketAddress(serverPort), 0):HttpServerProvider.provider().createHttpServer(new InetSocketAddress(serverPort), 0);
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
		if(enableAutentication){
			ctx.setAuthenticator (getAuthenticator("webdesktop@bradipo.it"));
		}
        for(Entry<String, HttpHandler> entry : HandlerManager.getHttpHandlers()){
			ctx = this.server.createContext(entry.getKey(), new ProxyHandler(entry.getValue()));
			if(enableAutentication){
				ctx.setAuthenticator (getAuthenticator("webdesktop@bradipo.it"));
			}
		}
		
		
	}
	
	public void start(){
		server.start();
	}
	
	public void stop(){
		server.stop(0);
	}
	
	
	
	public Authenticator getAuthenticator(String realm){
		return new BasicAuthenticator (realm) {
            public boolean checkCredentials (String username, String pw) {
                return "webdesktop".equals(username) && "webdesktop".equals(pw);
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
