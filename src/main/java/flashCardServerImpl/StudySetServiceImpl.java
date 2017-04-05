package flashCardServerImpl;


import java.util.ArrayList;
import java.util.HashMap;
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

import org.hibernate.validator.internal.util.privilegedactions.GetResolvedMemberMethods;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import flashCardServerDAO.CardDAO;
import flashCardServerDAO.StudySetDAO;
import flashCardServerModel.Card;
import flashCardServerModel.Content;
import flashCardServerModel.GetAllStudySetResponse;
import flashCardServerModel.GetCardResponse;
import flashCardServerModel.GetStudySetMultiSetResponse;
import flashCardServerModel.GetStudySetResponse;
import flashCardServerModel.StudySet;
import flashCardServerStartup.FlashCardServerConfig;
import flashCardServerUtils.Converter;


@Path("/studyset")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class StudySetServiceImpl {
	private static final Logger log = LoggerFactory.getLogger(StudySetServiceImpl.class);

	private final FlashCardServerConfig config;
	private final StudySetDAO studyDAO;
	private final CardDAO cardDAO;

	public StudySetServiceImpl(FlashCardServerConfig config, StudySetDAO studyDAO, CardDAO cardDAO){
		this.config = config;
		this.studyDAO = studyDAO;
		this.cardDAO = cardDAO;
	}
	
	@GET
	public Response getAllStudySet(){
		List<GetAllStudySetResponse> getAllStudySetResponse = new ArrayList<>();
		List<StudySet> studySets = studyDAO.getAll();
		for(StudySet studySet: studySets){
			GetAllStudySetResponse getStudySetResponse = new GetAllStudySetResponse();
			getStudySetResponse.setId(studySet.getId());
			getStudySetResponse.setName(studySet.getName());
			getStudySetResponse.setDescription(studySet.getDescription());
			getStudySetResponse.setCreated(studySet.getCreated());
			getStudySetResponse.setUpdated(studySet.getUpdated());
			getStudySetResponse.setSupportedLanguages(studySet.getSupportedLanguages());
			List<Card> cards = cardDAO.findCardByStudySetId(studySet.getId());
			getStudySetResponse.setCardResponse(cards);
			
			getAllStudySetResponse.add(getStudySetResponse);
		}
		
//		List<GetStudySetResponse> getStudySetResponses = new ArrayList<GetStudySetResponse>();
//		List<StudySet> studySets = studyDAO.getAll();
//		for(StudySet studySet: studySets){
//			GetStudySetResponse getStudySetResponse = new GetStudySetResponse();
//			getStudySetResponse.setId(studySet.getId());
//			getStudySetResponse.setName(studySet.getName());
//			getStudySetResponse.setDescription(studySet.getDescription());
//			getStudySetResponse.setCreated(studySet.getCreated());
//			getStudySetResponse.setUpdated(studySet.getUpdated());
//			getStudySetResponse.setSupportedLanguages(studySet.getSupportedLanguages());
//			getStudySetResponse.setCardResponse(Converter.getCardResponses(cardDAO.findCardByStudySetId(studySet.getId())));
//			
//			getStudySetResponses.add(getStudySetResponse);
//		}
//		
		try {
			return Response.ok(getAllStudySetResponse, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@GET
	@Path("/{id}")
	public Response getStudySet(@PathParam("id") int id) {
		StudySet studySet = studyDAO.findStudySetById(id);
		
		GetStudySetResponse studySetResponse = new GetStudySetResponse();
		studySetResponse.setId(studySet.getId());
		studySetResponse.setName(studySet.getName());
		studySetResponse.setDescription(studySet.getDescription());
		studySetResponse.setCreated(studySet.getCreated());
		studySetResponse.setUpdated(studySet.getUpdated());
		studySetResponse.setSupportedLanguages(studySet.getSupportedLanguages());
		
		List<GetCardResponse> cardResponse = Converter.getCardResponses(cardDAO.findCardByStudySetId(id));
		studySetResponse.setCardResponse(cardResponse);
		
		try {
			return Response.ok(studySetResponse, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@POST
	public Response addStudySet(@Valid StudySet studySet) {
		if(studySet == null) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("study set is mandatory").build());
		} 
		try{
			return Response.ok(studyDAO.insertStudySet(studySet), MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
	}
	
	@POST
	@Path("/cards")
	public Response addStudySetWithCards(@Valid GetStudySetResponse getStudySetResponse){
		
		if(getStudySetResponse == null) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity("study set is mandatory").build());
		} 
		try{
			StudySet studySet = new StudySet();
			studySet.setId(getStudySetResponse.getId());
			studySet.setName(getStudySetResponse.getName());
			studySet.setDescription(getStudySetResponse.getDescription());
			studySet.setCreated(getStudySetResponse.getCreated());
			studySet.setUpdated(getStudySetResponse.getUpdated());
			studySet.setSupportedLanguages(getStudySetResponse.getSupportedLanguages());
			studyDAO.insertStudySet(studySet);
			
			List<GetCardResponse> cards = getStudySetResponse.getCardResponse();
			int count = cardDAO.getLastCardId().getCardId();
			count++;
			for(GetCardResponse card: cards){
				List<Content> contents = card.getContent();
				for(Content content: contents){
					Card new_card = new Card();
					new_card.setStudySetId(getStudySetResponse.getId());
					new_card.setCardId(count);
					new_card.setLanguage(content.getLanguage());
					new_card.setWord(content.getWord());
					cardDAO.insertCard(new_card);
				}
				count++;
			}
			
			return Response.ok(getStudySetResponse, MediaType.APPLICATION_JSON).build();
		} catch (Exception e) {
			log.error(e.getMessage());
			return Response.serverError().entity(e.getMessage()).build();
		}
		
	}
	
	@GET
	@Path("/multiset/{keys}")
	public Response getStudySets(@PathParam("keys") String keys) {
		
		List<GetCardResponse> getCardResponseList = new ArrayList<GetCardResponse>();
		String[] temp = keys.split(",");
		
		for(int i = 0 ; i < temp.length ; i++){
			try{
				int id = Integer.parseInt(temp[i]);
				List<Card> cardSet = cardDAO.findCardByStudySetId(id);
				
				HashMap<Integer, List<Content>> cardMap = new HashMap<Integer, List<Content>>();
				for(Card card : cardSet) {
					Content content = new Content();
					content.setLanguage(card.getLanguage());
					content.setWord(card.getWord());
					if(cardMap.containsKey(card.getCardId())){
						cardMap.get(card.getCardId()).add(content);
					} else {
						List<Content> contentList = new ArrayList<Content>();
						contentList.add(content);
						cardMap.put(card.getCardId(), contentList);
					}
				}
				
				for(Integer key : cardMap.keySet()){
					GetCardResponse getCardResponse = new GetCardResponse();
					getCardResponse.setCardId(key);
					getCardResponse.setContent(cardMap.get(key));
					getCardResponseList.add(getCardResponse);
				}
				
			} catch (Exception e){
				throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
						.entity("study set is mandatory").build());
			}
		}
		
		return Response.ok(new GetStudySetMultiSetResponse(keys, getCardResponseList), MediaType.APPLICATION_JSON).build();
//		return Response.ok(keys, MediaType.APPLICATION_JSON).build();
	}
	
//	@DELETE
//	@Path("/{id}")
//	public Response deleteMenuItem(@PathParam("id") int id) {
//		try {
//			return Response.ok(recommendationsDAO.deleteMenuById(id), MediaType.APPLICATION_JSON).build();
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return Response.serverError().entity(e.getMessage()).build();
//		}
//	}

	
	
}
