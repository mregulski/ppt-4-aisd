package reshi.ppt.algorytmy.lcs;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcin Regulski on 12.05.2016.
 */
public class LongestCommonSubsequence {
    private int[][] lengths;
    public final String first;
    public final String second;

    private String longest;
    private List<String> sequences;

    public LongestCommonSubsequence(String first, String second) {
        this.first = first;
        this.second = second;
        this.lengths = getLengths();
    }

    public String get() {
        if(sequences != null) {
            return sequences.get(0);
        }
        if(longest == null) {
            longest = backtrack(first.length(), second.length());
        }
        return longest;

    }

    public List<String> getAll() {
        return sequences;
    }

    private int[][] getLengths() {
        int[][] lengths = new int[first.length()+1][second.length()+1];
        for (int i = 1; i <= first.length(); i++) {
            for (int j = 1; j <= second.length(); j++) {
                // shift indices because strings are 0-based
                if(first.charAt(i-1) == second.charAt(j-1)) {
                    lengths[i][j] = lengths[i-1][j-1]+1;
                }
                else {
                    lengths[i][j] = lengths[i][j-1] > lengths[i-1][j] ? lengths[i][j-1] : lengths[i-1][j];
                }
            }
        }
        return lengths;//[first.length()-1][second.length()-1];
    }

    private String backtrack(int i, int j) {
        if(i == 0 || j == 0) {
            return "";
        }
        if(first.charAt(i-1) == second.charAt(j-1)) {
            return backtrack(i-1, j-1) + first.charAt(i-1);
        }
        if(lengths[i][j-1] > lengths[i-1][j]) {
            return backtrack(i, j-1);
        }
        return backtrack(i-1, j);
    }

    /**
     * Populate the subsequences list with all subsequences of maximum length.
     * @param i
     * @param j
     */
    private List<String> backtrackAll(int i, int j) {
        if(i == 0 || j == 0) {
            return new ArrayList<>();
        }
        if(first.charAt(i) == second.charAt(j)) {
            return new ArrayList<>();
        }
        return null;
    }
}
