//package AI;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.StringTokenizer;

//import Util.Move;

public class Jarvis {

    private static int num_col;
    private static int num_row;
    private static int last_col;
    private static int total_game_time;
    private static int player1_time ;
    private static int last_move_time;
    
    public static boolean logging = false;
    
    /**
     * Number of moves Jarvis looks into the "future"
     */
    public static int gameDepth = 5;
    public static int numNodes = 0; //counts nodes searched, for funsies
    
    public static int possibleWinWeighting = 100;
    public static int columnWeighting = 3;
    public static int heightWeighting = 6;
    public static double greenWeighting = 0.5;
    
    private static IsWin isWin;
    
    public static void main(String[] args) {
        
        Util util = new Util();      
        String input = Util.getStandardInput();        
        GameTree gameTree = createGameTree(input);
        //int numWins[] = Util.numPossibleWins(gameTree, 6);
        //System.err.println("Blue wins: "+ numWins[0]+" Red wins: "+numWins[1]);
        printBoard(gameTree.gameBoard);
        isWin = new IsWin();           

        long startTime = System.currentTimeMillis();
        Move winningMove;

        System.err.println("Looking at depth: " + (gameDepth));
        
        for(;;){
            winningMove = findOptimalMove(gameTree);
            long taken = System.currentTimeMillis() - startTime;
            if(taken > 3000 || gameTree.pieceCount < 4 || gameDepth > (num_col*num_row - gameTree.pieceCount)){
                break;
            }
            gameDepth++;
            System.err.println("Looking deeper: " + gameDepth);
            numNodes = 0;
        }
        
        System.out.println("(" + (winningMove.column + 1) + "," + Util.getKeyByValue(Util.pieceMap, winningMove.gamePiece) + ")");    
    }

    private static void printBoard(byte[][] gameBoard){
        // prints the game board (will be rotated 90 degress)gameTree
         for (int x = 0; x < num_col; x++) {
             for (int y = 0; y < num_row; y++) {
                 print(Util.getKeyByValue(Util.pieceMap, gameBoard[x + 3][y + 3]));
             }
             println("");
         }
    }
    
    private static void print(Object string) {
        if(logging){
            System.out.print(string.toString());
        }
    }
    
    private static void println(Object string) {
        if(logging){
            System.out.println(string.toString());
        }
    }

    private static Move max(GameTree node, int depth, Move lastMove, Move alpha, Move beta){ 
        numNodes+=2;
        if(lastMove == null){
            lastMove = new Move();
        }else{
            int score = isWin.calculateScore(node.gameBoard, lastMove.column, node.tops[lastMove.column] - 1);
            if(score != 0){
                lastMove.score = score;
                return lastMove;
            }
            if(depth < 1){
                lastMove.score = score;
                return lastMove;
            }
        }
        
        Move val;
        depth = depth - 1;
        
        int column = node.boardWidth / 2 - 1;
        for(int count = 1; count <= node.boardWidth; count++){
        //for(int column = 0; column < node.boardWidth; column++){
            if(node.tops[column] == node.boardHeight) {
                if(count%2 > 0){
                    column += count; 
                }else{
                    column -= count;
                }
                continue;
            }
            
            node.insertPiece(column, Util.gamePiece_b);
            lastMove.gamePiece = Util.gamePiece_b;
            lastMove.column = column;            
            val = min(node, depth, lastMove, alpha.clone(), beta.clone());
            int numWins[] = Util.numPossibleWins(node, column);
            val.score += (double)numWins[0] / possibleWinWeighting;  
            val.score -= (double)numWins[1] / possibleWinWeighting;  
            node.removePiece(column);       
            if(val.score > alpha.score){
                alpha.column = column;
                alpha.gamePiece = Util.gamePiece_b;
                alpha.score = val.score;
            }

            if(alpha.score >= beta.score){
                return alpha;
            }

            node.insertPiece(column, Util.gamePiece_g);
            lastMove.gamePiece = Util.gamePiece_g;
            lastMove.column = column;            
            val = min(node, depth, lastMove, alpha.clone(), beta.clone());
            numWins = Util.numPossibleWins(node, column);
            val.score += (double)numWins[0] / possibleWinWeighting;  
            val.score -= (double)numWins[1] / possibleWinWeighting;  
            //Negative weighting to green pieces
            val.score -= greenWeighting;
            
            node.removePiece(column);        
            if(val.score > alpha.score){
                alpha.column = column;
                alpha.gamePiece = Util.gamePiece_g;
                alpha.score = val.score;
            }

            if(alpha.score >= beta.score){
                return alpha;
            }            
            
            if(count%2 > 0){
                column += count; 
            }else{
                column -= count;
            }
        }
        
        if(alpha.score == Integer.MIN_VALUE){
            alpha.score = 0;
        }
        return alpha;
    }
    
