package com.github.zkashu.tictactoe;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TicTacToe {
	
	char playerToken;
	char computerToken;
	char playerOneToken;
	char playerTwoToken;
	char[] board = new char[]{'-', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	
	public TicTacToe (char playerToken, char computerToken) {
		this.playerToken = playerToken;
		this.computerToken = computerToken;
	}
	
	public void resetBoard () {
		board = new char[]{'-', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	}
	
	public void insertToken (int position, char token) {
		//inserting a token into the board
		board[position] = token;
	}
	
	public boolean isSpotFree (int position) {
		// if the spot matches any number from 0 to 9, then it's free.
		return Character.isDigit(board[position]);
	}
	
	public boolean isBoardFull () {
		// if the board has any numbers, then it's not full.
		Pattern numPattern = Pattern.compile("[0-9]");
		Matcher matcher = numPattern.matcher(Arrays.toString(board));
		
		return !matcher.find();
	}
	
	public boolean checkWin (char[] board, char token) {

//		System.out.println("Board " + Arrays.toString(board));
//
//		System.out.println("Board Token " + Arrays.toString(board));
		
		return (board[1] == token && board[2] == token && board[3] == token) ||  // Top Row
				       (board[4] == token && board[5] == token && board[6] == token) ||  // Middle Row
				       (board[7] == token && board[8] == token && board[9] == token) ||  // Bottom Row
				
				       //columns
				       (board[1] == token && board[4] == token && board[7] == token) ||  // First Column
				       (board[2] == token && board[5] == token && board[8] == token) ||  // Second Column
				       (board[3] == token && board[6] == token && board[9] == token) || // Third Column
				
				       //diagonals
				       (board[1] == token && board[5] == token && board[9] == token) || // Left To Right (Diagonal)
				       (board[3] == token && board[5] == token && board[7] == token);
		
	}
	
	public int computerMove () {
		
		List<Character> possibleMoves = new ArrayList<>();
		
		for (char c : board) {
			if (Character.isDigit(c)) {
				Log.d("POSSIBLE MOVE", Character.toString(c));
				possibleMoves.add(c);
			}
		}
		
		for (char token : new char[]{computerToken, playerToken}) {
			
			for (Character move : possibleMoves) {
				char[] boardCopy = board.clone();

//				Log.d("Board Origin", String.valueOf(board));
//				Log.d("Board Copy", String.valueOf(boardCopy));
				
				int position = Character.getNumericValue(move);

//				Log.d("Board Position", String.valueOf(position));
				
				boardCopy[Character.getNumericValue(move)] = token;
				
				if (checkWin(boardCopy, token)) {
					return position;
				}
				
			}
			
		}
		
		// Checking if any corner is free.
		
		List<Character> freeCorners = new ArrayList<>();
		
		possibleMoves.forEach(move -> {
			Log.d("BOARD MOVE FREE CORNER", move.toString());
			if (move == '1' || move == '3' || move == '7' || move == '9') {
				freeCorners.add(move);
			}
		});
		
		if (!freeCorners.isEmpty()) {
			return getRandomSpot(freeCorners);
		}
		
		// Checking if the center is free.
		
		if (possibleMoves.contains('5')) {
			return 5;
		}
		
		// Checking if there are any free edges, and picking one.
		
		List<Character> freeEdges = new ArrayList<>();
		
		possibleMoves.forEach(move -> {
			if (move == '2' || move == '4' || move == '6' || move == '8') {
				freeEdges.add(move);
			}
		});
		
		if (!freeEdges.isEmpty()) {
			return getRandomSpot(freeEdges);
		}
		
		return 0;
	}
	
	private int getRandomSpot (List<Character> movesList) {
		int spot = new Random().nextInt(movesList.size());
		return Character.getNumericValue(movesList.get(spot));
	}
}
