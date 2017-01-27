package ca.uwaterloo.a12_j28tan;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Instructions_page extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instructions_page);
		TextView title = (TextView)findViewById(R.id.How_to_Play); 
		title.setTextColor(Color.BLACK);
		title.setText("How to Play \n" + "The jungle animals are playing hide and seek with their best buddies! " +
		"Use your amazing memory skills to help each animal find their friend! Click on the trees to uncover " +
		"a pair of jungle animals. If they don't match, the animals will go back into the trees. Match all " +
		"the animals to their partners in record time to complete the level!"); 
		
		Button back = (Button)findViewById(R.id.Menu);
		back.setOnClickListener(new Button.OnClickListener(){
	       	@Override
	       	public void onClick(View v) {
	       		Intent return_to_Main = new Intent(Instructions_page.this, MainActivity.class);
	       		startActivity(return_to_Main);
	       		
	       	}
	       });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_instructions_page, menu);
		return true;
	}

}
