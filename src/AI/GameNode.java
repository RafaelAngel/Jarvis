package AI;

import java.util.Arrays;

/***
 * A node that represents a paticular board setup in the decision tree.
 * 
 * @author hoshi
 *
 */
public class GameNode {
    
    public byte[][] gameBoard;
    public GameNode[] children;
    public GameNode parrent = null;
    
    public GameNode(GameNode parrentBoard, int xPos, byte piece){
        gameBoard = parrentBoard.gameBoard;
        parrent = parrentBoard;
        for (int y = 0; y < gameBoard[xPos].length; y++) {
            
            if(gameBoard[xPos + 3][y + 3] == Util.gamePiece_s){
                gameBoard[xPos + 3][y + 3] = piece;
                break;
            }
        }
        
        
        children = new GameNode[(gameBoard.length -6)*2];
        
    }
    
    public GameNode(byte[][] gameBoard){        
        this.gameBoard = gameBoard;        
        children = new GameNode[(gameBoard.length -6)*2];        
    }
    
    public GameNode(int num_col, int num_row, int xPos, byte piece){
        gameBoard = new byte[num_col+6][num_row+6];
        
        for(byte[] column: gameBoard){
            Arrays.fill(column, Util.gamePiece_s);
        }
               
        gameBoard[xPos + 3][3] = piece;
        
        children = new GameNode[(num_col -6)*2];
        
    }
    
    public GameNode(int num_col, int num_row){
        gameBoard = new byte[num_col+6][num_row+6];
        
        for(byte[] column: gameBoard){
            Arrays.fill(column, Util.gamePiece_s);
        }

        children = new GameNode[(num_col -6)*2];
        
    }

}
