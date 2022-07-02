package pl.sda.arppl4.hibernatestore.dao;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pl.sda.arppl4.hibernatestore.model.Produkt;
import pl.sda.arppl4.hibernatestore.util.HibernateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao implements IProduktDao{

    @Override
    public void dodajProdukt(Produkt produkt) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        // Try with resources
        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(produkt);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void usunProdukt(Produkt produkt) {

            SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
            try (Session session = fabrykaPolaczen.openSession()){
                Transaction transaction = session.beginTransaction();

                session.remove(produkt);

                transaction.commit();
            }
        }

    @Override
    public Optional<Produkt> zwrocProdukt(Long id) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {
            Produkt obiektProdukt = session.get(Produkt.class, id);

            return Optional.ofNullable(obiektProdukt);
        }

    }

    @Override
    public List<Produkt> zwrocListeProduktow() {
        List<Produkt> produktList = new ArrayList<>();

        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();
        try (Session session = fabrykaPolaczen.openSession()) {

            TypedQuery<Produkt> zapytanie = session.createQuery("from Produkt", Produkt.class);
            List<Produkt> wynikZapytania = zapytanie.getResultList();

            produktList.addAll(wynikZapytania);
        } catch (SessionException sessionException) {
            System.err.println("Błąd wczytywania danych.");
        }

        return produktList;
    }

    @Override
    public void updateProdukt(Produkt produkt) {
        SessionFactory fabrykaPolaczen = HibernateUtil.getSessionFactory();

        Transaction transaction = null;
        try (Session session = fabrykaPolaczen.openSession()) {
            transaction = session.beginTransaction();

            session.merge(produkt);

            transaction.commit();
        } catch (SessionException sessionException) {
            if (transaction != null){
                transaction.rollback();
            }
        }
    }
}

