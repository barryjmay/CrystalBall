package com.barrymay.crystalball;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.barrymay.crystalball.R;
import com.barrymay.crystalball.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {
	
	public static final String TAG = MainActivity.class.getSimpleName();
	
	//This method has been created as a stand alone method so that a CrystalBall object will be created once every
	//time the program is run. As opposed to creating it every time the button is clicked.
	//This creates a new CrystalBall object (mCrystalBall). 
	private CrystalBall mCrystalBall = new CrystalBall(); //mCrystalBall is a property or member var of the MainActivity object
	private TextView mAnswerLabel;
	private ImageView mCrystalBallImage;
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super refers to parent class i.e. calls OnCreate method of Activity class 
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Log.d(TAG, "We're loggin from the oncreate method");
        
        //Assigning the View variables from the layout file
        mAnswerLabel = (TextView) findViewById(R.id.textView1);
        mCrystalBallImage = (ImageView) findViewById(R.id.imageView1);
        //This references the system service that gives us sensors (SENSOR_SERVICE)
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //The system service that manages accelerometers (TYPE_ACCELEROMETER)
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector(new OnShakeListener() {
			
			@Override
			public void onShake() {
				// TODO Auto-generated method stub
				handleNewAnswer();
			}
		});
        
     
        
        //Toast.makeText(this, "Yay! Our activity was created!", Toast.LENGTH_LONG).show();
        
        //Toast.makeText - this is a static method of the Toast class. LENGTH_LONG is a constant that specifies duration
        
    
    }
    //definitions under @Override override definitions in Activity class
    @Override
    //This method is from the Base Activity class (just like onCreate)
    public void onResume() {
    	super.onResume();
    	 mSensorManager.registerListener(mShakeDetector, mAccelerometer, 
         		SensorManager.SENSOR_DELAY_UI);
    }
    
    @Override
  //This method is also from the Base Activity class 
    public void onPause() {
    	super.onPause();
    	mSensorManager.unregisterListener(mShakeDetector);
    }
    
    private void animateCrystalBall() {
    	
    	mCrystalBallImage.setImageResource(R.drawable.ball_animation);
    	AnimationDrawable ballAnimation = (AnimationDrawable) mCrystalBallImage.getDrawable();
    	
    	if (ballAnimation.isRunning()) {
    		ballAnimation.stop();
    	}
    	ballAnimation.start();
    }
    
    private void animateAnswer() {
    	//Declaring the AlphaAnimation variable fadeInAnimation & use the AlphaAnimation constructor to fade from transparent to opaque 0 -> 1
    	AlphaAnimation fadeInAnimation = new AlphaAnimation(0, 1); 
       	fadeInAnimation.setDuration(1500); //duration in milliseconds
    	fadeInAnimation.setFillAfter(true); //make animation stick after the animation is done 
    	
    	mAnswerLabel.setAnimation(fadeInAnimation); //this attaches the animation to the textView & runs it
    }
    
    private void playSound(){
    	//Declare & instantiate a MediaPlayer var. This time use a static method.
    	MediaPlayer player = MediaPlayer.create(this, R.raw.crystal_ball);
    	player.start();
    	//Listen for when mediaplayer has played the file & then release the file
    	player.setOnCompletionListener(new OnCompletionListener() {
			
    		@Override
			//this gets generated automatically by the onCompletionListener constructer
    		public void onCompletion(MediaPlayer mp) {
    			//manually put in to release so that we don't end up with too many MediaPlayer instances
    			mp.release(); 				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

	private void handleNewAnswer() {
		String answer = mCrystalBall.getAnAnswer();

		//Update the label with our dynamic answer
		mAnswerLabel.setText(answer);	
		
		animateCrystalBall();
		animateAnswer();
		playSound();
	}

}
