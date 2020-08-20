
package Logic;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SavedScore implements Serializable{
    public String filename = "Scores.tetris";
    public ArrayList<Score> scores = new ArrayList<Score>();
    public SavedScore oldScores;
    
    public void add(Score score){
        scores.add(score);
        Collections.sort(scores);
        if(scores.size() > 10)
        {
            scores.remove(0);
        }
        Collections.reverse(scores);

    }
    
       public void Save()
    {
        try
        {    
            System.out.println(filename);
            //Saving of object in a file             
            FileOutputStream file = new FileOutputStream(filename); 
            ObjectOutputStream out = new ObjectOutputStream(file); 
              
            // Method for serialization of object 
            out.writeObject(this); 
              
            out.close(); 
            file.close(); 
              
            System.out.println("Object has been serialized"); 
  
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
    }
    
    public void Upload()
    {
        try
        {    
            // Reading the object from a file 
            FileInputStream file = new FileInputStream(filename); 
            ObjectInputStream in = new ObjectInputStream(file); 
              
            // Method for deserialization of object 
             oldScores = (SavedScore)in.readObject(); 
             
             this.scores = oldScores.scores;
              
            in.close(); 
            file.close(); 
            
            System.out.println("Object has been deserialized"); 
            
        } 
          
        catch(IOException ex) 
        { 
            System.out.println("IOException is caught"); 
        } 
          
        catch(ClassNotFoundException ex) 
        { 
            System.out.println("ClassNotFoundException is caught"); 
        } 
    }


  }