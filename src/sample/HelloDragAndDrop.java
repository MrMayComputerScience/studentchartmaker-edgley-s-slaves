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

import org.apache.commons.io.*;

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
        stage.setTitle("Drag And Drop Attendance");

        Group root = new Group();
        Scene scene = new Scene(root, 600, 245);
        scene.setFill(Color.web("#da3609"));
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
       // pb.setBackground(Color.BLACK);
      //  pb
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
                if(headerList.size() == nameFilesList.size() && nameFilesList.size() == datesList.size()) {
                    button.setText("Html File Outputting");
                    for (int i = 0; i < headerList.size(); i++) {

                        packager(headerList.get(i), nameFilesList.get(i), datesList.get(i));
                    }
                }
                else{
                button.setText("Files Mismatch : Try again");


            }
                button.setDisable(true);
                pb.setProgress(0.0);
               headerDrop = false;
                nameDrop = false;
                dateDrop = false;
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
        Boolean one =false;
        //
        // Create a new Scanner object which will read the data from the
        // file passed in. To check if there are more line to read from it
        // we check by calling the scanner.hasNextLine() method. We then
        // read line one by one till all line is read.
        //
        try {
            Scanner scanner = new Scanner(header);
            if (scanner.hasNextLine() && !(one)) {
               headerName = scanner.nextLine();
               one = true;
            }
            while (scanner.hasNextLine() && one) {
                String line = scanner.nextLine();
                headerName = headerName + " " + line;
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
                System.out.println("here");
                String line = scanner1.nextLine();
                //System.out.println(line);
                final Pattern pattern = Pattern.compile("\\w+[,]\\s\\w+");
                final Matcher matcher = pattern.matcher(line);
                matcher.find();
                try {
                    line = matcher.group(0); // Prints String I want to extract
                }
                catch(Exception f)
                {
                    f.printStackTrace();
                }
                //String patternString = "\\w+[,]\\s\\w+\\s\\D/g";
                if("test, test".matches(".*[,].*"))
                {
                    System.out.println("matched comma");
                }
                System.out.println("this is line " + line);
                if(line.matches(".*[a-z].*") && line.matches(".*[,].*")){
                names.add(line);
                java.util.Collections.sort(names);

                }

            }
            for(int i = 0; i < names.size(); i++){
                String switchNames = names.get(i);
                System.out.println("test" + switchNames);
                String[] switchArray = switchNames.split(", ");
                System.out.println(switchArray.toString());
                System.out.println("1 " + switchArray[0]);
                System.out.println("2  " + switchArray[1]);
                switchNames = switchArray[1] + " " + switchArray[0];
                names.set(i,switchNames);
            }

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
            String first = "";
            Scanner scanner2 = new Scanner(Dates);
            while (scanner2.hasNextLine()) {
                String line = scanner2.nextLine();
                first = line;
                if(line.contains("#"))
                    break;
                //System.out.println(line);
                dates.add(line);
            }

            if(first.contains("#")){
                String num = first.substring(1,first.length());
                int x = Integer.parseInt(num);
                //System.out.println(x);
                for(int i = x; i>0; i--)
                {
                    dates.add("                              ");
                }
            }


        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //date
htmler(headerName,names,dates);





    }
   public void htmler(String header, List<String> names, List<String> dates)
   { dates.add(0,header);
       String html = "<html lang=‘en’>\n" +
           "<head>\n" +
           "\t<meta charset=‘utf-8’ />\n" +
           "\t<title>Hella lit</title>\n" +
           "\t<meta name=‘viewport’ content=‘initial-scale=1.0; maximum-scale=1.0; width=device-width;’>\n" +
           "</head>\n" +
           "<style>\n" +
           "\t@import url(https://fonts.googleapis.com/css?family=Roboto:400,500,700,300,100);\n" +
           "\n" +
           "body {\n" +
           "  background-color: #ed5140;\n" +
           "  font-family: ‘Roboto’, helvetica, arial, sans-serif;\n" +
           "  font-size: 16px;\n" +
           "  font-weight: 400;\n" +
           "  text-rendering: optimizeLegibility;\n" +
           "}\n" +
           "\n" +
           "div.table-title {\n" +
           "   display: block;\n" +
           "  margin: auto;\n" +
           "  max-width: 600px;\n" +
           "  padding:5px;\n" +
           "  width: 100%;\n" +
           "}\n" +
           "\n" +
           ".table-title h3 {\n" +
           "   color: #fafafa;\n" +
           "   font-size: 30px;\n" +
           "   font-weight: 400;\n" +
           "   font-style:normal;\n" +
           "   font-family: ‘Roboto’, helvetica, arial, sans-serif;\n" +
           "   text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n" +
           "   text-transform:uppercase;\n" +
           "}\n" +
           "\n" +
           "\n" +
           "/*** Table Styles **/\n" +
           "\n" +
           ".table-fill {\n" +
           "  background: white;\n" +
           "  border-radius:3px;\n" +
           "  border-collapse: collapse;\n" +
           "  max-height: 100px;\n" +
           "  margin: auto;\n" +
           "  max-width: 600px;\n" +
           "  min-width: 450px;\n" +
           "  padding:5px;\n" +
           "  width: 100%;\n" +
           "  box-shadow: 0 5px 10px rgba(0, 0, 0, 0.1);\n" +
           "  animation: float 5s infinite;\n" +
           "}\n" +
           " \n" +
           "th {\n" +
           "  color:#D5DDE5;;\n" +
           "  background:#1b1e24;\n" +
           "  border-bottom:4px solid #9ea7af;\n" +
           "  border-right: 1px solid #343a45;\n" +
           "  font-size:23px;\n" +
           "  font-weight: 100;\n" +
           "  padding:15px;\n" +
           "  text-align:left;\n" +
           "  text-shadow: 0 1px 1px rgba(0, 0, 0, 0.1);\n" +
           "  vertical-align:middle;\n" +
           "}\n" +
           "\n" +
           "th:first-child {\n" +
           "  border-top-left-radius:3px;\n" +
           "}\n" +
           " \n" +
           "th:last-child {\n" +
           "  border-top-right-radius:3px;\n" +
           "  border-right:none;\n" +
           "}\n" +
           "  \n" +
           "tr {\n" +
           "  border-top: 1px solid #C1C3D1;\n" +
           "  border-bottom-: 1px solid #C1C3D1;\n" +
           "  color:#666B85;\n" +
           "  font-size:16px;\n" +
           "  font-weight:normal;\n" +
           "  text-shadow: 0 1px 1px rgba(256, 256, 256, 0.1);\n" +
           "}\n" +
           " \n" +
           "tr:hover td {\n" +
           "  background:#4E5066;\n" +
           "  color:#FFFFFF;\n" +
           "  border-top: 1px solid #22262e;\n" +
           "}\n" +
           " \n" +
           "tr:first-child {\n" +
           "  border-top:none;\n" +
           "}\n" +
           "\n" +
           "tr:last-child {\n" +
           "  border-bottom:none;\n" +
           "}\n" +
           " \n" +
           "tr:nth-child(odd) td {\n" +
           "  background:#EBEBEB;\n" +
           "}\n" +
           " \n" +
           "tr:nth-child(odd):hover td {\n" +
           "  background:#4E5066;\n" +
           "}\n" +
           "\n" +
           "tr:last-child td:first-child {\n" +
           "  border-bottom-left-radius:3px;\n" +
           "}\n" +
           " \n" +
           "tr:last-child td:last-child {\n" +
           "  border-bottom-right-radius:3px;\n" +
           "}\n" +
           " \n" +
           "td {\n" +
           "  background:#FFFFFF;\n" +
           "  padding:5px;\n" +
           "  text-align:left;\n" +
           "  vertical-align:middle;\n" +
           "  font-weight:300;\n" +
           "  font-size:18px;\n" +
           "  text-shadow: -1px -1px 1px rgba(0, 0, 0, 0.1);\n" +
           "  border-right: 1px solid #C1C3D1;\n" +
           "}\n" +
           "\n" +
           "td:last-child {\n" +
           "  border-right: 0px;\n" +
           "}\n" +
           "\n" +
           "th.text-left {\n" +
           "  text-align: left;\n" +
           "}\n" +
           "\n" +
           "th.text-center {\n" +
           "  text-align: center;\n" +
           "}\n" +
           "\n" +
           "th.text-right {\n" +
           "  text-align: right;\n" +
           "}\n" +
           "\n" +
           "td.text-left {\n" +
           "  text-align: left;\n" +
           "}\n" +
           "\n" +
           "td.text-center {\n" +
           "  text-align: center;\n" +
           "}\n" +
           "\n" +
           "td.text-right {\n" +
           "  text-align: right;\n" +
           "}\n" +
               "@media print {\n" +
       "body {-webkit-print-color-adjust: exact;}\n" +
   "}\n" +
       "@page {\n" +
       "size:A4 landscape;\n" +
       "margin-left: 0px;\n" +
       "margin-right: 0px;\n" +
       "margin-top: 0px;\n" +
       "margin-bottom: 0px;\n" +
       "margin: 10;\n" +
       "-webkit-print-color-adjust: exact;\n" +
   "}\n" +
       "\n" +
           "\t</style>\n" +
           "\t\n" +
           "<body>\n" +
           "<div class=‘table-title’>\n" +
           "<h3>";
   html = html + header;
   html = html + "</h3>\n" +
           "</div>\n" +
           "<table class=‘table-fill’>\n" +
           "<thead>\n" +
           "<tr>\n";
   String datesAdd = "";
           for(int i= 0; i<dates.size(); i++) {
               datesAdd += "<th class=‘text-left’>" + dates.get(i) + "</th>\n";
           }
           html+=datesAdd;
           html+="</tr>\n" +
                   "</thead>\n" +
                   "<tbody class=‘table-hover’>\n";
       String namesAdd = "";

       for(int j= 0; j<names.size(); j++) {
           namesAdd += "<tr>\n" +
                   "<td class=\"text-center\">" + names.get(j) + "</td>\n";
                    for(int r=dates.size()-1; r>0; r--){
                        namesAdd += "<td class='text-center'> </td>\n";
                    }
                         namesAdd += "</tr>\n";
       }
       html+=namesAdd;
       html+="</tbody>\n" +
               "</table>\n" +
               "  \n" +
               "\n" +
               "  </body>";


       System.out.println("test");
       try {
           FileWriter fw = new FileWriter(header + ".html", false);
           BufferedWriter bw = new BufferedWriter(fw);
           PrintWriter out = new PrintWriter(bw, true);
//write data to the stream
           out.println(html);
//close the stream
           out.close();

           File htmlFile = new File(header + ".html");
           Desktop.getDesktop().browse(htmlFile.toURI());

       }
       catch(IOException e)
       {
           e.printStackTrace();
       }
   //System.out.println(html);

   }




    public static void main(String[] args) {
        Application.launch(args);
    }
}