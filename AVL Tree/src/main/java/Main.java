public class Main {

    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<Integer>();
        tree.insert(3);
        tree.insert(1);
        tree.insert(6);
        tree.insert(5);
        tree.insert(7);
        tree.printTreeFile("work.txt");
    }
}
