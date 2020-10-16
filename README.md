# AVLTree

This is a generic AVLTree implementation in Java. An AVLTree is a binary search tree that always stays balanced by performing roations during insertion and deletion. This ensures that the worst case and average case search times are O(log(n)).

### Node Pool

This implementation features the option of a node pool. If enabled, the node pool is filled with nodes removed from the tree during deletion. Aditionally, if nodes are available in the pool, they are used during insertion. Users can decide to enable the pool and set its max capacity.
