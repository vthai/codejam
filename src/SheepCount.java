import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class SheepCount {
    public static final int FOUND = 0;
    
    public static final int INSOMNIA = 1;
    
    public static void main(String[] args) throws IOException {
        SheepCount test = new SheepCount();
        test.run(args[0]);
    }

    private void run(String filename) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(new File(
                filename)));
        int numberOfCase = Integer.valueOf(fileReader.readLine());

        for (int i = 0; i < numberOfCase; i++) {
            System.out.print("Case #" + (i + 1) + ": ");
            int chosenNumber = Integer.valueOf(fileReader.readLine());
            
            Set<Character> digits = new HashSet<>();
            int counter = 2;
            
            int prevs = -1;
            int status = FOUND;
            
            int multiplication = chosenNumber;
            
            while (true) {
                record(digits, String.valueOf(multiplication));
                
                if (multiplication == prevs) {
                    status = INSOMNIA;
                    break;
                }
                if (digits.size() != 10) {
                    prevs = chosenNumber;
                    multiplication = chosenNumber * counter;
                    counter++;
                } else {
                    break;
                }
            }
            
            if (status == FOUND) {
                System.out.println(multiplication);
            } else {
                System.out.println("INSOMNIA");
            }
        }
        fileReader.close();
    }
    
    private void record(Set<Character> digits, String number) {
        for (int i = 0; i < number.length(); i++) {
            digits.add(number.charAt(i));
        }
    }
}
