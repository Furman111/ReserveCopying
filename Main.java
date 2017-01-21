/**
 * Created by Furman on 21.01.2017.
 */
import java.io.File;
import java.util.GregorianCalendar;

import CopyingFiles.*;
import Modes.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Furman\\Desktop\\test\\from\\Talanty.docx");
        File to  = new File("C:\\Users\\Furman\\Desktop\\test\\to");
        copyObject copy = new FileCopyObject(file,to,new GregorianCalendar());
    }
}
