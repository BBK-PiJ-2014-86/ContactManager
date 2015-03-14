package ContactManager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestContactManager {

	ContactManager testContactManager;
	Set <Contact> contacts;
	Calendar date;
	
	/**
	 * initialising fields in Before to implement before starting 
	 */
	@Before
	public void buildUp () {
		testContactManager = new ContactManagerImpl();
		contacts = new HashSet();
		date = Calendar.getInstance();
	}
	
	@Test (expected= IllegalArgumentException.class)
	public void addFutureMeetingTest1() {
		
		testContactManager.addNewContact("Marcus Eoin", "Interview");
		contacts.add(new ContactImpl ("Michael Sandison"));
		date.set(2015, Calendar.JUNE, 9);
		testContactManager.addFutureMeeting(contacts, date);
	}

}
