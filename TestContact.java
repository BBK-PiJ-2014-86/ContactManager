package ContactManager;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestContact {

	
	/**
	 * <b> Test Method </b>: int getId();
	 * <br>
	 * <b> Test Scope </b>: This test tests if method returns id
	 */
	
	@Test
	public void getIdNormalCaseTest() {

		final int ID = 1;
		ContactImpl contact = new ContactImpl(ID,"");
		
		assertEquals (ID,contact.getId());
	}
	
	/**
	 * <b> Test Method </b>: String getName();
	 * <br>
	 * <b> Test Scope </b>: This test tests if method returns name
	 */
	
	@Test
	public void getNameNormalCaseTest () {
		
		final int ID = 1;
		final String NAME = "TP";
		
		ContactImpl contact = new ContactImpl (ID,NAME);
		
		assertEquals(NAME,contact.getName());
		
	}
	
	/**
	 * <b> Test Method </b>: String getNotes() and void addNotes(String note);
	 * <br>
	 * <b> Test Scope </b>: This test tests if methods add and get notes to contact
	 */
	
	@Test

	public void addAndGetNotesNormalCaseTest () {
		
		final int ID = 1;
		final String NAME = "TP";
		final String NOTES = "I HAVE ARRIVED";
		
		ContactImpl contact = new ContactImpl (ID,NAME);
		
		contact.addNotes(NOTES);
		
		assertEquals (NOTES,contact.getNotes());
	}
}
