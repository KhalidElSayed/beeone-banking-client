package at.beeone.netbankinglight.api;

public class HttpError {

	private int mStatusCode;
	private String mStatusMessage;
	private String errorMessage;

	public HttpError(int code, String message, String errorMessage) {
		mStatusCode = code;
		mStatusMessage = message;
	}

	public int getStatusCode() {
		return mStatusCode;
	}

	public String getStatusMessage() {
		return mStatusMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

}
