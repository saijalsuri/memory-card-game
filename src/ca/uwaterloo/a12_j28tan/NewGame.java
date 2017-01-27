package ca.uwaterloo.a12_j28tan;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import ca.uwaterloo.a12_j28tan.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class NewGame extends Activity {
	private static int ROW_NUMBER = -1;
	private static int COL_NUMBER = -1;
	private Context context;
	private Drawable backCard;
	private int [] [] cards;
	private List<Drawable> animalPics;
	private Card cardOne;
	private Card cardTwo;
	private ButtonListener buttonListener;
	private int numberOfCards;
	private double counter=0;
	private Timer time = new Timer();


	private static Object locker = new Object();

	private TableLayout Table;
	private CardsUpdater handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final Runnable timerTick = new Runnable () {
			@Override
			public void run () {
				TextView time_text = (TextView)findViewById(R.id.time);

				counter++;
				time_text.setText("Time:" + String.format("%.2f", counter) + "s");
				if (counter >= 60){
					time_text.setText("Time:" + String.format("%.2f", counter/60) + "min");
					if (counter >= 3600){
						time_text.setText("Time:" + String.format("%.2f", counter/3600) + "hours");
					}
				}

			}
		};
		
		
		time.schedule(new TimerTask () {
	    	@Override
	    	public void run() {
	    	runOnUiThread(timerTick);
	    	}
	    	}, 0, 1000);
		
		Bundle extras = getIntent().getExtras();
		final int level = 1+extras.getInt("level");
		int r=0;
		int c=0;
		
		if(level == 1){
			r=3;
			c=4;
		}
		if(level == 2){
			r=4;
			c=5;
		}
		if(level == 3){
			r=4;
			c=6;
		}
		if(level == 4){
			r=5;
			c=6;
		}
		if(level == 5){
			r=6;
			c=6;
		}
		
		
		
		handler = new CardsUpdater();
		loadanimalPics();
		setContentView(R.layout.activity_new_game);

		backCard =  getResources().getDrawable(R.drawable.tree_icon);

		buttonListener = new ButtonListener();

		Table = (TableLayout)findViewById(R.id.TableLayout03);


		context  = Table.getContext();

		ROW_NUMBER = r;
		COL_NUMBER = c;

		numberOfCards = r*c;
		Log.d("initially","initial"+numberOfCards);

		cards = new int [COL_NUMBER] [ROW_NUMBER];


		Table.removeView(findViewById(R.id.TableRow01));
		Table.removeView(findViewById(R.id.TableRow02));

		TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
		tr.removeAllViews();

		Table = new TableLayout(context);
		tr.addView(Table);

		for (int y = 0; y < ROW_NUMBER; y++) {
			Table.addView(rowCreater(y));
		}

		cardOne=null;
		loadCards();


	}

	private void loadanimalPics() {
		animalPics = new ArrayList<Drawable>();
		
		animalPics.add(getResources().getDrawable(R.drawable.card1));
		animalPics.add(getResources().getDrawable(R.drawable.card2));
		animalPics.add(getResources().getDrawable(R.drawable.card3));
		animalPics.add(getResources().getDrawable(R.drawable.card4));
		animalPics.add(getResources().getDrawable(R.drawable.card5));
		animalPics.add(getResources().getDrawable(R.drawable.card6));
		animalPics.add(getResources().getDrawable(R.drawable.card7));
		animalPics.add(getResources().getDrawable(R.drawable.card8));
		animalPics.add(getResources().getDrawable(R.drawable.card9));
		animalPics.add(getResources().getDrawable(R.drawable.card10));
    	animalPics.add(getResources().getDrawable(R.drawable.card11));
    	animalPics.add(getResources().getDrawable(R.drawable.card12));
    	animalPics.add(getResources().getDrawable(R.drawable.card13));
    	animalPics.add(getResources().getDrawable(R.drawable.card14));
    	animalPics.add(getResources().getDrawable(R.drawable.card15));
    	animalPics.add(getResources().getDrawable(R.drawable.card16));
    	animalPics.add(getResources().getDrawable(R.drawable.card17));
    	animalPics.add(getResources().getDrawable(R.drawable.card18));
    	animalPics.add(getResources().getDrawable(R.drawable.card19));
    	animalPics.add(getResources().getDrawable(R.drawable.card20));
    	animalPics.add(getResources().getDrawable(R.drawable.card21));

	}

	private void loadCards(){
			int size = ROW_NUMBER*COL_NUMBER;

			ArrayList<Integer> list = new ArrayList<Integer>();

			for(int i=0;i<size;i++){
				list.add(new Integer(i));
			}


			Random r = new Random();

			for(int i=size-1;i>=0;i--){
				int t=0;

				if(i>0){
					t = r.nextInt(i);
				}

				t=list.remove(t).intValue();
				cards[i%COL_NUMBER][i/COL_NUMBER]=t%(size/2);
			}

	}

	private TableRow rowCreater(int y){
		TableRow row = new TableRow(context);
		row.setHorizontalGravity(Gravity.CENTER);

		for (int x = 0; x < COL_NUMBER; x++) {
			row.addView(imageCreaterButton(x,y));
		}
		return row;
	}

	private View imageCreaterButton(int x, int y){
		Button button = new Button(context);
		button.setBackgroundDrawable(backCard);
		button.setId(100*x+y);
		button.setOnClickListener(buttonListener);
		return button;
	}

	class ButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {

			synchronized (locker) {
				if(cardOne!=null && cardTwo != null){
					return;
				}
				int id = v.getId();
				int x = id/100;
				int y = id%100;
				turnCard((Button)v,x,y);
			}

		}

		private void turnCard(Button button,int x, int y) {
			button.setBackgroundDrawable(animalPics.get(cards[x][y]));

			if(cardOne==null){
				cardOne = new Card(button,x,y);
			}
			else{ 

				if(cardOne.x == x && cardOne.y == y){

					return; //the user pressed the same card

				}

				cardTwo = new Card(button,x,y);


				TimerTask timertask = new TimerTask() {

					@Override
					public void run() {
							synchronized (locker) {
								handler.sendEmptyMessage(0);
							}	
					}
				};

				Timer t = new Timer(false);
				t.schedule(timertask, 700);
			}


		}

	}

	class CardsUpdater extends Handler{

		@Override
		public void handleMessage(Message msg) {
			synchronized (locker) {
				checkCards();
			}
		}
		public void checkCards(){
			if(cards[cardTwo.x][cardTwo.y] == cards[cardOne.x][cardOne.y]){
				cardOne.button.setVisibility(View.INVISIBLE);
				cardTwo.button.setVisibility(View.INVISIBLE);
				numberOfCards = numberOfCards - 2;
				Log.d("left","left"+numberOfCards);
				if(numberOfCards == 0){
					time.cancel();
					AlertDialog youreDone = new AlertDialog.Builder(NewGame.this).create();
					youreDone.setTitle("Congratulations!");
					youreDone.setMessage("You have successfully completed this level in " + String.format("%.2f", counter)+"s");
					youreDone.show();
					new Handler().postDelayed(new Runnable() {
	                      @Override
	                      public void run() {

	                    	  Intent intent2 = new Intent(NewGame.this,MainActivity.class);
	      					intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	      					startActivity(intent2);
	                      }
	                  }, 3200);
					
					
				}
			}
			else {
				cardTwo.button.setBackgroundDrawable(backCard);
				cardOne.button.setBackgroundDrawable(backCard);
				Toast.makeText(getApplicationContext(), "The jungle animals out-smarted you! NO MATCH!", Toast.LENGTH_SHORT).show();
			}

			cardOne=null;
			cardTwo=null;
		}
	}




}