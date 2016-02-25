
public class KnapSack {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] values = {3, 7, 2, 9};
		int[] weights = {2, 2, 4, 5};
		int weightLimit = 10;
		int sol1 = DPMaxBenefit(weightLimit, values, weights);
		System.out.println("DP solution: " + sol1);
		int sol2 = findMaxBenefit(weightLimit, 0, values, weights);
		System.out.println("Recursive solution: " + sol2);
		
		int[] values2 = {4, 2, 10, 1, 2};
		int[] weights2 = {12, 1, 4, 1, 2};
		weightLimit = 15;
		int sol3 = unboundedSack(values2, weights2, weightLimit);
		System.out.println("Unbounded solution: " + sol3);
		

	}
	
	//DP
	//Table row labels: represent analysis UP TO FIRST n items. Either include or exclude
	//vs. unbounded: considered by NUMBER of items (so can include anything at each point)
	public static int DPMaxBenefit(int limit, int[] values, int[] weights)
	{
		int[][] table = new int[weights.length+1][limit + 1];
			
		for(int r=1; r<weights.length+1; r++) //row major order
			for(int c=1; c<limit+1; c++){ //c represents weight limits
				
				//can't include the item if its weight is greater than the limit
				if(weights[r-1]>c){ //c=1 represents the first element, so index 1 less than c
					table[r][c] = table[r-1][c];
				}
				else if(values[r-1] + table[r-1][c-weights[r-1]] > table[r-1][c]) //including > excluding
					table[r][c] = values[r-1] + table[r-1][c-weights[r-1]];
				else 
					table[r][c] = table[r-1][c];			
				
			}
		//printTable(table);
		return table[weights.length][limit];
	}
	
	public static void printTable(int[][] table)
	{
		for(int[] i : table)
		{
			for(int j: i)
				System.out.print(j + " ");
			System.out.println();
		}
		
	}
	
	//recursive
	public static int findMaxBenefit(int limit, int index, int[] values, int[] weights)
	{
		if(limit == 0 || index == weights.length) return 0; //base cases
		
		//if the item's weight is greater than weight limit then the item
		//cannot be included in the knapsack
		if(weights[index] > limit) 
			return findMaxBenefit(limit, index+1, values, weights);
		
		//max benefit from including current item
		int maxIncludeBenefit = values[index] + findMaxBenefit(limit-weights[index], index+1, values, weights);
		
		//max benefit from excluding current item
		int maxExcludeBenefit = findMaxBenefit(limit, index+1, values, weights);
		
		return Math.max(maxIncludeBenefit, maxExcludeBenefit);
	}
	
	public static int unboundedSack(int[] values, int[] weights, int limit)
	{
		int[] benefit = new int[limit+1]; 
		benefit[0] = 0;
		for(int i=1; i<limit+1; i++){ //examining weights
			int max = -1;
			for(int j=0; j<weights.length; j++) //examining items
				if(weights[j] <= i)
					max = Math.max(max, values[j] + benefit[i - weights[j]]);
			benefit[i] = max;
		}
		return benefit[limit];
	}
}
