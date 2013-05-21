/*package AI;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Jarvis {

    public static void main(String[] args) throws IOException {

        long startTime = System.nanoTime();
        
        IsWin isWin = new IsWin();
        
        System.out.println("Hello World");

        String input = ""; 
        
        BufferedReader br = new BufferedReader(new FileReader("testData2.txt"));    
        
        int count = 0;
        
        while(br.ready()){                       
            int nextChar = br.read();
            while (nextChar != -1 && ')' != (char) nextChar) {
                input += (char) nextChar;
                nextChar = br.read();
            }  
            input = input.substring(1);
            
            String actual = br.readLine();
            if(actual.contains(String.valueOf(isWin.winFunction(input))) == false){
                //System.out.println(isWin.winFunction(input) + " " + actual + " : " + input + " ;  successful:" + count);
            }else{
                count++;
            }
            input = "";
        }        

        long endTime = System.nanoTime();
        
        System.out.println("Successful wins: " + count + " , running time:" + (endTime - startTime));
        
    }

}
*/