package ViewController;

import Model.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.control.Button;
import javafx.util.Duration;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main extends Application {

    private int rowCount;
    private int columnCount;

    private Grid grille;
    private PacMan pacMan;
    private static final int X_PACMAN = 9;
    private static final int Y_PACMAN = 3;
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



    private Label timerLabel = new Label();
    private Label scoreLabel = new Label();
    private Integer timeSeconds = 0;
    private Integer timeMinutes = 0;

    public void startGameOver(Stage primaryStage) {


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                StackPane root = new StackPane();
                Scene scene = new Scene(root, 1000, 800);

                final Image gameOverScreen = new Image( "ressources/backgroundGameover.png" ); //title screen image
                final ImageView flashScreen_node = new ImageView();
                flashScreen_node.setImage(gameOverScreen); //set the image of the title screen

                root.getChildren().addAll(flashScreen_node); //add the title screen to the root
                primaryStage.setScene(scene);
                primaryStage.getIcons().add(gameOverScreen); //stage icon
                primaryStage.setResizable(false);
                primaryStage.show();
                root.requestFocus();
            }
        });

    }

    public void checkIfDead(Ghost ghost,PacMan pacman,Stage primaryStage){

        if(ghost.getX() == pacman.getX() && ghost.getY() == pacman.getY()){
            startGameOver(primaryStage);
        }
    }
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


        Image imgBlueGhost = new Image("ressources/blueGhost.gif");

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

        ArrayList<Ghost> ghosts = new ArrayList<Ghost>();
        ghosts.add(ghostRed);
        ghosts.add(ghostCyan);
        ghosts.add(ghostPink);
        ghosts.add(ghostOrange);

        grille.setPacman(pacMan);
        grille.setGhosts(ghosts);
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
                            System.out.println("GX: "+ghostRed.getX()+" GY: "+ghostRed.getY()+"  -  PX: "+pacMan.getX()+ "PY: "+pacMan.getY());
                            tab[i][j].setImage(ghostRed.getScared() ? imgBlueGhost : redGhostImages[(ghostRed.getDirection()).ordinal()]);
                            checkIfDead(ghostRed,pacMan,primaryStage);
                        }
                        else if(ghostCyan.getX() == i && ghostCyan.getY() == j){
                            tab[i][j].setImage(ghostCyan.getScared() ? imgBlueGhost : cyanGhostImages[(ghostCyan.getDirection()).ordinal()]);
                            checkIfDead(ghostCyan,pacMan,primaryStage);
                        }
                        else if(ghostPink.getX() == i && ghostPink.getY() == j){
                            tab[i][j].setImage(ghostPink.getScared() ? imgBlueGhost : pinkGhostImages[(ghostPink.getDirection()).ordinal()]);
                            checkIfDead(ghostPink,pacMan,primaryStage);
                        }
                        else if(ghostOrange.getX() == i && ghostOrange.getY() == j){
                            tab[i][j].setImage(ghostOrange.getScared() ? imgBlueGhost : orangeGhostImages[(ghostOrange.getDirection()).ordinal()]);
                            checkIfDead(ghostOrange,pacMan,primaryStage);
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
        ghostRed.start();
        ghostCyan.start();
        ghostPink.start();
        ghostOrange.start();
        pacMan.start(); // on démarre pacMan








        final Image gameSidebar = new Image( "ressources/gameSidebar.png" ); //title screen image
        final ImageView flashScreen_node = new ImageView();
        StackPane root = new StackPane();
        flashScreen_node.setImage(gameSidebar); //set the image of the title screen
        primaryStage.getIcons().add(gameSidebar); //stage icon




        // Configure the Label
        scoreLabel.setText((grille.getPacman().getNbEatenPacgum())*10+ "   ");
        scoreLabel.setTextFill(Color.RED);
        scoreLabel.setStyle("-fx-font-size: 3em;");
        // Configure the Label
        timerLabel.setText("00:00"+ "      ");
        timerLabel.setTextFill(Color.BLUE);
        timerLabel.setStyle("-fx-font-size: 3em;");

        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis( 1000 ),
                        event -> {
                            timeSeconds++;
                            if(timeSeconds>=60){
                                timeMinutes++;
                                timeSeconds=0;
                            }

                            String timeToDisplay="";
                            if(timeMinutes>=10){
                                timeToDisplay+=timeMinutes;
                            }else{
                                timeToDisplay+="0"+timeMinutes;
                            }
                            timeToDisplay+=":";
                            if(timeSeconds>=10){
                                timeToDisplay+=timeSeconds;
                            }else{
                                timeToDisplay+="0"+timeSeconds;
                            }

                            grille.incrementInternalTimer();
                            timerLabel.setText( timeToDisplay+"      " );
                            scoreLabel.setText((grille.getPacman().getNbEatenPacgum())*10+ "   ");
                            if(timeSeconds==5){
                                ghostRed.setMoveable(true);

                            }else if(timeSeconds==10){
                                ghostCyan.setMoveable(true);
                            }else if(timeSeconds==15){
                                ghostPink.setMoveable(true);
                            }else if(timeSeconds==20){
                                ghostOrange.setMoveable(true);
                            }

                        }
                )
        );
        timeline.setCycleCount( Animation.INDEFINITE );
        timeline.play();

        grid.add(timerLabel,1,1);

        root.getChildren().add(grid);

        root.getChildren().add(flashScreen_node);

        StackPane stackpane = new StackPane();
        StackPane.setAlignment(timerLabel, Pos.TOP_RIGHT);
        StackPane.setAlignment(scoreLabel, Pos.BOTTOM_RIGHT);

        stackpane.getChildren().addAll(timerLabel,scoreLabel);

        root.getChildren().add(stackpane);



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
        primaryStage.setResizable(false);
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


        //startGame(primaryStage);




    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
