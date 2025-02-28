import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static boolean getData(String fileIn, ArrayList<Integer> input) throws IOException {

        File file = new File(fileIn);
        if (file.exists()) {
            Scanner sc = new Scanner(file);
            while (sc.hasNextInt()) {
                int str = sc.nextInt();
                input.add(str);
            }
            sc.close();
            return true;
        } else return false;
    }

    public static void outData(String fileOut, ArrayList<Integer> output) throws IOException {

        FileWriter out = new FileWriter(fileOut);

        for (Integer integer : output) out.write(String.format("%d\n", integer));
        out.close();
    }

    public static void split(ArrayList<Integer> input, ArrayList<Integer> nominal, int k, ArrayList<Integer> nominalSort) {

        for (int i = 0; i < k; i++) {
            nominal.add(i, input.get(i + 1));
            nominalSort.add(i, input.get(i + 1));
        }

    }

    ///Поиск минимального количества монет
    public static void findCoins(ArrayList<Integer> nominalSort, int sum, ArrayList<Integer> coinsMin, ArrayList<Integer> coinsUsed) {
        int coinCount;

        Collections.sort(nominalSort);
        for (int i = 0; i <= sum + 1; i++) {
            coinCount = i;
            int newCoin = nominalSort.get(0);

            for (int j : nominalSort) {
                if (j > i) {
                    continue;
                }
                if (coinsMin.get(i - j) + 1 < coinCount) {
                    coinCount = coinsMin.get(i - j) + 1;
                    newCoin = j;
                }
            }
            coinsMin.add(i, coinCount);
            coinsUsed.add(i, newCoin);
        }
    }


    public static void countCoins(ArrayList<Integer> nominal, int sum, ArrayList<Integer> coinsMin, ArrayList<Integer> coinsUsed, ArrayList<Integer> output) {

        int coin = sum;
        //Минимальное количество монет
        output.set(0, coinsMin.get(sum));
        //Подсчет использованных монет
        while (coin > 0) {
            if (nominal.contains(coinsUsed.get(coin))) {
                int position = nominal.indexOf(coinsUsed.get(coin)) + 1;
                output.set(position, output.get(position) + 1);
                coin = coin - coinsUsed.get(coin);
            }
        }
        if (coin < 0) {
            output.clear();
            output.add(-1);
        }
    }


    public static void main(String[] args) {

        for (int i = 1; i <= 26; i++) {
            ArrayList<Integer> input = new ArrayList<>();
            ArrayList<Integer> nominal = new ArrayList<>();
            ArrayList<Integer> coinsMin = new ArrayList<>();
            ArrayList<Integer> coinsUsed = new ArrayList<>();
            ArrayList<Integer> nominalSort = new ArrayList<>();
            try {
                if (getData(String.format("%d.in", i), input)) {
                    int k = input.get(0);
                    int N = input.get(input.size() - 1);
                    ArrayList<Integer> output = new ArrayList<>(Collections.nCopies(k + 1, 0));
                    split(input, nominal, k, nominalSort);
                    findCoins(nominalSort, N, coinsMin, coinsUsed);
                    countCoins(nominal, N, coinsMin, coinsUsed, output);
                    outData(String.format("out/ans%d.full-answer.out", i), output);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

