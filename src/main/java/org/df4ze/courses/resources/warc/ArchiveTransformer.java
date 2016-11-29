package org.df4ze.courses.resources.warc;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.http.impl.io.AbstractSessionInputBuffer;
import org.apache.http.impl.io.ChunkedInputStream;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.params.BasicHttpParams;

public class ArchiveTransformer {


	private static final Pattern PAT_CHARSET = Pattern.compile("charset=(.*)",
			Pattern.CASE_INSENSITIVE);

	private static final Pattern PAT_PAGECODE = Pattern.compile(
			"(.*?)([0-9]{3})", Pattern.CASE_INSENSITIVE);


	public String ArchiveHeader="";
	public String ArchiveBody="";
	public Boolean ContentTypeHTML=false;
	public int BodyOffset;
	public String ContentType = null;
	public String ContentEncoding = null;
	public String Charset = null;
	public static String siteCharset = null;
	
	public ArchiveTransformer(ByteArrayOutputStream baos, Hashtable<String, Object> params) throws UnsupportedEncodingException 
	{
		byte[] bytes = baos.toByteArray();
		
		for(int pos=0; pos < bytes.length - 4; pos++)
		{
			if (bytes[pos] == 0x0D && bytes[pos+1] == 0x0A && bytes[pos+2] == 0x0D && bytes[pos+3] == 0x0A)
			{
				BodyOffset = pos + 4;
				break;
			}
		}
		
		this.ArchiveHeader = new String(bytes, 0, BodyOffset, "UTF-8");
		
		DetermineContentType();
		DetermineContentEncoding();
		
		if (params != null && params.get("charset") != null)
			this.Charset = params.get("charset").toString();
		
		/*
		 * Modif Clem
		 */
		
		if (isChunkedTransfert(this.ArchiveHeader, baos, BodyOffset))
		{
			ByteArrayOutputStream baos0 = new ByteArrayOutputStream();
			
			final byte[] bytesFinal = bytes;
					
			SessionInputBuffer sessionInputBuffer = new AbstractSessionInputBuffer() {
                {
                    init(new ByteArrayInputStream(bytesFinal, BodyOffset, bytesFinal.length - BodyOffset), bytesFinal.length - BodyOffset, new BasicHttpParams());
                }
              
                
                public boolean isDataAvailable(int timeout) throws IOException 
                {
                    throw new RuntimeException("have to override but probably not even called");
                }
            };
                
			ChunkedInputStream chunkedInputStream = new ChunkedInputStream(sessionInputBuffer);
			
			byte[] buffer = new byte[65536];
			
			int length0 = 0;
			
			try
			{
				while((length0 = chunkedInputStream.read(buffer)) != -1)
				{
					baos0.write(buffer, 0, length0);
				}
			}
			catch(Exception e)
			{
			}
			
			try {
				chunkedInputStream.close();
			} catch (IOException e1) {}
			
			bytes = baos0.toByteArray();
			BodyOffset = 0;
			
			try {
				baos0.close();
			} catch (IOException e) {}
		}
		/*
		 * Fin Modif Clem
		 */
		
		if (this.ContentEncoding != null)
		{
			if (this.ContentEncoding.compareToIgnoreCase("gzip") == 0)
			{
				ByteArrayOutputStream baos0 = null;
				try 
				{
					for(int pos=BodyOffset; pos < bytes.length - 3; pos++)
					{
						if (bytes[pos] == (byte)0x1F && bytes[pos+1] == (byte)0x8B && bytes[pos+2] == (byte)0x08)
						{
							BodyOffset = pos;
							break;
						}
					}
					
					baos0 = new ByteArrayOutputStream();
					
					GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bytes, BodyOffset, bytes.length - BodyOffset));
					
					byte[] buffer = new byte[65536];
					
					int length = 0;
					
					try
					{
						while((length = gzipInputStream.read(buffer)) != -1)
						{
							baos0.write(buffer, 0, length);
						}
					}
					catch(Exception e0)
					{
					}
					
					bytes = baos0.toByteArray();
					
					if (Charset == null)
						this.determineCharsetFromBody(baos0.toString());
					
					if (Charset == null)
						Charset = "UTF-8";
						
					this.ArchiveBody = baos0.toString(Charset);
					
					gzipInputStream.close();
					
					
					BodyOffset = 0;
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				finally {
					if( baos0 != null )
						try {
							baos0.close();
						} catch (IOException e) {}
				}
			}
			else if (this.ContentEncoding.compareToIgnoreCase("deflate") == 0)
			{
				OutputStream outputStream = null;
				try
				{
					outputStream = new FileOutputStream ("c:\\www\\t.gz"); 
					outputStream.write(bytes, BodyOffset, bytes.length - BodyOffset);
					outputStream.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				finally {
					if(outputStream != null)
						try {
							outputStream.close();
						} catch (IOException e) {}
				}
				
				for(int pos=BodyOffset; pos < bytes.length - 3; pos++)
				{
					if (bytes[pos] == (byte)0x1F && bytes[pos+1] == (byte)0x8B && bytes[pos+2] == (byte)0x08)
					{
						BodyOffset = pos;
						break;
					}
				}

				ByteArrayOutputStream baos0 = null;
				InflaterInputStream gzipInputStream = null;
				try 
				{					
					baos0 = new ByteArrayOutputStream();
										
					gzipInputStream = new InflaterInputStream(new ByteArrayInputStream(bytes, BodyOffset, bytes.length - BodyOffset), new Inflater());
					
					byte[] buffer = new byte[65536];
					
					int length = 0;
					
					while((length = gzipInputStream.read(buffer)) != -1)
					{
						baos0.write(buffer, 0, length);
					}
					
					if (Charset == null)
						this.determineCharsetFromBody(baos0.toString());
					
					if (Charset == null)
						Charset = "UTF-8";
					
					this.ArchiveBody = baos0.toString(Charset);
							
					bytes = baos0.toByteArray();
					
					
					
					BodyOffset = 0;
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				finally {
					if(baos0!=null)
						try {
							baos0.close();
						} catch (IOException e) {}
					
					if( gzipInputStream != null )
						try {
							gzipInputStream.close();
						} catch (IOException e) {}
					
				}
			}
		}
		
		if (this.ArchiveBody == null || this.ArchiveBody.length() == 0)
		{
			if (Charset != null)
			{
				try
				{
					this.ArchiveBody = new String(bytes, BodyOffset, bytes.length - BodyOffset, Charset);
				}
				catch(Exception e)
				{
					System.out.println(e.getMessage());
				}
			}
			else
			{
				this.ArchiveBody = new String(bytes, BodyOffset, bytes.length - BodyOffset);
				
				this.determineCharsetFromBody(this.ArchiveBody);
				
				if (Charset != null)
					this.ArchiveBody = new String(bytes, BodyOffset, bytes.length - BodyOffset, Charset);
			}
		}
		

		
		if (Charset != null && Charset.compareToIgnoreCase("ISO-8859-1") == 0)
		{
			for(int k = BodyOffset; k < bytes.length; k++)
			{
				byte abyte = bytes[k];
				if (abyte == -128)
				{
					this.ArchiveBody = this.ArchiveBody.substring(0, k - BodyOffset) + "�" + this.ArchiveBody.substring(k - BodyOffset + 1);  
				}
			}
		}

	}


	private boolean isChunkedTransfert(String archiveHeader, ByteArrayOutputStream baos, int bodyOffset)
	{
		
		byte[] bytes = baos.toByteArray();
		
		boolean isChunkedTransfert = true;
		boolean chunkLengthFound = false;
		boolean numberLengthFound = false;
		
		int index = bodyOffset;
		
		while(index < bytes.length - 1 && isChunkedTransfert && !chunkLengthFound)
		{
			byte aByte = bytes[index];
			
			if (!
					( (aByte >= '0' && aByte <= '9') || (aByte >= 'a' && aByte <= 'f') || (aByte >= 'A' && aByte <= 'F') || aByte == '\r' || aByte == '\n' || aByte == 32)
			   )
			{
				isChunkedTransfert = false;
			}
			

			if (
					(aByte >= '0' && aByte <= '9') || (aByte >= 'a' && aByte <= 'f') || (aByte >= 'A' && aByte <= 'F')
			   )
			{
				numberLengthFound = true;
			}
		
			if (aByte == '\r' && bytes[index + 1] == '\n' && numberLengthFound)
				chunkLengthFound = true;
			
			index++;
		}

		return isChunkedTransfert;
	}
	
	private void DetermineContentType()
	{
		String contentTypeLabelSearch = "Content-Type:";
		
		int posStart = -1;
		
		if ((posStart = this.ArchiveHeader.indexOf(contentTypeLabelSearch)) != -1) 
		{
			int posEnd = this.ArchiveHeader.indexOf("\r", posStart);
			
			if (posEnd == -1)
				posEnd = this.ArchiveHeader.indexOf("\n", posStart);
			
			String type = this.ArchiveHeader.substring(posStart + contentTypeLabelSearch.length(), posEnd);
			
			ContentType = type.trim();
		}
		
		if (ContentType != null && ContentType.indexOf("text/html") != -1)
			this.ContentTypeHTML = true;
		
		String charsetSearch = "charset=";
		
		posStart = -1;
		
		Charset = ArchiveTransformer.siteCharset;
		
		if ((posStart = this.ArchiveHeader.toLowerCase().indexOf(charsetSearch)) != -1) 
		{
			int posEnd = this.ArchiveHeader.indexOf("\r", posStart);
			
			if (posEnd == -1)
				posEnd = this.ArchiveHeader.indexOf("\n", posStart);
			
			String charsetValue = this.ArchiveHeader.substring(posStart + charsetSearch.length(), posEnd);
			
			int posChr = charsetValue.indexOf(";");
				
			if (posChr != -1)
				charsetValue = charsetValue.substring(0, posChr);
			
			Charset = charsetValue.trim();
			
			if (Charset.compareTo("utf-8") == 0)
				Charset = "UTF-8";
		}
	}

	private void DetermineContentEncoding()
	{
		String contentEncodingLabelSearch = "Content-Encoding:";
		
		int posStart = -1;
		
		if ((posStart = this.ArchiveHeader.indexOf(contentEncodingLabelSearch)) != -1) 
		{
			int posEnd = this.ArchiveHeader.indexOf("\r", posStart);
			
			if (posEnd == -1)
				posEnd = this.ArchiveHeader.indexOf("\n", posStart);
			
			String encoding = this.ArchiveHeader.substring(posStart + contentEncodingLabelSearch.length(), posEnd);
			
			this.ContentEncoding = encoding.trim();
		}		
	}

	private void determineCharsetFromBody(String body)
	{
		String foundCharset = "";
		
		int pos0 = body.toLowerCase().indexOf("content-type");
		
		if (pos0 != -1)
		{
			int left = body.lastIndexOf("<", pos0);
			int right = body.indexOf(">", pos0);
			
			if (left != -1 && right != -1)
			{
				String meta = body.substring(left, right + 1);
				
				String charsetAttribute = "charset"; 
				pos0 = meta.indexOf(charsetAttribute);
				
				if (pos0 != -1)
				{
					pos0 += charsetAttribute.length();
					
					while(meta.substring(pos0, pos0 + 1).compareTo("=") == 0 || meta.substring(pos0, pos0 + 1).compareTo(" ") == 0)
					{
						pos0++;
					}
					
					while(meta.substring(pos0, pos0 + 1).compareTo("\"") != 0 && meta.substring(pos0, pos0 + 1).compareTo(";") != 0)
					{
						foundCharset += meta.substring(pos0, pos0 + 1);
						pos0++;
					}
				}
			}
/*			else
			{
				int a = 1;
				a++;
			}
*/		}
		else
		{
			String charsetAttribute = "charset"; 
			pos0 = body.indexOf(charsetAttribute);
			
			if (pos0 != -1)
			{
				pos0 += charsetAttribute.length();
			
				while(pos0 < body.length() && body.substring(pos0, pos0 + 1).compareTo("=") == 0 || body.substring(pos0, pos0 + 1).compareTo(" ") == 0 || body.substring(pos0, pos0 + 1).compareTo("\"") == 0 || body.substring(pos0, pos0 + 1).compareTo("'") == 0)
				{
					pos0++;
				}
				
				while(pos0 < body.length() && body.substring(pos0, pos0 + 1).compareTo("\"") != 0 && body.substring(pos0, pos0 + 1).compareTo("'") != 0)
				{
					foundCharset += body.substring(pos0, pos0 + 1);
					pos0++;
				}
			}
		}
		
		
		if (foundCharset != null && foundCharset.length() > 0)
		{
			if (foundCharset.compareToIgnoreCase("windows-1252") == 0)
				foundCharset = "CP1252";
			
			String[] charsets = new String[] {"ISO-8859-1", "ISO-8859-15", "UTF-8", "UTF-16", "US-ASCII", "CP1252"};
			
			boolean charsetExist = false;
			
			foundCharset = foundCharset.toUpperCase();
			
			for(int index = 0; index < charsets.length; index++)
			{
				if (foundCharset.compareTo(charsets[index]) == 0)
				{
					charsetExist = true;
					break;
				}
			}
			
			if (charsetExist)
			{
				ArchiveTransformer.siteCharset = foundCharset;
				Charset = foundCharset;
			}
		}
	}
		
	public Boolean readStringlinebyLine(String strInput) {
		String str;
		boolean header = true;

		BufferedReader reader = new BufferedReader(new StringReader(strInput));

		try {
			while ((str = reader.readLine()) != null) {
				// System.out.println(str);
				if (header == true) {
					this.ArchiveHeader = this.ArchiveHeader + str+"\n";
				}
				if (header == false) {
					this.ArchiveBody = this.ArchiveBody + str;
				}
				if (str.trim().startsWith("Content-Type:")) {
					header = false;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				reader.close();
			} catch (IOException e) {}
		}
		
		
		return true;
	}

	public String getCharset() 
	{
		Matcher m = PAT_CHARSET.matcher(this.ArchiveHeader);
		
		if (m.find()) 
		{
			if (!m.group(1).contentEquals(""))
			{
				return m.group(1);
			}
			else
			{
				return "utf-8";
			}	
		}
		else 
		{
			return "utf-8";
		}
	}

	public boolean isMovedPermanently()
	{
		int pageCode = this.getPageCode();
		
		return pageCode == 301;
	}
	
	public int getPageCode() 
	{
		Matcher m = PAT_PAGECODE.matcher(this.ArchiveHeader);
		
		if (m.find()) 
		{
			return Integer.parseInt(m.group(2));
		} 
		else 
		{
			return 404;
		}
	}
}
