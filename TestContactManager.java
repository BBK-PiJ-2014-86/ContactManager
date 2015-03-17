package ContactManager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
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
		PastMeeting pastMeeting = new PastMeetingImpl (id,date,contacts);
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
		
		FutureMeeting futureMeeting = new FutureMeetingImpl (id,date, contacts); 
		
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
		FutureMeeting futureMeeting = new FutureMeetingImpl (id,date,contacts);
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
		
		FutureMeeting futureMeeting = new FutureMeetingImpl (id,date, contacts); 
		
		try{
		testContactManager.getFutureMeeting(id);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This test tests the normal case.
	 */
	
	@Test
	
	public void getMeetingNormal () {
		
		final int id = 1;
		date.set(2014, Calendar.JUNE,9);
		ContactManagerImpl contactManager = new ContactManagerImpl(); 
		PastMeetingImpl pastMeeting = new PastMeetingImpl (id,date,contacts);
		
		contactManager.addNewPastMeeting(pastMeeting);
		
		assertEquals(pastMeeting,contactManager.getMeeting(id));
		
	}
	
	/**
	 * This test tests invalid id. According to interface, it has to return null.
	 */
	
	@Test
	
	public void getMeetingInvalidId () {
		
		assertEquals(testContactManager.getMeeting(-1), null);
		
	}
	
	/**
	 * This test tests the normal case of getFutureMeetingList () method which returns list of future meetings scheduled with this contact.
	 */
	
	@Test
	
	public void getFutureMeetingListNormal () {
		
		final Calendar date1 = Calendar.getInstance(); date1.set(2015, Calendar.AUGUST, 3); // 3rd August 2015
		final Calendar date2 = Calendar.getInstance(); date2.set(2015,Calendar.JULY,19); // 19th July 2015
		final Calendar date3 = Calendar.getInstance(); date3.set(2015, Calendar.DECEMBER,24); //24th December 2015
		final int meetingID1 = 1;
		final int meetingID2 = 2;
		final int meetingID3 = 3;
		
		//create a Contact that I will look for and put the contact in a Set implementation of a hashset.
		
		final String name = "Michael Eoin";
		final Contact contact = new ContactImpl (1,name);
		final Set <Contact> contactList = new HashSet(); 
		contactList.add(contact);
		
		// Create 3 future Meetings with created Contact Set
		
		final FutureMeeting fMeeting1 = new FutureMeetingImpl (meetingID1, date1, contactList);
		final FutureMeeting fMeeting2 = new FutureMeetingImpl (meetingID2, date2, contactList);
		final FutureMeeting fMeeting3 = new FutureMeetingImpl (meetingID3, date3, contactList);
		
		// Create ArrayList that will store the Meetings in chronological order.
		
		final List <Meeting> meetingList = new ArrayList <Meeting> ();
		meetingList.add(0, fMeeting2);
		meetingList.add(1,fMeeting1);
		meetingList.add(2,fMeeting3);
		
		// Add the FutureMeetings objects to the ContactManager
		
		final ContactManagerImpl contactManager = new ContactManagerImpl ();
		contactManager.addNewFutureMeeting(fMeeting1);
		contactManager.addNewFutureMeeting(fMeeting2);
		contactManager.addNewFutureMeeting(fMeeting3);
		
		// Test if all elements are contained and that it is in the correct order.
		
		assertTrue(meetingList.containsAll(contactManager.getFutureMeetingList(contact)));
		assertEquals(meetingList.get(0), contactManager.getFutureMeetingList(contact).get(0));
		assertEquals(meetingList.get(1), contactManager.getFutureMeetingList(contact).get(1));
		assertEquals(meetingList.get(2), contactManager.getFutureMeetingList(contact).get(2));
		
	}
	
	
	
	
	
	

}
