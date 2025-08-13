import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
class StackAndQueue {
    // 1. 
    private static void DuplicateBrackets(String input) { // Time: O(n)
        Stack<Character> st = new Stack<>();
        boolean res = false;
        for (char ch : input.toCharArray()) {
            if (ch == ')') { // pop
                if (st.peek() == '(') {  // if top is (
                    // duplicacy -> as ( is found in stack again
                    res = true;
                    break;
                } else {
                    while (st.peek() != '(') {
                        st.pop();
                    }
                    st.pop(); // *** pop '('
                }
            } else { // any other char or ( => push
                st.push(ch);
            }
            System.out.println(st);
        }

        System.out.println("Duplicate Brackets -> " + input + " -> " + res);
        return;
    }

    // 2. 
    private static void BalancedBrackets(String input) { // Time: O(n)
        // Cases 
        // True -> Balanced, 
        // False -> Mismatch, 
        // False -> More closing (stack empty in mid), 
        // False -> More opening (stack not empty at end)
        Stack<Character> st = new Stack<>();
        System.out.print("\n" + input + " => ");

        // Only add brackets to stack -> not required in between chars
        for (char ch : input.toCharArray()) {
            if (ch == '(' || ch == '{' || ch == '[') {
                st.push(ch);
            } else if (ch == ')') {
                boolean res = handleClosingBracket(st, '(');
                if (res == false) {
                    return;
                }
            } else if (ch == '}') {
                boolean res = handleClosingBracket(st, '{');
                if (res == false) {
                    return;
                }
            } else if (ch == ']') {
                boolean res = handleClosingBracket(st, '[');
                if (res == false) {
                    return;
                }
            } else {
                // do nothing
            }
        }

        // Stack should be empty if balanced
        if (st.size() != 0) {
            System.out.println("False -> Extra opening brackets");
        } else {
            System.out.println("True -> Balanced");
        }

        return;
    }

    private static boolean handleClosingBracket(Stack<Character> st, char correspondingOpeningBracket) {
        if (st.size() == 0) { // no elemnts in stack > more closing brackets
            System.out.println("False -> Extra closing brackets");
            return false;
        } else if (st.peek() != correspondingOpeningBracket) { // mismatch
            System.out.println("False -> Mismatch");
            return false;
        } else {    // correct bracket present
            st.pop(); // pop the match
            return true;
        }
    }

    // 3.
    private static void NextGreaterElementOnRight(int[] arr) {
        // How to think
        // 1. Understand problem 
        // 2. Normal brute force
        // 3. Optimising > a. right to left ** , b. stack - maintains order and revisit elements
        //  [this I couldn't think at first]
        Stack<Integer> st = new Stack<>();
        int [] res = new int[arr.length];
        
        res[arr.length-1] = -1; // last element -> nothing to right
        st.push(arr[arr.length-1]);
        for (int i=arr.length-2; i>=0; i--) {
            // Pop elements smaller than current from stack
            while (st.size() > 0 && st.peek() < arr[i]) {
                st.pop();
            }

            // update res
            if (st.size() == 0) {
                res[i] = -1;
            } else {
                res[i] = st.peek();
            }

            // Push current to stack -> for next left elements
            st.push(arr[i]);
        }

        System.out.println("\n Next greater Element to Right");
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(res));
    }

    private static void NextGreaterElementOnRight2(int[] arr) {
        // Alternative left to right approach
        Stack<Integer> st = new Stack<>(); // Store indexes
        int [] res = new int[arr.length];
        
        st.push(0); // 1st element
        for (int i=1; i<arr.length; i++) {
            // Pop elements smaller than current from stack & update as their result
            while (!st.isEmpty() && arr[st.peek()] < arr[i]) {
                int idx = st.peek();
                res[idx] = arr[i]; // update current as NGE for popped from stack
                st.pop();
            }

            // Push current to stack -> for next left elements
            st.push(i);
        }

        // Now if remaining on stack -> all -1
        while (!st.isEmpty()) {
            int idx = st.peek();
            res[idx] = -1;
            st.pop();
        }

        System.out.println("\n Next greater Element to Right: Alternative approach");
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(res));
    }

    private static void NextSmallerElementOnRight(int[] arr) {
        // How to think
        // 1. Understand problem 
        // 2. Normal brute force
        // 3. Optimising > a. right to left ** , b. stack - maintains order and revisit elements
        //  [this I couldn't think at first]
        Stack<Integer> st = new Stack<>();
        int [] res = new int[arr.length];
        
        res[arr.length-1] = -1; // last element -> nothing to right
        st.push(arr[arr.length-1]);
        for (int i=arr.length-2; i>=0; i--) {
            // Pop elements larger than current from stack
            while (st.size() > 0 && st.peek() > arr[i]) {
                st.pop();
            }

            // update res
            if (st.size() == 0) {
                res[i] = -1;
            } else {
                res[i] = st.peek();
            }

            // Push current to stack -> for next left elements
            st.push(arr[i]);
        }

        System.out.println("\n Next Smaller Element to Right");
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(res));
    }

    // 4. 
    private static int[] NextGreaterElementOnLeft(int [] arr) {
        int[] res = new int[arr.length];
        Stack<Integer> st = new Stack<>(); // store indexes
        
        // 1st element
        st.push(0);
        res[0] = 1;

        for (int i=1; i<arr.length-1; i++) {
            while (!st.isEmpty() && st.peek() < arr[i]) { // pop smaller elements
                st.pop();
            }

            // update NGE on Left for i
            if (st.isEmpty()) {
                res[i] = i + -1;
            } else {
                res[i] = arr[st.peek()];
            }

            st.push(i); // push current
        }

        return res;
    }
    private static void StockSpan(int[] arr) {
        int[] span = new int[arr.length];
        Stack<Integer> st = new Stack<>(); // store indexes
        
        // 1st element
        st.push(0);
        span[0] = 1;

        for (int i=1; i<arr.length-1; i++) {
            while (!st.isEmpty() && st.peek() < arr[i]) { // pop smaller elements
                st.pop();
            }

            // update SPAN using NGE on Left for i
            if (st.isEmpty()) {
                span[i] = i + 1; // largest so far
            } else {
                span[i] = i - st.peek();  // diff between indexes
            }

            st.push(i); // push current
        }

        System.out.println("\nStock Span");
        System.out.println(Arrays.toString(arr));
        System.out.println(Arrays.toString(span));
    }

    public static void main(String[] args) {
        // 1. Duplicate Brackets
        // DuplicateBrackets("((a+b)+(c+d))");  // false
        // DuplicateBrackets("(a+b)+((c+d))");  // true

        // 2. Balanced Brackets
        // BalancedBrackets("[(a+b)+{(c+d)*(e/f)}]"); // balanced
        // BalancedBrackets("((a+b)"); // extra opening
        // BalancedBrackets("[(a+b)]}"); // extra closing
        // BalancedBrackets("[(a+b}]"); // mismatch
    
        // 3. Next Greater / Smaller Element on Right
        // NextGreaterElementOnRight(new int[]{2,5,9,3,1,12,6,8,7}); // right to left -> -, a, +
        // NextGreaterElementOnRight2(new int[]{2,5,9,3,1,12,6,8,7}); // left to right -> (- & a), +
        // NextSmallerElementOnRight(new int[]{2,5,9,3,1,12,6,8,7});

        // 4. Stock Span
        StockSpan(new int[]{2,5,9,3,1,12,6,8,7});
    }


    

        
}
