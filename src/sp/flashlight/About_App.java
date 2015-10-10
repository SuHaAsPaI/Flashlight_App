package sp.flashlight;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class About_App extends Activity  {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

}