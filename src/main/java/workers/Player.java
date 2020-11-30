package workers;

import aiAlgorithm.MinMax;
import aiAlgorithm.Node;
import model.Packet;
import java.util.*;
import java.util.concurrent.Callable;
/*
* Invert board is used when it's O's turn, since
*  MinMax works in the perspective of X only.
* Note: sometimes I refer to X as player1. please bear with this naming inconsistency.
* */

public class Player implements Callable<Packet> {
    String[] board;
    boolean isPlayer1;
    String difficulty;

    public Player(String[] board, boolean isPlayer1, String difficulty) {
        this.board = board;
        this.isPlayer1 = isPlayer1;
        this.difficulty = difficulty;
        if (!isPlayer1) {
            invertBoard();
        }
    }


    void invertBoard(){     //Invert the board if it's O's turn (turn all X's to O's and vice versa)
        for (int i=0; i< board.length; i++){
            if (board[i].equals("X")){
                board[i] = "O";
            }
            else if (board[i].equals("O")){
                board[i] = "X";
            }
        }
    }

    @Override
    public Packet call() throws Exception {
        System.out.println("\nplayer is "+isPlayer1);
        MinMax sendIn_InitState = new MinMax(board);
        ArrayList<Node> initMovesList = sendIn_InitState.getStateList();
        ArrayList<Node> movesList = sendIn_InitState.findMoves();
        // isDraw and winner values will be updated
        // accordingly for each turn (Node)
        boolean isDraw = false;
        String winner = "";

        Node winNode = getSpecialNode(initMovesList, 10);
        Node nextBestNode = getSpecialNode(movesList, 10);
        Node drawNode = getSpecialNode(movesList,  0);

        // Expert player plays the position that immediately results in a win
        // (Not all win/max of 10 immediately results in a win. Expert checks first).
        // if none exist, he plays the next best (win/max of 10).
        // if none exist, he playst the draw node
        //if none exist, he chooses the first one
        if (difficulty.equals("Expert")){
            System.out.println("EXPERT IS BEING PLAYED");
            if (winNode!=null){
                board = winNode.getInitStateString();
                isDraw = winNode.checkForDraw();
                winner = winNode.checkForWin();

            }
            else if ( nextBestNode != null){
                board = nextBestNode.getInitStateString();
                isDraw = nextBestNode.checkForDraw();
                winner = nextBestNode.checkForWin();
            }
            else if (drawNode != null){
                board = drawNode.getInitStateString();
                isDraw =drawNode.checkForDraw();
                winner = drawNode.checkForWin();
            }
            else{
//                board = pickRandomWithMinMax(movesList, -1).getInitStateString();
                board = movesList.get(0).getInitStateString();
                isDraw = movesList.get(0).checkForDraw();
                winner = movesList.get(0).checkForWin();
            }

        }


        // Advanced players choose randomly
        else if (difficulty.equals("Advanced")){
            Node randNode = sendIn_InitState.pickRandomSpot();
            board = randNode.getInitStateString();
            isDraw = randNode.checkForDraw();
            winner = randNode.checkForWin();
        }
        else if (difficulty.equals("Novice")){
            Node node = sendIn_InitState.getStateList().get(0);
            board = node.getInitStateString();
            isDraw = node.checkForDraw();
            winner = node.checkForWin();
        }

        // if player is 'O', let's not forget to un-invert the board
        // (It was previously inverted in constructor)
        if (!isPlayer1){
            System.out.println("Inverting board before sending back to GameSceneController");
            if (winner.equals("X")) winner = "O";
            else if (winner.equals("O")) winner = "X";
            invertBoard();
        }

        Packet packet = new Packet(board, isDraw, winner);
        System.out.println("returning packet");
        return packet;
    }


    /*
    * special nodes are win nodes (minMax = 10),
    *  and draw Nodes, (minMax = 0).
    * */
    Node getSpecialNode(ArrayList<Node> movesList, int minMaxToMatch){
        int count = 0;
        ArrayList<Node> winNodes = new ArrayList<>();
        for (Node move: movesList){
            int outcome = move.getMinMax();

            if (outcome == minMaxToMatch){
                winNodes.add(move);
            }
        }

        // get a random node from winNodes
        if (winNodes.size() == 0) return null;
        Random r = new Random();
        int val = r.nextInt(winNodes.size());
        return winNodes.get(val);

    }


}
