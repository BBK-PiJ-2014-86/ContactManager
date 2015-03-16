package ContactManager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestContactManager {

	private ContactManager testContactManager;
	private Set <Contact> contacts;
	private Calendar date;
	

	/**
	 * Initialising fields in Before to implement before starting 
	 */
	@Before
	public void buildUp () {
		testContactManager = new ContactManagerImpl();
		contacts = new HashSet();
		date = Calendar.getInstance();
	
		
	}
	
	/**
	 * This test ensures that IllegalArgumentException is thrown if the contact in parameter passed to addFurureMeeting method is unknown.
	 */
	
	@Test (expected= IllegalArgumentException.class)
	
	public void addFutureMeetingTestUnknownContactError () {
		
		testContactManager.addNewContact("Marcus Eoin", "Interview");
		contacts.add(new ContactImpl (1, "Michael Sandison"));
		date.set(2015, Calendar.JUNE, 9);
		testContactManager.addFutureMeeting(contacts, date);
	}
	
	/**
	 * This test checks if IllegalArgumentException is thrown if the date passed to addFurureMeeting method is not in the future.
	 */
	
	@Test (expected= IllegalArgumentException.class)
	
	public void addFutureMeetingTestPastDateError () {
		
		testContactManager.addNewContact("Marcus Eoin", "Interview");
		contacts.add(new ContactImpl (1, "Marcus Eoin"));
		date.set(2014, Calendar.JUNE, 9);
		testContactManager.addFutureMeeting(contacts, date);
	}
	
	/**
	 * This test checks the normal case. 
	 */
	
	@Test
	
	public void addFutureMeetingTest () {
		
		testContactManager.addNewContact("Marcus Eoin", "Interview");
		contacts.add(new ContactImpl (1, "Marcus Eoin"));
		int id = testContactManager.addFutureMeeting(contacts, date);
		assertEquals(testContactManager.getFutureMeeting(id).getId(),id);
	}
	
	
	/**
	 * This test ensures that IllegalArgumentException is thrown if the Set passed as a parameter is null.
	 */
	
	@Test (expected= IllegalArgumentException.class)
	
	public void addFutureMeetingTestUnknownContactNull () {
		
		testContactManager.addNewContact("Marcus Eoin", "Interview");
		date.set(2015, Calendar.JUNE, 9);
		testContactManager.addFutureMeeting(contacts, date);
	}
	
	@Test 
	
	public void getPastMeetingNormalCase () {
		
		testContactManager.
		
		
	}
	
	

}
