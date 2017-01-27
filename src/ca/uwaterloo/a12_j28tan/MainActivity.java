package ca.uwaterloo.a12_j28tan;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private NewGame Test;
	private double bestTime = 0;
	double check = 0;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		Button Instructions = (Button)findViewById(R.id.Instructions);
		Instructions.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent instructions_page = new Intent(MainActivity.this, Instructions_page.class);
				startActivity(instructions_page);

			}
		});

		Button start = (Button)findViewById(R.id.Start);

		start.setOnClickListener(new View.OnClickListener() {


			@Override
			public void onClick(View v) {

				Intent intent= new Intent(MainActivity.this,Levels.class);
				startActivity(intent);
			}
		});
		

	}
	


}



