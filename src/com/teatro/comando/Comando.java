package com.teatro.comando;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import android.content.Context;
import android.widget.Toast;

public class Comando implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private byte comandoid;
	private byte numero;
	private byte velocidad;
	private byte direccion;
	private int pasos;
	
	public Comando()
	{
		this.comandoid=0;
		this.numero=0;
		this.velocidad=0;
		this.direccion=0;
		this.pasos=0;
		
	}
	
	public Comando(byte comandoid,char nmotor,char velocidad, char direccion, int pasos) {
		
		this.comandoid = (byte)comandoid;
		this.velocidad = (byte)velocidad;
		this.numero=(byte)nmotor;
		this.direccion = (byte)direccion;
		this.pasos = pasos;
	}


	public byte getComandoid() {
		return comandoid;
	}


	public void setComandoid(char comandoId) {
		this.comandoid =(byte) comandoId;
	}


	public byte getVelocidad() {
		return  velocidad;
	}


	public void setVelocidad(char velocidad) {
		this.velocidad = (byte)velocidad;
	}


	public byte getDireccion() {
		return direccion;
	}


	public void setDireccion(char direccion) {
		this.direccion = (byte) direccion;
	}


	public int getPasos() {
		return (int)pasos;
	}


	public void setPasos(int pasos) {
		
		
		this.pasos = pasos;
	}
	
	public byte getNumero() {
		return numero;
	}


	public void setNumero(char nmotor) {
		this.numero = (byte)nmotor;
	}

	public void enviaComando(OutputStream out) {
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


	
	
	
	

}