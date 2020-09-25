package rest;

import DTO.PersonDTO;
import DTO.PersonsDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Person;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getPersonFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @POST
    @Path("addperson")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(String person) {

        PersonDTO p1 = GSON.fromJson(person, PersonDTO.class);

        p1 = FACADE.addPerson(p1.getfName(), p1.getlName(), p1.getPhone());

        return Response.ok(p1).build();
    }

    @PUT
    @Path("editperson/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editPerson(@PathParam("id") long id, String person) throws PersonNotFoundException {

        PersonDTO pDTO = GSON.fromJson(person, PersonDTO.class);
        pDTO.setId(id);
        PersonDTO pEdit = FACADE.editPerson(pDTO);
        return Response.ok(pEdit).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("deleteperson/{id}")
    public String deletePerson(@PathParam("id") long id) throws PersonNotFoundException {

        PersonDTO person = FACADE.deletePerson(id);
        return "{\"status\": Person: " + person.getfName() + ", " + "removed\"}";
    }

    @Path("id/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPerson(@PathParam("id") long id) throws PersonNotFoundException {
        PersonDTO person = FACADE.getPerson(id);

        return GSON.toJson(person);

    }

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllPersons() {

        PersonsDTO persons = FACADE.getAllPersons();

        return GSON.toJson(persons);
    }

}
