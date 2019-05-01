package ViewController;

import Model.*;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Main extends Application {

    public int SIZE_X;
    public int SIZE_Y;
    public int rowCount;
    public int columnCount;

    private PacMan spm;
    private GridElement[][] grille;

    @Override
    public void start(Stage primaryStage) {
        spm = new PacMan(); // initialisation du modèle;
        GridPane grid = new GridPane(); // création de la grille

        Image imgPMRight = new Image("ressources/pacmanRight.gif");
        Image imgPMLeft = new Image("ressources/pacmanLeft.gif");
        Image imgPMUp = new Image("ressources/pacmanUp.gif");
        Image imgPMDown = new Image("ressources/pacmanDown.gif");
        Image[] pacmanImages ={imgPMUp,imgPMRight,imgPMDown,imgPMLeft};

        Image imgGhost1Right = new Image("ressources/ghost1Right.jpg");
        Image imgGhost1Left = new Image("ressources/ghost1Left.jpg");
        Image[] ghost1Images ={imgGhost1Right, imgGhost1Left};
        Image imgGhost2Right = new Image("ressources/ghost2Right.jpg");
        Image imgGhost2Left = new Image("ressources/ghost2Left.jpg");
        Image[] ghost2Images ={imgGhost2Right, imgGhost2Left};
        Image imgGhost3Right = new Image("ressources/ghost3Right.jpg");
        Image imgGhost3Left = new Image("ressources/ghost3Left.jpg");
        Image[] ghost3Images ={imgGhost3Right, imgGhost3Left};

        Image imgSmallDot = new Image("ressources/smalldot.png");
        Image imgWhiteDot = new Image("ressources/whitedot.png");
        Image imgWall = new Image("ressources/wall.png");
        Image imgGround = new Image("ressources/ground.png");

        File file = new File("niveaux/level1.txt");

        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
         rowCount = 0;
         columnCount = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()) {
                lineScanner.next();
                columnCount++;
            }
            rowCount++;
        }
        columnCount = columnCount/rowCount;
        ImageView[][] tab = new ImageView[columnCount][rowCount]; // tableau permettant de récupérer les cases graphiques lors du rafraichissement
        grille = new GridElement[columnCount][rowCount];
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                ImageView img = new ImageView();
                tab[i][j] = img;
                grid.add(img, i, j);
            }
        }

        Scanner scanner2 = null;
        try {
            scanner2 = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int row = 0;
        while(scanner2.hasNextLine()){
            int column = 0;
            String line= scanner2.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()){
                String value = lineScanner.next();
                switch (value) {
                    case "W":
                        grille[column][row] = new Wall();
                        tab[column][row].setImage(imgWall);
                        break;
                    case "S":
                        grille[column][row] = new Ground(new PacGum());
                        tab[column][row].setImage(imgSmallDot);
                        break;
                    case "B":
                        grille[column][row] = new Ground(new SuperPacGum());
                        tab[column][row].setImage(imgWhiteDot);
                        break;
                    case "E":
                        grille[column][row] = new Ground();
                        tab[column][row].setImage(imgGround);
                        break;
                    case "1":
                        grille[column][row] = new Ghost(1);
                        tab[column][row].setImage(imgGhost1Right);
                        break;
                    case "2":
                        grille[column][row] = new Ghost(2);
                        tab[column][row].setImage(imgGhost2Right);
                        break;
                    case "P":
                        grille[column][row] = new PacMan();
                        tab[column][row].setImage(imgPMRight);
                        break;
                    default:
                        grille[column][row] = new Wall();
                        tab[column][row].setImage(imgWall);
                }
                column++;
            }
            row++;
        }

        Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
            @Override
            public void update(Observable o, Object arg) {
                for (int i = 0; i < columnCount; i++) { // rafraichissement graphique
                    for (int j = 0; j < rowCount; j++) {
                        if (spm.getX() == i && spm.getY() == j) { // spm est à la position i, j => le dessiner
                            System.out.println(spm.getX()+" "+spm.getY());
                            tab[i][j].setImage(pacmanImages[ (spm.getDirection()).ordinal()]);
                        } else {
                            if (grille[i][j] instanceof Wall) {
                                tab[i][j].setImage(imgWall);
                            } else if(grille[i][j] instanceof Ground){
                                if (((Ground) grille[i][j]).getItem() instanceof  PacGum) {
                                    tab[i][j].setImage(imgSmallDot);
                                } else if(((Ground) grille[i][j]).getItem() instanceof  SuperPacGum) {
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

        primaryStage.setTitle("Pac Man");
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
