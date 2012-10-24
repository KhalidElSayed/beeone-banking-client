package at.beeone.netbankinglight.api.model;

import java.util.List;

public interface Account {

	public abstract String getId();

	public abstract String getType();

	public abstract Long getBalance();

	public abstract Long getOverdraft();

	public abstract List<User> getOwners();

	public abstract String getIban();

	public abstract Long getAvailableFunds();

	public abstract AccountSettings getSettings();

	public abstract void setSettings(AccountSettings settings);

}