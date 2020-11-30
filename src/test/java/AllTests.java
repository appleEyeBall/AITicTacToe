import aiAlgorithm.Node;
import controllers.GameSceneController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AllTests {
    Node node;
    @BeforeEach
    void init() {
        String[] board = "X X X b b O b b O".split(" ");
        node = new Node(board, 0);
    }

    @Test
    void winTest() {
        assertEquals(node.checkForWin(), "X", "wrong Value");
    }

    @Test
    void win_2Test() {
        node = new Node("O X X b O O X b O".split(" "), 0);
        assertEquals(node.checkForWin(), "O", "wrong Value");
    }

    @Test
    void win_3Test() {
        node = new Node("X X X X O O O X O".split(" "), 0);
        assertEquals(node.checkForWin(), "X", "wrong Value");
    }

    @Test
    void win_4Test() {
        node = new Node("X O X X O O O X O".split(" "), 0);
        assertEquals(node.checkForWin(), "", "wrong Value");
    }

    @Test
    void drawTest() {
        node = new Node("X O X X O O O X O".split(" "), 0);
        assertTrue(node.checkForDraw(), "wrong Value");
    }
}
