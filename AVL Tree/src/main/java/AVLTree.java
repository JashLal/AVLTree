import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Binary search tree with self-balancing capabilities. Doesn't allow duplicates.
 * The data structure can be instantiated to contain a pool of nodes for increased efficiency of insertions.
 */
public class AVLTree<T extends Comparable<? super T>> {

//    enum BalanceFactor {
//        LLEFT, LEFT, EQUAL, RIGHT, RRIGHT
//    }

    /**
     * An element of the AVL tree
     */
    class AVLNode {

        private T data;
        private AVLNode left;
        private AVLNode right;
        private short balance;

        public AVLNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
            this.balance = 0;
        }
    }

    private AVLNode root;
    private AVLNode pool;
    private int poolSize;
    private int poolCapacity;

    /**
     * Creates an AVL tree that doesn't contain a pool
     */
    public AVLTree() {
        this.root = null;
        this.pool = null;
        this.poolSize = 0;
        this.poolCapacity = 0;
    }

    /**
     * Creates an AVL tree that contains a pool
     * @param poolCapacity the max number of nodes the pool can contain
     */
    public AVLTree(int poolCapacity) {
        this.root = null;
        this.pool = null;
        this.poolSize = 0;
        this.poolCapacity = poolCapacity;
    }

    public boolean insert(T data) {
        if (data == null) { return false; }
        if (this.root == null) {
            this.root = new AVLNode(data);
            return true;
        }
        if (insert(data, this.root)) {
            if (this.root.balance == 2) {
                this.root = this.reBalanceRight(this.root);
            }
            else if (this.root.balance == -2) {
                this.root = this.reBalanceLeft(this.root);
            }
            return true;
        }
        return false;
    }

    private boolean insert(T data, AVLNode cur) {
        // data is larger than cur
        if (cur.data.compareTo(data) < 0) {
            // at node if at end of tree
            if (cur.right == null) {
                cur.right = new AVLNode(data);
                // if there is no left subtree, increase the balance
                cur.balance++;
                return true;
            }
            // get balance of right tree to see if it changes
            short balance = cur.right.balance;
            // check if node was inserted
            if (insert(data, cur.right)) {
                // if rebalancing is required
                if (cur.right.balance == 2) {
                    cur.right = this.reBalanceRight(cur.right);
                }
                // increase balance only if rotating is not required and the balance of the right changed (rotating didn't occur)
                else if (cur.right.balance != balance && cur.right.balance != 0) {
                    cur.balance++;
                }
                return true;
            }
            return false;
        }
        // data is smaller than cur
        else if (cur.data.compareTo(data) > 0) {
            // at node if at end of tree
            if (cur.left == null) {
                cur.left = new AVLNode(data);
                cur.balance--;
                return true;
            }
            short balance = cur.left .balance;
            // check if node was inserted
            if (insert(data, cur.left)) {
                // if rebalancing is required
                if (cur.left.balance == -2) {
                    cur.left = this.reBalanceLeft(cur.left);
                }
                // decrement balance only if rotating is not required and it did not occur before
                else if (cur.left.balance != balance && cur.left.balance != 0) {
                    cur.balance--;
                }
                return true;
            }
            return false;
        }
        else {
            return false;
        }
    }

    private AVLNode reBalanceRight(AVLNode cur) {
        // right left case
        if (cur.right.balance < 0) {
            cur.right = this.rotateCW(cur.right);
        }
        // performs counter-clockwise rotation for right left and right right cases
        return rotateCCW(cur);
    }

    private AVLNode reBalanceLeft(AVLNode cur) {
        // left right case
        if (cur.left.balance > 0) {
            cur.left = this.rotateCCW(cur.left);
        }
        // performs clockwise rotation for left left and left right cases
        return this.rotateCW(cur);
    }

    private AVLNode rotateCCW(AVLNode head) {
        AVLNode newHead = head.right;
        // node to be moved to old head's right tree (could be null)
        AVLNode temp = newHead.left;
        newHead.left = head;
//        // if head is the root, reset the root to the new head
//        if (head == this.root) {
//            this.root = newHead;
//        }
        head.right = temp;
        // re-balance head first as it is the new child
        this.reBalance(head);
        this.reBalance(newHead);
        return newHead;
    }

    private AVLNode rotateCW(AVLNode head) {
        AVLNode newHead = head.left;
        // node to be moved to old head's left tree (could be null)
        AVLNode temp = newHead.right;
        newHead.right = head;
//        // if head is the root, reset the root be the new head
//        if (head == this.root) {
//            this.root = newHead;
//        }
        head.left = temp;
        // re-balance head first as it is the new child
        this.reBalance(head);
        this.reBalance(newHead);
        return newHead;
    }

    private short reBalance(AVLNode cur) {
        short left = 0, right = 0;
        if (cur.left != null) {
            left += cur.left.balance - 1;
        }
        if (cur.right != null) {
            right += cur.right.balance + 1;
        }
        return (short) (left + right);
    }

    public void printTreeFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            printTreeString(this.root, writer, 1);
        }
        catch (IOException e) {
            System.err.println("File " + filename + " not found");
        }
    }

    private void printTreeString(AVLNode node, FileWriter writer, int level) throws IOException {
        if (node != null) {
            printTreeString(node.left, writer, level + 1);
            for (int i = 0; i < level; i++) {
                writer.write('-');
            }
            writer.write("   ");
            writer.write(node.data.toString());
            writer.write(" (" + node.balance);
            writer.write(")");
            writer.write('\n');
            printTreeString(node.right, writer, level + 1);
        }
    }

    /**
     * Determines if the pool is empty
     * @return
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Prints a view of the tree out to console
     */
    public void printTree( ) {
        if ( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( this.root, 0 );
    }

    /**
     * Private helper of the printTree function
     * @param cur is the current node
     * @param level is the current level
     */
    private void printTree( AVLNode cur, int level ) {
        if ( cur != null ) {
            printTree( cur.right, level + 1 );
            for (int p = 0; p < level; p++) {
                System.out.print("   ");
            }
            System.out.println( cur.data );
            printTree( cur.left, level + 1 );
        }
    }
}
