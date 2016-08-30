package Refusal;

import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board {
	private int [][] board;
	private int rows = 9, col = 6;
	private CopyOnWriteArrayList<Piece> compValidMoves;
	private CopyOnWriteArrayList<Piece> humanValidMoves;
		
	public Board() {
		// Build starting board
		board = new int[rows][col];
		compValidMoves = new CopyOnWriteArrayList<Piece>();
		humanValidMoves = new CopyOnWriteArrayList<Piece>();
		
		for (int i = 1; i < rows; i++){
			for (int j = 1; j < col; j++) {
				board[i][j] = 0;
			}
		}
		
		//Set board pieces
		board[1][1] = -3;
		board[1][2] = -5;
		board[1][5] = -4;
		board[3][1] = -2;
		board[3][2] = -1;
		board[3][3] = -1;
		board[3][4] = -1;
		board[3][5] = -2;
		board[6][1] = 2;
		board[6][2] = 1;
		board[6][3] = 1;
		board[6][4] = 1;
		board[6][5] = 2;
		board[8][1] = 3;
		board[8][4] = 5;
		board[8][5] = 4;
	}
	
	public boolean checkValidMove(Piece move, int turn) {
		boolean isValid = false;
		if (turn == 1) {// 1 is designated as the human players turn
			for (int i = 0; i < humanValidMoves.size(); i++) {
				Piece validMove = humanValidMoves.get(i);
//				if (i == 0) System.out.print("The human valid moves are: ");
//				System.out.print(validMove.getMv().substring(0,4) + ", ");
				if (validMove.getMv().substring(0, 4).equals(move.getMv().substring(0, 4).toUpperCase())) isValid = true;
			}
		}
		else {
			for (int i = 0; i < compValidMoves.size(); i++) {
				Piece validMove = compValidMoves.get(i);
//				if (i == 0) System.out.print("The computer valid moves are: ");
//				System.out.println(validMove.getMv().substring(0,4) + "' ");
				if (validMove.getMv().substring(0, 4).equals(move.getMv().substring(0, 4).toUpperCase())) isValid = true;
			}
		}
		
		return isValid;
	}

	public void generateMove(int turn) {		
		Piece piece;
		piece =new Piece();
		String a = "", b = "", c = "", d= "";
		if (turn == 1) humanValidMoves.clear(); // Empty lists
		if (turn == 2) compValidMoves.clear();
		
		if (turn == 1) { // Human moves
			for (int i = 1; i  < rows; i++){				
				for (int j = 1; j < col; j++) {
					if (board[i][j] == -1) { // Looking at pawns
						if (i+1 < rows && board[i+1][j-1] > 0) {							
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+1 + 48));
							d = Character.toString((char)(j-1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i+1][j-1]);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
						if (i+1 < rows && board[i+1][j] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+1 + 48));
							d = Character.toString((char)(j + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
						if (i+1 < rows && j+1 < col && board[i+1][j+1] > 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+1 + 48));
							d = Character.toString((char)(j+1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i+1][j+1]);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
					}
				}
			}
		}	
		
		if (turn == 2) { ///Computer moves
			for (int i = rows-1; i  > 0; i--){				
				for (int j = 1; j < col; j++) {
					if (board[i][j] == 1) { // Looking at pawns
						if (i-1 > 0 && j-1 > 0 && board[i-1][j-1] < 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-1 + 48));
							d = Character.toString((char)(j-1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i-1][j-1]);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
						if (i-1 > 0 && board[i-1][j] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-1 + 48));
							d = Character.toString((char)(j + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
						if (i-1 > 0 && j+1 < col && board[i-1][j+1] < 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-1 + 48));
							d = Character.toString((char)(j+1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i-1][j+1]);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
					}
				}
			}
		}
		
		if (turn == 1) { // Human moves
			for (int i = 0; i  < rows; i++){				
				for (int j = 1; j < col; j++) {
					if (board[i][j] == -2) { // Looking at knights
						if (i+1 < rows && j-2 >= 1 && board[i+1][j-2] > 0) {							
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+1 + 48));
							d = Character.toString((char)(j-2 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i+1][j-2]);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
						if (i+1 < rows && j-2 >= 1 && board[i+1][j-2] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+1 + 48));
							d = Character.toString((char)(j-2 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
						if (i+2 < rows && j-1 >= 1 && board[i+2][j-1] > 0) {							
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+2 + 48));
							d = Character.toString((char)(j-1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i+2][j-1]);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
						if (i+2 < rows && j-1 >= 1 && board[i+2][j-1] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+2 + 48));
							d = Character.toString((char)(j-1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
						if (i+2 < rows && j+1 <= 5 && board[i+2][j+1] > 0) {							
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+2 + 48));
							d = Character.toString((char)(j+1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i+2][j+1]);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
						if (i+2 < rows && j+1 <= 5 && board[i+2][j+1] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+2 + 48));
							d = Character.toString((char)(j+1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
						if (i+1 < rows && j+2 <= 5 && board[i+1][j+2] > 0) {							
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+1 + 48));
							d = Character.toString((char)(j+2 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i+1][j+2]);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
						if (i+1 < rows && j+2 <= 5 && board[i+1][j+2] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i+1 + 48));
							d = Character.toString((char)(j+2 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							humanValidMoves.add(piece);
						}
					}
				}
			}
		}	
		
		if (turn == 2) { ///Computer moves
			for (int i = rows-1; i  > 0; i--){				
				for (int j = 1; j < col; j++) {
					if (board[i][j] == 2) { // Looking at knights
						if (i-1 >= 1 && j-2 >= 1 && board[i-1][j-2] < 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-1 + 48));
							d = Character.toString((char)(j-2 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i-1][j-2]);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
						if (i-1 >= 1 && j-2 >= 1 && board[i-1][j-2] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-1 + 48));
							d = Character.toString((char)(j-2 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
						if (i-2 >= 1 && j-1 >= 1 && board[i-2][j-1] < 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-2 + 48));
							d = Character.toString((char)(j-1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i-2][j-1]);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
						if (i-2 >= 1 && j-1 >= 1 && board[i-2][j-1] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-2 + 48));
							d = Character.toString((char)(j-1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
						if (i-1 >= 1 && j+2 <= 5 && board[i-1][j+2] < 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-1 + 48));
							d = Character.toString((char)(j+2 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i-1][j+2]);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
						if (i-1 >= 1 && j+2 <= 5 && board[i-1][j+2] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-1 + 48));
							d = Character.toString((char)(j+2 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
						if (i-2 >= 1 && j+1 <= 5 && board[i-2][j+1] < 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-2 + 48));
							d = Character.toString((char)(j+1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is a capture
							piece.setCaptured(true, board[i-2][j+1]);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
						if (i-2 >= 1 && j+1 <= 5 && board[i-2][j+1] == 0) {
							a = Character.toString((char)(i + 48));
							b = Character.toString((char)(j + 64));
							c = Character.toString((char)(i-2 + 48));
							d = Character.toString((char)(j+1 + 64));
							piece =new Piece();
							piece.setMv(b + a + d + c); //This is not a capture
							piece.setCaptured(false, 0);
							piece.setValue(board[i][j]);
							compValidMoves.add(piece);
						}
					}
				}
			}
		}
		
		boolean isEnd = false;
		if (turn == 1) { // Human moves
			for (int i = 1; i  < rows; i++){				
				for (int j = 1; j < col; j++) {
					if (board[i][j] == -3) { // Looking at right rook
						for (int k = i+1; k < rows; k++) {
							if (board[k][j] > 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(k + 48));
								d = Character.toString((char)(j + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[k][j]);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
								isEnd = true;
							}
							else if (board[k][j] == 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(k + 48));
								d = Character.toString((char)(j + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is not a capture
								piece.setCaptured(false, 0);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
							}
							else if (board[k][j] < 0 && !isEnd) isEnd = true;
						}
						isEnd = false;
						for (int k = j+1; k < col; k++) {
							if (board[i][k] > 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[i][k]);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
								isEnd = true; // Can go no further in this direction
							}
							else if (board[i][k] == 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is not a capture
								piece.setCaptured(false, 0);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
							}
							else if (board[i][k] < 0 && !isEnd) isEnd = true;
						}
						isEnd = false;
					}
				}
			}
		}	
		
		if (turn == 2) { ///Computer moves
			for (int i = rows-1; i  > 0; i--){				
				for (int j = 1; j < col; j++) {
					if (board[i][j] == 3) { // Looking at Right Rook
						for (int k = i-1; k > 0; k--) {
							if (board[k][j] < 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(k + 48));
								d = Character.toString((char)(j + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[k][j]);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
								isEnd = true; // Can go no further in this direction
							}
							else if (board[k][j] == 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(k + 48));
								d = Character.toString((char)(j + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is not a capture
								piece.setCaptured(false, 0);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
							}
							else if (board[k][j] > 0 && !isEnd) isEnd = true;
						}
						isEnd = false;
						for (int k = j+1; k < col; k++) {
							if (board[i][k] < 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[i][k]);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
								isEnd = true; // Can go no further in this direction
							}
							else if (board[i][k] == 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is not a capture
								piece.setCaptured(false, 0);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
							}
							else if (board[i][k] > 0 && !isEnd) isEnd = true;
						}
						isEnd = false;
					}
				}
			}
		}
		
		if (turn == 1) { // Human moves
			for (int i = 0; i  < rows; i++){				
				for (int j = 1; j < col; j++) {
					if (board[i][j] == -4) { // Looking at Left rook
						for (int k = i+1; k < rows; k++) {
							if (board[k][j] > 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(k + 48));
								d = Character.toString((char)(j + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[k][j]);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
								isEnd = true; // Can go no further in this direction
							}
							else if (board[k][j] == 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(k + 48));
								d = Character.toString((char)(j + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is not a capture
								piece.setCaptured(false, 0);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
							}
							else if (board[k][j] < 0 && !isEnd) isEnd = true;
						}
						isEnd = false;
						for (int k = j-1; k > 0; k--) {
							if (board[i][k] > 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[i][k]);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
								isEnd = true;
							}
							else if (board[i][k] == 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is not a capture
								piece.setCaptured(false, 0);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
							}
							else if (board[i][k] < 0 && !isEnd) isEnd = true;
						}
						isEnd = false;
					}
				}
			}
		}	
		
		if (turn == 2) { ///Computer moves
			for (int i = rows-1; i  > 0; i--){				
				for (int j = 1; j < col; j++) {
					if (board[i][j] == 4) { //Looking at Left Rook
						for (int k = i-1; k > 0; k--) {
							if (board[k][j] < 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(k + 48));
								d = Character.toString((char)(j + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[k][j]);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
								isEnd = true; // Can go no further in this direction
							}
							else if (board[k][j] == 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(k + 48));
								d = Character.toString((char)(j + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is not a capture
								piece.setCaptured(false, 0);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
							}
							else if (board[k][j] > 0 && !isEnd) isEnd = true;
						}
						isEnd = false;
						for (int k = j-1; k > 0; k--) {
							if (board[i][k] < 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[i][k]);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
								isEnd = true; // Can go no further in this direction
							}
							else if (board[i][k] == 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is not a capture
								piece.setCaptured(false, 0);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
							}
							else if (board[i][k] > 0 && !isEnd) isEnd = true;							
						}
						isEnd = false;
					}
				}
			}
		}
		
		if (turn == 1) { // Human moves
			for (int i = 1; i < 2; i++) {
				for (int j = 1; j < col; j++) {
					if (board[i][j] == -5) { // Looking at King
						for (int k = j-1; k > 0; k-- ) {
							if (board[i][k] > 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[i][k]);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
								isEnd = true;
							}
							else if (board[i][k] < 0 && !isEnd) {
								isEnd = true;
							}
						}
						isEnd = false;
						for (int k = j+1; k < col; k++ ) {
							if (board[i][k] > 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[i][k]);
								piece.setValue(board[i][j]);
								humanValidMoves.add(piece);
								isEnd = true;
							}
							else if (board[i][k] < 0 && !isEnd) {
								isEnd = true;
							}
						}
						isEnd = false;
					}
				}
			}
		}
		
		if (turn == 2) { // Computer moves
			for (int i = 8; i < rows; i++) {
				for (int j = 1; j < col; j++) {
					if (board[i][j] == 5) { // Looking at King
						for (int k = j-1; k > 0; k-- ) {
							if (board[i][k] < 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[i][k]);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
								isEnd = true;
							}
							else if (board[i][k] > 0 && !isEnd) {
								isEnd = true;
							}
						}
						isEnd = false;
						for (int k = j+1; k < col; k++ ) {
							if (board[i][k] < 0 && !isEnd) {
								a = Character.toString((char)(i + 48));
								b = Character.toString((char)(j + 64));
								c = Character.toString((char)(i + 48));
								d = Character.toString((char)(k + 64));
								piece =new Piece();
								piece.setMv(b + a + d + c); //This is a capture
								piece.setCaptured(true, board[i][k]);
								piece.setValue(board[i][j]);
								compValidMoves.add(piece);
								isEnd = true;
							}
							else if (board[i][k] > 0 && !isEnd) {
								isEnd = true;
							}
						}
						isEnd = false;
					}
				}
			}
		}
	}
	
	public int getPiece(int row, int col) {
		return board[row][col];
	}	
	
	public CopyOnWriteArrayList<Piece> getCompMoves() {
		return compValidMoves;
	}
	
	public CopyOnWriteArrayList<Piece> getHumanMoves() {
		return humanValidMoves;
	}
	
	public Piece getCompRandomMove() {
		Random rand = new Random();
		Piece next = compValidMoves.get(rand.nextInt(compValidMoves.size()));
		return next;
	}
	
	public Piece getHumanRandomMove() {
		Random rand = new Random();
		Piece next = humanValidMoves.get(rand.nextInt(humanValidMoves.size()));
		return next;
	}
	
	public String invertMove(String move) {
		String reverseMove = new String();
		
		// Build the inverted string
		if (move.substring(0, 1).toUpperCase().equals("A")) reverseMove = "E"; 
		if (move.substring(0, 1).toUpperCase().equals("B")) reverseMove = "D"; 
		if (move.substring(0, 1).toUpperCase().equals("C")) reverseMove = "C"; 
		if (move.substring(0, 1).toUpperCase().equals("D")) reverseMove = "B"; 
		if (move.substring(0, 1).toUpperCase().equals("E")) reverseMove = "A"; 
		
		if (move.substring(1, 2).equals("1")) reverseMove += "8";
		if (move.substring(1, 2).equals("2")) reverseMove += "7";
		if (move.substring(1, 2).equals("3")) reverseMove += "6";
		if (move.substring(1, 2).equals("4")) reverseMove += "5";
		if (move.substring(1, 2).equals("5")) reverseMove += "4";
		if (move.substring(1, 2).equals("6")) reverseMove += "3";
		if (move.substring(1, 2).equals("7")) reverseMove += "2";
		if (move.substring(1, 2).equals("8")) reverseMove += "1";
		
		if (move.substring(2, 3).toUpperCase().equals("A")) reverseMove += "E"; 
		if (move.substring(2, 3).toUpperCase().equals("B")) reverseMove += "D"; 
		if (move.substring(2, 3).toUpperCase().equals("C")) reverseMove += "C"; 
		if (move.substring(2, 3).toUpperCase().equals("D")) reverseMove += "B"; 
		if (move.substring(2, 3).toUpperCase().equals("E")) reverseMove += "A";
		
		if (move.substring(3, 4).equals("1")) reverseMove += "8";
		if (move.substring(3, 4).equals("2")) reverseMove += "7";
		if (move.substring(3, 4).equals("3")) reverseMove += "6";
		if (move.substring(3, 4).equals("4")) reverseMove += "5";
		if (move.substring(3, 4).equals("5")) reverseMove += "4";
		if (move.substring(3, 4).equals("6")) reverseMove += "3";
		if (move.substring(3, 4).equals("7")) reverseMove += "2";
		if (move.substring(3, 4).equals("8")) reverseMove += "1";
		
		return reverseMove;
	}
	
	// Takes the row and column of the board and sets the value in that space
	public void setBoard(int row, int col, int piece) {
		board[row][col] = piece;
	}
	
	// Prints out a Refusal Game Board
	public String toString() {
		for (int i = rows-1; i > 0; i--){
			System.out.print(i + " -->");
			for (int j = 1; j < col; j++) {
				if (board[i][j] < 0)
					System.out.print("| " + board[i][j] + " ");
				else
					System.out.print("|  " + board[i][j] + " ");
			}
			System.out.println(" |"); 
			System.out.println("--------------------------------"); 
		}
		
		System.out.println("        A    B    C    D    E");
		return null; 
	}
}
