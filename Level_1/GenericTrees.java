import java.util.*;

class GenericTrees {
    private static class Node {
        int data;
        ArrayList<Node> children;

        @SuppressWarnings("unused")
        Node () {
            this.data = -1;
            this.children = new ArrayList<>();
        }
        
        Node (int val) {
            this.data = val;
            this.children = new ArrayList<>();
        }

        @Override
        public String toString() {
            return "[ " + this.data + " , " + this.children + " ]";
        }
    }
    
    // 1. BuildTree -> Generic Tree from Array
    private static Node BuildTree (int [] arr) { // Time: O(n)
        Node root = new Node(arr[0]);
        
        Stack<Node> st = new Stack<>();
        st.push(root);

        for (int i=1; i<arr.length; i++) {
            if (arr[i] == -1) { // reached right of node in euler path
                st.pop(); // pop top / current node
            } else {
                if (!st.isEmpty()) {
                    Node parent = st.peek();    // get parent -> current top of stack
                    Node child = new Node(arr[i]);  // create new child
                    parent.children.add(child); // add this child to parent
                    st.push(child); // push child on stack
                }
            }
        }

        return root;
    }

    // 2. Display Tree -> Preorder as Node -> children, Left -> _ , Right -> _
    private static void DisplayTree (Node node) { // Time: O(n)
        String childString = "";
        if (node.children.isEmpty()) {
            childString = ".";
        } else {
            for (Node child: node.children) {
                childString += child.data + ", "; 
            }
        }
        
        // Current -> Root
        System.out.println(node.data + " -> " + childString);
        
        for (Node child: node.children) {
            DisplayTree (child);
        }
    }

    // 3. Size of Tree
    private static int SizeOfTree (Node node) { // Time: O(n) -> each node once
        int size = 0; // initialize

        for (Node child: node.children) { // get size of children
            size += SizeOfTree(child);
        }

        size += 1; // count current node
        return size;
    }

    // 4. Max in Tree
    private static int MaxOfTree (Node node) { // Time: O(n)
        int max = Integer.MIN_VALUE;

        for (Node child: node.children) {
            max = Math.max(max, MaxOfTree(child)); // ** Update max only if greater
        }

        return Math.max(max, node.data); // compare current node
    }

    // 5. Height of Tree
    private static int HeightOfTree (Node node) { // Time: O(n)
        // Edges -> -1, Nodes -> 0
        int heightOfNode = -1;  // leaf is 0 -> 0-1 = -1
        
        for (Node child: node.children) {
            heightOfNode = Math.max(heightOfNode, HeightOfTree(child));
        }
        
        return heightOfNode + 1; // +1 for current node
    }

    // 6. Traversal 
    private static void EulerTraversal (Node node) {
        System.err.println("Node Pre -> " + node.data);

        for (Node child: node.children) {
            System.out.println("Edge Pre -> " + node.data + " - " + child.data);
            EulerTraversal(child);
            System.out.println("Edge Post -> " + node.data + " - " + child.data);
        }
        System.out.println("Node Post -> " + node.data);
    }

    // 7. Level Order Traversal
    private static void LevelOrderTraversal (Node root) {
        System.out.println("Level Order Traversal");
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);

