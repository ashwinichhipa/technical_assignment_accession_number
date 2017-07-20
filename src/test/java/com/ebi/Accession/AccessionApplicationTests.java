package com.ebi.Accession;

import org.junit.Assert;
import org.junit.Test;

import com.ebi.Accession.Model.AccessionBulkNumbers;


public class AccessionApplicationTests {

		//Test1 : Check for single input
		@Test
	    public void singleInputTest() {
	        AccessionImplementation implm = new AccessionImplementation();
	       String input = "A00000"; 
	       AccessionBulkNumbers response =implm.bulkAccessionNumbers(input);
	        String expected = "A00000";
	        Assert.assertEquals(expected,response.toString());
	    }
		
		//Test2 : Check for multiple inputs
		@Test
	    public void multiInputTest() {
	        AccessionImplementation implm = new AccessionImplementation();
	
	       String input = "A00000, A0001, ERR000111, ERR000112, ERR000113, ERR000115, ERR000116, ERR100114, " +
	                "ERR200000001, ERR200000002, ERR200000003, DRR2110012, SRR211001, ABCDEFG1"; 
	       AccessionBulkNumbers response =implm.bulkAccessionNumbers(input);
	        String expected = "A00000, A0001, ABCDEFG1, DRR2110012, ERR000111-ERR000113, ERR000115-ERR000116, ERR100114, ERR200000001-ERR200000003, SRR211001";
	        Assert.assertEquals(expected,response.toString());
	    }
		
		//Test3 : Check for empty input
        @Test(expected = IllegalStateException.class)
		public void emptyInputTest() {
        	AccessionImplementation implm = new AccessionImplementation();
		    String input = "";
		    AccessionBulkNumbers response =implm.bulkAccessionNumbers(input);
		    String expected = "A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003";
		    Assert.assertEquals(expected,response.toString());
		}

        //Test4: Check for empty string in middle
        @Test(expected = ArrayIndexOutOfBoundsException.class)
		public void emptyStringMiddleTest() {
        	AccessionImplementation implm = new AccessionImplementation();
		    String input = "A00000, , ABCDEFG1";
		    AccessionBulkNumbers response =implm.bulkAccessionNumbers(input);
		    String expected = "A00000,A0001,ABCDEFG1";
		    Assert.assertEquals(expected,response.toString());
		}
        
        //Test5: Check for duplicate number handling
	    @Test
	    public void duplicateInputTest() {
	        AccessionImplementation implm = new AccessionImplementation();
	        String input = "A00000, A0001, A0001";
	        AccessionBulkNumbers response =implm.bulkAccessionNumbers(input);
	        Assert.assertEquals(2,response.getbulkAccession().size());
	        Assert.assertEquals("A00000",response.getbulkAccession().get(0));
	        Assert.assertEquals("A0001",response.getbulkAccession().get(1));
	    }

	    //Test6: Check for null input
	    @Test(expected = IllegalStateException.class)
	    public void nullInputTest() {
	        AccessionImplementation implm = new AccessionImplementation();
	        String input = null;
	        AccessionBulkNumbers response =implm.bulkAccessionNumbers(input);
	        String expected = "A00000,A0001,ABCDEFG1,DRR2110012,ERR000111-ERR000113,ERR000115-ERR000116,ERR100114,ERR200000001-ERR200000003";
	        Assert.assertEquals(expected,response.toString());
	    }
	    
}
