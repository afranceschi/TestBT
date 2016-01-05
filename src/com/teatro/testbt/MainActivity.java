package com.teatro.testbt;

import android.support.v7.app.ActionBarActivity;

import com.teatro.bluetooth.ConexionBT;
import com.teatro.bluetooth.ConexionBT_EVENTS;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
	
	public void AccionBTN(View view){
		final ConexionBT BT = new ConexionBT(this);
		BT.SetBluetoothEvent(new ConexionBT_EVENTS() {
			
			@Override
			public void BT_EVENT(int event) {
				// TODO Auto-generated method stub
				if(event == ConexionBT_EVENTS.BT_ON){
					if(BT.isEnabled()){
						Toast.makeText(getApplicationContext(), "SI", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
					}
			
				}
			}
		});
		
		
		BT.OnBluetooth();
	}
}
