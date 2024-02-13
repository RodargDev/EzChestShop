package me.deadlight.ezchestshop.databases.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import me.deadlight.ezchestshop.databases.persistence.DatabaseTransactionObject;

import java.util.List;

public class TransactionRepository {

    private EntityManagerFactory emf;

    public TransactionRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void addTransaction(DatabaseTransactionObject transaction) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(transaction);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<DatabaseTransactionObject> findAllTransactions() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<DatabaseTransactionObject> query = em.createQuery("SELECT t FROM Transaction t", DatabaseTransactionObject.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public List<DatabaseTransactionObject> findTransactionsByPlayerName(String playerName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<DatabaseTransactionObject> query = em.createQuery(
                    "SELECT t FROM Transaction t WHERE t.playerName = :playerName", DatabaseTransactionObject.class);
            query.setParameter("playerName", playerName);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Optional: Find transactions within a specific time range
    // This assumes your 'time' field is of a suitable date/time type for comparison
    // Adjust the type of startTime and endTime according to your actual time field type
    public List<DatabaseTransactionObject> findTransactionsByTimeRange(String startTime, String endTime) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<DatabaseTransactionObject> query = em.createQuery(
                    "SELECT t FROM Transaction t WHERE t.time BETWEEN :startTime AND :endTime", DatabaseTransactionObject.class);
            query.setParameter("startTime", startTime);
            query.setParameter("endTime", endTime);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
