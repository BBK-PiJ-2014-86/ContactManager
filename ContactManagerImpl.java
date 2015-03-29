package ContactManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactManagerImpl implements ContactManager, Serializable{
	
	private List <Meeting> meetingList;
	private List <Contact> contactList;
	private int idCount; //this variable will hold the next ID to be assigned to meetings
	private int contactCount; //this variable will hold the next ID to be assigned to contacts
	private Storage storage;
	private final String STORAGE_FILE_NAME = "storage.dat";
	private File storageFile; 
	
	
	public ContactManagerImpl () {	
		
		storageFile = new File(STORAGE_FILE_NAME);
		
		 // First we check if the file exists. If it doesn't, it will initialise the variables.
		 
		
		if (!storageFile.exists()) {
			idCount = 0;
			contactCount = 0;
			meetingList = new ArrayList();
			contactList = new ArrayList();
			flush();
		} else {
		
			ObjectInputStream in = null;;
			
			//
		
			try {
				 in = new ObjectInputStream(new FileInputStream (storageFile));
				 try {
					 
					storage = (Storage) in.readObject(); // reading the Storage object from the file
					
					meetingList = storage.getMeetingList();
					System.out.println(meetingList.size());
					 contactList = storage.getContactList();
					 System.out.println(contactList.size());
					 idCount = storage.getIdCount();
					 System.out.println(idCount);
					 contactCount = storage.getContactCount();
					 System.out.println(contactCount);
					 flush();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				 
				 // assigning the stored values from the file
				
				 
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					in.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
		
	}


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
		
		refreshMeetings();
		
		/*
		 * The for-loop loops through meetingList and looks for PastMeeting with the id requested. The else-if
		 * statement checks if there is a FutureMeeting with the id and throws IllegalArgumentException if so.
		 */
		for (Meeting m : meetingList){
			if (m instanceof PastMeeting && m.getId()==id ){
				return (PastMeeting) m;
			} else if (m instanceof FutureMeeting && m.getId()==id) {
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
		
		refreshMeetings();
		
		/*
		 * The for-loop loops through meetingList and looks for FutureMeeting with the id requested. The else-if
		 * statement checks if there is a PastMeeting with the id and throws IllegalArgumentException if so.
		 */
		for (Meeting m : meetingList){
			if (m instanceof FutureMeeting && m.getId()==id ){
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
		
		refreshMeetings();
		
		List <Meeting> list = new ArrayList(); // list will store found elements
		
		if (!contactList.contains(contact)) throw new IllegalArgumentException();
		
		for (Meeting m : meetingList) {
			if (m instanceof FutureMeeting) {//before executing checking if class is FutureMeeting. meetingList contains both past and future meetings
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
		
		refreshMeetings();
	
	List <Meeting> list = new ArrayList(); // list will store found elements
		
		for (Meeting m : meetingList) {
			if (m instanceof PastMeeting) {//before executing checking if class is FutureMeeting. meetingList contains both past and future meetings
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
		
		refreshMeetings();
		
			List <PastMeeting> list = new ArrayList(); // list will store found elements
			
			if (!contactList.contains(contact)) throw new IllegalArgumentException();
			
			for (Meeting m : meetingList) {
				if (m instanceof PastMeeting) {//before executing checking if class is PastMeeting. meetingList contains both past and future meetings
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
		refreshMeetings();
		
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
		System.out.println(array.length + " " + contactCount);
		Set <Contact> contactSet = new HashSet(); // set where to add found contacts
		
		for (int i : array) {
			if (i> contactCount || i<0) throw new IllegalArgumentException (); // if any int is bigger that the count or less than 0, throws exception
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
		
		// attaches a hook to save before shutdown. 
		
		storage = new Storage (contactList, meetingList, contactCount, idCount);

		/*
		Runtime.getRuntime().addShutdownHook(new Thread() {

		    @Override
		    public void run() {
		    	ObjectOutputStream oo = null;
				try {
					 oo = new ObjectOutputStream(new FileOutputStream(storageFile));
					 oo.writeObject(storage);
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

		});
		
		*/
		
		// it saves the current contents too
		
		ObjectOutputStream oo = null;
		
		try {
			 oo = new ObjectOutputStream(new FileOutputStream(storageFile));
			 oo.writeObject(storage);
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
	 * This method checks for FutureMeetings with past dates in the Meeting List. If so, 
	 * creates a new PastMeeting object, assigns the same id and overwrites the FutureMeeting 
	 * 
	 */
	
	private void refreshMeetings () {
		
		Calendar presentTime = Calendar.getInstance(); 
		
		/*
		 * Loop through the Meeting objects looking for FutureMeeting which occur in the past. 
		 * if found then a new object of type PastMeeting is created which copies the data and 
		 * overwrites the FutureMeeting objects
		 */
		for (Meeting m : meetingList) {
			if (m instanceof FutureMeeting && m.getDate().compareTo(presentTime)<0) {
				int id = m.getId();
				Calendar date = m.getDate();
				Set <Contact> contacts = m.getContacts();		
				meetingList.add(new PastMeetingImpl(id, date, contacts));
			}
		}
		
	}
	
	
	

}
