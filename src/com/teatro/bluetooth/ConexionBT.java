package com.teatro.bluetooth;

import java.io.IOException;
import java.io.OutputStream;

import com.teatro.utilidades.MetodosPantalla;
import com.teatro.utilidades.PantallaEspera;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

public class ConexionBT{
	
	private Context CONTEXT;
	private BluetoothAdapter BTADAPTER;
	private ConexionBT_EVENTS EVENTOS;
	
	
	////
	private static BluetoothSocket BTSOCKET;
	
	
	public ConexionBT(Context context){
		CONTEXT = context;
		BTADAPTER = BluetoothAdapter.getDefaultAdapter();
		
		IntentFilter filtro = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
		filtro.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
	 
	    CONTEXT.registerReceiver(new BluetoothListener(), filtro);
	    
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
		new PantallaEspera(CONTEXT,"Bluetooth","Desactivando Bluetooth",new MetodosPantalla() {
			
			@Override
			public void Run() {
				BTADAPTER.disable();
				while(BTADAPTER.isEnabled());
			}
			
			@Override
			public void Post() {
				Toast.makeText(CONTEXT, "BLUETOOTH DESACTIVADO", Toast.LENGTH_LONG).show();
				EVENTOS.BT_EVENT(ConexionBT_EVENTS.BT_ON,!BTADAPTER.isEnabled());
			}
		}).Start();
	}
	
	public void enviarBuffer(byte[] buffer)
	{
		OutputStream out;
		try {
			out = BTSOCKET.getOutputStream();
			out.write(buffer);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public boolean isEnabled(){
		return BTADAPTER.isEnabled();
	}
	
	private class BluetoothListener extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			String Accion = intent.getAction();
			int estado = 0;
			
			if(Accion == BluetoothAdapter.ACTION_STATE_CHANGED){
				estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR); 
				
				switch(estado){
				
				case BluetoothAdapter.STATE_ON:
					Toast.makeText(context, "Activado",Toast.LENGTH_LONG).show();
					
					break;
				case BluetoothAdapter.STATE_OFF:
					Toast.makeText(context, "Desactivado",Toast.LENGTH_LONG).show();
				}
			}else if(Accion == BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED){
				estado = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.ERROR);
				
				switch(estado){
					case BluetoothAdapter.STATE_CONNECTED:
						
						break;
					case BluetoothAdapter.STATE_CONNECTING:
						
						break;
					case BluetoothAdapter.STATE_DISCONNECTED:
						
						break;
					case BluetoothAdapter.STATE_DISCONNECTING:
						
				}
			}
		}
	}
	
}
