package ContactManager;

import java.util.Calendar;
import java.util.Set;

public class PastMeetingImpl implements PastMeeting{
	
	private int id;
	private Calendar date;
	private Set <Contact> contacts;
	private String notes;
	
	/**
	 * Constructor - passing id, Calendar date, Set contacts. Notes are an empty string. 
	 * 
	 * @param int id - sets the id of the meeting
	 * @param Caledar date - sets the date of the meeting
	 * @param Set <Contact> contacts - sets the a set of contacts.
	 * 
	 */
	
	public PastMeetingImpl (int id, Calendar date, Set <Contact> contacts) {
		
		this.id = id;
		this.date = date;
		this.contacts = contacts;
		notes="";
	}
	/**
	 * {@inheritDoc}
	 */

	@Override
	public int getId() {
		
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Calendar getDate() {
	
		return date;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts() {
		
		return contacts;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public String getNotes() {
		
		return notes;
	}
	
	public void addNotes(String notes) {
		this.notes= notes; 
	}

}
