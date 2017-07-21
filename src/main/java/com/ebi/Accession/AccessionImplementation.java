package com.ebi.Accession;


import org.springframework.stereotype.Service;
import com.ebi.Accession.Interface.AccessionInterface;
import com.ebi.Accession.Model.AccessionBulkNumbers;

import static java.util.stream.Collectors.groupingBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class AccessionImplementation implements AccessionInterface {

	/* This method handles implementation of ordering of Accession numbers*/
	@Override
    public AccessionBulkNumbers bulkAccessionNumbers(final String str) {
		
    	List<String> accessionList = new ArrayList<>();
	    List<String> result = new ArrayList<>();
	    Map<String, String> map = new HashMap<String, String>();
	  
	    //Trim the input string and use JAVA8 Streams to create String List Stream
	    //Throw exception for "" or null input string
	    
	    if (null != str && !(str.trim()).isEmpty()) {
	        accessionList = Arrays.stream(str.split(",")).distinct().map(String::trim).sorted().collect(Collectors.toList());
	        System.out.println("Input List :" + accessionList);
	    } else {
	        throw new IllegalStateException("Invalid input values");
	    }
	    
	    //Create a map from input stream to group the data based on first part of individual accession string
	    // mapGroup value eg. : {A=[A00000, A0001], SRR=[SRR211001], ERR=[ERR000111, ERR000112, ERR000113] DRR=[DRR2110012]}
	    
	    Map<String, List<String>> mapGroup =
	    		accessionList.stream().sorted().distinct()
	        	           .collect(groupingBy(i -> i.split("(?<=\\D)(?=\\d)")[0]));
	    
	    System.out.println(mapGroup);
	    
	    //Create another map from above grouped map to further remove the first part from the second part of individual accession string
	    // map value eg. : {ABCDEFG=1 , A=00000 0001 , SRR=211001 , ERR=000111 000112 000113 , DRR=2110012
	    
	    try {
		    mapGroup.entrySet().forEach(entry -> {
		    	String aList = "";
		    	for(String s:entry.getValue()) {
		    		aList +=s.split("(?<=\\D)(?=\\d)")[1]+" ";   
		        }
		        map.put(entry.getKey(), aList);           
		    });
	    } catch (ArrayIndexOutOfBoundsException excep) {
	    	System.out.println("Wrong Input like : Empty string in middle");
       	    throw new ArrayIndexOutOfBoundsException("Empty string in middle. " + excep);	
	    }
	    
	    //Sort the above map using TreeMap
	    Map<String, String> treeMap = new TreeMap<String, String>(map);
	    
	    //Using Stack data structure to store and consecutive data inputs and popping it out when next input is not in order 
	    //Can also add parallet stream concept on entrySet() of treeMap, which will help in faster processing
	    //  treeMap.entrySet()
	    //    .parallelStream()
	    //    .forEach(entry -> {
	    //		rest of the code.
	    
	    treeMap.entrySet().forEach(entry -> {   	
        	String val = entry.getValue();
        	String[] valArray = val.split(" ");
        	String lastStr ="";
 	        String firstStr="";
 	        Stack<String> stack = new Stack<String>();
 	        
 	        //Push first input into Stack
 	        stack.push(valArray[0]);
			
			for(int i=1;i<=valArray.length-1;i++) {
				//Check if difference between digit part of first input and second input is 1 and both are of same size, if true : push the data into stack
				if((Integer.parseInt(valArray[i])-Integer.parseInt(valArray[i-1]) == 1) && valArray[i].length()==valArray[i-1].length()) {
					stack.push(valArray[i]);
				}
				else{
				//Otherwise check if stack is empty, if not pop the top most data
				 if(!stack.empty()) {
					lastStr = stack.pop();
					//Keep popping stack data to get the initial string of consecutive inputs 
					if(!stack.empty()) {
						while(!stack.empty()) 
						  firstStr=stack.pop();
					//Add the output to result list and push the current data into stack 
						result.add(entry.getKey()+firstStr+"-"+entry.getKey()+lastStr);
				        stack.push(valArray[i]);
					}
					//if stack gets emptied, add last popped data to result list and push the current data into stack
					else{
						result.add(entry.getKey()+lastStr);
						stack.push(valArray[i]);
					}
				 }
				}
			}
			//Check again if stack is empty, if not pop and add data to result list
			if(!stack.empty()) {
				lastStr = stack.pop();
				if(!stack.empty()) {
					while(!stack.empty()) 
					firstStr=stack.pop();
			        result.add(entry.getKey()+firstStr+"-"+entry.getKey()+lastStr);
				}
				else{
					result.add(entry.getKey()+lastStr);
				}
			}
			
        });
	    System.out.println(result);	
	    
		AccessionBulkNumbers anum = new AccessionBulkNumbers();
	    anum.setbulkAccession(result);
        return anum;
    }
}
