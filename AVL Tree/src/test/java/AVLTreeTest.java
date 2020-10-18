import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

    AVLTree<Integer> avlTree;

    @BeforeEach
    void setup() {
        avlTree = new AVLTree<>();
    }

    @Test
    void testRightRightSimple() {
        avlTree.insert(1);
        avlTree.insert(2);
        avlTree.insert(3);
        avlTree.printTreeFile("ResultsFiles/testRightRightSimple.txt");
        assertTrue(FileComparator.compareFiles("ResultsFiles/testRightRightSimple.txt", "TestFiles/testRightRightSimple.txt"));
    }

    @Test
    void testRightRightComplex() {
        avlTree.insert(3);
        avlTree.insert(1);
        avlTree.insert(5);
        avlTree.insert(4);
        avlTree.insert(6);
        avlTree.insert(7);
        avlTree.printTreeFile("ResultsFiles/testRightRightComplex.txt");
        assertTrue(FileComparator.compareFiles("ResultsFiles/testRightRightComplex.txt", "TestFiles/testRightRightComplex.txt"));
    }

    @Test
    void testRightLeftSimple() {
        avlTree.insert(1);
        avlTree.insert(3);
        avlTree.insert(2);
        avlTree.printTreeFile("ResultsFiles/testRightLeftSimple.txt");
        assertTrue(FileComparator.compareFiles("ResultsFiles/testRightLeftSimple.txt", "TestFiles/testRightLeftSimple.txt"));
    }

    @Test
    void testRightLeftComplex() {
        avlTree.insert(3);
        avlTree.insert(1);
        avlTree.insert(6);
        avlTree.insert(5);
        avlTree.insert(7);
        avlTree.insert(4);
        avlTree.printTreeFile("ResultsFiles/testRightLeftComplex.txt");
        assertTrue(FileComparator.compareFiles("ResultsFiles/testRightLeftComplex.txt", "TestFiles/testRightLeftComplex.txt"));
    }

    @Test
    void testLeftLeftSimple() {
        avlTree.insert(3);
        avlTree.insert(2);
        avlTree.insert(1);
        avlTree.printTreeFile("ResultsFiles/testLeftLeftSimple.txt");
        assertTrue(FileComparator.compareFiles("ResultsFiles/testLeftLeftSimple.txt", "TestFiles/testLeftLeftSimple.txt"));
    }

    @Test
    void testLeftLeftComplex() {
        avlTree.insert(6);
        avlTree.insert(3);
        avlTree.insert(7);
        avlTree.insert(2);
        avlTree.insert(4);
        avlTree.insert(1);
        avlTree.printTreeFile("ResultsFiles/testLeftLeftComplex.txt");
        assertTrue(FileComparator.compareFiles("ResultsFiles/testLeftLeftComplex.txt", "TestFiles/testLeftLeftComplex.txt"));
    }

    @Test
    void testLeftRightSimple() {
        avlTree.insert(3);
        avlTree.insert(1);
        avlTree.insert(2);
        avlTree.printTreeFile("ResultsFiles/testLeftRightSimple.txt");
        assertTrue(FileComparator.compareFiles("ResultsFiles/testLeftRightSimple.txt", "TestFiles/testLeftRightSimple.txt"));
    }

    @Test
    void testLeftRightComplex() {
        avlTree.insert(6);
        avlTree.insert(3);
        avlTree.insert(7);
        avlTree.insert(2);
        avlTree.insert(5);
        avlTree.insert(4);
        avlTree.printTreeFile("ResultsFiles/testLeftRightComplex.txt");
        assertTrue(FileComparator.compareFiles("ResultsFiles/testLeftRightComplex.txt", "TestFiles/testLeftRightComplex.txt"));
    }

    @Test
    void testRandomInsertions() {
        try {
            PermutationGenerator permutationGenerator = new PermutationGenerator(-1000, 1000);
            for (int i : permutationGenerator) {
                avlTree.insert(i);
                assertTrue(avlTree.isBalanced());
            }
            avlTree.printTreeFile("test.txt");
        }
        catch (InvalidBoundsException e) {
            e.printStackTrace();
        }
    }
}
