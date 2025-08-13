import java.util.*;

class BinarySearchTrees {

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

    // 1. BuildTree -> Binary Search Tree from Array
    private static Node BuildTree (Integer [] arr, int low, int high) {
        if (low > high) {
            return null;
        }

        int mid = low + (high-low)/2;

        Node node = new Node(arr[mid]);
        node.left = BuildTree(arr, low, mid-1);     // create left subtree and return node
        node.right = BuildTree(arr, mid+1, high);   // create right subtree and return node
        
        return node;
    }

    // 2. DisplayTree -> Preorder
    private static void DisplayTreeHelper (Node node) {
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
        DisplayTreeHelper(node.left);
        DisplayTreeHelper(node.right);
    }

    private static void DisplayTree (Node root) {
        System.out.println("==== Display Tree ====");
        DisplayTreeHelper(root);
        System.out.println("======================");
    }

    // 3. Size, Sum, Max, Height
    // Functions depending on structure are similar to BinaryTrees
    // Functions depending on value -> need modification
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
    private static int height (Node node) { 
        if (node == null) {
            return -1; // nodes -> 0, edges -> -1
        }
        int nodeHeight = 0;
        nodeHeight = Math.max(height(node.left), height(node.right)) + 1;
        return nodeHeight;
    }

    // max element is rightmost in binary search tree
    private static int max (Node node) { 
        if (node.right != null) {
            return max(node.right);
        } else {
            return node.data;
        }
    }

    // min element is leftmost in binary search tree
    private static int min (Node node) {
        if (node.left != null) {
            return min(node.left);
        } else {
            return node.data;
        }
    }

    // 4. Find in BST -> Time: O(log(n))
    // Logic -> move left or right based on value and root
    private static boolean find (Node node, int x) {
        if (node == null) {
            return false;
        }

        // compare x and node's data
        if (x < node.data) {
            return find(node.left, x);  // find in left
        } else if (x > node.data) {
            return find(node.right, x); // find in right
        } else {
            return true; // node found
        }
    }

    private static Node findNode (Node node, int x) {
        if (node == null) {
            return null;
        }

        if (x < node.data) {
            return findNode(node.left, x);
        } else if (x > node.data) {
            return findNode(node.right, x);
        } else {    // found
            return node;
        }
    }

    // 5. Add node to BST -> Time: O(log(n))
    private static Node addNode (Node node, int x) {
        // new node is added always as a leaf ****
        if (node == null) {    
            return new Node(x);
        }

        if (x < node.data) {    // will be added to left
            node.left = addNode(node.left, x);
        } else if (x > node.data) { // will be added to right
            node.right = addNode(node.right, x);
        } else { // node already present
            // do nothing
        }

        return node;
    }
    
    // 6. Remove node from BST** Imp.
    private static Node removeNode (Node node, int x) {
        if (node == null) { // x not found   
            return null;
        }

        if (x < node.data) {    // will be removed from left
            node.left = removeNode(node.left, x);
        } else if (x > node.data) { // will be removed from right
            node.right = removeNode(node.right, x);
        } else { // node found
            // logic on basis of number of children
            if (node.left != null && node.right != null) { // 2 - children
                // 1. find max from leftSubtree
                // 2. make this max as node
                // 3. remove max from leftSubtree
                
                // Replace with max from leftSubtree
                // int maxFromLeftSubtree = max(node.left); // O(Height) -> O(log(n)) call
                // node.data = maxFromLeftSubtree;
                // node.left = removeNode(node.left, maxFromLeftSubtree);
                
                // Replace with min from rightSubtree
                int minFromRightSubtree = min(node.right); // O(Height) -> O(log(n)) call
                node.data = minFromRightSubtree;
                node.right = removeNode(node.right, minFromRightSubtree);
            } else if (node.left != null) { // 1 - child
                return node.left;
            } else if (node.right != null) { // 1 - child
                return node.right;
            } else {    // 0 children
                return null;
            }
        }

        return node;
    }


    // 7. Replace sum of Larger in BST** Imp.
    private static int largerSum;
    // reverse inorder
    private static void ReplaceSumOfLargerHelper (Node node) {
        if (node == null) {
            return;
        }

        ReplaceSumOfLargerHelper(node.right);
        
        int val = node.data; // store node.data
        node.data = largerSum;  // update node
        largerSum += val;   // update sum

        ReplaceSumOfLargerHelper(node.left);
    }
    private static void ReplaceSumOfLarger (Node root) {
        largerSum = 0;
        System.out.println("Before");
        DisplayTree(root);
        ReplaceSumOfLargerHelper(root);
        System.out.println("After");
        DisplayTree(root);
    }

