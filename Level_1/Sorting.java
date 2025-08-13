import java.util.Arrays;
import java.util.Scanner;

public class Sorting {
    public static void main(String[] args) {
        Scanner scannner = new Scanner(System.in);
        // int n = scannner.nextInt();
        // int [] arr = new int[n];
        // for (int i=0; i<n; i++) {
        //     arr[i] = scannner.nextInt();
        // }

        // int [] arr = new int[]{1,5,7,2,4};
        // int [] arr = new int[]{9,6,3,5,3,4,3,9,6,4,6,5,8,9,9}; // count sort
        // int [] arr = new int[]{12, 235, 45, 72, 5, 9875}; // radix sort
        // int [] arr = new int[]{1,1,0,1,1,0,0,0}; // sort 01
        // int [] arr = new int[]{1,1,0,2,1,2,1,0,2,2,0,2,0}; // sort 012
        
        System.out.println();
        // System.out.println("Before: " + Arrays.toString(arr));
        
        // BubbleSort(arr);
        // SelectionSort(arr);
        // InsertionSort(arr);
        // MergeSort(arr, 0, arr.length-1);
        // partition(arr, 3);
        // QuickSort(arr, 0, arr.length-1);

        // int res = QuickSelect(arr, 0, arr.length-1, 3);
        // System.out.println("4-th Smallest -> " + res);

        // CountSort(arr);
        // RadixSort(arr);

        // Sort01(arr);
        // Sort012(arr);
        // System.out.println("After : " + Arrays.toString(arr));

        int [] arr = new int[]{30, 40, 50, 10, 20};
        int res = findPivot(arr);
        System.out.println("Array = " + Arrays.toString(arr));
        System.out.println("Pivot = " + res);


        scannner.close();
    }

    // 13. Find Pivot in Rotated Sorted Array
    // Lo < Hi , if lo = hi -> single element can't be pivot
    public static int findPivot(int [] arr) {
        int lo = 0;
        int hi = arr.length-1;

        while(lo < hi) {
            int mid = lo + (hi-lo)/2;
            if (arr[mid] < arr[hi]) { // mid to hi -> increasing
                hi = mid; // search in left half
            } else { // mid to hi -> decreasing
                lo = mid+1; // search in right half
            }
        }
        return arr[lo];
    }

    // 12. Target Sum Pair -> SortArray -> then 2 pointers
    // Sort -> O(nlog(n)) + O(n) => O(n*log(n))

    // 11. Sort012 -> based on 3 pointers
    // Time: O(n)
    public static void Sort012(int [] arr) {
        int start = 0;
        int end = arr.length-1;
        while(arr[end] == 2) {
            end--;
        }

        for (int i=0; i<end; i++) {
            if (arr[i] == 0) {
                swap(arr, i, start);
                start++;
            } else if (arr[i] == 2) {
                swap(arr, i, end);
                i--;
                end--;
            } else {
                continue;
            }
        }
    }
    
    // 10. Sort01 -> based on 2 pointers
    // Time: O(n)
    public static void Sort01(int [] arr) {
        int i = 0;
        int j = 0;

        for (i=0; i<arr.length; i++) {
            if (arr[i] == 1) {
                continue;
            } else {
                swap(arr, i, j);
                j++;
            }
        }
    }

    // 9. 

    // 8. Radix Sort -> 
    // Time complexity -> O(n*m), where m is the digits in max element
    // m = log 10(max) -> digits in max element
    // Space complexity -> O(n+k)
    public static void RadixSort(int [] arr) {
        int max = Integer.MIN_VALUE;
        for (int x : arr) {
            if (x > max) {
                max = x;
            }
        }

        int exponent = 1;
        while (exponent <= max) {
            CountSort(arr, exponent);
            exponent = exponent * 10;
        }
    }
    
