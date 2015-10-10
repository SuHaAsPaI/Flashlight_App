package sp.flashlight;


import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
 
		private Camera camera;
		private Button bon,boff,bflickon,bflickoff;
		private TextView tview;
		private MediaPlayer mysound1;
		Thread t=new Thread("hello");
		 int progress=0;
		 private Boolean stat=true;
		 int k=1;
		 int lk=0;
		 Parameters p ;
		
		private SeekBar Sget;
	 
		 Context context; 
		 PackageManager pm;
		 
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			 setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	 
			bon = (Button) findViewById(R.id.on);
			boff = (Button) findViewById(R.id.off);
			bflickon = (Button) findViewById(R.id.flick);
			bflickoff = (Button) findViewById(R.id.button1);
			Sget = (SeekBar)findViewById(R.id.seekBar1);
			tview=(TextView)findViewById(R.id.textView1);
			Sget.setMax(1000);
			mysound1=MediaPlayer.create(MainActivity.this, R.raw.click );
		
			context = this;
			pm = context.getPackageManager();
	 
			// if device support camera?
			if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
				Log.e("err", "Device has no camera!");
				Toast.makeText(this, "Device has no camera.", Toast.LENGTH_LONG).show();
				return;
			}
	 
			camera = Camera.open();
			p = camera.getParameters();
	 
			bon.setOnClickListener(new View.OnClickListener() {
				
				
				@Override
				public void onClick(View arg0) {
					
					mysound1.start();
					Log.i("info", "torch is turn on!");
					p.setFlashMode(Parameters.FLASH_MODE_TORCH);
					camera.setParameters(p);
					
				}
			});
			boff.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					
					
					
					mysound1.start();
					Log.i("info", "torch is turn off!");
					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(p);
					
				}
			});
			
			
			bflickoff.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					mysound1.start();
					Log.i("info", "torch is flicker off!");
					stat=false;
					p.setFlashMode(Parameters.FLASH_MODE_OFF);
					camera.setParameters(p);
					if( t.isAlive()==true){
				    	   try{
				    		   t.destroy();
				    		   
				    		  // p.setFlashMode(Parameters.FLASH_MODE_OFF);
				    		   }catch(UnsupportedOperationException e){
				    			   e.printStackTrace();
				    		   
				    	   }
				    	   catch(NullPointerException e){
			    			   e.printStackTrace();
			    		   
			    	   }
				       }
					
					k=3;
				}
			});
			
			bflickon.setOnClickListener(new View.OnClickListener() {
				
		
				@Override
				public void onClick(View v) {
					mysound1.start();
					stat=true;
					Log.i("info", "torch is flicker on!");
					k=1;
					t=new Thread(){
						
						public void run(){
							
							
							try{
								while(stat){
								
								p.setFlashMode(Parameters.FLASH_MODE_TORCH);
								camera.setParameters(p);
								sleep(progress+50);
								p.setFlashMode(Parameters.FLASH_MODE_OFF);
								camera.setParameters(p);
								sleep(progress+50);
								}
							}catch(Exception e){
								e.printStackTrace();	
							}
							}
							
						
					};
					
					t.start();
					
				}
					
					
						
			
			});
			

			Sget.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
				
				
				
					           
			public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
				progress = progresValue;
					 tview.setText("Flicker Rate(ms)=" +(progress +50));
				}
					         
			    @Override
				 public void onStartTrackingTouch(SeekBar seekBar) {
				}
					         
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {
				 }
					
			});	
			
			
  }
		@SuppressWarnings("deprecation")
		public void onBackPressed(){
			super.onBackPressed();
			p.setFlashMode(Parameters.FLASH_MODE_OFF);
			camera.setParameters(p);
			stat=false;
			try{
			t.destroy();
			finish();
			camera.release(); 
		
			}catch(UnsupportedOperationException e){
				e.printStackTrace();
			}finally{
				camera.release(); 
			}
			
		}

	
		
		@Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.main, menu);
	 
	        return super.onCreateOptionsMenu(menu);
	    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.about_app:
        	Intent sread1=new Intent("sp.flashlight.ABOUT_APP");
    		startActivity(sread1);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }

    
}
