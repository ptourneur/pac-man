package pacman;


import java.util.Observable;
import java.util.Observer;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Epulapp
 */
public class View implements Observer{
    
    private Stage primaryStage;
    private Scene primaryScene;
    private TilePane primaryTilePane;
    
    public View(Stage primaryStage){
        this.primaryStage= primaryStage;
        
        
        StackPane root = new StackPane();
        //root.getChildren().add(btn);
        
        
         //Creating a Tile Pane 
        primaryTilePane = new TilePane();   
       
        //Setting the orientation for the Tile Pane 
        primaryTilePane.setOrientation(Orientation.HORIZONTAL); 
       
        //Setting the alignment for the Tile Pane 
        primaryTilePane.setTileAlignment(Pos.CENTER_LEFT); 
       
        //Setting the preferred columns for the Tile Pane 
        primaryTilePane.setPrefRows(4);  
     
	  
         //Creating a scene object 
        Scene scene = new Scene(primaryTilePane);  
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    @Override
    public void update(Observable o, Object arg) {
        Grille g=((Grille) o);
        for(int i=0;i<g.getX();i++){
            for(int j=0;j<g.getY();j++){
                Button btn = new Button();
                System.out.print(g.getTab()[i][j]?"1 ":"0 ");
            }
            System.out.println("");
        }
        System.out.println("-------------------------------------------");
        System.out.println("END GRID");
        System.out.println("-------------------------------------------");
    }
    
}
