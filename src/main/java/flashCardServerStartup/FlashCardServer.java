package flashCardServerStartup;

import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;

import java.io.FileNotFoundException;
import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;

import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flashCardServerDAO.CardDAO;
import flashCardServerDAO.ScoreboardDAO;
import flashCardServerDAO.StudySetDAO;
import flashCardServerImpl.CardServiceImpl;
import flashCardServerImpl.ScoreboardServiceImpl;
import flashCardServerImpl.StudySetServiceImpl;
import flashCardServerUtils.CLIParser;

public class FlashCardServer extends Application<FlashCardServerConfig>{

	private static final Logger log = LoggerFactory.getLogger(FlashCardServer.class);

	private static final String SERVER_START_COMMAND = "server"; 
	private static final String DATABASE_TYPE = "postgresql";
	
	private static final String CORS_SUPPORTED_HEADERS = "Content-Type,Authorization,X-Requested-With,Content-Length,Accept,Origin,Location,Accept-Content-Encoding,user_id,token_id";
	
	public static void main(String[] args) throws Exception {
		CLIParser parser = CLIParser.getInstance();
		String configFile = parser.parse(args);
		log.info("Starting flashCardServer with configuration file " + configFile + " in env. mode " + CLIParser.getEnvironment());
		try {
			new FlashCardServer().run(new String[] { SERVER_START_COMMAND, parser.parse(args) });
		} catch (FileNotFoundException e) {
			log.error("Failed to find the configuration file " + configFile + " for env. mode " + CLIParser.getEnvironment());
			System.exit(0);
		} catch (Exception e) {
			log.error("Exception occored while starting server ", e);
		}
	}

	
	@Override
	public void run(FlashCardServerConfig configuration, Environment env)
			throws Exception {
		
		final DBIFactory factory = new DBIFactory();
		final DBI jdbi = factory.build(env, configuration.getDataSourceFactory(), DATABASE_TYPE);
		final StudySetDAO studySetDAO = jdbi.onDemand(StudySetDAO.class);
		final CardDAO cardDAO = jdbi.onDemand(CardDAO.class);
		final ScoreboardDAO scoreboardDAO = jdbi.onDemand(ScoreboardDAO.class);
		
		final FilterRegistration.Dynamic cors = env.servlets().addFilter("CORS", CrossOriginFilter.class);

		// Configure CORS parameters
		cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
		cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,user_id,token_id");
		cors.setInitParameter(CrossOriginFilter.EXPOSED_HEADERS_PARAM, CORS_SUPPORTED_HEADERS);
		cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
		cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

		// Add URL mapping
		cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");
		
		env.jersey().register(new CardServiceImpl(configuration, cardDAO));	
		env.jersey().register(new StudySetServiceImpl(configuration, studySetDAO, cardDAO));
		env.jersey().register(new ScoreboardServiceImpl(configuration, scoreboardDAO));
	}

}