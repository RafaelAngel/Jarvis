package AI;

public class GameTree {
    
    public GameNode head;
    public GameNode current;
    public int boardWidth;
    public int boardHeight;
    
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
    }
    
    /**
     * Creates a GameTree from the formatted from file boardInfo
     * @param boardInfo
     */
    public GameTree(byte[][] gameData)
    {
        head = new GameNode(gameData);
        current = head;
        
        boardWidth = gameData.length;
        boardHeight = gameData[0].length;
    }
    
    public void GenerateChildren(GameNode currNode, int levelsDeep)
    {
        boolean isJarvisTurn = true;
        
        
        
    }

}
