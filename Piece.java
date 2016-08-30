package Refusal;

public class Piece {
	
	private String mv;
	private int value;
	private float score;
	private float moveValue;
	private String prevPosition, currPosition;
	private boolean isCaptured;
	private int capturedValue =0;
	private boolean rookSwitched = false;
	
	public Piece() {
		
		setMv("");
		setScore(0);
		isCaptured = false;
	}

	public String getMv() {
		return mv;
	}

	public void setMv(String mv) {
		this.mv = mv;
	}

	public float getScore() {
		return score;
	}

	public void setScore(float f) {
		this.score = f;
	}

	public boolean isCaptured() {
		return isCaptured;
	}

	public void setCaptured(boolean isCaptured, int capturedValue) {
		this.isCaptured = isCaptured;
		this.setCapturedValue(capturedValue);
	}

	public String getPrevPosition() {
		return prevPosition;
	}

	public void setPrevPosition(String prevPosition) {
		this.prevPosition = prevPosition;
	}

	public String getCurrPosition() {
		return currPosition;
	}

	public void setCurrPosition(String currPosition) {
		this.currPosition = currPosition;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getCapturedValue() {
		return capturedValue;
	}

	public void setCapturedValue(int capturedValue) {
		this.capturedValue = capturedValue;
	}

	public boolean isRookSwitched() {
		return rookSwitched;
	}

	public void setRookSwitched(boolean rookSwitched) {
		this.rookSwitched = rookSwitched;
	}

	public float getMoveValue() {
		return moveValue;
	}

	public void setMoveValue(float moveValue) {
		this.moveValue = moveValue;
	}

}
