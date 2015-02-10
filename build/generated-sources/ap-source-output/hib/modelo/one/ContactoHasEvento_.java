package hib.modelo.one;

import hib.modelo.Contacto;
import hib.modelo.ContactoHasEvento;
import hib.modelo.ContactoHasEventoPK;
import hib.modelo.Evento;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-31T22:03:59")
@StaticMetamodel(ContactoHasEvento.class)
public class ContactoHasEvento_ { 

    public static volatile SingularAttribute<ContactoHasEvento, ContactoHasEventoPK> contactoHasEventoPK;
    public static volatile SingularAttribute<ContactoHasEvento, Integer> acude;
    public static volatile SingularAttribute<ContactoHasEvento, Evento> evento;
    public static volatile SingularAttribute<ContactoHasEvento, Contacto> contacto;

}