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
		
		PastMeeting futureMeeting = new PastMeetingImpl (id,date, contacts); 
		
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
		
		// Test if all elements are contained
		
		assertTrue(meetingList.containsAll(contactManager.getFutureMeetingList(contact)));
	}
	
	/**
	 * This test tests whether getFutureMeetingList () method which returns list of future meetings in the correct order.
	 */
	
	@Test
	
	public void getFutureMeetingListCorrectOrder () {
		
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
		
		
		assertEquals(meetingList.get(0), contactManager.getFutureMeetingList(contact).get(0));
		assertEquals(meetingList.get(1), contactManager.getFutureMeetingList(contact).get(1));
		assertEquals(meetingList.get(2), contactManager.getFutureMeetingList(contact).get(2));
		
	}
	
	/**
	 * This test test whether getFutureMeetingList () method returns a list free of duplicates
	 */
	
	@Test
	
	public void getFutureMeetingListDuplicatesTest () {
	
		final Calendar date1 = Calendar.getInstance(); date1.set(2015, Calendar.AUGUST, 3); // 3rd August 2015
		final Calendar date2 = Calendar.getInstance(); date2.set(2015,Calendar.JULY,19); // 19th July 2015
		final Calendar date3 = Calendar.getInstance(); date3.set(2015, Calendar.DECEMBER,24); //24th December 2015
		final int meetingID1 = 1;
		final int meetingID2 = 2;
		final int meetingID3 = 3;
		
		//Create a Contact that I will look for and put the contact in a Set implementation of a hashset.
		
		final String name = "Michael Eoin";
		final Contact contact = new ContactImpl (1,name);
		final Set <Contact> contactList = new HashSet(); 
		contactList.add(contact);
		
		// Create 6 future Meetings with created Contact Set
		
		final FutureMeeting fMeeting1 = new FutureMeetingImpl (meetingID1, date1, contactList);
		final FutureMeeting fMeeting2 = new FutureMeetingImpl (meetingID2, date2, contactList);
		final FutureMeeting fMeeting3 = new FutureMeetingImpl (meetingID3, date3, contactList);
		
		// Create ArrayList that will store the Meetings in chronological order.
		
		final List <Meeting> meetingList = new ArrayList <Meeting> ();
		meetingList.add(0, fMeeting2);
		meetingList.add(1,fMeeting1);
		meetingList.add(2,fMeeting3);
		
		
		// Add the a few duplicate FutureMeetings objects to the ContactManager.
		
		final ContactManagerImpl contactManager = new ContactManagerImpl ();
		contactManager.addNewFutureMeeting(fMeeting1);
		contactManager.addNewFutureMeeting(fMeeting2);
		contactManager.addNewFutureMeeting(fMeeting3);
		contactManager.addNewFutureMeeting(fMeeting1);
		contactManager.addNewFutureMeeting(fMeeting2);
		
		// The List meetingList should contain all elements and the assert method below would return true because it would have disregarded the duplicates
		
		assertTrue (meetingList.containsAll(contactManager.getFutureMeetingList(contact)));
		
		
	}
	
	/**
	 * This test test whether getFutureMeetingList () method returns empty list if the contact is not found/unknown
	 */
	
	@Test
	
	public void getFutureMeetingListUnknownContactTest () {
		
		final String name = "Marcus Eoin";
		final Contact contact = new ContactImpl (1,name);

		assertTrue (testContactManager.getFutureMeetingList(contact).isEmpty());
		
	}
	
	/**
	 * This test tests the normal case for getFutureMeetingList
	 */
	
	@Test
	
	public void getFutureMeetingListDNormalCase () {
		
		/*create a bunch of future meetings same date different times. 
		add them to the Contact manager
		add them to an ArrayList ordered chronologically
		test contract manager with assert true
		 */
		
		final Calendar date1 = Calendar.getInstance();
		final Calendar date2 = Calendar.getInstance();
		final Calendar date3 = Calendar.getInstance();
		final Calendar date4 = Calendar.getInstance();
		date1.set(2015, Calendar.APRIL, 15, 13, 30);
		date2.set(2015, Calendar.APRIL, 15,11,0);
		date3.set(2015,Calendar.APRIL,15,10,0);
		date4.set(2015, Calendar.APRIL, 15);
		
		final FutureMeeting meeting1 = new FutureMeetingImpl (1, date1, contacts);
		final FutureMeeting meeting2 = new FutureMeetingImpl (1, date2, contacts);
		final FutureMeeting meeting3 = new FutureMeetingImpl (1, date3, contacts);
		
		final ContactManagerImpl contactMgmt = new ContactManagerImpl ();
		
		contactMgmt.addNewFutureMeeting(meeting1);
		contactMgmt.addNewFutureMeeting(meeting2);
		contactMgmt.addNewFutureMeeting(meeting3);
		
		List <FutureMeeting> list = new ArrayList();
		list.add(0,meeting3);
		list.add(1,meeting2);
		list.add(2,meeting1);
		
		assertTrue(list.get(2).getDate().equals(contactMgmt.getFutureMeetingList(date4).get(2).getDate()));
		
	}
	
	/**
	 * This test tests whether there are any duplicate elements returned
	 */
	
	@Test 
	
	public void getFutureMeetingListNoDuplicates () {
		
		/*
		 * Create date and 5 meetings with 2 duplicates in them. Then using AssertTrue we check that only 3 of these are returned because the other 2 are duplicates.
		 */
		
		final Calendar date1 = Calendar.getInstance();
		final Calendar date2 = Calendar.getInstance();
		final Calendar date3 = Calendar.getInstance();
		final Calendar date4 = Calendar.getInstance();
		date1.set(2015, Calendar.APRIL, 15, 13, 30);
		date2.set(2015, Calendar.APRIL, 15,11,0);
		date3.set(2015,Calendar.APRIL,15,10,0);
		date4.set(2015, Calendar.APRIL, 15);
		
		final FutureMeeting meeting1 = new FutureMeetingImpl (1, date1, contacts);
		final FutureMeeting meeting2 = new FutureMeetingImpl (1, date2, contacts);
		final FutureMeeting meeting3 = new FutureMeetingImpl (1, date3, contacts);
		
		final ContactManagerImpl contactMgmt = new ContactManagerImpl ();
		
		contactMgmt.addNewFutureMeeting(meeting1);
		contactMgmt.addNewFutureMeeting(meeting2);
		contactMgmt.addNewFutureMeeting(meeting3);
		contactMgmt.addNewFutureMeeting(meeting3);
		contactMgmt.addNewFutureMeeting(meeting2);
		
		assertTrue((contactMgmt.getFutureMeetingList(date4).size()==3));
		
	}
	
	/**
	 * This test confirms that elements that are not found 
	 */
	
	@Test
	
	public void getFutureMeetingListNoDateFound () {
		
		date.set(2015, Calendar.APRIL, 15,11,0);
		
		assertTrue(testContactManager.getFutureMeetingList(date).isEmpty());
		
	}
	
	/**
	 * This test tests the normal case of getFutureMeetingList () method which returns list of future meetings scheduled with this contact.
	 */
	
	@Test
	
	public void getPastMeetingListNormal () {
		
		final Calendar date1 = Calendar.getInstance(); date1.set(2014, Calendar.AUGUST, 3); // 3rd August 2014
		final Calendar date2 = Calendar.getInstance(); date2.set(2014,Calendar.JULY,19); // 19th July 2014
		final Calendar date3 = Calendar.getInstance(); date3.set(2014, Calendar.DECEMBER,24); //24th December 2014
		final int meetingID1 = 1;
		final int meetingID2 = 2;
		final int meetingID3 = 3;
		
		//create a Contact that I will look for and put the contact in a Set implementation of a hashset.
		
		final String name = "Michael Eoin";
		final Contact contact = new ContactImpl (1,name);
		final Set <Contact> contactList = new HashSet(); 
		contactList.add(contact);
		
		// Create 3 past Meetings with created Contact Set
		
		final PastMeeting pMeeting1 = new PastMeetingImpl (meetingID1, date1, contactList);
		final PastMeeting pMeeting2 = new PastMeetingImpl (meetingID2, date2, contactList);
		final PastMeeting pMeeting3 = new PastMeetingImpl (meetingID3, date3, contactList);
		
		// Create ArrayList that will store the Meetings in chronological order.
		
		final List <Meeting> meetingList = new ArrayList <Meeting> ();
		meetingList.add(0, pMeeting2);
		meetingList.add(1,pMeeting1);
		meetingList.add(2,pMeeting3);
		
		// Add the PastMeeting objects to the ContactManager
		
		final ContactManagerImpl contactManager = new ContactManagerImpl ();
		contactManager.addNewPastMeeting(pMeeting1);
		contactManager.addNewPastMeeting(pMeeting2);
		contactManager.addNewPastMeeting(pMeeting3);
		
		// Test if all elements are contained
		
		assertTrue(meetingList.containsAll(contactManager.getPastMeetingList(contact)));
	}
	
	/**
	 * This test tests whether getPastMeetingList () method which returns list of future meetings in the correct order.
	 */
	
	@Test
	
	public void getPastMeetingListCorrectOrder () {
		
		final Calendar date1 = Calendar.getInstance(); date1.set(2014, Calendar.AUGUST, 3); // 3rd August 2014
		final Calendar date2 = Calendar.getInstance(); date2.set(2014,Calendar.JULY,19); // 19th July 2014
		final Calendar date3 = Calendar.getInstance(); date3.set(2014, Calendar.DECEMBER,24); //24th December 2014
		final int meetingID1 = 1;
		final int meetingID2 = 2;
		final int meetingID3 = 3;
		
		//create a Contact that I will look for and put the contact in a Set implementation of a hashset.
		
		final String name = "Michael Eoin";
		final Contact contact = new ContactImpl (1,name);
		final Set <Contact> contactList = new HashSet(); 
		contactList.add(contact);
		
		// Create 3 past Meetings with created Contact Set
		
		final PastMeeting pMeeting1 = new PastMeetingImpl (meetingID1, date1, contactList);
		final PastMeeting pMeeting2 = new PastMeetingImpl (meetingID2, date2, contactList);
		final PastMeeting pMeeting3 = new PastMeetingImpl (meetingID3, date3, contactList);
		
		// Create ArrayList that will store the Meetings in chronological order.
		
		final List <Meeting> meetingList = new ArrayList <Meeting> ();
		meetingList.add(0, pMeeting2);
		meetingList.add(1,pMeeting1);
		meetingList.add(2,pMeeting3);
		
		// Add the PastMeeting objects to the ContactManager
		
		final ContactManagerImpl contactManager = new ContactManagerImpl ();
		contactManager.addNewPastMeeting(pMeeting1);
		contactManager.addNewPastMeeting(pMeeting2);
		contactManager.addNewPastMeeting(pMeeting3);
		
		// Test if all elements are contained and that it is in the correct order.
		
		
		assertEquals(meetingList.get(0), contactManager.getPastMeetingList(contact).get(0));
		assertEquals(meetingList.get(1), contactManager.getPastMeetingList(contact).get(1));
		assertEquals(meetingList.get(2), contactManager.getPastMeetingList(contact).get(2));
		
	}
	
	/**
	 * This test test whether getPastMeetingList () method returns a list free of duplicates
	 */
	
	@Test
	
	public void getPastMeetingListDuplicatesTest () {
	
		final Calendar date1 = Calendar.getInstance(); date1.set(2014, Calendar.AUGUST, 3); // 3rd August 2014
		final Calendar date2 = Calendar.getInstance(); date2.set(2014,Calendar.JULY,19); // 19th July 2014
		final Calendar date3 = Calendar.getInstance(); date3.set(2014, Calendar.DECEMBER,24); //24th December 2014
		final int meetingID1 = 1;
		final int meetingID2 = 2;
		final int meetingID3 = 3;
		
		//Create a Contact that I will look for and put the contact in a Set implementation of a hashset.
		
		final String name = "Michael Eoin";
		final Contact contact = new ContactImpl (1,name);
		final Set <Contact> contactList = new HashSet(); 
		contactList.add(contact);
		
		// Create 3 past Meetings with created Contact Set
		
		final PastMeeting pMeeting1 = new PastMeetingImpl (meetingID1, date1, contactList);
		final PastMeeting pMeeting2 = new PastMeetingImpl (meetingID2, date2, contactList);
		final PastMeeting pMeeting3 = new PastMeetingImpl (meetingID3, date3, contactList);
		
		// Create ArrayList that will store the Meetings in chronological order.
		
		final List <Meeting> meetingList = new ArrayList <Meeting> ();
		meetingList.add(0, pMeeting2);
		meetingList.add(1,pMeeting1);
		meetingList.add(2,pMeeting3);
		
		
		// Add the a few duplicate FutureMeetings objects to the ContactManager.
		
		final ContactManagerImpl contactManager = new ContactManagerImpl ();
		contactManager.addNewPastMeeting(pMeeting1);
		contactManager.addNewPastMeeting(pMeeting2);
		contactManager.addNewPastMeeting(pMeeting3);
		contactManager.addNewPastMeeting(pMeeting1);
		contactManager.addNewPastMeeting(pMeeting2);
		
		// The List meetingList should contain all elements and the assert method below would return true because it would have disregarded the duplicates
		
		assertTrue (meetingList.containsAll(contactManager.getPastMeetingList(contact)));
		
		
	}
	
	/**
	 * This test test whether getPastMeetingList () method returns empty list if the contact is not found/unknown
	 */
	
	@Test
	
	public void getPastMeetingListUnknownContactTest () {
		
		final String name = "Marcus Eoin";
		final Contact contact = new ContactImpl (1,name);

		assertTrue (testContactManager.getPastMeetingList(contact).isEmpty());
		
	}
	
	
	
	
	
	
	
	

}
