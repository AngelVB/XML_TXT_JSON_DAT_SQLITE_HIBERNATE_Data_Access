
package hib.controlador;

import hib.controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hib.modelo.Contacto;
import hib.modelo.Nota;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los controladores de la clase Nota.
 * 
 */
public class NotaJpaController implements Serializable {

    public NotaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
/**
 * Método para crear objetos de tipo Nota
 * @param nota 
 */
    public void create(Nota nota) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contacto contactoId = nota.getContactoId();
            if (contactoId != null) {
                contactoId = em.getReference(contactoId.getClass(), contactoId.getId());
                nota.setContactoId(contactoId);
            }
            em.persist(nota);
            if (contactoId != null) {
                contactoId.getNotaCollection().add(nota);
                contactoId = em.merge(contactoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/**
 * Método para editar los objetos de tipo nota.
 * Este método se utilizará para realiar los Updates.
 * @param nota
 * @throws NonexistentEntityException
 * @throws Exception 
 */
    public void edit(Nota nota) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nota persistentNota = em.find(Nota.class, nota.getId());
            Contacto contactoIdOld = persistentNota.getContactoId();
            Contacto contactoIdNew = nota.getContactoId();
            if (contactoIdNew != null) {
                contactoIdNew = em.getReference(contactoIdNew.getClass(), contactoIdNew.getId());
                nota.setContactoId(contactoIdNew);
            }
            nota = em.merge(nota);
            if (contactoIdOld != null && !contactoIdOld.equals(contactoIdNew)) {
                contactoIdOld.getNotaCollection().remove(nota);
                contactoIdOld = em.merge(contactoIdOld);
            }
            if (contactoIdNew != null && !contactoIdNew.equals(contactoIdOld)) {
                contactoIdNew.getNotaCollection().add(nota);
                contactoIdNew = em.merge(contactoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = nota.getId();
                if (findNota(id) == null) {
                    throw new NonexistentEntityException("The nota with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/**
 * Método para borrar un objeto de tipo Nota por el id recibido.
 * @param id
 * @throws NonexistentEntityException 
 */
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nota nota;
            try {
                nota = em.getReference(Nota.class, id);
                nota.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The nota with id " + id + " no longer exists.", enfe);
            }
            Contacto contactoId = nota.getContactoId();
            if (contactoId != null) {
                contactoId.getNotaCollection().remove(nota);
                contactoId = em.merge(contactoId);
            }
            em.remove(nota);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Nota> findNotaEntities() {
        return findNotaEntities(true, -1, -1);
    }

    public List<Nota> findNotaEntities(int maxResults, int firstResult) {
        return findNotaEntities(false, maxResults, firstResult);
    }

    private List<Nota> findNotaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Nota.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Nota findNota(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Nota.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Nota> rt = cq.from(Nota.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
