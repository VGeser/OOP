package ru.nsu.fit.lab1;

class Heap {
    /**
     * calls other small functions, makes steps of algorithm
     * @param arr - input array
     */
    public void heapsort(int[] arr) {
        int quantity=arr.length;
        int[] heap = new int[quantity]; //potentially unsafe
        for (int i = 0; i < quantity; i++) {
            heap[i] = arr[i];
            siftUp(i, heap);
        }
        int b = 0;
        for (int j = 0; j <= quantity - 1; ++j) {
            arr[j] = extractMin(quantity - b, heap);
            b++;
        }
    }

    /**
     * small method to swap array elements, nothing special
     * @param arr - link to current array
     * @param i - element index 1
     * @param j - element index 2
     */
    private static void swap(int[] arr, int i, int j) {
        arr[i] = (arr[i] + arr[j]) - (arr[j] = arr[i]);
    }

    /**
     * pulls lighter/smaller elements to the top of the heap
     * @param v - index of element that is checked
     * @param array - link to current array
     */
    public void siftUp(int v, int[] array) {
        int f = (v - 1) / 2;
        if (v == 0) return;
        if (array[v] < array[f]) {
            swap(array, v, f);
            siftUp(f, array);
        }
    }

    /**
     * pulls heavier/bigger elements to the bottom of the heap
     * @param i - index of current element
     * @param len - array size
     * @param array - link to current array
     */
    public void siftDown(int i, int len, int[] array) {
        int l = i * 2 + 1;
        int r = i * 2 + 2;
        if (l >= len && r >= len) return;
        if (l < len && r < len) {
            int m;
            if (array[l] < array[r]) {
                m = l;
            } else m = r;
            if (array[m] < array[i]) {
                swap(array, i, m);
                siftDown(m, len, array);
            }
        } else if (l < len && array[l] < array[i]) {
            swap(array, i, l);
            siftDown(l, len, array);
        }
    }

    /**
     * picks the smallest elements at a time and puts them to resulting array
     * @param k - index of current element
     * @param array - link to current array
     * @return - the lightest/smallest element at a time
     */
    public int extractMin(int k, int[] array) {
        int ans = array[0];
        swap(array, 0, k - 1);
        siftDown(0, k - 1, array);
        return ans;
    }
}


