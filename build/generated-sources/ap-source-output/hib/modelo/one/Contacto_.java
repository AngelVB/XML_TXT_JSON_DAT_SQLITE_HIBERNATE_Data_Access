package hib.modelo.one;

import hib.modelo.Contacto;
import hib.modelo.ContactoHasEvento;
import hib.modelo.Evento;
import hib.modelo.Nota;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-31T22:03:59")
@StaticMetamodel(Contacto.class)
public class Contacto_ { 

    public static volatile SingularAttribute<Contacto, String> password;
    public static volatile CollectionAttribute<Contacto, Contacto> contactoCollection1;
    public static volatile CollectionAttribute<Contacto, Nota> notaCollection;
    public static volatile CollectionAttribute<Contacto, ContactoHasEvento> contactoHasEventoCollection;
    public static volatile SingularAttribute<Contacto, Integer> id;
    public static volatile SingularAttribute<Contacto, String> telefono;
    public static volatile SingularAttribute<Contacto, String> nombre;
    public static volatile SingularAttribute<Contacto, String> user;
    public static volatile CollectionAttribute<Contacto, Contacto> contactoCollection;
    public static volatile SingularAttribute<Contacto, String> email;
    public static volatile CollectionAttribute<Contacto, Evento> eventoCollection;

}