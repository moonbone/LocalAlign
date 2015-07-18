package edu.tau.compbio.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Vector;

public class RNAWithPairingProbabilities {
	
	private String m_seq;
	private double[] m_probs;
	
	public RNAWithPairingProbabilities(String seq, double[] probs)
	{
		int i = seq.replaceAll("[ACGT]", "#").indexOf('#');
		int j = seq.replaceAll("[ACGT]", "#").lastIndexOf('#');
		m_seq = seq.substring(i, j+1);
		m_probs = Arrays.copyOfRange(probs,i,j+1);
		//m_probs = (Double[])Arrays.asList( probs ).subList(i,j).toArray();
		
	}
	
	public char sequenceAt(int i)
	{
		return m_seq.charAt(i);
	}
	
	public double probabilityAt(int i)
	{
		return m_probs[i];
	}
	
	public String getSequenceArray()
	{
		return m_seq;
	}
	
	public double[] getProbabilitiesArray()
	{
		return m_probs.clone();
	}
	
	public static Vector<RNAWithPairingProbabilities> readFromFiles(String sequencesFile, String probabilitiesFile)
	{
		return readFromFiles(sequencesFile, probabilitiesFile, false);
	}
	
	public static Vector<RNAWithPairingProbabilities> readFromFiles(String sequencesFile, String probabilitiesFile, boolean onlyCaps)
	{
		try
		{
			Vector<RNAWithPairingProbabilities> retVal = new Vector<RNAWithPairingProbabilities>();
			
			BufferedReader reader = new BufferedReader(new FileReader(sequencesFile));
			Scanner csv = new Scanner(new File(probabilitiesFile));//.useLocale(Locale.US);
			csv.useDelimiter("[,\\s]+");
			
			if(sequencesFile.endsWith(".fa"))
			{
				//add to suuport FASTA:
				String seq = null;
						
				String line = reader.readLine();
				while(line != null)
				{
					//FASTA support
					seq = "";
					while(line != null && (line=reader.readLine()) != null && !line.startsWith(">"))
					{
						seq += line.trim();
					}
					double[] probs = new double[seq.length()];
					for (int i=0;i<seq.length();i++)
					{
						//System.out.println(csv.next());
						probs[i] = csv.nextDouble();
					}
					
					RNAWithPairingProbabilities rna = new RNAWithPairingProbabilities(onlyCaps ? seq : seq.toUpperCase(), probs);
					retVal.add(rna);
					
				}
			}
			else
			{
				String line = reader.readLine();
				while(line != null)
				{
					double[] probs = new double[line.length()];
					for (int i=0;i<line.length();i++)
					{
						//System.out.println(csv.next());
						probs[i] = csv.nextDouble();
					}
					
					RNAWithPairingProbabilities rna = new RNAWithPairingProbabilities(onlyCaps ? line : line.toUpperCase(), probs);
					retVal.add(rna);
					
					line = reader.readLine();
				}
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
