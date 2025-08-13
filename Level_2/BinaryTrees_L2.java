import java.util.*;

class BinaryTrees_L2 {

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

    // BuildTree -> Binary Tree from Array
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

    // DisplayTree -> Preorder
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

    private static void DisplayTree(Node root) {
        System.out.println("==== DisplayTree ====");
        DisplayTreeHelper(root);
        System.out.println("=====================");
    }

    // 1. Morris InOrder Traversal
    private static Node getRightMostNode(Node leftNode, Node currentNode) {
        while(leftNode.right != null && leftNode.right != currentNode) {
            // either leaf or check if thread doesn't already exists
            leftNode = leftNode.right;
        }

        return leftNode;
    }
    private static void MorrisInorderTraversal(Node node) {
        ArrayList<Integer> inorder = new ArrayList<>();

        Node currentNode = node;
        while(currentNode != null) {
            Node leftNode = currentNode.left; // get left node
            if(leftNode == null) { // left is null **
                inorder.add(currentNode.data); // print 
                currentNode = currentNode.right; // move to right node
            } else { // left is not null **
                Node rightMostNode = getRightMostNode(currentNode.left, currentNode);

                if(rightMostNode.right == null) { // no existing thread -> create thread
                    rightMostNode.right = currentNode; // create thread
                    currentNode = currentNode.left; // move to next node
                } else { // thread already exists -> destroy thread [already traversed]
                    rightMostNode.right = null; // destroy thread
                    
                    inorder.add(currentNode.data); // add to traversal

                    currentNode = currentNode.right; // move to right
                }
            }
        }

        System.out.println("Morris inorder traversal -> " + inorder);
    }
    
    // 2. Morris Preorder Traversal
    private static void MorrisPreorderTraversal(Node node) {
        ArrayList<Integer> preorder = new ArrayList<>();

        Node currentNode = node;
        while(currentNode != null) {
            Node leftNode = currentNode.left; // get left node
            if(leftNode == null) { // left is null **
                preorder.add(currentNode.data); // print **
                currentNode = currentNode.right; // move to right node
            } else { // left is not null **
                Node rightMostNode = getRightMostNode(currentNode.left, currentNode);
                
                if(rightMostNode.right == null) { // no existing thread -> create thread
                    preorder.add(currentNode.data); // print ** 
                    rightMostNode.right = currentNode; // create thread
                    currentNode = currentNode.left; // move to next node
                } else { // thread already exists 
                    rightMostNode.right = null; // destroy thread
                    currentNode = currentNode.right; // move to right -> back to parent
                }
            }
        }

        System.out.println("Morris preorder traversal -> " + preorder);
    }

    // 3. Binary Tree Cameras -> Leetcode 968 HARD
    // https://leetcode.com/problems/binary-tree-cameras/description/
    private static int minCameras = 0;
    private static int CamerasInBinaryTreeHelper(Node node) {
        // -1 => need, 0 => camera, 1 => covered
        if (node == null) {
            return 1;
        }

        // leaf node
        if (node.left == null && node.right == null) {
            return -1;
        }

        int leftChildState = CamerasInBinaryTreeHelper(node.left);
        int rightChildState = CamerasInBinaryTreeHelper(node.right);
        
        // No camera in children
        if(leftChildState == -1 || rightChildState == -1) { // any one child needs camera
            minCameras++; // add camera to node
            return 0; // let parent know -> node has camera
        }

        // No need of camera in children
        if(leftChildState == 0 || rightChildState == 0) { // any one child has a camera
            return 1; // current node is covered by its child
        }

        return -1; // current node needs a camera -> ask parent
    }

    private static void CamerasInBinaryTree(Node root) {
        minCameras = 0;
        int isCameraRequiredForRoot = CamerasInBinaryTreeHelper(root); // Postorder traversal
        // Check for root -> after tree is processed
        if (isCameraRequiredForRoot == -1) {
            minCameras++;
        }
        System.out.println("Cameras required -> " + minCameras);
    }
    
    // 4. House Robber 3 -> leetcode 337
    // https://leetcode.com/problems/house-robber-iii/
    private static class HousePair {
        int withRobbery;
        int withoutRobbery;

