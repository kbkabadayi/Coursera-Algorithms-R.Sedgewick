/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Outcast {
    private final WordNet wordNet;

    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        int maxDistance = Integer.MIN_VALUE;
        int outcast = 0;
        for (int i = 0; i < nouns.length; i++) {
            int distance = 0;
            for (int j = 0; j < nouns.length; j++) {
                distance += wordNet.distance(nouns[i], nouns[j]);
            }
            if (distance > maxDistance) {
                maxDistance = distance;
                outcast = i;
            }
        }
        return nouns[outcast];
    }
}
