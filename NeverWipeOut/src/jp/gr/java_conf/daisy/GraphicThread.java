package jp.gr.java_conf.daisy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class GraphicThread extends Thread{
	final GameManager gameManager;
	final SurfaceHolder surfaceHolder;
	boolean surfaceReady;
	int canvasWidth;
	int canvasHeight;
	
	private static final String MESSAGE_READY_TO_START = "Press to Start";
	
	public GraphicThread(SurfaceHolder surfaceHolder) {
		this.surfaceHolder = surfaceHolder;
		this.gameManager = new GameManager();
	}
	
	public void setSurfaceReady(boolean surfaceReady) {
		this.surfaceReady = surfaceReady;
	}
	
	public void setCanvasSize(int width, int height) {
		canvasWidth = width;
		canvasHeight = height;
	}
	
	public void onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (gameManager.getState() == GameManager.STATE_READY) {
				gameManager.startGame();
			}
			break;
		default:
			break;
		}
	}
	
	@Override
	public void run() {
		while (surfaceReady) {
			 Canvas canvas = null;
             try {
                 canvas = surfaceHolder.lockCanvas(null);
                 if (gameManager.getState() == GameManager.STATE_RUNNING)
                	 gameManager.updateField();
                 doDraw(canvas);
             } finally {
                 if (canvas != null) {
                     surfaceHolder.unlockCanvasAndPost(canvas);
                 }
             }
		}
	}
	
	private void doDraw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		switch (gameManager.getState()) {
		case GameManager.STATE_READY:
			showReadyText(canvas);
		case GameManager.STATE_RUNNING:
		    gameManager.doDraw(canvas, canvasWidth, canvasHeight);
		    break;
		}
	}
	
	private void showReadyText(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        paint.setTextSize(80);
        int margin = 20;
        while (paint.measureText(MESSAGE_READY_TO_START) + margin > canvasWidth) {
        	paint.setTextSize(paint.getTextSize() - 5);
        }
        
        canvas.drawText(MESSAGE_READY_TO_START,
        		(canvasWidth - paint.measureText(MESSAGE_READY_TO_START)) / 2,
        		(canvasHeight) / 2, paint);
	}
}
