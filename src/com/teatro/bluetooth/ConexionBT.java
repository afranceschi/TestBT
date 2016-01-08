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
	private String DEVNAME="TEATRO";
	//private String DEVNAME;
	private static final UUID my_uuid=UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
	private IntentFilter filtro;
	
	public ConexionBT(Context context){
		CONTEXT = context;
		BTADAPTER = BluetoothAdapter.getDefaultAdapter();
		
		filtro = new IntentFilter();
		
		filtro.addAction(android.bluetooth.BluetoothAdapter.ACTION_STATE_CHANGED);
		filtro.addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED);
		filtro.addAction(android.bluetooth.BluetoothDevice.ACTION_ACL_DISCONNECTED);
	 
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
				//Toast.makeText(CONTEXT, "BLUETOOTH ACTIVADO", Toast.LENGTH_LONG).show();
				EVENTOS.BT_EVENT(ConexionBT_EVENTS.BT_ON,BTADAPTER.isEnabled());
			}
		}).Start();
	
	}
	
	public void ObtenerDsipositivo(){
		
		new PantallaEspera(CONTEXT,"Bluetooth","Obteniendo dispositivo Bluetooth",new MetodosPantalla() {
			
			@Override
			public void Run() {
				BTDEVICE = null;
				Set <BluetoothDevice> AL;
				
				AL = BTADAPTER.getBondedDevices();
				
				if(AL.size() > 0){
					for(BluetoothDevice device : AL){
						if(device.getName().equals(DEVNAME)) BTDEVICE = device;
					}
				}
				
				while(BTDEVICE == null);
				
				
				
			}
			
			@Override
			public void Post() {
				//Toast.makeText(CONTEXT, "DISPOSITIVO BLUETOOTH ENCONTRADO", Toast.LENGTH_LONG).show();
				//EVENTOS.BT_EVENT(ConexionBT_EVENTS.BT_ON,BTADAPTER.isEnabled());
			}
		}).Start();
				
	}
	
	
	@SuppressLint("NewApi")
	public void ConectarDispositivo() {
		
new PantallaEspera(CONTEXT,"Bluetooth","Estableciendo conexion con el dispositivo Bluetooth",new MetodosPantalla() {
			
			@Override
			public void Run() {
				try {
					
					BTSOCKET= BTDEVICE.createRfcommSocketToServiceRecord(my_uuid);					
					BTSOCKET.connect();
					
				//	BTSOCKET.IS
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(!BTSOCKET.isConnected());
			}
			
			@Override
			public void Post() {
				//Toast.makeText(CONTEXT, "DISPOSITIVO BLUETOOTH CONECTADO", Toast.LENGTH_LONG).show();
				//EVENTOS.BT_EVENT(ConexionBT_EVENTS.BT_ON,BTADAPTER.isEnabled());
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

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			
			String Accion = intent.getAction();
			Toast.makeText(context, Accion,Toast.LENGTH_LONG).show();
			
			BluetoothDevice ddd = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			
			if(Accion.equals(android.bluetooth.BluetoothDevice.ACTION_ACL_CONNECTED)){
				Toast.makeText(context, ddd.getName(),Toast.LENGTH_LONG).show();
			}
			int estado = 0;
			
			if(Accion == BluetoothAdapter.ACTION_STATE_CHANGED){
				estado = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR); 
				
				switch(estado){
				
				case BluetoothAdapter.STATE_ON:
					Toast.makeText(context, "Activado",Toast.LENGTH_LONG).show();
					//Connect();
					break;
				case BluetoothAdapter.STATE_OFF:
					Toast.makeText(context, "Desactivado",Toast.LENGTH_LONG).show();
				}
			}else if(Accion == BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED){
				estado = intent.getIntExtra(BluetoothAdapter.EXTRA_CONNECTION_STATE, BluetoothAdapter.ERROR);
				Toast.makeText(context, "CAZO",Toast.LENGTH_LONG).show();
				switch(estado){
					case BluetoothAdapter.STATE_CONNECTED:
						Toast.makeText(context, "CAZO CONNECTED",Toast.LENGTH_LONG).show();
						
						break;
					case BluetoothAdapter.STATE_CONNECTING:
						Toast.makeText(context, "CAZO CONNECTING",Toast.LENGTH_LONG).show();
						break;
					case BluetoothAdapter.STATE_DISCONNECTED:
						Toast.makeText(context, "CAZO DISCONNECTED",Toast.LENGTH_LONG).show();
						if(!isEnabled())
						{
							OnBluetooth();
						}
						ObtenerDsipositivo();
						ConectarDispositivo();
						
						break;
					case BluetoothAdapter.STATE_DISCONNECTING:
						Toast.makeText(context, "CAZO DISCONNECTING",Toast.LENGTH_LONG).show();
						
				}
			}
		}

		
	}
	
}
