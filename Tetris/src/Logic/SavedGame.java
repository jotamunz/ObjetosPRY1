
package Logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SavedGame implements Serializable{
    
    //Save data structure
    public String filename = "Default.tetris";

    //Game data
    public String user;
    public Square [][] logicalBoard;
    public Figure figures[];
    public Square [][] figureBoard;
    public int seconds;
    public int level;
    public double speed; 
    public int score;
    public int lines;
    public SavedGame savedMatch;
    
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
             savedMatch = (SavedGame)in.readObject(); 
             
             this.user = savedMatch.user;
             this.figureBoard = savedMatch.figureBoard;
             this.figures = savedMatch.figures;
             this.level = savedMatch.level;
             this.logicalBoard = savedMatch.logicalBoard;
             this.score = savedMatch.score;
             this.lines = savedMatch.lines;
             this.seconds = savedMatch.seconds;
             this.speed = savedMatch.speed;
              
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
