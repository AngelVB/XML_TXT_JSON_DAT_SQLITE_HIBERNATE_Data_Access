
package hib.controlador;

import hib.controlador.exceptions.IllegalOrphanException;
import hib.controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hib.modelo.Contacto;
import java.util.ArrayList;
import java.util.Collection;
import hib.modelo.ContactoHasEvento;
import hib.modelo.Evento;
import hib.modelo.Nota;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los controladores de la clase Contacto.
 * 
 */
public class ContactoJpaController implements Serializable {

    public ContactoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
/**
 * Método para crear los objetos de tipo Contacto
 * @param contacto 
 */
    public void create(Contacto contacto) {
        if (contacto.getContactoCollection() == null) {
            contacto.setContactoCollection(new ArrayList<Contacto>());
        }
        if (contacto.getContactoCollection1() == null) {
            contacto.setContactoCollection1(new ArrayList<Contacto>());
        }
        if (contacto.getContactoHasEventoCollection() == null) {
            contacto.setContactoHasEventoCollection(new ArrayList<ContactoHasEvento>());
        }
        if (contacto.getEventoCollection() == null) {
            contacto.setEventoCollection(new ArrayList<Evento>());
        }
        if (contacto.getNotaCollection() == null) {
            contacto.setNotaCollection(new ArrayList<Nota>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Contacto> attachedContactoCollection = new ArrayList<Contacto>();
            for (Contacto contactoCollectionContactoToAttach : contacto.getContactoCollection()) {
                contactoCollectionContactoToAttach = em.getReference(contactoCollectionContactoToAttach.getClass(), contactoCollectionContactoToAttach.getId());
                attachedContactoCollection.add(contactoCollectionContactoToAttach);
            }
            contacto.setContactoCollection(attachedContactoCollection);
            Collection<Contacto> attachedContactoCollection1 = new ArrayList<Contacto>();
            for (Contacto contactoCollection1ContactoToAttach : contacto.getContactoCollection1()) {
                contactoCollection1ContactoToAttach = em.getReference(contactoCollection1ContactoToAttach.getClass(), contactoCollection1ContactoToAttach.getId());
                attachedContactoCollection1.add(contactoCollection1ContactoToAttach);
            }
            contacto.setContactoCollection1(attachedContactoCollection1);
            Collection<ContactoHasEvento> attachedContactoHasEventoCollection = new ArrayList<ContactoHasEvento>();
            for (ContactoHasEvento contactoHasEventoCollectionContactoHasEventoToAttach : contacto.getContactoHasEventoCollection()) {
                contactoHasEventoCollectionContactoHasEventoToAttach = em.getReference(contactoHasEventoCollectionContactoHasEventoToAttach.getClass(), contactoHasEventoCollectionContactoHasEventoToAttach.getContactoHasEventoPK());
                attachedContactoHasEventoCollection.add(contactoHasEventoCollectionContactoHasEventoToAttach);
            }
            contacto.setContactoHasEventoCollection(attachedContactoHasEventoCollection);
            Collection<Evento> attachedEventoCollection = new ArrayList<Evento>();
            for (Evento eventoCollectionEventoToAttach : contacto.getEventoCollection()) {
                eventoCollectionEventoToAttach = em.getReference(eventoCollectionEventoToAttach.getClass(), eventoCollectionEventoToAttach.getId());
                attachedEventoCollection.add(eventoCollectionEventoToAttach);
            }
            contacto.setEventoCollection(attachedEventoCollection);
            Collection<Nota> attachedNotaCollection = new ArrayList<Nota>();
            for (Nota notaCollectionNotaToAttach : contacto.getNotaCollection()) {
                notaCollectionNotaToAttach = em.getReference(notaCollectionNotaToAttach.getClass(), notaCollectionNotaToAttach.getId());
                attachedNotaCollection.add(notaCollectionNotaToAttach);
            }
            contacto.setNotaCollection(attachedNotaCollection);
            em.persist(contacto);
            for (Contacto contactoCollectionContacto : contacto.getContactoCollection()) {
                contactoCollectionContacto.getContactoCollection().add(contacto);
                contactoCollectionContacto = em.merge(contactoCollectionContacto);
            }
            for (Contacto contactoCollection1Contacto : contacto.getContactoCollection1()) {
                contactoCollection1Contacto.getContactoCollection().add(contacto);
                contactoCollection1Contacto = em.merge(contactoCollection1Contacto);
            }
            for (ContactoHasEvento contactoHasEventoCollectionContactoHasEvento : contacto.getContactoHasEventoCollection()) {
                Contacto oldContactoOfContactoHasEventoCollectionContactoHasEvento = contactoHasEventoCollectionContactoHasEvento.getContacto();
                contactoHasEventoCollectionContactoHasEvento.setContacto(contacto);
                contactoHasEventoCollectionContactoHasEvento = em.merge(contactoHasEventoCollectionContactoHasEvento);
                if (oldContactoOfContactoHasEventoCollectionContactoHasEvento != null) {
                    oldContactoOfContactoHasEventoCollectionContactoHasEvento.getContactoHasEventoCollection().remove(contactoHasEventoCollectionContactoHasEvento);
                    oldContactoOfContactoHasEventoCollectionContactoHasEvento = em.merge(oldContactoOfContactoHasEventoCollectionContactoHasEvento);
                }
            }
            for (Evento eventoCollectionEvento : contacto.getEventoCollection()) {
                Contacto oldContactoIdOfEventoCollectionEvento = eventoCollectionEvento.getContactoId();
                eventoCollectionEvento.setContactoId(contacto);
                eventoCollectionEvento = em.merge(eventoCollectionEvento);
                if (oldContactoIdOfEventoCollectionEvento != null) {
                    oldContactoIdOfEventoCollectionEvento.getEventoCollection().remove(eventoCollectionEvento);
                    oldContactoIdOfEventoCollectionEvento = em.merge(oldContactoIdOfEventoCollectionEvento);
                }
            }
            for (Nota notaCollectionNota : contacto.getNotaCollection()) {
                Contacto oldContactoIdOfNotaCollectionNota = notaCollectionNota.getContactoId();
                notaCollectionNota.setContactoId(contacto);
                notaCollectionNota = em.merge(notaCollectionNota);
                if (oldContactoIdOfNotaCollectionNota != null) {
                    oldContactoIdOfNotaCollectionNota.getNotaCollection().remove(notaCollectionNota);
                    oldContactoIdOfNotaCollectionNota = em.merge(oldContactoIdOfNotaCollectionNota);
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
 * Método para modificar los objetos de tipo Contacto
 * @param contacto
 * @throws IllegalOrphanException
 * @throws NonexistentEntityException
 * @throws Exception 
 */
    public void edit(Contacto contacto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contacto persistentContacto = em.find(Contacto.class, contacto.getId());
            Collection<Contacto> contactoCollectionOld = persistentContacto.getContactoCollection();
            Collection<Contacto> contactoCollectionNew = contacto.getContactoCollection();
            Collection<Contacto> contactoCollection1Old = persistentContacto.getContactoCollection1();
            Collection<Contacto> contactoCollection1New = contacto.getContactoCollection1();
            Collection<ContactoHasEvento> contactoHasEventoCollectionOld = persistentContacto.getContactoHasEventoCollection();
            Collection<ContactoHasEvento> contactoHasEventoCollectionNew = contacto.getContactoHasEventoCollection();
            Collection<Evento> eventoCollectionOld = persistentContacto.getEventoCollection();
            Collection<Evento> eventoCollectionNew = contacto.getEventoCollection();
            Collection<Nota> notaCollectionOld = persistentContacto.getNotaCollection();
            Collection<Nota> notaCollectionNew = contacto.getNotaCollection();
            List<String> illegalOrphanMessages = null;
            for (ContactoHasEvento contactoHasEventoCollectionOldContactoHasEvento : contactoHasEventoCollectionOld) {
                if (!contactoHasEventoCollectionNew.contains(contactoHasEventoCollectionOldContactoHasEvento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ContactoHasEvento " + contactoHasEventoCollectionOldContactoHasEvento + " since its contacto field is not nullable.");
                }
            }
            for (Evento eventoCollectionOldEvento : eventoCollectionOld) {
//                if (!eventoCollectionNew.contains(eventoCollectionOldEvento)) {
//                    if (illegalOrphanMessages == null) {
//                        illegalOrphanMessages = new ArrayList<String>();
//                    }
//                    illegalOrphanMessages.add("You must retain Evento " + eventoCollectionOldEvento + " since its contactoId field is not nullable.");
//                }
            }
            for (Nota notaCollectionOldNota : notaCollectionOld) {
                if (!notaCollectionNew.contains(notaCollectionOldNota)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nota " + notaCollectionOldNota + " since its contactoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Contacto> attachedContactoCollectionNew = new ArrayList<Contacto>();
            for (Contacto contactoCollectionNewContactoToAttach : contactoCollectionNew) {
                contactoCollectionNewContactoToAttach = em.getReference(contactoCollectionNewContactoToAttach.getClass(), contactoCollectionNewContactoToAttach.getId());
                attachedContactoCollectionNew.add(contactoCollectionNewContactoToAttach);
            }
            contactoCollectionNew = attachedContactoCollectionNew;
            contacto.setContactoCollection(contactoCollectionNew);
            Collection<Contacto> attachedContactoCollection1New = new ArrayList<Contacto>();
            for (Contacto contactoCollection1NewContactoToAttach : contactoCollection1New) {
                contactoCollection1NewContactoToAttach = em.getReference(contactoCollection1NewContactoToAttach.getClass(), contactoCollection1NewContactoToAttach.getId());
                attachedContactoCollection1New.add(contactoCollection1NewContactoToAttach);
            }
            contactoCollection1New = attachedContactoCollection1New;
            contacto.setContactoCollection1(contactoCollection1New);
            Collection<ContactoHasEvento> attachedContactoHasEventoCollectionNew = new ArrayList<ContactoHasEvento>();
            for (ContactoHasEvento contactoHasEventoCollectionNewContactoHasEventoToAttach : contactoHasEventoCollectionNew) {
                contactoHasEventoCollectionNewContactoHasEventoToAttach = em.getReference(contactoHasEventoCollectionNewContactoHasEventoToAttach.getClass(), contactoHasEventoCollectionNewContactoHasEventoToAttach.getContactoHasEventoPK());
                attachedContactoHasEventoCollectionNew.add(contactoHasEventoCollectionNewContactoHasEventoToAttach);
            }
            contactoHasEventoCollectionNew = attachedContactoHasEventoCollectionNew;
            contacto.setContactoHasEventoCollection(contactoHasEventoCollectionNew);
            Collection<Evento> attachedEventoCollectionNew = new ArrayList<Evento>();
            for (Evento eventoCollectionNewEventoToAttach : eventoCollectionNew) {
                eventoCollectionNewEventoToAttach = em.getReference(eventoCollectionNewEventoToAttach.getClass(), eventoCollectionNewEventoToAttach.getId());
                attachedEventoCollectionNew.add(eventoCollectionNewEventoToAttach);
            }
            eventoCollectionNew = attachedEventoCollectionNew;
            contacto.setEventoCollection(eventoCollectionNew);
            Collection<Nota> attachedNotaCollectionNew = new ArrayList<Nota>();
            for (Nota notaCollectionNewNotaToAttach : notaCollectionNew) {
                notaCollectionNewNotaToAttach = em.getReference(notaCollectionNewNotaToAttach.getClass(), notaCollectionNewNotaToAttach.getId());
                attachedNotaCollectionNew.add(notaCollectionNewNotaToAttach);
            }
            notaCollectionNew = attachedNotaCollectionNew;
            contacto.setNotaCollection(notaCollectionNew);
            contacto = em.merge(contacto);
            for (Contacto contactoCollectionOldContacto : contactoCollectionOld) {
                if (!contactoCollectionNew.contains(contactoCollectionOldContacto)) {
                    contactoCollectionOldContacto.getContactoCollection().remove(contacto);
                    contactoCollectionOldContacto = em.merge(contactoCollectionOldContacto);
                }
            }
            for (Contacto contactoCollectionNewContacto : contactoCollectionNew) {
                if (!contactoCollectionOld.contains(contactoCollectionNewContacto)) {
                    contactoCollectionNewContacto.getContactoCollection().add(contacto);
                    contactoCollectionNewContacto = em.merge(contactoCollectionNewContacto);
                }
            }
            for (Contacto contactoCollection1OldContacto : contactoCollection1Old) {
                if (!contactoCollection1New.contains(contactoCollection1OldContacto)) {
                    contactoCollection1OldContacto.getContactoCollection().remove(contacto);
                    contactoCollection1OldContacto = em.merge(contactoCollection1OldContacto);
                }
            }
            for (Contacto contactoCollection1NewContacto : contactoCollection1New) {
                if (!contactoCollection1Old.contains(contactoCollection1NewContacto)) {
                    contactoCollection1NewContacto.getContactoCollection().add(contacto);
                    contactoCollection1NewContacto = em.merge(contactoCollection1NewContacto);
                }
            }
            for (ContactoHasEvento contactoHasEventoCollectionNewContactoHasEvento : contactoHasEventoCollectionNew) {
                if (!contactoHasEventoCollectionOld.contains(contactoHasEventoCollectionNewContactoHasEvento)) {
                    Contacto oldContactoOfContactoHasEventoCollectionNewContactoHasEvento = contactoHasEventoCollectionNewContactoHasEvento.getContacto();
                    contactoHasEventoCollectionNewContactoHasEvento.setContacto(contacto);
                    contactoHasEventoCollectionNewContactoHasEvento = em.merge(contactoHasEventoCollectionNewContactoHasEvento);
                    if (oldContactoOfContactoHasEventoCollectionNewContactoHasEvento != null && !oldContactoOfContactoHasEventoCollectionNewContactoHasEvento.equals(contacto)) {
                        oldContactoOfContactoHasEventoCollectionNewContactoHasEvento.getContactoHasEventoCollection().remove(contactoHasEventoCollectionNewContactoHasEvento);
                        oldContactoOfContactoHasEventoCollectionNewContactoHasEvento = em.merge(oldContactoOfContactoHasEventoCollectionNewContactoHasEvento);
                    }
                }
            }
            for (Evento eventoCollectionNewEvento : eventoCollectionNew) {
                if (!eventoCollectionOld.contains(eventoCollectionNewEvento)) {
                    Contacto oldContactoIdOfEventoCollectionNewEvento = eventoCollectionNewEvento.getContactoId();
                    eventoCollectionNewEvento.setContactoId(contacto);
                    eventoCollectionNewEvento = em.merge(eventoCollectionNewEvento);
                    if (oldContactoIdOfEventoCollectionNewEvento != null && !oldContactoIdOfEventoCollectionNewEvento.equals(contacto)) {
                        oldContactoIdOfEventoCollectionNewEvento.getEventoCollection().remove(eventoCollectionNewEvento);
                        oldContactoIdOfEventoCollectionNewEvento = em.merge(oldContactoIdOfEventoCollectionNewEvento);
                    }
                }
            }
            for (Nota notaCollectionNewNota : notaCollectionNew) {
                if (!notaCollectionOld.contains(notaCollectionNewNota)) {
                    Contacto oldContactoIdOfNotaCollectionNewNota = notaCollectionNewNota.getContactoId();
                    notaCollectionNewNota.setContactoId(contacto);
                    notaCollectionNewNota = em.merge(notaCollectionNewNota);
                    if (oldContactoIdOfNotaCollectionNewNota != null && !oldContactoIdOfNotaCollectionNewNota.equals(contacto)) {
                        oldContactoIdOfNotaCollectionNewNota.getNotaCollection().remove(notaCollectionNewNota);
                        oldContactoIdOfNotaCollectionNewNota = em.merge(oldContactoIdOfNotaCollectionNewNota);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contacto.getId();
                if (findContacto(id) == null) {
                    throw new NonexistentEntityException("The contacto with id " + id + " no longer exists.");
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
     * Método para borrar los objetos de tipo Contacto por su ID
     * @param id
     * @throws IllegalOrphanException
     * @throws NonexistentEntityException 
     */

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contacto contacto;
            try {
                contacto = em.getReference(Contacto.class, id);
                contacto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contacto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<ContactoHasEvento> contactoHasEventoCollectionOrphanCheck = contacto.getContactoHasEventoCollection();
            for (ContactoHasEvento contactoHasEventoCollectionOrphanCheckContactoHasEvento : contactoHasEventoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contacto (" + contacto + ") cannot be destroyed since the ContactoHasEvento " + contactoHasEventoCollectionOrphanCheckContactoHasEvento + " in its contactoHasEventoCollection field has a non-nullable contacto field.");
            }
            Collection<Evento> eventoCollectionOrphanCheck = contacto.getEventoCollection();
            for (Evento eventoCollectionOrphanCheckEvento : eventoCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contacto (" + contacto + ") cannot be destroyed since the Evento " + eventoCollectionOrphanCheckEvento + " in its eventoCollection field has a non-nullable contactoId field.");
            }
            Collection<Nota> notaCollectionOrphanCheck = contacto.getNotaCollection();
            for (Nota notaCollectionOrphanCheckNota : notaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Contacto (" + contacto + ") cannot be destroyed since the Nota " + notaCollectionOrphanCheckNota + " in its notaCollection field has a non-nullable contactoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Contacto> contactoCollection = contacto.getContactoCollection();
            for (Contacto contactoCollectionContacto : contactoCollection) {
                contactoCollectionContacto.getContactoCollection().remove(contacto);
                contactoCollectionContacto = em.merge(contactoCollectionContacto);
            }
            Collection<Contacto> contactoCollection1 = contacto.getContactoCollection1();
            for (Contacto contactoCollection1Contacto : contactoCollection1) {
                contactoCollection1Contacto.getContactoCollection().remove(contacto);
                contactoCollection1Contacto = em.merge(contactoCollection1Contacto);
            }
            em.remove(contacto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contacto> findContactoEntities() {
        return findContactoEntities(true, -1, -1);
    }

    public List<Contacto> findContactoEntities(int maxResults, int firstResult) {
        return findContactoEntities(false, maxResults, firstResult);
    }

    private List<Contacto> findContactoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contacto.class));
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

    public Contacto findContacto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contacto.class, id);
        } finally {
            em.close();
        }
    }

     public boolean anyadirAmigo (Contacto logged, Contacto am){
         EntityManager em = null;
            em = getEntityManager();
            em.getTransaction().begin();   

            logged.getContactoCollection().add(am);
             em.merge(logged);
            // em.merge(am);
         
        em.getTransaction().commit();
        
        return true;
        
    }
    public boolean borrarAmigo (Contacto logged, Contacto am){
         EntityManager em = null;
            em = getEntityManager();
            em.getTransaction().begin();   

        if (logged.getContactoCollection().contains(am)) {
            
            boolean remove = logged.getContactoCollection().remove(am);
             boolean remove1 = am.getContactoCollection1().remove(logged);
          
        } else {
            boolean remove2 = logged.getContactoCollection1().remove(am);
             boolean remove1 = am.getContactoCollection().remove(logged);
        
        }

        em.merge(logged);
        em.merge(am);
        em.getTransaction().commit();
        
        return true;
        
    }
    public int getContactoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contacto> rt = cq.from(Contacto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
