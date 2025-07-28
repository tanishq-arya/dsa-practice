import java.util.*;

class BinaryTrees {

    private static class Node {
        int data;
        Node left;
        Node right;

        @SuppressWarnings("unused")
        Node () {
            this.data = -1;
            this.left = this.right = null;
        }
        
        Node (int val) {
            this.data = val;
            this.left = this.right = null;
        }

        @Override
        public String toString() {
            return "" + this.data;// "[ " + this.data + " -> " + this.left + ", " + this.right + " ]";
        }
    }

    // 1. BuildTree -> Binary Tree from Array
    private static class Pair {
        Node node;
        int state; // 1 -> left, 2 -> right, 3 -> pop

        Pair(Node node) {
            this.node = node;
            this.state = 1;
        }

        Pair(Node node, int state) {
            this.node = node;
            this.state = state;
        }
    }
    private static Node BuildTree (Integer [] arr) {
        Node root = new Node(arr[0]); // root 
        
        Stack<Pair> st = new Stack<>();
        st.push(new Pair(root));

        int i = 1; // arr idx -> root already pushed so start at 1
        while (st.size() > 0) {
            Pair top = st.peek(); // get top

            if (top.state == 1) {    // add left child
                if (arr[i] != null) {
                    Node node = new Node(arr[i]);
                    top.node.left = node;
                    st.push(new Pair(node));
                } // else top.node.left -> null
                top.state++;
                i++;
            } else if (top.state == 2) { // add right child
                if (arr[i] != null) {
                    Node node = new Node(arr[i]);
                    top.node.right = node;
                    st.push(new Pair(node));
                } // else top.node.right -> null
                top.state++;
                i++;
            } else {
                st.pop();
            }
        }

        return root;
    } 

    // 2. DisplayTree -> Preorder
    private static void DisplayTree (Node node) {
        if (node == null) { // base case
            return;
        }
        
        // Print for current node
        String nodeString = "";
        nodeString += node.left != null ? node.left.data : ".";
        nodeString += " <- " + node.data + " -> ";
        nodeString += node.right != null ? node.right.data : ".";
        System.out.println(nodeString);
        
        // Recursive calls
        DisplayTree(node.left);
        DisplayTree(node.right);
    }
    
    // 3. Size, Sum, Max, Height
    private static int size (Node node) {
        if (node == null) {
            return 0;
        }
        int nodeSize = 0;
        nodeSize = size(node.left) + size(node.right) + 1;
        return nodeSize;
    }
    private static int sum (Node node) {
        if (node == null) {
            return 0;
        }
        int nodeSum = 0;
        nodeSum = sum(node.left) + sum(node.right) + node.data;
        return nodeSum;
    }
    private static int max (Node node) {
        if (node == null) {
            return Integer.MIN_VALUE; // identity
        }
        int nodeMax = 0;
        nodeMax = Math.max(max(node.left), max(node.right)); // for leaf -> child are (null,null) -> (Integer.MIN_VALUE,Integer.MIN_VALUE) 
        nodeMax = Math.max(nodeMax, node.data); // for leaf we compare and return leaf node value
        return nodeMax;
    }
    private static int min (Node node) {
        if (node == null) {
            return Integer.MAX_VALUE;
        }
        int nodeMin = 0;
        nodeMin = Math.min(min(node.left), min(node.right));
        nodeMin = Math.min(nodeMin, node.data);
        return nodeMin;
    }
    private static int height (Node node) { 
        if (node == null) {
            return -1; // nodes -> 0, edges -> -1
        }
        int nodeHeight = 0;
        nodeHeight = Math.max(height(node.left), height(node.right)) + 1;
        return nodeHeight;
    }

    // 4. Traversals
    private static void Traverse (Node node) {
        if (node == null) {
            return;
        }
        System.out.println("Pre -> " + node.data);
        Traverse(node.left);
        System.out.println("In -> " + node.data);
        Traverse(node.right);
        System.out.println("Post -> " + node.data);
    }

