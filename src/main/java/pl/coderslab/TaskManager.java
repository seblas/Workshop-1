package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    static String[] opcje = {"add", "remove", "list", "exit"};
    static String plikglobal = "src/main/java/pl/coderslab/tasks.csv";
    static String[][] zadania = new String[1][3];

    public static void main(String[] args) {

        // wczytanie danych z pliku.
        if(wczytanieDanychZPliku()) {
            //Tu będzie cały program o ile plik istnieje lub nie jest pusty
        }
    }

    public static boolean wczytanieDanychZPliku() { // wczytanie danych z pliku i wrzucenie ich do tabeli
        File plik = new File(plikglobal);
        try {
            int licznik = 0; // liczy ilość zadań w pliku
            Scanner skaner = new Scanner(plik);
            StringBuilder calyplik = new StringBuilder(); // tu wczytam cały plik
            while (skaner.hasNextLine()) {
                calyplik.append(skaner.nextLine()).append("&&&"); // dodaję kolejne linie z pliku i dodaję separator &&&
                licznik++;
            }
            if(licznik == 0) {
                System.out.println("Plik jest pusty");
                return false;
            }
            // System.out.println(calyplik); // testowo wyświetlam cały plik
            // System.out.println("Licznik: " + licznik);
            zadania = Arrays.copyOf(zadania, licznik); // nowa tablica o "licznik" - wierszach
            String[] linia = calyplik.toString().split("&&&"); // tworzę tabelę wierszy
            for(int i=0; i<licznik;i++) {
                zadania[i] = linia[i].split(", "); // wpisuję konkretne dane do tabeli
            }
        } catch (FileNotFoundException e) {
            System.out.println("Brak pliku.");
            return false;
        }
        return true;
    }
}
