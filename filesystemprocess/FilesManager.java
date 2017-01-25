package filesystemprocess;

import java.io.*;
import java.nio.file.Files;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by Furman on 24.01.2017.
 */
public class FilesManager {

    public static boolean copyFileFromTo(File from, File to) {
        try {
            Files.copy(from.toPath(), to.toPath(), REPLACE_EXISTING);
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean deleteFile(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                FilesManager.deleteFile(f);
            }
        }
        return file.delete();
    }

    public static boolean fileToZipCopy(File from, File to) {
        try (
                FileInputStream fileInputStream = new FileInputStream(from);
                ZipOutputStream out = new ZipOutputStream(new FileOutputStream(to.toPath() + ".zip"))) {
            out.putNextEntry(new ZipEntry(from.getName()));
            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);
            out.write(buffer);
            out.closeEntry();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static boolean deleteZipFile(File zipFile){
        return (new File(zipFile.toPath()+".zip").delete());
    }

    public static boolean zipToFileCopy(File from, File to) {
        try {
            ZipFile zipFile = new ZipFile(from.getPath()+".zip");
            Enumeration entries = zipFile.entries();
            while(entries.hasMoreElements()){
                ZipEntry entry =(ZipEntry) entries.nextElement();
                write(zipFile.getInputStream(entry), new FileOutputStream(to));
            }
        }
        catch(IOException e){return false;}
        return true;
    }

    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
        out.close();
        in.close();
    }
}
