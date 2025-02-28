import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void getData(String fileIn, ArrayList<Integer> input) throws IOException {

        Scanner sc = new Scanner(new File(fileIn));

        while (sc.hasNextInt()) {
            int str = sc.nextInt();
            input.add(str);
        }
        sc.close();
    }

    public static void outData(String fileOut, ArrayList<Integer> output) throws IOException {

        FileWriter out = new FileWriter(fileOut);

        for (Integer integer : output) out.write(String.format("%d\n", integer));
        out.close();
    }

    public static void split(ArrayList<Integer> input, ArrayList<Integer> arr, ArrayList<Integer> x_i) {

        int N = input.get(0);
        for (int i = 0; i<N; i++) arr.add(i, input.get(i + 1));

        int K = input.get(N+1);
        for (int i = 0; i<K; i++) x_i.add(i, input.get(i + N + 2));

    }

    public static void search(ArrayList<Integer> arr, ArrayList<Integer> x_i, ArrayList<Integer> output) {

        for (int i = 0; i<x_i.size(); i++) {
            int left = 0;
            int right = arr.size()-1;
            int mid = (left + right) / 2;
            while ((!arr.get(mid).equals(x_i.get(i))) && (left <= right)) {

                if (arr.get(mid) > x_i.get(i)) {
                    right = mid - 1;
                } else left = mid + 1;
                mid = (left + right) / 2;
            }
            if (left <= right) {
                output.add(i, mid);
            } else output.add(i, -1);
        }
    }

    public static void main(String[] args) {

        for (int i = 1; i <= 5; i++) {
            ArrayList<Integer> input = new ArrayList<>();
            ArrayList<Integer> arr = new ArrayList<>();
            ArrayList<Integer> x_i = new ArrayList<>();
            ArrayList<Integer> output = new ArrayList<>();

            try {
                getData(String.format("%d.in", i), input);
                split(input, arr, x_i);
                search(arr, x_i, output);
                outData(String.format("ans%d.out", i), output);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
