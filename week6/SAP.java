/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

public class SAP {
    private final Digraph digraph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        handleNull(G);
        if (G.V() == 0) {
            throw new IllegalArgumentException();
        }

        digraph = new Digraph(G);

    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        checkRange(v);
        checkRange(w);

        BreadthFirstDirectedPaths forV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths forW = new BreadthFirstDirectedPaths(digraph, w);

        int min = Integer.MAX_VALUE;

        for (int i = 0; i < digraph.V(); i++) {
            if (forV.hasPathTo(i) && forW.hasPathTo(i)) {
                int distance = forV.distTo(i) + forW.distTo(i);
                if (distance < min) {
                    min = distance;
                }
            }
        }

        if (min == Integer.MAX_VALUE) {
            return -1;
        }
        return min;

    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        checkRange(v);
        checkRange(w);

        BreadthFirstDirectedPaths forV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths forW = new BreadthFirstDirectedPaths(digraph, w);

        int min = Integer.MAX_VALUE;
        int anc = -1;

        for (int i = 0; i < digraph.V(); i++) {
            if (forV.hasPathTo(i) && forW.hasPathTo(i)) {
                int distance = forV.distTo(i) + forW.distTo(i);
                if (distance < min) {
                    min = distance;
                    anc = i;
                }
            }
        }
        return anc;
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        handleNull(v);
        handleNull(w);

        for (Integer i : v) {
            handleNull(i);
            checkRange(i);
        }
        for (Integer i : w) {
            handleNull(i);
            checkRange(i);
        }

        BreadthFirstDirectedPaths forV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths forW = new BreadthFirstDirectedPaths(digraph, w);

        int min = Integer.MAX_VALUE;

        for (int i = 0; i < digraph.V(); i++) {
            if (forV.hasPathTo(i) && forW.hasPathTo(i)) {
                int distance = forV.distTo(i) + forW.distTo(i);
                if (distance < min) {
                    min = distance;
                }
            }
        }
        if (min == Integer.MAX_VALUE) {
            return -1;
        }
        return min;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        handleNull(v);
        handleNull(w);

        for (Integer i : v) {
            handleNull(i);
            checkRange(i);
        }
        for (Integer i : w) {
            handleNull(i);
            checkRange(i);
        }

        BreadthFirstDirectedPaths forV = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths forW = new BreadthFirstDirectedPaths(digraph, w);

        int min = Integer.MAX_VALUE;
        int anc = -1;

        for (int i = 0; i < digraph.V(); i++) {
            if (forV.hasPathTo(i) && forW.hasPathTo(i)) {
                int distance = forV.distTo(i) + forW.distTo(i);
                if (distance < min) {
                    min = distance;
                    anc = i;
                }
            }
        }
        return anc;
    }

    private void handleNull(Object input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
    }

    private void checkRange(int vertex) {
        if (vertex < 0 || vertex >= digraph.V()) {
            throw new IllegalArgumentException();
        }
    }

}



