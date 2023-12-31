package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class TaskManager {
    static String[] opcje = {"add", "remove", "list", "exit"};
    static String plikglobal = "src/main/java/pl/coderslab/tasks.csv";
    static String[][] zadania = new String[1][3];

    public static void main(String[] args) {

        // wczytanie danych z pliku.
        if(wczytanieDanychZPliku()) {
            //Tu będzie cały program o ile plik istnieje lub nie jest pusty
            Scanner skaner = new Scanner(System.in);
                etykietaDlaPetli:
                while(true) {
                    wyswietlenieMenu();
                    String wybor = skaner.nextLine();
                    switch (wybor) {
                        case "add":
                            dodawanieZadania();
                            break;
                        case "remove":
                            if(zadania.length >0) usuwanieZadania();
                            else System.out.println(ConsoleColors.RED + "Nie ma żadnych zadań!");
                            break;
                        case "list":
                            pokazanieZadan();
                            break;
                        case "exit":
                            zapisZadan();
                            System.out.println(ConsoleColors.RED + "Bye, bye.");
                            break etykietaDlaPetli;
                        default:
                            System.out.println(ConsoleColors.RED + "Nieprawidłowe polecenie. Spróbuj ponownie");
                    }
            }
        }
    }

    public static void zapisZadan() {

    }

    public static void pokazanieZadan() {
        if(zadania.length == 0) System.out.println(ConsoleColors.RED + "Brak zadań!");
        else {
            for(int i=0; i<zadania.length; i++) {
                System.out.print(ConsoleColors.RESET + i + " : ");
                System.out.print(zadania[i][0] + "  " + zadania[i][1] + " ");
                System.out.println(ConsoleColors.RED + zadania[i][2]);
            }
        }
    }

    public static void usuwanieZadania() {
        Scanner skaner = new Scanner(System.in);
        String wybor;
        while(true) {
            System.out.println(ConsoleColors.BLUE + "Please select number to remove." + ConsoleColors.GREEN);
            wybor = skaner.nextLine();
            if(NumberUtils.isParsable(wybor)) {
                int numer = NumberUtils.toInt(wybor);
                if (numer>=0 && numer<zadania.length) {


            /*     //   Sprawdzanie jak wygląda tablica przed usunięciem wiersza
                    System.out.println("Tablica przed dodaniem, length = " + zadania.length);
                    for (int i=0; i<zadania.length; i++)
                        System.out.println(Arrays.toString(zadania[i]));
            */
                    zadania = ArrayUtils.remove(zadania, numer);
                    System.out.println(ConsoleColors.RED + "Value was successfully deleted.");

                 /*   //   Sprawdzanie jak wygląda tablica po usunięciu wiersza
                    System.out.println("Tablica po usunięciu, length = " + zadania.length);
                    for (int i=0; i<zadania.length; i++)
                        System.out.println(Arrays.toString(zadania[i]));
                    */
                    break;


                } else System.out.println(ConsoleColors.RED + "Podaj prawidłową wartość (0 - " + (zadania.length-1) + ")");
            } else System.out.println(ConsoleColors.RED + "Podaj liczbę");
        }
    }

    public static boolean wczytanieDanychZPliku() { // wczytanie danych z pliku i wrzucenie ich do tabeli
        File plik = new File(plikglobal);
        try (Scanner skaner = new Scanner(plik);) {
            int licznik = 0; // liczy ilość zadań w pliku
            Scanner skanerplik = new Scanner(plik);
            StringBuilder calyplik = new StringBuilder(); // tu wczytam cały plik
            while (skanerplik.hasNextLine()) {
                calyplik.append(skanerplik.nextLine()).append("&&&"); // dodaję kolejne linie z pliku i dodaję separator &&&
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

    public static void wyswietlenieMenu() {
        System.out.println(ConsoleColors.BLUE + "Please select an option");
        System.out.print(ConsoleColors.RESET);
        for(String opcja : opcje) {
            System.out.println(opcja);
        }
        System.out.print(ConsoleColors.GREEN);
    }

    public static void dodawanieZadania() {

        Scanner skaner = new Scanner(System.in);
        String opis, data, waznosc;
        while(true) {
            System.out.println(ConsoleColors.BLUE + "Please add task description" + ConsoleColors.GREEN);
            opis = skaner.nextLine();
            if(opis.length()<3) {
                System.out.println(ConsoleColors.RED + "Zbyt krótki opis");
            }
            else break;
        }

        while(true) {
            System.out.println(ConsoleColors.BLUE + "Please add task due data (yyyy-MM-dd)" + ConsoleColors.GREEN);
            data = skaner.nextLine();
            if(data.length()>10) data = data.substring(0, 10);
            if(sprawdzDate(data)) break;
            else System.out.println(ConsoleColors.RED + "Nieprawidłowy format daty");
        }

        while(true) {
            System.out.println(ConsoleColors.BLUE + "Is your task important: " + ConsoleColors.RED + "true" +
                    ConsoleColors.RESET + "/" + ConsoleColors.RED +"false" + ConsoleColors.GREEN);
            waznosc = skaner.nextLine();
            if(waznosc.equals("true") || waznosc.equals("false")) break;
            else System.out.println(ConsoleColors.RED + "Wybierz true lub false");
        }

        /* A tu kod, który napisałem, ale potem zakomentowałem i jednak skorzystałem z sugerowanej ArrayList
        zadania = Arrays.copyOf(zadania, zadania.length +1);
        // Teraz muszę inicjować nową tablicę, bo jej jeszcze nie ma i nie można określić rozmiaru.
        // W przypadku tablicy jednowymiaropwej problem nie występuje.
        zadania[zadania.length-1] = new String[3]; // tu inicjuję nową tablicę, muszę tak zrobić, bo nowododana tablica
        //jest NULL i nie można określić jej wielkości. Podaję wartość 3, bo wiem, że takie dane są. Jeżeli nie wiem
        //to muszę skorzystać z ArrayList co też w końcu zrobiłem
        zadania[zadania.length-1][0] = opis;
        zadania[zadania.length-1][1] = data;
        zadania[zadania.length-1][2] = waznosc; */

        List<String[]> zadaniaLista = new ArrayList<>(); // tworzę nową listę tablic
        String[] nowaTablica = new String[]{opis, data, waznosc}; // elementy wiersza do dodania
        for(String[] tablica : zadania) // do listy Tablic dodaję pierwotne dane
        zadaniaLista.add(tablica);
        zadaniaLista.add(nowaTablica); // dodaję też nowe dane (nowy wiersz)
        int dlugosc = zadania.length;
        zadania = zadaniaLista.toArray(new String[dlugosc+1][]); //zamieniam listę tablic na tabelę tabel
    }

    public static boolean sprawdzDate(String czyData) { //metoda zapożyczona z internetu, bo ręczne sprawdzanie daty byłoby za bardzo pracochłonne
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setLenient(false); // Ustawienie na "false" sprawi, że parser będzie bardziej restrykcyjny
        try {
            format.parse(czyData);
            return true; // Jeśli parsowanie się powiedzie, to data jest prawidłowa
        } catch (ParseException e) {
            return false; // Jeśli wystąpi ParseException, data jest nieprawidłowa
        }
    }






}
