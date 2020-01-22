package hu.dayroom;

import hu.dayroom.controller.DayroomService;
import hu.dayroom.model.service.*;

import java.util.Scanner;

public class App {

    private final DayroomService dayroomService;
    private final FileWriter fileWriter;
    private final Console console;

    private App() {
        DataApi dataApi = new DataApi(new FileReader(), new DataParser());
        dayroomService = new DayroomService(dataApi.getData("ajto.txt"));
        fileWriter = new FileWriter("athaladas.txt");
        console = new Console(new Scanner(System.in));
    }

    public static void main(String[] args) {
        new App().run();
    }

    private void run() {
        System.out.println("2. feladat");
        System.out.println("Az első belépő: " + dayroomService.getFirstInId());
        System.out.println("Az utolsó kilépő: " + dayroomService.getLastOutId());
        fileWriter.write(dayroomService.getIdMap());
        System.out.println();
        System.out.println("4. feladat");
        System.out.println("A végén a társalgóban voltak: " + dayroomService.getStayedIds());
        System.out.println();
        System.out.println("5. feladat");
        System.out.println("Például " + dayroomService.getMostCrowdedTime() + "-kor voltak a legtöbben a társalgóban.");
        System.out.println();
        System.out.println("6. feladat");
        System.out.print("Adja meg aszély azonosítóját! ");
        int id = console.readInt();
        System.out.println();
        System.out.println("7. feladat");
        System.out.println(dayroomService.getFormattedLogTimesById(id));
        System.out.println();
        System.out.println("8. feladat");
        System.out.println(dayroomService.getTotalDuration(id));
    }

}
