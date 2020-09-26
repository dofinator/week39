package dat3.jpademo.entities;


import dto.PersonStyleDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class Tester {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Anders", 1998);
        Person p2 = new Person("Kalle", 2001);
        Adress a1 = new Adress("vilhelmsro", 3400, "Hillerød");
        Adress a2 = new Adress("ostekade", 3400, "Hillerød");

        p1.setAdress(a1);
        p2.setAdress(a2);

        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        Fee f3 = new Fee(300);

        p1.AddFee(f1);
        p2.AddFee(f2);
        p2.AddFee(f3);

        SwimStyle s1 = new SwimStyle("crawl");
        SwimStyle s2 = new SwimStyle("butterfly");
        SwimStyle s3 = new SwimStyle("breast stroke");

        p1.addSwimStyle(s1);
        p1.addSwimStyle(s2);
        p2.addSwimStyle(s3);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        p1.removeSwimStyle(s2);
        em.getTransaction().commit();

        System.out.println("p1 id: " + p1.getP_id() + " p1 name: " + p1.getName());
        System.out.println("p1 id: " + p2.getP_id() + " p1 name: " + p2.getName());
        System.out.println("Kalles street: " + p1.getAdress().getStreet());

        System.out.println("lad os se om to-vejs virker: " + a1.getPerson().getName());

        System.out.println("Hvem har betalt f2? Det har: " + f2.getPerson().getName());

        TypedQuery<Fee> q1 = em.createQuery("SELECT f FROM Fee f", Fee.class);
        List<Fee> fees = q1.getResultList();

        System.out.println("Hvem har betalt hvad ?");

        for (Fee f : fees) {
            System.out.println(f.getPerson().getName() + " " + f.getAmount() + " " + "By: " + f.getPerson().getAdress().getCity());
        }

        TypedQuery<Person> q2 = em.createQuery("SELECT p FROM Person p", Person.class);
        List<Person> persons = q2.getResultList();

        for (Person p : persons) {
            System.out.println("Navn: " + p.getName());
            for (Fee f : p.getFees()) {
                System.out.println("-- beløb " + f.getAmount() + ", " + f.getPayDate().toString());
            }
        }

        System.out.println("****** JPQL Joins ******");
        Query q3 = em.createQuery("SELECT new dto.PersonStyleDTO(p.name, p.year, s.styleName) FROM Person p JOIN p.styles s");
        
        List<PersonStyleDTO> personDetails = q3.getResultList();
        
        for(PersonStyleDTO ps: personDetails){
            System.out.println("Navn: " + ps.getName() + ", " + ps.getYear() + ", " + ps.getSwimStyle());
        }
    }
}
