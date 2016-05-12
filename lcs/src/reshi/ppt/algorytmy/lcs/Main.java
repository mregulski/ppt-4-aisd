package reshi.ppt.algorytmy.lcs;

import javafx.application.Application;

import java.util.Random;

import static reshi.common.util.StringUtil.randomString;

/**
 * @author Marcin Regulski on 12.05.2016.
 */
public class Main {
    public static void main(String[] args) {
        LongestCommonSubsequence lcs = new LongestCommonSubsequence("xmjyauz", "mzjawxu");
        System.out.println(lcs.get());
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        LongestCommonSubsequence lcs2 = new LongestCommonSubsequence(randomString(200 , alphabet), randomString(200, alphabet));
        System.out.println(lcs2.get());
        Application.launch(TimesTest.class);
    }


}
