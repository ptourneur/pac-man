package sample;

import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class View implements Observer {


    public View(){

    }
    @Override
    public void update(Observable o, Object arg) {
        Grille g=((Grille) o);
        for(int i=0;i<g.getX();i++){
            for(int j=0;j<g.getY();j++){
                if(g.getPlayer().getX()==i && g.getPlayer().getY()==j){
                    System.out.print("X ");
                }else{
                    System.out.print(g.getTab()[i][j]?"1 ":"0 ");
                }

            }
            System.out.println("");
        }
        System.out.println("-------------------------------------------");
        System.out.println("END GRID");
        System.out.println("-------------------------------------------");
    }

}
