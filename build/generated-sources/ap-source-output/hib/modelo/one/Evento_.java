package hib.modelo.one;

import hib.modelo.Contacto;
import hib.modelo.ContactoHasEvento;
import hib.modelo.Evento;
import hib.modelo.Lugar;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-31T22:03:59")
@StaticMetamodel(Evento.class)
public class Evento_ { 

    public static volatile SingularAttribute<Evento, String> descripcion;
    public static volatile CollectionAttribute<Evento, ContactoHasEvento> contactoHasEventoCollection;
    public static volatile SingularAttribute<Evento, Date> inicio;
    public static volatile SingularAttribute<Evento, String> titulo;
    public static volatile SingularAttribute<Evento, Integer> id;
    public static volatile SingularAttribute<Evento, Lugar> lugarId;
    public static volatile SingularAttribute<Evento, Contacto> contactoId;
    public static volatile SingularAttribute<Evento, Date> finaliza;

}