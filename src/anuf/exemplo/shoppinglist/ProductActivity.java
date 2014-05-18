package anuf.exemplo.shoppinglist;

import android.app.Activity;
import android.os.Bundle;

public class ProductActivity extends Activity {	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_product);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ProductFragment()).commit();
		}
	}
}
