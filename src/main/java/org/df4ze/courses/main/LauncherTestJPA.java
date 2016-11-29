package org.df4ze.courses.main;

import java.io.File;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.df4ze.courses.controlers.Refactorer;
import org.df4ze.courses.controlers.Scraper;
import org.df4ze.courses.resources.Debug;
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
			
			
			ctx.close();

		} catch (ParseException e) {
			System.out.println(e.getMessage() + "\n");
			CommandLineMaker.printIndexingHelper();
		}

	}

}
