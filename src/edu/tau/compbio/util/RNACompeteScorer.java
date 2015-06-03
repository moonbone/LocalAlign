package edu.tau.compbio.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class RNACompeteScorer {

	
	public RNACompeteScorer(File inputData, String proteinID) throws FileNotFoundException, IOException
	{
		BufferedReader lineScanner = new BufferedReader(new FileReader(inputData));
		String line = lineScanner.readLine();
		Scanner valuesScanner = new Scanner(line).useDelimiter("\\s+");
		Vector<String> titles = new Vector<String>(32);
		for(String title :() { valuesScanner })
		{
			titles.add(title);
		}
		while (null != (line = lineScanner.readLine()))
		{
			
		}
	}
	
	public void setRefSequence(String seq)
	{
		//TODO		
	}
	
	public double getScoreForSubsequence(String subSeq)
	{
		//TODO
		
		
		return 0;
	}
	
	
}
