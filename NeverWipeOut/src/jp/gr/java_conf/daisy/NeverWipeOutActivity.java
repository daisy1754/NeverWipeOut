package jp.gr.java_conf.daisy;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

public class NeverWipeOutActivity extends Activity implements SensorEventListener {
	private SensorManager manager;
	private static float tilt;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		manager = (SensorManager)getSystemService(SENSOR_SERVICE);

		setContentView(new GraphicView(this));
	}

	@Override
	protected void onStop() {
		super.onStop();
		manager.unregisterListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_ORIENTATION);
		if (sensors.size() > 0) {
			Sensor sensor = sensors.get(0);
			manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
		} else {
			Toast.makeText(this, "failed to get orientation sensor", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		tilt = event.values[2];
	}
	
	public static float getdeviceTilt() {
		return tilt;
	}
}