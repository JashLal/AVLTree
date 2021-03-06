import java.io.FileWriter;
import java.io.IOException;
import java.lang.Math;

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
            this.root = reBalance(this.root);
            return true;
        }
        return false;
    }

    private boolean insert(T data, AVLNode cur) {
        if (data.compareTo(cur.data) < 0) {
            if (cur.left == null) {
                cur.left = new AVLNode(data);
                cur.balance--;
                return true;
            }
            int oldBalance = cur.left.balance;
            if (insert(data, cur.left)) {
                cur.left = reBalance(cur.left);
                if (cur.left.balance != oldBalance && cur.left.balance != 0) {
                    cur.balance--;
                }
                return true;
            }
            return false;
        }
        else if (data.compareTo(cur.data) > 0) {
            if (cur.right == null) {
                cur.right = new AVLNode(data);
                cur.balance++;
                return true;
            }
            int oldBalance = cur.right.balance;
            if (insert(data, cur.right)) {
                cur.right = reBalance(cur.right);
                if (cur.right.balance != oldBalance && cur.right.balance != 0) {
                    cur.balance++;
                }
                return true;
            }
            return false;
        }
        else {
            return false;
        }
    }

//    private boolean insert(T data, AVLNode cur) {
//        // data is larger than cur
//        if (cur.data.compareTo(data) < 0) {
//            // at node if at end of tree
//            if (cur.right == null) {
//                cur.right = new AVLNode(data);
//                // if there is no left subtree, increase the balance
//                cur.balance++;
//                return true;
//            }
//            // get balance of right tree to see if it changes
//            short balance = cur.right.balance;
//            // check if node was inserted
//            if (insert(data, cur.right)) {
//                // if rebalancing is required
//                cur.right = this.reBalance(cur.right);
//                // increase balance only if rotating is not required and the balance of the right changed (rotating didn't occur)
//                else if (cur.right.balance != balance && cur.right.balance != 0) {
//                    cur.balance++;
//                }
//                return true;
//            }
//            return false;
//        }
//        // data is smaller than cur
//        else if (cur.data.compareTo(data) > 0) {
//            // at node if at end of tree
//            if (cur.left == null) {
//                cur.left = new AVLNode(data);
//                cur.balance--;
//                return true;
//            }
//            short balance = cur.left.balance;
//            // check if node was inserted
//            if (insert(data, cur.left)) {
//                // if rebalancing is required
//                if (cur.left.balance == -2) {
//                    cur.left = this.reBalanceLeft(cur.left);
//                }
//                // decrement balance only if rotating is not required and it did not occur before
//                else if (cur.left.balance != balance && cur.left.balance != 0) {
//                    cur.balance--;
//                }
//                return true;
//            }
//            return false;
//        }
//        else {
//            return false;
//        }
//    }

    private AVLNode reBalance(AVLNode cur) {
        if (cur.balance >= 2) {
            return reBalanceRight(cur);
        }
        else if (cur.balance <= -2) {
            return reBalanceLeft(cur);
        }
        return cur;
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
        head.right = temp;
        // re-balance with balance fixing formulas
        head.balance = (short) (head.balance - 1 - Math.max(0, newHead.balance));
        newHead.balance = (short) (newHead.balance - 1 + Math.min(head.balance, 0));
        return newHead;
    }

    private AVLNode rotateCW(AVLNode head) {
        AVLNode newHead = head.left;
        // node to be moved to old head's left tree (could be null)
        AVLNode temp = newHead.right;
        newHead.right = head;
        head.left = temp;
        // re-balance with balance fixing formulas
        head.balance = (short) (head.balance + 1 - Math.min(newHead.balance, 0));
        newHead.balance = (short) (newHead.balance + 1 + Math.max(head.balance, 0));
        return newHead;
    }

    protected void printTreeFile(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            printTreeString(this.root, writer, 1);
        }
        catch (IOException e) {
            System.err.println("File " + filename + " not found");
        }
    }

    private class Height {
        private int height;
    }

    protected boolean isBalanced() {
        return isBalanced(this.root, new Height());
    }

    private boolean isBalanced(AVLNode cur, Height height) {
        if (cur == null) {
            // height is set to 0 by default
            return true;
        }
        Height left = new Height(), right = new Height();
        boolean leftBalanced = isBalanced(cur.left, left);
        boolean rightBalanced = isBalanced(cur.right, right);
        height.height = 1 + Math.max(left.height, right.height);
        return leftBalanced && rightBalanced && Math.abs(left.height - right.height) < 2;
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
     * @return whether the pool is empty
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Prints a view of the tree out to console
     */
    public void printTree( ) {
        if (this.isEmpty())
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
