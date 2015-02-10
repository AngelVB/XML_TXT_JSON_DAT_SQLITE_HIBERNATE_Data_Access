package hib.modelo.one;

import hib.modelo.Contacto;
import hib.modelo.Nota;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-01-31T22:03:59")
@StaticMetamodel(Nota.class)
public class Nota_ { 

    public static volatile SingularAttribute<Nota, String> texto;
    public static volatile SingularAttribute<Nota, Date> fecha;
    public static volatile SingularAttribute<Nota, String> importancia;
    public static volatile SingularAttribute<Nota, String> resumen;
    public static volatile SingularAttribute<Nota, Integer> id;
    public static volatile SingularAttribute<Nota, Contacto> contactoId;

}