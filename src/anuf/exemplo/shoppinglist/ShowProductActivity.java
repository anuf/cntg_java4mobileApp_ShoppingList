package anuf.exemplo.shoppinglist;

import android.app.Activity;
import android.os.Bundle;

public class ShowProductActivity extends Activity {	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_showproduct);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new ShowProductFragment()).commit();
		}
	}
}
