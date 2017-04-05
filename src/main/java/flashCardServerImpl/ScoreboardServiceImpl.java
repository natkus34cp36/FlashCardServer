package flashCardServerImpl;

import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flashCardServerStartup.FlashCardServerConfig;

import flashCardServerDAO.ScoreboardDAO;
import flashCardServerModel.Scoreboard;

@Path("/scoreboard")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class ScoreboardServiceImpl {
	private static final Logger log = LoggerFactory.getLogger(ScoreboardServiceImpl.class);
	
	private final FlashCardServerConfig config;
	private final ScoreboardDAO scoreboardDAO;

	public ScoreboardServiceImpl(FlashCardServerConfig config, ScoreboardDAO scoreboardDAO){
		this.config = config;
		this.scoreboardDAO = scoreboardDAO;
	}
	
	@GET
	@Path("/{studysetId}/{lang1}/{lang2}")
	public Response getScoreboardByStudySetId(@PathParam("studysetId") int studysetId, @PathParam("lang1") String lang1, @PathParam("lang2") String lang2){
		try {
			return Response.ok(scoreboardDAO.findScoreboardByStudySetId(studysetId, lang1, lang2), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@GET
	@Path("/{android_id}/{studysetId}/{lang1}/{lang2}")
	public Response getScoreboardById(@PathParam("android_id") String android_id, @PathParam("studysetId") int studysetId, @PathParam("lang1") String lang1, @PathParam("lang2") String lang2){
		try {
			return Response.ok(scoreboardDAO.findScoreboardById(android_id, studysetId, lang1, lang2), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteScoreboardById(@PathParam("id") int id) {
		try {
			return Response.ok(scoreboardDAO.deleteScoreboardById(id), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@POST
	public Response addScoreboard(Scoreboard scoreboard){
		if(scoreboard == null) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("scoreboard is mandatory").build());
		} 
		try{
			return Response.ok(scoreboardDAO.insertScoreboard(scoreboard), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
}

