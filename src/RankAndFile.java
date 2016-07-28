import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class RankAndFile {
    public static void main(String[] args) throws IOException {
        RankAndFile test = new RankAndFile();
        //test.run(args[0]);
        test.testComb();
    }
    
    private void testComb() {
        List<Set<Integer>> results = new ArrayList<>();
        combinations(new int[]{0,1,2,3,4,5}, 3, 0, results, new int[3]);
        
        for(Set<Integer> list : results) {
            for(int c : list) {
                System.out.print(c + ",");
            }
            System.out.println();
        }
    }
    
    private void run(String filename) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(new File(filename)));
        int numberOfCase = Integer.valueOf(fileReader.readLine());
        
        for (int i = 0; i < numberOfCase; i++) {
            System.out.print("Case #" + (i+1) + ": ");
            int N = Integer.valueOf(fileReader.readLine());
            
            List<String> rnc = new ArrayList<>();
            int[] indices = new int[N];
            for (int t = 0; t < N; t++) {
                rnc.add(fileReader.readLine());
                indices[t] = t;
            }
            
            int realCases = (N+1)/2;
            
            List<Set<Integer>> combination = new ArrayList<>();
            combinations(indices, realCases, 0, combination, new int[realCases]);
            
            List<String> trainSet = new ArrayList<String>();
            List<String> testSet = new ArrayList<String>();
            while (true) {
                trainSet.clear();
                
                for (int t = 0; t < combination.size(); t++) {
                    Set<Integer> trainIndices = combination.get(t);
                    
                    for (int t1 = 0; t1 < realCases; t1++) {
                        if (trainIndices.contains(t1)) {
                            trainSet.add(rnc.get(t1));
                        } else {
                            testSet.add(rnc.get(t1));
                        }
                    }
                    
                    List<String> rowSet = extractRowFromTrainSet(trainSet, N);
                }
            }
        }
        fileReader.close();
    }
    
    public List<String> extractRowFromTrainSet(List<String> trainSet, int length) {
        List<String>[] columns = new ArrayList<>();
        for (int col = 0; col < length; col++) {
            
            for (String str : trainSet) {
                String[] token = str.split(" ");
                columns[col] = token[col];
            }
        }
    }
    
    public void combinations(int[] arr, int k, int startPosition, List<Set<Integer>> result, int[] temp){
        if (k == 0){
            //System.out.println(Arrays.toString(result));
            Set<Integer> set = new HashSet<Integer>();
            for (int num : temp) {
                set.add(num);
            }
            result.add(set);
            return;
        }       
        for (int i = startPosition; i <= arr.length-k; i++){
            temp[temp.length - k] = arr[i];
            combinations(arr, k-1, i+1, result, temp);
        }
    } 
}
