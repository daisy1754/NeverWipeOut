package jp.gr.java_conf.daisy;

import java.util.ArrayList;
import java.util.List;

import jp.gr.java_conf.daisy.stage.Slope;
import jp.gr.java_conf.daisy.stage.Stage;

public class DefaultStage extends Stage {
	private final List<Slope> slopes;
	
	public DefaultStage() {
		slopes = new ArrayList<Slope>();
		Slope slope;
		slope = new Slope(
				new int[]{100, 150, 200},
				new int[]{100, 110, 100}, 10);
		slopes.add(slope);
		slope = new Slope(
				new int[]{150, 250, 300},
				new int[]{200, 200, 190}, 10);
		slopes.add(slope);
	}
	
	@Override
	public List<Slope> getSlopes() {
		return slopes;
	}
}
