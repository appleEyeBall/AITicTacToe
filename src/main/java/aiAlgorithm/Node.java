package aiAlgorithm;

/**
 * This class is used to store a state of a tic tac toe puzzle in the form of a string as well as a min/max value
 * Methods are included to set the min/max value depending on whose turn it is, X or O
 * @author Mark Hallenbeck
 *
 * Copyright© 2014, Mark Hallenbeck, All Rights Reservered.
 *
 */

public class Node {
	
	private String[] state;
	
	private int minMaxValue;
	
	private int movedTo;
	
	public Node(String[] stateOfPuzzle, int move)
	{
		state = stateOfPuzzle;
		
		movedTo = move;
		
		minMaxValue = -1;
	}
	
	public int getMovedTo()
	{
		return movedTo;
	}
	
	/**
	 * checks for all the ways that O can win and sets minmax to -10. If it is a draw, sets it to 0
	 */
	public void setMinMax_for_O()
	{
		
		if(checkForDraw())
			{
				minMaxValue = 0;
			}
		
		if(state[0].equals("O") && state[1].equals("O") && state[2].equals("O")) //horizontal top
		{
			minMaxValue = -10;
		}
		
		if(state[3].equals("O") && state[4].equals("O") && state[5].equals("O"))//horizontal middle
		{
			minMaxValue = -10;
		}

		if(state[6].equals("O") && state[7].equals("O") && state[8].equals("O"))//horizontal bottom
		{
			minMaxValue = -10;
		}

		if(state[0].equals("O") && state[3].equals("O") && state[6].equals("O"))//vert right
		{
			minMaxValue = -10;
		}

		if(state[1].equals("O") && state[4].equals("O") && state[7].equals("O"))//vert middle
		{
			minMaxValue = -10;
		}

		if(state[2].equals("O") && state[5].equals("O") && state[8].equals("O"))//vert left
		{
			minMaxValue = -10;
		}

		if(state[0].equals("O") && state[4].equals("O") && state[8].equals("O"))// diag from top left
		{
			minMaxValue = -10;
		}

		if(state[2].equals("O") && state[4].equals("O") && state[6].equals("O"))// diag from top right
		{
			minMaxValue = -10;
		}

	}
	
	/**
	 * checks for all the ways that X can win and sets minmax to 10. If a draw, sets minmax to 0
	 */
	public void setMinMax_for_X()
	{
		if(checkForDraw())
		{
			minMaxValue = 0;
		}
		
		if(state[0].equals("X") && state[1].equals("X") && state[2].equals("X")) //horizontal top
		{
			minMaxValue = 10;
		}
		
		if(state[3].equals("X") && state[4].equals("X") && state[5].equals("X"))//horizontal middle
		{
			minMaxValue = 10;
		}

		if(state[6].equals("X") && state[7].equals("X") && state[8].equals("X"))//horizontal bottom
		{
			minMaxValue = 10;
		}

		if(state[0].equals("X") && state[3].equals("X") && state[6].equals("X"))//vert right
		{
			minMaxValue = 10;
		}

		if(state[1].equals("X") && state[4].equals("X") && state[7].equals("X"))//vert middle
		{
			minMaxValue = 10;
		}

		if(state[2].equals("X") && state[5].equals("X") && state[8].equals("X"))//vert left
		{
			minMaxValue = 10;
		}

		if(state[0].equals("X") && state[4].equals("X") && state[8].equals("X"))// diag from top left
		{
			minMaxValue = 10;
		}

		if(state[2].equals("X") && state[4].equals("X") && state[6].equals("X"))// diag from top right
		{
			minMaxValue = 10;
		}

	}

	public void setMinMax(int x)
	{
		minMaxValue = x;
	}
	
	/**
	 * check the state to see if it is a draw (no b's in the string only X and O)
	 * @return true if its a draw, false if not
	 */
	public boolean checkForDraw()
	{
		for(int x = 0; x < state.length; x++)
		{
			if(state[x].equals("b"))
			{
				return false;
			}
		}
		
		return true;
	}
	public int getMinMax()
	{
		return minMaxValue;
	}
	
	public String[] getInitStateString()
	{
		return state;
	}

	/*
	 * Checks the entire board to see if 'O' or 'X' won
	 * return "" if no winner, 'X' if 'X' won and 'O' if 'O' won
	 * */
	public String checkForWin(){
		String winner = "";
		// check cols
		if ((state[0].equals(state[1])) && (state[0].equals(state[2])) && !state[0].equals("b") ){
			System.out.println("WIN -> 012");
			winner = state[0];
		}
		else if ((state[3].equals(state[4])) && (state[3].equals(state[5])) && !state[3].equals("b") ){
			System.out.println("WIN -> 345");
			winner = state[3];
		}
		else if ((state[6].equals(state[7])) && (state[6].equals(state[8]))  && !state[6].equals("b")  ){
			System.out.println("WIN -> 678");
			winner = state[6];
		}

		// check rows
		else if ((state[0].equals(state[3])) && (state[0].equals(state[6]))  && !state[0].equals("b") ){
			System.out.println("WIN -> 036");
			winner = state[0];
		}
		else if ((state[1].equals(state[4])) && (state[1].equals(state[7]))  && !state[1].equals("b")  ){
			System.out.println("WIN -> 147");
			winner = state[1];
		}
		else if ((state[2].equals(state[5])) && (state[2].equals( state[8]))  && !state[2].equals("b")  ){
			System.out.println("WIN -> 258");
			winner = state[2];
		}

		// check diagonal
		else if ((state[0].equals(state[4])) && (state[0].equals(state[8]))  && !state[0].equals("b")   ){
			System.out.println("WIN -> 048");
			winner = state[0];
		}
		else if ((state[2].equals(state[4])) && (state[2].equals(state[6]))  && !state[2].equals("b")   ){
			System.out.println("WIN -> 246");
			winner = state[2];
		}

		return winner;

	}

}
