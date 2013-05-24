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
    
    private static final byte gamePiece_r = new Byte((byte) 00);
    private static final byte gamePiece_b = new Byte((byte) 01);
    private static final byte gamePiece_g = new Byte((byte) 10);
    private static final byte gamePiece_s = new Byte((byte) 11);
    
    public GameNode(byte[][] parrentBoard, int xPos, byte piece){
        gameBoard = parrentBoard;
        
        for (int y = 0; y < gameBoard[xPos].length; y++) {
            
            if(gameBoard[xPos + 3][y + 3] == gamePiece_s){
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
            Arrays.fill(column, gamePiece_s);
        }
               
        gameBoard[xPos + 3][3] = piece;
        
        children = new GameNode[(num_col -6)*2];
        
    }
    
    public GameNode(int num_col, int num_row){
        gameBoard = new byte[num_col+6][num_row+6];
        
        for(byte[] column: gameBoard){
            Arrays.fill(column, gamePiece_s);
        }

        children = new GameNode[(num_col -6)*2];
        
    }

}
