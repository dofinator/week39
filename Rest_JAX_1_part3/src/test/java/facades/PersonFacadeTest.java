package facades;

import org.junit.Rule;
import DTO.PersonDTO;
import DTO.PersonsDTO;
import utils.EMF_Creator;
import entities.Person;
import exceptions.PersonNotFoundException;
import exceptions.PersonNotFoundExceptionMapper;
import java.util.ArrayList;
import java.util.List;
import javassist.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

//Uncomment the line below, to temporarily disable this test
public class PersonFacadeTest {

    private static EntityManagerFactory emf;
    private static PersonFacade facade;
    Person p1;
    Person p2;

    public PersonFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = PersonFacade.getPersonFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
//        Clean up database after test is done or use a persistence unit with drop-and-create to start up clean on every test
    }

    // Setup the DataBase in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the script below to use YOUR OWN entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("DELETE FROM PERSON").executeUpdate();
            em.createNativeQuery("alter table PERSON AUTO_INCREMENT = 1").executeUpdate();
            p1 = new Person("Lars", "Larsen", "44444444");
            p2 = new Person("Jens", "Jensen", "88888888");
            em.persist(p1);
            em.persist(p2);

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
//        Remove any data after each test was run
    }

    // TODO: Delete or change this method 
    @Test
    public void testGetPerson() throws PersonNotFoundException {
        PersonDTO person = facade.getPerson(p1.getP_id());
        String exp = "Lars";
        assertEquals(person.getfName(), exp);
    }

    @Test
    public void testGetAllPersons() {
        PersonsDTO persons = facade.getAllPersons();
        int expSize = 2;

        assertEquals(persons.getAll().size(), expSize);
    }

    @Test
    public void testAddPerson() {
        String fName = "Kulle";
        String lName = "Katsen";
        String phone = "54685975";
        PersonDTO person = facade.addPerson(fName, lName, phone);
        String exp = "Kulle";
        assertEquals(person.getfName(), exp);
    }

    @Test
    public void testDeletePerson() throws PersonNotFoundException {

        PersonDTO person = facade.deletePerson(p1.getP_id());

        assertEquals(person.getfName(), "Lars");

    }

    @Test
    public void editPerson() throws PersonNotFoundException {

        PersonDTO personDTO = new PersonDTO(p1);
        personDTO.setfName("Christian");
        PersonDTO personEdited = facade.editPerson(personDTO);
        assertEquals("Christian", personEdited.getfName());

    }

    @Test
    public void NegativeEditPerson() throws PersonNotFoundException {
        Person p3 = new Person("Kenneth", "ostesen", "55448745");
        long id = 3;
        try {
            p3.setP_id(id);
            PersonDTO personDTO = new PersonDTO(p3);
            personDTO.setfName("Christian");
            PersonDTO personEdited = facade.editPerson(personDTO);
        } catch (PersonNotFoundException exception) {
            assertEquals(exception.getMessage(), "Person with given id not found");
        }

    }

}