    public static void CountSort(int[] arr, int exponent) {
        int [] ans = new int [arr.length];

        // Find freq of each element -> digits
        int [] freq = new int [10];
        for (int x : arr) {
            freq[(x/exponent) % 10]++;
        }

        // Can't directly write all occurances x times
        // We need to make it stable.
        // Count sort -> Stable Sort

        // Convert freq arr to prefix sum array
        for (int i=1; i<freq.length; i++) {
            freq[i] += freq[i-1];
        }

        // Now we know at what index element will occur last
        // Loop over array in reverse
        for (int i=arr.length-1; i>=0; i--) {
            int val = (arr[i]/exponent) % 10;   // find current freq
            int idx = freq[val]-1;  // find new index
            ans[idx] = arr[i];      // update ans 
            freq[val]--;    // update freq 
        }

        // filling back original array
        for (int i=0; i<ans.length; i++) {
            arr[i] = ans[i];
        }

        return;
    }

    // 7. Count Sort -> 
    // Time Complexity: 𝑂(𝑛+𝑘), where 𝑛 is the number of elements in the array and 
    // 𝑘 is the range of the elements (i.e., max−min+1)
    // Space: O(n + k)
    public static void CountSort(int [] arr) {
        int [] ans = new int [arr.length];
        
        // Find min, max in arr
        int max, min;
        max = min = arr[0];
        for (int i=1; i<arr.length; i++) {
            if (arr[i] < min) {
                min = arr[i];
            } else if (arr[i] > max) {
                max = arr[i];
            }
        }

        // Find freq of each element
        int [] freq = new int [max-min+1];
        for (int x : arr) {
            freq[x - min]++;
        }

        // Can't directly write all occurances x times
        // We need to make it stable.
        // Count sort -> Stable Sort

        // Convert freq arr to prefix sum array
        for (int i=1; i<freq.length; i++) {
            freq[i] += freq[i-1];
        }

        // Now we know at what index element will occur last
        // Loop over array in reverse
        for (int i=arr.length-1; i>=0; i--) {
            int val = arr[i]-min;   // find current freq
            int idx = freq[val]-1;  // find new index
            ans[idx] = arr[i];      // update ans 
            freq[val] = idx;    // update freq 
        }

        // filling back original array
        for (int i=0; i<ans.length; i++) {
            ans[i] = arr[i];
        }

        return;
    }

    // 6. Quick Select -> Find K-th Smallest Element in array
    public static int QuickSelect(int[] arr, int lo, int hi, int K) {
        int pivot = arr[hi];
        int pivotIdx  = partition(arr, pivot, lo, hi);

        if (K < pivotIdx) { // partition left
            return QuickSelect(arr, lo, pivotIdx-1, K);
        } else  if (K > pivotIdx) { // partition right
            return QuickSelect(arr, pivotIdx+1, hi, K);
        } else {       // found
            return arr[pivotIdx];
        }
    }

    // 5. Quick Sort -> divide and conquer based on a Pivot element
    // a. Stable -> NO
    // b. Inplace -> YES
    public static int partition(int[] arr, int pivot, int lo, int hi) {
        // 0 to j-1 is <= Pivot
        // j to i-1 is > Pivot
        // i to end is Unkown

        int i=lo;
        int j=lo;

        while (i <= hi) {
            if (arr[i] > pivot) {
                i++;
            } else {
                swap(arr, i, j);
                i++;
                j++;
            }
        }

        return j-1;
    }

    public static void QuickSort(int [] arr, int lo, int hi) {
        if (lo >= hi) {
            return;
        }

        int pivot = arr[hi]; // take last element as Pivot
        int pivotIdx = partition(arr, pivot, lo, hi); // partition and find pivot index

        QuickSort(arr, lo, pivotIdx-1);
        QuickSort(arr, pivotIdx+1, hi);
    }

