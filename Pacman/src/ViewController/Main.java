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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class Main extends Application {

    public int SIZE_X;
    public int SIZE_Y;

    private SimplePacMan spm;

    @Override
    public void start(Stage primaryStage) {
        //spm = new SimplePacMan(SIZE_X, SIZE_Y); // initialisation du modèle;
        GridPane grid = new GridPane(); // création de la grille

        Image imgPMRight = new Image("ressources/pacmanRight.gif");
        Image imgPMLeft = new Image("ressources/pacmanLeft.gif");
        Image imgPMUp = new Image("ressources/pacmanUp.gif");
        Image imgPMDown = new Image("ressources/pacmanDown.gif");
        Image imgGhost1 = new Image("ressources/ghost1.gif");
        Image imgGhost2 = new Image("ressources/ghost2.gif");
        Image imgGhost3 = new Image("ressources/ghost3.gif");
        Image imgSmallDot = new Image("ressources/smalldot.png");
        Image imgWhiteDot = new Image("ressources/whitedot.png");
        Image imgWall = new Image("ressources/wall.png");

        File file = new File("C:\\Users\\Epulapp\\Documents\\IdeaProjects\\pac-man\\Pacman\\src\\niveaux\\level1.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int rowCount = 0, columnCount = 0;
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
        ImageView[][] tab = new ImageView[rowCount][columnCount]; // tableau permettant de récupérer les cases graphiques lors du rafraichissement
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
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
        int pacmanRow = 0;
        int pacmanColumn = 0;
        int ghost1Row = 0;
        int ghost1Column = 0;
        int ghost2Row = 0;
        int ghost2Column = 0;
        while(scanner2.hasNextLine()){
            int column = 0;
            String line= scanner2.nextLine();
            Scanner lineScanner = new Scanner(line);
            while (lineScanner.hasNext()){
                String value = lineScanner.next();
                switch (value) {
                    case "W":
                        tab[row][column].setImage(imgWall);
                        break;
                    case "S":
                        tab[row][column].setImage(imgSmallDot);
                        break;
                    case "B":
                        tab[row][column].setImage(imgWhiteDot);
                        break;
                    case "1":
                        tab[row][column].setImage(imgPMLeft);
                        break;
                    case "2":
                        tab[row][column].setImage(imgWall);
                        break;
                    case "P":
                        tab[row][column].setImage(imgPMRight);
                        break;
                    default:
                        tab[row][column].setImage(imgPMRight);
                }
                column++;
            }
            row++;
        }





        Observer o =  new Observer() { // l'observer observe l'obervable (update est exécuté dès notifyObservers() est appelé côté modèle )
            @Override
            public void update(Observable o, Object arg) {
                /*for (int i = 0; i < SIZE_X; i++) { // rafraichissement graphique
                    for (int j = 0; j < SIZE_Y; j++) {
                        if (spm.getX() == i && spm.getY() == j) { // spm est à la position i, j => le dessiner
                            tab[i][j].setImage(imPM);
                        } else {
                            tab[i][j].setImage(imVide);
                        }
                    }
                }*/
            }
        };




        StackPane root = new StackPane();
        root.getChildren().add(grid);

        Scene scene = new Scene(root, 300, 250);


        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.ENTER) {
                System.out.println("You pressed enter");
            }
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
