package dataManager; /**
 * Created by Furman on 25.01.2017.
 */

import copyingFiles.Journal;

import java.io.*;

public class DataManager {
    public static final String JOURNAL_PATH = "Journal.jour";
    public static final String FILE_PATH = "defaultDirectoryForCopies.fil";
    public static Journal journal;
    public static File defaultDirectoryForCopies;

    public static File getDefaultDirectoryForCopies() throws FileNotFoundException, IOException {
        if (defaultDirectoryForCopies == null) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                defaultDirectoryForCopies = (File) in.readObject();
                return defaultDirectoryForCopies;
            } catch (FileNotFoundException e) {
                defaultDirectoryForCopies = new File(System.getProperty("user.home"));
                saveDefaultDirectoryForCopies();
                return (defaultDirectoryForCopies);
            } catch (IOException e) {
                defaultDirectoryForCopies = new File(System.getProperty("user.home"));
                saveDefaultDirectoryForCopies();
                return (defaultDirectoryForCopies);
            } catch (ClassNotFoundException e) {
                defaultDirectoryForCopies = new File(System.getProperty("user.home"));
                saveDefaultDirectoryForCopies();
                return (defaultDirectoryForCopies);
            }
        } else return defaultDirectoryForCopies;
    }

    public static Journal getJournal() throws IOException, ClassNotFoundException {
        if (journal == null) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(JOURNAL_PATH))) {
                journal = (Journal) in.readObject();
                return journal;
            } catch (FileNotFoundException e) {
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(JOURNAL_PATH))) {
                    journal = new Journal();
                    out.writeObject(journal);
                    File file = new File(JOURNAL_PATH);
                    file.setWritable(true);
                    file.setReadable(true);
                    return journal;
                } catch (FileNotFoundException e1) {
                    throw new FileNotFoundException("Журнал не найден!");
                } catch (IOException e1) {
                    throw new IOException("Не удалось сохранить журнал!");
                }
            } catch (IOException e) {
                throw new IOException("Ошибка при чтении журнала!");
            } catch (ClassNotFoundException e) {
                throw new ClassCastException("Нестандартное содержание журнала!");
            }
        } else return journal;
    }

    public static void saveJournal() throws IOException {
        if (journal != null) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(JOURNAL_PATH))) {
                out.writeObject(journal);
                File file = new File(JOURNAL_PATH);
                file.setWritable(true);
                file.setReadable(true);
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("Журнал не найден!");
            } catch (IOException e) {
                throw new IOException("Не удалось сохранить журнал!");
            }
        } else
            throw new IOException("Журнал не был загружен!");
    }

    public static void saveDefaultDirectoryForCopies() throws IOException {
        if (defaultDirectoryForCopies != null) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                out.writeObject(defaultDirectoryForCopies);
                File file = new File(FILE_PATH);
                file.setWritable(true);
                file.setReadable(true);
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("Файл не найден!");
            } catch (IOException e) {
                throw new IOException("Не удалось сохранить стандартную директорию для сохранения копий!");
            }
        } else
            throw new IOException("Стандартная директория для сохранения копий не была загружена!");
    }

    public static void setDefaultDirectoryForCopies(File file) {
        DataManager.defaultDirectoryForCopies = file;
    }

}
