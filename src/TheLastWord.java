import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TheLastWord {
    public static void main(String[] args) throws IOException {
        TheLastWord test = new TheLastWord();
        test.run(args[0]);
        //test.testWord();
    }
    
    private void testWord() {
        char[] word = {'J', 'A', 'M'};
        List<char[]> result = new ArrayList<>();
        String temp = String.valueOf(word[0]);
        wordPossibbilities(word, result, temp, 1);
        
        for(char[] w : result) {
            for(char c : w) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        
        sort(result, word.length);
    }
    
    private void run(String filename) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(new File(filename)));
        int numberOfCase = Integer.valueOf(fileReader.readLine());
        
        for (int i = 0; i < numberOfCase; i++) {
            System.out.print("Case #" + (i+1) + ": ");
            char[] word = fileReader.readLine().toCharArray();
            List<char[]> result = new ArrayList<>();
            String temp = String.valueOf(word[0]);
            wordPossibbilities(word, result, temp, 1);
            sort(result, word.length);
            System.out.println();
        }
        fileReader.close();
    }
    
    private void wordPossibbilities(char[] word, List<char[]> result, String str, int n) {
        if (n == word.length) {
            result.add(str.toCharArray());
        } else {
            String temp = str;
            str = word[n] + str;
            wordPossibbilities(word, result, str, n + 1);
            str = temp + word[n];
            wordPossibbilities(word, result, str, n + 1);
        }
    }
    
    private void sort(List<char[]> result, int length) {
        Map<Character, List<Integer>> hash = new HashMap<>();
        
        for (int i = 0; i < length; i++) {
            char max = 'A';
            hash.clear();
            //System.out.println("Now " + hash.size());
            for (int j = 0; j < result.size(); j++) {
                char[] word = result.get(j);
                max = (word[i] > max ? word[i] : max);
                if (!hash.containsKey(word[i])) {
                    List<Integer> list = new ArrayList<>();
                    list.add(j);
                    hash.put(word[i], list);
                } else {
                    hash.get(word[i]).add(j);
                }
            }
            
            List<Integer> indices = hash.get(max);
            //System.out.println("found max " + max + " size " + indices.size() + " at " + i);
            if (indices.size() == 1 || i == length - 1) {
                char[] finalResult = result.get(indices.get(0));
                for (char c : finalResult) {
                    System.out.print(c);
                }
                break;
            } else {
                List<char[]> temp = new ArrayList<>();
                
                for(int index : hash.get(max)) {
                    temp.add(result.get(index));
                }
                
                result = temp;
            }
        }
    }
}
