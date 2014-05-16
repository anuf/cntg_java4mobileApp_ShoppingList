package anuf.exemplo.shoppinglist;

import android.app.Activity;
import android.os.Bundle;

public class ShoppingListActivity extends Activity {	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_shoppinglist);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ShoppingListFragment()).commit();
		}
	}
}
