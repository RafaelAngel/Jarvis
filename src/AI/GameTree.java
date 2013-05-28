//package AI;

public class GameTree {
    
    public byte[][] gameBoard;
    public GameNode head;
    public int boardWidth;
    public int boardHeight;
    public IsWin isWin;
    
    /**
     * Basic GameTree constructor that makes an empty game tree
     * 
     * @param num_col Number of columns in the game
     * @param num_row Number of rows in the game
     */
    public GameTree(int num_col, int num_row)
    {
        gameBoard = new byte[num_col+6][num_row+6];
        
        head = new GameNode(num_col, this);
        
        boardWidth = num_col;
        boardHeight = num_row;
        isWin = new IsWin();
    }
    
    /**
     * Creates a GameTree from the formatted from file boardInfo
     * @param boardInfo
     */
    public GameTree(byte[][] gameData)
    {
        gameBoard = gameData;        
        head = new GameNode(gameData.length - 6, this);
        
        boardWidth = gameData.length - 6;
        boardHeight = gameData[0].length - 6;
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
        if(levelsDeep == 0){
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
            GameNode countingNode = currNode.parent;
            int piecesInColumn = 0;
            while(countingNode != null){
                if( countingNode.column == i){
                    piecesInColumn++;
                }
                countingNode = countingNode.parent;
            }
            if(piecesInColumn < boardHeight){
                //add coloured node
                currNode.children[i] = new GameNode(currNode, i, colouredNode, boardWidth);
            }            
            if(currNode.children[i] != null){            
                double score = currNode.children[i].calculateScore(isWin, i);
                if(score > 0){
                    //TODO: win condition, somehow break out of recursion and play this piece
                }
                else if(score < 0){
                    //lose condition - do nothing, don't generate more children
                }
                else{
                    GenerateChildren(currNode.children[i],newLevelsDeep,!isJarvisTurn);
                }
            }
            
            
            countingNode = currNode.parent;
            piecesInColumn = 0;
            while(countingNode != null){
                if( countingNode.column == i){
                    piecesInColumn++;
                }
                countingNode = countingNode.parent;
            }
            if(piecesInColumn < boardHeight){
                //add green node
                currNode.children[(boardWidth)*2 - i - 1] = new GameNode(currNode, i, greenColouredNode, boardWidth);
            }                        
            if(currNode.children[(boardWidth)*2 - i - 1] !=null){
                double score = currNode.children[(boardWidth)*2 - i - 1].calculateScore(isWin, i);
                if(score > 0){
                    //TODO: win condition, somehow break out of recursion
                }
                else if(score < 0){
                    //lose condition - do nothing, don't generate more children
                }
                else{
                    GenerateChildren(currNode.children[(boardWidth)*2 - i - 1],newLevelsDeep,!isJarvisTurn);
                }
            }         
        }
    }
}
