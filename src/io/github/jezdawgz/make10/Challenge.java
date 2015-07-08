package io.github.jezdawgz.make10;

import java.util.Random;
import java.util.logging.Logger;

import android.util.Log;

import com.fathzer.soft.javaluator.DoubleEvaluator;

public class Challenge {
	
	private static final Character[] OPERATORS = new Character[]{'+','-','*','/','^'};
	private static DoubleEvaluator evaluator = new DoubleEvaluator();
	private static Random r = new Random();
	
	
	private static Character getRandomSign()
	{
		if (r.nextInt(2) == 0) return '-';
		else return '+';
	}
	
	private static Character getRandomOperator()
	{
		return OPERATORS[r.nextInt(OPERATORS.length)];		
	}
	
	private static Character getDivisionOrMultiplicationOperator()
	{
		if (r.nextInt(2) == 0) return '*';
		else return '/';
	}
	
	private static Character getRandomDigit()
	{
		return Character.forDigit(r.nextInt(10),10);
	}
	
	private static Character getRandomPositiveDigit()
	{
		return Character.forDigit(r.nextInt(9)+1,10);
	}
	
	public static Challenge generateRandomInstance()
	{
		//TODO implement better algorithm
		
		char a = ' ';
		char b = ' ';
		char c = ' ';
		char d = ' ';
		
		String testString = "0";
		while (evaluator.evaluate(testString.toString()) != 10)
		{
			a = getRandomPositiveDigit();
			b = getRandomPositiveDigit();
			c = getRandomPositiveDigit();
			d = getRandomPositiveDigit();
			//testString = a+" "+getRandomOperator()+" "+b+" "+getRandomOperator()+" "+c+" "+getRandomOperator()+" "+d;		//too boring - need more multiplication and division
			testString = a+" "+getRandomOperator()+" "+b+" "+getDivisionOrMultiplicationOperator()+" "+c+" "+getRandomOperator()+" "+d;
			
			Log.i("Debug","IN-LOOP EVAL: "+testString);
		}
		
		Log.i("Debug","ANSWER: "+testString);
		return new Challenge(a,b,c,d, testString);
		
		   
		
	}
	
	private char a, b, c, d;
	private int index = 0;
	private char[] numbers;
	private String solution;
	
	public Challenge(char a, char b, char c, char d, String solution)
	{		
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		numbers = new char[]{a,b,c,d};
		this.solution = solution;
	}
	
	public String getSolution()
	{
		return solution;
	}
	
	public char[] getNumbers()
	{
		return numbers;
	}
	
	public boolean hasNextNumber()
	{
		if (index < numbers.length-1) return true;
		else return false;
	}
	
	public boolean hasPreviousNumber()
	{
		if (index > 0) return true;
		else return false;
	}
	
	public char getNextNumber()
	{
		return numbers[++index];
	}
	
	public char getPreviousNumber()
	{
		return numbers[--index];
	}
	
	public char peekNextNumber()
	{
		return numbers[index+1];
	}
	
	public char peekPreviousNumber()
	{
		return numbers[index-1];
	}
	
	public char getCurrentNumber()
	{
		return numbers[index];
	}
	
	public String toString()
	{
		return a+", "+b+", "+c+", "+d;
	}

	
	
}
