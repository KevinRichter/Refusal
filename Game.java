package Refusal;

import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game {
	private Board board;
	private int turn;
	private Piece move, compBest, compSecondBest,
				  humanBestCounterMove, humanSecondBestCounterMove,
				  compPreviousRefusedBest;

//	private Piece humanMove = new Piece();
	private int maxDepth = 1;
	private int counter = 0, maxCounter = 500000;
	private boolean refused = false;
	private boolean humanRightRookSwitched = false, humanLeftRookSwitched = false,
					compRightRookSwitched = false, compLeftRookSwitched = false;
	Scanner scan;
	
	public Game() {
		board = new Board();
		move = new Piece();
		compBest = new Piece();		
		compSecondBest = new Piece();
		humanBestCounterMove = new Piece();
		humanBestCounterMove.setMv("A3C4");
		humanSecondBestCounterMove = new Piece();
		compPreviousRefusedBest = new Piece();
		scan= new Scanner(System.in);
		System.out.println("Call me Eleven. While the dial on other chess programs\n" +
							"only go up to 10, I go all the way to 11!\n"); 
		System.out.println("Who do you want to play first? You(1) or 'Eleven'(2).");
		turn = scan.nextInt();
		scan.nextLine();
		board.toString();
		do {			
			getMove();
			board.toString();
		}while (!checkGameOver(0));		
	}
			
	private float evaluate(Piece move, CopyOnWriteArrayList<Piece> opponentMoves) {
		// Calculate the remaining values of pieces still on board
		int comp = 0, player = 0, pieceValue = 0, capValue = 0;
		for (int i = 1; i < 9; i++) {
			for (int j = 1; j < 6; j++) {
				if (board.getPiece(i, j) < 0) {
					player += board.getPiece(i, j);
				}
				else if (board.getPiece(i, j) > 0) {
					comp += board.getPiece(i, j);
				}
			}
		}	
		if (move.getValue() > 0) pieceValue = comp; // Added to computer return value
		else if (move.getValue()<0) pieceValue = player; // Added to player return value
		
		comp = 0; player = 0;
		// Control of the center part of board. The 4 x 3 area in center of board.
		// Based on number of pieces and not their associated values.
		for (int i = 3; i < 7; i++) {
			for (int j = 2; j < 5; j++) {
				if (board.getPiece(i, j) < 0) {
					player++;
				}
				else if (board.getPiece(i, j) > 0) {
					comp++;
				}
			}
		}
		
		// Checks to see if the piece being looked at could be captured if it DOESN'T move this turn
		// Improves score, thus encouraging the piece to move
		for (int i = 0; i < opponentMoves.size(); i++) { 
			if(opponentMoves.get(i).getMv().substring(2, 4).toUpperCase().equals(move.getMv().substring(0, 2).toUpperCase())){
				if (move.getValue() > 0) {	// Check to see if a computer piece was captured by human				
					capValue = -3 * move.getValue(); // + helps the computer when piece by avoiding captured
					break;
				}
				else if (move.getValue() < 0) { // Check to see if a human piece was captured by computer
					capValue = -3 * move.getValue(); // - helps the player when piece by avoiding captured
					break;
				}
			}
		}
		
		if (move.getValue() > 0) return (float) ((.8*pieceValue) + (.4*comp) + (-move.getCapturedValue() * 4) + (capValue));
		else return (float) ((.8*pieceValue) + (.4*(-player)) + (-move.getCapturedValue() * 4) + (capValue));
	}

	private void getMove() {
		if (turn == 1) {
			System.out.println("Make your move.");
			String myMove = scan.nextLine();
			String tempMove = "";
			move.setMv(myMove);
			board.generateMove(turn);
			if (board.checkValidMove(move, turn))	{
				while ((move.getMv().toUpperCase().equals(humanBestCounterMove.getMv().toUpperCase()) ||
					   move.getMv().toUpperCase().equals(humanSecondBestCounterMove.getMv().toUpperCase()))
					   && !refused){
					System.out.println("Eleven has refused your first move. Try again.");
					refused = true;
					tempMove = move.getMv();
					myMove = scan.nextLine();
					move.setMv(myMove);
					while (!board.checkValidMove(move, turn) || tempMove.toUpperCase().equals(move.getMv().toUpperCase())) {
						System.out.println("Not a Valid Move!");
						System.out.println("Make your move.");
						myMove = scan.nextLine();
						move.setMv(myMove);
					}
				}
				refused = false;
				move.setValue(board.getPiece(getPosRow(myMove), getPosCol(myMove)));
				makeFinalMove(move);
				turn = 2;
			}else {
				System.out.println("Not a Valid Move!");
			}			
		}
		else {
			Piece tempBest = new Piece();
			System.out.println("\nEleven is thinking...\n");
			miniMax(board);
			if (compBest.getMv().toUpperCase().equals(compPreviousRefusedBest.getMv().toUpperCase())){
				tempBest = compBest;
				compSecondBest = compPreviousRefusedBest;
				compBest = tempBest;
			}
			System.out.println("Eleven wants to make the move " + compBest.getMv()  + "(" + board.invertMove(compBest.getMv())+"). Do you wish to refuse this move? (y/n)" );
			String response = scan.nextLine();
			if (response.equals("n")) {				
				makeFinalMove(compBest);				
				System.out.println("\nEleven made the move: " + compBest.getMv() + "        (" + board.invertMove(compBest.getMv())+")\n");			
			}
			else {
				if (compBest.getMv().equals(compSecondBest.getMv())) {// Sometimes both moves end up being the same
					compSecondBest = board.getCompRandomMove(); 
					makeFinalMove(compSecondBest);
				}
				makeFinalMove(compSecondBest);
				compPreviousRefusedBest = compBest;
				System.out.println("\nEleven made the move: " + compSecondBest.getMv() + "        (" + board.invertMove(compSecondBest.getMv())+")\n");
			}
			
			turn = 1;
		}
	}
	
	private int getPosRow(String move) {
		int j = move.charAt(1);
		j -= 48;
		return j;
	}
	
	private int getPosCol(String move) {
		int i = move.toUpperCase().charAt(0);
		i -= 64;
		return i;
	}

	private void makeFinalMove(Piece move2) {
		char i, k;
		int ii, kk, j, l;
		i = move2.getMv().toUpperCase().charAt(0);
		j = move2.getMv().charAt(1);
		k = move2.getMv().toUpperCase().charAt(2);
		l = move2.getMv().charAt(3);
		j -= 48;
		l -= 48;
		ii = i - 64;
		kk = k - 64;
		
		// Checking to see if Rook needs to be switched make final change to switched flag
		// on Piece	
		if (move2.getValue() == -3 && l == 8 && !humanRightRookSwitched && !move2.isRookSwitched()) {
			board.setBoard(8,kk,-4);
			humanRightRookSwitched = true;
			move2.setValue(-4);
			move2.setRookSwitched(true);
		}
		else if (move2.getValue() == -4 && l == 8 && !humanLeftRookSwitched && !move2.isRookSwitched()) {
			board.setBoard(8,kk,-3);
			humanLeftRookSwitched = true;
			move2.setValue(-3);
			move2.setRookSwitched(true);
		}
		else if (move2.getValue() == 3 && l == 1 && !compRightRookSwitched && !move2.isRookSwitched()) {
			board.setBoard(1,kk,4);
			compRightRookSwitched = true;
			move2.setValue(4);
			move2.setRookSwitched(true);
		}
		else if (move2.getValue() == 4 && l == 1 && !compLeftRookSwitched && !move2.isRookSwitched()) {
			board.setBoard(1,kk,3);
			compLeftRookSwitched = true;
			move2.setValue(3);
			move2.setRookSwitched(true);
		}		
		else {
			board.setBoard(l, kk, board.getPiece(j, ii));			
		}
		board.setBoard(j, ii, 0);	
	}

	private void makeMove(Piece move2) {
		char i, k;
		int ii, kk, j, l;
		i = move2.getMv().toUpperCase().charAt(0);
		j = move2.getMv().charAt(1);
		k = move2.getMv().toUpperCase().charAt(2);
		l = move2.getMv().charAt(3);
		j -= 48;
		l -= 48;
		ii = i - 64;
		kk = k - 64;
		
		board.setBoard(l, kk, board.getPiece(j, ii));
		board.setBoard(j, ii, 0);
	}
	
	public float max(int depth, float alpha, float beta, Piece move, CopyOnWriteArrayList<Piece> opponentMoves) {
		if (check4winner(depth) != 0) return (check4winner(depth));
		else if (depth == maxDepth || counter> maxCounter) {return evaluate(move, opponentMoves);}
		else {
			int newDepth = depth +1;
			
			board.generateMove(2);
			CopyOnWriteArrayList<Piece> compMoves = board.getCompMoves();
			for (Piece m : compMoves) {
				counter++;
				makeMove(m);
				m.setScore(min(newDepth, alpha, beta, m, compMoves));
				retractMove(m);
				if (m.getScore() >=alpha) alpha = m.getScore();
				if (alpha >= beta) break;				
			}
			return alpha;
		}
	}
	
	// Start of the minimax algorithm
	public void miniMax(Board board) {
		compBest.setScore(-9999);
		compSecondBest.setScore(-9999);
		humanBestCounterMove.setScore(9999);
		humanSecondBestCounterMove.setScore(9999);
		Piece temp = new Piece();
		int depth = 1;
		
		board.generateMove(2);
		CopyOnWriteArrayList<Piece> compMoves = board.getCompMoves();		
		
		for (Piece m : compMoves) {				
			counter++;
			maxDepth++;
			makeMove(m);
			m.setScore(minInit(depth,-9999,9999, m, compMoves));
			retractMove(m);
			if (m.getScore() > compSecondBest.getScore()) {
				compSecondBest = m;
			}
			if (compSecondBest.getScore() > compBest.getScore()) {
				temp = compBest;
				compBest = compSecondBest;
				compSecondBest = temp;
			}
		}
		System.out.println("A total of " + counter + " nodes were visited.");		
		counter = 0;
		maxDepth = 1;
	}
	
	private float min(int depth, float alpha, float beta, Piece move, CopyOnWriteArrayList<Piece> opponentMoves) {
		if (check4winner(depth) != 0) return (check4winner(depth));
		else if (depth == maxDepth || counter> maxCounter) {return evaluate(move, opponentMoves);}
		else {
			int newDepth = depth +1;
						
			board.generateMove(1);
			CopyOnWriteArrayList<Piece> humanMoves = board.getHumanMoves();
			for (Piece m : humanMoves) {
				counter++;
				makeMove(m);
				m.setScore(max(newDepth, alpha, beta, m, humanMoves));				
				retractMove(m);
				if (m.getScore() <= beta) beta = m.getScore();
				if (alpha >= beta) break;				
			}
			return beta;
		}
	}	
	
	// Initial Min method. Saves the best and second best player counter move.
	private float minInit(int depth, float alpha, float beta, Piece move, CopyOnWriteArrayList<Piece> opponentMoves) {
		if (check4winner(depth) != 0) return (check4winner(depth));
		else if (depth == maxDepth || counter> maxCounter) {return evaluate(move, opponentMoves);}
		else {
			Piece temp = new Piece();
			int newDepth = depth +1;
						
			board.generateMove(1);
			CopyOnWriteArrayList<Piece> humanMoves = board.getHumanMoves();
			for (Piece m : humanMoves) {
				counter++;
				makeMove(m);
				m.setScore(max(newDepth, alpha, beta, m, humanMoves));
				retractMove(m);
				if (m.getScore() < humanSecondBestCounterMove.getScore()) {
					humanSecondBestCounterMove = m;
				}
				if (humanSecondBestCounterMove.getScore() < humanBestCounterMove.getScore()){
					temp = humanBestCounterMove;
					humanBestCounterMove = humanSecondBestCounterMove;
					humanSecondBestCounterMove = temp;
				}
				
				if (m.getScore() <= beta) beta = m.getScore();
				if (alpha >= beta) break;				
			}			
			return beta;
		}
	}

	private void retractMove(Piece piece) {
		char i, k;
		int ii, kk, j, l;
		i = piece.getMv().toUpperCase().charAt(0);
		j = piece.getMv().charAt(1);
		k = piece.getMv().toUpperCase().charAt(2);
		l = piece.getMv().charAt(3);
		j -= 48; // Row originated from
		l -= 48; // Row moved to
		ii = i - 64; // Column originated from
		kk = k - 64; // Column moved to
		

		board.setBoard(j, ii, piece.getValue());		
		board.setBoard(l, kk, piece.getCapturedValue()); // reset spot to previous value
		piece.setCapturedValue(0);
	}

	private int check4winner (int depth) {
		boolean isFound = false;
		// Checks to see if the computer's King was removed
		for (int i = 1; i < 6; i++) {
			if (board.getPiece(8,i) == 5) {
				isFound = true;
			}
		}
		if (!isFound) return (-999 + depth); // human wins
		else isFound = false; // reset to check if computer wins
		
		// Checks to see if the human's King was removed
		for (int i = 1; i < 6; i++) {
			if (board.getPiece(1,i) == -5) {
				isFound = true;
			}
		}
		if (!isFound) return (999 - depth); // Computer wins
		
		board.generateMove(1); // See how many human moves are remaining
		if (board.getHumanMoves().size() == 0) return (-999 + depth); // Human wins
		board.generateMove(1); // See how many human moves are remaining
		if (board.getHumanMoves().size() == 0) return (999 - depth); // Computer wins
		
		return 0; // No winner found yet
	}

	private boolean checkGameOver(int depth) {
		boolean gameOver = true;
		if (check4winner(depth) == -999) {
			System.out.println("You WIN!!");
			return gameOver;
		}
		if (check4winner(depth) == 999) {
			System.out.println("You've LOST!");
			return gameOver;
		}
		return false;
	}
}
