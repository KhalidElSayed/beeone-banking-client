package at.beeone.netbankinglight.api;

import at.beeone.netbankinglight.api.model.*;
import at.beeone.netbankinglight.util.JsonParser;
import at.beeone.netbankinglight.util.RestCall;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class NetbankingSession {


	public static class Builder {
		private URL endpoint;
		private String username;
		private String password;

		public Builder endpoint(String endpoint) {
			try {
				if (!endpoint.endsWith("/")) {
					endpoint = endpoint + "/";
				}
				this.endpoint = new URL(endpoint);
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
			return this;
		}

		public NetbankingSession login() {
			NetbankingSession conn = new NetbankingSession(this);
			conn.mUser = conn.login(username, password);
			return conn;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder username(String username) {
			this.username = username;
			return this;
		}

	}

	private RestCall newRestCall() {
		return mBuilder.build();
	}

	private User mUser;

	private RestCall.Builder mBuilder;

	private NetbankingSession(Builder builder) {
		if (builder.endpoint == null) {
			throw new AssertionError("you need to specify an endpoint URL");
		}

		mBuilder = new RestCall.Builder().authTokenField("X-BeeOne-Auth")
				.endpoint(builder.endpoint)
				.header("Content-Type", "application/json");

	}

	public Transaction addTransaction(Account account, Transaction transaction) {
		return addTransaction(account, transaction, null);
	}

	public Transaction addTransaction(Account account, Transaction transaction,
			ErrorHandler errorHandler) {
		String resource = "user/" + mUser.getId() + "/accounts/"
				+ account.getIban() + "/transactions/";

		JSONObject json = JsonParser.toJSON(transaction);
		RestCall restCall = newRestCall().resource(resource)
				.errorHandler(errorHandler).body(json.toString());
		String response = restCall.post();

		if (response == null) {
			return null;
		}

		try {
			return JsonParser.toTransaction(new JSONObject(response));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public void deleteTransaction(Transaction transaction) {
		deleteTransaction(transaction, null);
	}

	public void deleteTransaction(Transaction transaction,
			ErrorHandler errorHandler) {

		String resource = "user/" + mUser.getId() + "/accounts/"
				+ transaction.getAccount().getIban() + "/transactions/"
				+ transaction.getId();

		RestCall restCall = newRestCall().errorHandler(errorHandler).resource(
				resource);
		String response = restCall.delete();
	}

	public Account getAccount(String iban) {
		return getAccount(iban, null);
	}

	public Account getAccount(String iban, ErrorHandler errorHandler) {
		String resource = "user/" + mUser.getId() + "/accounts/" + iban;
		try {
			String response = newRestCall().resource(resource)
					.errorHandler(errorHandler).get();
			if (response == null) {
				return null;
			}

			JSONObject json = new JSONObject(response);
			return JsonParser.toAccount(json);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Account> getAccounts() {
		return getAccounts(null);
	}

	public List<Transaction> searchTransactions(String query, Integer pageSize,
			Integer page) {
		return searchTransactions(query, pageSize, page, null);
	}

	public List<Transaction> searchTransactions(String query, Integer pageSize,
			Integer page, ErrorHandler errorHandler) {

		String resource = "user/" + mUser.getId() + "/search/transactions";
		String response = newRestCall().resource(resource)
				.errorHandler(errorHandler).param("q", query)
				.param("pageSize", pageSize.toString())
				.param("page", page.toString()).get();

		if (response == null) {
			return null;
		}

		try {
			JSONObject json = new JSONObject(response);

			List<Transaction> transactionList = JsonParser
					.toTransactionList(json.getJSONArray("transactions"));

			return transactionList;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Account> getAccounts(ErrorHandler errorHandler) {
		try {
			String resource = "user/" + mUser.getId() + "/accounts";

			String response = newRestCall().errorHandler(errorHandler)
					.resource(resource).expectedHttpSuccessCode(200).get();

			if (response == null) {
				return null;
			}
			JSONArray json = new JSONArray(response);
			return JsonParser.toAccountList(json);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public Transaction getTransaction(Account account, String id) {
		return getTransaction(account, id, null);
	}

	public Transaction getTransaction(Account account, String id,
			ErrorHandler errorHandler) {
		String resource = "user/" + mUser.getId() + "/accounts/"
				+ account.getIban() + "/transactions/" + id;
		String response = newRestCall().resource(resource)
				.expectedHttpSuccessCode(200).errorHandler(errorHandler).get();

		if (response == null || response == "") {
			return null;
		}

		try {
			JSONObject json = new JSONObject(response);
			return JsonParser.toTransaction(json);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Transaction> getTransactions(String iban, Integer pageSize,
			Integer page) {
		return getTransactions(iban, pageSize, page, null);
	}

	public List<Transaction> getTransactions(String iban, Integer pageSize,
			Integer page, ErrorHandler errorHandler) {
		String resource = "user/" + mUser.getId() + "/accounts/" + iban
				+ "/transactions";

		String response = newRestCall().resource(resource)
				.errorHandler(errorHandler)
				.param("pageSize", pageSize.toString())
				.param("page", page.toString()).get();

		if (response == null) {
			return null;
		}

		try {
			JSONObject json = new JSONObject(response);

			List<Transaction> transactionList = JsonParser
					.toTransactionList(json.getJSONArray("transactions"));

			return transactionList;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}

	}

	private User login(String username, String password) {
		return login(username, password, null);
	}

	private User login(String username, String password,
			ErrorHandler errorHandler) {
		JSONObject json;
		try {

			String response = newRestCall().resource("login")
					.errorHandler(errorHandler).param("username", username)
					.param("password", password).post();

			if (response == null) {
				return null;
			}

			json = new JSONObject(response);
			return JsonParser.toUser(json);
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public void logout(String username, String password,
			ErrorHandler errorHandler) {
		newRestCall().resource("logout").errorHandler(errorHandler).post();
	}

	public Transaction newTransaction() {
		TransactionImpl transactionImpl = new TransactionImpl();
		return transactionImpl;
	}

	public boolean ping() {
		final boolean[] result = new boolean[] { true };
		newRestCall().resource("debug/ping").expectedHttpSuccessCode(200)
				.errorHandler(new ErrorHandler() {
					@Override
					public void onError(HttpError status) {
						result[0] = false;
					}
				}).get();
		return result[0];
	}

    public User getUser() {
        return getUser(null);
    }

    public User getUser(ErrorHandler errorHandler) {
        String resource = "user/" + mUser.getId();
        String response = newRestCall().resource(resource)
                .expectedHttpSuccessCode(200).errorHandler(errorHandler).get();

        if (response == null || response == "") {
            return null;
        }

        try {
            JSONObject json = new JSONObject(response);
            return JsonParser.toUser(json);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAccountSettings(Account account) {
		updateAccountSettings(account.getIban(), account.getSettings());
	}

	public void updateAccountSettings(Account account, ErrorHandler errorHandler) {
		updateAccountSettings(account.getIban(), account.getSettings(),
				errorHandler);
	}

	private void updateAccountSettings(String iban, AccountSettings newSettings) {
		updateAccountSettings(iban, newSettings, null);
	}

	private void updateAccountSettings(String iban,
			AccountSettings newSettings, ErrorHandler errorHandler) {
		String resource = "user/" + mUser.getId() + "/accounts/" + iban;
		try {
			JSONObject obj = new JSONObject();
			obj.put("name", newSettings.getName());
			obj.put("searchable", newSettings.isSearchable() ? "true" : "false");

			RestCall restCall = newRestCall().resource(resource)
					.errorHandler(errorHandler).body(obj.toString());

			restCall.put();

		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	public Transaction updateTransaction(Transaction transaction) {
		return updateTransaction(transaction, null);
	}

	public Transaction updateTransaction(Transaction transaction,
			ErrorHandler errorHandler) {
		String resource = "user/" + mUser.getId() + "/accounts/"
				+ transaction.getAccount().getIban() + "/transactions/"
				+ transaction.getId();

		JSONObject json = JsonParser.toJSON(transaction);
		RestCall restCall = newRestCall().resource(resource)
				.errorHandler(errorHandler).body(json.toString());
		String response = restCall.put();

		if (response == null) {
			return null;
		}

		try {
			return JsonParser.toTransaction(new JSONObject(response));
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}
}
