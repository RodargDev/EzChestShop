package me.deadlight.ezchestshop.databases.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import me.deadlight.ezchestshop.databases.persistence.DatabaseShopObject;

import java.util.List;
import java.util.Optional;

public class ShopRepository {

    private final EntityManagerFactory emf;

    public ShopRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public Optional<DatabaseShopObject> findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            DatabaseShopObject shop = em.find(DatabaseShopObject.class, id);
            return Optional.ofNullable(shop);
        } finally {
            em.close();
        }
    }

    public List<DatabaseShopObject> findAllShops() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s", DatabaseShopObject.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public DatabaseShopObject createShop(DatabaseShopObject shop) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            em.persist(shop);
            tx.commit();
            return shop;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public DatabaseShopObject updateShop(DatabaseShopObject shop) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            DatabaseShopObject managedShop = em.merge(shop);
            tx.commit();
            return managedShop;
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteShop(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            DatabaseShopObject shop = em.find(DatabaseShopObject.class, id);
            if (shop != null) {
                em.remove(shop);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteShop(String location) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                em.remove(shop);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void setShopOwner(String location, String owner) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setOwner(owner);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set buy price of a shop by location
    public void setShopBuyPrice(String location, double buyPrice) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setBuyPrice(buyPrice);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set sell price of a shop by location
    public void setShopSellPrice(String location, double sellPrice) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setSellPrice(sellPrice);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set message toggle of a shop by location
    public void setShopMessageToggle(String location, boolean messageToggle) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setMessageToggle(messageToggle);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set buy option toggle of a shop by location
    public void setShopBuyOptionDisable(String location, boolean buyOptionToggle) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setDisableBuyOption(buyOptionToggle);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set sell option toggle of a shop by location
    public void setShopSellOptionDisable(String location, boolean sellOptionToggle) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setDisableSellOption(sellOptionToggle);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set share income toggle of a shop by location
    public void setShopShareIncome(String location, boolean shareIncomeToggle) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setShareIncomeOption(shareIncomeToggle);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set admin shop toggle of a shop by location
    public void setShopAdminShop(String location, boolean adminShopToggle) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setAdminShopStatus(adminShopToggle);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set rotation of a shop by location
    public void setShopRotation(String location, String rotation) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setRotation(rotation);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set admins of a shop by location
    public void setShopAdmins(String location, List<String> admins) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setAdmins(admins);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    //set custom messages of a shop by location
    public void setShopCustomMessages(String location, List<String> customMessages) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            TypedQuery<DatabaseShopObject> query = em.createQuery("SELECT s FROM Shop s WHERE s.location = :location", DatabaseShopObject.class);
            query.setParameter("location", location);
            DatabaseShopObject shop = query.getSingleResult();
            if (shop != null) {
                shop.setCustomMessages(customMessages);
            }
            tx.commit();
        } catch (RuntimeException e) {
            if (tx != null && tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }



}
