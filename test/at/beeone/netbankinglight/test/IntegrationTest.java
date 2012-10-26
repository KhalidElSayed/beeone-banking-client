/*
 * Copyright (C) 2012 BeeOne GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.beeone.netbankinglight.test;

import at.beeone.netbankinglight.api.ErrorHandler;
import at.beeone.netbankinglight.api.HttpError;
import at.beeone.netbankinglight.api.NetbankingSession;
import at.beeone.netbankinglight.api.model.Account;
import at.beeone.netbankinglight.api.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class IntegrationTest {

	private static final String ENDPOINT = "http://localhost:8080/api/";
	private static final String USERNAME = "tester1";
	private static final String PASSWORD = "pwd";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testPlaythrough() {

		// create a new session for a user and login to the netbanking system
		NetbankingSession session = new NetbankingSession.Builder()
				.endpoint(ENDPOINT).username(USERNAME).password(PASSWORD)
				.login();

		// let's start with something simple:
		assertTrue(session.ping());// the server is up and reachable


        assertEquals(USERNAME, session.getUser().getUserName());

		// a list of all accounts that the user owns.
		List<Account> accounts = session.getAccounts();

		// you can get a single account for a specific iban
		String someIban = "AT032011156172743372";
		Account account = session.getAccount(someIban);

		// you can update an accounts settings, like giving it a new name:
		account.getSettings().setName("my new account name");
		session.updateAccountSettings(account);

		Long amount = 133700L;

		// lets create a new transaction. There are no mandatory fields on the
		// transaction. Unless we finish it, it will be considered a just a
		// draft.
		Transaction newTransaction = session.newTransaction();
		newTransaction.setAmount(amount);
		newTransaction = session.addTransaction(account, newTransaction);
		assertNotNull(newTransaction.getId());
		assertEquals(someIban, newTransaction.getAccount().getIban());
		// let's see if this worked. We try to get the new transaction from the
		// server
		Transaction ourTransaction = session.getTransaction(account,
				newTransaction.getId());

		assertEquals(newTransaction.getId(), ourTransaction.getId());
		assertEquals(amount, ourTransaction.getAmount());

		// now we add some more data to the transaction and send the update to
		// the server:
		String purpose = "I won the hackathon!";
		ourTransaction.setPurpose(purpose);

		Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, 1);
		Date carryOutDate = cal.getTime();
		ourTransaction.setCarryOutDate(carryOutDate);

		session.updateTransaction(ourTransaction);

		// to test this, we fetch the transaction once again from the server
		ourTransaction = session
				.getTransaction(account, ourTransaction.getId());

		assertEquals(purpose, ourTransaction.getPurpose());

		// finally, we finish the transaction
		ourTransaction.setFinished(true);
		session.updateTransaction(ourTransaction);

		// of course, we cannot update the transaction after we finished it:
		try {
			ourTransaction.setAmount(1234500L);
			session.updateTransaction(ourTransaction);
			fail();
		} catch (RuntimeException e) {
			assert (true);
		}

		// errors like these are expected and not really exceptional. So often
		// we want to pass an ErrorHandler to handle these cases, instead of
		// catching Exceptions:
		ourTransaction.setAmount(1234500L);
		final boolean[] errorHandlerExecuted = new boolean[] { false };
		session.updateTransaction(ourTransaction, new ErrorHandler() {
			public void onError(HttpError status) {
				errorHandlerExecuted[0] = true;
				assertEquals(409, status.getStatusCode());
				assertEquals("Conflict", status.getStatusMessage());

				// we would like to have an Error
				assertEquals(null, status.getErrorMessage());
			}
		});

		if (!errorHandlerExecuted[0]) {
			fail();
		}

		// finally, we can delete the transaction
		session.deleteTransaction(ourTransaction);

		// let's get some transactions from the account
		int pageSize = 16;
		int page = 2;
		List<Transaction> transactions = session.getTransactions(
				account.getIban(), pageSize, page);

		// or we search for some transactions
		pageSize = 5;
		page = 0;
		transactions = session.searchTransactions(">119.80", pageSize, page);
		assertEquals(5, transactions.size());

	}

}
