/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import entities.Person;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import sun.security.pkcs11.P11Util;
/**
 *
 * @author lukas
 */
public class Tester {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();
        
        Person pers1 = new Person("lars", "andersen", "44444444");
        Person pers2 = new Person("test", "test", "55555555");
        
        Adress a1 = new Adress("Vilhelms 23", 3480, "Fredensborg");
        Adress a2 = new Adress("Gammelby 2", 3400, "Hillerød");
        
        pers1.setAdress(a1);
        pers2.setAdress(a2);
        
        em.getTransaction().begin();
        em.persist(pers1);
        em.persist(pers2);
        em.getTransaction().commit();
    }
}
