package anuf.exemplo.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

	public static final String DBNAME = "shoppinglist";
	public static final int DBVERSION = 1;
	
	
	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		createProductsTable(db);
		
	}

	private void createProductsTable(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder("create table ");
		//sb.append();
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
