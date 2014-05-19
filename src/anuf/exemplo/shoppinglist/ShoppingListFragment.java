package anuf.exemplo.shoppinglist;

import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources.Theme;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

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
		registerForContextMenu(getListView());
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

	private void cargarLista() {

		dboh = new DBOpenHelper(getActivity());
		db = dboh.getReadableDatabase();

		String[] columns = { dboh.COLUMN_ID, dboh.COLUMN_NAME,
				dboh.COLUMN_QUANTITY, dboh.COLUMN_UNIT };
		curResultado = db.query(dboh.TABLE_PRODUCTS, columns, null, null, null,
				null, null);
		getActivity().startManagingCursor(curResultado);

		String[] from = new String[] { dboh.COLUMN_NAME, dboh.COLUMN_QUANTITY };
		int[] to = new int[] { android.R.id.text1, android.R.id.text2 };

		// OPTION 1 : use a list adapter with a list of products
		List<Product> products = new ArrayList<Product>();
		curResultado.moveToFirst();
		while (!curResultado.isAfterLast()) {
			Product p = new Product(
					Integer.parseInt(curResultado.getString(0)),
					curResultado.getString(1), Integer.parseInt(curResultado
							.getString(2)), curResultado.getString(3));
			products.add(p);
			curResultado.moveToNext();
		}
		ListAdapter lAdapter = new ArrayAdapter<Product>(getActivity(),
				android.R.layout.simple_list_item_1, products);

		// OPTION 2 : use a cursor adapter
		CursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
				android.R.layout.two_line_list_item, curResultado, from, to,
				CursorAdapter.FLAG_AUTO_REQUERY);

		// set one of the adapters defined above
		setListAdapter(lAdapter);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater mInflater = getActivity().getMenuInflater();
		mInflater.inflate(R.menu.context_menu_layout, menu);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		Product theProduct;
		// --- This is done to get the position of the item we are clicking
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Adapter adapter = getListAdapter();
		// 

		// ---

		// item.setTitle(menuInfo.position + ":- " +item.getTitle().toString());
		switch (item.getItemId()) {
		case R.id.context_create:
			Toast.makeText(
					getActivity(),
					item.getTitle() + " selected at position "
							+ menuInfo.position, Toast.LENGTH_LONG).show();
			break;
		case R.id.context_read:
			Toast.makeText(
					getActivity(),
					item.getTitle() + " selected at position "
							+ menuInfo.position, Toast.LENGTH_LONG).show();
			break;
		case R.id.context_update:
			Toast.makeText(
					getActivity(),
					item.getTitle() + " selected at position "
							+ menuInfo.position, Toast.LENGTH_LONG).show();
			break;
		case R.id.context_delete:
			if (adapter instanceof ListAdapter) {
				theProduct = (Product) adapter.getItem(menuInfo.position);
				eliminarElemento(theProduct);
				cargarLista();
			}else if(adapter instanceof CursorAdapter){
				item.getItemId()
				eliminarElemento();
				// TODO facer este caso para que a lista se actualice automaticamente
			};
				
			//Toast.makeText(getActivity(),	item.getTitle() + " selected at position "+ menuInfo.position, Toast.LENGTH_LONG).show();
			break;
		default:
			return super.onOptionsItemSelected(item);
		}
		return true;
	}

	private void eliminarElemento(Product p) {

		dboh = new DBOpenHelper(getActivity());
		db = dboh.getWritableDatabase();
		db.beginTransaction();
		try {

			String whereClause = DBOpenHelper.COLUMN_ID + " = ?";

			String[] whereArgs = new String[] { String.valueOf(p.getID()) };

			db.delete(DBOpenHelper.TABLE_PRODUCTS, whereClause, whereArgs);
			db.setTransactionSuccessful();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.endTransaction();
		}

	}
	
	private void eliminarElemento2(Long id) {

		dboh = new DBOpenHelper(getActivity());
		db = dboh.getWritableDatabase();
		db.beginTransaction();
		try {

			String whereClause = DBOpenHelper.COLUMN_ID + " = ?";

			String[] whereArgs = new String[] { String.valueOf(id) };

			db.delete(DBOpenHelper.TABLE_PRODUCTS, whereClause, whereArgs);
			db.setTransactionSuccessful();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.endTransaction();
		}

	}

}
