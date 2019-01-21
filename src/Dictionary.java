import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class Dictionary extends HashMap<String, Integer> {

    public Dictionary(String path) {
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
            String line;
            while ((line=br.readLine())!=null) put(line.toLowerCase().trim(), 1);
            br.close();
        } catch (Exception e) {
            System.err.println("Mei Yi broke something again");
            throw new InternalError();
        }
        put("mrjay",1);
        put("meiyi",1);
    }
    
    public boolean exists(String word) {
        return get(word.toLowerCase().trim())!=null;
    }
 
    public static void main (String[] args) {
        Dictionary dict = new Dictionary ("Words.txt");
    }
    
}
