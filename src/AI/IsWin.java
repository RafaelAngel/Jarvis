package AI;

import java.util.Map;
import java.util.Map.Entry;

public class IsWin {

    /*public int winFunction(GameNode gameNode) {
        
        byte[][] gameBoard = Util.buildGameBoardFromNode(gameNode);
        
        return calculateScore(gameBoard, gameNode.column);
    }*/

    public int calculateScore(byte[][] gameBoard, int last_col, int last_row) {
                
        int x = last_col;
        int y = last_row;
        
        /*int y;
        for(y = 0; y < gameBoard[x+3].length; y++){
            if(gameBoard[x+3][y+3] == Util.gamePiece_s){
                break;
            }
        }
        
        if(y >= Util.gameHeight){
            //TODO: this shouldn't ever be reached, but it is.
            return 0;
        }*/
    
        Byte[][] possibleWins = new Byte[13][4]; 
        
        int score = 0;
        int highScore = Integer.MIN_VALUE;
        int lowScore = Integer.MAX_VALUE;
        int totalScore = 0;
        
        // Horizontal wins
        //possibleWins[0] = new Byte[] { gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 3], gameBoard[x + 1][y + 3], gameBoard[x][y + 3] };
        score = Util.winMap[gameBoard[x + 3][y + 3] << 6 | gameBoard[x + 2][y + 3] << 4 | gameBoard[x + 1][y + 3] << 2 | gameBoard[x][y + 3]];
        
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[1] = new Byte[] { gameBoard[x + 4][y + 3], gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 3], gameBoard[x + 1][y + 3] };
        score = Util.winMap[gameBoard[x + 4][y + 3] << 6 | gameBoard[x + 3][y + 3] << 4 | gameBoard[x + 2][y + 3] << 2 | gameBoard[x + 1][y + 3]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[2] = new Byte[] { gameBoard[x + 5][y + 3], gameBoard[x + 4][y + 3], gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 3] };
        score = Util.winMap[gameBoard[x + 5][y + 3] << 6 | gameBoard[x + 4][y + 3] << 4 | gameBoard[x + 3][y + 3] << 2 | gameBoard[x + 2][y + 3]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[3] = new Byte[] { gameBoard[x + 6][y + 3], gameBoard[x + 5][y + 3], gameBoard[x + 4][y + 3], gameBoard[x + 3][y + 3] };
        score = Util.winMap[gameBoard[x + 6][y + 3] << 6 | gameBoard[x + 5][y + 3] << 4 | gameBoard[x + 4][y + 3] << 2 | gameBoard[x + 3][y + 3]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        
        // Diagonal wins
        //possibleWins[4] = new Byte[] { gameBoard[x + 6][y + 6], gameBoard[x + 5][y + 5], gameBoard[x + 4][y + 4], gameBoard[x + 3][y + 3] };
        score = Util.winMap[gameBoard[x + 6][y + 6] << 6 | gameBoard[x + 5][y + 5] << 4 | gameBoard[x + 4][y + 4] << 2 | gameBoard[x + 3][y + 3]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[5] = new Byte[] { gameBoard[x + 5][y + 5], gameBoard[x + 4][y + 4], gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 2] };
        score = Util.winMap[gameBoard[x + 5][y + 5] << 6 | gameBoard[x + 4][y + 4] << 4 | gameBoard[x + 3][y + 3] << 2 | gameBoard[x + 2][y + 2]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[6] = new Byte[] { gameBoard[x + 4][y + 4], gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 2], gameBoard[x + 1][y + 1] };
        score = Util.winMap[gameBoard[x + 4][y + 4] << 6 | gameBoard[x + 3][y + 3] << 4 | gameBoard[x + 2][y + 2] << 2 | gameBoard[x + 1][y + 1]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[7] = new Byte[] { gameBoard[x + 3][y + 3], gameBoard[x + 2][y + 2], gameBoard[x + 1][y + 1], gameBoard[x][y] };
        score = Util.winMap[gameBoard[x + 3][y + 3] << 6 | gameBoard[x + 2][y + 2] << 4 | gameBoard[x + 1][y + 1] << 2 | gameBoard[x][y]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[8] = new Byte[] { gameBoard[x][y + 6], gameBoard[x + 1][y + 5], gameBoard[x + 2][y + 4], gameBoard[x + 3][y + 3] };
        score = Util.winMap[gameBoard[x][y + 6] << 6 | gameBoard[x + 1][y + 5] << 4 | gameBoard[x + 2][y + 4] << 2 | gameBoard[x + 3][y + 3]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[9] = new Byte[] { gameBoard[x + 1][y + 5], gameBoard[x + 2][y + 4], gameBoard[x + 3][y + 3], gameBoard[x + 4][y + 2] };
        score = Util.winMap[gameBoard[x + 1][y + 5] << 6 | gameBoard[x + 2][y + 4] << 4 | gameBoard[x + 1][y + 3] << 2 | gameBoard[x + 4][y + 2]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[10] = new Byte[] { gameBoard[x + 2][y + 4], gameBoard[x + 3][y + 3], gameBoard[x + 4][y + 2], gameBoard[x + 5][y + 1] };
        score = Util.winMap[gameBoard[x + 2][y + 4] << 6 | gameBoard[x + 3][y + 3] << 4 | gameBoard[x + 4][y + 2] << 2 | gameBoard[x + 5][y + 1]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //possibleWins[11] = new Byte[] { gameBoard[x + 3][y + 3], gameBoard[x + 4][y + 2], gameBoard[x + 5][y + 1], gameBoard[x + 6][y] };
        score = Util.winMap[gameBoard[x + 3][y + 3] << 6 | gameBoard[x + 4][y + 2] << 4 | gameBoard[x + 5][y + 1] << 2 | gameBoard[x + 6][y]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        
        // Vertical wins
        //possibleWins[12] = new Byte[] { gameBoard[x + 3][y + 3], gameBoard[x + 3][y + 2], gameBoard[x + 3][y + 1], gameBoard[x + 3][y] };
        score = Util.winMap[gameBoard[x + 3][y + 3] << 6 | gameBoard[x + 3][y + 2] << 4 | gameBoard[x + 3][y + 1] << 2 | gameBoard[x + 3][y]];
        totalScore += score;
        highScore = (highScore < score) ? score : highScore;
        lowScore = (lowScore > score) ? score : lowScore;
        
        //tie or no win
        if(totalScore == 0){
            return 0;
        }
        //Blue win
        else if(totalScore > 0){
            return highScore;
        }
        //Red win
        else if(totalScore < 0){
            return lowScore;
        }
        return 0;
               
        /*for(Byte[] possibleWin: possibleWins){
            Byte score = Util.winMap[possibleWin[0] << 6 | possibleWin[1] << 4 | possibleWin[2] << 2 | possibleWin[3]];//Util.winMap.get(Arrays.toString(possibleWin));
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
            }
        }        
        
        // No current win condition
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
        }*/
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
