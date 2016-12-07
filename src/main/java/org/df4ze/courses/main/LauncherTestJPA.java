package org.df4ze.courses.main;

import java.io.File;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.df4ze.courses.controlers.Refactorer;
import org.df4ze.courses.controlers.Scraper;
import org.df4ze.courses.models.Entities.CourseComplete;
import org.df4ze.courses.resources.Debug;
import org.df4ze.courses.services.DB2File;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LauncherTestJPA {

//	--archiveDirectory /media/df4ze/01CEC90AE25D62F0/PAARLY/crawl/jobs/geny_com/items/20161106213317/warcs/ -sv
	
	
	public static void main(String[] args) {

		Thread hook = new Thread(new Runnable() {

			@Override
			public void run() {

			}
		});

		Runtime.getRuntime().addShutdownHook(hook);

		try {
			CommandLine line = CommandLineMaker.getIndexingCommandLine(args);

			if (line.hasOption('d')) {
				Debug.setEnable(true);
			} else {
				Debug.setEnable(false);
			}


			System.out.println("------ Starting --------");
			// Create Spring application context
			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:*spring.xml");
//			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("file:///home/df4ze/Bureau/spring.xml");
			if (line.hasOption("archiveDirectory")) {

				File arcDir = new File(line.getOptionValue("archiveDirectory"));

				Scraper scraper = ctx.getBean(Scraper.class);

				scraper.setArcDirectory(arcDir);
				if (line.hasOption("sv"))
					scraper.setSkipValidation(true);

				try {
					System.out.println("------ Start Indexing --------");
					scraper.start();
					System.out.println("------ Indexing done --------");

				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("------ Indexing aborded --------");
				}
			}
			
			if( line.hasOption('r') ){
				Refactorer refactorer = ctx.getBean(Refactorer.class);
				
				Long from = null;
				if( line.getOptionValue('r') != null )
					from = Long.parseLong(line.getOptionValue('r'));
				
				System.out.println("------ Start Refactoring --------");
				refactorer.makeCourseComplete(from);
				System.out.println("------ Refactoring done --------");
			}
			
			if( line.hasOption('w') ){
				@SuppressWarnings("unchecked")
				DB2File<CourseComplete> db2file = ctx.getBean(DB2File.class);
				db2file.setClazz(CourseComplete.class);
				
				if( line.getOptionValue('w') != null  )
					db2file.setPath(line.getOptionValue('w'));
				else
					db2file.setPath("/home/Bureau/test.auto.xlsx");
				
				db2file.send();
			}
			
			ctx.close();

		} catch (ParseException e) {
			System.out.println(e.getMessage() + "\n");
			CommandLineMaker.printIndexingHelper();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
