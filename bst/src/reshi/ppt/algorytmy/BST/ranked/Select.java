package reshi.ppt.algorytmy.BST.ranked;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Marcin Regulski on 07.05.2016.
 */
public class Select {
    private static Random rng = new Random();

    /**
     * Select nth smallest element from array by randomly partitioning it.
     * @param array source array
     * @param n number of element to get (1-based)
     * @param <T> type of array elements
     * @return nth smallest member of array
     */
    public static <T extends Comparable<T>> T randomSelect(ArrayList<T> array, int n) {
        return randomSelect(array, 0, array.size()-1, n);
    }
    
    /**
     * Partition range of array into [< pivot, >= pivot], with random pivot.
     * @param array array to partitionHoare
     * @param start first index in range
     * @param end list index in range
     * @param <T> type of array elements
     * @return index of last item in array that is < pivot.
     */
    public static <T extends Comparable<T>> int partitionHoare(ArrayList<T> array, int pivotIdx, int start, int end) {
        T x = array.get(pivotIdx);
        int i = start - 1;
        int j = end + 1;
        while(true) {
            do {j--;} while(array.get(j).compareTo(x)>0);
            do {i++;} while(array.get(i).compareTo(x)<0);
            if(i < j) {
                T tmp = array.get(i);
                array.set(i, array.get(j));
                array.set(j, tmp);
            }
            else {
                return j;
            }
        }
    }


    private static <T extends Comparable<T>> T randomSelect(ArrayList<T> array, int start, int end, int n) {
        if(start == end) {
            return array.get(start);
        }
        int pivotIdx = rng.nextInt(end-start)+start;
        int q = partitionHoare(array, pivotIdx, start, end);
        int k = q - start + 1;
        if(n <= k) {
            return randomSelect(array, start, q, n);
        }
        return randomSelect(array, q+1, end, n-k);
    }


    private static <T extends Comparable<T>> T median(ArrayList<T> array) {
        array.sort(null);
        return array.get(array.size()/2);
    }

    /**
     * Deterministically find nth smallest element of array using median-of-five
     * @param array source array
     * @param n number of element to find (1-based)
     * @param <T> type of array elements
     * @return nth smallest element in array
     */
    public static <T extends Comparable<T>> T select(ArrayList<T> array, int n) {
        if(array.size() <= 10) {
            array.sort(null);
            return array.get(n-1);
        }

        ArrayList<ArrayList<T>> fives = new ArrayList<>(array.size()/5 + 1);
        int j = 0;

        // 1. split array into parts of max. 5 elements
        for (int i = 0; i < array.size(); i++) {
            if(fives.size() <= (i/5)) {
                fives.add(new ArrayList<>());
            }
            fives.get(i/5).add(array.get(i)); /*[j] = array[i];*/
            j = j == 4 ? 0 : j + 1;

        }

        // 2. find median of each five (higher of two if fives[i] has even number of elements)
        int nMedians = array.size() % 5 == 0 ? array.size()/5 : array.size()/5+1;
        ArrayList<T> medians = new ArrayList<>(nMedians);
        for (int i = 0; i < nMedians; i++) {
            medians.add(median(fives.get(i)));
//            medians.add(select(fives.get(i), fives.get(i).size()/2));
        }

        // 3. get median of medians
        T almostMedian = select(medians, medians.size()/2 + 1);

        // 4. partitionHoare array around the median of medians.
        // [less: k elements, more/eq: array.size() - k]
        int idx = partitionHoare(array, array.indexOf(almostMedian), 0, array.size()-1) + 1;
        ArrayList<T> less = new ArrayList<>(array.subList(0, idx));
        ArrayList<T> moreEq = new ArrayList<>(array.subList(idx, array.size()));
        // 5. select nth element of lower part or (n-k)th from upper if n > k
        if(n <= idx) {
            return select(less, n);
        }
        else {
            return select(moreEq, n - less.size());
        }

//        return array.get(0);

    }


}
