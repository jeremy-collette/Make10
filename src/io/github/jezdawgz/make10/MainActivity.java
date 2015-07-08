package io.github.jezdawgz.make10;

import com.fathzer.soft.javaluator.AbstractEvaluator;
import com.fathzer.soft.javaluator.DoubleEvaluator;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	//TODO FIX BUG WHEN REPEATED DIGITS
	

	private DoubleEvaluator evaluator = new DoubleEvaluator();
	private Button submit;
	private Button digit;
	private TextView prompt;
	private TextView input;
	private Challenge challenge;
	private static final int[] KEYPAD_BUTTONS = new int[]{R.id.buttonA, R.id.buttonDigit, R.id.buttonD, R.id.buttonE, R.id.buttonF, R.id.buttonG, R.id.buttonH, R.id.buttonI, R.id.buttonBack};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		prompt = (TextView)findViewById(R.id.textPrompt);
		input = (TextView)findViewById(R.id.textInput);
		
		digit = (Button)findViewById(R.id.buttonDigit);		
		submit = (Button)findViewById(R.id.submitButton);
		submit.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0) {
				submit();
				
			}		
		});
		
		
		for(int i : KEYPAD_BUTTONS)
		{
			OnClickListener click = new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					keypadClick((Button)v);
					
				}
			};
			((Button)findViewById(i)).setOnClickListener(click);
		}		
		
		setup();
		
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
	
	private void setup()
	{
		input.setText("");
		challenge = Challenge.generateRandomInstance();
		prompt.setText("Make 10 using the numbers "+challenge.toString()+"!");
		digit.setText(""+challenge.getCurrentNumber());
		digit.setEnabled(true);
		
	}
	
	private void keypadClick(Button b)
	{
		if (b.getId() == R.id.buttonBack)
		{
			CharSequence text = input.getText();
			if (text.length() > 0)
			{
				char toRemove = text.charAt(text.length()-1);
				if (!challenge.hasNextNumber() && toRemove == challenge.getCurrentNumber())
				{
					digit.setEnabled(true);
				}
				else if (challenge.hasPreviousNumber() && toRemove == challenge.peekPreviousNumber())
				{
					digit.setText(""+challenge.getPreviousNumber());
				}

				
				input.setText(text.subSequence(0, text.length()-1));
				
			}
		}
		else 
		{
			input.append(b.getText());
			if (b.getId() == R.id.buttonDigit)
			{
				if (challenge.hasNextNumber())
				{
					b.setText(""+challenge.getNextNumber());
				}
				else b.setEnabled(false);
			}
		}
	}
	
	private void submit()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		try
		{
			if (input.getText().toString().equals("+++"))
			{
				builder.setMessage("Solution: "+challenge.getSolution());
				builder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
					
						
						@Override
						public void onClick(DialogInterface dialog, int which) {}});
				
			}
			
			else if (evaluator.evaluate(input.getText().toString()) == 10)
			{
				builder.setMessage("Congratulations, you made 10!");
				builder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						setup();
						
					}});
				
			}
			else 
			{
				builder.setMessage("You did not make 10!");
				builder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {}});
			}

	
		}
		catch(IllegalArgumentException e)
		{
			builder.setMessage("Invalid input!");
			builder.setPositiveButton(R.string.ok_string, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {}});
		}
		
		
		
		builder.create().show();
	}
}
