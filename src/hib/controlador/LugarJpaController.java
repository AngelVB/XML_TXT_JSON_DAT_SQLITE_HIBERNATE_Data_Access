
package hib.controlador;

import hib.controlador.exceptions.IllegalOrphanException;
import hib.controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hib.modelo.Evento;
import hib.modelo.Lugar;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los controladores de la clase Lugar.
 * 
 */
public class LugarJpaController implements Serializable {

    public LugarJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lugar lugar) {
        if (lugar.getEventoCollection() == null) {
            lugar.setEventoCollection(new ArrayList<Evento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Evento> attachedEventoCollection = new ArrayList<Evento>();
            for (Evento eventoCollectionEventoToAttach : lugar.getEventoCollection()) {
                eventoCollectionEventoToAttach = em.getReference(eventoCollectionEventoToAttach.getClass(), eventoCollectionEventoToAttach.getId());
                attachedEventoCollection.add(eventoCollectionEventoToAttach);
            }
            lugar.setEventoCollection(attachedEventoCollection);
            em.persist(lugar);
            for (Evento eventoCollectionEvento : lugar.getEventoCollection()) {
                Lugar oldLugarIdOfEventoCollectionEvento = eventoCollectionEvento.getLugarId();
                eventoCollectionEvento.setLugarId(lugar);
                eventoCollectionEvento = em.merge(eventoCollectionEvento);
                if (oldLugarIdOfEventoCollectionEvento != null) {
                    oldLugarIdOfEventoCollectionEvento.getEventoCollection().remove(eventoCollectionEvento);
                    oldLugarIdOfEventoCollectionEvento = em.merge(oldLugarIdOfEventoCollectionEvento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/**
 * Método para editar los objetos de tipo Lugar
 * @param lugar
 * @throws IllegalOrphanException
 * @throws NonexistentEntityException
 * @throws Exception 
 */
    public void edit(Lugar lugar) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lugar persistentLugar = em.find(Lugar.class, lugar.getId());
            Collection<Evento> eventoCollectionOld = persistentLugar.getEventoCollection();
            Collection<Evento> eventoCollectionNew = lugar.getEventoCollection();
            List<String> illegalOrphanMessages = null;
            for (Evento eventoCollectionOldEvento : eventoCollectionOld) {
                if (!eventoCollectionNew.contains(eventoCollectionOldEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evento " + eventoCollectionOldEvento + " since its lugarId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Evento> attachedEventoCollectionNew = new ArrayList<Evento>();
            for (Evento eventoCollectionNewEventoToAttach : eventoCollectionNew) {
                eventoCollectionNewEventoToAttach = em.getReference(eventoCollectionNewEventoToAttach.getClass(), eventoCollectionNewEventoToAttach.getId());
                attachedEventoCollectionNew.add(eventoCollectionNewEventoToAttach);
            }
            eventoCollectionNew = attachedEventoCollectionNew;
            lugar.setEventoCollection(eventoCollectionNew);
            lugar = em.merge(lugar);
            for (Evento eventoCollectionNewEvento : eventoCollectionNew) {
                if (!eventoCollectionOld.contains(eventoCollectionNewEvento)) {
                    Lugar oldLugarIdOfEventoCollectionNewEvento = eventoCollectionNewEvento.getLugarId();
                    eventoCollectionNewEvento.setLugarId(lugar);
                    eventoCollectionNewEvento = em.merge(eventoCollectionNewEvento);
                    if (oldLugarIdOfEventoCollectionNewEvento != null && !oldLugarIdOfEventoCollectionNewEvento.equals(lugar)) {
                        oldLugarIdOfEventoCollectionNewEvento.getEventoCollection().remove(eventoCollectionNewEvento);
                        oldLugarIdOfEventoCollectionNewEvento = em.merge(oldLugarIdOfEventoCollectionNewEvento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = lugar.getId();
                if (findLugar(id) == null) {
                    throw new NonexistentEntityException("The lugar with id " + id + " no longer exists.");
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
 * Método para eliminar los objetos Lugar de la base de datos en función de su id.
 * @param id
 * @throws IllegalOrphanException
 * @throws NonexistentEntityException 
 */
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lugar lugar;
            try {
                lugar = em.getReference(Lugar.class, id);
                lugar.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lugar with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Evento> eventoCollectionOrphanCheck = lugar.getEventoCollection();
            for (Evento eventoCollectionOrphanCheckEvento : eventoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Lugar (" + lugar + ") cannot be destroyed since the Evento " + eventoCollectionOrphanCheckEvento + " in its eventoCollection field has a non-nullable lugarId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(lugar);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Lugar> findLugarEntities() {
        return findLugarEntities(true, -1, -1);
    }

    public List<Lugar> findLugarEntities(int maxResults, int firstResult) {
        return findLugarEntities(false, maxResults, firstResult);
    }

    private List<Lugar> findLugarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lugar.class));
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

    public Lugar findLugar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lugar.class, id);
        } finally {
            em.close();
        }
    }

    public int getLugarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lugar> rt = cq.from(Lugar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
