package com.teatro.utilidades;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class PantallaEspera extends AsyncTask<Void, Void, Void>{
	
	private MetodosPantalla MEP;
	private ProgressDialog PD;
	
	public PantallaEspera(Context context, String title, String msg,MetodosPantalla MP){
		MEP = MP;
		PD = new ProgressDialog(context);
		PD.setTitle(title);
		PD.setMessage(msg);
		PD.setCancelable(false);
		MEP.Pre();
	};
	
	public void Start(){
		this.execute();
	}
	
	protected void onPreExecute(){
		PD.show();
	}
	
	protected Void doInBackground(Void... param) {
		MEP.Run();
		return null;
	}
	
	protected void onPostExecute(Void result){
		PD.dismiss();
		MEP.Post();
	}
	
}