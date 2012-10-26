beeone-banking-client
=====================

API client for the BeeOne netbanking light API. 

You should use this together with the REST api documentation for the netbanking API. I did not bother packaging this into a jar file, since you probably want tohave the option to look at or modify the code anyway.  

There are no dependencies necessary to run this on Android. Just add the source code to your project and you should be fine.
If you need to use the API in a JDK project instead of Android, you also need to package the json parser in /lib with your project.


    // create a new session for a user and login to the netbanking system
    NetbankingSession session = new NetbankingSession.Builder()
            .endpoint(ENDPOINT).username(USERNAME).password(PASSWORD)
            .login();
   
    // let's start with something simple:
    assertTrue(session.ping());// the server is up and reachable
   
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
    cal.add(Calendar.MONTH, 1); // I hate java.util.Calendar...
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
