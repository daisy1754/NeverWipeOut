package jp.gr.java_conf.daisy.stage;

import java.util.List;

import android.graphics.Point;

public abstract class Stage {
	public static int DEFAULT_WIDTH = 480;
	public static int DEFAULT_HEIGHT = 800;
	public static int DEFAULT_START_X = 400;
	public static int DEFAULT_START_Y = 30;
	public static int DEFAULT_START_POINT_WIDTH = 40;
	private final Goal defaultGoal = new Goal(20, 10, 50, 5);
	
	public Point getSize() {
		return new Point(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Point getStartPoint() {
		return new Point(DEFAULT_START_X, DEFAULT_START_Y);
	}
	
	public int getStartPointWidth() {
		return DEFAULT_START_POINT_WIDTH;
	}
	
	/**
	 *  Returning list must consist in ascending order of y, in other words,
	 *  if a slope shown above another slope has smaller index.
	 */
	public abstract List<Slope> getSlopes();
	
	public Goal getGoal() {
		return defaultGoal;
	}
}
