// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.io.*;
import java.util.*;

@SuppressWarnings("unused")
class HelloWorld {
    public static void main(String[] args) {
        System.out.println("== Start ==");
        
        Scanner scn = new Scanner(System.in);
        // int x = scn.nextInt();
        // int n = scn.nextInt();
        scn.close();

        // printTargetSumSubsets(new int[]{10,20,30,40,50}, 0, 0, 60, "");
        // NQueens(new int[4][4], 0, "");
        KnightsTour(new int [5][5], 0, 0, 1, 5*5);

        System.out.println("== End ==");
    }

    // 29 - KnightsTour
    public static void printKnightBoard(int [][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void KnightsTour(int [][] board, int row, int col, int move, int N) {
        if (row<0 || row>=board.length || col<0 || col>=board[0].length || board[row][col]!=0) {
            return;
        }
        
        if (move == N) {
            board[row][col] = move;
            printKnightBoard(board);
            board[row][col] = 0;
            return;
        }

        board[row][col] = move; // place knight

        KnightsTour(board, row-1, col-2, move+1, N);
        KnightsTour(board, row-2, col-1, move+1, N);
        KnightsTour(board, row-2, col+1, move+1, N);
        KnightsTour(board, row-1, col+2, move+1, N);
        KnightsTour(board, row+1, col+2, move+1, N);
        KnightsTour(board, row+2, col+1, move+1, N);
        KnightsTour(board, row+2, col-1, move+1, N);
        KnightsTour(board, row+1, col-2, move+1, N);
        
        board[row][col] = 0; // remove knight
    }

    // 28 - N Queens
    public static void printBoard(int [][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print((cell == 1 ? "Q " : ". ") + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    public static boolean isSafeToPlaceQueen(int [][] board, int row, int col) {
        // left diagonal
        for (int i=row, j=col; i>=0 && j>=0; i--, j--) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        // check current column
        for (int i=row; i>=0; i--) {
            if (board[i][col] == 1) {
                return false;
            }
        }

        // right diagonal
        for (int i=row, j=col; i>=0 && j<board[0].length; i--, j++) {
            if (board[i][j] == 1) {
                return false;
            }
        }

        return true;
    }

    public static void NQueens(int [][] board, int row, String ans) {
        if (row == board.length) {
            printBoard(board);
            System.out.println("////////////////////////");
            return;
        }

        for (int col=0; col<board[0].length; col++) {
            if (isSafeToPlaceQueen(board, row, col)) {
                board[row][col] = 1; // place queen
                
                NQueens(board, row+1, ans);
                
                board[row][col] = 0; // remove queen > for any other possible way
            }
        }

        return;
    }

    // 27 Target Sum Subsets
    public static void printTargetSumSubsets(int [] arr, int idx, int sum, int target, String ans) {
        if (idx == arr.length) {
            if (sum == target) {
                System.out.println(ans);
            }

            return;
        }

        // include current
        printTargetSumSubsets(arr, idx+1, sum + arr[idx], target, ans + "," + arr[idx]);

        // exclude current
        printTargetSumSubsets(arr, idx+1, sum, target, ans);
    }

    // 26 - Flood Fill
    // Stupid calls -> smart base case
    public static int [][] visited;
    public static void floodFill(int [][] maze, int row, int col, String path) {
        // base case -> handle exit points & 1's & already visited
        if (row < 0 || row >= maze.length || col < 0 || col >= maze[0].length || maze[row][col] == 1 || visited[row][col] == 1) {
            return;
        }

        if (row == maze.length-1 && col == maze[0].length-1) {
            System.out.println(path);
            return;
        }

        // System.err.println(row + "," + col);
        visited[row][col] = 1; // visit -> mark current (row,col)=1

        // top
        floodFill(maze, row-1, col, path + "T");

        // right
        floodFill(maze, row, col+1, path + "R");

        // down
        floodFill(maze, row+1, col, path + "D");

        // left
        floodFill(maze, row, col-1, path + "L");

        // Unvisit -> to explore all paths
        visited[row][col] = 0; // un-visit -> mark current (row,col)=0 
    } 

    // 25 - print Encodings
    // Encodings -> number -> digits can be 1,2, .. 26 -> alphabets
    // Example -> 123 -> 1 + 23 -> a + w OR 123 -> 12 + 3 -> a + w
    public static void printEncodings(String number, String ans) {
        if (number.length() == 0) {
            System.out.println(ans);
            return;
        } else if (number.length() == 1) { // only single char
            char ch = number.charAt(0);
            if (ch == '0') {
                return;
            } else {
                int charValue = ch - '0';
                char code = (char) ('a' + (charValue - 1));
                printEncodings(number.substring(1), ans + code);
            }
        } else { // more than one chars -> single digit call and two digit calls possible
            // single digit
            char ch = number.charAt(0);
            if (ch == '0') {
                return;
            } else {
                int charValue = ch - '0';
                char code = (char) ('a' + (charValue - 1));
                printEncodings(number.substring(1), ans + code);
            }

            // two-digit
            String ch12 = number.substring(0, 2);
            int char12Value = Integer.parseInt(ch12);

            if (char12Value <= 26) {
                char code = (char) ('a' + (char12Value - 1));
                printEncodings(number.substring(2), ans + code);
            }

        }

    } 

    // 24 - Print permutations
    public static void printPermutations(String str, String ans) {
        if (str.length() == 0) { // all characters included
            System.out.println(ans);
            return;
        }

        for (int i=0; i<str.length(); i++) {
            // Include current character in string
            char currentChar = str.charAt(i);
            String rem = str.substring(0, i) + str.substring(i+1);
            printPermutations(rem, ans + currentChar);
        }
    }

    // 23 - print Maze Paths With jumps
    public static void printMazePathWithJumps(int sr, int sc, int dr, int dc, String path) {
        if (sr == dr && sc == dc) { // reached ground
            System.out.println(path);
            return;
        }

        if (sc < 0 || sr > dr || sc < 0 || sc > dc) { // invalid path
            return;
        }
        for (int step=1; step<=dc-sc; step++) {
            printMazePathWithJumps(sr, sc+1, dr, dc, path + "h" + step); // horizontal
        }
        for (int step=1; step<=dr-sr; step++) {
            printMazePathWithJumps(sr+1, sc, dr, dc, path + "v" + step); // vertical
        }
        for (int step=1; step<=dr-sr && step<=dc-sc; step++) {
            printMazePathWithJumps(sr+1, sc+1, dr, dc, path + "d" + step); // diagonal
        }
    }

    // 22 - print Maze Paths
    public static void printMazePath(int sr, int sc, int dr, int dc, String path) {
        if (sr == dr && sc == dc) { // reached ground
            System.out.println(path);
            return;
        }

        if (sc < 0 || sr > dr || sc < 0 || sc > dc) { // invalid path
            return;
        }

        printMazePath(sr, sc+1, dr, dc, path + "h"); // horizontal
        printMazePath(sr+1, sc, dr, dc, path + "v"); // vertical
    }

    // 21 - print Stair Paths
    public static void printStairPath(int n, String path) {
        if (n == 0) { // reached ground
            System.out.println(path);
            return;
        }

        if (n < 0) { // invalid path
            return;
        }

        // step -> 1, 2, 3
        for (int step=1; step <= 3; step++) {
            printStairPath(n-step, path + step);
        }
    }

    // 20 - Print Keypad combinations
    public static String [] codes = {".;", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tu", "vwx", "yz"};
    public static void printKeypadCombinations(String str, String ans) {
        if (str.length() == 0) {
            System.out.println(ans);
            return;
        }

        int digit = str.charAt(0) - '0';
        for (char code : codes[digit].toCharArray()) {
            printKeypadCombinations(str.substring(1), ans + code);
        }

        return;
    }

    // 19b ** Print Subsequence - String has repeating chars > Avoid Extra space
    // 1. With space -> Map/Set -> Will work
    // 2. Without space -> ? -> Restrict for loop -> check prev element -> X
    // Include char -> will cover all | Exclude -> exclude all next contiguous occurances of current character 

    public static void printSubsequenceRepeatingCharWithoutSpace(String str, String ans) {
        if (str.length() == 0) {
            System.out.println(ans);
            return;
        }

        // Include
        char currentChar = str.charAt(0);
        String remaining = str.substring(1);
        printSubsequenceRepeatingCharWithoutSpace(remaining, ans + currentChar);
        
        // Exclude -> if current char is excluded -> exclude all its continuous occurances
        while(remaining.length() > 0 && remaining.charAt(0) == currentChar)
            remaining = remaining.substring(1);
        
        printSubsequenceRepeatingCharWithoutSpace(remaining, ans);
    }


    // 19a - Print Subsequence
    public static void printSubsequence(String str, String ans) {
        if (str.length() == 0) {
            System.out.println(ans);
            return;
        }

        // Include char - YES
        printSubsequence(str.substring(1), ans + str.charAt(0));

        // Exclude char - NO
        printSubsequence(str.substring(1), ans);

        return;
    }

    // 18 - get Maze Paths With Jumps
    public static ArrayList<String> getMazePathsWithJumps(int sr, int sc, int dr, int dc) {
        if (sr == dr && sc == dc) {
            return new ArrayList<>(Arrays.asList(""));
        }

        // if (sr < 0 || sr > dr || sc < 0 || sc > dc) {
        //     return new ArrayList<>();
        // }
        
        ArrayList<String> res = new ArrayList<>();


        // move horizontal
        for (int step=1; step<=dc-sc; step++) {
            for (String path : getMazePathsWithJumps(sr, sc+step, dr, dc)) {
                res.add("h" + step + path);
            }
        }

        // move vertical
        for (int step=1; step<=dr-sr; step++) {
            for (String path : getMazePathsWithJumps(sr+step, sc, dr, dc)) {
                res.add("v" + step + path);
            }
        }

        // move diagonal
        for (int step=1; step<=dr-sr && step<=dc-sc; step++) {
            for (String path : getMazePathsWithJumps(sr+step, sc+step, dr, dc)) {
                res.add("d" + step + path);
            }
        }

        return res;
    }
    // 17 - get Maze Paths
    public static ArrayList<String> getMazePaths(int sr, int sc, int dr, int dc) {
        if (sr < 0 || sr > dr || sc < 0 || sc > dc) {
            return new ArrayList<>();
        }

        if (sr == dr && sc == dc) {
            return new ArrayList<>(Arrays.asList(""));
        }
        
        ArrayList<String> res = new ArrayList<>();

        // move horizontal
        for (String path : getMazePaths(sr, sc+1, dr, dc)) {
            res.add("h" + path);
        }
        
        // move vertical
        for (String path : getMazePaths(sr+1, sc, dr, dc)) {
            res.add("v" + path);
        }

        return res;
    }

    // 16b - Count Stairs Path
    public static int countStairsPath(int n) {
        if (n == 0) { // reached ground -> one path -> ""
            return 1;
        }
        
        if (n < 0) { // invalid path
            return 0;
        }

        int totalWays = 0;

        // take 1 step
        totalWays += countStairsPath(n-1);
        
        // take 2 step
        totalWays += countStairsPath(n-2);
        
        // take 3 step
        totalWays += countStairsPath(n-3);

        return totalWays;
    }
    // 16a - Get Stairs Path
    public static ArrayList<String> getStairsPath(int n) {
        if (n == 0) { // reached ground -> one path -> ""
            return new ArrayList<>(Arrays.asList(""));
        }
        
        if (n < 0) { // invalid path
            return new ArrayList<>();
        }

        ArrayList<String> res = new ArrayList<>();

        // take 1 step
        for (String path: getStairsPath(n-1)) {
            res.add("1" + path);
        }
        
        // take 2 step
        for (String path: getStairsPath(n-2)) {
            res.add("2" + path);
        }

        // take 3 step
        for (String path: getStairsPath(n-3)) {
            res.add("3" + path);
        }

        return res;
    }

    // 15 - Get keypad combinations
    // public static String [] codes = {".;", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tu", "vwx", "yz"};
    public static ArrayList<String> getKeypadCombinations(String number) {
        if (number.length() == 0) {
            return new ArrayList<>(Arrays.asList(""));
        }
        
        // Get recursive combinations
        ArrayList<String> combinations = getKeypadCombinations(number.substring(1));

        // Current digit
        ArrayList<String> res = new ArrayList<>();
        int digit = (int) number.charAt(0) - '0';

        for (char code : codes[digit].toCharArray()) {
            for (String combination : combinations) {
                res.add("" + code + combination); 
            }
        }

        return res;
    }

    
    // 14a - Get all subsequences
    public static ArrayList<String> getSubsequences(String str) {
        if (str.length() == 0) {
            return new ArrayList<String>(Arrays.asList(""));
        }

        char firstChar = str.charAt(0);
        String remainingString = str.substring(1);
        ArrayList<String> remainingRes = getSubsequences(remainingString);

        ArrayList<String> res = new ArrayList<>();
        for (String r : remainingRes) {
            res.add("" + r);
            res.add(firstChar + r);
        }

        return res;
    }

    // 13 - allIndex
    public static int[] allIndex(int[] arr, int i, int x, int count) {
        if (i == arr.length) {
            return new int[count];
        }
        if (arr[i] == x){
            int[] ans = allIndex(arr, i+1, x, count+1);
            ans[count] = i;
            return ans;
        } else {
            return allIndex(arr, i+1, x, count);
        }
    }

    // 12 - lastIndex
    public static int lastIndex(int[] arr, int i, int x) {
        if (i == arr.length) {
            return -1;
        }
        int lastIdx = lastIndex(arr, i+1, x);
        if (lastIdx != -1) {
            return lastIdx;
        } else if (arr[i] == x){
            return i;
        } else {
            return -1;
        }
    }
    // 11 - firstIndex
    public static int firstIndex(int[] arr, int i, int x) {
        if (i == arr.length) {
            return -1;
        }
        if (arr[i] == x) {
            return i;
        } else {
            int firstIdx = firstIndex(arr, i+1, x);
            return firstIdx;
        }
    }

    // 11 - maxOfArray
    public static int maxOfArray(int n, int i, int[] arr) {
        if (i == n) {
            return -1;
        }
        return Math.max(arr[i], maxOfArray(n, i+1, arr));
    }

    // 10 - printArrayReverse
    public static void printArrayReverse(int n, int i, int[] arr) {
        if (i == n) {
            return;
        }
        printArrayReverse(n, i+1, arr);
        System.out.println(arr[i]);
    }

    // 9 - printArray
    public static void printArray(int n, int i, int[] arr) {
        if (i == n) {
            return;
        }
        System.out.println(arr[i]);
        printArray(n, i+1, arr);
    }

    // 8 - Tower of Hanoi
    public static void TowerOfHanoi(int n, String start, String end, String aux) {
        if (n == 0) {
            return;
        }

        TowerOfHanoi(n-1, start, aux, end);
        System.out.println("Move: " + n + " " + start + " -> " + end);
        TowerOfHanoi(n-1, aux, end, start);
    }

    // 7 - Print ZigZag
    public static void printZigZag(int n) {
        if (n == 0) {
            return;
        }

        System.out.println("Pre: " + n);
        printZigZag(n-1);
        System.out.println("In: " + n);
        printZigZag(n-1);
        System.out.println("Post: " + n);
    }

    // 6 - Power Logarithmic
    public static int powerLog(int x, int n) {
        if (n == 0) {
            return 1;
        }
        int xnb2 = powerLog(x, n/2);
        // System.out.println(x + "," + n);
        if (n%2 == 0) { // n is even
            return  xnb2 * xnb2;
        }
        else { // n is odd
            return xnb2 * xnb2 * x;
        }
    }

    // 5 - Power
    public static int power(int x, int n) {
        if (n == 1) {
            return x;
        }
        
        return x*power(x, n-1);
    }

    // 4 - Factorial
    public static int factorial(int n) {
        if (n == 0) {
            return 1;
        }

        return n * factorial(n-1);
    }
    
    // 3 - printDecreasingIncreasing
    public static void printDecreasingIncreasing(int n) {
        if (n == 0) {
            return;
        }
        System.out.println(n);
        printDecreasingIncreasing(n-1);
        System.out.println(n);
    }
    
    // 2 - printIncreasing
    public static void printIncreasing(int n) {
        if (n == 0) {
            return;
        }
        
        printIncreasing(n-1);
        System.out.println(n);
    }
    
    // 1 - printDecreasing
    public static void printDecreasing(int n) {
        if (n == 0) {
            return;
        }
        
        System.out.println(n);
        printDecreasing(n-1);
    }
}