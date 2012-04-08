package jp.gr.java_conf.daisy.stage;

public class Goal {
	int width;
	int rangeLeft;
	int rangeRight;
	float speed;
	
	public Goal(int width, int rangeLeft, int rangeRight, float speed) {
		this.width = width;
		this.rangeLeft = rangeLeft;
		this.rangeRight = rangeRight;
		this.speed = speed;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getRangeLeft() {
		return rangeLeft;
	}
	
	public int getRangeRight() {
		return rangeRight;
	}
	
	public float getSpeed() {
		return speed;
	}
}
