package sample;


import com.sun.org.apache.regexp.internal.RE;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import jdk.nashorn.internal.runtime.regexp.RegExp;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;
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
    public List<File> Folder = new ArrayList<File>();
    public List<File> FoundFiles = new ArrayList<File>();
    public List<File> datesList = new ArrayList<File>();
    public Double progressBar = 0.0;
    public Boolean headerDrop = false;
    public Boolean textFill = false;
    public Boolean dateDrop = false;
    //CheckBox ExactMatch = new CheckBox("Exact Match");

    CheckBox SubFolders = new CheckBox("Check Subfolder");
    CheckBox ParseThrough = new CheckBox("Parse Through");
    CheckBox FuzzyMatch = new CheckBox("Fuzzy Match");
    CheckBox Regex = new CheckBox("Regex");
    Button button = new Button();
    ProgressBar pb = new ProgressBar(progressBar);

    //RunnableClass rc = new RunnableClass();



    public getAllFilesFilter myFilter = new getAllFilesFilter();

    @Override public void start(Stage stage) {


        stage.setTitle("File Searcher");

        Group root = new Group();
        Scene scene = new Scene(root, 352, 245);
        scene.setFill(Color.web("#da3609"));
        HBox hbox = new HBox();

        Image himg = new Image("/sample/header.png");
        ImageView header = new ImageView(himg);

        VBox options = new VBox();



        TextField searchTerm = new TextField();
        searchTerm.setPromptText("Search Term");
        searchTerm.resize(searchTerm.getWidth(), searchTerm.getHeight());
        //CheckBox ExactMatch = new CheckBox("Exact Match");
       // ExactMatch.setSelected(true);
       /* CheckBox SubFolders = new CheckBox("Check Subfolder");
        CheckBox ParseThrough = new CheckBox("Parse Through");
        CheckBox FuzzyMatch = new CheckBox("Fuzzy Match");
        CheckBox Regex = new CheckBox("Regex");*/



        options.getChildren().addAll(searchTerm, SubFolders, ParseThrough, FuzzyMatch, Regex);

       // hbox.getChildren().addAll(header, name_files,dates);
        hbox.getChildren().addAll(header, options);
        VBox vbox = new VBox();
        vbox.getChildren().add(hbox);

       // pb.setBackground(Color.BLACK);
      //  pb
        pb.setMaxWidth(Double.MAX_VALUE);
        //pb.setProgress(.3);
        vbox.getChildren().add(pb);

        button.setMaxWidth(Double.MAX_VALUE);
        button.setDisable(true);
        button.setText("Start!");
        vbox.getChildren().add(button);


        searchTerm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(textFill==false){
                    progressBar = progressBar + .5;
                }
                pb.setProgress(progressBar);
                /* data dropped */
                textFill = true;
                if(progressBar==1)
                    button.setDisable(false);
                if(searchTerm.getText()!=null){
                    System.out.println("typing");
                }

            }
        });

        header.setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println(progressBar + ":" + pb.getProgress());
                /* data dropped */
                if(headerDrop==false){
                    progressBar = progressBar + .5;
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

                    System.out.println("found a folder");
                }

                    List<File> phil = db.getFiles();

                    Folder = phil;
                    System.out.println(phil.get(0).toString());

            }
        });

        header.setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {



                if (event.getGestureSource() != header) {

                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });




        button.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent f) {
                System.out.println(scene.getWidth());
                if(Folder.size()!=0 && (Folder.get(0).isDirectory())) {
                   // button.setText("Searching");
                    pb.setProgress(0.0);
                    progressBar = 0.0;
                    button.setDisable(true);
                    Finder(Folder.get(0), searchTerm.getText());

                    button.setText("Start!");



                }
                else{
                button.setText("Window Search Term Failed: Make Sure It's A Folder.");
                    button.setDisable(true);
                    progressBar = 0.0;
                    pb.setProgress(0.0);

            }

               headerDrop = false;
                textFill = false;
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
public void Finder(File root, String Term){

RunnableClass t = new RunnableClass(this, root, Term, SubFolders, ParseThrough, FuzzyMatch, Regex);

/*
    boolean isRunning = true;
    while(isRunning) {
        if(t.getName().contains("done")) {
            System.out.println("ooiubhdgflidfbhuifdhiufgdhuiogfdhui");
            isRunning = false;
        }
        while (!t.isAlive()) {
            Platform.runLater(() -> {
                htmler(Term);
                button.setText("Start!");
                t.setName("isdone");
            });

        }
    }
*/


t.start();




    }
    public void setText(int on, int of){
   button.setText("On file of " + on +  "of " + of);
   pb.setProgress(on/of);

    }
    public void setFoundFiles(List<File> FoundFiles, String Term){
        this.FoundFiles = FoundFiles;
        System.out.println("rteached");

        htmler(Term);
    }

    public void htmler (String Term) {
        Platform.runLater(() -> {
                    button.setText("Generating the Html File");
                });
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
               /* "@media print {\n" +
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
                "}\n" +*/
                "\n" +
                "\t</style>\n" +
                "\t\n" +
                "<body>\n" +
                /*"<script type='text/javascript'>\n"+
                "window.onload = function() { window.print(); }\n"+
                "</script>\n"+*/
                "<div class=‘table-title’>\n" +
                "<h3>";
        html = html + "Found Files with Search: " + Term;
        html = html + "</h3>\n" +
                "</div>\n" +
                "<table class=‘table-fill’>\n" +
                "<thead>\n" +
                "<tr>\n"+
                "<th class=‘text-left’>" + FoundFiles.size() + " Files Found" + "</th>\n";

        /*String datesAdd = "";
        for(int i= 0; i<FoundFiles.size(); i++) {
            datesAdd += "<th class=‘text-left’>" + FoundFiles.get(i) + "</th>\n";
        }
        html+=datesAdd;*/
        html+="</tr>\n" +
                "</thead>\n" +
                "<tbody class=‘table-hover’>\n";
        String namesAdd = "";

        for(int j= 0; j<FoundFiles.size(); j++) {
            String link = FoundFiles.get(j).toString();
            //link.replaceAll(" ", " ");
            //link.replace()
            System.out.println(link);
            namesAdd += "<tr>\n" +
                    "<td class=\"text-center\"><a href = " + link + "\">" + FoundFiles.get(j) + "</a></td>\n";
          /*  for(int r=FoundFiles.size()-1; r>0; r--){
                namesAdd += "<td class='text-center'> </td>\n";
            }*/
            namesAdd += "</tr>\n";
        }
        html+=namesAdd;
        html+="</tbody>\n" +
                "</table>\n" +
                "  \n" +
                "\n" +
                "  </body>";


        System.out.println("here");
        try {
            FileWriter fw = new FileWriter("Search Term " + Term + ".html", false);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw, true);
//write data to the stream
            out.println(html);
//close the stream
            out.close();

            File htmlFile = new File("Search Term " + Term + ".html");
            Desktop.getDesktop().browse(htmlFile.toURI());


        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        //System.out.println(html);

        Platform.runLater(() -> {
            pb.setProgress(0.0);
            progressBar = 0.0;
            button.setText("Start!");

    });
    }

    public void searchSub(File root, String Term){
        File[] sub = root.listFiles(myFilter);
        for (int i = sub.length - 1; i > -1; i--) {

            if (sub[i].getName().toString().contains(Term)) {
                FoundFiles.add(sub[i]);
                System.out.println(sub[i].getName().toString());
            }
        }

    }





    public static void main(String[] args) {
        Application.launch(args);
    }
}