package pl.sda.arppl4.hibernatestore;

import pl.sda.arppl4.hibernatestore.dao.ProductDao;
import pl.sda.arppl4.hibernatestore.parser.ProduktCommandLineParser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ProductDao productDao = new ProductDao();

        ProduktCommandLineParser parser = new ProduktCommandLineParser (scanner, productDao);
        parser.parse();
    }
}
