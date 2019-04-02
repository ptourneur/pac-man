package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{


        Grille grille=new Grille(10,10);

        Entity player=new Entity(grille);
        grille.addEntity(player);
        View view=new View();
        grille.addObserver(view);
        Thread t = new Thread(grille);
        t.start();
        Thread t2 = new Thread(player);
        t2.start();
        /*

         */
    }


    public static void main(String[] args) {
        launch(args);
    }
}
