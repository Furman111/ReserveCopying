/**
 * Created by Furman on 21.01.2017.
 */

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import CopyingFiles.*;
import Modes.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Furman\\Desktop\\test\\from\\вывы");
        File to = new File("C:\\Users\\Furman\\Desktop\\test\\to");
        CopyObject copy = new DirectoryCopyObject(file, to, Mode.DEC, 10000);
        int i = 2;
        while (i > 0) {
            System.out.println(copy.copy());
            i--;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
            }
        }
        copy.delete();
    }
}
