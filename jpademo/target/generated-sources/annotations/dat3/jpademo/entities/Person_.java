package dat3.jpademo.entities;

import dat3.jpademo.entities.Adress;
import dat3.jpademo.entities.Fee;
import dat3.jpademo.entities.SwimStyle;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-09-26T15:11:04")
@StaticMetamodel(Person.class)
public class Person_ { 

    public static volatile ListAttribute<Person, Fee> fees;
    public static volatile SingularAttribute<Person, Integer> year;
    public static volatile SingularAttribute<Person, String> name;
    public static volatile SingularAttribute<Person, Adress> adress;
    public static volatile ListAttribute<Person, SwimStyle> styles;
    public static volatile SingularAttribute<Person, Long> p_id;

}