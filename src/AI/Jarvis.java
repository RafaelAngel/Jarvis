//package AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Jarvis {

    private static int num_col;
    private static int num_row;
    private static int last_col;
    private static int total_game_time;
    private static int player1_time ;
    private static int last_move_time;
    
    /**
     * Number of moves Jarvis looks into the "future"
     */
    public static int movesDepth = 5;
    
    private static IsWin isWin;
    
    public static void main(String[] args) {
        
        Util util = new Util();      
        String input = Util.getStandardInput();        
        //long startTime = System.currentTimeMillis();
        GameTree gameTree = createGameTree(input);
        
        gameTree.GenerateChildren(gameTree.head, movesDepth, true);
        
        Util.Move winningMove = findOptimalMove(gameTree.head);
                
        isWin = new IsWin();                    
        //int score = isWin.winFunction(gameTree.head, last_col);
        
        //long elapsedTime = System.currentTimeMillis() - startTime;
        //printBoard(gameTree.gameBoard);
        //System.out.println("Time elapsed: " + elapsedTime);
        //System.out.println("Ident Boards Skipped: " + gameTree.nodesSkipped );
        System.out.println("(" + (winningMove.column + 1) + "," + Util.getKeyByValue(Util.pieceMap, winningMove.gamePiece) + ")");    
    }
    
    private static void printBoard(byte[][] gameBoard){
        // prints the game board (will be rotated 90 degress)gameTree
         for (int x = 0; x < num_col; x++) {
             for (int y = 0; y < num_row; y++) {
                 System.out.print(Util.getKeyByValue(Util.pieceMap, gameBoard[x + 3][y + 3]));
             }
             System.out.println();
         }
    }
    
    private static int player_minimax(GameNode node){
        if(node.score != 0 || node.children == null){
            return node.score;
        }
        boolean isempty = true;
        for(GameNode child: node.children){
            if(child != null){
                isempty = false;
                break;
            }
        }
        if(isempty){
            return 0;
        }
        Integer alpha = Integer.MAX_VALUE;
        for(GameNode child: node.children){
            if(child == null) continue;
            alpha = Math.min(alpha, oponent_minimax(child));
        }
        return alpha;
    }
    
    private static int oponent_minimax(GameNode node){
        if(node.score != 0 || node.children == null){
            return node.score;
        }
        boolean isempty = true;
        for(GameNode child: node.children){
            if(child != null){
                isempty = false;
                break;
            }
        }
        if(isempty){
            return 0;
        }
        Integer alpha = Integer.MIN_VALUE;
        for(GameNode child: node.children){
            if(child == null) continue;
            alpha = Math.max(alpha, player_minimax(child));
        }
        return alpha;
    }
    
    private static Util.Move findOptimalMove(GameNode gameNodeHead){
        
        double highscore = Integer.MIN_VALUE;
        GameNode bestGameBoard = null;        
        
        for(GameNode child: gameNodeHead.children){
            if(child == null) continue;
            
            double score = player_minimax(child);
            if(score == Integer.MIN_VALUE) score = 0;
            
            //Give higher weighting to central columns. Will add maximum of 0.5 score
            score = score + 1/(Math.exp(Math.pow(child.column - Util.gameWidth/2,2)))/2;            
            //System.out.println(score);
            
            if(score > highscore){
                bestGameBoard = child;
                highscore = score;
            }
        }
        
        return new Util.Move(bestGameBoard);        
    }
    
    /*private static Util.Move findOptimalMove(GameNode gameNodeHead) {
        
        GameNode bestGameBoard = null;
        double highestScore = -10;
        double heuristic;
        
        ArrayList<GameNode> badMoves = new ArrayList<GameNode>();
        
        //Find if any immediate move will result in a win
        //also builds badMoves list, list of nodes that will result in immediate opponent victor
        for(GameNode gameNode: gameNodeHead.children){       
            if(gameNode == null) continue;
            if(gameNode.score > 0){
                //immediate move to win
                switch((int)gameNode.score){
                    case 5:
                        System.err.println("Initiating the clean slate protocol.");                        
                        break;
                    case 4:                        
                        System.err.println("Test complete. Preparing to power down and begin diagnostics...");
                        break;
                    case 3:
                        System.err.println("Upgrades are going well, Sir, but it seems when I get to the end of a sentence I say the wrong cranberry.");
                        break;
                    }                
                return new Util.Move(gameNode);
            }
            if(gameNode.preventChildVictory() == 1 || gameNode.score < 0){
                badMoves.add(gameNode);
            }
        }           
        
        //Finds best move ignoring badMoves List
        for(GameNode gameNode: gameNodeHead.children){ 
            if(gameNode == null) continue;
            if(badMoves.contains(gameNode)) continue;
            heuristic = gameNode.calculateHeuristic();
            if(highestScore < heuristic){
                highestScore = heuristic;
                bestGameBoard = gameNode;
            }                    
        }        
        
        //If no bestGameBoard is chosen, find column with available space starting from the center column, expanding outwards
        if(bestGameBoard == null){
            int start = (int) Math.floor(Util.gameWidth / 2) - 1;
            for(int col = 0; col < start ; col++){
                byte[] column = gameNodeHead.gameTree.gameBoard[start + 3 + col];   
                if(column[num_row + 2] == Util.gamePiece_s){
                    return new Util.Move(start + col, Util.gamePiece_b);
                }
                column = gameNodeHead.gameTree.gameBoard[start + 3 - col];  
                if(column[num_row + 2] == Util.gamePiece_s){
                    return new Util.Move(start - col, Util.gamePiece_b);
                }                
            }
        }
        
        //ensures the chosen bestGameBoard has a free space in top position
        for(byte[] column: Arrays.copyOfRange(gameNodeHead.gameTree.gameBoard, 3, num_col + 3)){                           
             if(column[num_row + 2] == Util.gamePiece_s){
                 return new Util.Move(bestGameBoard);
             }
        } 

        //This shot not occur
        return new Util.Move(bestGameBoard);
    }*/
    
    private static GameTree createGameTree(String input){
        
        StringTokenizer tokenizer = new StringTokenizer(input, ",");
        
        try {
            num_col = Integer.parseInt((String) tokenizer.nextElement());
            num_row = Integer.parseInt((String) tokenizer.nextElement());
            last_col = Integer.parseInt((String) tokenizer.nextElement());
            total_game_time = Integer.parseInt((String) tokenizer.nextElement());
            player1_time = Integer.parseInt((String) tokenizer.nextElement());
            last_move_time = Integer.parseInt((String) tokenizer.nextElement());
            
            Util.gameHeight = num_row;
            Util.gameWidth = num_col;
        } catch (NumberFormatException e) {
            System.err.println("Invalid input: " + e);
            return null;
        }
        
        byte[][] gameBoard = new byte[num_col + 6][num_row + 6];
        for(byte[] column: gameBoard){
            Arrays.fill(column, Util.gamePiece_s);
        }
        
        for (int x = 0; x < num_col; x++) {
            for (int y = 0; y < num_row; y++) {
                gameBoard[x + 3][y + 3] = Util.pieceMap.get((String) tokenizer.nextElement());
            }
        }

        GameTree gameTree = new GameTree(gameBoard, movesDepth);
        return gameTree;
    }
}
