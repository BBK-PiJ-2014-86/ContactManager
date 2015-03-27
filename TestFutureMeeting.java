package ContactManager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestFutureMeeting {


	private int id;
	private Calendar date;
	private Set<Contact> contactSet;

	@Before
	
	public void BuildUp (){
		id = 1;
		date = Calendar.getInstance();
		date.set(2016, Calendar.FEBRUARY, 11, 10, 0);
		contactSet = new HashSet();
		contactSet.add(new ContactImpl (2,"Someone"));
	}
	
	/**
	 * <b> Test Method </b>: int getId();
	 * <br>
	 * <b> Test Scope</b>: gets the id
	 */
	@Test
	public void getIdNormalCase () {

		FutureMeeting meeting = new FutureMeetingImpl (id, date, contactSet);
		assertEquals(id,meeting.getId());
		
	}
	
	/**
	 * <b> Test Method </b>: Calendar getDate();
	 * <br>
	 * <b> Test Scope</b>: gets the date of the meeting
	 */
	@Test
	
	public void getDateNormalCaseTest() {
		
		FutureMeeting meeting = new FutureMeetingImpl (id, date, contactSet);
		assertEquals(date,meeting.getDate());
		
	}
	
	/**
	 * <b> Test Method </b>: Set<Contact> getContacts();
	 * <br>
	 * <b> Test Scope</b>: gets the date of the meeting
	 */
	@Test
	
	public void getContactNormalCaseTest () {
		
		FutureMeeting meeting = new FutureMeetingImpl (id, date, contactSet);
		assertEquals(contactSet,meeting.getContacts());
		
	}
	

}
