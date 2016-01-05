package com.teatro.bluetooth;

import java.util.concurrent.ExecutionException;

import com.teatro.utilidades.MetodosPantalla;
import com.teatro.utilidades.PantallaEspera;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.WindowDecorActionBar.TabImpl;
import android.widget.Toast;

public class ConexionBT{
	
	private Context CONTEXT;
	private BluetoothAdapter BTADAPTER;
	private ConexionBT_EVENTS EVENTOS;
	
	public ConexionBT(Context Act){
		CONTEXT = Act;
		BTADAPTER = BluetoothAdapter.getDefaultAdapter();
		
		/*if(BTADAPTER != null){
			this.OnBluetooth();
		}*/
	}
	
	public void SetBluetoothEvent(ConexionBT_EVENTS event){
		EVENTOS = event;
	}
	
	public void OnBluetooth(){
		new PantallaEspera(CONTEXT,"Bluetooth","Activando Bluetooth",new MetodosPantalla() {
			
			@Override
			public void Run() {
				BTADAPTER.enable();
				while(!BTADAPTER.isEnabled());
			}
			
			@Override
			public void Post() {
				Toast.makeText(CONTEXT, "BLUETOOTH ACTIVADO", Toast.LENGTH_LONG).show();
				EVENTOS.BT_EVENT(ConexionBT_EVENTS.BT_ON,BTADAPTER.isEnabled());
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
