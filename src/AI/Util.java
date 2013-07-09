package AI;

/*
* non-blocking console input courtesy of http://www.darkcoding.net/software/non-blocking-console-io-is-not-possible/
*/

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

public final class Util {
    
    private static String ttyConfig;
    
    public static int gameHeight;
    public static int gameWidth;

    public static final HashMap<String, Byte> pieceMap = new HashMap<String, Byte>();
    
    public static final byte[] winMap = new byte[256];
    public static final byte[] possibleWinMap = new byte[256];

    public static final byte gamePiece_r = 1;
    public static final byte gamePiece_b = 2;
    public static final byte gamePiece_g = 3;
    public static final byte gamePiece_s = 0;
    public static final byte gamePiece_e = 4;
    
    public static IsWin isWin;
    
    public Util(){
        
        isWin = new IsWin();
        
        pieceMap.put("r", gamePiece_r);
        pieceMap.put("b", gamePiece_b);
        pieceMap.put("g", gamePiece_g);
        pieceMap.put("s", gamePiece_s);

        winMap[gamePiece_b << 6 | gamePiece_b << 4 | gamePiece_g << 2 | gamePiece_g] = 5;
        winMap[gamePiece_g << 6 | gamePiece_g << 4 | gamePiece_b << 2 | gamePiece_b] = 5;
        winMap[gamePiece_b << 6 | gamePiece_g << 4 | gamePiece_g << 2 | gamePiece_b] = 4;
        winMap[gamePiece_g << 6 | gamePiece_b << 4 | gamePiece_b << 2 | gamePiece_g] = 4;
        winMap[gamePiece_b << 6 | gamePiece_g << 4 | gamePiece_b << 2 | gamePiece_g] = 3;
        winMap[gamePiece_g << 6 | gamePiece_b << 4 | gamePiece_g << 2 | gamePiece_b] = 3;

        winMap[gamePiece_r << 6 | gamePiece_r << 4 | gamePiece_g << 2 | gamePiece_g] = -5;
        winMap[gamePiece_g << 6 | gamePiece_g << 4 | gamePiece_r << 2 | gamePiece_r] = -5;
        winMap[gamePiece_r << 6 | gamePiece_g << 4 | gamePiece_g << 2 | gamePiece_r] = -4;
        winMap[gamePiece_g << 6 | gamePiece_r << 4 | gamePiece_r << 2 | gamePiece_g] = -4;
        winMap[gamePiece_r << 6 | gamePiece_g << 4 | gamePiece_r << 2 | gamePiece_g] = -3;
        winMap[gamePiece_g << 6 | gamePiece_r << 4 | gamePiece_g << 2 | gamePiece_r] = -3;

        
        winMap[gamePiece_s << 6 | gamePiece_b << 4 | gamePiece_g << 2 | gamePiece_g] = 5;
        winMap[gamePiece_b << 6 | gamePiece_s << 4 | gamePiece_g << 2 | gamePiece_g] = 5;
        winMap[gamePiece_b << 6 | gamePiece_b << 4 | gamePiece_s << 2 | gamePiece_g] = 5;
        winMap[gamePiece_b << 6 | gamePiece_b << 4 | gamePiece_g << 2 | gamePiece_s] = 5;
        
        winMap[gamePiece_s << 6 | gamePiece_g << 4 | gamePiece_b << 2 | gamePiece_b] = 5;
        winMap[gamePiece_g << 6 | gamePiece_s << 4 | gamePiece_b << 2 | gamePiece_b] = 5;
        winMap[gamePiece_g << 6 | gamePiece_g << 4 | gamePiece_s << 2 | gamePiece_b] = 5;
        winMap[gamePiece_g << 6 | gamePiece_g << 4 | gamePiece_b << 2 | gamePiece_s] = 5;

        winMap[gamePiece_s << 6 | gamePiece_g << 4 | gamePiece_g << 2 | gamePiece_b] = 4;
        winMap[gamePiece_b << 6 | gamePiece_s << 4 | gamePiece_g << 2 | gamePiece_b] = 4;
        winMap[gamePiece_b << 6 | gamePiece_g << 4 | gamePiece_s << 2 | gamePiece_b] = 4;
        winMap[gamePiece_b << 6 | gamePiece_g << 4 | gamePiece_g << 2 | gamePiece_s] = 4;

        winMap[gamePiece_s << 6 | gamePiece_b << 4 | gamePiece_b << 2 | gamePiece_g] = 4;
        winMap[gamePiece_g << 6 | gamePiece_s << 4 | gamePiece_b << 2 | gamePiece_g] = 4;
        winMap[gamePiece_g << 6 | gamePiece_b << 4 | gamePiece_s << 2 | gamePiece_g] = 4;
        winMap[gamePiece_g << 6 | gamePiece_b << 4 | gamePiece_b << 2 | gamePiece_s] = 4;

        winMap[gamePiece_s << 6 | gamePiece_g << 4 | gamePiece_b << 2 | gamePiece_g] = 3;
        winMap[gamePiece_b << 6 | gamePiece_s << 4 | gamePiece_b << 2 | gamePiece_g] = 3;
        winMap[gamePiece_b << 6 | gamePiece_g << 4 | gamePiece_s << 2 | gamePiece_g] = 3;
        winMap[gamePiece_b << 6 | gamePiece_g << 4 | gamePiece_b << 2 | gamePiece_s] = 3;

        winMap[gamePiece_s << 6 | gamePiece_b << 4 | gamePiece_g << 2 | gamePiece_b] = 3;
        winMap[gamePiece_g << 6 | gamePiece_s << 4 | gamePiece_g << 2 | gamePiece_b] = 3;
        winMap[gamePiece_g << 6 | gamePiece_b << 4 | gamePiece_s << 2 | gamePiece_b] = 3;
        winMap[gamePiece_g << 6 | gamePiece_b << 4 | gamePiece_g << 2 | gamePiece_s] = 3;
        
        winMap[gamePiece_s << 6 | gamePiece_r << 4 | gamePiece_g << 2 | gamePiece_g] = -5;
        winMap[gamePiece_r << 6 | gamePiece_s << 4 | gamePiece_g << 2 | gamePiece_g] = -5;
        winMap[gamePiece_r << 6 | gamePiece_r << 4 | gamePiece_s << 2 | gamePiece_g] = -5;
        winMap[gamePiece_r << 6 | gamePiece_r << 4 | gamePiece_g << 2 | gamePiece_s] = -5;
        
        winMap[gamePiece_s << 6 | gamePiece_g << 4 | gamePiece_r << 2 | gamePiece_r] = -5;
        winMap[gamePiece_g << 6 | gamePiece_s << 4 | gamePiece_r << 2 | gamePiece_r] = -5;
        winMap[gamePiece_g << 6 | gamePiece_g << 4 | gamePiece_s << 2 | gamePiece_r] = -5;
        winMap[gamePiece_g << 6 | gamePiece_g << 4 | gamePiece_r << 2 | gamePiece_s] = -5;
        
        winMap[gamePiece_s << 6 | gamePiece_g << 4 | gamePiece_g << 2 | gamePiece_r] = -4;
        winMap[gamePiece_r << 6 | gamePiece_s << 4 | gamePiece_g << 2 | gamePiece_r] = -4;
        winMap[gamePiece_r << 6 | gamePiece_g << 4 | gamePiece_s << 2 | gamePiece_r] = -4;
        winMap[gamePiece_r << 6 | gamePiece_g << 4 | gamePiece_g << 2 | gamePiece_s] = -4;
        
        winMap[gamePiece_s << 6 | gamePiece_r << 4 | gamePiece_r << 2 | gamePiece_g] = -4;
        winMap[gamePiece_g << 6 | gamePiece_s << 4 | gamePiece_r << 2 | gamePiece_g] = -4;
        winMap[gamePiece_g << 6 | gamePiece_r << 4 | gamePiece_s << 2 | gamePiece_g] = -4;
        winMap[gamePiece_g << 6 | gamePiece_r << 4 | gamePiece_r << 2 | gamePiece_s] = -4;
        
        winMap[gamePiece_s << 6 | gamePiece_g << 4 | gamePiece_r << 2 | gamePiece_g] = -3;
        winMap[gamePiece_r << 6 | gamePiece_s << 4 | gamePiece_r << 2 | gamePiece_g] = -3;
        winMap[gamePiece_r << 6 | gamePiece_g << 4 | gamePiece_s << 2 | gamePiece_g] = -3;
        winMap[gamePiece_r << 6 | gamePiece_g << 4 | gamePiece_r << 2 | gamePiece_s] = -3;
        
        winMap[gamePiece_s << 6 | gamePiece_r << 4 | gamePiece_g << 2 | gamePiece_r] = -3;
        winMap[gamePiece_g << 6 | gamePiece_s << 4 | gamePiece_g << 2 | gamePiece_r] = -3;
        winMap[gamePiece_g << 6 | gamePiece_r << 4 | gamePiece_s << 2 | gamePiece_r] = -3;
        winMap[gamePiece_g << 6 | gamePiece_r << 4 | gamePiece_g << 2 | gamePiece_s] = -3;
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

        while(!moves.isEmpty()){
            Move move = moves.pop();
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
    
    public static class Move implements Cloneable{
        int column = 0;
        byte gamePiece;
        int score = 0;
        public Move(){};
        public Move(GameNode gameNode) {
            column = gameNode.column;
            gamePiece = gameNode.gamePiece;
        }
        public Move(int col, byte gamepiece) {
            column = col;
            gamePiece = gamepiece;
        }
        public Move(int col, int score) {
            column = col;
            this.score = score;
        }
        public Move(int col, int score, byte gamePiece){
            column = col;
            this.score = score;
            this.gamePiece = gamePiece;
        }
        @Override
        protected Move clone() {
            return new Move(this.column, this.score, this.gamePiece);
        }
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
    
    public static int findYFromCol(byte[][] gameBoard, int x)
    {
        int y = 0;
        
        for(y = 0; y < gameBoard[x+3].length; y++){
            int length = gameBoard[x+3].length;
            if(gameBoard[x+3][y+3] == Util.gamePiece_s || gameBoard[x+3][y+3] == Util.gamePiece_e){
                y--;
                break;
            }
        }
        
        if(y >= Util.gameHeight){
            //TODO: this shouldn't ever be reached, but it is.
            y = 0;
        }
        
        return y;
    }
    
    /**
     * Util Function that returns the number of possible wins in any paticular board
     * 
     * @param gameBoard - the board
     * @param colour - which player we are calculating wins for, red or blue
     * @return
     */
    private static int numPossibleWins(byte[][] possibleWingameBoard)
    {
        int numWins = 0;
        
        return numWins;
    
    }
    
   /**
    * Util Function that returns the number of possible wins in any paticular board
    * 
    * This one only calculates the wins of a paticular piece, the top piece of a
    *   column  
    *
    * @param gameBoard - the board
    * @param col - the column
    * 
    * @return a Length 2 int array with the first element the number of blue wins
    *   and the secont the number of red wins
    */
    public static int[] numPossibleWins(byte[][] gameBoard, int col)
    {
        int numWins[] = {0,0};
        int space= 0;
        int i = 0;
        
        Byte[] possibleWins = new Byte[13]; 
        
        int x = col;
        int y = findYFromCol(gameBoard, x);
        
     // Horizontal wins
        possibleWins[0] =   (byte) (gameBoard[x + 3][y + 3] << 6 | gameBoard[x + 2][y + 3] << 4 | gameBoard[x + 1][y + 3] << 2 | gameBoard[x][y + 3]) ;
        possibleWins[1] = (byte) (gameBoard[x + 4][y + 3] << 6 | gameBoard[x + 3][y + 3] << 4 | gameBoard[x + 2][y + 3]<< 2 | gameBoard[x + 1][y + 3]) ;
        possibleWins[2] = (byte) (gameBoard[x + 5][y + 3] << 6 | gameBoard[x + 4][y + 3] << 4 | gameBoard[x + 3][y + 3] << 2 | gameBoard[x + 2][y + 3]) ;
        possibleWins[3] = (byte) (gameBoard[x + 6][y + 3] << 6 | gameBoard[x + 5][y + 3] << 4 | gameBoard[x + 4][y + 3] << 2 | gameBoard[x + 3][y + 3]) ;
        
        // Diagonal wins
        possibleWins[4] =  (byte) (gameBoard[x + 6][y + 6] << 6 | gameBoard[x + 5][y + 5] << 4 | gameBoard[x + 4][y + 4] << 2 | gameBoard[x + 3][y + 3]);
        possibleWins[5] =  (byte) (gameBoard[x + 5][y + 5] << 6 | gameBoard[x + 4][y + 4] << 4 | gameBoard[x + 3][y + 3] << 2 | gameBoard[x + 2][y + 2]);
        possibleWins[6] =  (byte) (gameBoard[x + 4][y + 4] << 6 | gameBoard[x + 3][y + 3] << 4 | gameBoard[x + 2][y + 2] << 2 | gameBoard[x + 1][y + 1]);
        possibleWins[7] =  (byte) (gameBoard[x + 3][y + 3] << 6 | gameBoard[x + 2][y + 2] << 4 | gameBoard[x + 1][y + 1] << 2 | gameBoard[x][y]);
        possibleWins[8] =  (byte) (gameBoard[x][y + 6] << 6 | gameBoard[x + 1][y + 5] << 4 | gameBoard[x + 2][y + 4] << 2 | gameBoard[x + 3][y + 3]);
        possibleWins[9] =  (byte) (gameBoard[x + 1][y + 5] << 6 | gameBoard[x + 2][y + 4] << 4 | gameBoard[x + 3][y + 3] << 2 | gameBoard[x + 4][y + 2]);
        possibleWins[10] = (byte) (gameBoard[x + 2][y + 4] << 6 | gameBoard[x + 3][y + 3] << 4 | gameBoard[x + 4][y + 2] << 2 | gameBoard[x + 5][y + 1]);
        possibleWins[11] = (byte) (gameBoard[x + 3][y + 3] << 6 | gameBoard[x + 4][y + 2] << 4 | gameBoard[x + 5][y + 1] << 2 | gameBoard[x + 6][y]);
        
        // Vertical wins
        possibleWins[12] =  (byte) (gameBoard[x + 3][y + 3]<< 6 | gameBoard[x + 3][y + 2]<< 4 | gameBoard[x + 3][y + 1]<< 2 | gameBoard[x + 3][y]);
        
        for(Byte possibleWin: possibleWins){
            //There is only one space
            /*if(possibleWin[0]==gamePiece_s ^ possibleWin[1]==gamePiece_s ^possibleWin[2]==gamePiece_s ^possibleWin[3]==gamePiece_s){
                
            }
            */
            
            space =0;
            if(((possibleWin&192)>>6==0) ^((possibleWin&48)>>4==0)^((possibleWin&12)>>2==0)^((possibleWin&3)==0)){
                space = 1;
            }
            
            
            if (space==1){
                
                for(i = 0; i<4; i++){
                    Integer score = Util.possibleWinMap[possibleWin];
                    if(score != null){
                        if(score>0){
                            numWins[0]++;
                        }
                        else{
                            numWins[1]++;
                        }
                        
                    }
                    
                }
            }
            
                
        }
        
        
        return numWins;
    }

}
