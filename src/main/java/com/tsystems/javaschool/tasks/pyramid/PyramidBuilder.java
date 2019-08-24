package com.tsystems.javaschool.tasks.pyramid;

import java.util.Collections;
import java.util.List;

public class PyramidBuilder {

    /**
     * Builds a pyramid with sorted values (with minumum value at the top line and maximum at the bottom,
     * from left to right). All vacant positions in the array are zeros.
     *
     * @param inputNumbers to be used in the pyramid
     * @return 2d array with pyramid inside
     * @throws {@link CannotBuildPyramidException} if the pyramid cannot be build with given input
     */
    public int[][] buildPyramid(List<Integer> inputNumbers) {
        // TODO : Implement your solution here

        //определяем высоту пирамиды
        double height = (Math.sqrt(8 * inputNumbers.size() + 1) - 1) / 2;

        //проверяем валидность высоты
        if (Double.isNaN(height) || height % 1 > 0.0 || height == 1 || inputNumbers.stream().anyMatch(item -> (item == null))) {
            throw new CannotBuildPyramidException();
        };

        //Создаем двухмерный массив
        int[][] arr = new int[(int) height][(int) (2 * height - 1)];

        //сортируем список для заполнения
        Collections.sort(inputNumbers);

        //заполняем массив
        int n = 0;
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < i + 1 ; j++) {
                arr[i][arr.length - 1 - i + (2 * j)] = inputNumbers.get(n);
                n++;
            }
        }
        
        return arr;
    }
}
