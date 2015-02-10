package hib.modelo.one;

import hib.modelo.Evento;
import hib.modelo.Lugar;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-31T22:03:59")
@StaticMetamodel(Lugar.class)
public class Lugar_ { 

    public static volatile SingularAttribute<Lugar, String> codpost;
    public static volatile SingularAttribute<Lugar, String> calle;
    public static volatile SingularAttribute<Lugar, Integer> id;
    public static volatile CollectionAttribute<Lugar, Evento> eventoCollection;

}