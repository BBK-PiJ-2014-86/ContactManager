package ContactManager;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl implements FutureMeeting,Serializable {
	
	private int id;
	private Calendar date;
	private Set <Contact> contacts;
	
	/**
	 * 
	 * @param int id sets id of the meeting
	 * @param Calendar date sets the date of the meeting
	 * @param Set <Contact> sets the set of contact in the particular FutureMeeting instance
	 */
	
	public FutureMeetingImpl (int id, Calendar date, Set<Contact> contacts){
		this.id = id;
		this.date = date;
		this.contacts = contacts;
	}

	
	/**
	 * {@inheritDoc}
	 *
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

}
