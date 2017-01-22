/**
 * Created by Furman on 21.01.2017.
 */
import java.io.File;

import CopyingFiles.*;
import Modes.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Furman\\Desktop\\test\\from\\Talanty.docx");
        File to  = new File("C:\\Users\\Furman\\Desktop\\test\\to");
        copyObject copy = new FileCopyObject(file,to,Mode.DEC,10000);
        try {
            int i=3;
            while (i>=0) {
                System.out.println(copy.copy());
                Thread.sleep(copy.getTimeToCopy());
                i--;
            }
            System.out.println(copy.upgrade(2));
        }
        catch(InterruptedException e){}
    }
}