    private static Move min(GameTree node, int depth, Move lastMove, Move alpha, Move beta){ 
        numNodes+=2;
        if(lastMove == null){
            lastMove = new Move();
        }else{
            int score = isWin.calculateScore(node.gameBoard, lastMove.column, node.tops[lastMove.column] - 1);
            if(score != 0){
                lastMove.score = score;
                return lastMove;
            }
            if(depth < 1){
                lastMove.score = score;
                return lastMove;
            }
        }
        
        Move val;
        depth = depth - 1;
        
        int column = node.boardWidth / 2 - 1;
        for(int count = 1; count <= node.boardWidth; count++){
        //for(int column = 0; column < node.boardWidth; column++){
            if(node.tops[column] == node.boardHeight) {
                if(count%2 > 0){
                    column += count; 
                }else{
                    column -= count;
                }
                continue;
            }
            
            node.insertPiece(column, Util.gamePiece_r);
            lastMove.gamePiece = Util.gamePiece_r;
            lastMove.column = column;            
            val = max(node, depth, lastMove, alpha.clone(), beta.clone());
            int numWins[] = Util.numPossibleWins(node, column);
            val.score += (double)numWins[0] / possibleWinWeighting;  
            val.score -= (double)numWins[1] / possibleWinWeighting;  
            node.removePiece(column);            
            if(val.score < beta.score){
                beta.column = column;
                beta.gamePiece = Util.gamePiece_r;
                beta.score = val.score;
            }

            if(alpha.score >= beta.score){
                return beta;
            }

            node.insertPiece(column, Util.gamePiece_g);
            lastMove.gamePiece = Util.gamePiece_g;
            lastMove.column = column;            
            val = max(node, depth, lastMove, alpha.clone(), beta.clone());
            numWins = Util.numPossibleWins(node, column);
            val.score += (double)numWins[0] / possibleWinWeighting;  
            val.score -= (double)numWins[1] / possibleWinWeighting;  
            node.removePiece(column);           
            if(val.score < beta.score){
                beta.column = column;
                beta.gamePiece = Util.gamePiece_g;
                beta.score = val.score;
            }

            if(alpha.score >= beta.score){
                return beta;
            }

            if(count%2 > 0){
                column += count; 
            }else{
                column -= count;
            }
        }

        if(beta.score == Integer.MAX_VALUE){
            beta.score = 0;
        }
        return beta;
    }
    
    /*private static int oponent_minimax(GameTree node, int depth, Move lastMove){
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
            alpha = Math.max(alpha, player_minimax(child, --depth));
        }
        return alpha;
    }*/
    
