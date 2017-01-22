/**
 * Created by Furman on 21.01.2017.
 */

import java.io.File;
import java.util.Calendar;
import java.util.Date;


import CopyingFiles.*;
import Modes.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Furman\\Desktop\\test\\from\\вывы");
        File to = new File("C:\\Users\\Furman\\Desktop\\test\\to");
        CopyObject copy = new DirectoryCopyObject(file, to, Mode.INC, 5000);
        int i = 5;
        long start = System.currentTimeMillis();
        while (i > 0) {
            System.out.println(copy.copy());
            i--;
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }
        System.out.println(copy.upgrade(System.currentTimeMillis()-start));

    }

}
