package model;

public class Packet {
    public String[] board;
    public boolean isDraw;
    public String winner;

    public Packet(String[] board, boolean isDraw, String winner) {
        this.board = board;
        this.isDraw = isDraw;
        this.winner = winner;
    }
}
