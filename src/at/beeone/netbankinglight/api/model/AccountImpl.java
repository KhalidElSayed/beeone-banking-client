package at.beeone.netbankinglight.api.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccountImpl implements Account {

	private String id;
	private String type;

	private AccountSettings settings;

	// in eurocent
	private Long balance;

	private Long overdraft;

	private List<User> owners = new ArrayList<User>();

	private String iban;

	private Long availableFunds;

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Account#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Account#getType()
	 */
	@Override
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Account#getBalance()
	 */
	@Override
	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Account#getOverdraft()
	 */
	@Override
	public Long getOverdraft() {
		return overdraft;
	}

	public void setOverdraft(Long overdraft) {
		this.overdraft = overdraft;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Account#getOwners()
	 */
	@Override
	public List<User> getOwners() {
		return Collections.unmodifiableList(owners);
	}

	public void addOwner(User owner) {
		this.owners.add(owner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Account#getIban()
	 */
	@Override
	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Account#getAvailableFunds()
	 */
	@Override
	public Long getAvailableFunds() {
		return availableFunds;
	}

	public void setAvailableFunds(Long availableFunds) {
		this.availableFunds = availableFunds;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Account#getSettings()
	 */
	@Override
	public AccountSettings getSettings() {
		return settings;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Account#setSettings(at.beeone.
	 * netbankinglight.api.model.AccountSettings)
	 */
	@Override
	public void setSettings(AccountSettings settings) {
		this.settings = settings;
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountImpl other = (AccountImpl) obj;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
