package ViewController;

import Model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

public class Main extends Application {

    private int rowCount;
    private int columnCount;

    private Grid grille;
    private PacMan pacMan;
    private Ghost ghostRed;
    private Ghost ghostBlue;
    @Override
    public void start(Stage primaryStage) {
        String musicFile = "src/ressources/pacman_beginning.wav";     // For example
        Media sound = new Media(new File(musicFile).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();

        GridPane grid = new GridPane(); // création de la grille

        Image imgPMRight = new Image("ressources/pacmanRight.gif");
        Image imgPMLeft = new Image("ressources/pacmanLeft.gif");
        Image imgPMUp = new Image("ressources/pacmanUp.gif");
        Image imgPMDown = new Image("ressources/pacmanDown.gif");
        Image[] pacmanImages ={imgPMUp,imgPMRight,imgPMDown,imgPMLeft};

        Image imgRedGhostRight = new Image("ressources/redGhostRight.gif");
        Image imgRedGhostLeft = new Image("ressources/redGhostLeft.gif");
        Image imgRedGhostUp = new Image("ressources/redGhostUp.gif");
        Image imgRedGhostDown = new Image("ressources/redGhostDown.gif");
        Image[] redGhostImages ={imgRedGhostUp, imgRedGhostRight, imgRedGhostDown, imgRedGhostLeft};

        Image imgCyanGhostRight = new Image("ressources/cyanGhostRight.gif");
        Image imgCyanGhostLeft = new Image("ressources/cyanGhostLeft.gif");
        Image imgCyanGhostUp = new Image("ressources/cyanGhostUp.gif");
        Image imgCyanGhostDown = new Image("ressources/cyanGhostDown.gif");
        Image[] cyanGhostImages ={imgCyanGhostUp, imgCyanGhostRight, imgCyanGhostDown, imgCyanGhostLeft};

        Image imgOrangeGhostRight = new Image("ressources/orangeGhostRight.gif");
        Image imgOrangeGhostLeft = new Image("ressources/orangeGhostLeft.gif");
        Image imgOrangeGhostUp = new Image("ressources/orangeGhostUp.gif");
        Image imgOrangeGhostDown = new Image("ressources/orangeGhostDown.gif");
        Image[] orangeGhostImages ={imgOrangeGhostUp, imgOrangeGhostRight, imgOrangeGhostDown, imgOrangeGhostLeft};

        Image imgSmallDot = new Image("ressources/smalldot.png");
        Image imgWhiteDot = new Image("ressources/whitedot.png");
        Image imgWall = new Image("ressources/wall.png");
        Image imgGround = new Image("ressources/ground.png");

        grille = new Grid();
        columnCount = grille.getColumnCount();
        rowCount = grille.getRowCount();
        pacMan = grille.getPacMan();
        ghostRed = grille.getGhostRed();
        ghostBlue = grille.getGhostBlue();

        ImageView[][] tab = new ImageView[columnCount][rowCount]; // tableau permettant de récupérer les cases graphiques lors du rafraichissement

        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }




        Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
            @Override
            public void update(Observable o, Object arg) {
                for (int i = 0; i < columnCount; i++) { // rafraichissement graphique
                    for (int j = 0; j < rowCount; j++) {
                        if (pacMan.getX() == i && pacMan.getY() == j) { // pacMan est à la position i, j => le dessiner
                            tab[i][j].setImage(pacmanImages[ (pacMan.getDirection()).ordinal()]);
                        }
                        else if(ghostRed.getX() == i && ghostRed.getY() == j){
                            tab[i][j].setImage(redGhostImages[0]);
                        }
                        else if(ghostBlue.getX() == i && ghostBlue.getY() == j){
                            tab[i][j].setImage(cyanGhostImages[0]);
                        }
                        else {
                            if (grille.getElement(i,j) instanceof Wall) {
                                tab[i][j].setImage(imgWall);
                            } else if(grille.getElement(i,j) instanceof Ground){
                                if (((Ground) grille.getElement(i,j)).getItem() instanceof  PacGum) {
                                    tab[i][j].setImage(imgSmallDot);
                                } else if(((Ground) grille.getElement(i,j)).getItem() instanceof  SuperPacGum) {
                                    tab[i][j].setImage(imgWhiteDot);
                                } else {
                                    tab[i][j].setImage(imgGround);
                                }
                            }
                        }
                    }
                }
            }
        };


        pacMan.addObserver(o);
        pacMan.start(); // on démarre pacMan
        ghostRed.start();
        ghostBlue.start();

        StackPane root = new StackPane();
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 800, 800);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            boolean keyRecognized = true;
            KeyCode code = key.getCode();
            Direction direction=Direction.SOUTH;
            if (code == KeyCode.LEFT) {
                direction=(Direction.WEST);
            } else if (code == KeyCode.RIGHT) {
                direction=(Direction.EAST);
            } else if (code == KeyCode.UP) {
                direction=(Direction.NORTH);
            } else if (code == KeyCode.DOWN) {
                direction=(Direction.SOUTH);
            }
            pacMan.setDirection(direction);
        });

        primaryStage.setTitle("Pac Man");
        primaryStage.setScene(scene);
        primaryStage.show();

        grid.requestFocus();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
