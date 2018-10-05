package com.example.ichi;
import android.app.Activity;  //
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;  //相关类
import android.database.Cursor;  //
import android.database.sqlite.SQLiteDatabase;   //主类

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.ListView;  //主类
import android.widget.SimpleAdapter;  //相关类
import android.widget.AdapterView.OnItemClickListener;

public class three extends Activity {
	
    private EditText et_name;
	private EditText et_age;
	
	private Map<String,Object> item;  //
	private ArrayList<Map<String, Object>> data;    //
	private DbHelper dbHelper;
	private SQLiteDatabase db;
	private Cursor cursor;
	private SimpleAdapter listAdapter;   //
	private ListView listview;
	private Button addBtn,updBtn,delBtn;
	private String selId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.three);
        
        et_name=(EditText) findViewById(R.id.et_name);
		et_age=(EditText) findViewById(R.id.et_age);
		listview = (ListView) findViewById(R.id.listView);
		addBtn=(Button)findViewById(R.id.bt_add);
		updBtn=(Button)findViewById(R.id.bt_modify);
		delBtn=(Button)findViewById(R.id.bt_del);
		
		addBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbAdd();  //添加记录
				dbFindAll();
			}			
		});
		updBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbUpdate();  //修改当前记录
				dbFindAll();				
			}			
		});
		delBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbDel();      //删除当前记录
				dbFindAll();
			}			
		});
		
		dbHelper = new DbHelper(this,DbHelper.DATABASE_NAME, null, 1);   //创建工具类的实例
		db = dbHelper.getWritableDatabase();    // 以可写入方式打开数据库		
		data = new ArrayList<Map<String,Object>>();	//列表类型数据
		dbFindAll();
		
		listview.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				// TODO Auto-generated method stub
				Map<String,Object> listItem =(Map<String,Object>) listview.getItemAtPosition(position);
				et_name.setText((String)listItem.get("name"));
				et_age.setText((String)listItem.get("age"));
				selId=(String)listItem.get("_id");
				Toast.makeText(getApplicationContext(), "选择的id是："+selId,Toast.LENGTH_SHORT).show();
			}
		});		
    }
//------------------------------------------------------
	protected void dbDel() {
		String where="_id="+selId;
		int i=db.delete(DbHelper.TABLE_NAME, where, null);
		if(i>0)
			Toast.makeText(getApplicationContext(), "数据删除成功！",Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "数据删除失败！",Toast.LENGTH_SHORT).show();
	}

	private void showList() {       // 显示记录集
		listAdapter=new SimpleAdapter(this,data,
				R.layout.list_item,
				new String[]{"_id","name","age"},
				new int[]{R.id.tv_id,R.id.tvname,R.id.tvage});
		listview.setAdapter(listAdapter);
	}

	protected void dbUpdate() {
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("name", et_name.getText().toString().trim());
		values.put("age", et_age.getText().toString().trim());
		String where="_id="+selId;
		int i=db.update(DbHelper.TABLE_NAME, values, where, null);
		if(i>0)
			Toast.makeText(getApplicationContext(), "数据更新成功！",Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "数据更新失败！",Toast.LENGTH_SHORT).show();
	}

	protected void dbAdd() {    //插入记录
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("name", et_name.getText().toString().trim());
		values.put("age", et_age.getText().toString().trim());
		long rowid=db.insert(DbHelper.TABLE_NAME, null, values);  //第二参数通常为null
		if(rowid==-1)
			Toast.makeText(getApplicationContext(), "数据插入失败！",Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "数据插入成功！",Toast.LENGTH_SHORT).show();
	}

	protected void dbFindAll() {       //记录查找
		data.clear();  //必须清除
		
		//生成记录集的两种等价方法
		cursor=db.rawQuery("select * from friends order by _id ASC",null);
		//cursor = db.query(DbHelper.TB_NAME, null, null, null, null, null,"_id ASC");  
		
		item=new HashMap<String,Object>();
		item.put("_id", "序号");
		item.put("name", "日期");
		item.put("age", "事件");
		data.add(item);
		
		cursor.moveToFirst();		
		while (!cursor.isAfterLast() ) {
			String id=cursor.getString(0);
			String name=cursor.getString(1);
			String age=cursor.getString(2);			
			item=new HashMap<String,Object>();
			item.put("_id", id);
			item.put("name", name);
			item.put("age", age);
			data.add(item);
			cursor.moveToNext();			
		}
		showList();
	}	
}