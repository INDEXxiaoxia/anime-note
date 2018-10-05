package com.example.ichi;
import android.app.Activity;  //
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentValues;  //�����
import android.database.Cursor;  //
import android.database.sqlite.SQLiteDatabase;   //����

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.widget.ListView;  //����
import android.widget.SimpleAdapter;  //�����
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
				dbAdd();  //��Ӽ�¼
				dbFindAll();
			}			
		});
		updBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbUpdate();  //�޸ĵ�ǰ��¼
				dbFindAll();				
			}			
		});
		delBtn.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dbDel();      //ɾ����ǰ��¼
				dbFindAll();
			}			
		});
		
		dbHelper = new DbHelper(this,DbHelper.DATABASE_NAME, null, 1);   //�����������ʵ��
		db = dbHelper.getWritableDatabase();    // �Կ�д�뷽ʽ�����ݿ�		
		data = new ArrayList<Map<String,Object>>();	//�б���������
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
				Toast.makeText(getApplicationContext(), "ѡ���id�ǣ�"+selId,Toast.LENGTH_SHORT).show();
			}
		});		
    }
//------------------------------------------------------
	protected void dbDel() {
		String where="_id="+selId;
		int i=db.delete(DbHelper.TABLE_NAME, where, null);
		if(i>0)
			Toast.makeText(getApplicationContext(), "����ɾ���ɹ���",Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "����ɾ��ʧ�ܣ�",Toast.LENGTH_SHORT).show();
	}

	private void showList() {       // ��ʾ��¼��
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
			Toast.makeText(getApplicationContext(), "���ݸ��³ɹ���",Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "���ݸ���ʧ�ܣ�",Toast.LENGTH_SHORT).show();
	}

	protected void dbAdd() {    //�����¼
		// TODO Auto-generated method stub
		ContentValues values = new ContentValues();
		values.put("name", et_name.getText().toString().trim());
		values.put("age", et_age.getText().toString().trim());
		long rowid=db.insert(DbHelper.TABLE_NAME, null, values);  //�ڶ�����ͨ��Ϊnull
		if(rowid==-1)
			Toast.makeText(getApplicationContext(), "���ݲ���ʧ�ܣ�",Toast.LENGTH_SHORT).show();
		else
			Toast.makeText(getApplicationContext(), "���ݲ���ɹ���",Toast.LENGTH_SHORT).show();
	}

	protected void dbFindAll() {       //��¼����
		data.clear();  //�������
		
		//���ɼ�¼�������ֵȼ۷���
		cursor=db.rawQuery("select * from friends order by _id ASC",null);
		//cursor = db.query(DbHelper.TB_NAME, null, null, null, null, null,"_id ASC");  
		
		item=new HashMap<String,Object>();
		item.put("_id", "���");
		item.put("name", "����");
		item.put("age", "�¼�");
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