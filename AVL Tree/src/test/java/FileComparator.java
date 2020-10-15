import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileComparator {

    public static boolean compareFiles(String fileName1, String fileName2) {
        try (RandomAccessFile file1 = new RandomAccessFile(fileName1, "r");
            RandomAccessFile file2 = new RandomAccessFile(fileName2, "r")) {
            String file1Pointer = file1.readLine();
            String file2Pointer = file2.readLine();
            while (file1Pointer != null && file2Pointer != null) {
                if (!file1Pointer.strip().equals(file2Pointer.strip())) {
                    return false;
                }
                file1Pointer = file1.readLine();
                file2Pointer = file2.readLine();
            }
            while (file1Pointer != null) {
                if (!file1Pointer.strip().equals("")) {
                    return false;
                }
                file1Pointer = file1.readLine();
            }
            while (file2Pointer != null) {
                if (!file2Pointer.strip().equals("")) {
                    return false;
                }
                file2Pointer = file2.readLine();
            }
            return true;
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
        } catch (IOException e) {
            System.err.println("Error occurred while reading");
        }
        return false;
    }
}
