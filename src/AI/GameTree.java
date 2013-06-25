package AI;

import java.util.ArrayList;
import java.util.Arrays;

public class GameTree {
    
    public byte[][] gameBoard;
    public GameNode head;
    public int boardWidth;
    public int boardHeight;
    public IsWin isWin;
    public int depth;
    
    public int nodesSkipped=0;
    
    public int dupeLevelToCheck = 3;
    public ArrayList<GameNode> dupeNodes = new ArrayList<GameNode>();
    /**
     * Basic GameTree constructor that makes an empty game tree
     * 
     * @param num_col Number of columns in the game
     * @param num_row Number of rows in the game
     */
    public GameTree(int num_col, int num_row, int depth)
    {
        gameBoard = new byte[num_col+6][num_row+6];
        
        head = new GameNode(num_col, this);
        
        boardWidth = num_col;
        boardHeight = num_row;
        
        this.depth=depth;
        isWin = new IsWin();
    }
    
    /**
     * Creates a GameTree from the formatted from file boardInfo
     * @param boardInfo
     */
    public GameTree(byte[][] gameData, int depth)
    {
        gameBoard = gameData;        
        head = new GameNode(gameData.length - 6, this);
        
        boardWidth = gameData.length - 6;
        boardHeight = gameData[0].length - 6;
        
        this.depth=depth;
        isWin = new IsWin();
    }
   
    
    /**
     * Recursively generates the GameTree by adding a level of children for levelsDeep
     * 
     * @param currNode
     * @param levelsDeep
     * @param isJarvisTurn
     */
    public void GenerateChildren(GameNode currNode, int levelsDeep, boolean isJarvisTurn)
    {   
        double score;
        
        if(levelsDeep == 0){
            return;
        }
        //There exists a identical board to currNodes board
        if(currNode.identical!=null)
        {
            return;
        }
        byte colouredNode = Util.gamePiece_r;
        byte greenColouredNode = Util.gamePiece_g;
        if(isJarvisTurn)
        {
            colouredNode = Util.gamePiece_b;
        }
        int newLevelsDeep = levelsDeep-1;
        
        for(int i = 0; i < boardWidth; i++)
        {
            //Count number of pieces already in the column
            GameNode countingNode = currNode.parent;
            if(countingNode == null){
                countingNode = currNode;
            }
            int piecesInColumn = 0;
            while(countingNode != null){
                if( countingNode.column == i){
                    piecesInColumn++;
                }
                if(countingNode.parent == null){
                    int r;
                    for(r = 0; countingNode.gameTree.gameBoard[i + 3][r + 3] != Util.gamePiece_s; r++){}
                    piecesInColumn += r;
                    break;
                }
                countingNode = countingNode.parent;
            }
            
            if(piecesInColumn < boardHeight){
                //add coloured node
                currNode.children[i] = new GameNode(currNode, i, colouredNode, boardWidth);
                if (this.depth-levelsDeep+1 == dupeLevelToCheck)
                {
                    byte[][] childBoard =Util.buildGameBoardFromNode(currNode.children[i]); 
                    GameNode identNode =null;
                    
                    for(GameNode tempNode:dupeNodes){
                        if(Arrays.deepEquals(tempNode.board, childBoard))
                        {
                            identNode = tempNode;
                            break;
                        }
                    }
                    if(identNode ==null){
                        currNode.children[i].board=childBoard;
                        dupeNodes.add(currNode.children[i]);
                    }
                    else{
                        nodesSkipped++;
                        currNode.children[i].identical=identNode;
                        currNode.children[i].score = identNode.score;                 
                        currNode.children[i].children = null;
                    }
                }
            }
            if(currNode.children[i] != null){            
                score = currNode.children[i].calculateScore(isWin);
                if(score > 0){
                    //TODO: win condition, somehow break out of recursion and play this piece if on level 1
                }
                else if(score < 0){
                    //lose condition - do nothing, don't generate more children
                }
                else if(currNode.children[i].children != null){
                    GenerateChildren(currNode.children[i],newLevelsDeep,!isJarvisTurn);
                }
            }
            
            
            //Count number of pieces already in the column
            countingNode = currNode.parent;
            if(countingNode == null){
                countingNode = currNode;
            }
            piecesInColumn = 0;
            while(countingNode != null){
                if( countingNode.column == i){
                    piecesInColumn++;
                }
                if(countingNode.parent == null){
                    int r;
                    for(r = 0; countingNode.gameTree.gameBoard[i + 3][r + 3] != Util.gamePiece_s; r++){}
                    piecesInColumn += r;
                    break;
                }
                countingNode = countingNode.parent;
            }
            if(piecesInColumn < boardHeight){
                //add green node if on level 1
                currNode.children[(boardWidth)*2 - i - 1] = new GameNode(currNode, i, greenColouredNode, boardWidth);
                
                if (this.depth-levelsDeep+1 == dupeLevelToCheck)
                {
                    byte[][] childBoard =Util.buildGameBoardFromNode(currNode.children[(boardWidth)*2 - i - 1]); 
                    GameNode identNode =null;
                    
                    for(GameNode tempNode:dupeNodes){
                        if(Arrays.deepEquals(tempNode.board, childBoard))
                        {
                            identNode = tempNode;
                            break;
                        }
                    }
                    if(identNode ==null){
                        currNode.children[(boardWidth)*2 - i - 1].board=childBoard;
                        dupeNodes.add(currNode.children[(boardWidth)*2 - i - 1]);
                    }
                    else{
                        nodesSkipped++;
                        currNode.children[(boardWidth)*2 - i - 1].identical=identNode;
                        currNode.children[(boardWidth)*2 - i - 1].score = identNode.score;                        
                        currNode.children[(boardWidth)*2 - i - 1].children = null;
                    }
                }
            }
            if(currNode.children[(boardWidth)*2 - i - 1] !=null){
                score = currNode.children[(boardWidth)*2 - i - 1].calculateScore(isWin);
                if(score > 0){
                    //TODO: win condition, somehow break out of recursion if on level 1
                }
                else if(score < 0){
                    //lose condition - do nothing, don't generate more children
                }
                else if(currNode.children[(boardWidth)*2 - i - 1].children != null){
                    GenerateChildren(currNode.children[(boardWidth)*2 - i - 1],newLevelsDeep,!isJarvisTurn);
                }
            }  
        }
    }
}
