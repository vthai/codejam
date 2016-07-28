import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JamCoins {
    Set<Integer> primeMap = new HashSet<>();

    public static void main(String[] args) throws IOException {
        JamCoins test = new JamCoins();
        test.run(args[0]);
        // test.testMutation();
        // test.testPrime();
    }

    private void testMutation() {
        char[] number = { '0', '0', '0' };
        List<String> results = new ArrayList<String>();
        permutateStr(number, results, 0);

        for (int i = 0; i < results.size(); i++) {
            System.out.println(i + ": " + results.get(i));
        }
    }

    private void run(String filename) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(new File(filename)));
        int numberOfCase = Integer.valueOf(fileReader.readLine());

        final int[] bases = { 2, 3, 4, 5, 6, 7, 8, 9, 10 };

        for (int i = 0; i < numberOfCase; i++) {
            System.out.println("Case #" + (i + 1) + ": ");
            String[] line = fileReader.readLine().split(" ");

            final int N = Integer.valueOf(line[0]);
            final int J = Integer.valueOf(line[1]);

            String[] achievements = new String[J];
            String[] divisors = new String[J];

            int achieved = 0;
            char[] numbers = initNumber(N);
            int mutateIndex = 0;

            List<String> permutation = new ArrayList<>();
            permutateStr(numbers, permutation, 0);

            while (achieved < J && mutateIndex < permutation.size()) {
                String number = permutation.get(mutateIndex++);

                // explore the number with all bases
                int countNonPrime = 0;
                StringBuilder tempDivisor = new StringBuilder();
                for (int base : bases) {
                    try {
                        long based10Number = Long.parseLong(number, base);

                        long divisor = getDivisor(based10Number);
                        boolean isPrime = (divisor == -1);

                        if (!isPrime) {
                            countNonPrime++;
                            tempDivisor.append(divisor + " ");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {

                    }
                }

                // the number is non prime for all base
                if (countNonPrime == bases.length) {
                    achievements[achieved] = number.toString();
                    divisors[achieved] = tempDivisor.toString();
                    System.out.println("Match at index:" + mutateIndex + ":" + achieved + ":" + number);
                    achieved++;
                }
            }

            for (int index = 0; index < J; index++) {
                System.out.print(achievements[index] + " ");
                System.out.println(divisors[index]);
            }
        }
        fileReader.close();
    }

    private void permutateStr(char[] array, List<String> results, int n) {
        // System.out.println("n:" + n);
        if (n == array.length) {
            StringBuilder temp = new StringBuilder();
            temp.append("1").append(array).append("1");
            results.add(temp.toString());
            // results.add(String.copyValueOf(array));
        } else {
            permutateStr(array, results, n + 1);

            array[n] = '1';
            permutateStr(array, results, n + 1);
            array[n] = '0';
        }
    }

    private long getDivisor(long n) {
        for (long i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return i;
            }
        }
        return -1;
    }

    private char[] initNumber(int length) {
        int realLength = length - 2;
        char[] number = new char[realLength];

        for (int i = 0; i < realLength; i++) {
            number[i] = '0';
        }
        return number;
    }
}
