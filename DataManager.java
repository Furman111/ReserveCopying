/**
 * Created by Furman on 25.01.2017.
 */
import CopyingFiles.Journal;

import java.io.*;

public class DataManager{
    public static final String BASE_PATH = "";
    public static Journal journal;

    public static Journal getJournal() throws FileNotFoundException,IOException,ClassNotFoundException{
        if (journal==null) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BASE_PATH))) {
                journal =(Journal) in.readObject();
                in.close();
                return journal;
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException();
            } catch (IOException e) {
                throw new IOException();
            }
            catch (ClassNotFoundException e){
                throw new ClassCastException();
            }
        }
        else return journal;
    }

    public static void saveJournal() throws IOException{
        if (journal!=null){
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BASE_PATH))) {
                out.writeObject(journal);
                out.close();
            }
            catch(IOException e){
                throw  new IOException("Не удалось сохранить журнал!");
            }
        }
        else
            throw new IOException("Журнал не был загружен!");
    }

}
