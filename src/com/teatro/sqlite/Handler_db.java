package com.teatro.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import android.annotation.SuppressLint;

//import static android.provider.BaseColumns._ID;

import android.content.ContentValues;
import android.content.Context;

public class Handler_db extends SQLiteOpenHelper{
	
	public static final String DB_NAME = "teatro.db";
	public static final String TABLE_1_NAME = "acto";
	public static final String TABLE_2_NAME = "comando";	
	public static final String T1C1 = "_ID";
	public static final String T1C2 = "nombre";	
	public static final String T2C1 = "ID_COMANDO";
	public static final String T2C2 = "codcomando";
	public static final String T2C3 = "numero";
	public static final String T2C4 = "velocidad";
	public static final String T2C5 = "direccion";
	public static final String T2C6 = "pasos";
	public static final String T2C7 = "ID_ACTO";
	
//Script de Creación de las tablas Acto y comandos
    
    public static final String QUERY_ACTO= "CREATE TABLE "+TABLE_1_NAME+"("+T1C1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+T1C2+" TEXT UNIQUE);";
   
    public static final String QUERY_COMANDO= "CREATE TABLE "+TABLE_2_NAME+"("+T2C1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+T2C2+" TEXT,"+T2C3+" TEXT,"+T2C4+" TEXT,"+T2C5+" TEXT,"+T2C6+" TEXT,"+T2C7+" INTEGER,FOREIGN KEY("+T2C7+") REFERENCES "+TABLE_1_NAME+"("+T1C1+"));";
    
    
//Contexto
    public Context contexto;
    	
    public SQLiteDatabase db;
	
	public Handler_db(Context ctx)
	{
		super(ctx,DB_NAME,null,1);
		
		contexto = ctx;
	}
	
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_1_NAME);
		db.execSQL("DROP TABLE IF EXISTS"+TABLE_2_NAME);
		onCreate(db);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(QUERY_ACTO);
		db.execSQL(QUERY_COMANDO);
		
		Toast.makeText(contexto, "CREE UNA BASE DE DATOS NUEVA", Toast.LENGTH_LONG).show();
		
		
	}
	
	public void onOpen(SQLiteDatabase db)
	{
		this.db = db;
		
		Toast.makeText(contexto, "ABRI La base", Toast.LENGTH_LONG).show();
	}
	
	
	public Long creaActo(String nom)//Se envia el nombre de acto y si no existe crea el acto
	{
		ContentValues valores = new ContentValues();
		
		valores.put("nombre", nom);

		Long resu = this.getWritableDatabase().insert(TABLE_1_NAME, null, valores);
		
		return resu;
	}

		
	public boolean cargaComando(int c, String n, String v, String d, String p,int act)//carga comando, el ultimo parametro que se le pasa es el id de acto al que esta vinculado
	{
				
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues valores = new ContentValues();
		
		valores.put(T2C2,c);
		valores.put(T2C3,n);
		valores.put(T2C4,v);
		valores.put(T2C5,d);
		valores.put(T2C6, p);
		valores.put(T2C7,act);
		
		
		Long result = db.insert(TABLE_2_NAME, null,valores);
		
		if(result == -1)
		{
			return false;
		}
		return true;
		
	}
	
	
	public Cursor getComando(String nombreacto)//Devuelve un Cursor con todos los comandos que tiene ese acto
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor resu = db.rawQuery("SELECT * FROM comando JOIN acto on ID_ACTO=_ID WHERE nombre= ?",new String[] {nombreacto});
			
			return resu;//El que lo llame debera validar si la query devolvio algo
		
	}
		
	@SuppressLint("NewApi")
	public int getActoid(String nombreacto)//Se usa para verificar si un nombre de acto no existe
	{
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor resu = db.rawQuery("SELECT * FROM "+TABLE_1_NAME+" WHERE nombre= ?",new String[] {nombreacto});
		
		if(resu.getCount() == 1)
		{
		 resu.moveToFirst();
		 return resu.getInt(0);	
		}
		else return 0;
		
	}
	
	public boolean borrarActo(int actoid)//borra el acto segun el id de acto que se envie, elimina el acto de la taba acto y todos los comandos ligados al acto
	{
		SQLiteDatabase db = this.getWritableDatabase();
		
		
		int resu = db.delete(TABLE_2_NAME,T2C7+"=?",new String[] {Integer.toString(actoid)});//borra filas vinculadas al id de acto
		int resu2 = db.delete(TABLE_1_NAME, T1C1+"=?", new String[] {Integer.toString(actoid)});//borra acto de la tabla acto
		
		if(resu !=0 && resu2 != 0)
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}

	
	public void cerrar()
	{
		this.close();
	}
	
	public boolean Conectado()
	{
		return this.getWritableDatabase().isOpen();
		
		
	}

	

}