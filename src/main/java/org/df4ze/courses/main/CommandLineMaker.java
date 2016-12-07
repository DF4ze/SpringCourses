package org.df4ze.courses.main;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class CommandLineMaker {
	
	private static Options indexifyOptions;
	
	CommandLineMaker() {
	}

	@SuppressWarnings("static-access")
	public static CommandLine getIndexingCommandLine(String[] arguments) throws ParseException{
		indexifyOptions = new Options();
		
		indexifyOptions.addOption( OptionBuilder
				.withLongOpt( "archiveDirectory" )
                .withDescription( "Folder containing '.warc' files, which means that Indexifying will start" )
                .hasArg()
                .withArgName("folder")
                .create() );
		indexifyOptions.addOption("sv", "skipValidation", false, "Skip calculation and validation of the archive size (in Indexing mode)");
		indexifyOptions.addOption("r", "refactor", false, "Launch refactoring (after Indexing if it's asked)");
		indexifyOptions.addOption("f", "from", true, "Tel the courseID the refactoring will start");
		indexifyOptions.addOption("w", "write", true, "Set a path to write refactored datas");
		indexifyOptions.addOption("d", "debugMode", false, "Activate debug verbose");
				
		CommandLineParser parser = new BasicParser();
		
		CommandLine line = parser.parse(indexifyOptions, arguments);

		return line;
	}
	
	public static void printIndexingHelper(){
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp( "Indexifier", indexifyOptions );
	}
}