    // 7. LCA in BST ** Imp.
    private static Node LeastCommonAncestor (Node node, int x, int y) {
        if (node == null) {
            return null;
        }

        if (x < node.data && y < node.data) { // both in left subtree
            return LeastCommonAncestor(node.left, x, y);
        } else if (x > node.data && y > node.data) { // both in right subtree
            return LeastCommonAncestor(node.right, x, y);
        } else { // we found the node where both diverge -> LCA
            return node;
        }
    }

    // 7. PrintNodesInRange in BST ** Imp. -> Time: O(height)
    private static void PrintNodesInRange (Node node, int start, int end) {
        if (node == null) {
            return;
        }
        
        if (node.data < start) { // smaller than range -> go right
            PrintNodesInRange(node.right, start, end);
        } else if (node.data > end) {   // greater than range -> go left
            PrintNodesInRange(node.left, start, end);
        } else {
            PrintNodesInRange(node.left, start, end);
            System.out.print(node.data + ", ");
            PrintNodesInRange(node.right, start, end);
        }
    }

    // 8. PrintTargetSumPairs
    // a. Time: O(n*log(n)) -> find complement for each node
    private static void PrintTargetSumPairsHelper_1 (Node root, Node node, int targetSum) {
        if (node == null) {
            return;
        }

        PrintTargetSumPairsHelper_1(root, node.left, targetSum);

        int complement = targetSum - node.data;
        if (node.data < complement) { // To prevent printing twice, 
            // This handles same node twice case too
            Node complementNode = findNode(root, complement);
            if (complementNode != null && complementNode != node) {
                System.out.println("Pair -> " + node.data + ", " + complement);
            }
        }

        PrintTargetSumPairsHelper_1(root, node.right, targetSum);
    }

    // b. Time: O(n) -> get list from inorder and run 2 pointers 
    // Space: O(n)
    private static ArrayList<Integer> inorder;
    private static void InorderTraversal (Node node) {
        if (node == null) {
            return;
        }

        InorderTraversal(node.left);
        inorder.add(node.data);
        InorderTraversal(node.right);
    }
    private static void PrintTargetSumPairsHelper_2 (Node root, int targetSum) {
        inorder = new ArrayList<>();
        InorderTraversal(root);
        int start = 0;
        int end = inorder.size()-1;

        while (start < end) {
            int sum = inorder.get(start) + inorder.get(end);
            if (sum > targetSum) {
                end--;
            } else if (sum < targetSum) {
                start++;
            } else {
                System.out.println("Pair -> " + inorder.get(start) + ", " + inorder.get(end));
                start++;
                end--;
            }
        }
    }

    // c. Time:O(n), Space:O(log(n)) -> Iterative Inorder
    private static class Pair {
        Node node;
        int state;

        Pair(Node node) {
            this.node = node;
            this.state = 0;
        }
    }
    private static void PrintTargetSumPairsHelper_3 (Node root, int targetSum) {
        Stack<Pair> leftStack = new Stack<>();
        leftStack.push(new Pair(root));

        Stack<Pair> rightStack = new Stack<>();
        rightStack.push(new Pair(root));

        Node left = getNextFromNormalInorder(leftStack);
        Node right = getNextFromReverseInorder(rightStack);

        while (left.data < right.data) {
            int sum = left.data + right.data;
            if (sum < targetSum) {
                left = getNextFromNormalInorder(leftStack);
            } else if (sum > targetSum) {
                right = getNextFromReverseInorder(rightStack);
            } else {  // targetSum found
                System.out.println("Pair -> " + left.data + ", " + right.data);
                left = getNextFromNormalInorder(leftStack);
                right = getNextFromReverseInorder(rightStack);
            }
        }
    }

    public static Node getNextFromNormalInorder (Stack<Pair> st) {
        while (st.size() > 0) {
            Pair top = st.peek();
            if (top.state == 0) { // 0 -> preorder
                if (top.node.left != null) {
                    st.push(new Pair(top.node.left));
                }
                top.state++;
            } else if (top.state == 1) { // 1 -> inorder
                if (top.node.right != null) {
                    st.push(new Pair(top.node.right));
                }
                top.state++;
                return top.node;
            } else { // 2 -> postorder
                st.pop();
            }
        }
        return null; // unreachable
    }
    
