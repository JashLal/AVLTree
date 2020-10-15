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
                if (cur.balance == 0) {
                    cur.balance++;
                }
                return true;
            }
            // check if node was inserted
            if (insert(data, cur.right)) {
                // if rebalancing is required
                if (cur.right.balance == 2) {
                    cur.right = this.reBalanceRight(cur.right);
                }
                // increase balance only if rotating is not required and the balance isn't 0 (height increased)
                else if (cur.right.balance != 0) {
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
                if (cur.balance == 0) {
                    cur.balance--;
                }
                return true;
            }
            // check if node was inserted
            if (insert(data, cur.left)) {
                // if rebalancing is required
                if (cur.left.balance == -2) {
                    cur.left = this.reBalanceLeft(cur.left);
                }
                // decrement balance only if rotating is not required
                else if (cur.left.balance != 0) {
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
