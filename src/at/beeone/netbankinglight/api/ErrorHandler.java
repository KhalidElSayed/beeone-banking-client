package at.beeone.netbankinglight.api;

public interface ErrorHandler {
	void onError(HttpError status);
}
