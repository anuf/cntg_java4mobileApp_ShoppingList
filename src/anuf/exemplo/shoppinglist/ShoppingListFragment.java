package anuf.exemplo.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ShoppingListFragment extends ListFragment {
	public static int PRODUCTO;
	private Button btnAdd;
	private Cursor curResultado;
	private DBOpenHelper dboh;
	private SQLiteDatabase db;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shoppinglist,
				container, false);

		btnAdd = (Button) getActivity().findViewById(R.id.btnAdd);

		btnAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent anIntent = new Intent(getActivity(),
						ProductActivity.class);
				startActivityForResult(anIntent, PRODUCTO);
			}
		});
		

		
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		cargarLista();
		super.onViewCreated(view, savedInstanceState);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		cargarLista();
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private void cargarLista(){
		
		dboh = new DBOpenHelper(getActivity());
		db = dboh.getReadableDatabase();
		
		String[] columns = {dboh.COLUMN_ID, dboh.COLUMN_NAME, dboh.COLUMN_QUANTITY, dboh.COLUMN_UNIT};
		curResultado = db.query(dboh.TABLE_PRODUCTS, columns, null, null, null, null, null);

		String[] from = new String[] {dboh.COLUMN_NAME, dboh.COLUMN_QUANTITY};
		int[] to = new int[] { android.R.id.text1, android.R.id.text2}; 
				
		// OPTION 1 : use a list adapter with a list of products 		
		List <Product> products = new ArrayList<Product>();
		curResultado.moveToFirst();
	    while (!curResultado.isAfterLast()) {
	      Product p = new Product(Integer.parseInt(curResultado.getString(0)), curResultado.getString(1), Integer.parseInt(curResultado.getString(2)), curResultado.getString(3));
	      products.add(p);
	      curResultado.moveToNext();
	    }
	    ListAdapter lAdapter = new ArrayAdapter<Product>(
				getActivity(),
				android.R.layout.simple_list_item_1,
				products);
		
	    // OPTION 2 : use a cursor adapter
		CursorAdapter adapter = new SimpleCursorAdapter (getActivity(),  android.R.layout.two_line_list_item, 
				curResultado, from, to, CursorAdapter.FLAG_AUTO_REQUERY); 
		
		// set one of the adapters defined above
		setListAdapter(adapter);
	}

}
