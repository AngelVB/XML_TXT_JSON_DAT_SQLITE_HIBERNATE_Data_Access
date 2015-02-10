package hib.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Evento.class)
public abstract class Evento_ {

	public static volatile SingularAttribute<Evento, String> descripcion;
	public static volatile CollectionAttribute<Evento, ContactoHasEvento> contactoHasEventoCollection;
	public static volatile SingularAttribute<Evento, Date> inicio;
	public static volatile SingularAttribute<Evento, String> titulo;
	public static volatile SingularAttribute<Evento, Integer> id;
	public static volatile SingularAttribute<Evento, Lugar> lugarId;
	public static volatile SingularAttribute<Evento, Contacto> contactoId;
	public static volatile SingularAttribute<Evento, Date> finaliza;

}

