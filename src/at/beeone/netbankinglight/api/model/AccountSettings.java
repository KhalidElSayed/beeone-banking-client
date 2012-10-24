package at.beeone.netbankinglight.api.model;

public class AccountSettings {

	private boolean mSearchable;

	private String mName;

	public AccountSettings(String name, boolean searchable) {
		mName = name;
		mSearchable = searchable;
	}

	public boolean isSearchable() {
		return mSearchable;
	}

	public void setSearchable(boolean searchable) {
		this.mSearchable = searchable;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

}