        while (!q.isEmpty()) {
            Node node = q.remove();
            System.out.println(node.data + ",");

            for (Node child: node.children) {
                q.add(child);
            }
        }
    }

    // 8. LevelOrderLineWiseTraversal
    // a. 2 Queues -> Main & Child
    // b. 1 Queue -> Extra marker node -> null node
    // c. 1 Queue -> Size ->
    private static void LevelOrderLineWiseTraversal(Node root) {
        System.out.println("Level Order Linewise Traversal");
        Queue<Node> mq = new ArrayDeque<>();
        Queue<Node> cq = new ArrayDeque<>();
        mq.add(root);

        while (mq.size() > 0 || cq.size() > 0) {
            Node node = mq.remove();            // remove
            System.out.print(node.data + ",");  // print

            for (Node child: node.children) {   // add child
                cq.add(child);
            }

            if (mq.size() == 0) {  // update Queues
                System.out.println("");
                mq = cq;    // child q is now main q
                cq = new ArrayDeque<>();  // new child q
            }
        }
    }

    private static void LevelOrderLineWiseTraversalUsingNull(Node root) { // TIme: O(n), Space: O(n) Q
        System.out.println("Level Order Linewise Traversal -> Null marker");
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);
        q.add(new Node(-1)); // add marker to Q to mark end of level

        while (q.size() > 0) {
            Node node = q.remove(); // remove

            if (node.data == -1) {      // check if marker -> end of level
                if (q.size() > 0) {
                    System.out.println("");  // end current level
                    q.add(new Node(-1));    // add new marker to mark end of next level
                }
            } else {
                System.out.print(node.data + ", ");  // work 
                for (Node child: node.children) {   // add children
                    q.add(child);
                }
            }
        }
    }

    private static void LevelOrderLineWiseTraversalUsingSize(Node root) { // TIme: O(n), Space: O(n) Q
        System.out.println("Level Order Linewise Traversal");
        Queue<Node> q = new ArrayDeque<>();
        q.add(root);

        while (q.size() > 0) {
            int levelSize = q.size(); // current level size
            
            for (int i=1; i<=levelSize; i++) {  // process current level
                Node node = q.remove();
                System.out.print(node.data + ", ");
                for (Node child: node.children) {
                    q.add(child);
                }
            }

            System.out.println(); // end current level
        }
    }

    // 9. LevelOrderZigZagTraversal
    // a. 2 queues with reverse
    // b. 2 stacks
    // c. 1 queue with direction flag
    private static void LevelOrderZigZagTraversal(Node root) {
        System.out.println("Level Order ZigZag Traversal");
        Stack<Node> ms = new Stack<>();
        Stack<Node> cs = new Stack<>();
        ms.add(root);

        // If we are going left to right -> push child in left to right order
        // Now when we remove children from stack they will be in right to left order**

        int direction = 1; // to maintain direction -> 1, <- 2
        while (ms.size() > 0 || cs.size() > 0) {
            Node node = ms.pop();            // remove
            System.out.print(node.data + ", ");  // print

            if (direction == 1) {
                for (Node child: node.children) {   // add child
                    cs.push(child);
                }
            } else {
                Collections.reverse(node.children);
                for (Node child: node.children) {   // add child
                    cs.push(child);
                }
            }
            direction = (direction+1)%2;
            if (ms.size() == 0) {           // update Queues
                System.out.println("");
                ms = cs;
                cs = new Stack<>();
            }
        }
    }


    // Using Size of level - ** Best Time:O(n), Space:O(n) for res
    private static void LevelOrderZigZagTraversal2(Node root) {
        System.out.println("Level Order ZigZag Traversal2 - single Q and direction");
 
        List<List<Integer>> res = new ArrayList<>();
        if(root == null)
            return res;
        
        Queue<TreeNode> q = new ArrayDeque<>();
        q.add(root);

        boolean leftToRight = true;  // Direction flag

        while (!q.isEmpty()) {
            int levelSize = q.size();
            List<Integer> level = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = q.remove();  // Dequeue the node

                if (leftToRight) {
                    level.add(node.val);  // Add node value from left to right
                } else {
                    // Add node value from right to left (add to the front)
                    level.add(0, node.val);
                }

                // Enqueue left and right children for the next level
                if (node.left != null) q.add(node.left);
                if (node.right != null) q.add(node.right);
            }

            res.add(level);  // Add current level to result
            leftToRight = !leftToRight;  // Toggle direction for the next level
        }
        System.out.print("Zig-Zag Traversal: " + res);
    }

    // 10. Mirror a Generic Tree
    private static void mirrorTreeHelper(Node node) {
        if (node.children.size() == 0) { // base case -> leaf node
            return;
        }

        for (Node child: node.children) { // Run for each child
            mirrorTreeHelper(child);
        }
        Collections.reverse(node.children); // Reverse children of current
        return;
    }
    private static void mirrorTree(Node root) {
        System.out.println("==== Mirror a generic Tree ====");
        System.out.println("\n>>>> Before >>>>");
        DisplayTree(root);
        mirrorTreeHelper(root);
        System.out.println("\n>>>> After >>>>");
        DisplayTree(root);
    }

    // 11. Remove Leaf nodes
    private static void removeLeafNodesHelper(Node node) {
        for (int i=node.children.size()-1; i>=0; i--) { // traverse in reverse order
            Node child = node.children.get(i);
            if (child.children.size() == 0) {
                node.children.remove(child); 
            }
        }
        // Cannot do this with forward loop -> concurrent modification exception**

        for (Node child: node.children) {
            removeLeafNodesHelper(child);
        }
    }

    private static void removeLeafNodes(Node root) {
        System.out.println("==== Remove leaf nodes of generic Tree ====");
        System.out.println("\n>>>> Before >>>>");
        DisplayTree(root);
        removeLeafNodesHelper(root);
        System.out.println("\n>>>> After >>>>");
        DisplayTree(root);
    }

    // 12. Linearize Tree
    private static Node findLeaf (Node node) {
        while(node.children.size() == 1) {
            node = node.children.get(0);
        }
        return node;
    }
    private static void linearizeTreeHelper (Node node) { // Time: O(n*n) -> PostOrder * Loop for each node
        for (Node child: node.children) {
            linearizeTreeHelper(child);
        }

        while (node.children.size() > 1) { // Time: O(n)
            Node lastChild = node.children.remove(node.children.size()-1);      // remove last child
            Node secondLastChild = node.children.get(node.children.size()-1);   // get secondlast child
            Node leafNodeOfSecondLastChild = findLeaf(secondLastChild);
            System.out.println("Node: " + node.data + " => make " + leafNodeOfSecondLastChild.data + " -> " + lastChild.data);
            leafNodeOfSecondLastChild.children.add(lastChild);
        }
    }
    private static Node linearizeTreeHelper_Efficient (Node node) { // Time: O(n) -> save & return tail when returning from preorder
        if (node.children.size() == 0) { // leaf node
            return node;
        }

        Node leafOfLastNode = linearizeTreeHelper_Efficient(node.children.get(node.children.size()-1)); // linearize last child
        while (node.children.size() > 1) {
            Node lastChild = node.children.remove(node.children.size()-1);  // remove last child
            Node leafNodeOfSecondLastChild = linearizeTreeHelper_Efficient(node.children.get(node.children.size()-1));   // get secondlast child
            System.out.println("Node: " + node.data + " => make " + leafNodeOfSecondLastChild.data + " -> " + lastChild.data);
            leafNodeOfSecondLastChild.children.add(lastChild);
        }

        return leafOfLastNode;
    }

    private static void LinearizeTree (Node root) {
        System.out.println("==== Linearize a generic Tree ====");
        System.out.println("\n>>>> Before >>>>");
        DisplayTree(root);
        // linearizeTreeHelper(root);
        linearizeTreeHelper_Efficient(root);
        System.out.println("\n>>>> After >>>>");
        DisplayTree(root);
    }

    // 13. Find Node in Tree
    private static boolean FindNodeInTreeHelper(Node node, int x) {
        if (node.data == x) {
            return true;
        }

        for (Node child: node.children) {
            boolean foundInChild = FindNodeInTreeHelper(child, x);
            if (foundInChild == true) {
                return true;
            }
        }

        return false;
    }
    private static void FindNodeInTree(Node root, int x) {
        System.out.println("==== Linearize a generic Tree ====");
        System.out.println("\n>>>> Before >>>>");
        DisplayTree(root);
        boolean found = FindNodeInTreeHelper(root, x);
        System.out.println("Found node -> " + x + " -> " + found);
    }

    // 13. Find Path from Root till Node in Tree
    private static ArrayList<Integer> NodeToRootPathHelper(Node node, int x) {
        if (node.data == x) {
            ArrayList<Integer> res = new ArrayList<Integer>();
            res.add(node.data);
            return res;
        }

        for (Node child: node.children) {
            ArrayList<Integer> path = NodeToRootPathHelper(child, x); // get path from child
            // System.out.println(path); // includes child
            if (path.size() > 0) {
                path.add(node.data); // add current node as parent
                return path;
            }
        }

        return new ArrayList<Integer>();
    }
    private static void NodeToRootPath(Node root, int x) {
        System.out.println("==== NodeToRootPath a generic Tree ====");
        ArrayList<Integer> path = NodeToRootPathHelper(root, x);
        System.out.println("Path -> " + path);
    }

    // 14. LowestCommonAncestor
    private static void LowestCommonAncestor(Node root, int x, int y) {
        System.out.println("==== Lowest Common Ancestor ====");
        ArrayList<Integer> pathForX = NodeToRootPathHelper(root, x);
        ArrayList<Integer> pathForY = NodeToRootPathHelper(root, y);

        System.out.println(x + " -> " + pathForX);
        System.out.println(y + " -> " + pathForY);

        int ptX = pathForX.size() - 1;
        int ptY = pathForY.size() - 1;

        // Node1 -> ... -> Root
        // Node2 -> ... -> Root
        // Start from end and go back till 1st difference is found
        while (ptX >= 0 && ptY >= 0) {
            if (pathForX.get(ptX) != pathForY.get(ptY)) { // different nodes
                break;
            }
            ptX--;
            ptY--;
        }
        ptX++; // increase one to get to LCA
        ptY++;
        // pointers point to index/distance from node1, node2 to LCA node
        System.out.println("LCA -> " + pathForX.get(ptX));
    }

    // 15. Distance between nodes
    private static void DistanceBetweenNodes(Node root, int x, int y) {
        System.out.println("==== Distance Between Nodes ====");
        ArrayList<Integer> pathForX = NodeToRootPathHelper(root, x);
        ArrayList<Integer> pathForY = NodeToRootPathHelper(root, y);

        System.out.println(x + " -> " + pathForX);
        System.out.println(y + " -> " + pathForY);

        int ptX = pathForX.size() - 1;
        int ptY = pathForY.size() - 1;

        // Node1 -> ... -> Root
        // Node2 -> ... -> Root
        // Start from end and go back till 1st difference is found
        while (ptX >= 0 && ptY >= 0) {
            if (pathForX.get(ptX) != pathForY.get(ptY)) { // different nodes
                break;
            }
            ptX--;
            ptY--;
        }
        
        // pointers point to index/distance from node1, node2 to LCA node
        System.out.println("Distance -> " + (ptX + ptY + 2));
    }

    // 16. Are Trees Similar
    private static boolean AreTreesSimilarHelper (Node one, Node two) {
        if (one.children.size() != two.children.size()) {
            return false;
        }

        for (int i=0; i<one.children.size(); i++) {
            boolean isChildSimilar = AreTreesSimilarHelper(one.children.get(i), two.children.get(i));
            if (isChildSimilar == false) {
                return false;
            }
        }

        return true;
    }
    private static void AreTreesSimilar(Node root1, Node root2) {
        System.out.println("==== Are Trees Similar ====");
        boolean result = AreTreesSimilarHelper(root1, root2);
        System.out.println("Result -> " + result);
    }

    // 17. Are Trees Mirror
    private static boolean AreTreesMirrorHelper(Node node1, Node node2) {
        if (node1.children.size() != node2.children.size()) {
            return false;
        }

        // check children
        for (int i=0,j=node2.children.size()-1; i<node1.children.size() && j >=0; i++, j--) {
            if (AreTreesMirrorHelper(node1.children.get(i), node2.children.get(j)) == false) {
                return false;
            }
        }

        return true;
    }
    private static void AreTreesMirror(Node root1, Node root2) {
        System.out.println("==== Are Trees Mirror ====");
        boolean result = AreTreesMirrorHelper(root1, root2);
        System.out.println("Result -> " + result);
    }

    // 17. Is tree symmetric
    private static void IsTreeSymmetric(Node root) {
        System.out.println("==== Are Trees Mirror ====");
        boolean result = AreTreesMirrorHelper(root, root); // Mirror of self
        System.out.println("Result -> " + result);
    }

    // 18. Multisolver
    private static int size = 0;
    private static int min = Integer.MAX_VALUE;
    private static int max = Integer.MIN_VALUE;
    private static int height = 0;

    private static void MultisolverHelper(Node node, int depth) {
        // preorder -> for node
        size++;
        min = Math.min(min, node.data);
        max = Math.max(max, node.data);
        height = Math.max(height, depth);

        // traverse child
        for (Node child: node.children) {
            MultisolverHelper(child, depth + 1);
        }
    }
    private static void Multisolver(Node root) {
        System.out.println("==== Mutisolver ====");
        DisplayTree(root);
        // reset default values
        size = 0;
        min = Integer.MAX_VALUE;
        max = Integer.MIN_VALUE;
        height = 0;
        MultisolverHelper(root, 0);
        System.out.println("\n");
        System.out.println("Size -> " + size);
        System.out.println("Height -> " + height);
        System.out.println("Min -> " + min);
        System.out.println("Max -> " + max);
    }

    // 19. PredecessorAndSuccessor
    private static Integer predecessor = null;
    private static Integer succcessor = null;
    private static int state = 0;
    // Cant use true/false as found -> we traverse after found also
    // states 
    // 0 -> not found 
    // 1 -> node found
    // 2 -> successor found

    private static void PredecessorAndSuccessorHelper(Node node, int x) {
        // traverse over child
        if (state == 0) {
            if (node.data == x) {
                state = 1;
            } else {
                predecessor = node.data;
            }
        } else if (state == 1) {
            succcessor = node.data;
            state = 2;
        } else { // state = 2
            // do nothing
        }

        for (Node child: node.children) {
            PredecessorAndSuccessorHelper(child, x);
        }
    }
    private static void PredecessorAndSuccessor(Node root, int x) {
        System.out.println("==== PredecessorAndSuccessor ====");
        PredecessorAndSuccessorHelper(root, x);
        System.out.println("Predecessor -> " + predecessor);
        System.out.println("Successor   -> " + succcessor);
    }

    // 20. Ceil and FLoor
    private static int ceil = Integer.MAX_VALUE; // smallest among larger
    private static int floor = Integer.MIN_VALUE; // largest among smaller

    private static void CeilAndFloorHelper(Node node, int x) {
        // ceil
        if (node.data > x) {    // among larger
            if (node.data < ceil) {    // smallest
                ceil = node.data;
            }
        }

        // floor
        if (node.data < x) {   // among smaller
            if (node.data > floor) {  // largest
                floor = node.data;
            }
        }

        for (Node child: node.children) {
            CeilAndFloorHelper(child, x);
        }
    }
    private static void CeilAndFloor(Node root, int x) {
        System.out.println("==== CeilAndFloor ====");
        ceil = Integer.MAX_VALUE; 
        floor = Integer.MIN_VALUE; 
        System.out.println("For node : " + x);
        CeilAndFloorHelper(root, x);
        System.out.println("Ceil  -> " + ceil);
        System.out.println("Floor -> " + floor);
    }

    // 21. KthLargestElement -> Time: O(n*k) -> k times * (traversal over all nodes/ O(n))
    private static void KthLargestElement(Node root, int K) {
        System.out.println("==== KthLargestElement ====");
        CeilAndFloor(root, Integer.MAX_VALUE); // 1st time
        for (int i=2; i<=K; i++) {   // K-1 times
            CeilAndFloor(root, floor); 
        }
        int result = floor; // floor has kth largest value
        System.out.println("\nKthLargest  -> " + result);
    }
    
    // 22. Node with Maximum Subtree Sum
    // Return sum and calculate max sum & max sum node
    private static int maxSum = Integer.MIN_VALUE;
    private static Node maxSumNode = null;

    private static int MaxSubtreeSumNodeHelper (Node node) { // Time:O(N)
        int childSum = 0;
        for (Node child: node.children) {
            childSum += MaxSubtreeSumNodeHelper(child);
        }
        int nodeSum = childSum + node.data;

        // Update max sum Node - check in postorder
        if (nodeSum > maxSum) {
            maxSum = nodeSum;
            maxSumNode = node;
        }

        return nodeSum;
    }
    private static void MaxSubtreeSumNode (Node root) {
        System.out.println("==== MaxSubtreeSumNode ====");
        int result = MaxSubtreeSumNodeHelper(root);
        System.out.println("MaxSumNode  -> " + maxSumNode.data + ", Sum -> " + maxSum + "\n");
    }

    // 23. DiameterOfTree -> max edges between 2 nodes 
    // Return height/deepest node and calculate diameter

    // Find max 2 deepest nodes from children and add 2
    //          a       
    //      b   c   d 
    //     2   3    3
    // answer = c -> a -> d = 3 + 1 + 1 + 3 = 8 
    private static int diameter = 0;
    private static int DiameterOfTreeHelper (Node node) {
        if (node.children.size() == 0) {
            return 0;
        }

        int maxHeight = -1;
        int secondMaxHeight = -1;
        for (Node child: node.children) {
            int childHeight = DiameterOfTreeHelper(child);
            if (childHeight > maxHeight) {
                secondMaxHeight = maxHeight;
                maxHeight = childHeight;
            } else if (childHeight > secondMaxHeight) {
                secondMaxHeight = childHeight; 
            }
        }

        int nodeDiameter = maxHeight + secondMaxHeight + 2; // max 2 child via cuurent node
        if (nodeDiameter > diameter) {
            diameter = nodeDiameter;
        }

        return maxHeight + 1; // return node height
    }
    private static void DiameterOfTree (Node root) {
        System.out.println("==== DiameterOfTree ====");
        diameter = Integer.MIN_VALUE;
        DiameterOfTreeHelper(root);
        System.out.println("Diameter  -> " + diameter + "\n");
    }
    
    // 24. Iterative traversal
    private static class Pair {
        Node node;
        int state;

        Pair(Node node) {
            this.node = node;
            this.state = -1;
        }
    }
    private static void IterativeTraversal (Node root) {
        System.out.println("==== IterativeTraversal ====");
        Stack<Pair> st = new Stack<>();

        st.push(new Pair(root));
        String preorder = "";
        String postorder = "";
        while (st.size() > 0) {
            Pair top = st.peek();
            if (top.state == -1) {
                // System.out.println("Pre  -> " + top.node.data);
                preorder += top.node.data + ", ";
                top.state++;
            } else if (top.state == top.node.children.size()) {
                // System.out.println("Post -> " + top.node.data);
                postorder += top.node.data + ", ";
                st.pop();
            } else {
                st.push(new Pair(top.node.children.get(top.state)));
                top.state++;
            }
        }
        System.out.println("Preorder  -> " + preorder);
        System.out.println("Postorder -> " + postorder);
    }

    public static void main(String[] args) {
        int [] arr = {10,20,50,-1,60,-1,-1,30,70,-1,80,110,-1,120,-1,-1,90,-1,-1,40,100,-1,-1,-1};
        
        Node root = BuildTree(arr);
        System.out.println("\nRoot Node -> " + root.data + "\n");
        
        // System.out.println("==== DisplayTree ====");
        // DisplayTree(root);
        // System.out.println("=====================");
        
        // int size = SizeOfTree(root);
        // System.out.println("\nSize / Nodes -> " + size + "\n");
        
        // int max = MaxOfTree(root);
        // System.out.println("\nMax of Tree -> " + max + "\n");
        
        // int height = HeightOfTree(root);
        // System.out.println("\nHeight of Tree -> " + height + "\n");
        
        // System.out.println("\nEuler Traversal of Tree\n");
        // EulerTraversal(root);
        
        // LevelOrderTraversal(root);
        
        // LevelOrderLineWiseTraversal(root);
        // LevelOrderLineWiseTraversalUsingSize(root);
        // LevelOrderLineWiseTraversalUsingNull(root);
        
        // LevelOrderZigZagTraversal(root);
        
        // mirrorTree(root);
        
        removeLeafNodes(root);
        
        // LinearizeTree(root);
        
        // FindNodeInTree(root, 110);
        
        // NodeToRootPath(root, 20);
        // NodeToRootPath(root, 100);
        // NodeToRootPath(root, 50);
        
        // LowestCommonAncestor(root, 110, 100);
        
        // DistanceBetweenNodes(root, 110, 120);
        // DistanceBetweenNodes(root, 110, 100);
        
        // AreTreesSimilar(root, root); // ans -> true
        
        // AreTreesMirror(root, root); // ans -> false
        
        // Symmetric -> own is mirror image 
        // IsTreeSymmetric(root); // ans -> true if isMirrorImage of self
        
        // Multisolver(root);

        // PredecessorAndSuccessor(root, 90);
        
        // CeilAndFloor(root, 90);
        // CeilAndFloor(root, 120);
        // CeilAndFloor(root, 10);
        
        // KthLargestElement(root, 3);
        
        // int [] arr2 = {10,20,-50,-1,-60,-1,-1,30,-70,-1,80,-110,-1,120,-1,-1,90,-1,-1,40,-100,-1,-1,-1};
        // Node root2 = BuildTree(arr2);
        // MaxSubtreeSumNode(root2);

        // DiameterOfTree(root);

        // IterativeTraversal(root);
    }
}