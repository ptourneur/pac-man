package ViewController;

import Model.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
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

import javafx.scene.control.Button;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CyclicBarrier;

public class Main extends Application {

    private int rowCount;
    private int columnCount;

    private Grid grille;
    private PacMan pacMan;
    private static final int X_PACMAN = 9;
    private static final int Y_PACMAN = 15;
    private Ghost ghostRed;
    private static final int X_GHOSTRED = 9;
    private static final int Y_GHOSTRED = 7;
    private Ghost ghostCyan;
    private static final int X_GHOSTCYAN = 8;
    private static final int Y_GHOSTCYAN = 9;
    private Ghost ghostPink;
    private static final int X_GHOSTPINK = 9;
    private static final int Y_GHOSTPINK = 9;
    private Ghost ghostOrange;
    private static final int X_GHOSTORANGE = 10;
    private static final int Y_GHOSTORANGE = 9;


    public void startGame(Stage primaryStage){
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

        Image imgPinkGhostRight = new Image("ressources/pinkGhostRight.gif");
        Image imgPinkGhostLeft = new Image("ressources/pinkGhostLeft.gif");
        Image imgPinkGhostUp = new Image("ressources/pinkGhostUp.gif");
        Image imgPinkGhostDown = new Image("ressources/pinkGhostDown.gif");
        Image[] pinkGhostImages ={imgPinkGhostUp, imgPinkGhostRight, imgPinkGhostDown, imgPinkGhostLeft};

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

        CyclicBarrier cyclicBarrier = new CyclicBarrier(5 ,new AggregatorThread());
        pacMan = new PacMan(X_PACMAN, Y_PACMAN, grille,cyclicBarrier);
        ghostRed = new Ghost(X_GHOSTRED, Y_GHOSTRED, 1, grille,cyclicBarrier);
        ghostCyan = new Ghost(X_GHOSTCYAN, Y_GHOSTCYAN, 2, grille,cyclicBarrier);
        ghostPink = new Ghost(X_GHOSTPINK, Y_GHOSTPINK, 3, grille,cyclicBarrier);
        ghostOrange = new Ghost(X_GHOSTORANGE, Y_GHOSTORANGE, 4, grille,cyclicBarrier);


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
                            tab[i][j].setImage(pacmanImages[(pacMan.getDirection()).ordinal()]);
                        }
                        else if(ghostRed.getX() == i && ghostRed.getY() == j){
                            tab[i][j].setImage(redGhostImages[(ghostRed.getDirection()).ordinal()]);
                        }
                        else if(ghostCyan.getX() == i && ghostCyan.getY() == j){
                            tab[i][j].setImage(cyanGhostImages[(ghostCyan.getDirection()).ordinal()]);
                        }
                        else if(ghostPink.getX() == i && ghostPink.getY() == j){
                            tab[i][j].setImage(pinkGhostImages[(ghostPink.getDirection()).ordinal()]);
                        }
                        else if(ghostOrange.getX() == i && ghostOrange.getY() == j){
                            tab[i][j].setImage(orangeGhostImages[(ghostOrange.getDirection()).ordinal()]);
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
        ghostCyan.start();
        ghostPink.start();
        ghostOrange.start();



        final Image gameSidebar = new Image( "ressources/gameSidebar.png" ); //title screen image
        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(gameSidebar); //set the image of the title screen
        primaryStage.getIcons().add(gameSidebar); //stage icon

        StackPane root = new StackPane();
        root.getChildren().add(grid);
        root.getChildren().add(flashScreen_node);

        Scene scene = new Scene(root, 1000, 800);
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
    @Override
    public void start(Stage primaryStage) {

        final Image titleScreen = new Image( "ressources/backgroundTitle.png" ); //title screen image

        final ImageView flashScreen_node = new ImageView();
        flashScreen_node.setImage(titleScreen); //set the image of the title screen

        primaryStage.setTitle( "Super PacMan" );
        primaryStage.getIcons().add(titleScreen); //stage icon

        final double CANVAS_WIDTH = 1000;
        final double CANVAS_HEIGHT = 800;

        Group root = new Group();
        root.getChildren().addAll(flashScreen_node); //add the title screen to the root

        Button startGameButton = new Button();
        Image imageDecline = new Image("ressources/buttonStartgame.png");
        startGameButton.setGraphic(new ImageView(imageDecline));
        startGameButton.setLayoutX(400);
        startGameButton.setLayoutY(670);
        startGameButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startGame(primaryStage);
            }
        });

        root.getChildren().add(startGameButton);

        startGameButton.getStylesheets().add("ressources/startButtonCss.css");


        Scene theScene = new Scene( root, CANVAS_WIDTH, CANVAS_HEIGHT);
        primaryStage.setScene( theScene );
        primaryStage.show();


        startGame(primaryStage);




    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
