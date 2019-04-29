package ViewController;

import Model.Direction;
import Model.SimplePacMan;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Main extends Application {

    public final int SIZE_X =20;
    public final int SIZE_Y = 20;

    private SimplePacMan spm;

    @Override
    public void start(Stage primaryStage) {
        spm = new SimplePacMan(SIZE_X, SIZE_Y); // initialisation du modèle;
        GridPane grid = new GridPane(); // création de la grille

        // Pacman.svg.png
        Image imPM = new Image("ressources/pacmanDown.gif"); // préparation des images
        Image imPMnorth = new Image("ressources/pacmanUp.gif"); // préparation des images
        Image imPMsouth = new Image("ressources/pacmanDown.gif"); // préparation des images
        Image imPMeast = new Image("ressources/pacmanRight.gif"); // préparation des images
        Image imPMwest = new Image("ressources/pacmanLeft.gif"); // préparation des images
        Image[] pacmanImages={imPMnorth,imPMeast,imPMsouth,imPMwest};
        Image imVide = new Image("ressources/ground.png");

        //img.setScaleY(0.01);
        //img.setScaleX(0.01);

        ImageView[][] tab = new ImageView[SIZE_X][SIZE_Y]; // tableau permettant de récupérer les cases graphiques lors du rafraichissement

        for (int i = 0; i < SIZE_X; i++) { // initialisation de la grille (sans image)
            for (int j = 0; j < SIZE_Y; j++) {
                ImageView img = new ImageView();

                tab[i][j] = img;
                grid.add(img, i, j);
            }

        }

        Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
            @Override
            public void update(Observable o, Object arg) {
                for (int i = 0; i < SIZE_X; i++) { // rafraichissement graphique
                    for (int j = 0; j < SIZE_Y; j++) {
                        if (spm.getX() == i && spm.getY() == j) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(pacmanImages[ (spm.getDirection()).ordinal()]);
                        } else {
                            tab[i][j].setImage(imVide);
                        }
                    }
                }
            }
        };


        spm.addObserver(o);
        spm.start(); // on démarre spm

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
            System.out.println(direction);
            spm.setDirection(direction);
        });

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();

        root.setOnKeyPressed(new EventHandler<KeyEvent>() { // on écoute le clavier


            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                if (event.isShiftDown()) {
                    spm.initXY(); // si on clique sur shift, on remet spm en haut à gauche
                }
            }
        });

        grid.requestFocus();



    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
