package com.teatro.comando;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import android.content.Context;
import android.widget.Toast;

public class Comando{
	
	private static final int T_ARRAY = 14;
	
	private byte COMANDO;
	private byte ID;
	private int VELOCIDAD;
	private int PASOS;
	private int TIEMPO;
	
	public Comando()
	{
		this.COMANDO=0;
		this.ID=0;
		this.VELOCIDAD=0;
		this.PASOS=0;
		this.TIEMPO=0;
		
	}

	public byte getComando() {
		return COMANDO;
	}


	public void setComando(byte comando) {
		this.COMANDO = comando;
	}


	public int getVelocidad() {
		return VELOCIDAD;
	}


	public void setVelocidad(byte velociadad) {
		this.VELOCIDAD = velociadad;
	}

	public int getPasos() {
		return PASOS;
	}


	public void setPasos(int pasos) {
		this.PASOS = pasos;
	}
	
	public byte getID() {
		return ID;
	}


	public void setID(byte id) {
		this.ID = id;
	}
	
	public byte[] getByteArray(){
		byte[] r = new byte[T_ARRAY];
		byte[] aux;
		
		r[0] = COMANDO;
		r[1] = ID;
		
		aux = Int_To_ByteArray(VELOCIDAD);
		r[2] = aux[0];
		r[3] = aux[1];
		r[4] = aux[2];
		r[5] = aux[3];
		
		aux = Int_To_ByteArray(PASOS);
		r[6] = aux[0];
		r[7] = aux[1];
		r[8] = aux[2];
		r[9] = aux[3];
		
		aux = Int_To_ByteArray(TIEMPO);
		r[10] = aux[0];
		r[11] = aux[1];
		r[12] = aux[2];
		r[13] = aux[3];
		
		return r;
	}
	//
	private byte[] Int_To_ByteArray(int num){
		
		byte[] r = new byte[4];
		
		r[0] = (byte) (num & 0xff);
		
		r[1] = (byte) ((num & 0xff00) >> 8);
		
		r[2] = (byte) ((num & 0xff0000) >> 16);
		
		r[3] = (byte) ((num & 0xff000000) >> 24);
		
		return r;
	}

	/*public void enviaComando(OutputStream out) {
		// TODO Auto-generated method stub
		
		try {
			
			//dividiendo pasos x byte
			int a;
			int b;
			int c;
			int d;
			
			d = (byte) (pasos & 0xff);
			
			c = (byte) ((pasos & 0xff00) >> 8);
			
			b = (byte) ((pasos & 0xff0000) >> 16);
			
			a = (byte) ((pasos & 0xff000000) >> 24);
		
			
			byte[] buffer;
			buffer = new byte[8];
			
			buffer[0]=comandoid;
			buffer[1]=numero;
			buffer[2]=velocidad;
			buffer[3]=direccion;
			buffer[4]=(byte)a;
			buffer[5]=(byte)b;
			buffer[6]=(byte)c;
			buffer[7]=(byte)d;
			
			out.write(buffer);
			
			
			

			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	*/

}