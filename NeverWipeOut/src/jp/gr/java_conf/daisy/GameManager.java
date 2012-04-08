package jp.gr.java_conf.daisy;

import java.util.List;

import jp.gr.java_conf.daisy.stage.Ball;
import jp.gr.java_conf.daisy.stage.Slope;
import jp.gr.java_conf.daisy.stage.Stage;
import jp.gr.java_conf.daisy.util.Geometric;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class GameManager {
	private long lastTime;
	private Ball ball;
	private Stage stage;
	private int currentSlopeNo;
	public static final float CONSTANT_GRAVITY = 0.015f;
	
	int state;
	public static final int STATE_READY = 0;
	public static final int STATE_RUNNING = 1;
	public static final int STATE_FAIL = 2;
	public static final int STATE_SUCCESS = 3;
	
	public GameManager() {
		stage = new DefaultStage();
		// instantiate, but out of frame
		ball = new Ball(-100, -100);
		state = STATE_READY;
	}
	
	public void startGame() {
		lastTime = System.currentTimeMillis();
		ball.setVelocity(0, 0);
		ball.moveTo(stage.getStartPoint());
		currentSlopeNo = 0;
		state = STATE_RUNNING;
	}
	
	public void updateField() {
		long timeForMove = System.currentTimeMillis() - lastTime;
		lastTime += timeForMove;
		
		double deviceTilt = NeverWipeOutActivity.getdeviceTilt() * Math.PI / 180.0;
		
		if (!ball.onSlope()) {
			int timeToCollide = dropToCollide(deviceTilt, (int) timeForMove);
			if (timeToCollide < 0) {
				// check if game is not over.
				if (ball.getY() > stage.getSize().y) {
					state = STATE_FAIL;
				}
				return;
			}
		} 
	}
	
	public void doDraw(Canvas canvas, int canvasWidth, int canvasHeight) {
		float widthRatio = canvasWidth / (float) stage.getSize().x;
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
		ball.draw(canvas, paint, widthRatio);
		
		// For sake of simple implementation, draw all slopes regardless of the
		// slope in the window or not.
		for (Slope slope : stage.getSlopes()) {
			slope.draw(canvas, paint, widthRatio);
		}
		
	}
	
	/**
	 * drop ball until it touch to slope or ground.
	 * 
	 * @return time to collide the slope in millisecond. If the ball cannot
	 * reach the any slope within given timeForMove, returns -1.
	 */
	private int dropToCollide(double deviceTilt, int timeForMove) {
		float velocityX = ball.getVelocityX() 
				- (float) (Math.sin(deviceTilt) * timeForMove * CONSTANT_GRAVITY);
		float velocityY = ball.getVelocityY() 
				+ (float) (Math.cos(deviceTilt) * timeForMove * CONSTANT_GRAVITY);
		int mostMovedX = ball.getX() + (int)velocityX;
		int mostMovedY = ball.getY() + (int)velocityY;
		double extendedXForRadius = Ball.RADIUS * Math.sqrt(velocityX * velocityX 
				/ (velocityX * velocityX + velocityY * velocityY));
		double extendedYForRadius = Ball.RADIUS * Math.sqrt(velocityY * velocityY 
				/ (velocityX * velocityX + velocityY * velocityY));
		Point ballPosition = new Point(ball.getX(), ball.getY());
		Point farthestPosition = new Point(
				(int) (mostMovedX + extendedXForRadius),
				(int) (mostMovedY + extendedYForRadius));
		
		int smallerX = ball.getX() < mostMovedX ? ball.getX() : mostMovedX;
		int biggerX = ball.getX() >= mostMovedX ? ball.getX() : mostMovedX;
		
		// ball may collide with last collided slope or next one.
		for (int slopeIndex = currentSlopeNo; slopeIndex <= currentSlopeNo + 1; slopeIndex++) {
			Slope slope = stage.getSlopes().get(slopeIndex);
			List<Point> collisionCandidatesPoint
				= slope.getPointsContainXRange(smallerX, biggerX);
			for (int i = 0; i < collisionCandidatesPoint.size() - 1; i++) {
				if (Geometric.intersect(ballPosition, farthestPosition,
						collisionCandidatesPoint.get(i),
						collisionCandidatesPoint.get(i + 1))) {
					float intersectRatio 
						= Geometric.getIntersectRatio(ballPosition,
							farthestPosition,
							collisionCandidatesPoint.get(i),
							collisionCandidatesPoint.get(i + 1));
					int x = (int) ((velocityX + extendedXForRadius) * intersectRatio - extendedXForRadius);
					int y = (int) ((velocityY + extendedYForRadius) * intersectRatio - extendedYForRadius);
					ball.moveTo(x, y);
					currentSlopeNo = slopeIndex;
					return (int) (intersectRatio * timeForMove);
				}
			}
		}
		
		ball.setVelocity(velocityX, velocityY);
		ball.moveTo(mostMovedX,  mostMovedY);
		return -1;
	}
	
	public int getState() {
		return state;
	}
}
