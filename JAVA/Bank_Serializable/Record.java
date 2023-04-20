package Bank_Serializable;

import java.io.*;
import java.util.ArrayList;

public class Record {
    
    private final String filename = "list.ser";
        
    void Save(ArrayList<Client> c) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        try {
            oos.writeObject(c);
        }
        catch(Exception e) {
            //e.printStackTrace();
        }
        oos.close();
    }
    
    void Read() throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
        try {
            Main.com = (ArrayList<Client>) ois.readObject();
        }
        catch(Exception e) {
            //e.printStackTrace();
        }
        ois.close();
    }
    
}
