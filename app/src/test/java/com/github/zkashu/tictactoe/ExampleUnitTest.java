package com.github.zkashu.tictactoe;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
	
	
//	@Test
//	public void addition_isCorrect () {
//		boolean expected = true;
//		TicTacToe ticTacToe = new TicTacToe('X','O');
//		ticTacToe.board[0] = "X";
//		assertEquals(ticTacToe.isSpotFree(0), false);
//	}
	
	@Test
	public void isBoardFull () {
		TicTacToe ticTacToe = new TicTacToe('X','O');
		for (int i = 0; i < ticTacToe.board.length; i++) {
			ticTacToe.board[i] = "X";
		}
		ticTacToe.board[1] = "1";
		System.out.println(Arrays.toString(ticTacToe.board));
		assertTrue(ticTacToe.isBoardFull());
	}
	

	
}