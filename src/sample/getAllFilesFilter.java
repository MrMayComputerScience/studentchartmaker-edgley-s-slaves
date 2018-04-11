package sample;
import java.io.*;
public class getAllFilesFilter implements FileFilter{
    @Override
    public boolean accept(File pathname) {
       // System.out.println(pathname);
        return true;
    }
}
