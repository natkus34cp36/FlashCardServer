package flashCardServerUtils;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CLIParser {

	private static final Logger log = LoggerFactory.getLogger(CLIParser.class);

	/**
	 * Construct and provide GNU-compatible Options.
	 */

	private static CLIParser instance = null;
	private static Options options = null; 
	private static CommandLineParser parser = null;

	private final static String MODE = "mode";
	private final static String MODE_SHORT = "m";
	private static EnvironmentMode environment;

	private CLIParser() {
		options = new Options();
		options.addOption(MODE_SHORT, MODE, true, "Runs the service in the specified mode. Supported args - dev, qa, stg, prod, local. If no option is specified tries to load a file conf.yml to start in default mode.");
		parser = new DefaultParser();
	}

	public static CLIParser getInstance() {
		if(instance == null) {
			instance = new CLIParser();
		}
		return instance;
	}

	public static EnvironmentMode getEnvironment() {
		return environment;
	}

	/**
	 * Apply Apache Commons CLI Gnu like Parser to command-line arguments.
	 * 
	 * @param commandLineArguments Command-line arguments to be processed with
	 *    Gnu-style parser.
	 */

	public String parse(final String[] commandLineArguments) {
		CommandLine commandLine = null;
		try {
			commandLine = parser.parse(options, commandLineArguments);
			if (commandLine.hasOption(MODE_SHORT)) {
				EnvironmentMode mode = EnvironmentMode.valueOf(commandLine.getOptionValue(MODE_SHORT).toUpperCase());
				log.debug("Parsed environment mode " + mode.toString() + " as argument.");
				switch(mode) {
				case PROD: 
					environment = EnvironmentMode.PROD;
					return EnvironmentMode.PROD.getConfigFileName();
				case QA:
					environment = EnvironmentMode.QA;
					return EnvironmentMode.QA.getConfigFileName();
				case STG:
					environment = EnvironmentMode.STG;
					return EnvironmentMode.STG.getConfigFileName();
				case DEV:
					environment = EnvironmentMode.DEV;
					return EnvironmentMode.DEV.getConfigFileName();
				case LOCAL:
					environment = EnvironmentMode.LOCAL;
					return EnvironmentMode.LOCAL.getConfigFileName();
				case DEFAULT:
					environment = EnvironmentMode.DEFAULT;
					return EnvironmentMode.DEFAULT.getConfigFileName();
				default:
					environment = EnvironmentMode.DEFAULT;
					return EnvironmentMode.DEFAULT.getConfigFileName();
				}
			} else {
				environment = EnvironmentMode.DEFAULT;
				return EnvironmentMode.DEFAULT.getConfigFileName();
			}
		} 
		catch (MissingArgumentException missingException) {
			printHelp();
			log.error("Missing argument for environment mode " + commandLine.getOptionValue(MODE_SHORT).toUpperCase());
			System.exit(0);
		}
		catch (ParseException parseException) {
			log.error(parseException.getMessage());
			System.exit(0);
		} catch (IllegalArgumentException e) {
			printHelp();
			log.error("Unrecognized environment mode " + commandLine.getOptionValue(MODE_SHORT).toUpperCase());
			System.exit(0);
		}
		return new String();
	}

	private void printHelp() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("java -jar UserService-0.0.x-Snapshot.jar", options);
	}

}