        HousePair() {
            this.withRobbery = 0;
            this.withoutRobbery = 0;
        }
    }
    // Instead of Pair we can use array
    // int [2] => {with-robbery max amount, without-robbery max amount}
    private static HousePair HouseRobberHelper(Node node) {
        if (node == null) {
            return new HousePair();
        }

        // Faith and expectation
        HousePair leftPair = HouseRobberHelper(node.left);
        HousePair rightPair = HouseRobberHelper(node.right);

        HousePair nodePair = new HousePair();
        // Rob current node
        nodePair.withRobbery = node.data + leftPair.withoutRobbery + rightPair.withoutRobbery;  
        
        // Donot Rob current node
        nodePair.withoutRobbery = Math.max(leftPair.withRobbery, leftPair.withoutRobbery) + Math.max(rightPair.withRobbery, rightPair.withoutRobbery);  
        
        return nodePair;
    }
    private static void HouseRobber(Node root) {
        HousePair rootPair = HouseRobberHelper(root);
        System.out.println("Max Amount Robbed -> " + Math.max(rootPair.withRobbery, rootPair.withoutRobbery));
    }

    // 5. Longest zig zag path -> leetcode 1372
    // https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/description/
    private static class ZZPair {
        int forwardSlope;
        int backwardSlope;
        int maxLen = 0;

        ZZPair() {
            this.forwardSlope = -1;
            this.backwardSlope = -1;
            this.maxLen = 0;
        }
    }

    public static ZZPair longestZigZagPathHelper(Node node) {
        if(node == null) {
            return new ZZPair();
        }

        ZZPair left = longestZigZagPathHelper(node.left);
        ZZPair right = longestZigZagPathHelper(node.right);

        ZZPair nodePair = new ZZPair();
        // including current node as root
        int nodeMaxLen = Math.max(left.backwardSlope, right.forwardSlope) + 1;
        
        // maxLen in left child / maxLen in right child / including node gives maxLen
        nodePair.maxLen = Math.max(Math.max(left.maxLen, right.maxLen), nodeMaxLen);

        nodePair.forwardSlope = left.backwardSlope + 1; // left / node
        nodePair.backwardSlope = right.forwardSlope + 1;// node \ right
        return nodePair;
    }

    public static void longestZigZagPath(Node root) {
        ZZPair rootPair = longestZigZagPathHelper(root);
        System.out.println("MaxLen ZigZag Path -> " + rootPair.maxLen);
    }

    // 6. isTree BST -> leetcode 98
    // Use inorder of BST is sorted -> traverse inorder and check if this is false anywhere
    // https://leetcode.com/problems/validate-binary-search-tree/
    private static Node prev = null;
    private static boolean validateBSTHelper(Node node) {
        if (node == null) {
            return true;
        }

        if (!validateBSTHelper(node.left)) {
            return false;
        }

        // Check inorder here 
        if(prev != null && prev.data > node.data) { // inorder failure case
            return false;
        }

        prev = node; // update prev node

        if (!validateBSTHelper(node.right)) {
            return false;
        }

        return true;
    }
    private static void validateBST(Node root) {
        prev = null;
        boolean ans = validateBSTHelper(root);
        System.out.println("is Tree BST -> " + ans);
    }

    // 7. Validate BST using Morris Traversal
    private static void validateBSTMorris(Node root) {
        ArrayList<Integer> inorder = new ArrayList<>();
        
        Node prevNode = null;
        Node currentNode = root;

        boolean ans = true;
        while(currentNode != null) {
            Node leftNode = currentNode.left; // get left node
            if(leftNode == null) { // left is null **
                inorder.add(currentNode.data); // print 
                // check in inorder
                if (prevNode!= null && prevNode.data > currentNode.data) {
                    ans = false;
                    break;
                }
                prevNode = currentNode; // update prevNode

                currentNode = currentNode.right; // move to right node
            } else { // left is not null **
                Node rightMostNode = getRightMostNode(currentNode.left, currentNode);

                if(rightMostNode.right == null) { // no existing thread -> create thread
                    rightMostNode.right = currentNode; // create thread
                    currentNode = currentNode.left; // move to next node
                } else { // thread already exists -> destroy thread [already traversed]
                    rightMostNode.right = null; // destroy thread
                    
                    // check in inorder
                    if (prevNode!= null && prevNode.data > currentNode.data) {
                        ans = false;
                        break;
                    }
                    prevNode = currentNode; // update prevNode

                    currentNode = currentNode.right; // move to right
                }
            }
        }

        System.out.println("Is Valid BST Morris -> " + ans);
    }

