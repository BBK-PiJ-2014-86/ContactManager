package ContactManager;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public PastMeeting getPastMeeting(int id) {
		
		/*
		 * The for-loop loops through meetingList and looks for PastMeeting with the id requested. The else-if
		 * statement checks if there is a FutureMeeting with the id and throws IllegalArgumentException if so.
		 */
		for (Meeting m : meetingList){
			if (m.getClass() == PastMeeting.class && m.getId()==id ){
				return (PastMeeting) m;
			} else if (m.getClass() == FutureMeeting.class && m.getId()==id) {
				throw new IllegalArgumentException();
			}
		}
		
		return null;
		
	}
	
	/**
	 * {@inheritDoc}
	 */


	@Override
	public FutureMeeting getFutureMeeting(int id) {
		
		/*
		 * The for-loop loops through meetingList and looks for FutureMeeting with the id requested. The else-if
		 * statement checks if there is a PastMeeting with the id and throws IllegalArgumentException if so.
		 */
		for (Meeting m : meetingList){
			if (m.getClass() == FutureMeeting.class && m.getId()==id ){
				return (FutureMeeting) m;
			} else if (m.getClass() == PastMeeting.class && m.getId()==id) {
				throw new IllegalArgumentException();
			}
		}
		
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public Meeting getMeeting(int id) {
		
		for (Meeting m : meetingList) {
			if(m.getId()==id) {
				return m;
			}
		}
		
		return null;
	}
	
	/**
	 * 
	* Returns the list of future meetings scheduled with this contact.
	*
	* If there are none, the returned list will be empty. Otherwise,
	* the list will be chronologically sorted and will not contain any
	* duplicates.
	*
	* @param contact one of the user�s contacts
	* @return the list of future meeting(s) scheduled with this contact (maybe empty).
	* @throws IllegalArgumentException if the contact does not exist
	*/

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		
		List <Meeting> list = new ArrayList(); // list will store found elements
		
		if (!contactList.contains(contact)) throw new IllegalArgumentException();
		
		for (Meeting m : meetingList) {
			if (m.getClass()==FutureMeeting.class) {//before executing checking if class is FutureMeeting. meetingList contains both past and future meetings
				for (Contact c : m.getContacts()){
					if (c.equals(contact) && !list.contains(m)){ // find contact AND list does NOT contain the meeting
					list.add(m);
					}
				}
			}
		}
		
		/* At this point the list would contain all elements but not necessarily in chronological order.
		 * The next line will sort the list in chronological order
		 */
		
		list.sort(Comparator.comparing(m->m.getDate()));
		
		return list;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
	
	List <Meeting> list = new ArrayList(); // list will store found elements
		
		for (Meeting m : meetingList) {
			if (m.getClass()==FutureMeeting.class) {//before executing checking if class is FutureMeeting. meetingList contains both past and future meetings
				if(date.YEAR==m.getDate().YEAR && date.DAY_OF_YEAR == m.getDate().DAY_OF_YEAR) {
					list.add(m);
				}
			}
		}
		
		/* At this point the list would contain all elements but not necessarily in chronological order.
		 * The next line will sort the list in chronological order
		 */
		
		list.sort(Comparator.comparing(m->m.getDate()));
		
		return list;
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
