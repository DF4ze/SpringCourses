package org.df4ze.courses.resources;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

	private String path = "current.log";
	private static FileWriter writer = null;
	
	private Logger() throws IOException {
		File log = new File( path );

		if( !log.exists() )
			log.createNewFile();
		
		writer = new FileWriter(path, false);
		
	}

	
	public static void write( String texte ){
		
		SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		texte = formater.format(new Date())+"\t"+texte+"\n";
		
		try{
			if( writer == null )
				new Logger();
		
			writer.write(texte,0,texte.length());
			writer.flush();
		}catch(IOException ex){
			System.err.println("Erreur avec le fichier de log");
		}
	}
}
