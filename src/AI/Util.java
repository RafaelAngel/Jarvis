package AI;

/*
* non-blocking console input courtesy of http://www.darkcoding.net/software/non-blocking-console-io-is-not-possible/
*/

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.Map.Entry;

public final class Util {
    
    private static String ttyConfig;
    
    public static int gameHeight;
    public static int gameWidth;

    public static final HashMap<String, Byte> pieceMap = new HashMap<String, Byte>();
    public static final HashMap<String, Integer> winMap = new HashMap<String, Integer>();

    public static final byte gamePiece_r = new Byte((byte) 00);
    public static final byte gamePiece_b = new Byte((byte) 01);
    public static final byte gamePiece_g = new Byte((byte) 10);
    public static final byte gamePiece_s = new Byte((byte) 11);
    
    public Util(){
        
        pieceMap.put("r", gamePiece_r);
        pieceMap.put("b", gamePiece_b);
        pieceMap.put("g", gamePiece_g);
        pieceMap.put("s", gamePiece_s);

        winMap.put(Arrays.toString(new Byte[] { gamePiece_b, gamePiece_b, gamePiece_g, gamePiece_g }), 5);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_g, gamePiece_b, gamePiece_b }), 5);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_b, gamePiece_g, gamePiece_g, gamePiece_b }), 4);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_b, gamePiece_b, gamePiece_g }), 4);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_b, gamePiece_g, gamePiece_b, gamePiece_g }), 3);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_b, gamePiece_g, gamePiece_b }), 3);

        winMap.put(Arrays.toString(new Byte[] { gamePiece_r, gamePiece_r, gamePiece_g, gamePiece_g }), -5);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_g, gamePiece_r, gamePiece_r }), -5);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_r, gamePiece_g, gamePiece_g, gamePiece_r }), -4);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_r, gamePiece_r, gamePiece_g }), -4);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_r, gamePiece_g, gamePiece_r, gamePiece_g }), -3);
        winMap.put(Arrays.toString(new Byte[] { gamePiece_g, gamePiece_r, gamePiece_g, gamePiece_r }), -3);
        
    }

    public static byte[][] buildGameBoardFromNode(GameNode gameNode) {
        
        GameNode parent = gameNode.parent;
        while(parent.parent != null){
            parent = parent.parent;
        }        
        byte[][] gameBoard = parent.gameTree.gameBoard.clone();
        int i = 0;
        for(byte[] gameRow: parent.gameTree.gameBoard){
            gameBoard[i++] = gameRow.clone();
        }
        
        Stack<Util.Move> moves = new Stack<Util.Move>();

        while(gameNode.parent != null){
            moves.add(new Util.Move(gameNode));
            gameNode = gameNode.parent;
        }
        
        for(Util.Move move: moves){
            int row;
            for(row = 3; gameBoard[move.column + 3][row] != Util.gamePiece_s; row++){
                if(row >= gameBoard[move.column + 3].length){
                    break;
                }
            }
            gameBoard[move.column + 3][row] = move.gamePiece;            
        }     
        
        return gameBoard;
    }
    
    public static class Move{
        public Move(GameNode gameNode) {
            column = gameNode.column;
            gamePiece = gameNode.gamePiece;
        }
        public Move(int col, byte gamepiece) {
            column = col;
            gamePiece = gamepiece;
        }
        int column;
        byte gamePiece;
    }
    
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static String getStandardInput(){
        
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
