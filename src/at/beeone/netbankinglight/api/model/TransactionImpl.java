package at.beeone.netbankinglight.api.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TransactionImpl implements Transaction {

	private String id;
	private Account account;
	private TransactionType type;
	private Date createdOn;
	private Long amount;
	private Set<String> tags = new HashSet<String>();
	private String notes;
	private boolean finished;
	private boolean cancelled;
	private String bill;
	private Date carryOutDate;
	private String customerData;
	private String identification;
	private String purpose;
	private String receiverIban;
	private String receiverReference;
	private String receiverName;
	private String record;
	private String senderIban;
	private String senderName;
	private String signature;
	private TransactionStatus status;

	public TransactionImpl() {
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		TransactionImpl other = (TransactionImpl) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getId()
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
	 * @see at.beeone.netbankinglight.api.model.Transaction#getAccount()
	 */
	@Override
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getType()
	 */
	@Override
	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getCreatedOn()
	 */
	@Override
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getAmount()
	 */
	@Override
	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getTags()
	 */
	@Override
	public Set<String> getTags() {
		return tags;
	}

	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getNotes()
	 */
	@Override
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#isFinished()
	 */
	@Override
	public Boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#isCancelled()
	 */
	@Override
	public Boolean isCancelled() {
		return cancelled;
	}

	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getBill()
	 */
	@Override
	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getCarryOutDate()
	 */
	@Override
	public Date getCarryOutDate() {
		return carryOutDate;
	}

	public void setCarryOutDate(Date carryOutDate) {
		this.carryOutDate = carryOutDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getCustomerData()
	 */
	@Override
	public String getCustomerData() {
		return customerData;
	}

	public void setCustomerData(String customerData) {
		this.customerData = customerData;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getIdentification()
	 */
	@Override
	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getPurpose()
	 */
	@Override
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getReceiverIban()
	 */
	@Override
	public String getReceiverIban() {
		return receiverIban;
	}

	public void setReceiverIban(String receiverIban) {
		this.receiverIban = receiverIban;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * at.beeone.netbankinglight.api.model.Transaction#getReceiverReference()
	 */
	@Override
	public String getReceiverReference() {
		return receiverReference;
	}

	public void setReceiverReference(String receiverReference) {
		this.receiverReference = receiverReference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getReceiverName()
	 */
	@Override
	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getRecord()
	 */
	@Override
	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getSenderIban()
	 */
	@Override
	public String getSenderIban() {
		return senderIban;
	}

	public void setSenderIban(String senderIban) {
		this.senderIban = senderIban;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getSenderName()
	 */
	@Override
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getSignature()
	 */
	@Override
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see at.beeone.netbankinglight.api.model.Transaction#getStatus()
	 */
	@Override
	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

}
