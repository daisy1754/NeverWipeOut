package jp.gr.java_conf.daisy.stage;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

public class Slope {
	private final int[] pointXs;
	private final int[] pointYs;
	private final int breads;
	private final int lowestY;
	
	/**
	* size of pointXs and pointYs must be same. 
	* If they are different, 
	* this methods use pointXs length as both lengths.
	*/
	public Slope(int[] pointXs, int[] pointYs, int breads) {
		this.pointXs = new int[pointXs.length];
		this.pointYs = new int[pointXs.length];
		System.arraycopy(pointXs, 0, this.pointXs, 0, pointXs.length);
		System.arraycopy(pointYs, 0, this.pointYs, 0, pointXs.length);
		this.breads = breads;
		int lowestY = Integer.MAX_VALUE;
		for (int i = 0; i < pointYs.length; i++) {
			if (lowestY > pointYs[i])
				lowestY = pointYs[i];
		}
		this.lowestY = lowestY;
	}
	
	public void draw(Canvas canvas, Paint paint, float ratio) {
		Path path = new Path();
		int startX = pointXs[0];
		int startY = pointYs[0];
		path.moveTo(startX / ratio, startY / ratio);
		for (int i = 1; i < pointXs.length; i++) {
			path.lineTo(pointXs[i] / ratio, pointYs[i] / ratio);
		}
		int endX = pointXs[pointXs.length - 1];
		int endY = pointYs[pointYs.length - 1];
		path.lineTo(endX / ratio, ((startY + endY) / 2 + breads) / ratio);
		path.lineTo(startX / ratio, ((startY + endY) / 2 + breads) / ratio);
		canvas.drawPath(path, paint);
	}
	
	/**
	 * @return list of points whose X value that satisfy: minimuX is less than
	 * startX, maximumX is more than endX, and other Xs  are between startX and
	 * endX.
	 */
	public List<Point> getPointsContainXRange(int startX, int endX) {
		List<Point> points = new ArrayList<Point>();
		int startIndex = 0;
		int endIndex = pointXs.length;
		for (int i = pointXs.length; i >= 0; i--) {
			if (pointXs[i] < startX) {
				startIndex = i;
				break;
			}
		}
		for (int i = 0; i < pointXs.length; i++) {
			if (endX < pointXs[i]) {
				endIndex = i;
				break;
			}
		}
		for (int i = startIndex; i <= endIndex; i++) {
			points.add(new Point(pointXs[i], pointYs[i]));
		}
		return points;
	}
}
