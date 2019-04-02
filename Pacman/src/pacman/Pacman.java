/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Epulapp
 */
public class Pacman extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Grille grille=new Grille(10,10);
        View view=new View(primaryStage);
        grille.addObserver(view);
        Thread t = new Thread(grille);
        t.start();
        /*
        
        */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
