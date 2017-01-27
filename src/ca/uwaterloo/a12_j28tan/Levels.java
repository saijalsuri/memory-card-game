package ca.uwaterloo.a12_j28tan;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Levels extends ListActivity{

	String classes[] = {"Level 1","Level 2","Level 3","Level 4","Level 5"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setListAdapter(new ArrayAdapter<String>(Levels.this, android.R.layout.simple_list_item_1, classes));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

			Intent intent1 = new Intent(Levels.this,NewGame.class);
			intent1.putExtra("level", position);
			Log.d("sadasd","adas"+position);
			startActivity(intent1);
		

	}
}
