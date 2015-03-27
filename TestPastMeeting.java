package ContactManager;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.HashSet;
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
	
	

}
