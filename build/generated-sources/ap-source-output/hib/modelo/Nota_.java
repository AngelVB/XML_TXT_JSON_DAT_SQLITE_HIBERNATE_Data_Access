package hib.modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Nota.class)
public abstract class Nota_ {

	public static volatile SingularAttribute<Nota, String> texto;
	public static volatile SingularAttribute<Nota, Date> fecha;
	public static volatile SingularAttribute<Nota, String> importancia;
	public static volatile SingularAttribute<Nota, String> resumen;
	public static volatile SingularAttribute<Nota, Integer> id;
	public static volatile SingularAttribute<Nota, Contacto> contactoId;

}

