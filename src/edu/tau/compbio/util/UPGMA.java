package edu.tau.compbio.util;

import java.util.Set;
import java.util.TreeSet;

public class UPGMA {
	
	private double[][] m_allDistances;
	private Set<Integer> m_clustersSet; 
	private int m_nextClusterToCreate;
	private int[][] m_clusterCreationOrder;
	private int[] m_clusterSizes;
	
	public UPGMA(double[][] distances)
	{
		int Nclusters = 2*distances.length-1;
		m_nextClusterToCreate = 0;
		m_allDistances = new double[Nclusters][Nclusters];
		storeInitialDistances(distances);
		m_clusterCreationOrder = new int[Nclusters][2];
		m_clusterSizes = new int[Nclusters];
				
		m_clustersSet = new TreeSet<Integer>();
		
		//add all pseu-clusters to clusterSet
		initializeClusterSet(distances.length);
		
		//until one cluster remains:
		while(m_clustersSet.size() > 1)
		{
			//take two most similar clusters and join them into a new cluster.
			//calculate distances to other clusters in set
			int[] newClus = joinNearestClusters();
			m_clustersSet.add(newClus[0]);
			
			//store pairing as a step in the final tree.
			m_clusterCreationOrder[newClus[0]][0] = newClus[1];
			m_clusterCreationOrder[newClus[0]][1] = newClus[2];
		}
				
	}

	private int[] joinNearestClusters() {
		int[] result = new int[3];
		result[0] = m_nextClusterToCreate++;
		double minDistanceFound = Double.POSITIVE_INFINITY;
		int aClus =0 ,bClus =0; 
		for (Integer i : m_clustersSet)
		{
			for( Integer j : m_clustersSet)
			{
				if(i.equals(j))
				{
					continue;
				}
				
				if( m_allDistances[i][j] < minDistanceFound )
				{
					minDistanceFound = m_allDistances[i][j];
					aClus = i;
					bClus = j;
				}
			}
		}
		
		result[1] = aClus;
		result[2] = bClus;
		m_clusterSizes[result[0]] = m_clusterSizes[aClus] + m_clusterSizes[bClus]; 
		
		
		m_clustersSet.remove(aClus);
		m_clustersSet.remove(bClus);
		
		for (Integer i : m_clustersSet)
		{
			m_allDistances[result[0]][i] = m_allDistances[aClus][i] * (((double)(m_clusterSizes[aClus])) / m_clusterSizes[result[0]]);
			m_allDistances[result[0]][i] += m_allDistances[bClus][i] * (((double)(m_clusterSizes[bClus])) / m_clusterSizes[result[0]]);
			m_allDistances[i][result[0]] = m_allDistances[aClus][i] * (((double)(m_clusterSizes[aClus])) / m_clusterSizes[result[0]]);
			m_allDistances[i][result[0]] += m_allDistances[bClus][i] * (((double)(m_clusterSizes[bClus])) / m_clusterSizes[result[0]]);
		}
		
		return result;
	}

	private void storeInitialDistances(double[][] distances) {
		for (int i=0; i<distances.length; i++)
		{
			m_allDistances[i][i] = 0;
			
			for (int j=i+1; j<distances.length; j++)
			{
				m_allDistances[i][j] = 1/distances[i][j];
				m_allDistances[j][i] = 1/distances[i][j];
			}
		}
		
	}

	private void initializeClusterSet(int length) {
		for(int i=0; i<length; i++)
		{
			m_clusterSizes[i] = 1;
			m_clustersSet.add(m_nextClusterToCreate++);
			m_clusterCreationOrder[i][0] = -1;
			m_clusterCreationOrder[i][1] = -1;
		}
		
	}
	
	public int[][] getClusterCreationOrder()
	{
		return m_clusterCreationOrder;
	}
}

