//package AI;

/***
 * A node that represents a paticular board setup in the decision tree.
 * 
 * @author hoshi
 *
 */
public class GameNode {
    
    public GameTree gameTree;
    public GameNode parent;
    public GameNode identical = null;
    public GameNode[] children;
    public byte gamePiece;
    public int column;
    public byte[][] board=null;
    public int score = 0;
    public double childrenScore = 0;

    /**
     * Initial GameNode, used as parent node in the GameTree
     * @param boardWidth
     */
    public GameNode(int boardWidth, GameTree gameTree){
        this.gameTree = gameTree;
        children = new GameNode[boardWidth * 2];        
    }
    
    /**
     * A child GameNode representing an additional piece played 
     * @param parent
     * @param xPos
     * @param piece
     * @param boardWidth
     */
    public GameNode(GameNode parent, int xPos, byte piece, int boardWidth){        
        this.parent = parent;
        column = xPos;
        gamePiece = piece;
        children = new GameNode[boardWidth * 2];        
    }
    
    public double calculateScore(IsWin isWin){        
        score = isWin.winFunction(this);        
        return score;
    }
    
    public double calculateHeuristic(){
        //TODO: add heuristic to favour central columns
        return calculateChildrenScores();
    }
    
    public double calculateChildrenScores(){
        double totalScore = score; 
        
        int depth = 1;
        GameNode father = this.parent;
        while(father != null){
            father = father.parent;
            depth++;
        }
        
        double weight = Math.pow(10, depth);
        
        if(children == null || children.length == 0){
            if(identical!=null)
            {
                return identical.calculateChildrenScores();                
            }
            if(score < 0){
                return (score / weight) * 10;
            }
            return (score / weight);
        }       
        for(GameNode gameNode: children){
            if(gameNode == null){
                continue;
            }
            totalScore += gameNode.calculateChildrenScores();
        }
        if(score < 0){
            return (score / weight) * 10 + totalScore;
        }
        return (score / weight) + totalScore;
    }
    
    /**
     * Used for looking 1 level deep to see if there is a win condition for the opponent.
     * Output will determine if an immediate move is needed to prevent a win.
     * @return
     */
    public int preventChildVictory(){
        
        for(GameNode gameNode: children){
            if(gameNode == null){
                continue;
            }
            if(gameNode.score < 0){
                return 1;
            }
        }
        return 0;
    }
}
