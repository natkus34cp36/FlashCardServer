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

import flashCardServerDAO.CardDAO;
import flashCardServerModel.Card;
import flashCardServerStartup.FlashCardServerConfig;

@Path("/card")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CardServiceImpl {
	private static final Logger log = LoggerFactory.getLogger(CardServiceImpl.class);

	private final FlashCardServerConfig config;
	private final CardDAO cardDAO;

	public CardServiceImpl(FlashCardServerConfig config, CardDAO cardDAO){
		this.config = config;
		this.cardDAO = cardDAO;
	}

	@GET
	public Response getAllCards(){
		try {
			return Response.ok(cardDAO.getAll(), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/{id}")
	public Response getCardById(@PathParam("id") int id) {
		try {
			return Response.ok(cardDAO.findCardById(id), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("/{id}")
	public Response deleteCardById(@PathParam("id") int id) {
		try {
			return Response.ok(cardDAO.deleteCardById(id), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@POST
	public Response addCard(@Valid Card card) {
		if(card == null) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("card is mandatory").build());
		} 
		try{
			return Response.ok(cardDAO.insertCard(card), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

}
