package AI;

/*
* non-blocking console input courtesy of http://www.darkcoding.net/software/non-blocking-console-io-is-not-possible/
*/

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Jarvis {
    
    private static String ttyConfig;

    public static void main(String[] args) {
        
        String input = getStandardInput();
        IsWin isWin = new IsWin();                    
        int score = isWin.winFunction(input);
        System.out.println(score);    
    }
    
    private static String getStandardInput(){
        
        String input = "";
        
        try {
            setTerminalToCBreak();
            char character = (char) System.in.read();
            while (character != ')') {
                if ( System.in.available() != 0 ) {
                    input += character;
                    character = (char) System.in.read();
                }
            } // end while
        }
        catch (IOException e) {
            System.err.println("IOException");
        }
        catch (InterruptedException e) {
            System.err.println("InterruptedException");
        }
        finally 
        {
            try {
                stty( ttyConfig.trim() );
            }
            catch (Exception e) {
                System.err.println("Exception restoring tty config");
            }
        }
        input = input.substring(1);   
        return input;
    }

    private static void setTerminalToCBreak() throws IOException, InterruptedException {

        ttyConfig = stty("-g");

        // set the console to be character-buffered instead of line-buffered
        stty("-icanon min 1");

        // disable character echoing
        stty("-echo");
    }

    /**
     *  Execute the stty command with the specified arguments
     *  against the current active terminal.
     */
    private static String stty(final String args)
                    throws IOException, InterruptedException {
        String cmd = "stty " + args + " < /dev/tty";

        return exec(new String[] {
                    "sh",
                    "-c",
                    cmd
                });
    }

    /**
     *  Execute the specified command and return the output
     *  (both stdout and stderr).
     */
    private static String exec(final String[] cmd)
                    throws IOException, InterruptedException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        Process p = Runtime.getRuntime().exec(cmd);
        int c;
        InputStream in = p.getInputStream();

        while ((c = in.read()) != -1) {
            bout.write(c);
        }

        in = p.getErrorStream();

        while ((c = in.read()) != -1) {
            bout.write(c);
        }

        p.waitFor();

        String result = new String(bout.toByteArray());
        return result;
    }
}
