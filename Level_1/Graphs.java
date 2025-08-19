import java.util.*;

class Graphs {
    public static void main(String[] args) {
        int vertices = 5;
        int[][] edges = {{0, 1}, {2, 1}, {3, 4}};

        ArrayList<Integer>[] graph = new ArrayList[vertices];
        for(int i=0; i<vertices; i++) graph[i] = new ArrayList<>();

        for(int[] edge: edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        for(int i=0; i<vertices; i++) {
            System.out.println(i + " > " + graph[i]);
        }
    }
}