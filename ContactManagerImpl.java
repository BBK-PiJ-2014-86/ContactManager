package ContactManager;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager{
	
	private List <Meeting> meetingList;
	private List <Contact> contactList;
	private int idCount; //this variable will hold the next ID to be assigned to meetings

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		int meetingId = idCount;// meetingId gets the next available id 
		
		int checkIfPastDate = date.compareTo(Calendar.getInstance()); // comparing the passed date to the current date. If the date is happening in the past, then the value of the variable will be less then 0;
		if (checkIfPastDate < 0) throw new IllegalArgumentException();
		
		/*
		 * The following for-loop goes through the Contacts in the set and checks against each contact
		 * if it is found in the contactList. If not at any point, it will throw an IllegalArgumentException.
		 */
		for (Contact c: contacts) {
			boolean found = contactList.contains(c);
			if (!found) {
				throw new IllegalArgumentException();
			}
		}
		
		meetingList.add(new FutureMeetingImpl (meetingId, date, contacts));
		idCount++;
		return meetingId;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewContact(String name, String notes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * This method takes a PastMeeting object and adds it to the ContactManager Implementation.
	 * @param PastMeeting 
	 */
	
	public void addNewPastMeeting (PastMeeting pastmeeting) {

		
	}
	
	/**
	 * This method takes a FutureMeeting object and adds it to the ContractManager Implementation
	 * @param FutureMeeting futureMeeting
	 */
	
	public void addNewFutureMeeting (FutureMeeting futureMeeting) {
		
	}
	
	/**
	 * 
	 * @return a copy of the file where contacts are saved.
	 */
	
	public File contactFileCopy ()  {
		return null;
	}
	
	
	

}
