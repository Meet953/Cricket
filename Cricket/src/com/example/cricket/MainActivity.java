package com.example.cricket;

import java.util.Random;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	Integer[] overs = {1,2,3};
	Integer[] players = {5,11};
	
	Spinner spinOver,spinPlyr;
	EditText etTeam1,etTeam2;
	TextView tvStatus,tvCurPlyr,tvScore,tvSBTeam1,tvSBTeam2,tvResult;
	Button btStart,btBall;
	
	int totOver, totPlyr, totBalls;
	int tmflag = 0;
	int t1KO = 0,t2KO = 0;
	int ctBall1 = 0, ctPlyr1 = 1, totTeam1Score = 0;
	int ctBall2 = 0, ctPlyr2 = 1, totTeam2Score = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		spinOver = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<Integer> overAdp = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,overs);
		spinOver.setAdapter(overAdp);
	
		spinPlyr = (Spinner) findViewById(R.id.spinner2);
		ArrayAdapter<Integer> plyrAdp = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,players);
		spinPlyr.setAdapter(plyrAdp);
		
		tvStatus = (TextView) findViewById(R.id.textView5);
		tvCurPlyr = (TextView) findViewById(R.id.textView6);
		tvScore = (TextView) findViewById(R.id.textView7);
		tvSBTeam1 = (TextView) findViewById(R.id.textView9);
		tvSBTeam2 = (TextView) findViewById(R.id.textView11);
		tvResult = (TextView) findViewById(R.id.textView12);
		etTeam1 = (EditText) findViewById(R.id.editText1);
		etTeam2 = (EditText) findViewById(R.id.editText2);
		btStart = (Button) findViewById(R.id.button1);
		btBall = (Button) findViewById(R.id.button2);
		
		btBall.setEnabled(false);
		
		btStart.setOnClickListener(this);
		btBall.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		if(arg0 == btStart)
		{
			btStart.setEnabled(false);
			etTeam1.setEnabled(false);
			etTeam2.setEnabled(false);
			spinOver.setEnabled(false);
			spinPlyr.setEnabled(false);
			
			int min = 1;
			int max = 2;
			Random r = new Random();
			int toss = r.nextInt(max - min +1 ) + min;
			if(toss==1)
			{
				tmflag = 1;
				Toast.makeText(this,"Team 1 won the toss & will Ball first",Toast.LENGTH_LONG).show();
			}
			else
			{
				tmflag = 2;
				Toast.makeText(this,"Team 2 won the toss & will Ball first",Toast.LENGTH_LONG).show();	
			}
			
			totOver = Integer.parseInt(spinOver.getSelectedItem().toString().toString());
			totBalls = 6*totOver;
			totPlyr = Integer.parseInt(spinPlyr.getSelectedItem().toString().toString());
			btBall.setEnabled(true);
		}
		
		else if(arg0 == btBall)
		{
			if(t1KO == 1 && t2KO == 1)
			{
				tvStatus.setText("Team 1 Avg. :"+totTeam1Score/ctPlyr1+" Team 2 Avg. :"+totTeam2Score/ctPlyr2);
				if(totTeam1Score > totTeam2Score)
				{
					tvResult.setText("Team "+etTeam1.getText().toString()+" won");	
				}
				else if(totTeam2Score > totTeam1Score)
				{
					tvResult.setText("Team "+etTeam2.getText().toString()+" won ");
				}
			}
			
			else if(tmflag == 1)
			{
				tvStatus.setText("Team 1 is Bowling");
				if((ctBall1 < totBalls)&&(ctPlyr2 < totPlyr))
				{
					ctBall1++;
					tvCurPlyr.setText("Team 2 :" + "Player "+ ctPlyr2+" : SCORE :");
					int min = 0;
					int max = 9;
					Random r = new Random();
					int run = r.nextInt(max - min +1 ) + min;
					if((run == 1)||(run == 2)||(run == 3)||(run == 4)||(run == 5)||(run == 6))
					{
						tvScore.setText(run+" ");
						Toast.makeText(this,run +"Runs Scored",Toast.LENGTH_LONG).show();
						totTeam2Score+=run;
					}
					else if(run == 7)
					{
						tvScore.setText("1");
						Toast.makeText(this,"No Ball 1 Run Scored",Toast.LENGTH_LONG).show();
						totTeam2Score+=1;
						ctBall1--;
					}
					else if(run == 8)
					{
						tvScore.setText("1");
						Toast.makeText(this,"Wide Ball 1 Run Scored",Toast.LENGTH_LONG).show();
						totTeam2Score+=1;
					}
					else if(run == 9)
					{
						tvScore.setText("4");
						Toast.makeText(this,"Leg Bye 4 Runs Scored",Toast.LENGTH_LONG).show();
						totTeam2Score+=4;
					}
					else if(run == 0)
					{
						tvScore.setText("0");
						Toast.makeText(this,"Player "+ctPlyr2+" of Team 2 is Out",Toast.LENGTH_LONG).show();
						ctPlyr2++;
					}
				}
				
				else if((ctBall1 < totBalls)&&(ctPlyr2 > totPlyr))
				{
					Toast.makeText(this,"Team 2 All Out",Toast.LENGTH_LONG).show();
					tvStatus.setText("Team 2 All Out");
					t2KO = 1;
					tmflag=2;
				}
				
				else if((ctBall1 >= totBalls)&&(ctPlyr2 < totPlyr))
				{
					Toast.makeText(this,"Team 2 Innings Over",Toast.LENGTH_LONG).show();
					tvStatus.setText("Team 2 Innings Over");
					t2KO = 1;
					tmflag=2;
				}
				
				int q = ctBall1/6;
				int r = ctBall1%6;
				int d = ctPlyr2-1;
				tvSBTeam2.setText("Runs :"+totTeam2Score+" Wickets :"+d+" Overs :"+q+"."+r);
			}
			
			else if(tmflag == 2)
			{
				tvStatus.setText("Team 2 is Bowling");
				if((ctBall2 < totBalls)&&(ctPlyr1 < totPlyr))
				{
					ctBall2++;
					tvCurPlyr.setText("Team 1 :" + "Player "+ ctPlyr1+" : SCORE :");
					int min = 0;
					int max = 9;
					Random r = new Random();
					int run = r.nextInt(max - min +1 ) + min;
					if((run == 1)||(run == 2)||(run == 3)||(run == 4)||(run == 5)||(run == 6))
					{
						tvScore.setText(run+" ");
						Toast.makeText(this,run +"Runs Scored",Toast.LENGTH_LONG).show();
						totTeam1Score+=run;
					}
					else if(run == 7)
					{
						tvScore.setText("1");
						Toast.makeText(this,"No Ball 1 Run Scored",Toast.LENGTH_LONG).show();
						totTeam1Score+=1;
						ctBall1--;
					}
					else if(run == 8)
					{
						tvScore.setText("1");
						Toast.makeText(this,"Wide Ball 1 Run Scored",Toast.LENGTH_LONG).show();
						totTeam1Score+=1;
					}
					else if(run == 9)
					{
						tvScore.setText("4");
						Toast.makeText(this,"Leg Bye 4 Runs Scored",Toast.LENGTH_LONG).show();
						totTeam1Score+=4;
					}
					else if(run == 0)
					{
						tvScore.setText("0");
						Toast.makeText(this,"Player "+ctPlyr1+" of Team 1 is Out",Toast.LENGTH_LONG).show();
						ctPlyr1++;
					}
				}
				
				else if((ctBall2 < totBalls)&&(ctPlyr1 > totPlyr))
				{
					Toast.makeText(this,"Team 1 All Out",Toast.LENGTH_LONG).show();
					tvStatus.setText("Team 1 All Out");
					t1KO = 1;
					tmflag=1;
				}
				
				else if((ctBall2 >= totBalls)&&(ctPlyr2 < totPlyr))
				{
					Toast.makeText(this,"Team 1 Innings Over",Toast.LENGTH_LONG).show();
					tvStatus.setText("Team 1 Innings Over");
					t1KO = 1;
					tmflag=1;
				}
				int q = ctBall2/6;
				int r = ctBall2%6;
				int d = ctPlyr1-1;
				tvSBTeam1.setText("Runs :"+totTeam1Score+" Wickets :"+d+" Overs :"+q+"."+r);
			}
			
			
		}
		
	}
}
