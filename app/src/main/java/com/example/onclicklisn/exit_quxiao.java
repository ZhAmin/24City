package com.example.onclicklisn;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class exit_quxiao implements OnClickListener{
	private Activity con;
	public exit_quxiao(Activity con){
		this.con = con;
	}
	@Override
	public void onClick(DialogInterface arg0, int arg1) {
		// TODO Auto-generated method stub
		arg0.dismiss();
	}

}