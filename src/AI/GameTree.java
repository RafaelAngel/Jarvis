package AI;

public class GameTree {
    
    public GameNode head;
    public GameNode current;
    
    /**
     * Basic GameTree constructor that makes an empty game tree
     * 
     * @param num_col Number of columns in the game
     * @param num_row Number of rows in the game
     */
    public GameTree(int num_col, int num_row)
    {
        head = new GameNode(num_col, num_row);
        
    }
    
    /**
     * Creates a GameTree from the formatted from file boardInfo
     * @param boardInfo
     */
    public GameTree(GameNode node)
    {
        
        
    }

}
