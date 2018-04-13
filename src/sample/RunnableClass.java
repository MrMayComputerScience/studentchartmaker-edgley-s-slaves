package sample;

import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RunnableClass extends Thread{
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
    Button button = new Button("dfgdfgfdg!");
    File root;
    String term;
    sample.HelloDragAndDrop stuff;
    public getAllFilesFilter myFilter = new getAllFilesFilter();
    public RunnableClass(sample.HelloDragAndDrop stuff, File root, String term, CheckBox SubFolders, CheckBox ParseThrough, CheckBox FuzzyMatch, CheckBox Regex){
        this.stuff = stuff; this.root = root; this.term = term; this.SubFolders = SubFolders; this.ParseThrough = ParseThrough; this.FuzzyMatch = FuzzyMatch; this.Regex = Regex;
    }
    public void run()
    {

        searchRoot(root, term);
        System.out.println("ran");
        stuff.setFoundFiles(FoundFiles, term);
    }
public void searchRoot(File root, String Term){
    //System.out.println("came here");
    //Gets files with Exact Match - Does not go through sub
    if(!SubFolders.isSelected() && !ParseThrough.isSelected() && !FuzzyMatch.isSelected() &&!Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        for (int i = sub.length - 1; i > -1; i--) {
            final int r = i;
            Platform.runLater(() -> {
                System.out.println("On file of " + r +  "of " + sub.length);
                stuff.setText(sub.length - r, sub.length);
            });
            System.out.println(sub[i].getPath());
            if (sub[i].getName().toString().contains(Term)) {
                FoundFiles.add(sub[i]);

                System.out.println(sub[i].getName().toString());
            }
        }
    }


//Gets files with Exact Match - Goes Through Sub
    if(SubFolders.isSelected() && !ParseThrough.isSelected() && !FuzzyMatch.isSelected() &&!Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        // System.out.println( sub.length);
        if(sub != null)
            for (int i = sub.length - 1; i > -1; i--) {
                final int r = i;
                Platform.runLater(() -> {

                    stuff.setText(sub.length - r, sub.length);
                });
                if (sub[i].isDirectory())
                    searchRoot(sub[i], Term);


                if (sub[i].getName().toString().contains(Term)) {
//                    button.setText(i + "");
                    FoundFiles.add(sub[i]);
                    System.out.println(sub[i].getName().toString());
                }
            }

    }

    //Gets files with Fuzzy - Does not go through sub
    if(!SubFolders.isSelected() && !ParseThrough.isSelected() && FuzzyMatch.isSelected() &&!Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        for (int i = sub.length - 1; i > -1; i--) {
            final int r = i;
            Platform.runLater(() -> {

                stuff.setText(sub.length - r, sub.length);
            });

            if (StringUtils.indexOfDifference(new String[]{sub[i].getName().toString(), Term}) < 5) {
                FoundFiles.add(sub[i]);
                System.out.println(sub[i].getName().toString());
            }
        }
    }

    //Gets files with Fuzzy - Goes Through Sub
    if(SubFolders.isSelected() && !ParseThrough.isSelected() && FuzzyMatch.isSelected() &&!Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        if(sub != null)
            for (int i = sub.length - 1; i > -1; i--) {
                final int r = i;
                Platform.runLater(() -> {
                   // System.out.println("On file of " + r +  "of " + sub.length);
                    stuff.setText(sub.length - r, sub.length);

                });
                if(sub[i].isDirectory())
                    searchRoot(sub[i], Term);

                if (StringUtils.indexOfDifference(new String[]{sub[i].getName().toString(), Term}) < Term.length()/2) {
                    FoundFiles.add(sub[i]);
                    System.out.println("this"+sub[i].getName().toString());
                }
            }
    }


//Gets files with Regex - Does not go through sub
    if(!SubFolders.isSelected() && !ParseThrough.isSelected() && Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        Pattern pattern = Pattern.compile(Term);
        Matcher m;
        for (int i = sub.length - 1; i > -1; i--) {
            final int r = i;
            Platform.runLater(() -> {

                stuff.setText(sub.length - r, sub.length);
            });
            m=pattern.matcher(sub[i].getName().toString());

            if (m.find()) {
                FoundFiles.add(sub[i]);
                System.out.println(sub[i].getName().toString());

            }
        }
    }

//Gets files with Regex - Go throught sub
    if(SubFolders.isSelected() && !ParseThrough.isSelected() && Regex.isSelected() ) {
        // System.out.println("worked");
        File[] sub = root.listFiles(myFilter);
        Pattern pattern = Pattern.compile(Term);
        Matcher m;
        if(sub != null)
            for (int i = sub.length - 1; i > -1; i--) {
                final int r = i;
                Platform.runLater(() -> {

                    stuff.setText(sub.length - r, sub.length);
                });
                if(sub[i].isDirectory())
                    searchRoot(sub[i], Term);
                m=pattern.matcher(sub[i].getName().toString());
                //System.out.println(sub[i].getName().toString());

                if (m.find()) {
                    FoundFiles.add(sub[i]);
                    System.out.println("Worked" + sub[i].getName().toString());

                }
            }
    }


//Gets files with Exact Match - Parse Through - Does not go through sub
    if(!SubFolders.isSelected() && ParseThrough.isSelected() && !FuzzyMatch.isSelected() &&!Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        for (int i = sub.length - 1; i > -1; i--) {
            final int r = i;
            Platform.runLater(() -> {

                stuff.setText(sub.length - r, sub.length);
            });
            LineIterator it = null;
            if(!sub[i].isDirectory()) {
                try {
                    it = FileUtils.lineIterator(sub[i], "UTF-8");
                    try {

                        while (it.hasNext()) {
                            String line = it.nextLine();
                            if (line.contains(Term)) {
                                FoundFiles.add(sub[i]);
                                System.out.println(sub[i].getName().toString());
                            }
                        }
                    } finally {
                        it.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }


    //Gets files with Exact Match - Parse Through - Go Trhough Sub
    if(SubFolders.isSelected() && ParseThrough.isSelected() && !FuzzyMatch.isSelected() &&!Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        if(sub != null)
            for (int i = sub.length - 1; i > -1; i--) {
                final int r = i;
                Platform.runLater(() -> {

                    stuff.setText(sub.length - r, sub.length);
                });
                LineIterator it = null;
                if(sub[i].isDirectory())
                    searchRoot(sub[i], Term);
                if(!sub[i].isDirectory()) {
                    try {
                        it = FileUtils.lineIterator(sub[i], "UTF-8");
                        try {

                            while (it.hasNext()) {
                                String line = it.nextLine();
                                if (line.contains(Term)) {
                                    FoundFiles.add(sub[i]);
                                    System.out.println(sub[i].getName().toString());
                                }
                            }
                        } finally {
                            it.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
    }

    //Gets files with Fuzzy Match - Parse Through - Does not go through sub
    if(!SubFolders.isSelected() && ParseThrough.isSelected() && FuzzyMatch.isSelected() &&!Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        for (int i = sub.length - 1; i > -1; i--) {
            final int r = i;
            Platform.runLater(() -> {

                stuff.setText(sub.length - r, sub.length);
            });
            LineIterator it = null;
            if(!sub[i].isDirectory()) {
                try {
                    it = FileUtils.lineIterator(sub[i], "UTF-8");
                    try {

                        while (it.hasNext()) {
                            String line = it.nextLine();

                            if (StringUtils.indexOfDifference(new String[]{line, Term}) < Term.length()/2) {
                                FoundFiles.add(sub[i]);
                                System.out.println(sub[i].getName().toString());
                            }
                        }
                    } finally {
                        it.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    //Gets files with Fuzzy Match - Parse Through - Goes through sub
    if(SubFolders.isSelected() && ParseThrough.isSelected() && FuzzyMatch.isSelected() &&!Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        if(sub != null)
            for (int i = sub.length - 1; i > -1; i--) {final int r = i;
                Platform.runLater(() -> {

                    stuff.setText(sub.length - r, sub.length);
                });

                LineIterator it = null;
                if(sub[i].isDirectory())
                    searchRoot(sub[i], Term);
                if(!sub[i].isDirectory()) {
                    try {
                        it = FileUtils.lineIterator(sub[i], "UTF-8");
                        try {

                            while (it.hasNext()) {
                                String line = it.nextLine();

                                if (StringUtils.indexOfDifference(new String[]{line, Term}) < Term.length()/2) {
                                    FoundFiles.add(sub[i]);
                                    System.out.println(sub[i].getName().toString());
                                }
                            }
                        } finally {
                            it.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
    }



    //Gets files with Regex Match - Parse Through - Does not go through sub
    if(!SubFolders.isSelected() && ParseThrough.isSelected() && Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        Pattern pattern = Pattern.compile(Term);
        Matcher m;
        for (int i = sub.length - 1; i > -1; i--) {final int r = i;
            Platform.runLater(() -> {

                stuff.setText(sub.length - r, sub.length);
            });

            LineIterator it = null;
            if(!sub[i].isDirectory()) {
                try {
                    it = FileUtils.lineIterator(sub[i], "UTF-8");
                    try {

                        while (it.hasNext()) {
                            String line = it.nextLine();
                            m=pattern.matcher(line);
                            if (m.find()) {
                                FoundFiles.add(sub[i]);
                                System.out.println(sub[i].getName().toString());
                            }
                        }
                    } finally {
                        it.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }
    }



    //Gets files with Regex Match - Parse Through -go through sub
    if(!SubFolders.isSelected() && ParseThrough.isSelected() && Regex.isSelected() ) {
        File[] sub = root.listFiles(myFilter);
        Pattern pattern = Pattern.compile(Term);
        Matcher m;
        if(sub != null)
            for (int i = sub.length - 1; i > -1; i--) {
                final int r = i;
                Platform.runLater(() -> {

                    stuff.setText(sub.length - r, sub.length);
                });
                LineIterator it = null;
                if(sub[i].isDirectory())
                    searchRoot(sub[i], Term);
                if(!sub[i].isDirectory()) {
                    try {
                        it = FileUtils.lineIterator(sub[i], "UTF-8");
                        try {

                            while (it.hasNext()) {
                                String line = it.nextLine();
                                m=pattern.matcher(line);
                                if (m.find()) {
                                    FoundFiles.add(sub[i]);
                                    System.out.println(sub[i].getName().toString());
                                }
                            }
                        } finally {
                            it.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }
    }
}

}
