import java.lang.Math;
import java.util.ArrayList;
import java.util.*;

/* 
 * This code was written by Sanad Alwerfali, student # 201857760
 * Nature Inspired Computing
 */
public class Queens
{
    private static int boardSize = 12;
    
    // creates a valid genotype with random values
    public static Integer [] createGeno()
    { 
        Integer [] genotype = new Integer [boardSize];
        
        // initializing with the required size and range of numbers
        genotype = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
        
        // changing the array to arraylist to ease the process of shuffling the numbers to produce random array every time
        List<Integer> intList = Arrays.asList(genotype);
        
        // shuffle the arraylist
		Collections.shuffle(intList);
		
		//change it back to array 
		intList.toArray(genotype);
 
        return genotype;
    }
    
    // selects a random index of a given array
    private static Integer pickRandomElement (Integer[] array) {
		
    	Random rand = new Random();
    	int limit = array.length;
    	int index = rand.nextInt(limit);
    	return index;
    	
    }
    
    // helper function to find the probability of mutation for a given rate 
    private static boolean probabilityMaker (int rate) {
    	Integer [] probabilityMaker = new Integer[] {0,0,0,0,0,0,0,0,0,0,0,0};
    	int index; 
    	for (int i = 0; i < rate; i++) {
    		probabilityMaker[i] = 1;
    	}
    	
    	index = pickRandomElement(probabilityMaker);
    	if (probabilityMaker[index] == 0) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }
    
    // move a gene in the genotype
    // the move happens with probability p, so if p = 0.8
    // then 8 out of 10 times this method is called, a move happens
    public static Integer[] insertionMutate(Integer[] genotype, double p)
    {
    	int rate = (int) (p * 10);
    	int Mindex;
    	int[] temp = new int [genotype.length];
    	
    	if (probabilityMaker(rate) == false) {
    		return genotype;
    	}
    	
    	int M = pickRandomElement(genotype);
    	int N = pickRandomElement(genotype);
    	
    	while (M >= N) {
    		M = pickRandomElement(genotype);
    		N = pickRandomElement(genotype);
    	}

    	// copying elements from the gene to a temporary array
    	for (int i = 0; i < genotype.length; i++) {
    		temp[i] = genotype[i];
    	}
    	
    	// insertion mutate process  
    	for (int i = N - 1; i > M; i--) {
    		Mindex = temp[i + 1];
    		temp[i+1] = temp[i];
    		temp[i] = Mindex;
    	}
    	
    	// copying the results back to gene array
    	for (int i = 0; i < genotype.length; i++) {
    		genotype[i] = temp[i];
    	}

    	return genotype;
    }
    
    // creates 2 child genotypes using the 'cut-and-crossfill' method
    public static Integer[][] crossover(Integer[] parent0, Integer[] parent1)
    {
        int slice = (parent0.length)/2;
    	Integer [] child0 = new Integer [parent0.length];
    	Integer [] child1 = new Integer [parent1.length];
    	ArrayList <Integer> temp0 = new ArrayList <Integer> ();
    	ArrayList <Integer> temp1 = new ArrayList <Integer> ();
    	
    	for (int i = 0; i < slice; i++) {
    		child0[i] = parent0[i];
    	}
    	
    	for (int i = 0; i < slice; i++) {
    		child1[i] = parent1[i];
    	}
    	
    	
    	// crossover process for the first child
    	for (int i = slice; i < parent1.length+slice; i++) {
    		int counter = 0;
    		for (int j = 0; j < slice; j++) {
    			if (parent1[i%parent1.length] == child0[j]) {
    				counter = counter + 1;
    			}
    		}
    		if (counter == 1) {
    			counter = 0;
    			continue;
    		}
    		else {
    			temp0.add(parent1[i%parent1.length]);
    		}
    	}
    	
    	// crossover process for the second child
    	for (int i = slice; i < parent0.length+slice; i++) {
    		int counter = 0;
    		for (int j = 0; j < slice; j++) {
    			if (parent0[i%parent0.length] == child1[j]) {
    				counter = counter + 1;
    			}
    		}
    		if (counter == 1) {
    			counter = 0;
    			continue;
    		}
    		else {
    			temp1.add(parent0[i%parent0.length]);
    		}
    	}
    	
    	for (int i = slice ; i < child0.length; i++) {
    		child0[i] = temp0.get(i-slice);
    	}
    	
    	for (int i = slice ; i < child1.length; i++) {
    		child1[i] = temp1.get(i-slice);
    	}
    	
    	Integer [][] children = new Integer [2][boardSize];
        
        children[0] = child0;
        children[1] = child1;
        
        return children;
    }
    

    // calculates the fitness of an individual
    public static int fitness(Integer [] genotype)
    {
    	
    	int totalFitness = 66;
    	int queenCheck = 0;
    	
    	//checking the fitness of the gene
    	for (int i=0; i < genotype.length;  i++) {
    		for (int j=0; j < genotype.length ;  j++) {
    			if ( i != j) 
    			{
    				int x = Math.abs(i-j);
    				int y = Math.abs(genotype[i] - genotype[j]);

    				if(x == y) {
    					queenCheck += 1;
    				}
    			}
    		} 
    	}

    	Arrays.sort(genotype);
    	
    	int counter = 1;
    	
    	for (int i = 1; i < genotype.length; i++) {
    		if (genotype[i-1] != genotype[i]) {
    			counter = counter + 1;
    		}
    	}
    
    	int boardCheck = genotype.length - counter;
    	int currentFitness = boardCheck/2 + queenCheck/2;

    	int fitness = totalFitness - (currentFitness);

    	return fitness;
    	
    }
}
