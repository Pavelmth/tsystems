package com.tsystems.javaschool.tasks.subsequence;

import java.util.LinkedList;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here

        //проверка на null
        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }

        boolean checking = false;
        if (x.size() == 0 && y.size() == 0) {
            checking = true;
        }

        int i = 0;
        for (Object o:
                y) {
            if (i < x.size() && o.equals(x.get(i))) {
                i++;
            }
            if (i == x.size()) {
                checking = true;
                break;
            }
        }
        return checking;
    }
}
