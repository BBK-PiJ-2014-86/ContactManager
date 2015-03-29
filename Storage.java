package ContactManager;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Storage implements Serializable {
	
	private List<Contact> contactList;
	private List<Meeting> meetingList;
	private int contactCount;
	private int idCount;
	
	public Storage (List<Contact> contactList, List<Meeting> meetingList, int contactCount, int idCount) {
		
		this.contactList =contactList;
		this.meetingList = meetingList;
		this.contactCount = contactCount;
		this.idCount = idCount;
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	public List<Meeting> getMeetingList() {
		return meetingList;
	}

	public int getContactCount() {
		return contactCount;
	}


	public int getIdCount() {
		return idCount;
	}
	
	
}
