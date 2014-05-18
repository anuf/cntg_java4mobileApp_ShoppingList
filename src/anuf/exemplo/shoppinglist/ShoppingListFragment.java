package anuf.exemplo.shoppinglist;

import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

public class ShoppingListFragment extends ListFragment {
	public static int PRODUCTO;
	private Button btnAdd;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_shoppinglist, container,
				false);
		
		btnAdd = (Button) getActivity().findViewById(R.id.btnAdd);
		
		btnAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent anIntent = new Intent(getActivity(), ProductActivity.class);
				startActivityForResult(anIntent, PRODUCTO);
			}
		});
		//List <Product> products = 
		//List<Product> books = DataAccessFactory.getInstance(getActivity()).getBooks();
		/*
		CursorAdapter adapter = new CursorAdapter(getActivity(),
				android.R.layout.simple_list_item_1, books);
		setListAdapter(adapter);
*/
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		
	}


}