    // 8. Recover Binary Search Tree -> leetcode 99
    // https://leetcode.com/problems/recover-binary-search-tree/description/
    // Inorder of BST should be sorted.
    // If 2 nodes are swapped there will be 2 peaks
    // Find and swap the 2 peaks.
    private static Node first;
    private static Node second;
    private static Node prevNode;
    private static void RecoverBSTHelper(Node node) {
        if(node == null) {
            return;
        }

        RecoverBSTHelper(node.left);
        
        // Inorder traversal
        // main logic for setting A, B [peaks]
        if(prevNode != null && node.data < prevNode.data) {
            if(first == null) {
                first = prevNode;
            }
            second = node;
        }

        prevNode = node; // update prev

        RecoverBSTHelper(node.right);
    }
    private static void RecoverBST(Node root) {
        System.out.println("RecoverBST called");
        first = null;
        second = null;
        prevNode = null;
        
        RecoverBSTHelper(root);
        System.out.println("First -> " + first.data + ", Second -> "+ second.data);

        // swap nodes
        int temp = first.data;
        first.data = second.data;
        second.data = temp;
    }

    // 9. Build Tree From Preorder And Inorder -> leetcode 105
    // https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
    // We are traversing in preorder.
    private static Node BuildTreeFromPreAndInorderHelper (int [] preorder, int preStart, int preEnd, int [] inorder, int inStart, int inEnd, Map<Integer, Integer> inMap) {
        if (preStart > preEnd || inStart > inEnd) {
            return null;
        }

        // Create node -> root is 1st element of preorder
        Node node = new Node(preorder[preStart]); 
        
        // Find Root in inorder
        int inRoot = inMap.get(node.data); // Using map for O(1) search
        int numsLeft = inRoot - inStart; // elements to left of root
        
        node.left = BuildTreeFromPreAndInorderHelper(preorder, preStart+1, preStart+numsLeft, inorder, inStart, inRoot-1, inMap);
        node.right = BuildTreeFromPreAndInorderHelper(preorder, preStart+numsLeft+1, preEnd, inorder, inRoot+1, inEnd, inMap);

        return node;
    }
    private static void BuildTreeFromPreAndInorder (int [] preorder, int [] inorder) {
        // Create Map
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for(int i=0; i<inorder.length; i++) {
            inMap.put(inorder[i], i);
        }

        // Call to helper
        Node root = BuildTreeFromPreAndInorderHelper(preorder, 0, preorder.length-1, inorder, 0, inorder.length-1, inMap);
        System.out.println("BuildTreeFromPreAndInorder Root -> " + root.data);
        DisplayTree(root);
    }

    
    // 10. BuildTree from PostOrder and Inorder Traversal -> leetcode 106
    // https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/
    public Node BuildTreeFromPostAndInorderHelper(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd, Map<Integer, Integer> inMap) {
        if(inStart > inEnd || postStart > postEnd || postEnd < 0) {
            return null;
        }

        // System.out.println(inStart + "," + inEnd + ", " + postStart + "," + postEnd);

        // Create root node
        Node root = new Node(postorder[postEnd]);

        // Find root index in inorder
        int inRoot = inMap.get(root.data);
        int numsLeft = inRoot - inStart;

        root.left = BuildTreeFromPostAndInorderHelper(inorder, inStart, inRoot-1, postorder, postStart, postStart+numsLeft-1, inMap);
        root.right = BuildTreeFromPostAndInorderHelper(inorder, inRoot+1, inEnd, postorder, postStart+numsLeft, postEnd-1, inMap);
        
        return root;
    }

