import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class RevengeOfThePanCakes {
    public static void main(String[] args) throws IOException {
        RevengeOfThePanCakes test = new RevengeOfThePanCakes();
        test.run(args[0]);
    }
    
    private void run(String filename) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(new File(filename)));
        int numberOfCase = Integer.valueOf(fileReader.readLine());
        
        for (int i = 0; i < numberOfCase; i++) {
            System.out.print("Case #" + (i+1) + ": ");
            String line = fileReader.readLine();
            
            char[] temps = line.toCharArray();
            boolean[] cakes = new boolean[temps.length];
            
            for (int index = 0; index < temps.length; index++) {
                cakes[index] = temps[index] == '+';
            }
            //printDebug("init:", cakes);
            
            int upsidedown = 0;
            int times = 0;
            final int lastIndex = cakes.length - 1;
            do {
                upsidedown = 0;
                int cutoff = 0;
                
                for (int j = 0; j < cakes.length; j++) {
                    boolean cake = cakes[j];
                    if (!cake) {
                        upsidedown++;
                        cutoff = j + 1;
                    }
                    if (cake && cutoff != 0) {
                        if (j != lastIndex && !cakes[j+1]) {
                            cutoff = j + 1;
                        }
                        break;
                    }
                }
                if (upsidedown != 0) {
                    flipStack(cakes, cutoff);
                    times++;
                }
                //printDebug("run:", cakes);
            } while (upsidedown > 0);
            
            System.out.println(times);
        }
        fileReader.close();
    }
    
    private void flipStack(boolean[] cakes, int cutoff) {
        for (int i = 0; i < cutoff; i++) {
            cakes[i] = !cakes[i];
        }
        //printDebug("flip:", cakes);
    }
    
    private void printDebug(String state, boolean[] cakes) {
        System.out.println(state + ":");
        for(Boolean b : cakes) {
            System.out.print(b + ",");
        }
        System.out.println();
    }
}
