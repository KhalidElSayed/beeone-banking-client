package at.beeone.netbankinglight.api.model;

public class HttpResponse {

	private int mStatusCode;
	private String mStatusMessage;
	private String mBody;

	public HttpResponse(int statusCode, String statusMessage, String body) {
		mStatusCode = statusCode;
		mStatusMessage = statusMessage;
		mBody = body;
	}

	public int getStatusCode() {
		return mStatusCode;
	}

	public void setStatusCode(int code) {
		this.mStatusCode = code;
	}

	public String getStatusMessage() {
		return mStatusMessage;
	}

	public void setStatusMessage(String message) {
		this.mStatusMessage = message;
	}

	public String getBody() {
		return mBody;
	}

}