    // 4. Merge Sort -> Divide & Conquer technique
    // Divide array into half till one single element left -> then merge upwards
    // Merge -> Time: O(n) -> Space: O(n)
    // Merge Sort -> Time: O(n*log(n)) -> Space: O(n)
    // **Imp. -> Stable but not Inplace
    // Stable -> 2a 2b -> order remains same for two same elements.
    public static void merge(int [] arr, int lo, int mid, int hi) {
        int idx1 = lo;      // traverse over left half
        int idx2 = mid+1;   // traverse over right half

        int [] temp = new int[hi-lo+1];
        int start = 0;
        // System.out.println(lo + " , " + mid + " , " + hi);
        // System.out.println("Arr -> " + Arrays.toString(arr));
        // System.out.println("Temp -> " + Arrays.toString(temp));
        while(idx1 <= mid && idx2 <= hi) {
            // System.out.println(">> " + arr[idx1] + " , " + arr[idx2]);
            if (arr[idx1] < arr[idx2]) {
                temp[start] = arr[idx1];
                idx1++;
            } else {
                temp[start] = arr[idx2];
                idx2++;
            }
            start++;
            // System.out.println("temp -> " + Arrays.toString(temp));
        }
        
        while (idx1 <= mid) {
            temp[start] = arr[idx1];
            idx1++;
            start++;
        }

        while (idx2 <= hi) {
            temp[start] = arr[idx2];
            idx2++;
            start++;
        }
        // System.out.println("temp -> " + Arrays.toString(temp));

        // copy to original array
        start = lo;
        int idx = 0;
        while (start <= hi && idx<temp.length) {
            arr[start++] = temp[idx++];
        }
    }

    public static void MergeSort(int[] arr, int lo, int hi) {
        if (lo == hi) {
            return;
        }

        int mid = lo + (hi-lo)/2;

        MergeSort(arr, lo, mid);    // left half
        MergeSort(arr, mid+1, hi);  // right half
        // System.out.println("merge -> " + lo + ", " + mid + ", " + hi);
        merge(arr, lo, mid, hi);
    }

    // 3. Insertion Sort -> Insert element at its correct position
    public static void InsertionSort (int[] arr) {
        // Assume 1st is sorted -> single element
        for (int i=1; i<arr.length; i++) { // start from 2nd idx
            // System.out.println("i = " + i);
            for (int j=i; j>0; j--) {     // Compare & find correct position j, j-1, j-2, ... 1 
                // System.out.println("Compare: j = " + j + " , j-1 = " + (j-1));
                if (arr[j] < arr[j-1]) { // Swap if current is smaller
                    swap(arr, j-1, j);
                } else {
                    break;  // no need to check anymore to left
                }
            }
            // System.out.println();
        }
    }

    // 2. Selection Sort -> Find min in unsorted list and swap with start 
    // Time complexity -> Time:O(n^2)

    public static void SelectionSort(int[] arr) {
        for (int i=0; i<arr.length-1; i++) { // n-1 passes
            int minIdx = i;
            for (int j=i+1; j<arr.length; j++) {  // loop over unsorted array
                System.out.println("Compare : " + arr[j] + " , " + arr[minIdx] + " -> min=" + arr[minIdx]);
                if (arr[j] < arr[minIdx]) { // update min index
                    minIdx = j;
                }
            }
            System.out.println(minIdx + ", " + i);
            if (minIdx != i) {
                swap(arr, i, minIdx);
            }
        }
    }

    public static void swap(int[] arr, int idx1, int idx2) {
        int temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }

    // 1. Bubble-Sort -> Bubble the largest element to end of array in one pass
    // Time complexity -> Time:O(n^2) -> boolean flag to optimize
    public static void BubbleSort(int [] arr) {
        boolean flag;  
        for (int i=0; i<arr.length; i++) { // n passes over the array
            flag = false;
            System.out.println("Pass : " + i);
            for (int j=i; j<arr.length-1; j++) { // comparisons -> bubble up the largest element to end
                if (arr[j+1] < arr[j]) {
                    flag = true;
                    // next element < current -> current is larger -> swap & move current to end 
                    swap(arr, j+1, j);
                }
            }
            if (flag == false) { // no comparisons -> all sorted -> break
                break;
            }
        }
    }
}
