import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
class LL {
    private static class Node {
        int data;
        Node next;

        Node (int val) {
            data = val;
            next = null;
        }
    }

    private static class MyLinkedList {
        private Node head;
        private Node tail;
        private int size;

        MyLinkedList () {
            head = null;
            tail = null;
            size = 0;
        }

        public MyLinkedList(int[] nums) {
            for (int num: nums) {
                addLast(num);
            }
        }

        void addLast (int val) {    // Time: O(1)
            Node node = new Node(val);

            if (size == 0) { // Empty List -> single element
                head = tail = node;
            } else {   // Add to existing list
                tail.next = node;  // add to list
                tail = node;    // update tail to curr node
            }
            size++;  // update size
        }

        void addFirst (int val) {  // Time: O(1)
            Node node = new Node(val);

            if (size == 0) {    // Empty list
                head = tail = node;
            } else {    // Otherwise
                node.next = head; // point next of new node to head
                head = node;    // update head to new node
            }
            size++;  // update size
        }

        void addAt (int val, int idx) {  // Time: O(n)
            if (idx < 0 || idx > size) {
                System.out.println("Invalid arguments");
            } else if (idx == 0) {
                addFirst(val);
            } else if (idx == size) {
                addLast(val);
            } else {
                Node node = new Node(val);

                Node temp = head;
                // We need to get pointer to prev node to idx
                for (int i=0; i<idx-1; i++) {
                    temp = temp.next;
                }
                // now temp points to prev than idx
                node.next = temp.next;
                temp.next = node;  // update next of current "temp"
                size++;
            }
        }

        void removeFirst () { // Time: O(1)
            System.out.println("==== removeFirst called ====");
            if (size == 0) {    // Empty list
                System.out.println("List is empty");
            } else if (size == 1) { // single element
                head = tail = null;
                size = 0;
            } else {    // Otherwise
                head = head.next; // move to next 
                size--;
            }
        }

        void removeLast () { // Time: O(n)
            if (size == 0) {
                System.out.println("Empty list");
            } else if (size == 1) {
                head = tail = null;
                size = 0;
            } else {
                Node temp = head;

                // We need pointer to 2nd last element
                // Last idx -> size-1
                // 2nd Last idx -> size-2
                for (int i=0; i<size-2; i++) {
                    temp = temp.next;
                }
                // now temp is at 2nd last 

                temp.next = null; // now temp is tail as next is null
                tail = temp;  // update tail
                size--;
            }
        }

        void removeAt (int idx) { // Time: O(n)
            // idx = size points to null
            // last idx -> size-1 as size from {0,1,2, ... ,(size-1)}
            if (idx < 0 || idx >= size) {
                System.out.println("Invalid arguments");                
            } else if (idx == 0) {
                removeFirst();
            } else if (idx == size-1) {
                removeLast();
            } else {
                Node temp = head;
                for (int i=0; i<idx-1; i++) { // goto prev node -> idx-1
                    temp = temp.next;
                }

                temp.next = temp.next.next; // bypass "idx" node
            }
        }

        // >> Get Data from linked list
        int getFirst () { // Time: O(1)
            if (size == 0) {
                System.out.println("List is empty");
                return -1;
            }
            return head.data;
        }

        int getLast () { // Time: O(1)
            if (size == 0) {
                System.out.println("List is empty");
                return -1;
            }
            return tail.data;
        }

        int getAt (int idx) { // Time: O(n)
            if (size == 0) {
                System.out.println("List is empty");
                return -1;
            } else if (idx < 0 || idx >= size) {
                System.out.println("Invalid arguments");
                return -1;
            } else {
                Node temp = head;
                for (int i=0; i<idx; i++) {
                    temp = temp.next;
                }
                return temp.data;
            }
        }

        int size () {    // Time: O(1)
            return size;
        }

        void display () { // Time: O(n)
            System.out.println("==== Display called ====");
            Node temp = head;
            int idx = 0;
            while (temp != null) {
                System.out.print("[" + idx + "|" + temp.data + "]" + " > ");
                temp = temp.next;
                idx++;
            }
            System.out.println();
        }

