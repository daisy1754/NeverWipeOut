package jp.gr.java_conf.daisy;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class GraphicView extends SurfaceView implements SurfaceHolder.Callback {
	GraphicThread thread;
	
	public GraphicView(Context context) {
		super(context);
		getHolder().addCallback(this);
		thread = new GraphicThread(getHolder());
	}

	 @Override
     public boolean onTouchEvent(MotionEvent event) {
         thread.onTouchEvent(event);
         return true;
     }
	
	@Override
	public void surfaceChanged(
			SurfaceHolder surfaceHolder, int format, int width, int height) {
		Log.d("sze", width + " " + height);
		thread.setCanvasSize(width, height);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder surfaceHolder) {
		thread.setSurfaceReady(true);
		thread.start();	
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		boolean retry = true;
        thread.setSurfaceReady(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
	}
}
