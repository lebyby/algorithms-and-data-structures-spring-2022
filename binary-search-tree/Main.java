import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    Node root;

    static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }
    }

    public static void getData(String fileIn, ArrayList<Integer> input) throws IOException {

        Scanner sc = new Scanner(new File(fileIn));

        while (sc.hasNextInt()) {
            int str = sc.nextInt();
            input.add(str);
        }
        sc.close();
    }

    public static void outData_contains(String fileOut, ArrayList<String> output) throws IOException {

        FileWriter out = new FileWriter(fileOut);

        for (String sign : output) out.write(String.format("%s\n", sign));
        out.close();
    }

    public static void outData_min_after(String fileOut, ArrayList<String> output_contains, ArrayList<String> output_min_after) throws IOException {

        FileWriter out = new FileWriter(fileOut);

        for (int i = 0; i < output_contains.size(); i++) {
            out.write(String.format("%s %s\n", output_contains.get(i), output_min_after.get(i)));
        }
        out.close();
    }

    public static void split(ArrayList<Integer> input, ArrayList<Integer> tree) {

        int N = input.get(0);
        for (int i = 0; i < N; i++) tree.add(i, input.get(i + 1));

    }

    ////Проверка на вхождение элемента
    private Node addRecursive(Node current, int value, ArrayList<String> output) {
        if (current == null) {
            output.add("-");
            return new Node(value);
        }

        if (value < current.value) {
            if (current.left != null) {
                current.left = addRecursive(current.left, value, output);
            } else {
                current.left = new Node(value);
                output.add("-");
            }
        } else if (value > current.value) {
            if (current.right != null) {
                current.right = addRecursive(current.right, value, output);
            } else {
                current.right = new Node(value);
                output.add("-");
            }
        } else {
            output.add("+");
            return current;
        }

        return current;
    }

    public void addElement(int value, ArrayList<String> output) {
        root = addRecursive(root, value, output);
    }

    /////////Поиск следующего элемента
    public void findNext(int value, ArrayList<String> output) {
        Node current = root;
        Node next = null;
        while (current != null) {
            if (value >= current.value) {
                current = current.right;
            } else {
                next = current;
                current = current.left;
            }
        }
        if (next != null) {
            output.add(String.valueOf(next.value));
        } else {
            output.add("-");
        }
    }

    private static void createBinaryTree(ArrayList<Integer> tree, ArrayList<String> output, ArrayList<String> output_min_after) {
        Main bt = new Main();

        for (Integer integer : tree) {
            bt.addElement(integer, output);
            bt.findNext(integer, output_min_after);
        }
    }

    public static void main(String[] args) {

        for (int i = 1; i <= 4; i++) {
            ArrayList<Integer> input = new ArrayList<>();
            ArrayList<Integer> tree = new ArrayList<>();
            ArrayList<String> output_contains = new ArrayList<>();
            ArrayList<String> output_min_after = new ArrayList<>();

            try {
                getData(String.format("%d.in", i), input);
                split(input, tree);
                createBinaryTree(tree, output_contains, output_min_after);
                outData_contains(String.format("ans%d.contains.out", i), output_contains);
                outData_min_after(String.format("ans%d.min-after.out", i), output_contains, output_min_after);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}