        // >> Reverse Linked List** V.V.Imp.
        private Node getNodeAt (int idx) {
            Node temp = head;
            for (int i=0; i<idx; i++) {
                temp = temp.next;
            }

            return temp;
        }

        void reverseListDataIterative () { // Time: O(n^2)
            // Use only data property
            // Loop * Fetching 2 nodes
            // Time -> O(n) * O(2*n) -> O(2*n*n) -> O(n^2)

            System.out.println("==== reverseListDataIterative ====");
            int leftIdx = 0;
            int rightIdx = size-1;

            while (leftIdx < rightIdx) {
                Node leftNode = getNodeAt(leftIdx); 
                Node rightNode = getNodeAt(rightIdx); 
                
                // swap nodes
                int temp = leftNode.data;
                leftNode.data = rightNode.data;
                rightNode.data = temp;

                // update left/right indexes
                leftIdx++;
                rightIdx--;
            }
        }

        void reverseListPointerIterative () { // Time: O(n)
            // Use next property / pointer

            Node curr = head;
            Node prev = null;
            Node next = null;

            while (curr != null) {
                // System.out.println(curr.data);
                next = curr.next; // store next **

                curr.next = prev;   // update link > modify next pointer
                
                prev = curr;   // update prev
                curr = next;    // update next
            }
            // now curr = null, prev = last node 

            // update head and tail / or swap head and tail
            tail = head;
            head = prev;
        }


        Node KthNodeFromLast (int K) { // Time: O(n)
            // Can't use size property
            // Do it in Single traversal
            Node prev = head;
            Node curr = head;
            
            // Trick -> create a gap of K between prev and curr
            // Then if we stop when curr is at last node 
            // -> prev points to Kth node from last

            for (int i=0; i<K; i++) {
                if (curr.next != null) {
                    curr = curr.next;
                }
            }

            // stop at last node
            while (curr.next != null) {
                prev = prev.next;
                curr = curr.next;
            }

            System.out.println(K + "th Node: " + prev.data);

            return prev;
        }

        Node middleOfList () { // Time:O(n)
            // Approach-1 Two traversals
            // Time: O(2*n) -> size then find node at idx = size/2
            // But we need single traversal
            Node slow = head;
            Node fast = head;

            while (fast.next != null && fast.next.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }

            System.out.println("Middle Node: " + slow.data);
            return slow;
        }

        // Fold a Linked List
        private Node foldLeft = null;
        private void foldHelper (Node foldRight, int idx) {
            if (foldRight == null) {
                return;
            }

            foldHelper(foldRight.next, idx+1); // move right

            // logic -> runs till half
            if (idx > this.size/2) {
                System.out.println(foldLeft.data + " , " + foldRight.data);
                Node leftNext = foldLeft.next;  // left = left.next 
                foldLeft.next = foldRight;      // left.next -> right
                foldRight.next = leftNext;      // right.next -> next
                
                foldLeft = leftNext; // move next
            } else if (idx == this.size/2){  // *** Set tail correctly
                this.tail = foldRight;
                this.tail.next = null;
            }
        }

        public void FoldLinkedList () {
            foldLeft = this.head;
            foldHelper(this.head, 0);
        }
    }

    // Merge 2 sorted linked lists -> Time: O(n+m), Space:O(n+m)
    private static MyLinkedList mergeTwoSortedLinkedList (MyLinkedList list1, MyLinkedList list2) {
        Node one = list1.head;
        Node two = list2.head;

        MyLinkedList res = new MyLinkedList();

        while (one != null && two != null) {
            if (one.data < two.data) {
                res.addLast(one.data);
                one = one.next;
            } else {
                res.addLast(two.data);
                two = two.next;
            }
        }

        while (one != null) {
            res.addLast(one.data);
            one = one.next;
        }
        
        while (two != null) {
            res.addLast(two.data);
            two = two.next;
        }

        return res;
    }
    
