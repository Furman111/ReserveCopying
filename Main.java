/**
 * Created by Furman on 21.01.2017.
 */
import java.io.File;

import CopyingFiles.*;
import Modes.*;

public class Main {
    public static void main(String[] args) {
        File file = new File("E:\\SP_Flash_Tool_v5.1352.01");
        File to = new File("C:\\Users\\Furman\\Desktop\\test\\to");
        CopyObject copy = new DirectoryCopyObject(file, to, Mode.INC, 10000);
        System.out.println(copy.copy());
    }
}
