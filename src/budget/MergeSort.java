package budget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeSort {
    public static List<Purchase> sortPurchases(List<Purchase> purchases) {
        Purchase[] purchasesArray = new Purchase[purchases.size()];
        purchases.toArray(purchasesArray);
        dividePurchases(purchasesArray, 0, purchases.size());
        return Arrays.asList(purchasesArray);
    }

    private static void dividePurchases(Purchase[] purchases, int leftIncl, int rightExl) {
        if (leftIncl >= rightExl - 1) {
            return;
        }
        int middle = leftIncl + (rightExl - leftIncl) / 2;

        dividePurchases(purchases, leftIncl, middle);
        dividePurchases(purchases, middle, rightExl);

        mergePurchases(purchases, leftIncl, middle, rightExl);
    }

    private static void mergePurchases(Purchase[] purchases, int left, int middle, int right) {
        int i = left;
        int j = middle;
        int k = 0;
        Purchase[] temp = new Purchase[right - left];
        while (i < middle && j < right) {
            if (purchases[i].getCost() < purchases[j].getCost()) {
                temp[k] = purchases[i];
                i++;
            } else {
                temp[k] = purchases[j];
                j++;
            }
            k++;
        }
        for (; i < middle; i++, k++) {
            temp[k] = purchases[i];
        }
        for (; j < right; j++) {
            temp[k] = purchases[j];
        }
        if (temp.length >= 0) System.arraycopy(temp, 0, purchases, 0 + left, temp.length);
    }
}
