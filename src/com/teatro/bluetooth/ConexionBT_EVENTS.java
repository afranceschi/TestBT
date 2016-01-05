package com.teatro.bluetooth;

public interface ConexionBT_EVENTS {
	
	//EVENTOS BLUETOOTH
	public static final int BT_ON = 1;
	public static final int BT_OFF = 2;
		
	public void BT_EVENT(int event, boolean respuesta);
}
