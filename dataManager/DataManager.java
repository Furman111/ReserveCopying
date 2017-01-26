package dataManager; /**
 * Created by Furman on 25.01.2017.
 */
import copyingFiles.Journal;

import java.io.*;

public class DataManager{
    public static final String BASE_PATH = "Journal.jour";
    public static Journal journal;

    public static Journal getJournal() throws FileNotFoundException,IOException,ClassNotFoundException{
        if (journal==null) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(BASE_PATH))) {
                journal = (Journal) in.readObject();
                return journal;
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("Журнал не найден!");
            } catch (IOException e) {
                throw new IOException("Ошибка при чтении журнала!");
            }
            catch (ClassNotFoundException e){
                throw new ClassCastException("Нестандартное содержание журнала!");
            }
        }
        else return journal;
    }

    public static void saveJournal() throws IOException{
        if (journal!=null){
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BASE_PATH))) {
                out.writeObject(journal);
                File file = new File(BASE_PATH);
                file.setWritable(true);
                file.setReadable(true);
            }catch (FileNotFoundException e) {
                throw new FileNotFoundException("Журнал не найден!");
            }
            catch(IOException e){
                throw  new IOException("Не удалось сохранить журнал!");
            }
        }
        else
            throw new IOException("Журнал не был загружен!");
    }

    public static void saveJournal(Journal j) throws IOException{
        if (j!=null){
            try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(BASE_PATH))) {
                out.writeObject(j);
                File file = new File(BASE_PATH);
                file.setWritable(true);
                file.setReadable(true);
            }
            catch (FileNotFoundException e) {
                throw new FileNotFoundException("Журнал не найден!");
            }
            catch(IOException e){
                throw  new IOException("Не удалось сохранить журнал!");
            }
        }
        else
            throw new IOException("Журнал не был загружен!");
    }

}
