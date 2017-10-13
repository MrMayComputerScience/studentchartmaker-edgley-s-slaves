package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Demonstrates a drag-and-drop feature.
 */
public class HelloDragAndDrop extends Application {
    public List<File> headerList = new ArrayList<File>();
    public List<File> nameFilesList = new ArrayList<File>();
    public List<File> datesList = new ArrayList<File>();
    public Double progressBar = 0.0;
    public Boolean headerDrop = false;
    public Boolean nameDrop = false;
    public Boolean dateDrop = false;

    @Override public void start(Stage stage) {
        stage.setTitle("Hello Drag And Drop");

        Group root = new Group();
        Scene scene = new Scene(root, 600, 250);
        scene.setFill(Color.web("#0042ad"));
        HBox hbox = new HBox();

        Image himg = new Image("/sample/header.png");
        ImageView header = new ImageView(himg);

      //  final Text header = new Text("Header");
/*        header.setScaleX(2.0);
        header.setScaleY(2.0);*/

        Image nimg = new Image("/sample/name_files.png");
        ImageView name_files = new ImageView(nimg);
       // final Text name_files = new Text("Name Files");
/*        name_files.setScaleX(2.0);
        name_files.setScaleY(2.0);*/

        Image dimg = new Image("/sample/dates.png");
        ImageView dates = new ImageView(dimg);
 //       final Text dates = new Text("Dates");
/*        dates.setScaleX(2.0);
        dates.setScaleY(2.0);*/

        hbox.getChildren().addAll(header, name_files,dates);
        VBox vbox = new VBox();
        vbox.getChildren().add(hbox);
        ProgressBar pb = new ProgressBar(progressBar);
        pb.setMaxWidth(Double.MAX_VALUE);
        //pb.setProgress(.3);
        vbox.getChildren().add(pb);
        Button button = new Button("Start!");
        button.setMaxWidth(Double.MAX_VALUE);
        button.setDisable(true);
        vbox.getChildren().add(button);

        dates.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                if(dateDrop==false){
                    progressBar = progressBar + .3;
                }
                pb.setProgress(progressBar);
                /* data dropped */
                dateDrop = true;
                if(progressBar==1)
                    button.setDisable(false);
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
                if(headerDrop==false){
                    progressBar = progressBar + .2;
                }
                pb.setProgress(progressBar);
                /* data dropped */
                headerDrop = true;
                if(progressBar==1)
                    button.setDisable(false);
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
                  //  name_files.setFill(Color.GREEN);
                }

                event.consume();
            }
        });

        name_files.setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
                //name_files.setFill(Color.BLACK);

                event.consume();
            }
        });

        name_files.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                if(nameDrop==false){
                    progressBar = progressBar + .5;
                }
                pb.setProgress(progressBar);
                /* data dropped */
                nameDrop = true;
                if(progressBar==1)
                    button.setDisable(false);
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
        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent f) {

            packager(headerList.get(0),nameFilesList.get(0),datesList.get(0));


            }
        });


       // root.getChildren().add(header);
       // root.getChildren().add(name_files);
        //root.getChildren().add(dates);
        root.getChildren().add(vbox);

        stage.setScene(scene);
        stage.show();
    }


    public void packager(File header, File nameFiles, File Dates) {
        //header
        String headerName = "";
        //
        // Create a new Scanner object which will read the data from the
        // file passed in. To check if there are more line to read from it
        // we check by calling the scanner.hasNextLine() method. We then
        // read line one by one till all line is read.
        //
        try {
            Scanner scanner = new Scanner(header);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                headerName = headerName + line;
            }
        }
        catch(Exception r){

        }
        //header

        //nameFiles
        List<String> names = new ArrayList<>();
        try {
            //
            // Create a new Scanner object which will read the data from the
            // file passed in. To check if there are more line to read from it
            // we check by calling the scanner.hasNextLine() method. We then
            // read line one by one till all line is read.
            //
            Scanner scanner1 = new Scanner(nameFiles);
            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine();
                System.out.println(line);
                final Pattern pattern = Pattern.compile("\\w+[,]\\s\\w+\\s\\D/g");
                final Matcher matcher = pattern.matcher(line);
                matcher.find();
                line = matcher.group(1); // Prints String I want to extract
                //String patternString = "\\w+[,]\\s\\w+\\s\\D/g";

                names.add(line);
            }
            java.util.Collections.sort(names);
            System.out.println(names);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        //nameFiles

        //date
        List<String> dates = new ArrayList<>();
        try {
            //
            // Create a new Scanner object which will read the data from the
            // file passed in. To check if there are more line to read from it
            // we check by calling the scanner.hasNextLine() method. We then
            // read line one by one till all line is read.
            //
            Scanner scanner2 = new Scanner(nameFiles);
            while (scanner2.hasNextLine()) {
                String line = scanner2.nextLine();
                System.out.println(line);
                dates.add(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //date
//Tableviewer(headerName, )




    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}