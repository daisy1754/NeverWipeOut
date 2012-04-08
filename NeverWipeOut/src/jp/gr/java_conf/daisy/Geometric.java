package jp.gr.java_conf.daisy;

import android.graphics.Point;

public class Geometric {
	/**
	 * @return true if two line segments 'start1-end1' and 'start2-end2' intersect.
	 */
	static public boolean intersect(
			Point start1, Point end1, Point start2, Point end2) {
		// http://www5d.biglobe.ne.jp/~tomoya03/shtml/algorithm/Intersection.htm
		int start2isLeft = (start1.x - end1.x) * (start2.y - start1.y) 
				+ (start1.y - end1.y) * (start1.x - start2.x);
		int end2isLeft = (start1.x - end1.x) * (end2.y - start1.y) 
				+ (start1.y - end1.y) * (start1.x - end2.x);
		if (start2isLeft * end2isLeft < 0) {
			int start1isLeft = (start2.x - end2.x) * (start1.y - start2.y)
					+ (start2.y - end2.y) * (start2.x - start1.x);
			int end1isLeft = ((start2.x - end2.x) * (end1.y - start2.y)
					+ (start2.y - end2.y) * (start2.x - end1.x));
			return start1isLeft * end1isLeft < 0;
		}
		return false;
	}
	
	/**
	 * Suppose P is the intersect point of 'start1-end1' and 'start2-end2'.
	 * Return value specifieds ratio 'start1-P' / 'start1-end1'
	 * Note that this method can return negative value.
	 */
	static public float getIntersectRatio(
			Point start1, Point end1, Point start2, Point end2) {
		return ((start2.y - end2.y) * (end2.x - end1.x)
				- (start2.x - end2.x) * (end2.y - end1.y))
				/ (float) ((start2.y - end2.y) * (start1.x - end1.x)
						- (start2.x - end2.x) * (start1.y - end1.y));
	}
}
