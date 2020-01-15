package hu.dayroom;

import hu.dayroom.controller.DayroomService;
import hu.dayroom.model.service.DataApi;
import hu.dayroom.model.service.DataParser;
import hu.dayroom.model.service.FileReader;
import hu.dayroom.model.service.FileWriter;

public class App {

    private final DayroomService dayroomService;
    private final FileWriter fileWriter;

    private App() {
        DataApi dataApi = new DataApi(new FileReader(), new DataParser());
        dayroomService = new DayroomService(dataApi.getData("ajto.txt"));
        fileWriter = new FileWriter("athaladas.txt");
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
        System.out.println(dayroomService.getMostCrowdedTime());
    }

}