    public void BuildTreeFromPostAndInorder(int[] inorder, int[] postorder) {
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for(int i=0; i<inorder.length; i++) {
            inMap.put(inorder[i], i);
        }

        Node root = BuildTreeFromPostAndInorderHelper(inorder, 0, inorder.length-1, postorder, 0, postorder.length - 1, inMap);
        System.out.println("BuildTreeFromPostAndInorder Root -> " + root.data);
        DisplayTree(root);
    }

    // 11. Binary Tree From Inorder and Level Order
    // https://www.geeksforgeeks.org/problems/construct-tree-from-inorder-and-levelorder/1?itm_source=geeksforgeeks&itm_medium=article&itm_campaign=practice_card
    private static Node BuildTreeFromInAndLevelOrderHelper(int inorder[], int inStart, int inEnd, int levelorder[], Map<Integer, Integer> inMap) {
        if (inStart > inEnd) {
            return null;
        }
        
        Node root = new Node(levelorder[0]); // create root node
        
        int inRoot = inMap.get(root.data); // find root node in inorder
        
        int [] levelorderLeft = new int[inRoot-inStart]; 
        int [] levelorderRight = new int[inEnd - inRoot];
        
        HashSet<Integer> hs = new HashSet<Integer>();
        for(int i=inStart; i<inRoot; i++) {
            hs.add(inorder[i]);
        }
        
        int li=0, ri=0;
        for(int i=1; i<levelorder.length; i++) {
            if (hs.contains(levelorder[i])) {
                levelorderLeft[li++] = levelorder[i];
                hs.remove(levelorder[i]);
            } else {
                levelorderRight[ri++] = levelorder[i];
            }
        }
        
        root.left = BuildTreeFromInAndLevelOrderHelper(inorder, inStart, inRoot-1, levelorderLeft, inMap);
        root.right = BuildTreeFromInAndLevelOrderHelper(inorder, inRoot+1, inEnd, levelorderRight, inMap);
        
        return root;
    }
    private static void BuildTreeFromInAndLevelOrder (int [] inorder, int [] levelorder) {
        HashMap<Integer, Integer> inMap = new HashMap<>();
        for(int i=0; i<inorder.length; i++) {
            inMap.put(inorder[i], i);
        }

        Node root = BuildTreeFromInAndLevelOrderHelper(inorder, 0, inorder.length-1, levelorder, inMap);
        System.out.println("BuildTreeFromInAndLevelOrder Root -> " + root.data);
        DisplayTree(root);
    }
    
    // 12. Binary Tree From Preorder and PostOrder -> leetcode 889
    // https://leetcode.com/problems/construct-binary-tree-from-preorder-and-postorder-traversal/description/
    private static Node BuildTreeFromPreAndPostOrderHelper(int preorder[], int preStart, int preEnd, int postorder[], int postStart, int postEnd, Map<Integer, Integer> postMap) {
        if (preStart > preEnd || postStart > postEnd) {
            return null;
        }
        
        Node root = new Node(preorder[preStart]); // create root node
        int nextToRoot = preorder[preStart+1];

        int postIdx = postMap.get(nextToRoot); // find root node in postorder
        int numsLeft = postIdx-postStart+1;

        root.left = BuildTreeFromPreAndPostOrderHelper(preorder, preStart+1, preStart+1+numsLeft, postorder, postStart, postIdx, postMap);
        root.right = BuildTreeFromPreAndPostOrderHelper(preorder, preStart+1+numsLeft+1, preEnd, postorder, postIdx+1, postEnd-1, postMap);
        
        return root;
    }
    private static void BuildTreeFromPreAndPostOrder (int [] preorder, int [] postorder) {
        HashMap<Integer, Integer> postMap = new HashMap<>();
        for(int i=0; i<postorder.length; i++) {
            postMap.put(postorder[i], i);
        }

        Node root = BuildTreeFromPreAndPostOrderHelper(preorder, 0, preorder.length-1, postorder, 0, postorder.length-1, postMap);
        System.out.println("BuildTreeFromPreAndPostOrder Root -> " + root.data);
        DisplayTree(root);
    }

