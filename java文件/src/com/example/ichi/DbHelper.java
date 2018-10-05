package com.example.ichi;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;  //����1
import android.database.sqlite.SQLiteDatabase;  //����2
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DbHelper extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "mydb";  //����
	public static final String TABLE_NAME = "friends";    //����
	public static final int DATABASE_VERSION = 1;

	public static final int FRIENDS = 1;
	public static final int FRIENDS_ID = 2;
	
	//��Ӧ�ڱ�friends�������ֶ�
	 public static final String ID = "_id";  //���»��߱�ʾ���ֶβ����û�����
	 public static final String NAME = "name";  //�����ֶ�
	 public static final String AGE = "age";
	
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		// TODO Auto-generated constructor stub
		//����Ĺ��췽�����ø���Ĺ��췽��
		super(context, name, factory, version);   
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE IF NOT EXISTS " +
				TABLE_NAME + "( _id integer primary key autoincrement," +//
				"name varchar," + 
				"age integer"+
				")");
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);  //��ɾ��
		onCreate(db);   //�󴴽�
	}
}
