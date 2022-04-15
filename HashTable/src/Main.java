import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException {
	//Initialise the list with single list of random zipcodes 
	List<List<Integer>> zipCodesList1 = List.of(
			                List.of(6,8,14,17,15,16,20)
			            );
	
	//Initialise the list with lists(increasing in size) of random zipcodes for Experimental Analysis 
	List<List<Integer>> zipCodesList2 = List.of(
					                List.of(12,5),
					                List.of(4,3,1),
					                List.of(7,9,13,19,18),
					                List.of(21,22,23,11,2,10),
					                List.of(6,8,14,17,15,16,20)
					            );
	
	int operation = 0;
	
	
	HashMap<Integer, Integer> mapOfCrimeIncidentsForZipMap = fetchDataFromCsv(); // fetch data from the csv

	//Perform operations depending upon the input operation
	while(operation != 6) {
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("Enter the operation number you want to perform from the list below : "
				+ "\n1.\tInsert data into Hash tables and find zip code with max crime incidents"
				+ "\n2.\tExperimental Analysis STM and PHM - Insertion (put)"
				+ "\n3.\tExperimental Analysis STM and PHM - Get"
				+ "\n4.\tExperimental Analysis STM and PHM - Remove"
				+ "\n5.\tExperimental Analysis STM and PHM - Highest Crime Zone"
				+ "\n6.\tEnd\n");
		BufferedReader reader = new BufferedReader(
            new InputStreamReader(System.in));
		operation = Integer.parseInt(reader.readLine());
		
		switch(operation) {
		
		case 1: System.out.println("\n"+"**********************************************************************************************************************************************************");
			    System.out.println("\n"+"\t\t\t\tInsert data into Hash tables and find zip code with max crime incidents");
			    System.out.println("\n"+"**********************************************************************************************************************************************************");
			    hashTableOperations(1,zipCodesList1,1,mapOfCrimeIncidentsForZipMap);
			    break;
	    
		case 2: System.out.println("\n"+"**********************************************************************************************************************************************************");
		   	    System.out.println("\n"+"\t\t\t\tExperimental Analysis STM and PHM - Insertion (put)\t\t\t\t\t\t\t\t\t\t");
		        System.out.println("\n"+"**********************************************************************************************************************************************************");
		        hashTableOperations(5,zipCodesList2,2,mapOfCrimeIncidentsForZipMap);
		        break;
			   
		case 3: System.out.println("\n"+"**********************************************************************************************************************************************************");
   	    		System.out.println("\n"+"\t\t\t\tExperimental Analysis STM and PHM - Get\t\t\t\t\t\t\t\t\t\t");
   	    		System.out.println("\n"+"**********************************************************************************************************************************************************");
   	    		hashTableOperations(5,zipCodesList2,3,mapOfCrimeIncidentsForZipMap);
   	    		break;
		       
		case 4: System.out.println("\n"+"**********************************************************************************************************************************************************");
    			System.out.println("\n"+"\t\t\t\tExperimental Analysis STM and PHM - Remove\t\t\t\t\t\t\t\t\t\t");
    			System.out.println("\n"+"**********************************************************************************************************************************************************");
    			hashTableOperations(5,zipCodesList2,4,mapOfCrimeIncidentsForZipMap);
    			break;
			        	
		case 5: System.out.println("\n"+"**********************************************************************************************************************************************************");
				System.out.println("\n"+"\t\t\t\tExperimental Analysis STM and PHM - Find maximum\t\t\t\t\t\t\t\t\t\t");
				System.out.println("\n"+"**********************************************************************************************************************************************************");
				hashTableOperations(5,zipCodesList2,5,mapOfCrimeIncidentsForZipMap);
				break;
				
		case 6: System.out.println("Process Completed, System Exiting!");
			    System.exit(0); // Exit the system
		 	    break;
		
		default : System.out.println("Invalid operation");
				  break;
		}
		
	}
	
	}
	
	/*
	 * This method reads the csv file and creates a Map with Key = Zip code and Value = Count of crime incidents for the zipcode 
	 * @return mapOfCrimeIncidentsForZipMap //HashMap<Integer,Integer>
	 */
	private static HashMap<Integer, Integer> fetchDataFromCsv() {
		FileManager fileManager = new FileManager(); //Initialize the FileManager class
		HashMap<Integer,Integer> mapOfCrimeIncidentsForZipMap = new HashMap<>(); // initilize hashmap to store the data
		for(CrimeIncidents crime: fileManager.getCrimeData()) {
			//If map contains the zip code already, increment its count value by 1 or else put the map entry for the zipcode
			if(mapOfCrimeIncidentsForZipMap.containsKey(crime.getZipCode())) {
				mapOfCrimeIncidentsForZipMap.put(crime.getZipCode(),mapOfCrimeIncidentsForZipMap.get(crime.getZipCode())+1);
				
			}else {
				mapOfCrimeIncidentsForZipMap.put(crime.getZipCode(), 1);
				 
			}
		}
		
		return mapOfCrimeIncidentsForZipMap;
	}
	
	/**
	 * 
	 * @param numberOfIterations : 5 for experimental analysis else 1
	 * @param zipCodesList : List of list of zip codes that needs to be processed
	 * @param operation : Number of operation to be performed
	 * @param mapOfCrimeIncidentsForZipMap : the map of the raw csv which is processed, where key : zip code and value : count of
	 * 										 crime incidents of the zip code
	 */
	
	
	private static void hashTableOperations(int numberOfIterations, 
										   List<List<Integer>> zipCodesList,
		                                   int operation,
		                                   HashMap<Integer, Integer> mapOfCrimeIncidentsForZipMap) {
	
		//initial hashvalue
		int probeHashValue = 10;
		
		SortedTableMap<Integer,Integer> sortedTableMap = new SortedTableMap<>(); //initializing Sorted Table Map
		SortedTableMap<Integer,Integer> tempSortedTableMap = new SortedTableMap<>(); //initializing temp Sorted Table Map

		ProbeHashMap<Integer,Integer> probeHashMap = new ProbeHashMap<>(23);//initializing Probe hash map with capacity 23 as we have 23 zip codes to process
		ProbeHashMap<Integer,Integer> tempProbeHashMap = new ProbeHashMap<>(23);//initializing temp Probe hash map with capacity 23 as we have 23 zip codes to process

		//initialize Number of elements that are processed
		int processedElements = 0;
		//initialize max zip code value
		int zipCode = 0;
		//initialize max count of crime incidents
	    int maxCrimeIncidents = 0;
		
		HashMap<Integer,Integer> filteredZipCodeMap = new HashMap<>(); //Hashmap to store the filtered zip codes to be processed
		String temp = operation == 5? "Zip Code\t|":"";
		if(operation != 1)
			System.out.println("Number of element\t\t|\tSorted Hash Map (nano sec)\t|\t\tProbe Hash Map (nano sec)\t|\t"+temp);	
			
		for(int i = 0;i<zipCodesList.size();i++) {
			//initialize the time taken to insert data in sorted table map
			long timeToInsertInSTM = 0;
			//initialize the time taken to get data from sorted table map
			long timeToGetFromSTM = 0;
			//initialize the time taken to empty all data from sorted table map
			//long stmTimeToEmpty = 0;
			//initialize the time taken to insert data in sorted table map
			long timeToEmptySTM = 0;
			//initialize the time to find maximum data from sorted table map
			long timeToFindMaxInSTM = 0;
			
			//initialize the time taken to insert data in probe hash map
			long timeToInsertInPHM = 0;
			//initialize the time taken to get data from probe hash map
			long timeToGetFromPHM = 0;
			//long phmTimeToEmpty = 0;
			//initialize the time taken to empty all data from probe hash map
			long timeToEmptyPHM = 0;
			//initialize the time taken to find maximum data in probe hash map
			long timeToFindMaxInPHM = 0;
			
			//Filter the mapOfCrimeIncidentsForZipMap map to contain only the zip codes from the input zip codes list
			filteredZipCodeMap = filterMapForZipCode(mapOfCrimeIncidentsForZipMap,zipCodesList,i);
			//Experimental Analysis : Perform 5 iterations (of insertion) on the same data and calculate the average time taken to insert the record in sorted table map
			//Other cases : Only 1 iteration
			for(int j = 0; j<numberOfIterations;j++) {
				
				if(operation == 4) {
					//For removal operation, ensure the previous data is present sorted table map / probe hash map
					tempSortedTableMap.entrySet().forEach(
							entry->sortedTableMap.put(entry.getKey(), entry.getValue()));
					tempProbeHashMap.entrySet().forEach(
							entry->probeHashMap.bucketPut(probeHashValue,entry.getKey(), entry.getValue()));
				}
				
				//Start time to insert into Sorted table map
				long startTimeToInsertInSTM = System.nanoTime();
				
				filteredZipCodeMap.entrySet().forEach(entry->{
					//Insert entry into sorted table map where key = zipcode and value = count of crime incidents for the zip code
					sortedTableMap.put(entry.getKey(),entry.getValue());
					//Insert entry into a temporary sorted table map where key = zipcode and value = count of crime incidents for the zip code
					tempSortedTableMap.put(entry.getKey(), entry.getValue());
				});
				
				//End time to insert into Sorted table map
				long endTimeToInsertInSTM = System.nanoTime();
				
				//Start time to insert into probe hash map
				long startTimeToInsertInPHM = System.nanoTime();
				
				filteredZipCodeMap.entrySet().forEach(entry->{
					//Insert entry into probe hash map where key = zipcode and value = count of crime incidents for the zip code
					probeHashMap.bucketPut(probeHashValue, entry.getKey(), entry.getValue());
					//Insert entry into a temporary probe hash map where key = zipcode and value = count of crime incidents for the zip code
					tempProbeHashMap.bucketPut(probeHashValue, entry.getKey(), entry.getValue());
				});
				
				//End time to insert into probe hash map
				long endTimeToInsertInPHM = System.nanoTime();
				
				//Sum up time to insert in sorted table map after every iteration
				timeToInsertInSTM += endTimeToInsertInSTM - startTimeToInsertInSTM;	
				
				//Sum up time to insert in probe hash map after every iteration
				timeToInsertInPHM += endTimeToInsertInPHM - startTimeToInsertInPHM;
				
				//Count of number of elements being processed
				processedElements = sortedTableMap.size();
				
				
				if(operation == 3) {
					//Time to fetch the element from sorted table map when the number of elements in the map increases
					//Start time to fetch element from a sorted table map
					long startTimeToGetFromSTM = System.nanoTime();
					Entry<Integer,Integer> firstEntrySTM = sortedTableMap.firstEntry(); //fetch first entry from sorted table map
					//End time to fetch element from a sorted table map
					long endTimeToGetFromSTM = System.nanoTime();
					
					//Sum up time to fetch from  sorted table map for each iteration
					timeToGetFromSTM += endTimeToGetFromSTM - startTimeToGetFromSTM;
					
					//Time to fetch the element from probe hash map when the number of elements in the map increases
					//Start time to fetch element from a probe hash map
					long startTimeToGetFromPHM = System.nanoTime();
					int firstEntryPHM = probeHashMap.bucketGet(probeHashValue, zipCodesList.get(i).get(1)); // fetch element from probe hash map
					//End time to fetch element from a probe hash map
					long endTimeToGetFromPHM = System.nanoTime();
					//Sum up time to fetch from  probe hash map for each iteration
					timeToGetFromPHM += endTimeToGetFromPHM - startTimeToGetFromPHM;
				}
				
				//Experimental analysis for removing data from stored table map and probe hash map
				if(operation == 4) {
					//get time to empty stored table map
					timeToEmptySTM = timeToEmptySTM(sortedTableMap,timeToEmptySTM);
					//get time to empty probe hash map
					timeToEmptyPHM = timeToEmptyPHM(probeHashMap,timeToEmptyPHM);
				}
				
				
			    if(operation == 1 || operation == 5) {
			    	
			    	zipCode = 0;
		    		maxCrimeIncidents = 0;
		    		//Start time to find maximum element from a stored table map
		    		long startTimeToFindMaxInSTM = System.nanoTime();
		    		for (Entry<Integer,Integer> entry : sortedTableMap.entrySet()) {    // find max crime incidents from sorted table map
		  	  	      if (entry.getValue() > maxCrimeIncidents) {
		  	  	    	  zipCode = entry.getKey();
		  	  	    	  maxCrimeIncidents = entry.getValue();
		  	  	      }
		    		}
		    		//End time to find maximum element from a stored table map
		    		long endTimeToFindMaxInSTM = System.nanoTime();
		    		
		    		//Sum up time to find maximum element from stored table map
		    		timeToFindMaxInSTM += endTimeToFindMaxInSTM - startTimeToFindMaxInSTM;
		    		
			    	if(operation == 1) {
			    		
			    		//Display the zip code from stored table map with maximum crime incidents for operation 1
			    		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");

			    		System.out.println("Sorted Hash Map : Zip Code with Maximum Crime Incidents -> Zip Code : "+zipCode+" | Crime Incidents Count : "+maxCrimeIncidents);
			    	}
			    	    		
			    	
			    	zipCode = 0;
		    		maxCrimeIncidents = 0;
		    		//Start time to find maximum element from probe hash map
		    		long startTimeToFindMaxInPHM = System.nanoTime();
		    		for (Entry<Integer,Integer> entry : probeHashMap.entrySet()) {    // find max crime incidents from the probe hash map
		  	  	      if (entry.getValue() > maxCrimeIncidents) {
		  	  	    	  zipCode = entry.getKey();
		  	  	    	  maxCrimeIncidents = entry.getValue();
		  	  	      }
		    		}
		    		//End time to find maximum element from a probe hash map
		    		long endTimeToFindMaxInPHM = System.nanoTime();
		    		
		    		//Sum up time to find maximum element from probe hash map
		    		timeToFindMaxInPHM += endTimeToFindMaxInPHM - startTimeToFindMaxInPHM;
			    	
			    	if(operation == 1) {
			    		//Display the zip code from probe hash map with maximum crime incidents for operation 1
			    		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
				  		System.out.println("Probe Hash Map  : Zip Code with Maximum Crime Incidents -> Zip Code : "+zipCode+" | Crime Incidents Count : "+maxCrimeIncidents);
				  		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------");
				  		System.out.println("Number of element\t\t|\tSorted Hash Map (nano sec)\t|\t\tProbe Hash Map (nano sec)\t|");
			    	}
			    	
			    }	

			}
			
			if(operation == 1) {
				//Display time for insertion into sorted table map and probe hash map
				System.out.println("\t\t"+processedElements+"\t\t|\t\t"+(double)(timeToInsertInSTM/numberOfIterations)+"\t\t|\t\t"+(double)(timeToInsertInPHM/numberOfIterations)+"\t\t\t|");
			}
			if(operation == 2) {
				//Display time for experimental analysis of insertion into sorted table map and probe hash map
				System.out.println("\t\t"+processedElements+"\t\t|\t\t"+(double)(timeToInsertInSTM/numberOfIterations)+"\t\t\t|\t\t"+(double)(timeToInsertInPHM/numberOfIterations)+"\t\t\t\t|");
			}
			if(operation == 3) {
				//Display time for experimental analysis of fetching from sorted table map and probe hash map
				System.out.println("\t\t"+processedElements+"\t\t|\t\t"+(double)(timeToGetFromSTM/numberOfIterations)+"\t\t\t|\t\t"+(double)(timeToGetFromPHM/numberOfIterations)+"\t\t\t\t|");
			}
			if(operation == 4) {
				//Display time for experimental analysis of emptying all data from sorted table map and probe hash map
				System.out.println("\t\t"+processedElements+"\t\t|\t\t"+(double)(timeToEmptySTM/numberOfIterations)+"\t\t\t|\t\t"+(double)(timeToEmptyPHM/numberOfIterations)+"\t\t\t\t|");
			}
			if(operation == 5)
				//Display time for experimental analysis of finding maximum data from sorted table map and probe hash map
	    		System.out.println("\t\t"+processedElements+"\t\t|\t\t"+(double)(timeToFindMaxInSTM/numberOfIterations)+"\t\t\t|\t\t"+(double)(timeToFindMaxInPHM/numberOfIterations)+"\t\t\t\t|\t"+zipCode+"\t\t|");
	
		}  
	}


	/**
	 * 
	 * @param mapOfCrimeIncidentsForZipMap
	 * @param zipCodesList
	 * @param index
	 * @return filteredZipCodeMap which contains entry set for the input zip codes list
	 */
	private static HashMap<Integer,Integer> filterMapForZipCode(HashMap<Integer,Integer> mapOfCrimeIncidentsForZipMap, 
																List<List<Integer>> zipCodesList,
																int index){
		HashMap<Integer,Integer> filteredZipCodeMap = new HashMap<>();
		for(HashMap.Entry<Integer, Integer> entry : mapOfCrimeIncidentsForZipMap.entrySet()) {
			//Filter out the data for the zipcodes present in the zipcode list and store in a new map
			if(zipCodesList.get(index).contains(entry.getKey())) {
				filteredZipCodeMap.put(entry.getKey(), entry.getValue());
			}
		}
		
		return filteredZipCodeMap;
	}
	
	
	/**
	 * 
	 * @param sortedTableMap
	 * @param timeToRemoveFromSTM
	 * @return sum of time taken to empty the sorted table map for every iteration
	 */
	private static long timeToEmptySTM(SortedTableMap<Integer,Integer> sortedTableMap,long timeToRemoveFromSTM) {
		
		//Start time to remove all data from sorted table map
		long startTimeToRemoveFromSTM = System.nanoTime();
		while(!sortedTableMap.isEmpty()) {
			//while the sorted table map is not empty, keep removing entries from the map
			for(Entry<Integer,Integer> entry : sortedTableMap.entrySet()) {
				sortedTableMap.remove(entry.getKey());
			}
		}
		//End time to remove all data from sorted table map
		long endTimeToRemoveFromSTM = System.nanoTime();
		
		//Sum of the time taken to remove data from sorted table map for each iteration
		timeToRemoveFromSTM += endTimeToRemoveFromSTM - startTimeToRemoveFromSTM;

		return timeToRemoveFromSTM;
	}
	
	/**
	 * 
	 * @param probeHashMap
	 * @param timeToRemoveFromPHM
	 * @return sum of time taken to empty the probe hash map for every iteration
	 */
	private static long timeToEmptyPHM(ProbeHashMap<Integer,Integer> probeHashMap,long timeToRemoveFromPHM) {
		
		//Start time to remove all data from probe hash map
		long startTimeToRemoveFromPHM = System.nanoTime();
		while(!probeHashMap.isEmpty()) {
			//While the probe hash map is not empty, keep removing the entries from the map
			for(Entry<Integer,Integer> entry : probeHashMap.entrySet()) {
				probeHashMap.bucketRemove(10, entry.getKey());
			}
		}
		//End time to remove all data from probe hash map 
		long endTimeToRemoveFromPHM = System.nanoTime();
		//Sum time taken to remove data from probe hash map for each iteration
		timeToRemoveFromPHM += endTimeToRemoveFromPHM - startTimeToRemoveFromPHM;
		
		return timeToRemoveFromPHM;
	}

}	
