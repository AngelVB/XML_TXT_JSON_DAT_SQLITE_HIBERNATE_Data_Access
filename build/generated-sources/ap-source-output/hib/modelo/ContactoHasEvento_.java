package hib.modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContactoHasEvento.class)
public abstract class ContactoHasEvento_ {

	public static volatile SingularAttribute<ContactoHasEvento, ContactoHasEventoPK> contactoHasEventoPK;
	public static volatile SingularAttribute<ContactoHasEvento, Integer> acude;
	public static volatile SingularAttribute<ContactoHasEvento, Evento> evento;
	public static volatile SingularAttribute<ContactoHasEvento, Contacto> contacto;

}

