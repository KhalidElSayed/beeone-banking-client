package at.beeone.netbankinglight.api.model;

import java.util.Date;
import java.util.Set;

public interface Transaction {

	public abstract String getId();

	public abstract Account getAccount();

	public abstract TransactionType getType();

	public abstract Date getCreatedOn();

	public abstract Long getAmount();

	public abstract Set<String> getTags();

	public abstract String getNotes();

	public abstract Boolean isFinished();

	public abstract Boolean isCancelled();

	public abstract String getBill();

	public abstract Date getCarryOutDate();

	public abstract String getCustomerData();

	public abstract String getIdentification();

	public abstract String getPurpose();

	public abstract String getReceiverIban();

	public abstract String getReceiverReference();

	public abstract String getReceiverName();

	public abstract String getRecord();

	public abstract String getSenderIban();

	public abstract String getSenderName();

	public abstract String getSignature();

	public abstract TransactionStatus getStatus();

	void setAmount(Long amount);

	void setReceiverName(String receiverName);
	
	void setReceiverReference(String receiverReference);

	void setReceiverIban(String receiverIban);

	void setCarryOutDate(Date carryOutDate);

	void setPurpose(String purpose);

	void setFinished(boolean finished);

	void setIdentification(String identification);

}