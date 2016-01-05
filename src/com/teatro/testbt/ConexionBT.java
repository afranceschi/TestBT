package com.teatro.testbt;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.WindowDecorActionBar.TabImpl;
import android.widget.Toast;

public class ConexionBT extends Thread{

	private Context CONTEXT;
	private BluetoothAdapter BTADAPTER;
	
	public ConexionBT(Context Act){
		CONTEXT = Act;
		BTADAPTER = BluetoothAdapter.getDefaultAdapter();
		
		if(BTADAPTER != null){
			this.OnBluetooth();
		}
	}
	
	public void OnBluetooth(){
		new PantallaEspera(CONTEXT,"Bluetooth","Activando Bluetooth",new MetodosPantalla() {
			
			@Override
			public void Run() {
				BTADAPTER.enable();
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void Post() {
				Toast.makeText(CONTEXT, "BLUETOOTH ACTIVADO", Toast.LENGTH_LONG).show();
			}
		}).Start();
	}
	
	public void OffBluetooth(){
		BTADAPTER.disable();
	}
	
	public boolean isEnabled(){
		return BTADAPTER.isEnabled();
	}
	
}
