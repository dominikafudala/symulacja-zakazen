package sim;

import sim.entity.Simulation;

import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args){
        System.out.println("Wybierz opcje:");
        System.out.println("[1] Rozpocznij nową symulację");
        System.out.println("[2] Wczytaj symulację");
        Scanner s = new Scanner(System.in);

        String op = s.nextLine();

        switch (op) {
            case "1" -> {
                System.out.println("[1] Symulacja bez początkowej odporności");
                System.out.println("[2] Symulacja z odpornością");
                String sim = s.nextLine();
                switch (sim) {
                    case "1":
                        new Simulation(700, 1400, 200).startSimulation(false);
                    case "2":
                        new Simulation(700, 1400, 200).startSimulation(true);
                    default:
                        System.out.println("Wybrana opcja nie istnieje");
                }
            }
            case "2" -> {
                File root = new File("./");
                String[] contents = root.list();
                System.out.println("Pliki do wczytania");
                if(contents == null) {
                    System.out.println("Brak plików do wczytania");
                    break;
                }
                Arrays.stream(contents).forEach(elem -> {
                    Pattern pattern = Pattern.compile("state");
                    Matcher matcher = pattern.matcher(elem);
                    boolean matchFound = matcher.find();
                    if (matchFound) {
                        System.out.println(elem);
                    }
                });
                System.out.println("Wpisz nazwe pliku");
                String fileName = s.nextLine();
                new Simulation(700, 1400, fileName).startSimulation(false);
            }
            default -> System.out.println("Wybrana opcja nie istnije");
        }

    }
}