    // Merge Sort -> Time: O(n*log(n))
    private static Node findMidNode (Node head, Node tail) {
        Node slow = head;
        Node fast = head;

        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    private static MyLinkedList mergeSort (Node head, Node tail) {
        if (head == tail) {
            MyLinkedList baseResult = new MyLinkedList();
            baseResult.addLast(head.data);
            return baseResult;
        }
        
        Node mid = findMidNode(head, tail);

        MyLinkedList firstSortedHalf = mergeSort(head, mid);
        MyLinkedList secondSortedHalf = mergeSort(mid.next, tail);

        MyLinkedList mergedList = mergeTwoSortedLinkedList(firstSortedHalf, secondSortedHalf);
        return mergedList;
    }

    // Remove duplicates from sorted list
    private static void removeDuplicates (MyLinkedList list) { // Time:O(n)
        Node end = list.head;
        Node curr = list.head;

        while (curr != null && curr.next != null) {
            // if data is not equal update next ptr and move tail
            if (end.data != curr.data) {
                end.next = curr;
                end = curr;
            }
            curr = curr.next;
        }
        
        end.next = null; // mark end as tailNode
    }

    // Odd Even LinkedList
    // Bring all odd at front and even at back
    private static void oddEvenLinkedList (MyLinkedList list) { // Time : O(n), Space: O(1)
        System.out.println("oddEvenLinkedList");
        Node oddHead = new Node(-1);
        Node oddTail = oddHead;
        
        Node evenHead = new Node(-1);
        Node evenTail = evenHead;

        Node curr = list.head;
        while (curr != null) {  // traverse list
            if (curr.data % 2 == 0) { // even data
                evenTail.next = curr;
                evenTail = curr;
            } else { // odd data
                oddTail.next = curr;
                oddTail = curr;
            }
            curr = curr.next; // move to next element in list
        }

        oddTail.next = null;
        evenTail.next = null;

        if (oddHead.next != null && evenHead.next != null) {
            oddTail.next = evenHead.next;
            oddHead = oddHead.next;

            list.head = oddHead;
            list.tail = evenTail;
        } else if (oddHead.next != null) {      // only odd
            oddHead = oddHead.next;

            list.head = oddHead;
            list.tail = oddTail;
        } else {    // only even
            evenHead = evenHead.next;

            list.head = evenHead;
            list.tail = evenTail;
        }
    }

    // KReverseLinkedList
    private static void KReverseLinkedList (MyLinkedList list, int K) { // Time: O(n)
        MyLinkedList prev = null;

        while (list.size() > 0) {
            MyLinkedList curr = new MyLinkedList();
            for (int i=1; i<=K && list.size > 0; i++) {
                int val = list.getFirst();
                list.removeFirst();
                curr.addFirst(val); // add in reverse order
            }

            if (prev == null) {
                prev = curr;
            } else {
                prev.tail.next = curr.head;
                prev.tail = curr.tail;
                prev.size += curr.size;
            }
        }

        list.head = prev.head;
        list.tail = prev.tail;
        list.size = prev.size;
    }

    private static void displayReverseRecursive (Node head) { // Time: O(n)
        if (head == null) {
            return;
        }

        displayReverseRecursive(head.next);
        System.out.print(head.data + " , ");
    }

    // Reverse List -> Pointer Recursive
    private static void reverseListRecursivePointerHelper (Node node) {
        if (node == null) {
            return;
        }

        reverseListRecursivePointerHelper(node.next);
        if (node.next != null) { // update next node's next pointer to current node
            node.next.next = node;
        }
    }
    private static void reverseListRecursivePointer (MyLinkedList list) {
        reverseListRecursivePointerHelper(list.head);
        list.head.next = null;
        // swap head and tail
        Node temp = list.head;
        list.head = list.tail;
        list.tail = list.head;
    }

    // Reverse List -> Data Recursive
    private static Node leftNode = null;
    private static int listSize = 0;
    private static void reverseListDataRecursiveHelper (Node rightNode, int idx) {
        if (rightNode == null) {
            return;
        }

        // move with recusion -> right node
        reverseListDataRecursiveHelper(rightNode.next, idx+1);

        // Only till we reach half
        if (idx >= listSize/2) {
            System.out.println(leftNode.data + " , " + rightNode.data);
            // swap left & right node
            int temp = leftNode.data;
            leftNode.data = rightNode.data;
            rightNode.data = temp;
        } 

        // move left node
        leftNode = leftNode.next;
        return;
    }
    public static void reverseListDataRecursive (MyLinkedList list) {
        leftNode = list.head;
        listSize = list.size();
        reverseListDataRecursiveHelper (list.head, 0);
    }

    // Add two linked list
    private static MyLinkedList addListResult = null;
    private static int addListHelper (Node one, int placeValue1, Node two, int placeValue2) {
        if (one == null && two == null) {
            return 0;
        }

        int carry;
        int data = 0;

        // move larger one first
        if (placeValue1 > placeValue2) { // list one is larger
            carry = addListHelper(one.next, placeValue1-1, two, placeValue2); // move one
            data = one.data + carry;
        } else if (placeValue1 < placeValue2) { // list one is smaller
            carry = addListHelper(one, placeValue1, two.next, placeValue2-1); // move two
            data = two.data + carry;
        } else {
            // go to null
            carry = addListHelper(one.next, placeValue1-1, two.next, placeValue2-1);
            data = one.data + two.data + carry;
        }
        
        // update data and carry
        carry = data / 10;
        data = data % 10;
        addListResult.addFirst(data);
        return carry;
    }

    public static MyLinkedList addList (MyLinkedList one, MyLinkedList two) {
        addListResult = new MyLinkedList();
        int carry = addListHelper(one.head, one.size(), two.head, two.size());
        if (carry > 0) {
            addListResult.addFirst(carry);
        }
        return addListResult;
    }

    // Is list Palindrome
    private static Node left = null;
    private static boolean isPalindromeHelper (Node right) {
        if (right == null) {
            return true;
        }
        
        boolean rightResult = isPalindromeHelper(right.next); // move right
        if (rightResult == false) { // return current false comparison
            return false;           // no need to check next
        } else if (left.data != right.data) { // current dont match
            return false;
        } else {        // current match
            left = left.next; // move left
            return true;
        }
    }

    private static boolean isPalindrome (MyLinkedList list) {
        left = list.head;
        return isPalindromeHelper(list.head);
    }


    // Find intersection of Two LinkedList
    // Time: O(n)
    private static void findIntersection (MyLinkedList listOne, MyLinkedList listTwo) {
        Node one = listOne.head;
        Node two = listTwo.head;

        int sizeDifference = Math.abs(listOne.size() - listTwo.size()); 

        // list one is larger
        if (listOne.size() > listTwo.size()) {
            for (int i=1; i<=sizeDifference; i++) {
                one = one.next;
            }
        } else { // list two is larger
            for (int i=1; i<=sizeDifference; i++) {
                two = two.next;
            }
        }

        // Now both heads are at same distance from the intersection
        // We don't know how many nodes ahead
        // Move both together and find intersection

        while (one != null && two != null) {
            if (one.data == two.data) {
                System.out.println("\nIntersection Node -> " + one.data);
                return;
            }

            one = one.next;
            two = two.next;
        }

        return;
    }

    // Adapter Questions : One data struct to another
    private static class LLToStackAdapter {
        private LinkedList<Integer> list;

        public LLToStackAdapter () {
            list = new LinkedList<>();
        }

        void push (int val) {
            list.addFirst(val);
        }

        int size () {
            return list.size();
        }

        int pop () {
            if (size() == 0) {
                System.out.println("Stack underflow");
                return -1;
            } else {
                int top = list.getFirst();
                list.removeFirst();
                return top;
            }
        }

        int top () {
            if (size() == 0) {
                System.out.println("Stack underflow");
                return -1;
            } else {
                return list.getFirst();
            }
        }
    }

    private static class LLToQueueAdapter {
        private LinkedList<Integer> list;

        public LLToQueueAdapter () {
            list = new LinkedList<>();
        }

        void add (int val) {
            list.addLast(val);
        }

        int size() {
            return list.size();
        }

        int remove () {
            if (size() == 0) {
                System.out.println("Queue Underflow");
                return -1;
            } else {
                return list.removeFirst();
            }
        }

        int peek () {
            if (size() == 0) {
                System.out.println("Queue Underflow");
                return -1;
            } else {
                return list.getFirst();
            }
        }
    }

    public static void main(String[] args) {
        MyLinkedList list = new MyLinkedList();
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);
        
        list.addLast(40);
        list.addLast(50);
        list.addLast(60);

        // list.display();
        // System.out.println("Size: " + list.size());
        
        // list.removeFirst();
        
        // list.display();
        // System.out.println("Size: " + list.size());

        // System.out.println("At head: " + list.getFirst());
        // System.out.println("At last: " + list.getLast());
        // System.out.println("At idx 2: " + list.getAt(2));
        // System.out.println("At idx 5: " + list.getAt(5));

        // list.addFirst(40);
        // list.addFirst(50);

        // list.addAt(40, 0);
        // list.addAt(50, list.size());
        // list.addAt(60, 2);
        // list.display();

        // list.reverseListDataIterative();
        // list.reverseListPointerIterative();

        // list.removeAt(2);
        // list.display();
        // list.removeAt(3);
        // list.display();


        // LLToStackAdapter stack = new LLToStackAdapter();
        // stack.push(10);
        // stack.push(20);
        // stack.push(30);
        // System.out.println("Top: " + stack.top());
        // System.out.println("Popped: " + stack.pop());
        // System.out.println("Top: " + stack.top());

        // LLToQueueAdapter queue = new LLToQueueAdapter();
        // queue.add(10);
        // queue.add(20);
        // queue.add(30);
        // System.out.println("Peek: " + queue.peek());
        // System.out.println("Popped: " + queue.remove());
        // System.out.println("Peek: " + queue.peek());

        // list.KthNodeFromLast(0);
        // list.KthNodeFromLast(1);
        // list.KthNodeFromLast(2);
        // list.KthNodeFromLast(3);

        // list.display();
        // list.middleOfList();

        // list.addLast(70);
        // list.display();
        // list.middleOfList();

        // MyLinkedList mergedList = mergeTwoSortedLinkedList(
        //     new MyLinkedList(new int []{10, 20, 30}),
        //     new MyLinkedList(new int []{7, 9, 12, 35, 55, 60})
        // );
        // mergedList.display();

        // MyLinkedList unsortedList = new MyLinkedList(new int[]{2,8,1,7,4,6,5});
        // MyLinkedList sortedList = mergeSort(unsortedList.head, unsortedList.tail);
        // sortedList.display();

        // MyLinkedList sortedListWithDuplicates = new MyLinkedList(new int[]{2,2,2,3,3,4,5,5,5,5});
        // removeDuplicates(sortedListWithDuplicates);
        // sortedListWithDuplicates.display();

        // MyLinkedList mixList = new MyLinkedList(new int[]{2,9,7,8,1,6,5,4});
        // oddEvenLinkedList(mixList);
        // mixList.display();
        // MyLinkedList oddList = new MyLinkedList(new int[]{9,7,1,5,3});
        // oddEvenLinkedList(oddList);
        // oddList.display();
        // MyLinkedList evenList = new MyLinkedList(new int[]{2,8,6,4,12});
        // oddEvenLinkedList(evenList);
        // evenList.display();

        // list.display();
        // KReverseLinkedList(list, 2);
        // list.display();

        // displayReverseRecursive(list.head);

        // reverseListRecursivePointer(list);
        // list.display();

        // reverseListDataRecursive(list);
        // list.display();

        // list.FoldLinkedList();
        // list.display();

        // MyLinkedList res = addList(new MyLinkedList(new int[]{9,8,7}), new MyLinkedList(new int[]{6,5}));
        // res.display();

        findIntersection(new MyLinkedList(new int[]{0,1,2,3,4,5,6}), new MyLinkedList(new int[]{9,8,4,5,6}));
    }
}