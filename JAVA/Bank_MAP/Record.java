package Bank_MAP;

import java.io.*;
import java.util.HashMap;

public class Record {
    
    private final String filename = "list.ser";
        
    void Save(MapData m) throws Exception {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(m.GetData());
            oos.close();
        }
        catch(Exception e) {
            //e.printStackTrace();
        }
    }
    
    void Read(MapData m) throws Exception {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            m.ReadDate((HashMap<String, HashMap<String, Integer>>) ois.readObject());
            ois.close();
        }
        catch(Exception e) {
            //e.printStackTrace();
        }
    }
    
}
