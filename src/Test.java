import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Test {
    public static void main(String[] args) throws IOException {
        Test test = new Test();
        test.run(args[0]);
    }
    
    private void run(String filename) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(new File(filename)));
        int numberOfCase = Integer.valueOf(fileReader.readLine());
        
        for (int i = 0; i < numberOfCase; i++) {
            System.out.print("Case #" + (i+1) + ": ");
            String[] line = fileReader.readLine().split(" ");
        }
        fileReader.close();
    }
}
