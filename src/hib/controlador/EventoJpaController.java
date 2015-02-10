
package hib.controlador;

import hib.controlador.exceptions.IllegalOrphanException;
import hib.controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hib.modelo.Lugar;
import hib.modelo.Contacto;
import hib.modelo.ContactoHasEvento;
import hib.modelo.Evento;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los controladores de la clase Evento.
 * 
 */
public class EventoJpaController implements Serializable {

    public EventoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evento evento) {
        if (evento.getContactoHasEventoCollection() == null) {
            evento.setContactoHasEventoCollection(new ArrayList<ContactoHasEvento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Lugar lugarId = evento.getLugarId();
            if (lugarId != null) {
                lugarId = em.getReference(lugarId.getClass(), lugarId.getId());
                evento.setLugarId(lugarId);
            }
            Contacto contactoId = evento.getContactoId();
            if (contactoId != null) {
                contactoId = em.getReference(contactoId.getClass(), contactoId.getId());
                evento.setContactoId(contactoId);
            }
            Collection<ContactoHasEvento> attachedContactoHasEventoCollection = new ArrayList<ContactoHasEvento>();
            for (ContactoHasEvento contactoHasEventoCollectionContactoHasEventoToAttach : evento.getContactoHasEventoCollection()) {
                contactoHasEventoCollectionContactoHasEventoToAttach = em.getReference(contactoHasEventoCollectionContactoHasEventoToAttach.getClass(), contactoHasEventoCollectionContactoHasEventoToAttach.getContactoHasEventoPK());
                attachedContactoHasEventoCollection.add(contactoHasEventoCollectionContactoHasEventoToAttach);
            }
            evento.setContactoHasEventoCollection(attachedContactoHasEventoCollection);
            em.persist(evento);
            if (lugarId != null) {
                lugarId.getEventoCollection().add(evento);
                lugarId = em.merge(lugarId);
            }
            if (contactoId != null) {
                contactoId.getEventoCollection().add(evento);
                contactoId = em.merge(contactoId);
            }
            for (ContactoHasEvento contactoHasEventoCollectionContactoHasEvento : evento.getContactoHasEventoCollection()) {
                Evento oldEventoOfContactoHasEventoCollectionContactoHasEvento = contactoHasEventoCollectionContactoHasEvento.getEvento();
                contactoHasEventoCollectionContactoHasEvento.setEvento(evento);
                contactoHasEventoCollectionContactoHasEvento = em.merge(contactoHasEventoCollectionContactoHasEvento);
                if (oldEventoOfContactoHasEventoCollectionContactoHasEvento != null) {
                    oldEventoOfContactoHasEventoCollectionContactoHasEvento.getContactoHasEventoCollection().remove(contactoHasEventoCollectionContactoHasEvento);
                    oldEventoOfContactoHasEventoCollectionContactoHasEvento = em.merge(oldEventoOfContactoHasEventoCollectionContactoHasEvento);
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
 * Método para editar los objetos Evento
 * @param evento
 * @throws IllegalOrphanException
 * @throws NonexistentEntityException
 * @throws Exception 
 */
    public void edit(Evento evento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento persistentEvento = em.find(Evento.class, evento.getId());
            Lugar lugarIdOld = persistentEvento.getLugarId();
            Lugar lugarIdNew = evento.getLugarId();
            Contacto contactoIdOld = persistentEvento.getContactoId();
            Contacto contactoIdNew = evento.getContactoId();
            Collection<ContactoHasEvento> contactoHasEventoCollectionOld = persistentEvento.getContactoHasEventoCollection();
            Collection<ContactoHasEvento> contactoHasEventoCollectionNew = evento.getContactoHasEventoCollection();
            List<String> illegalOrphanMessages = null;
            for (ContactoHasEvento contactoHasEventoCollectionOldContactoHasEvento : contactoHasEventoCollectionOld) {
                if (!contactoHasEventoCollectionNew.contains(contactoHasEventoCollectionOldContactoHasEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContactoHasEvento " + contactoHasEventoCollectionOldContactoHasEvento + " since its evento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (lugarIdNew != null) {
                lugarIdNew = em.getReference(lugarIdNew.getClass(), lugarIdNew.getId());
                evento.setLugarId(lugarIdNew);
            }
            if (contactoIdNew != null) {
                contactoIdNew = em.getReference(contactoIdNew.getClass(), contactoIdNew.getId());
                evento.setContactoId(contactoIdNew);
            }
            Collection<ContactoHasEvento> attachedContactoHasEventoCollectionNew = new ArrayList<ContactoHasEvento>();
            for (ContactoHasEvento contactoHasEventoCollectionNewContactoHasEventoToAttach : contactoHasEventoCollectionNew) {
                contactoHasEventoCollectionNewContactoHasEventoToAttach = em.getReference(contactoHasEventoCollectionNewContactoHasEventoToAttach.getClass(), contactoHasEventoCollectionNewContactoHasEventoToAttach.getContactoHasEventoPK());
                attachedContactoHasEventoCollectionNew.add(contactoHasEventoCollectionNewContactoHasEventoToAttach);
            }
            contactoHasEventoCollectionNew = attachedContactoHasEventoCollectionNew;
            evento.setContactoHasEventoCollection(contactoHasEventoCollectionNew);
            evento = em.merge(evento);
            if (lugarIdOld != null && !lugarIdOld.equals(lugarIdNew)) {
                lugarIdOld.getEventoCollection().remove(evento);
                lugarIdOld = em.merge(lugarIdOld);
            }
            if (lugarIdNew != null && !lugarIdNew.equals(lugarIdOld)) {
                lugarIdNew.getEventoCollection().add(evento);
                lugarIdNew = em.merge(lugarIdNew);
            }
            if (contactoIdOld != null && !contactoIdOld.equals(contactoIdNew)) {
                contactoIdOld.getEventoCollection().remove(evento);
                contactoIdOld = em.merge(contactoIdOld);
            }
            if (contactoIdNew != null && !contactoIdNew.equals(contactoIdOld)) {
                contactoIdNew.getEventoCollection().add(evento);
                contactoIdNew = em.merge(contactoIdNew);
            }
            for (ContactoHasEvento contactoHasEventoCollectionNewContactoHasEvento : contactoHasEventoCollectionNew) {
                if (!contactoHasEventoCollectionOld.contains(contactoHasEventoCollectionNewContactoHasEvento)) {
                    Evento oldEventoOfContactoHasEventoCollectionNewContactoHasEvento = contactoHasEventoCollectionNewContactoHasEvento.getEvento();
                    contactoHasEventoCollectionNewContactoHasEvento.setEvento(evento);
                    contactoHasEventoCollectionNewContactoHasEvento = em.merge(contactoHasEventoCollectionNewContactoHasEvento);
                    if (oldEventoOfContactoHasEventoCollectionNewContactoHasEvento != null && !oldEventoOfContactoHasEventoCollectionNewContactoHasEvento.equals(evento)) {
                        oldEventoOfContactoHasEventoCollectionNewContactoHasEvento.getContactoHasEventoCollection().remove(contactoHasEventoCollectionNewContactoHasEvento);
                        oldEventoOfContactoHasEventoCollectionNewContactoHasEvento = em.merge(oldEventoOfContactoHasEventoCollectionNewContactoHasEvento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evento.getId();
                if (findEvento(id) == null) {
                    throw new NonexistentEntityException("The evento with id " + id + " no longer exists.");
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
 * Método para borrar los objetos Evento por id
 * @param id
 * @throws IllegalOrphanException
 * @throws NonexistentEntityException 
 */
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento evento;
            try {
                evento = em.getReference(Evento.class, id);
                evento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ContactoHasEvento> contactoHasEventoCollectionOrphanCheck = evento.getContactoHasEventoCollection();
            for (ContactoHasEvento contactoHasEventoCollectionOrphanCheckContactoHasEvento : contactoHasEventoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Evento (" + evento + ") cannot be destroyed since the ContactoHasEvento " + contactoHasEventoCollectionOrphanCheckContactoHasEvento + " in its contactoHasEventoCollection field has a non-nullable evento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Lugar lugarId = evento.getLugarId();
            if (lugarId != null) {
                lugarId.getEventoCollection().remove(evento);
                lugarId = em.merge(lugarId);
            }
            Contacto contactoId = evento.getContactoId();
            if (contactoId != null) {
                contactoId.getEventoCollection().remove(evento);
                contactoId = em.merge(contactoId);
            }
            em.remove(evento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evento> findEventoEntities() {
        return findEventoEntities(true, -1, -1);
    }

    public List<Evento> findEventoEntities(int maxResults, int firstResult) {
        return findEventoEntities(false, maxResults, firstResult);
    }

    private List<Evento> findEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evento.class));
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

    public Evento findEvento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evento.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evento> rt = cq.from(Evento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
