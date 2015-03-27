package ContactManager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class TestPastMeeting {

	
	private int id;
	private Calendar date;
	private Set<Contact> contactSet;

	@Before
	
	public void BuildUp (){
		id = 1;
		date = Calendar.getInstance();
		date.set(2015, Calendar.FEBRUARY, 11, 10, 0);
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

		PastMeeting meeting = new PastMeetingImpl (id, date, contactSet);
		assertEquals(id,meeting.getId());
		
	}
	
	/**
	 * <b> Test Method </b>: Calendar getDate();
	 * <br>
	 * <b> Test Scope</b>: gets the date of the meeting
	 */
	@Test
	
	public void getDateNormalCaseTest() {
		
		PastMeeting meeting = new PastMeetingImpl (id, date, contactSet);
		assertEquals(date,meeting.getDate());
		
	}
	
	/**
	 * <b> Test Method </b>: Set<Contact> getContacts();
	 * <br>
	 * <b> Test Scope</b>: gets the date of the meeting
	 */
	@Test
	
	public void getContactNormalCaseTest () {
		
		PastMeeting meeting = new PastMeetingImpl (id, date, contactSet);
		assertEquals(contactSet,meeting.getContacts());
		
	}
	
	/**
	 * <b> Test Method </b>: String getNotes();
	 * <br>
	 * <b> Test Scope</b>: gets the notes of the meeting
	 */
	@Test
	
	public void getNotesNormalCaseTest () {
		
		ContactManager CM = new ContactManagerImpl ();
		CM.addNewPastMeeting(contactSet, date, "hello");
		Contact [] con = (Contact[]) contactSet.toArray();
		List<PastMeeting> meet = CM.getPastMeetingList(con[0]);//assume position 0
		Contact cont = (Contact) meet.get(0); // assume position 0
		
		assertEquals("hello",cont.getNotes());
		
	}
	
	
	
	

}