    public static Node getNextFromReverseInorder (Stack<Pair> st) {
        while (st.size() > 0) {
            Pair top = st.peek();
            if (top.state == 0) { // 0 -> preorder -> go right first
                if (top.node.right != null) {
                    st.push(new Pair(top.node.right));
                }
                top.state++;
            } else if (top.state == 1) { // 1 -> inorder -> go left
                if (top.node.left != null) {
                    st.push(new Pair(top.node.left));
                }
                top.state++;
                return top.node;
            } else { // 2 -> postorder
                st.pop();
            }
        }
        return null; // unreachable
    }

    // Imp.** BST Iterator
    private static class BSTIterator implements Iterator {
        private Stack<Node> stack;
        boolean reverse;

        public BSTIterator(Node root, boolean reverse) {
            // System.out.println("start");
            this.stack = new Stack<>();
            this.reverse = reverse;

            if (reverse) {
                addAllRight(root);
            } else {
                addAllLeft(root); // intialize stack
            }
        }

        private void addAllLeft (Node node) {
            while (node != null) {
                this.stack.push(node);
                // System.out.println("addAllLeft " + node.val);
                node = node.left;
            }
        }
        
        private void addAllRight (Node node) {
            while (node != null) {
                this.stack.push(node);
                // System.out.println("addAllLeft " + node.val);
                node = node.right;
            }
        }
        
        public Node next() {
            Node topNode = this.stack.pop();
            // System.out.println("next top -> " + topNode.val);
            
            if (this.reverse) {
                addAllRight(topNode.left);
            } else {
                addAllLeft(topNode.right); // Add right node and all to it's left till leaf
            }
            
            return topNode;
        }
        
        public boolean hasNext() {
            // System.out.println("hasNext -> " + (this.stack.size() != 0));
            return this.stack.size() != 0;
        }
    }

    private static void PrintTargetSumPairs (Node root, int targetSum) {
        System.out.println("PrintTargetSumPairs sum -> " + targetSum);
        // PrintTargetSumPairsHelper_1(root, root, targetSum);
        // PrintTargetSumPairsHelper_2(root, targetSum);
        PrintTargetSumPairsHelper_3(root, targetSum);
        System.out.println();
    }

    public static void main(String[] args) {
        Integer [] arr = {12, 25, 37, 50, 62, 75, 87};
        
        // Tree
        //             50                -> K = 0
        //     25              75        -> K = 1
        //  12    37        62     87    -> K = 2
        Node root = BuildTree(arr, 0, arr.length-1);
        System.out.println("\nRoot Node -> " + root.data + "\n");

        DisplayTree(root);
        System.out.println();

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

        // System.out.println();
        // System.out.println("Find 10 -> " + find(root, 10));
        // System.out.println("Find 12 -> " + find(root, 12));
        // System.out.println("Find 62 -> " + find(root, 62));
        // System.out.println("Find 87 -> " + find(root, 87));

        // addNode(root, 30);
        // DisplayTree(root);

        // addNode(root, 30);
        // addNode(root, 40);
        // DisplayTree(root);
        // removeNode(root, 25);
        // DisplayTree(root);
        // removeNode(root, 30);
        // DisplayTree(root);

        // ReplaceSumOfLarger(root);

        // int x = 62;
        // int y = 87;
        // Node lcaNode = null; 
        // lcaNode = LeastCommonAncestor(root, x, y);
        // System.out.println("LCA of " + x + ", " + y + " -> " + lcaNode);

        // int x = 24;
        // int y = 77;
        // System.out.println("Nodes in range " + x + " to " + y + " -> ");
        // PrintNodesInRange(root, x, y);
        // System.out.println();

        // PrintTargetSumPairs(root, 100);

        BSTIterator iterator = new BSTIterator(root, false);
        BSTIterator reverseIterator = new BSTIterator(root, true);
        
        System.out.println("Normal -> ");
        while (iterator.hasNext()) {
            System.out.print(iterator.next().data + ", ");
        }
        System.out.println("");
        
        System.out.println("Reverse -> ");
        while (reverseIterator.hasNext()) {
            System.out.print(reverseIterator.next().data + ", ");
        }
        System.out.println("");
    }
}