package hib.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contacto.class)
public abstract class Contacto_ {

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

