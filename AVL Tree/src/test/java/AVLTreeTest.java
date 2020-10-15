import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

    @Test
    void testFileComparator() {
        assertFalse(FileComparator.compareFiles("TestFiles/file1.txt", "TestFiles/file2.txt"));
    }

}
