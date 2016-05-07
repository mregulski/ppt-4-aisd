package reshi.ppt.algorytmy.augmented;

/**
 * @author Marcin
 * @date 2016-05-01
 */
public class Main {
    public static void main(String[] args) {
        RankedBST<Integer, String> tree = new RankedBST<>();
        tree.insert(41, "le first");
        tree.insert(21, "le first");
        tree.insert(51, "le first");
        tree.insert(11, "le first");
        tree.insert(19, "le first");
        tree.insert(17, "le first");
        tree.insert(44, "le first");
        tree.minimum();
        tree.inOrder(System.out::println);
    }
}
