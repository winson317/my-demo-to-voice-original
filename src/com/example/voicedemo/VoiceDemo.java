package com.example.voicedemo;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class VoiceDemo extends Activity {
	
	private RelativeLayout llPlayerVoice;
	private ImageButton btnPlayerVoice;
	private SeekBar sbPlayerVoice;  //控制音量
	private int maxVolume, currentVolume;
	//音量面板的显示和隐藏动画
	private Animation showVoicePanelAnimation;
	private Animation  hiddenVoicePanelAnimation;
	
	private AudioManager audioManager; //获取系统的音频对象
	//private TextView txtHint;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

     llPlayerVoice = (RelativeLayout)this.findViewById(R.id.llPlayerVoice);
     btnPlayerVoice = (ImageButton) findViewById(R.id.btnPlayerVoice);
     btnPlayerVoice.setOnClickListener(listener);
        
     sbPlayerVoice = (SeekBar)this.findViewById(R.id.sbPlayerVoice);
     sbPlayerVoice.setOnSeekBarChangeListener(seekBarChangeListener);
     //txtHint = (TextView)findViewById(R.id.txtHint);
     //加载动画资源
     showVoicePanelAnimation = AnimationUtils.loadAnimation(VoiceDemo.this, R.anim.push_up_in);
     hiddenVoicePanelAnimation = AnimationUtils.loadAnimation(VoiceDemo.this, R.anim.push_up_out);
     
     audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE); //获取AudioManager实例
     currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC); //获取系统音频当前的大小
     
     sbPlayerVoice.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
     //audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.FLAG_SHOW_UI);
     //removeDialog(AudioManager.FLAG_SHOW_UI);
     //maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
     //sb_player_voice.setMax(maxVolume); //拖动条最大值与系统最大值匹配
     //currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
     sbPlayerVoice.setProgress(currentVolume);
     //txtHint.setText(currentVolume+"/"+audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
     
    }
     
     //音频面板的显示和隐藏
     private void voicePanelAnimation() {
    	 if (llPlayerVoice.getVisibility() == View.GONE)
    	 {
    		 llPlayerVoice.startAnimation(showVoicePanelAnimation);
    		 llPlayerVoice.setVisibility(View.VISIBLE);
    	 }
    	 else {
    		 llPlayerVoice.startAnimation(hiddenVoicePanelAnimation);
    		 llPlayerVoice.setVisibility(View.GONE);
    	 }
     }
     
     private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnPlayerVoice:
				voicePanelAnimation();
				break;

			default:
				break;
			}
		}
	};
        
   private OnSeekBarChangeListener seekBarChangeListener = new OnSeekBarChangeListener() {
	
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		
	}
	
	//当拖动条的滑块位置发生改变时触发该方法
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		// TODO Auto-generated method stub
		
		if (seekBar.getId() == R.id.sbPlayerVoice)
		{
			//int max = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);  
			//Log.d("test audio","volume:"+progress+" max:"+max);
			currentVolume = progress;
			audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0); //设置音量
			//currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
			//sb_player_voice.setProgress(currentVolume);
		}
	}
};

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	switch (keyCode) 
	{
	case KeyEvent.KEYCODE_VOLUME_UP:
		//audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_SHOW_UI);
		//audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		//Toast.makeText(this, "您按下了增大音量按键！", Toast.LENGTH_LONG).show();
		currentVolume += 2;
		if(currentVolume >= audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)){
			currentVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		}
		//sbPlayerVoice.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
		sbPlayerVoice.setProgress(currentVolume);
		//audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0);
		//txtHint.setText(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)+"/"+audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		return true;  //隐藏系统音量
	case KeyEvent.KEYCODE_VOLUME_DOWN:
		//audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
		//audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC), AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
		//Toast.makeText(this, "您按下了降低音量按键！", Toast.LENGTH_LONG).show();
		currentVolume -= 2;
		if(currentVolume < 0) {
			currentVolume = 0;
		}
		//sbPlayerVoice.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
		sbPlayerVoice.setProgress(currentVolume);
		//audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
		//txtHint.setText(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)+"/"+audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
		return true;
	default:
		break;
	}
	return super.onKeyDown(keyCode, event);
}
}
