/**
 * Created by Furman on 21.01.2017.
 */
import java.io.File;

public class Main {
    public static void main(String[] args) {
        System.out.println("Привет");
        File file = new File("C:\\Users\\Furman\\Desktop\\Оси");
        try {
            System.out.println(file.getName());
            for(int i=0;i<file.list().length;i++){
                System.out.println(file.list()[i]);
            }
            while(file.list().length>0)
                file.listFiles()[0].deleteOnExit();
        }
        catch(Exception e){}
    }
}
