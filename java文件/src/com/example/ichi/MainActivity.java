package com.example.ichi;

import com.example.ichi.MainActivity;
import com.example.ichi.R;
import com.example.ichi.MainActivity.MyListener;

import android.support.v7.app.ActionBarActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;



public class MainActivity extends ActionBarActivity {
	RadioGroup rg_bottom;
	
    FrameLayout fl_top;
	View vone,vtwo,vthree,vfour;
	//定义变量名


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        rg_bottom=(RadioGroup) this.findViewById(R.id.rg_bottom);
        
        fl_top=(FrameLayout) this.findViewById(R.id.fl_top);
        
        MyListener listener =new MyListener();
        //添加一个监听
        rg_bottom.setOnCheckedChangeListener(listener);
       vone=View.inflate(MainActivity.this, R.layout.one, null);
       vtwo=View.inflate(MainActivity.this, R.layout.two, null);
       vthree=View.inflate(MainActivity.this, R.layout.three, null);
        vfour=View.inflate(MainActivity.this, R.layout.four, null);
        
        fl_top.removeAllViews();
    	fl_top.addView(vone);
    	
    	rg_bottom.check(R.id.radio0);
    }
    //创建一个自己的监听,监听单选按钮组
    class MyListener implements  OnCheckedChangeListener{
    	
    	@Override
    	public void onCheckedChanged(RadioGroup group,int checkedId){
    		//TODO Auto-generated method stub
    		//循环擦除显示
    		fl_top.removeAllViews();
    		switch(checkedId){
    		case R.id.radio0:
    		fl_top.addView(vone);
    			break;
    		case R.id.radio1:
    			fl_top.addView(vtwo);
    		 break;
    		case R.id.radio2:
    			fl_top.addView(vthree);
    			startActivity(new Intent(MainActivity.this, three.class));  
    			break;
    		case R.id.radio3:
    			fl_top.addView(vfour);  
    			 Intent intent=new Intent();  
                 intent.setComponent(new ComponentName("com.example.bdlocationmap", "com.example.bdlocationmap.MainActivity"));  
                 startActivity(intent);  

    		}
    	}
    }


}
