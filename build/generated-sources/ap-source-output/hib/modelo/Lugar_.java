package hib.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Lugar.class)
public abstract class Lugar_ {

	public static volatile SingularAttribute<Lugar, String> codpost;
	public static volatile SingularAttribute<Lugar, String> calle;
	public static volatile SingularAttribute<Lugar, Integer> id;
	public static volatile CollectionAttribute<Lugar, Evento> eventoCollection;

}

