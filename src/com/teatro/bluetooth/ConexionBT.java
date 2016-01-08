package com.teatro.bluetooth;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import com.teatro.utilidades.MetodosPantalla;
import com.teatro.utilidades.PantallaEspera;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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
	private BluetoothDevice BTDEVICE;
	private static final String DEVNAME="TEATRO";
	//private String DEVNAME;
	private static final UUID my_uuid=UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
	private IntentFilter filtro;
	private BluetoothListener BluetoothReceiver;
	
	public ConexionBT(Context context){
		CONTEXT = context;
		BTADAPTER = BluetoothAdapter.getDefaultAdapter();
		
		filtro = new IntentFilter();
		
		filtro.addAction(android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED);
		filtro.addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED);
		filtro.addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECTED);
	 
		BluetoothReceiver = new BluetoothListener();
		
		CONTEXT.registerReceiver(BluetoothReceiver, filtro);
		//
	    
	}
	
	public void onListener(){
		Toast.makeText(CONTEXT, "Activar Listener", Toast.LENGTH_SHORT).show();
		BluetoothReceiver.enable();
	}
	
	public void offListener(){
		Toast.makeText(CONTEXT, "Desactivar Listener", Toast.LENGTH_SHORT).show();
		BluetoothReceiver.disable();
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
				//Toast.makeText(CONTEXT, "BLUETOOTH ACTIVADO", Toast.LENGTH_LONG).show();
				//onListener();
				EVENTOS.BT_EVENT(ConexionBT_EVENTS.BT_ON,BTADAPTER.isEnabled());
			}

			@Override
			public void Pre() {
				// TODO Auto-generated method stub
				//offListener();
			}
		}).Start();
	
	}
	
	public boolean ObtenerDsipositivo(){
		
		BTDEVICE = null;
		Set <BluetoothDevice> AL;
				
		AL = BTADAPTER.getBondedDevices();
				
		if(AL.size() > 0){
			for(BluetoothDevice device : AL){
				if(device.getName().equals(DEVNAME)) BTDEVICE = device;
			}
		}
					
		if(BTDEVICE != null) return true; else return false;
	}
	
	
	@SuppressLint("NewApi")
	public void ConectarDispositivo() {
		
		new PantallaEspera(CONTEXT,"Bluetooth","Estableciendo conexion con el dispositivo Bluetooth",new MetodosPantalla() {
			
			@Override
			public void Run() {
				int timeout = 0;
				do{
					if(!BTADAPTER.isEnabled()){
						BTADAPTER.enable();
					}
					try {
					
						BTSOCKET= BTDEVICE.createRfcommSocketToServiceRecord(my_uuid);					
						BTSOCKET.connect();
					
						//	BTSOCKET.IS
					
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					timeout = 0;
					while(!BTSOCKET.isConnected() && timeout <= 30){
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						timeout++;
					}
				}while(!BTSOCKET.isConnected());
			}
			
			@Override
			public void Post() {
				//Toast.makeText(CONTEXT, "DISPOSITIVO BLUETOOTH CONECTADO", Toast.LENGTH_LONG).show();
				//EVENTOS.BT_EVENT(ConexionBT_EVENTS.BT_ON,BTADAPTER.isEnabled());
				onListener();
			}

			@Override
			public void Pre() {
				// TODO Auto-generated method stub
				offListener();
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
				//onListener();
				EVENTOS.BT_EVENT(ConexionBT_EVENTS.BT_ON,!BTADAPTER.isEnabled());
			}

			@Override
			public void Pre() {
				// TODO Auto-generated method stub
				//offListener();
			}
		}).Start();
	}
	
	public void Connect(){
		ObtenerDsipositivo();
		ConectarDispositivo();
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

		
		private boolean FLAG_ENABLE;
		
		public BluetoothListener() {
			// TODO Auto-generated constructor stub
			super();
			FLAG_ENABLE = true;
		}
		
		public void enable(){
			FLAG_ENABLE = true;
		}
		
		public void disable(){
			FLAG_ENABLE = false;
		}
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(FLAG_ENABLE){
				String Accion = intent.getAction();
				int estado = 0;
			
				if(Accion.equals(BluetoothAdapter.ACTION_STATE_CHANGED)){
					estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,BluetoothAdapter.ERROR);
					switch(estado){
						case BluetoothAdapter.STATE_ON:
							Connect();
							break;
						case BluetoothAdapter.STATE_OFF:
							OnBluetooth();
					}
				}
			
				if(Accion.equals(BluetoothDevice.ACTION_ACL_CONNECTED)){
					BluetoothDevice BD = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					if(BD.getName().equals(DEVNAME)){
					
					}
				}else if(Accion.equals(BluetoothDevice.ACTION_ACL_DISCONNECTED)){
					BluetoothDevice BD = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
					if(BD.getName().equals(DEVNAME)){
						if(isEnabled()) Connect();
					}
				}
			}
		}
	}
	
}
