package AI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

public class IsWin {

    HashMap<String, Byte> pieceMap = new HashMap<String, Byte>();
    HashMap<String, Integer> winMap = new HashMap<String, Integer>();

    private static final byte gamePiece_r = new Byte((byte) 00);
    private static final byte gamePiece_b = new Byte((byte) 01);
    private static final byte gamePiece_g = new Byte((byte) 10);
    private static final byte gamePiece_s = new Byte((byte) 11);
    
    public int winFunction(String input) {

        initializeMaps();
        StringTokenizer tokenizer = new StringTokenizer(input, ",");
        
        int num_col;
        int num_row;
        try {
            num_col = Integer.parseInt((String) tokenizer.nextElement());
            num_row = Integer.parseInt((String) tokenizer.nextElement());
            int last_col = Integer.parseInt((String) tokenizer.nextElement());
            int total_game_time = Integer.parseInt((String) tokenizer.nextElement());
            int player1_time = Integer.parseInt((String) tokenizer.nextElement());
            int last_move_time = Integer.parseInt((String) tokenizer.nextElement());
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + e);
            return 0;
        }
        
        byte[][] gameBoard = new byte[num_col + 6][num_row + 6];
        for(byte[] column: gameBoard){
            Arrays.fill(column, gamePiece_s);
        }

        ArrayList<int[]> activePieces = new ArrayList<int[]>();        
        
        for (int x = 0; x < num_col; x++) {
            for (int y = 0; y < num_row; y++) {
                gameBoard[x + 3][y + 3] = pieceMap.get((String) tokenizer.nextElement());
                if(gameBoard[x + 3][y + 3] != gamePiece_s){
                    int[] piece = {x,y};
                    activePieces.add(piece);
                }
            }
        }
        
       // prints the game board (will be rotated 90 degress)
       /* for (int x = 0; x < num_col; x++) {
            for (int y = 0; y < num_row; y++) {
                System.out.print(getKeyByValue(pieceMap, gameBoard[x + 3][y + 3]));
            }
            System.out.println();
        }*/

        int score = calculateScore(gameBoard, num_col, num_row, activePieces);

        return score;
    }

    private int calculateScore(byte[][] gameBoard, int num_col, int num_row, ArrayList<int[]> activePieces) {
        
        int blueScore_total = 0;
        int redScore_total = 0;
        int blueScore_highest = 0;
        int redScore_highest = 0;
        
        int x;
        int y;
        
        for(int[] piece: activePieces){            
            x = piece[0];
            y = piece[1];
        
            Byte[][] possibleWins = new Byte[13][4]; 
            
            // Horizontal wins
            possibleWins[0] = new Byte[] { gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 3], gameBoard[x + 1][y + 3], gameBoard[x][y + 3] };
            possibleWins[1] = new Byte[] { gameBoard[x + 4][y + 3], gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 3], gameBoard[x + 1][y + 3] };
            possibleWins[2] = new Byte[] { gameBoard[x + 5][y + 3], gameBoard[x + 4][y + 3], gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 3] };
            possibleWins[3] = new Byte[] { gameBoard[x + 6][y + 3], gameBoard[x + 5][y + 3], gameBoard[x + 4][y + 3], gameBoard[x + 3][y + 3] };
            
            // Diagonal wins
            possibleWins[4] = new Byte[] { gameBoard[x + 6][y + 6], gameBoard[x + 5][y + 5], gameBoard[x + 4][y + 4], gameBoard[x + 3][y + 3] };
            possibleWins[5] = new Byte[] { gameBoard[x + 5][y + 5], gameBoard[x + 4][y + 4], gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 2] };
            possibleWins[6] = new Byte[] { gameBoard[x + 4][y + 4], gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 2], gameBoard[x + 1][y + 1] };
            possibleWins[7] = new Byte[] { gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 2], gameBoard[x + 1][y + 1], gameBoard[x][y] };
            possibleWins[8] = new Byte[] { gameBoard[x][y + 6], gameBoard[x + 1][y + 5], gameBoard[x + 2][y + 4], gameBoard[x + 3][y + 3] };
            possibleWins[9] = new Byte[] { gameBoard[x + 1][y + 5], gameBoard[x + 2][y + 4], gameBoard[x + 3][y + 3], gameBoard[x + 4][y + 2] };
            possibleWins[10] = new Byte[] { gameBoard[x + 2][y + 4], gameBoard[x + 3][y + 3], gameBoard[x + 4][y + 2], gameBoard[x + 5][y + 1] };
            possibleWins[11] = new Byte[] { gameBoard[x + 3][y + 3], gameBoard[x + 4][y + 2], gameBoard[x + 5][y + 1], gameBoard[x + 6][y] };
            
            // Vertical wins
            possibleWins[12] = new Byte[] { gameBoard[x + 3][y + 3], gameBoard[x + 3][y + 2], gameBoard[x + 3][y + 1], gameBoard[x + 3][y] };
                   
            for(Byte[] possibleWin: possibleWins){
                Integer score = winMap.get(Arrays.toString(possibleWin));
                if(score != null){
                    if(score > 0){
                        blueScore_total += score; 
                        if(blueScore_highest < score){
                            blueScore_highest = score;
                        }
                    }else if(score < 0){
                        redScore_total -= score;      
                        if(redScore_highest > score){
                            redScore_highest = score;
                        }               
                    }
                    gameBoard[x+3][y+3] = gamePiece_s;
                }
            }                
        }
        
        // No current win condition (game still in progress)
        if(redScore_highest == 0 && blueScore_highest == 0){
            return 0;
        }
        
        // Equal win tie
        else if(redScore_total == blueScore_total){
            return 1;
        }
        
        // Blue win
        else if(blueScore_total > redScore_total){
            return -blueScore_highest;
        }
        
        // Red win
        else{        
            return -redScore_highest;
        }
    }

    private String readStandardInput() {

        String input = "";
        try {
            int nextChar = System.in.read();

            while (nextChar != -1 && ')' != (char) nextChar) {
                input += (char) nextChar;
                // System.out.println(input);
                nextChar = System.in.read();
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
        input = input.substring(1);
        return input;
    }

    private void initializeMaps() {

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

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Entry<T, E> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
