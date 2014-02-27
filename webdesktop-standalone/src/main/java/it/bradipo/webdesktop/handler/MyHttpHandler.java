package it.bradipo.webdesktop.handler;


import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

class MyHandler implements HttpHandler
{

	public void handle(HttpExchange t) throws IOException
	{
		try {
			//System.out.println("Received "+t.getRequestMethod()+" request from "+t.getRemoteAddress());
			Headers hdrs = t.getRequestHeaders();
			boolean headersOk = false;
			boolean dataOk = false;
			Set<Entry<String,List<String>>> set = hdrs.entrySet();
			for (Entry<String,List<String>> entry : set)
			{
				String key = entry.getKey();
				List<String> values = entry.getValue();
				StringBuilder b = new StringBuilder();
				b.append("  "+key+":");
				boolean first = true;
				for (String value : values)
				{
					if (first)
					{
						b.append(value);
						first = false;
					} else {
						b.append(","+value);
					}
				}
			} //end mListRequest
			//Set up to read
			int contentLength = -1;
			boolean multipartForms = false;
			String boundary = null;
			List<String> contentHdrs = hdrs.get("Content-type");
			for (String contentHdr : contentHdrs)
			{
				if (contentHdr.contains("multipart/form-data"))
				{
					multipartForms = true;
				} 
				int posB = 0;
				if ((posB =contentHdr.indexOf("boundary=")) >= 0)
				{
					posB += "boundary=".length();
					boundary = contentHdr.substring(posB, contentHdr.length());
				}  
			}
			String tmp = hdrs.getFirst("Content-length");
			try {
				contentLength = Integer.parseInt(tmp);
			} catch (Exception e) 
			{
				; //leaves contentLength at -1
			}
			if (!multipartForms || boundary == null || contentLength < 0)
			{
				System.out.println("Header Error: missing mulitpart forms mime type or boundary value");
			} else {
				headersOk = true;
			}

			if (headersOk)
			{
				//Read start of body
				boolean bodyEof = false;
				InputStream is = t.getRequestBody();
				ByteArrayOutputStream baosBody = new ByteArrayOutputStream();
				int b = 0;
				while (b != -1)
				{
					b = is.read();
					if (b != -1) baosBody.write(b);
				}
				byte[] bodyArray = baosBody.toByteArray();
				//Working offset in body array
				//Start and end of binary inline part
				int binaryStart = 0;
				int binaryEnd = 0;
				String bodyString = baosBody.toString("ISO-8859-1");
				BufferedReader rdr = new BufferedReader(new StringReader(bodyString));
				//InputStreamReader rdr = new InputStreamReader(is));
				String line;
				int lineNo = 0;
				/*
				 * The code below implements the following state machine
		STATE	INPUT			NEXTSTATE 	ENTRYACTION
		0	openboundary		1
		    endboundary			7
		1	ConDisp name XAIT	2			-
		    name KIT			4			save filename
		2	blank				3
		3	yes					0
		4	contentType=zip		5			initialize base64 file
		5	blank				6			
		6	openboundary		1			save base64 file
			endbounadry			7			save base64file
				 *					6			append base64file
		7	exit
				 */			
				int state = 0;
				boolean xait = false;
				boolean dataReady = false;
				while (state != 7)
				{
					if ((line = rdr.readLine()) == null)
					{
						System.err.println("Body Error: unexpected EOF");
						bodyEof = true;
						break;
					}
					lineNo++;
					switch (state) {
					case 0:
						if (line.equals("--"+boundary))
						{
							state = 1;
						} else if (line.equals("--"+boundary+"--"))
						{
							state = 7;
						} else {
							System.err.println("Body Error: Expected boundary, got "+line);
							state = 7;
						}
						break;
					case 1:
						if (!line.startsWith("Content-Disposition: form-data;"))
						{
							System.err.println("Body Error: Expected Content-Disposition, got "+line);
							state = 7;
							break;
						}
						String name = getArg(line, "name");
						if (name == null)
						{
							System.err.println("Body Error: Missing arg 'name' in "+line);
							state = 7;
							break;						   
						}
						//Dispatch on it
						if (name.equals("XAIT"))
						{
							state = 2;  
						} else if (name.equals("KIT")) 
						{
							state = 4;
						} else {
							System.err.println("Body Error: unexpected value for 'name' in "+line);
							state = 7;
						}
						break;
					case 2:
						if (line == null || line.length() == 0 || line.equals(""))
						{
							state = 3;
						} else {				   
							System.err.println("Body Error: unexpected blank line, got:"+line);
							state = 7;
						}
						break;
					case 3:
						if (line.equals("yes"))
						{
							xait = true;
							state = 0;
						} else {				   
							System.err.println("Body Error: expected line 'yes', got:"+line);
							state = 7;
						}					   
						break;
					case 4:
						if (line.startsWith("Content-Type: application/x-zip-compressed"))
						{
							state = 5;
						} else {
							System.err.println("Body Error: expected 'Content-Type' zip, got:"+line);
							state = 7;						   
						}
						break;
					case 5:
						if (line == null || line.length() == 0 || line.equals(""))
						{
							state = 6;
							//Start at end of this line in byte array
							binaryStart = findLine(lineNo, bodyArray);
						} else {				   
							System.err.println("Body Error: unexpected blank line, got:"+line);
							state = 7;
						}
						break;
					case 6:
						//
						if (line.equals("--"+boundary))
						{
							binaryEnd = findLine((lineNo-1), bodyArray);
							dataReady = true;
							state = 1;
						} else if (line.equals("--"+boundary+"--"))
						{
							binaryEnd = findLine((lineNo-1), bodyArray);
							dataReady = true;
							state = 7;
						}
						break;
					case 7:
					default:
						System.err.println("Body Error: unexpected state "+state+" at:"+line);
						state = 7;					   
					}				   
				} //end while state != 7
				//Well behaved server requires entire body be consumed
				if (!bodyEof)
				{
					while ((line = rdr.readLine()) != null)
					{
						;
					}
				}
				rdr.close();


				//Prepare response
				String response = null;
				if (dataOk)
				{
					System.out.println("Success response");
					response = "Uploaded successfully";
					t.sendResponseHeaders(200, response.length());
				} else {
					System.out.println("Failed response");
					response = "Uploaded failed";
					t.sendResponseHeaders(400, response.length());	        	   
				}
				OutputStream os = t.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}
		} catch (Exception e)
		{
			System.err.println("Exception in Handle():"+e);
		}
	} //end Handle
	
	
	 String getArg(String line, String arg)
	   {
		   //Locate the arg
		   int pos1 = line.indexOf(arg+"=\"");
		   if (pos1 < 0)
		   {
			   System.err.println("Body Error: missing '"+arg+"=\"' in "+line);
			   return null;
		   }
		   pos1 += arg.length()+2;
		   //Locate end
		   int pos2 = line.indexOf("\"", (pos1));
		   //extract and return
		   try {
			   String value =line.substring(pos1, pos2); 
			   return value; 
		   } catch (IndexOutOfBoundsException iobe)
		   {
			   return null;
		   }
	   }
	 
	 int findLine(int lineNo, byte[] bodyArray)
		{
			   int tmpLines = 0;
			   int bodyOffset = 0;
			   while (bodyOffset < bodyArray.length)
			   {
				   if (bodyArray[bodyOffset]=='\n' || bodyArray[bodyOffset] == '\r')
				   {
					   //Yes, count the line
					   tmpLines++;
					   //Check for newline+cr									   }
					   if (bodyArray[bodyOffset]=='\r' && bodyArray[(bodyOffset+1)] == '\n')
					   {
						   //Yes, skip extra chr
						   bodyOffset++;
					   }
					   //Done?
					   if (tmpLines == lineNo)
					   {
						   //Account for bypassing increment
						   bodyOffset++;
						   break;
					   }
				   }
				   //Advance one chr
				   bodyOffset++;
			   }
			   //Start of binary inline 
			   return bodyOffset;
		  }
} //end HttpHandler