    private static String preOrder = ""; 
    private static String inOrder = ""; 
    private static String postOrder = ""; 
    private static void BinaryTreeTraversal (Node node) {
        if (node == null) {
            return;
        }
        preOrder += node.data + ", ";
        BinaryTreeTraversal(node.left);
        inOrder += node.data + ", ";
        BinaryTreeTraversal(node.right);
        postOrder += node.data + ", ";
    }

    // 4. Level Order Traversal
    private static void LevelOrderTraversal (Node root) {
        String res = "";

        System.out.println("Level Order Traversal");
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);
        while (!q.isEmpty()) {
            Node node = q.remove();
            
            res += node.data + ", ";

            if (node.left != null) 
            q.add(node.left);
            
            if (node.right != null) 
            q.add(node.right);
        }
        System.out.println(res);
    }
    private static void LevelOrderLinewiseTraversal (Node root) {
        System.out.println("Level Order Linewise Traversal");

        Queue<Node> q = new ArrayDeque<>();
        q.add(root);

        while (!q.isEmpty()) {
            int levelSize = q.size();
            String level = "";
            for (int i=1; i<=levelSize; i++) {
                Node node = q.remove();
                level += node.data + ", ";

                if (node.left != null) 
                q.add(node.left);
                
                if (node.right != null) 
                q.add(node.right);
            }
            System.out.println(level);
        }
    }

    // 5. IterativeTraversal
    private static void IterativeTraversal (Node node) {
        System.out.println("Iterative Traversal");
        Stack<Pair> st = new Stack<>();
        st.push(new Pair(node, 1));

        String preOrder = ""; 
        String inOrder = ""; 
        String postOrder = ""; 

        while(!st.isEmpty()) {
            Pair top = st.peek();
            if (top.state == 1) { // left side -> preorder
                preOrder += top.node.data + ", ";
                top.state++;
                if (top.node.left != null) {
                    st.push(new Pair(top.node.left, 1));
                }
            } else if (top.state == 2) { // in order
                inOrder += top.node.data + ", ";
                top.state++;
                if (top.node.right != null) {
                    st.push(new Pair(top.node.right, 1));
                }
            } else { // right side -> postorder
                st.pop();
                postOrder += top.node.data + ", ";
            }
        }
        System.out.println("Pre -> " + preOrder);
        System.out.println("In -> " + inOrder);
        System.out.println("Post -> " + postOrder);
    }

    // 6. NodeToRootPath
    private static boolean find (Node node, int x) {
        if (node == null) { // base case -> node not found
            return false;
        }

        if (node.data == x) { // found self
            return true;
        }

        return find (node.left, x) || find (node.right, x);
    }
    private static ArrayList<Integer> NodeToRootPath (Node node, Integer x) {
        if (node == null) { // base case
            return new ArrayList<Integer>();
        }

        if (node.data == x) { // node found
            ArrayList<Integer> path = new ArrayList<Integer>(List.of(node.data));
            // System.out.println("node -> " + node.data + " path -> " + path);
            return path;
        }
        
        ArrayList<Integer> leftPath =  NodeToRootPath(node.left, x);
        // System.out.println("node -> " + node.data + " leftpath -> " + leftPath);
        if (leftPath.size() > 0) {  // found in left child
            leftPath.add(node.data); // add current node as parent 
            return leftPath;
        }
        
        ArrayList<Integer> rightPath = NodeToRootPath(node.right, x);
        // System.out.println("node -> " + node.data + " rightPath -> " + rightPath);
        if (rightPath.size() > 0) {  // found in right child
            rightPath.add(node.data); // add current node as parent
            return rightPath;
        }

        return new ArrayList<Integer>();
    }

    // Return boolean and create path
    private static ArrayList<Integer> intPath = new ArrayList<>();
    private static ArrayList<Node> nodePath = new ArrayList<>();
    private static boolean NodeToRootPath2 (Node node, Integer x) {
        if (node == null) { // base case
            return false;
        }

        if (node.data == x) { // node found
            intPath.add(node.data);
            nodePath.add(node);
            return true;
        }
        
        boolean foundInLeft =  NodeToRootPath2(node.left, x);
        if (foundInLeft) {  // found in left child
            intPath.add(node.data); // add current node as parent 
            nodePath.add(node); // add current node as parent 
            return true;
        }
        
        boolean foundInRight = NodeToRootPath2(node.right, x);
        if (foundInRight) {  // found in right child
            intPath.add(node.data); // add current node as parent
            nodePath.add(node); // add current node as parent
            return true;
        }

        return false;
    }

    // 7. PrintKLevelsDown 
    //           50                  -> K = 0
    //     25              75        -> K = 1
    //  12    37        62     87    -> K = 2
    // .  .  30,40    60,70   .  .   -> K = 3
    private static void PrintKLevelsDown(Node root, int K) {
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);
        int level = 0; // root is level = 0

        ArrayList<Integer> KthLevel = new ArrayList<>();

        while (!q.isEmpty()) {
            int size = q.size();
            for (int i=1; i<=size; i++) {
                Node node = q.remove();
                if (level == K) {
                    KthLevel.add(node.data);
                }
                if (node.left != null) {
                    q.add(node.left);
                }
                if (node.right != null) {
                    q.add(node.right);
                }
            }
            level++;
        }

        System.out.println("KthLevel -> " + KthLevel);
    }

    private static void PrintKLevelsDownRecursive(Node node, int K) {
        if (node == null) {
            return;
        }

        if (K == 0) {
            System.out.print(node.data + ", ");
            return;
        }

        PrintKLevelsDownRecursive(node.left, K-1);
        PrintKLevelsDownRecursive(node.right, K-1);
    }
    private static void PrintKLevelsDownRecursive(Node node, int K, Node blocker) {
        if (node == null || K < 0 || node == blocker) {
            return;
        }

        if (K == 0) {
            System.out.print(node.data + ", ");
            return; // prune
        }

        PrintKLevelsDownRecursive(node.left, K-1, blocker);
        PrintKLevelsDownRecursive(node.right, K-1, blocker);
    }

    // 8. PrintKNodesFar -> Time:O(N), Space:O(n)
    private static void PrintKNodesFar(Node node, int x, int K) {
        System.out.println("\nPrint " + K + " Nodes Far from " + x);
        
        // a -> Find node to root path
        nodePath = new ArrayList<>(); // initialize
        NodeToRootPath2(node, x); 
        ArrayList<Node> path = nodePath;
        System.out.println("Node to Root path -> " + path);
        
        // b -> Print k down, k-1 down, ... , 1 down, 0 down 
        // with block condition
        for (int i=0; i<path.size() && i<=K ; i++) {
            Node blocker = (i == 0) ? null : path.get(i-1);
            PrintKLevelsDownRecursive(path.get(i), K-i, blocker);
        }
        System.out.println("\n");
    }

    // K nodes Away - 2nd approach optimised -> Time: O(n), Space: O(n)
    private static HashMap<Node, Node> parentMap; 
    private static void createParentMap (Node node, Node parent) {
        if (node == null) {
            return;
        }

        if (parent != null) {
            parentMap.put(node, parent);
        }

        createParentMap(node.left, node);
        createParentMap(node.right, node);
    }

    private static Node findNode (Node node, int x) {
        if (node == null) { // base case -> to stop recursion
            return null;
        }

        if (node.data == x) { // found self
            return node;
        }

        Node leftSearchResult = findNode (node.left, x);
        if (leftSearchResult != null) {
            return leftSearchResult;
        }
        
        Node rightSearchResult = findNode (node.right, x);
        if (rightSearchResult != null) {
            return rightSearchResult;
        }

        return null; // not found
    }
    private static void PrintKNodesAway (Node root, int x, int K) {
        // 1. create a parent map
        parentMap = new HashMap<>();
        createParentMap(root, null);
        System.out.println(K + " levels away from " + x  + " ->");
        System.out.println("ParentMap -> " + parentMap);

        // 2. Find node for x to start the BFS search
        Node startNode = findNode(root, x);

        // 3. BFS search from startNode
        Queue<Node> q = new ArrayDeque<>(); // Queue 
        q.add(startNode);
        int currentLevel = 0;   // level

        HashSet<Node> seen = new HashSet<>(); // Seen -> to mark visited nodes  
        seen.add(startNode);

        while (!q.isEmpty()) {
            int size = q.size();
            // check level before *** IMportant
            // If we check afterwards level will be 1 more than current
            // as we have removed current level nodes from Q and processed them.
            if (currentLevel == K) {  // Stop recursion we are processing Kth level
                break;
            }
            currentLevel++;

            for (int i=1; i<=size; i++) {
                Node node = q.remove();
                
                // left child
                if (node.left != null) {
                    if (seen.contains(node.left) == false) { // not already processed
                        q.add(node.left);
                        seen.add(node.left);
                    }
                }
                
                // right child
                if (node.right != null) {
                    if (seen.contains(node.right) == false) { // not already processed
                        q.add(node.right);
                        seen.add(node.right);
    
                    }
                }

                // parent
                Node parent = parentMap.getOrDefault(node, null);
                if (parent != null) {
                    if (seen.contains(parent) == false) { // not already processed
                        q.add(parent);
                        seen.add(parent);
                    }
                }
            }
        }
        System.out.println("Nodes -> " + q);
    }

    // 9. PrintRootToLeafPathInSumRange
    private static void PrintRootToLeafPathInSumRange(Node node, int low, int high, int pathSum, String path) {
        if (node == null) {
            return;
        }

        if (node.left == null && node.right == null) { // leaf node
            if (pathSum >= low && pathSum <= high) {
                System.out.println("Path -> " + path + " || Sum -> " + pathSum);
            }
            return;
        }

        pathSum += node.data;
        path += node.data + ", ";

        PrintRootToLeafPathInSumRange(node.left, low, high, pathSum, path);
        PrintRootToLeafPathInSumRange(node.right, low, high, pathSum, path);
    } 

    // 10. Transform To Left Cloned Tree
    private static Node TransformToLeftClonedTree (Node node) {
        if (node == null) {
            return null;
        }

        // children
        Node leftChild = TransformToLeftClonedTree(node.left);  // tranformed child
        Node rightChild = TransformToLeftClonedTree(node.right); // transformed child

        // work for current node
        Node copyOfNode = new Node(node.data); 
        
        node.left = copyOfNode; // node -> copy -> leftChild
        copyOfNode.left = leftChild; 

        node.right = rightChild; // add rightChild as it is

        return node;
    }

    // 11. TransformBackFromLeftClonedTree
    private static Node TransformBackFromLeftClonedTree (Node node) {
        if (node == null) {
            return null;
        }

        // node.left is a' (copy)*** -> skip this node
        Node leftChild = TransformBackFromLeftClonedTree(node.left.left); 

        Node rightChild = TransformBackFromLeftClonedTree(node.right);

        node.left = leftChild; 
        node.right = rightChild;

        return node;
    }

    // 12. Print Single Child Nodes
    private static void PrintSingleChildNodes (Node node, Node parent) {
        if (node == null) {
            return;
        }

        if (parent != null) {
            if (parent.left == node && parent.right == null) { 
                // current node is left child of parent
                System.out.println(parent.data + " -> has left child -> " + node.data);
            } else if (parent.right == node && parent.left == null) {
                // current node is right child of parent
                System.out.println(parent.data + " -> has right child -> " + node.data);        
            }
        }

        PrintSingleChildNodes(node.left, node);
        PrintSingleChildNodes(node.right, node);
    }

    // 13. RemoveLeafNodes
    private static Node RemoveLeafNodes (Node node) {
        if (node == null) {
            return null;
        }

        // if node is leaf -> return null
        if (node.left == null && node.right == null) { 
            return null;
        }

        Node leftChild = RemoveLeafNodes(node.left);
        Node rightChild = RemoveLeafNodes(node.right);

        node.left = leftChild;
        node.right = rightChild;

        return node;
    }

    // 14. Diameter of Binary Tree
    // 1. Get height in each node call
    // Time: O(n*n) -> as height is called for each node
    private static int Diameter_1 (Node node) { 
        if (node == null) {
            return 0;
        }

        int leftDiameter = Diameter_1(node.left);  // max diameter in left subtree
        int rightDiameter = Diameter_1(node.right); // max diameter in right subtree

        // nodeDiameter ->     
        // height of left_subtree <- root -> height of right_subtree
        // left_height + 1 [left to root] + 1 [root to right] + right_height 

        int nodeDiameter = height(node.left) + height(node.right) + 2;

        return Math.max(Math.max(leftDiameter, rightDiameter), nodeDiameter);
    }

    // 2. Better way -> Get diameter and height in a single call together
    private static class DiameterPair {
        int diameter;
        int height;

        DiameterPair () {
            this.diameter = 0;
            this.height = 0;
        }
    }

    // Time: O(n) -> only recursion call, 
    // Space: O(n) -> node pair for each node
    public static DiameterPair Diameter_2 (Node node) { 
        if (node == null) {
            DiameterPair basePair = new DiameterPair();
            basePair.diameter = 0; // diameter of null is 0
            basePair.height = -1; // height of null is -1
            
            return basePair;
        }

        DiameterPair leftPair = Diameter_2(node.left);
        DiameterPair rightPair = Diameter_2(node.right);

        DiameterPair nodePair = new DiameterPair();
        nodePair.height = Math.max(leftPair.height, rightPair.height) + 1;
        nodePair.diameter = Math.max(Math.max(leftPair.diameter, rightPair.diameter), leftPair.height + rightPair.height + 2);

        return nodePair;
    } 

    // 3. Time: O(n), Space: O(1)
    // Return height and update diameter
    private static int maxDiameter = 0;
    private static int Diameter_3 (Node node) {
        if (node == null) {
            return -1;
        }
        
        int leftHeight = Diameter_3(node.left);
        int rightHeight = Diameter_3(node.right);

        // update maxDiameter global variable
        maxDiameter = Math.max(maxDiameter, leftHeight + rightHeight + 2);
        
        // return node height 
        return Math.max(leftHeight, rightHeight) + 1;
    }

    private static void DiameterOfBinaryTree (Node root) {
        int diameter_1 = Diameter_1(root);
        System.out.println("Diameter 1 -> " + diameter_1);

        DiameterPair diameterPair = Diameter_2(root);
        System.out.println("Diameter 2 -> " + diameterPair.diameter);
        
        BinaryTrees.maxDiameter = 0;
        int diameter_3 = Diameter_3(root);
        System.out.println("Diameter 3 -> " + BinaryTrees.maxDiameter);
    }

    // 15. Tilt Of Binary Tree
    // Tilt is absolute of difference of sum of LeftSubtree & RightSubtree 
    // Return Sum and calculate tilt for each node
    private static int tiltOfTree = 0;
    private static int TiltOfBinaryTreeHelper (Node node) {
        if (node == null) {
            return 0;
        }

        int leftSubtreeSum = TiltOfBinaryTreeHelper(node.left);
        int rightSubtreeSum = TiltOfBinaryTreeHelper(node.right);

        int tiltOfNode = Math.abs(leftSubtreeSum - rightSubtreeSum); // for current node
        tiltOfTree += tiltOfNode; // update sum of tilt
        
        return leftSubtreeSum + rightSubtreeSum + node.data; // return sum of subtree with current node as Root
    }
    public static void TiltOfBinaryTree (Node root) {
       BinaryTrees.tiltOfTree = 0;
       TiltOfBinaryTreeHelper(root);
       System.out.println("Tilt of Binary Tree -> " + BinaryTrees.tiltOfTree);
    }

    // 16. is Tree a Binary Search Tree
    // BST Condition ->  
    // For all nodes -> all nodes to left < node and all nodes to right > node
    private static class BSTPair {
        int min;
        int max;
        boolean isBST;
        int size;
        Node largestBSTRoot;

        BSTPair() {
            this.min = Integer.MAX_VALUE;
            this.max = Integer.MIN_VALUE;
            this.isBST = false;
            this.size = 0;
            this.largestBSTRoot = null;
        }
        BSTPair(int min, int max, boolean isBST) {
            this.min = min;
            this.max = max;
            this.isBST = isBST;
        }

        @Override
        public String toString() {
            return "[" + this.min + ", " + this.max + ", " + this.isBST + " ]";
        }
    }
    // Tree 
    //           50                  -> K = 0
    //     25              75        -> K = 1
    //  12    37        62     87    -> K = 2
    // .  .  30 .      . 70   .  .   -> K = 3
    private static BSTPair isBST (Node node) {
        if (node == null) {
            BSTPair basePair =  new BSTPair(Integer.MAX_VALUE, Integer.MIN_VALUE, true);
            return basePair;
        }

        BSTPair leftPair = isBST(node.left);
        BSTPair rightPair = isBST(node.right);

        BSTPair nodePair = new BSTPair();
        boolean isNodeBST = (node.data >= leftPair.max) && (node.data <= rightPair.min);
        nodePair.isBST = isNodeBST && leftPair.isBST && rightPair.isBST;

        // Why calculate left.min and right.max ? 
        // If am a child of a node -> 
        // my parent needs this to calculate its values
        nodePair.min = Math.min(node.data, Math.min(leftPair.min, rightPair.min));
        nodePair.max = Math.max(node.data, Math.max(leftPair.max, rightPair.max));

        System.out.println(node.data  + " -> " + nodePair);

        return nodePair;
    }

    // 17. isBalancedTree
    // Return height and maintain isBalancedTree
    private static boolean isBinaryTreeBalanced;
    private static int isBalancedTreeHelper (Node node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = isBalancedTreeHelper(node.left);
        int rightHeight = isBalancedTreeHelper(node.right);

        boolean isNodeBalanced = Math.abs(rightHeight-leftHeight)<=1;
        isBinaryTreeBalanced = isBinaryTreeBalanced && isNodeBalanced;

        return Math.max(leftHeight, rightHeight) + 1;
    }
    private static void isBalancedTree (Node root) {
        BinaryTrees.isBinaryTreeBalanced = true;
        isBalancedTreeHelper (root);
        System.out.println("Is tree balanced -> " + isBinaryTreeBalanced);
    }

    // 18. LargestBST

    private static BSTPair LargestBSTHelper (Node node) {
        if (node == null) {
            BSTPair basePair = new BSTPair();
            basePair.min = Integer.MAX_VALUE;
            basePair.max = Integer.MIN_VALUE;
            basePair.isBST = true;
            basePair.size = 0;
            basePair.largestBSTRoot = null;
            
            return basePair;
        }

        BSTPair leftPair = LargestBSTHelper(node.left);
        BSTPair rightPair = LargestBSTHelper(node.right);

        boolean isNodeBST = (node.data >= leftPair.max) && (node.data <= rightPair.min);
        BSTPair nodePair = new BSTPair();
        nodePair.isBST = leftPair.isBST && rightPair.isBST && isNodeBST;
        nodePair.min = Math.min(Math.min(leftPair.min, rightPair.min), node.data);
        nodePair.max = Math.max(Math.max(leftPair.max, rightPair.max), node.data);

        // Largest BST -> logic
        if (nodePair.isBST) {  // current node is BST SubTree
            nodePair.largestBSTRoot = node;
            nodePair.size = leftPair.size + rightPair.size + 1;
        } else {  // Current node is not BST SubTree -> compare left and right 
            if (leftPair.size > rightPair.size) {
                nodePair.largestBSTRoot = leftPair.largestBSTRoot;
                nodePair.size = leftPair.size;
            } else {
                nodePair.largestBSTRoot = rightPair.largestBSTRoot;
                nodePair.size = rightPair.size;
            }
        }

        // Why compare left & right if current is not a BST Subtree?
        // Why does this work if left and right are also not BST Subtree ?

        // Ans -> We need to propagate the largestBSTNode, Size 
        // upwards till Root to return -> so we have to return it to upper nodes

        return nodePair;
    }
    private static void LargestBST (Node root) {
        System.out.println("Largest BST");
        BSTPair rootPair = LargestBSTHelper(root);
        System.out.println("Largest BST at node > " + rootPair.largestBSTRoot + " , size > " + rootPair.size);
    }



    public static void main(String[] args) {
        Integer [] arr = {50, 25, 12, null, null, 37, 30, null, null, null, 75, 62, null, 70, null, null, 87, null, null};
        
        // Tree 
        //           50                  -> K = 0
        //     25              75        -> K = 1
        //  12    37        62     87    -> K = 2
        // .  .  30 .      . 70   .  .   -> K = 3
        Node root = BuildTree(arr);
        System.out.println("\nRoot Node -> " + root.data + "\n");

        // DisplayTree(root);

        // int size = size(root);
        // int sum = sum(root);
        // int max = max(root);
        // int min = min(root);
        // int height = height(root);
        // System.out.println("Tree size -> " + size);
        // System.out.println("Tree sum -> " + sum);
        // System.out.println("Tree max -> " + max);
        // System.out.println("Tree min -> " + min);
        // System.out.println("Tree height -> " + height);

        // Traverse(root);
        // BinaryTreeTraversal(root);
        // System.out.println("Pre -> " + preOrder);
        // System.out.println("In -> " + inOrder);
        // System.out.println("Post -> " + postOrder);

        // LevelOrderTraversal(root);
        // LevelOrderLinewiseTraversal(root);
        
        // IterativeTraversal(root);

        // int x = 30;
        // boolean found = find(root, x);
        // System.out.println("Find " + x + " -> " + found);
        // ArrayList<Integer> path = NodeToRootPath(root, x);
        // System.out.println("Path from root till " + x + " -> " + path);
        // NodeToRootPath2(root, x);
        // System.out.println("Path2 from root till " + x + " -> " + BinaryTrees.path);

        // ==== PrintKLevelsDown ====
        // PrintKLevelsDown(root, 0);
        // PrintKLevelsDown(root, 1);
        // PrintKLevelsDown(root, 2);
        // PrintKLevelsDown(root, 3);

        // PrintKLevelsDownRecursive(root, 0);
        // System.err.println();
        // PrintKLevelsDownRecursive(root, 1);
        // System.err.println();
        // PrintKLevelsDownRecursive(root, 2);
        // System.err.println();
        // PrintKLevelsDownRecursive(root, 3);
        // System.err.println();


        // PrintKNodesFar(root, 37, 1);
        // PrintKNodesFar(root, 25, 3);

        // PrintKNodesAway(root, 37, 1);
        // PrintKNodesAway(root, 25, 3);

        // PrintRootToLeafPathInSumRange(root, 50, 200, 0, "");

        // System.out.println("\nOriginal Tree");
        // DisplayTree(root);
        // Node transformedRoot = TransformToLeftClonedTree(root);
        // System.out.println("\nTransformed Tree");
        // DisplayTree(transformedRoot);
        // Node normalRoot = TransformBackFromLeftClonedTree(transformedRoot);
        // System.out.println("\nNormal Tree");
        // DisplayTree(normalRoot);

        // System.out.println("Print Single Child Nodes");
        // DisplayTree(root);
        // PrintSingleChildNodes(root, null);
        // System.out.println();
        
        // System.out.println("Original Tree");
        // DisplayTree(root);
        // Node transformedTree = RemoveLeafNodes(root);
        // System.out.println("Transformed Tree");
        // DisplayTree(transformedTree);
        // System.out.println();

        // DiameterOfBinaryTree(root);

        // TiltOfBinaryTree(root);

        // BSTPair rootPair = isBST(root);
        // System.out.println("Is Tree BST -> " + rootPair.isBST);

        // isBalancedTree(root);

        LargestBST(root); // -> Node 50, Size 9
        Integer [] arr2 = {50, 25, 12, null, null, 37, 30, null, null, null, 75, 62, null, 77, null, null, 87, null, null};
        Node root2 = BuildTree(arr2);
        LargestBST(root2); // -> Node 25, size 4
    }
}