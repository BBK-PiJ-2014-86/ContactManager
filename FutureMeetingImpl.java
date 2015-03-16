package ContactManager;

import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl implements FutureMeeting {
	
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

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Calendar getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contact> getContacts() {
		// TODO Auto-generated method stub
		return null;
	}

}
