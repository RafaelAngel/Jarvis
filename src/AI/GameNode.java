//package AI;

import java.util.Arrays;

/***
 * A node that represents a paticular board setup in the decision tree.
 * 
 * @author hoshi
 *
 */
public class GameNode {
    
    public byte[][] gameBoard;
    public GameNode parent;
    public GameNode[] children;
    public byte gamePiece;
    public int column;
    public int score = 0;
    public int childrenScore;

    // Initializes gameNode to current board state, used for creating head of gameTree
    public GameNode(byte[][] gameBoard){        
        this.gameBoard = gameBoard;    
        parent = null;
        children = new GameNode[(gameBoard.length -6)*2];        
    }
    
    // Adds a piece at xPos to the parent's board state
    public GameNode(byte[][] parentBoard, int xPos, byte piece, int boardHeight, int boardWidth){
        
        gameBoard = new byte[boardWidth + 6][boardHeight + 6];        
        for(int i = 0; i < parentBoard.length; i++){
            System.arraycopy(parentBoard[i], 0, gameBoard[i], 0, parentBoard[i].length);
        }

        int y;
        for (y = 0; y < gameBoard[xPos].length - 6; y++) {            
            if(gameBoard[xPos + 3][y + 3] == Util.gamePiece_s){
                gameBoard[xPos + 3][y + 3] = piece;
                break;
            }
        }
        
        // This is true if the column was full
        if(y == gameBoard[xPos].length - 6){
            gameBoard = null;
            return;
        }
        
        column = xPos;
        gamePiece = piece;
        children = new GameNode[(gameBoard.length - 6) * 2];        
    }
    
    // Creates gameNode with empty board state and adds a piece to xPos
    public GameNode(int num_col, int num_row, int xPos, byte piece){
        gameBoard = new byte[num_col+6][num_row+6];
        
        for(byte[] column: gameBoard){
            Arrays.fill(column, Util.gamePiece_s);
        }
               
        gameBoard[xPos + 3][3] = piece;

        column = xPos;
        gamePiece = piece;
        children = new GameNode[(num_col -6)*2];
        
    }
    
    // Creates gameNode with empty board state
    public GameNode(int num_col, int num_row){
        gameBoard = new byte[num_col+6][num_row+6];
        
        for(byte[] column: gameBoard){
            Arrays.fill(column, Util.gamePiece_s);
        }

        children = new GameNode[(num_col -6)*2];
        
    }
    
    public int calculateScore(IsWin isWin, int last_col){
        
        score = isWin.winFunction(this, last_col);
        
        return score;
    }
    
    public int calculateChildrensScore(){
        int totalScore = 0;
        if(children == null || children.length == 0){
            return score;
        }        
        for(GameNode gameNode: children){
            if(gameNode == null){
                continue;
            }
            totalScore += gameNode.calculateChildrensScore();
        }
        return totalScore;
    }

}
