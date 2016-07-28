import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;


public class Fractiles {
    public static void main(String[] args) throws IOException {
        Fractiles test = new Fractiles();
        test.run(args[0]);
        // test.testP();
        // test.testComb();
    }
    
    private void testComb() {
        List<char[]> results = new ArrayList<>();
        combination("012", results);
        
        for(char[] list : results) {
            for(char c : list) {
                System.out.print(c + ",");
            }
            System.out.println();
        }
    }
    
    private void testP() {
        int[] init = new int[2];
        List<ArtWork> artworks = new ArrayList<>();
        permutateStr(init, artworks, 0);
        
        for (int i = 0; i < artworks.size(); i++) {
            System.out.print(i + ": ");
            for (int num : artworks.get(i).tiles) {
                System.out.print(num + ",");
            }
            System.out.println();
        }
        
        makeTile(artworks, 3);
        for (int i = 0; i < artworks.size(); i++) {
            System.out.print(i + ": ");
            for (int num : artworks.get(i).tiles) {
                System.out.print(num + ",");
            }
            System.out.println("=" + artworks.get(i).hasGold);
        }
    }
    
    public void combination(String s, List<char[]> results) { 
        combRecursive("", s, results);
    }
    
    private void combRecursive(String prefix, String s, List<char[]> results) {
        if (s.length() > 0) {
            //System.out.println(prefix + s.charAt(0));
            results.add(new String(prefix + s.charAt(0)).toCharArray());
            combRecursive(prefix + s.charAt(0), s.substring(1), results);
            combRecursive(prefix,               s.substring(1), results);
        }
    }
    
    private void run(String filename) throws IOException {
        BufferedReader fileReader = new BufferedReader(new FileReader(new File(filename)));
        int numberOfCase = Integer.valueOf(fileReader.readLine());
        
        for (int i = 0; i < numberOfCase; i++) {
            System.out.print("Case #" + (i+1) + ": ");
            String[] line = fileReader.readLine().split(" ");
            
            int K = Integer.valueOf(line[0]); 
            int C = Integer.valueOf(line[1]);
            int S = Integer.valueOf(line[2]);
            
            long length = BigDecimal.valueOf(Math.pow(K, C)).longValueExact();
            
            int[] init = new int[K];
            List<ArtWork> artworks = new ArrayList<>();
            permutateStr(init, artworks, 0);
            
            makeTile(artworks, C);
            
            StringBuilder seed = new StringBuilder();
            for(int index = 0; index < length; index++) {
                seed.append(index);
            }
            
            List<char[]> results = new ArrayList<>();
            combination(seed.toString(), results);
            
            List<int[]> ambigousList = new ArrayList<>();
            generateOptions(artworks, ambigousList);
            
            boolean found = false;
            
            for(char[] indices : results) {
                if (indices.length <= S) { // only within budget
                    List<int[]> pickedList = new ArrayList<int[]>();
                    for(char index : indices) {
                        pickedList.add(ambigousList.get(index));
                    }
                    
                    int rowProduct = 1;
                    for(int row = 0; row < artworks.size(); row++) {
                        for(int col = 0; col < pickedList.size(); col++) {
                            rowProduct *= pickedList.get(row)[col];
                        }
                        rowProduct += rowProduct;
                    }
                    
                    if(rowProduct == 0) {
                        found = true;
                        break;
                    }
                }
            }
            
            if (found) {
                System.out.println("Yes");
            } else {
                System.out.println("IMPOSSIBLE");
            }
        }
        fileReader.close();
    }
    
    private void generateOptions(List<ArtWork> artworks, List<int[]> ambigousList) {
        for(ArtWork artWork : artworks) {
            int[] ambigous = new int[artWork.tiles.length];
            int index = 0;
            for(int tile : artWork.tiles) {
                if (artWork.hasGold && tile == 0) {
                    ambigous[index++] = 1;
                } else {
                    ambigous[index++] = 0;
                }
            }
            ambigousList.add(ambigous);
        }
    }
    
    private void debugArray(String title, int[] array) {
        System.out.print(title + ":");
        for (int a : array) {
            System.out.print(a + ",");
        }
        System.out.println();
    }

    private void debugQueue(Queue<Integer> q) {
        System.out.print("Q:");
        for (int i : q) {
            System.out.print(i + ",");
        }
        System.out.println();
    }
    
    private void makeTile(List<ArtWork> artworks, int depth) {
        Queue<Integer> q = new LinkedList<>();
        for (ArtWork artwork : artworks) {
            int[] tiles = artwork.tiles;

            for (int tile : tiles) {
                q.add(tile);
            }
            //debugQueue(q);

            int currentDepth = tiles.length;
            int count = 1;
            while (count < depth) {
                int[] tokens = extractTokens(q, currentDepth);
                //debugArray("extract", tokens);
                for (int token : tokens) {
                    if (token == 1) {
                        for (int j = 0; j < tiles.length; j++) {
                            q.add(1);
                        }
                    } else {
                        for (int tile : tiles) {
                            q.add(tile);
                        }
                    }
                }
                
                currentDepth = currentDepth * tiles.length;
                count++;
            }
            //debugQueue(q);
            artwork.tiles = finalTiles(q);
            //debugQueue(q);
        }
    }
    
    private int[] finalTiles(Queue<Integer> q) {
        int[] finalTiles = new int[q.size()];
        int i = 0;
        while(!q.isEmpty()) {
            finalTiles[i++] = q.poll();
        }

        return finalTiles;
    }
    
    private int[] extractTokens(Queue<Integer> q, int currentDepth) {
        int[] tokens = new int[currentDepth];
        
        for (int i = 0; i < currentDepth; i++) {
            tokens[i] = q.poll();
        }
        return tokens;
    }
    
    private void permutateStr(int[] array, List<ArtWork> results, int n) {
        if (n == array.length) {
            ArtWork tiles = new ArtWork(array.clone());
            results.add(tiles);
            //results.add(String.copyValueOf(array));
        } else {
            permutateStr(array, results, n+1);

            array[n] = 1;
            permutateStr(array, results, n+1);
            array[n] = 0;
        }
    }
    
    public static class ArtWork {
        public boolean hasGold;
        
        public int[] tiles;
        
        public ArtWork(int[] tiles) {
            this.tiles = tiles;
            //int value = Integer.valueOf(String.copyValueOf(tiles));
            int total = 0;
            for (int value : tiles) {
                total += value;
            }
            
            if (total != 0) {
                hasGold = true;
            }
        }
    }
}
