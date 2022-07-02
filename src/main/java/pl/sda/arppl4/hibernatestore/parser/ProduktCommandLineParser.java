package pl.sda.arppl4.hibernatestore.parser;

import pl.sda.arppl4.hibernatestore.dao.ProductDao;
import pl.sda.arppl4.hibernatestore.model.ProductUnit;
import pl.sda.arppl4.hibernatestore.model.Produkt;

import javax.sound.midi.Soundbank;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ProduktCommandLineParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Scanner scanner;
    private final ProductDao dao;

    public ProduktCommandLineParser(Scanner scanner, ProductDao dao) {
        this.scanner = scanner;
        this.dao = dao;
    }

    public void parse() {
        String command;
        do {
            System.out.println("Command");
            command = scanner.next();
            if (command.equalsIgnoreCase("add")) {
                handleAddCommand();
            } else if (command.equalsIgnoreCase("list")) {
                handleListCommand();
            }else if(command.equalsIgnoreCase("update")){
                handleUpdateCommand();

            }

        } while (!command.equals("quit"));
    }
    private void handleUpdateCommand() {
        System.out.println("Provide id of the product You'd like to update:");
        Long id = scanner.nextLong();

        Optional<Produkt> productOptional = dao.zwrocProdukt(id);
        if (productOptional.isPresent()) {
            Produkt produkt = productOptional.get();

            System.out.println("What would You like to update [price, expiryDate, quantity]");
            String output = scanner.next();
            switch (output) {
                case "name":
                    System.out.println("Provide price:");
                    Double price = scanner.nextDouble();

                    produkt.setPrice(price);
                    break;
                case "expiryDate":
                    LocalDate expiryDate = loadExpiryDateFromUser();

                    produkt.setExpiryDate(expiryDate);
                    break;
                case "quantity":
                    System.out.println("Provide quantity:");
                    Double quantity = scanner.nextDouble();

                    produkt.setQuantity(quantity);
                    break;
                default:
                    System.out.println("Field with this name is not handled.");
            }

            dao.updateProdukt(produkt);
            System.out.println("Product has been updated.");
        } else {
            System.out.println("Product not found");
        }
    }
//    private void handleDeleteCommand(){
//        System.out.println("Provide id of the product");
//        Long id = scanner.nextLong();
//
//        Optional<Produkt> produktOptional = dao.zwrocProdukt(id);
//        if(produktOptional.isPresent()) {
//            Produkt produkt = produktOptional.get();
//            dao.usunProdukt(produkt);
//            System.out.println("Produkt removed");
//        }else{
//            System.out.println("Produkt naot found");
//        }
//    }

    private void handleListCommand() {
        List<Produkt> produktList = dao.zwrocListeProduktow();
        for(Produkt produkt : produktList) {
            System.out.println(produkt);
        }
        System.out.println();
    }

    private void handleAddCommand() {
        System.out.println("Provide name");
        String name = scanner.next();

        System.out.println("Provide price");
        Double price = scanner.nextDouble();

        System.out.println("Provide producent:");
        String producent = scanner.next();

        LocalDate expiryDate = loadExpiryDateFromUser();

        System.out.println("Provide quantity");
        Double quantity = scanner.nextDouble();

        ProductUnit productUnit = loadProductUnitFromUser();

        Produkt produkt = new Produkt(null, name, price, producent, expiryDate, quantity, productUnit);
        dao.dodajProdukt(produkt);
    }

    private ProductUnit loadProductUnitFromUser() {
        ProductUnit productUnit = null;
        do {
            try {
                System.out.println("Provide unit");
                String unitString = scanner.next();

                productUnit = ProductUnit.valueOf(unitString.toUpperCase());
            } catch (IllegalArgumentException iae) {
                System.err.println("Wrong unit");
            }
        } while (productUnit == null);
        return productUnit;
    }

    private LocalDate loadExpiryDateFromUser() {
        LocalDate expiryDate = null;
        do {
            try {
                System.out.println("Podaj expiry date");
                String expiryDateString = scanner.next();


                expiryDate = LocalDate.parse(expiryDateString, FORMATTER);

                LocalDate today = LocalDate.now();
                if (expiryDate.isBefore(today)) {
                    throw new IllegalArgumentException(("Date is before today"));
                }

            } catch (IllegalArgumentException iae) {
                expiryDate = null;
                System.err.println("Wrong date, please provide date in format: yyy-MM-dd");
            }
        } while (expiryDate == null);
        return expiryDate;
    }
}
