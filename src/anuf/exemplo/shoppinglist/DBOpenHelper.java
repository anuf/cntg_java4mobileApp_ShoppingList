package anuf.exemplo.shoppinglist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

	public static final String DBNAME = "shoppinglist";
	public static final int DBVERSION = 1;

	public static final String TABLE_PRODUCTS = "products";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_QUANTITY = "quantity";
	public static final String COLUMN_UNIT = "unit";

	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, DBVERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Creating tables
		createProductsTable(db);

	}

	private void createProductsTable(SQLiteDatabase db) {
		StringBuilder sb = new StringBuilder("create table ");
		sb.append(TABLE_PRODUCTS).append(" (");
		sb.append(COLUMN_ID).append(" integer primary key autoincrement, ");
		sb.append(COLUMN_NAME).append(
				" varchar(100) not null unique on conflict rollback, ");
		sb.append(COLUMN_QUANTITY).append(" integer not null, ");
		sb.append(COLUMN_UNIT).append(" varchar(10) not null)");
		db.execSQL(sb.toString());

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// We decide to drop the table and create a new one on Upgrade
		Log.w(DBOpenHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
		onCreate(db);
	}

}
