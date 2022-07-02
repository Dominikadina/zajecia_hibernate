package pl.sda.arppl4.hibernatestore.dao;

import pl.sda.arppl4.hibernatestore.model.Produkt;

import java.util.List;
import java.util.Optional;

public interface IProduktDao {


            // Create
            public void dodajProdukt(Produkt produkt);
            // Delete
            public void usunProdukt(Produkt produkt);
            // Read
            public Optional<Produkt> zwrocProdukt(Long id);

            public List<Produkt> zwrocListeProduktow();

            public void updateProdukt(Produkt produkt);
        }