    private static Move findOptimalMove(GameTree gameTree){
        
        double highscore = Integer.MIN_VALUE;        
        
        Move move = null;// = min(gameTree,gameDepth,null);
        Move bestMove = new Move();        
        //bestMove = max(gameTree, gameDepth, null, new Move(0, Integer.MIN_VALUE, Util.gamePiece_b), new Move(0, Integer.MAX_VALUE, Util.gamePiece_r));
        
        int column = gameTree.boardWidth / 2 - 1;
        for(int count = 1; count <= gameTree.boardWidth; count++){
        //for(int column = 0; column < gameTree.boardWidth; column++){
            numNodes+=2;
            if(gameTree.tops[column] == gameTree.boardHeight) {
                if(count%2 > 0){
                    column += count; 
                }else{
                    column -= count;
                }
                continue;
            }
            
            Move lastMove = new Move();
            lastMove.column = column;
            lastMove.gamePiece = Util.gamePiece_b;
            gameTree.insertPiece(column, Util.gamePiece_b);
            move = min(gameTree, gameDepth, lastMove, new Move(0, Integer.MIN_VALUE, Util.gamePiece_b), new Move(0, Integer.MAX_VALUE, Util.gamePiece_r)); 

            //========================== HEURISTICS ==========================/
            print("Piece: " + Util.gamePiece_b + "  Column: " + (column+1) + "  Unadjusted: " + round(move.score,3) + " ");  

            double score = move.score + 1/(Math.abs((double) column - Util.gameWidth/2) + 1) / columnWeighting;
            print("columnWeight adjusted: " + round(score,3) + " ");
            
            int[] possibleWins = Util.numPossibleWins(gameTree, column);
            score += (double) possibleWins[0] / possibleWinWeighting;   
            score -= (double) possibleWins[1] / possibleWinWeighting;   
            print("possibleWin adjusted: " + round(score,3) + " "); 

            score += 1 / (double) (gameTree.tops[column] + 1) / heightWeighting;
            print("heightWeight adjusted: " + round(score,3) + " ");
            //========================== HEURISTICS ==========================/
            
            gameTree.removePiece(column);            
            println("final score: " + score);   
            
            if(score > highscore){
                bestMove.gamePiece = Util.gamePiece_b;
                bestMove.column = column;
                bestMove.score = move.score;
                highscore = score;  
            }

            lastMove.column = column;
            lastMove.gamePiece = Util.gamePiece_g;
            gameTree.insertPiece(column, Util.gamePiece_g);
            move = min(gameTree, gameDepth, lastMove, new Move(0, Integer.MIN_VALUE, Util.gamePiece_b), new Move(0, Integer.MAX_VALUE, Util.gamePiece_r));     

            //========================== HEURISTICS ==========================/
            print("Piece: " + Util.gamePiece_g + "  Column: " + (column+1) + "  Unadjusted: " + round(move.score,3) + " ");
            
            score = move.score + 1/(Math.abs((double) column - Util.gameWidth/2) + 1) / columnWeighting;
            print("columnWeight adjusted: " + round(score,3) + " ");
            
            possibleWins = Util.numPossibleWins(gameTree, column);
            score += (double) possibleWins[0] / possibleWinWeighting;  
            score -= (double) possibleWins[1] / possibleWinWeighting;   
            print("possibleWin adjusted: " + round(score,3) + " ");
            
            score += 1 / (double) (gameTree.tops[column] + 1) / heightWeighting;
            print("heightWeight adjusted: " + round(score,3) + " ");
            
            //Negative weighting to green pieces
            score -= greenWeighting;
            //========================== HEURISTICS ==========================/
            
            gameTree.removePiece(column);           
            println("final score: " + score);   
            
            if(score > highscore){
                bestMove.gamePiece = Util.gamePiece_g;
                bestMove.column = column;
                bestMove.score = move.score;
                highscore = score;             
            }

            if(count%2 > 0){
                column += count; 
            }else{
                column -= count;
            }
        }
        
        println("Winning move - Piece: " + bestMove.gamePiece + "  Column: " + (bestMove.column+1) + "  Score: " + bestMove.score);    
        println("Number of nodes searched: " + numNodes);
        
        return bestMove;        
    }    
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
    
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
            Arrays.fill(column, Util.gamePiece_e);
        }
        
        GameTree gameTree = new GameTree(num_col, num_row);
        
        for (int x = 0; x < num_col; x++) {
            for (int y = 0; y < num_row; y++) {
                byte piece = Util.pieceMap.get((String) tokenizer.nextElement());
                
                gameTree.insertPiece(x,piece);
                
            }
        }

        return gameTree;
    }
    
    public static class Move implements Cloneable{
        int column = 0;
        byte gamePiece;
        double score = 0;
        public Move(){};
        public Move(GameNode gameNode) {
            column = gameNode.column;
            gamePiece = gameNode.gamePiece;
        }
        public Move(int col, byte gamepiece) {
            column = col;
            gamePiece = gamepiece;
        }
        public Move(int col, double score) {
            column = col;
            this.score = score;
        }
        public Move(int col, double score, byte gamePiece){
            column = col;
            this.score = score;
            this.gamePiece = gamePiece;
        }
        @Override
        protected Move clone() {
            return new Move(this.column, this.score, this.gamePiece);
        }
    }
}
