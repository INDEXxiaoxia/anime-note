package com.example.ichi;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;  //主类1
import android.database.sqlite.SQLiteDatabase;  //主类2
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DbHelper extends SQLiteOpenHelper{
	
	public static final String DATABASE_NAME = "mydb";  //库名
	public static final String TABLE_NAME = "friends";    //表名
	public static final int DATABASE_VERSION = 1;

	public static final int FRIENDS = 1;
	public static final int FRIENDS_ID = 2;
	
	//对应于表friends的三个字段
	 public static final String ID = "_id";  //加下划线表示该字段不由用户输入
	 public static final String NAME = "name";  //其它字段
	 public static final String AGE = "age";
	
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		// TODO Auto-generated constructor stub
		//本类的构造方法调用父类的构造方法
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
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);  //先删除
		onCreate(db);   //后创建
	}
}
