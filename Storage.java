package ContactManager;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class Storage implements Serializable {

	List<Contact> contactList;
	List<Meeting> meetingList;
	int contactCount;
	int idCount;
	
	public Storage (List<Contact> contactList, List<Meeting> meetingList, int contactCount, int idCount) {
	
		this.contactList =contactList;
		this.meetingList = meetingList;
		this.contactCount = contactCount;
		this.idCount;
	}
	
	
}
