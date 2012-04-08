package jp.gr.java_conf.daisy.stage;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

public class Ball {
	private int x;
	private int y;
	private float velocityX;
	private float velocityY;
	private boolean onSlope;
	public static final int RADIUS = 10;
	
	public Ball() {
		this(0, 0);
	}
	
	public Ball(int x, int y) {
		this.x = x;
		this.y = y;
		this.velocityX = 0;
		this.velocityY = 0;
	}
	
	public void moveTo(int newX, int newY) {
		x = newX;
		y = newY;
	}
	
	public void moveTo(Point point) {
		moveTo(point.x, point.y);
	}
	
	public void setVelocity(float velocityX, float velocityY) {
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}
	
	public float getVelocityX() {
		return velocityX;
	}
	
	public float getVelocityY() {
		return velocityY;
	}
	
	public void setOnSlope(boolean onSlope) {
		this.onSlope = onSlope;
	}
	
	public boolean onSlope() {
		return onSlope;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void draw(Canvas canvas, Paint paint, float ratio) {
		canvas.drawCircle(x / ratio, y / ratio, RADIUS / ratio, paint);
	}
}
