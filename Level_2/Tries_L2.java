import java.util.*;

// A Trie (prefix tree) stores keys as paths from the root, one character per edge.
// This seemingly simple idea delivers advantages that make it the go-to structure whenever prefix handling or alphabet-based ordering matters.

class Tries_L2 {
    static class TrieNode {     // class for TrieNode
        boolean isWord;     // stores the end of word 
        TrieNode [] children = new TrieNode[26]; // child nodes
        int countWord; // count of words
        int countPrefix; // count of prefixes

        TrieNode() {
            isWord = false;
            for(int i=0; i<children.length; i++) {
                children[i] = null;
            }
            countWord = 0;
            countPrefix = 0;
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("isWord=").append(isWord).append(", children=[");
    
            for (int i = 0; i < 26; i++) {
                if (children[i] != null) {
                    sb.append((char) ('a' + i)).append(",");
                }
            }
    
            if (sb.charAt(sb.length() - 1) == ',')
                sb.setLength(sb.length() - 1); // remove last comma
    
            sb.append("]");
            return sb.toString();
        }
    }
    
    public static TrieNode root;      // Tree Root to access Trie
    public Tries_L2() { // constructor
        root = new TrieNode();  // initialise Root
    }

    // Time: O(len(word))
    public static void insert(String word) {
        TrieNode node = root;  // 1. set pointer to Root

        // 2. Traverse down in level[characters], find position to insert
        for(int i=0; i<word.length(); i++) {
            int idx = word.charAt(i) - 'a';   // a. Find idx / char
            if(node.children[idx] == null) {  // b. If node is null => make new node
                node.children[idx] = new TrieNode();    
            }
            
            node = node.children[idx]; // 3. Move pointer to next node [down]
            node.countPrefix++;
        }
        node.isWord = true;  // 4. Mark last char as END of word
        node.countWord++;
    }

    // Time: O(len(str))
    public static TrieNode findNode(String word) {
        TrieNode node = root;  // 1. set pointer to Root

        // 2. Traverse down in level[characters], find position to insert
        for(int i=0; i<word.length(); i++) {
            int idx = word.charAt(i) - 'a';   // a. Find idx / char 
            if(node.children[idx] == null) {  // b. If node is null => return NULL** - base-case
                return null;   
            }
            
            node = node.children[idx]; // 3. Move pointer to next node [down]
        }
        return node;  // 4. Return the last node reached
    }

    public static boolean search(String word) {
        TrieNode node = findNode(word);
        
        // Conditions:
        // 1. node is not null 
        // 2. Check if END of word reached
    
        return node!=null && node.isWord;      
    }

    public static boolean startsWith(String prefix) {
        TrieNode node = findNode(prefix);
        
        // Conditions:
        // last Node is not null => means => 
        // a word containing prefix is present
        
        return node != null;
    }

    public static int countWordsStartsWith(String prefix) {
        TrieNode node = findNode(prefix);

        // Conditions:
        // 1. node is not null -> if null then 0
        // 2. Check if END of word reached -> return counPrefix
    
        return node == null ? 0 : node.countPrefix;
    }

    public static int countWordsEndWith(String prefix) {
        TrieNode node = findNode(prefix);

        // Conditions:
        // 1. node is not null -> if null then 0
        // 2. Check if END of word reached -> return countWord
    
        return node == null ? 0 : node.countWord;
    }

    // Imp**
    public static void remove(String word) {
        TrieNode node = root;  // 1. set pointer to Root

        // 2. Traverse down in level[characters], find position to insert
        for(int i=0; i<word.length(); i++) {
            int idx = word.charAt(i) - 'a';   // a. Find idx / char 
            if(node.children[idx] == null) {  // b. If node is null => return NULL** - base-case
                return;   
            }
            
            node = node.children[idx]; // 3. Move pointer to next node [down]
            node.countPrefix--;
        }
        node.isWord = false;  // 4. Mark last char as END of word
        node.countWord--;
    }

    public static void printTrie() {
        printTrieHelper(root, "");
    }

    private static void printTrieHelper(TrieNode node, String prefix) {
        if (node == null) return;

        // If this node marks end of a word, print it
        if (node.isWord) {
            System.out.println(prefix);
        }

        // Traverse all possible children
        for (int i = 0; i < 26; i++) {
            if (node.children[i] != null) {
                char c = (char) ('a' + i);
                printTrieHelper(node.children[i], prefix + c);
            }
        }
    }

    public static void main(String[] args) {
        Tries_L2 trie = new Tries_L2(); // Trie DS
        trie.insert("apple"); // null
        trie.search("apple"); // true
        trie.search("app"); // false
        trie.startsWith("app"); // true
        trie.insert("app"); // null
        trie.search("app"); // true

        printTrie();
    }
}