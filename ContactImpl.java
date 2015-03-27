package ContactManager;

public class ContactImpl implements Contact{
	
	private String notes;
	private int id;
	private String name;
	
	public ContactImpl (int id, String name) {
		this.id = id;
		this.name = name;
		
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
	 * 
	 * {@inheritDoc}
	 */
	
	@Override
	public String getName() {
		
		return name;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public String getNotes() {

		return notes;
	}

	/**
	 * {@inheritDoc}
	 */
	
	@Override
	public void addNotes(String note) {
		
		notes = note;
		
	}
 
}
