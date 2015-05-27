package edu.tau.compbio.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

public class RNAWithPairingProbabilities {
	
	private String m_seq;
	private double[] m_probs;
	
	public RNAWithPairingProbabilities(String seq, double[] probs)
	{
	
		m_seq = seq;
		m_probs = probs.clone();
		
	}
	
	public char sequenceAt(int i)
	{
		return m_seq.charAt(i);
	}
	
	public double probabilityAt(int i)
	{
		return m_probs[i];
	}
	
	public char[] getSequenceArray()
	{
		return m_seq.toCharArray();
	}
	
	public double[] getProbabilitiesArray()
	{
		return m_probs.clone();
	}
	
	public static Vector<RNAWithPairingProbabilities> readFromFiles(String sequencesFile, String probabilitiesFile)
	{
		try
		{
			Vector<RNAWithPairingProbabilities> retVal = new Vector<RNAWithPairingProbabilities>();
			
			BufferedReader reader = new BufferedReader(new FileReader(sequencesFile));
			Scanner csv = new Scanner(probabilitiesFile);
			csv.useDelimiter("[,\\s]");
			
			String line = reader.readLine();
			while(line != null)
			{
				double[] probs = new double[line.length()];
				for (int i=0;i<line.length();i++)
				{
					probs[i] = csv.nextDouble();
				}
				
				RNAWithPairingProbabilities rna = new RNAWithPairingProbabilities(line.toUpperCase(), probs);
				retVal.add(rna);
				
				line = reader.readLine();
			}
			
					
			return retVal;
		}
		catch (FileNotFoundException e)
		{
			System.err.println("One of the files is missing. check paths and try again");
		}
		catch (IOException e)
		{
			System.err.println("error reading file.");
		}
		return null;
	}
	
}
