//package AI;

public class GameTree {
    
    public GameNode head;
    public GameNode current;
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
        head = new GameNode(num_col, num_row);
        current = head;
        
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
        head = new GameNode(gameData);
        current = head;
        
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
            //add coloured node
            if(currNode.children[i]==null){
                currNode.children[i] = new GameNode(currNode.gameBoard, i, colouredNode, boardHeight, boardWidth);
            }
            if(currNode.children[i].gameBoard == null){
                //gameboard is set to null if the column is full, this is an invalid child
                currNode.children[i] = null;
            }
            else{
                int score = currNode.children[i].calculateScore(isWin, i);
                if(score > 0){
                    //TODO: win condition, somehow break out of recursion
                }
                else if(score < 0){
                    //lose condition - do nothing, don't generate more children
                }
                else{
                    GenerateChildren(currNode.children[i],newLevelsDeep,!isJarvisTurn);
                }
            }
            
            //add green
            if(currNode.children[(boardWidth)*2 - i - 1] ==null){
                currNode.children[(boardWidth)*2 - i - 1]  = new GameNode(currNode.gameBoard, i, greenColouredNode, boardHeight, boardWidth);
            }
            if(currNode.children[(boardWidth)*2 - i - 1].gameBoard == null){
                //gameboard is set to null if the column is full, this is an invalid child
                currNode.children[(boardWidth)*2 - i - 1] = null;
            }
            else{
                int score = currNode.children[(boardWidth)*2 - i - 1].calculateScore(isWin, i);
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
