package edu.tau.compbio.util;

public class MultipleSequenceAndProbabilities {

	public String[] sequences;
	public double[] probabilities;
	public String[] ids;
	
	public MultipleSequenceAndProbabilities(String[] seqs, double[] probs,String[] ids)
	{
		sequences = seqs;
		probabilities = probs;
		this.ids = ids;
	}
	
}
