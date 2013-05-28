//package AI;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

public class IsWin {

    public int winFunction(GameNode gameNode, int last_col) {
        
        byte[][] gameBoard = Util.buildGameBoardFromNode(gameNode);
        
        return calculateScore(gameBoard, last_col);
    }

    private int calculateScore(byte[][] gameBoard, int last_col) {
        
        int blueScore_total = 0;
        int redScore_total = 0;
        int blueScore_highest = 0;
        int redScore_highest = 0;
        
        int x = last_col;
        
        int y;
        for(y = 0; y < gameBoard[x+3].length; y++){
            if(gameBoard[x+3][y+3] == Util.gamePiece_s){
                y--;
                break;
            }
        }
        
        if(y >= Util.gameHeight){
            //TODO: this shouldn't ever be reached, but it is.
            return 0;
        }
    
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
            Integer score = Util.winMap.get(Arrays.toString(possibleWin));
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
                gameBoard[x+3][y+3] = Util.gamePiece_s;
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
            return blueScore_highest;
        }
        
        // Red win
        else{        
            return redScore_highest;
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
}
