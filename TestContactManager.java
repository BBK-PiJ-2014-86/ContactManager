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
		
		try {
			
		testContactManager.addFutureMeeting(contacts, date);
		
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This test checks if IllegalArgumentException is thrown if the date passed to addFurureMeeting method is not in the future.
	 */
	
	@Test (expected= IllegalArgumentException.class)
	
	public void addFutureMeetingTestPastDateError () {
		
		testContactManager.addNewContact("Marcus Eoin", "Interview");
		contacts.add(new ContactImpl (1, "Marcus Eoin"));
		date.set(2014, Calendar.JUNE, 9);
		try {
		testContactManager.addFutureMeeting(contacts, date);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
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
		
		try {		
		testContactManager.addFutureMeeting(contacts, date);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This test tests the normal case of the getPastMeeting method
	 */
	
	@Test 
	
	public void getPastMeetingNormalCase () {
		
		final int id = 1;
		
		date.set(2014, Calendar.JUNE,9);
		PastMeeting pastMeeting = new PastMeetingImpl (id,date,new HashSet ());
		ContactManagerImpl contactManager = new ContactManagerImpl ();
		contactManager.addNewPastMeeting(pastMeeting);
		
		assertEquals(pastMeeting, contactManager.getPastMeeting(id));
		
	}
	
	/**
	 * This test tests if the method returns null if id is not found.
	 */
	
	@Test 
	
	public void getPastMeetingNull () {
		
		final int id = 4;
		assertEquals (testContactManager.getPastMeeting(4), null); // there isn't a PastMeeting with id = 4 so this must return null.
		
	}
	
	/**
	 * This test tests if the method returns IllegalArgumentException if the id belongs to a meeting happening in the future;
	 */
	
	@Test (expected = IllegalArgumentException.class)
	public void getPastMeetingException () {
		
		final int id = 1;
		date.set(2014, Calendar.APRIL, 15);
		
		FutureMeeting futureMeeting = new FutureMeetingImpl (id,date, new HashSet ()); 
		
		try{
		testContactManager.getPastMeeting(id);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This test tests the normal case of the getPastMeeting method
	 */
	
	@Test 
	
	public void getFutureMeetingNormalCase () {
		
		final int id = 1;
		
		date.set(2015, Calendar.JUNE,9);
		FutureMeeting futureMeeting = new FutureMeetingImpl (id,date,new HashSet ());
		ContactManagerImpl contactManager = new ContactManagerImpl ();
		contactManager.addNewFutureMeeting(futureMeeting);
		
		assertEquals(futureMeeting, contactManager.getPastMeeting(id));
		
	}
	
	/**
	 * This test tests if the method returns null if id is not found.
	 */
	
	@Test 
	
	public void getFutureMeetingNull () {
		
		final int id = 4;
		assertEquals (testContactManager.getFutureMeeting(4), null); // there isn't a PastMeeting with id = 4 so this must return null.
		
	}
	
	/**
	 * This test tests if the method returns IllegalArgumentException if the id belongs to a meeting happening in the future;
	 */
	
	@Test (expected = IllegalArgumentException.class)
	public void getFutureMeetingException () {
		
		final int id = 1;
		date.set(2015, Calendar.APRIL, 15);
		
		FutureMeeting futureMeeting = new FutureMeetingImpl (id,date, new HashSet ()); 
		
		try{
		testContactManager.getFutureMeeting(id);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

}
