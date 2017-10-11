package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Demonstrates a drag-and-drop feature.
 */
public class HelloDragAndDrop extends Application {
    public List<File> headerList = new ArrayList<File>();
    public List<File> nameFilesList = new ArrayList<File>();
    public List<File> datesList = new ArrayList<File>();

    @Override public void start(Stage stage) {
        stage.setTitle("Hello Drag And Drop");

        Group root = new Group();
        Scene scene = new Scene(root, 600, 200);
        scene.setFill(Color.web("#0042ad"));

        final Text header = new Text(50, 100, "Header");
        header.setScaleX(2.0);
        header.setScaleY(2.0);

        final Text name_files = new Text(250, 100, "Name Files");
        name_files.setScaleX(2.0);
        name_files.setScaleY(2.0);

        final Text dates = new Text(450, 100, "Dates");
        dates.setScaleX(2.0);
        dates.setScaleY(2.0);

        dates.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                //System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {

                    //System.out.println("hasfiles");
                }
                try {
                    List<File> phil = db.getFiles();
                    FileInputStream fis;
                    fis = new FileInputStream(phil.get(0));
                    datesList= phil;
                    StringBuilder builder = new StringBuilder();
                    int ch;
                    while ((ch = fis.read()) != -1) {
                        builder.append((char) ch);
                    }

                    fis.close();

                    System.out.println(builder.toString());

                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

               /*
                boolean success = false;
                if (db.hasString()) {
                    target.setText(db.getString());
                    success = true;
                }
                */
                /* let the source know whether the string was successfully
                 * transferred and used */
                // event.setDropCompleted(success);

                //  event.consume();
            }
        });

        dates.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
               // System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != name_files) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });


        header.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                //System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {

                    System.out.println("hasfiles");
                }
                try {
                    List<File> phil = db.getFiles();
                    FileInputStream fis;
                    System.out.println(phil);
                    fis = new FileInputStream(phil.get(0));
                    headerList = phil;
                    StringBuilder builder = new StringBuilder();
                    int ch;
                    while ((ch = fis.read()) != -1) {
                        builder.append((char) ch);
                    }

                    fis.close();

                    System.out.println(builder.toString());

                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

               /*
                boolean success = false;
                if (db.hasString()) {
                    target.setText(db.getString());
                    success = true;
                }
                */
                /* let the source know whether the string was successfully
                 * transferred and used */
                // event.setDropCompleted(success);

                //  event.consume();
            }
        });

        header.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
               // System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != name_files) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });


        name_files.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                //System.out.println("onDragOver");

                /* accept it only if it is  not dragged from the same node
                 * and if it has a string data */
                if (event.getGestureSource() != name_files) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        name_files.setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
             //   System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != name_files &&
                        event.getDragboard().hasString()) {
                    name_files.setFill(Color.GREEN);
                }

                event.consume();
            }
        });

        name_files.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                name_files.setFill(Color.BLACK);

                event.consume();
            }
        });

        name_files.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                //System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {

                    System.out.println("hasfiles");
                }
                try {
                    List<File> phil = db.getFiles();
                    FileInputStream fis;
                    fis = new FileInputStream(phil.get(0));
                    nameFilesList = phil;
                    StringBuilder builder = new StringBuilder();
                    int ch;
                    while ((ch = fis.read()) != -1) {
                        builder.append((char) ch);
                    }

                    fis.close();

                    System.out.println(builder.toString());

                } catch (FileNotFoundException fnfe) {
                    fnfe.printStackTrace();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }

               /*
                boolean success = false;
                if (db.hasString()) {
                    target.setText(db.getString());
                    success = true;
                }
                */
                /* let the source know whether the string was successfully
                 * transferred and used */
                // event.setDropCompleted(success);

                //  event.consume();
            }
        });



        root.getChildren().add(header);
        root.getChildren().add(name_files);
        root.getChildren().add(dates);

        stage.setScene(scene);
        stage.show();
    }
/*
    public void packager(File header, File nameFiles, File Dates) throws IOException {
        //header
        FileInputStream hfis;
        hfis = new FileInputStream(header);

        StringBuilder hbuilder = new StringBuilder();
        int ch;
        while ((ch = hfis.read()) != -1) {
            hbuilder.append((char) ch);
        }

        hfis.close();
String headerName = hbuilder.toString();
        System.out.println(hbuilder.toString());
        //header

        //nameFiles
        try {
            //
            // Create a new Scanner object which will read the data from the
            // file passed in. To check if there are more line to read from it
            // we check by calling the scanner.hasNextLine() method. We then
            // read line one by one till all line is read.
            //
            Scanner scanner = new Scanner(nameFiles);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
        //nameFiles

        //date
        FileInputStream dfis;
        dfis = new FileInputStream(Dates);

        StringBuilder dbuilder = new StringBuilder();
        int dch;
        while ((dch = dfis.read()) != -1) {
            dbuilder.append((char) dch);
        }

        dfis.close();

        System.out.println(dbuilder.toString());
        //date
//Tableviewer(headerName, )




    }
    */

    public static void main(String[] args) {
        Application.launch(args);
    }
}