
import java.io.FileNotFoundException;
import java.util.*;

/**
 *
 * @author Vaishali Vijaykumar
 */
public class CheckTime 
{
    public static void main(String[] args) throws FileNotFoundException
    {
    int i;
    //binary heap
    long startTime = System.currentTimeMillis();
    for(i=0; i<10; i++)
    { //run 10 times on given data set
    	System.out.println(i);
        PHeap.main(new String[0]);
    }
    long stopTime = System.currentTimeMillis();
    System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
    
  
    
    // 4-way heap
    startTime = System.currentTimeMillis();
    for(i=0; i<10; i++)
    { //run 10 times on given data set
    	System.out.println(i);
         FWHeap.main(new String[0]);
    }
    stopTime = System.currentTimeMillis();    
    System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
   
    
    // pairing heap
    startTime = System.currentTimeMillis();
    for(i=0;i<10; i++)
    { //run 10 times on given data set
    	System.out.println(i);
    	BHeap.main(new String[0]);
    }
    stopTime = System.currentTimeMillis();    
    System.out.println("Elapsed time was " + (stopTime - startTime) + " miliseconds.");
}
}