    // 13. BST from Inorder Traversal
    // Balanced BST -> searching is O(log(n))
    private static Node BuildBSTFromInorderHelper (int [] inorder, int startIdx, int endIdx) {
        if (startIdx > endIdx) {
            return null;
        }

        int mid = (startIdx + endIdx)/2;
        Node node = new Node(inorder[mid]);

        node.left = BuildBSTFromInorderHelper(inorder, startIdx, mid-1);
        node.right = BuildBSTFromInorderHelper(inorder, mid+1, endIdx);
        
        return node;
    }
    private static void BuildBSTFromInorder (int [] inorder) {
        Node root = BuildBSTFromInorderHelper(inorder, 0, inorder.length-1);
        System.out.println("BuildBSTFromInorder Root -> " + root.data);
        DisplayTree(root);
    }
    
    
    // 14. BST from Preorder -> leetcode 1008
    // https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/description/
    private static int idx = 0;
    private static Node BuildBSTFromPreorderHelper (int [] preorder, int start, int end) {
        if (idx >= preorder.length) {
            return null;
        }
        
        if(preorder[idx] < start || preorder[idx] > end) {
            return null;
        }
        Node node = new Node(preorder[idx++]);
        
        node.left = BuildBSTFromPreorderHelper(preorder, start, node.data);
        node.right = BuildBSTFromPreorderHelper(preorder, node.data, end);
        return node;
    }
    private static void BuildBSTFromPreorder (int [] preorder) {
        idx = 0;
        Node root = BuildBSTFromPreorderHelper(preorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("BuildBSTFromPreorder Root -> " + root.data);
        DisplayTree(root);
    }
    
    // 14. BST from Postorder
    // https://www.geeksforgeeks.org/problems/construct-bst-from-post-order/1
    private static Node BuildBSTFromPostorderHelper (int [] postorder, int start, int end) {
        if (idx < 0) {
            return null;
        }
        
        if(postorder[idx] < start || postorder[idx] > end) {
            return null;
        }
        Node node = new Node(postorder[idx--]);
        
        node.right = BuildBSTFromPostorderHelper(postorder, node.data, end);
        node.left = BuildBSTFromPostorderHelper(postorder, start, node.data);
        return node;
    }
    private static void BuildBSTFromPostorder (int [] postorder) {
        idx = postorder.length-1;
        Node root = BuildBSTFromPostorderHelper(postorder, Integer.MIN_VALUE, Integer.MAX_VALUE);
        System.out.println("BuildBSTFrompostorder Root -> " + root.data);
        DisplayTree(root);
    }
    
    // 15. BST from Levelorder Traversal
    private static class LevelPair {
        Node parent = null;
        int left = Integer.MIN_VALUE;
        int right = Integer.MAX_VALUE;
        
        LevelPair(Node parent) {
            this.parent = parent;
        }
        LevelPair(Node parent, int left, int right) {
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }
    private static void BuildBSTFromLevelorder (int [] levelorder) {
        Node root = null;

        Queue<LevelPair> q = new ArrayDeque<>();
        q.add(new LevelPair(null));

        int idx = 0; // traverse over levelorder array
        while(q.size() > 0 && idx < levelorder.length) {
            LevelPair pair = q.remove();

            int val = levelorder[idx];
            if(val < pair.left || val > pair.right) continue;

            Node node = new Node(val);
            idx++;

            if(pair.parent == null) {
                root = node;
            } else {
                Node parent = pair.parent;
                if (val < parent.data) {
                    parent.left = node;
                } else {
                    parent.right = node;
                }
            }

            // add this node as potential range
            q.add(new LevelPair(node, pair.left, node.data)); // child < node < parent
            q.add(new LevelPair(node, node.data, pair.right)); // parent > node > child
        }

        System.out.println("BuildBSTFromLevelOrder Root -> " + root.data);
        DisplayTree(root);
    }

    // 17. Serialize Binary Tree -> leetcode 297
    private static void SerializeBinaryTreeHelper(Node node, StringBuilder sb) {
        if(node == null) {
            sb.append("null,");
            return;
        }

        sb.append(node.data + ",");

        SerializeBinaryTreeHelper(node.left, sb);
        SerializeBinaryTreeHelper(node.right, sb);
    }

    private static void SerializeBinaryTree(Node root) {
        StringBuilder sb = new StringBuilder();
        SerializeBinaryTreeHelper(root, sb);

        System.out.println("SerializeBinaryTree -> " + sb);
    }

    // 18. DeSerialize Binary Tree -> leetcode 297
    private static Node DeSerializeBinaryTreeHelper(String [] arr) {
        if(idx >= arr.length-1) { // array length out of bounds
            return null;
        }

        if (arr[idx].equals("null")) { // element in array is null
            idx++;
            return null;
        }

        Node node = new Node(Integer.parseInt(arr[idx++]));

        node.left = DeSerializeBinaryTreeHelper(arr);
        node.right = DeSerializeBinaryTreeHelper(arr);
        return node;
    }

    private static void DeSerializeBinaryTree(Node root) {
        StringBuilder sb = new StringBuilder();
        SerializeBinaryTreeHelper(root, sb);

        System.out.println("DeSerializeBinaryTree sb -> " + sb);
        idx = 0;
        Node new_root = DeSerializeBinaryTreeHelper(sb.toString().split(","));
        System.out.println("DeSerializeBinaryTree Root -> " + root.data);
        DisplayTree(new_root);
    }


    // 29. Vertical order Sum of binary Tree
    private static class LLNode {
        int sum;
        LLNode prev;
        LLNode next;
        
        LLNode(int sum) {
            this.sum = sum;
            this.prev = this.next = null;
        }
        
        public String toString() {
            return "[" + this.sum + "," + this.prev + "," + this.next + "]";
        }
    }
    
    private static void preorder(Node node, LLNode head) {
        if(node == null) {
            return;
        }
        head.sum += node.data; // add sum to LLnode
        
        // System.out.println("node -> " + node.data + ", head -> " + head.sum);

        if(node.left != null) {
            if(head.prev == null) {
                LLNode newNode = new LLNode(0);
                head.prev = newNode; // prev <- head
                newNode.next = head; // prev -> head
            }
            preorder(node.left, head.prev);
        }
        if(node.right != null) {
            preorder(node.right, head);
        }
    }
    
    private static void DiagonalSum(Node root)
    {
        System.out.println("==== Diagonal Sum ====");
        DisplayTree(root);
        LLNode head = new LLNode(0);
        LLNode start = head;

        preorder(root, head);
        
        ArrayList<Integer> res = new ArrayList<Integer>();
        
        while(start != null) {
            res.add(start.sum);
            start = start.prev;
        }
        
        System.out.println("Sum ->" + res);
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

        // MorrisInorderTraversal(root);
        
        // MorrisPreorderTraversal(root);

        // CamerasInBinaryTree(root);

        // HouseRobber(root);

        // longestZigZagPath(root); 

        // validateBST(root);

        // validateBSTMorris(root);

        // To check -> swapping 2 nodes
        // RecoverBST(root);
        // DisplayTree(root);

        // int [] preorder = {0,1,3,7,8,4,9,10,2,5,11,6};
        // int [] inorder = {7,3,8,1,9,4,10,0,11,5,2,6};
        // BuildTreeFromPreAndInorder(preorder, inorder);
        
        // int [] inorder = {9,3,15,20,7};
        // int [] postorder = {9,15,7,20,3};
        // BuildTreeFromPostAndInorder(inorder, postorder);

        // int [] levelorder = {2,7,15,3,6,9,5,11,4};
        // int [] inorder = {3,7,5,6,11,2,15,4,9};
        // BuildTreeFromInAndLevelOrder(inorder, levelorder);

        // int [] inorder = {9,12,14,17,19,23,50,54,67,72,76};
        // BuildBSTFromInorder(inorder);

        // int [] preorder = {30,20,10,15,25,23,39,35,42};
        // BuildBSTFromPreorder(preorder);

        // int [] postorder = {15,10,23,25,20,35,42,39,30};
        // BuildBSTFromPostorder(postorder);

        // int [] levelorder = {50,17,72,12,23,54,76,9,14,19,67};
        // BuildBSTFromLevelorder(levelorder);

        // SerializeBinaryTree(root);

        // DeSerializeBinaryTree(root);

        DiagonalSum(root);
    }
}