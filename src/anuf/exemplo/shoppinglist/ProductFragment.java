package anuf.exemplo.shoppinglist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
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
		spinnerUnits = (Spinner) getActivity().findViewById(R.id.spinnerUnits);
		buttonSave = (Button) getActivity().findViewById(R.id.btnSave);
		buttonCancel = (Button) getActivity().findViewById(R.id.btnCancel);
		etName = (EditText) getActivity().findViewById(R.id.etName);
		etQuantity = (EditText) getActivity().findViewById(R.id.etQuantity);

		ArrayAdapter<String> unitsAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_dropdown_item,
				units);

		spinnerUnits.setAdapter(unitsAdapter);

		buttonSave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Product newProduct = new Product(0,
						etName.getText().toString(), Integer
								.parseInt(etQuantity.getText().toString()),
						(String) spinnerUnits.getSelectedItem());
				saveProduct(newProduct);
				Intent saveIntent = new Intent();
				getActivity().setResult(getActivity().RESULT_OK, saveIntent);
				getActivity().finish();
			}
		});
		buttonCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cancelIntent = new Intent();
				getActivity().setResult(getActivity().RESULT_CANCELED,
						cancelIntent);
				getActivity().finish();

			}
		});
		super.onViewCreated(view, savedInstanceState);
	}

	private void saveProduct(Product prod) {
		dboh = new DBOpenHelper(getActivity());
		db = dboh.getWritableDatabase();
		db.beginTransaction();
		try {
			ContentValues cv = new ContentValues();
			cv.put(DBOpenHelper.COLUMN_NAME, prod.getName());
			cv.put(DBOpenHelper.COLUMN_QUANTITY, prod.getQuantity());
			cv.put(DBOpenHelper.COLUMN_UNIT, prod.getUnit());
			db.insert(DBOpenHelper.TABLE_PRODUCTS, null, cv);
			db.setTransactionSuccessful();

		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			db.endTransaction();
		}

	}

}
