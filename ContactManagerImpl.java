package ContactManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager{
	
	private List <Meeting> meetingList;
	private List <Contact> contactList;
	private int idCount; //this variable will hold the next ID to be assigned to meetings
	private int contactCount; //this variable will hold the next ID to be assigned to contacts
	private Storage storage;
	private final String STORAGE_FILE = "storage.java";


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
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		
			List <PastMeeting> list = new ArrayList(); // list will store found elements
			
			if (!contactList.contains(contact)) throw new IllegalArgumentException();
			
			for (Meeting m : meetingList) {
				if (m.getClass()==PastMeeting.class) {//before executing checking if class is FutureMeeting. meetingList contains both past and future meetings
					for (Contact c : m.getContacts()){
						if (c.equals(contact) && !list.contains(m)){ // find contact AND list does NOT contain the meeting
						list.add((PastMeeting) m);
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
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date, String text) {
		
		if(contacts.isEmpty()) throw new IllegalArgumentException(); // 
		
		//if contact not found, it throws IllegalArgumentException
		
		for (Contact c: contacts) {
			boolean found = contactList.contains(c);
			if (!found) {
				throw new IllegalArgumentException();
			}
		}
		int meetingId = idCount;
		meetingList.add(new PastMeetingImpl(meetingId, date, contacts));
		addMeetingNotes(meetingId, text);
		idCount++;		
	}
	
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public void addMeetingNotes(int id, String text) {
		
		PastMeetingImpl meeting;
		if(text==null) throw new NullPointerException ();
		if(meetingList.get(id) ==null) throw new IllegalArgumentException ();
		
		meeting =(PastMeetingImpl) meetingList.get(id);
		
		if(meeting.getDate().compareTo(Calendar.getInstance())>0) throw new IllegalStateException();
		
		meeting.addNotes(text);
		
		
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public void addNewContact(String name, String notes) {
		
		if (name == null || notes == null) throw new NullPointerException () ;
		
		contactList.add(new ContactImpl(contactCount, name));
		contactList.get(contactCount).addNotes(notes);
		contactCount++;
		
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public Set<Contact> getContacts(int... ids) {
		
		int [] array = ids;//turning the passed ids into int array
		Set <Contact> contactSet = new HashSet(); // set where to add found contacts
		
		for (int i : array) {
			if (i>= contactCount || i<0) throw new IllegalArgumentException (); // if any int is bigger that the count or less than 0, throws exception
		}
		
		for (int i : array) {
			for (Contact c : contactList) {
				if (c.getId()==i) {
					contactSet.add(c);
				}
			}
		}
		
		return contactSet;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public Set<Contact> getContacts(String name) {
		
		if (name == null) throw new NullPointerException ();//dealing with null String
		
		Set <Contact> contactSet = new HashSet();
		
		for (Contact c: contactList) {
			if(c.getName().contains(name)) {
				contactSet.add(c);
			}
		}
		
		return contactSet;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void flush() {
		
		ObjectOutputStream oo = null;
		storage = new Storage (contactList, meetingList, contactCount, idCount);
		try {
			 oo = new ObjectOutputStream(new FileOutputStream(STORAGE_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				oo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * This method takes a PastMeeting object and adds it to the ContactManager Implementation.
	 * This method is created for testing purposes and would be deleted in real implementation
	 * @param PastMeeting 
	 */
	
	public void addNewPastMeeting (PastMeeting pastmeeting) {
		
		meetingList.add (pastmeeting);
		
	}
	
	/**
	 * This method takes a FutureMeeting object and adds it to the ContractManager Implementation
	 * This method is created for testing purposes and would be deleted in real implementation
	 * @param FutureMeeting futureMeeting
	 */
	
	public void addNewFutureMeeting (FutureMeeting futureMeeting) {
		meetingList.add(futureMeeting);
	}
	
	/**
	 * 
	 * @return a copy of the file where contacts are saved.
	 */
	
	public File contactFileCopy ()  {
		return null;
	}
	
	
	

}
