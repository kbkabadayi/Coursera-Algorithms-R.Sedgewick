/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

import java.util.HashMap;

public class WordNet {
    private final Digraph wordNet;
    private final HashMap<Integer, String> idToSynset;
    private final HashMap<String, Bag<Integer>> nounToIds;
    private final SAP sap;


    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        handleNull(synsets);
        handleNull(hypernyms);

        idToSynset = new HashMap<>();
        nounToIds = new HashMap<>();

        readSynsets(synsets);
        int vertexCount = idToSynset.size();
        wordNet = new Digraph(vertexCount);
        checkCycle();

        readHypernyms(hypernyms);
        sap = new SAP(wordNet);


    }

    private void checkCycle() {
        DirectedCycle dc = new DirectedCycle(wordNet);
        if (dc.hasCycle()) {
            throw new IllegalArgumentException();
        }
    }

    private void readSynsets(String synsets) {
        In in = new In(synsets);

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split(",");
            String[] synsetParts = parts[1].split(" ");

            int id = Integer.parseInt(parts[0]);
            idToSynset.put(id, parts[1]);

            for (String s : synsetParts) {
                if (!nounToIds.containsKey(s)) {
                    Bag<Integer> bag = new Bag<>();
                    bag.add(id);
                    nounToIds.put(s, bag);
                }
                else {
                    Bag<Integer> bag = nounToIds.get(s);
                    bag.add(id);
                    nounToIds.put(s, bag);
                }
            }
        }
    }

    private void readHypernyms(String hypernyms) {
        In in = new In(hypernyms);

        while (in.hasNextLine()) {
            String line = in.readLine();
            String[] parts = line.split(",");
            int synsetId = Integer.parseInt(parts[0]);
            for (int i = 1; i < parts.length; i++) {
                wordNet.addEdge(synsetId, Integer.parseInt(parts[i]));
            }
        }

    }


    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounToIds.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        handleNull(word);

        return nounToIds.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        handleNull(nounA);
        handleNull(nounB);
        handleNonexistentNoun(nounA, nounB);

        Iterable<Integer> idsContainingNounA = nounToIds.get(nounA);
        Iterable<Integer> idsContainingNounB = nounToIds.get(nounB);

        return sap.length(idsContainingNounA, idsContainingNounB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        handleNull(nounA);
        handleNull(nounB);
        handleNonexistentNoun(nounA, nounB);

        Iterable<Integer> idsContainingNounA = nounToIds.get(nounA);
        Iterable<Integer> idsContainingNounB = nounToIds.get(nounB);

        int id = sap.ancestor(idsContainingNounA, idsContainingNounB);

        return idToSynset.get(id);
    }

    private void handleNull(Object input) {
        if (input == null) {
            throw new IllegalArgumentException();
        }
    }

    private void handleNonexistentNoun(String nounA, String nounB) {
        if (!isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
    }
}

