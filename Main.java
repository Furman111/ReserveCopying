/**
 * Created by Furman on 21.01.2017.
 */

import java.io.File;
import  Modes.Mode;
import CopyingFiles.*;

import filesystemprocess.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("C:\\Users\\Furman\\Desktop\\test\\from\\Экономика");
        File to = new File("C:\\Users\\Furman\\Desktop\\test\\to");
        CopyObject copy = new DirectoryCopyObject(file, to, Mode.INC, 5000);
        int i = 5;
        while (i > 0) {
            System.out.println(copy.copy(System.currentTimeMillis()));
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
            }
            i--;
        }
    }
}
