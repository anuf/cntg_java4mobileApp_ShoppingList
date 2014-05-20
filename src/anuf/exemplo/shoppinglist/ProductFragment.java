package anuf.exemplo.shoppinglist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.datatype.Duration;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ProductFragment extends Fragment {
	// DB
	DBOpenHelper dboh;
	SQLiteDatabase db;

	// Views
	EditText etName, etQuantity;
	Spinner spinnerUnits;
	Button buttonSave, buttonCancel;
	List<String> units = new ArrayList<String>(Arrays.asList("Uds", "L", "Kg"));

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_product, container,
				false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {

		createViews();
		setListeners();

		//Intent theIntent = getActivity().getIntent();
		// We get the element at row desired and fill the views
		if (!(getActivity().getIntent().getStringExtra("id")==null)) {
			Cursor newCursor;
			dboh = new DBOpenHelper(getActivity());
			db = dboh.getReadableDatabase();
			
			String[] columns = { DBOpenHelper.COLUMN_ID, DBOpenHelper.COLUMN_NAME, DBOpenHelper.COLUMN_QUANTITY, DBOpenHelper.COLUMN_UNIT };
			db.beginTransaction();
			try {
				String selection = DBOpenHelper.COLUMN_ID +" = "+ getActivity().getIntent().getStringExtra("id"); 
				newCursor = db.query(DBOpenHelper.TABLE_PRODUCTS, columns, selection, null, null,
						null, null);
				
				newCursor.moveToFirst();
				while (!newCursor.isAfterLast()) {
					etName.setText(newCursor.getString(1));
					etQuantity.setText(String.valueOf(newCursor.getInt(2)));
					for(int i=0; i < spinnerUnits.getAdapter().getCount(); i++) {
						  if(String.valueOf(newCursor.getString(3)).trim().equals(spinnerUnits.getAdapter().getItem(i).toString())){
							  spinnerUnits.setSelection(i);
						    break;
						  }
						}
					newCursor.moveToNext();
				}	
				
				db.setTransactionSuccessful();

			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				db.endTransaction();
			}
		
			
		}

		super.onViewCreated(view, savedInstanceState);
	}

	private void saveProduct(Product prod) {
		dboh = new DBOpenHelper(getActivity());
		db = dboh.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues cv = new ContentValues();
			cv.put(DBOpenHelper.COLUMN_NAME, etName.getText().toString());
			cv.put(DBOpenHelper.COLUMN_QUANTITY, Integer.parseInt(etQuantity.getText().toString()));
			cv.put(DBOpenHelper.COLUMN_UNIT, (String) spinnerUnits.getSelectedItem());
			
			if (!(getActivity().getIntent().getStringExtra("id")==null)) {
				String whereClause = DBOpenHelper.COLUMN_ID +"=?";
				String[] whereArgs = {getActivity().getIntent().getStringExtra("id")};
				db.update(DBOpenHelper.TABLE_PRODUCTS, cv, whereClause, whereArgs);
			}else{
				db.insert(DBOpenHelper.TABLE_PRODUCTS, null, cv);
			}
			db.setTransactionSuccessful();

		} catch (Exception e) {
			Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG);
		} finally {
			db.endTransaction();
		}

	}

	private void createViews() {
		spinnerUnits = (Spinner) getActivity().findViewById(R.id.spinnerUnits);
		buttonSave = (Button) getActivity().findViewById(R.id.btnSave);
		buttonCancel = (Button) getActivity().findViewById(R.id.btnCancel);
		etName = (EditText) getActivity().findViewById(R.id.etName);
		etQuantity = (EditText) getActivity().findViewById(R.id.etQuantity);

		ArrayAdapter<String> unitsAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				units);

		spinnerUnits.setAdapter(unitsAdapter);
	}

	private void setListeners() {
		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Product newProduct = new Product(0,
						etName.getText().toString(), Integer
								.parseInt(etQuantity.getText().toString()),
						(String) spinnerUnits.getSelectedItem());
				saveProduct(newProduct);
				Intent saveIntent = new Intent();
				getActivity();
				getActivity().setResult(Activity.RESULT_OK, saveIntent);
				getActivity().finish();
			}
		});
		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cancelIntent = new Intent();
				getActivity();
				getActivity().setResult(Activity.RESULT_CANCELED, cancelIntent);
				getActivity().finish();

			}
		});
	}

	

	
	

}
