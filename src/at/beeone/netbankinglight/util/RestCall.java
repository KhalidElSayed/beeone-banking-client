package at.beeone.netbankinglight.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import at.beeone.netbankinglight.api.ErrorHandler;
import at.beeone.netbankinglight.api.HttpError;

public class RestCall {

	private static enum MethodType {
		get, post, put, delete
	}

	public static class Builder {
		private URL mEndpoint;
		private Map<String, String> mHeaders = new HashMap<String, String>();
		private Map<String, String> mParams = new HashMap<String, String>();
		private String mAuthTokenHeader;
		private String mAuthToken;

		public Builder endpoint(URL endpoint) {
			mEndpoint = endpoint;
			return this;
		}

		public Builder authTokenField(String name) {
			mAuthTokenHeader = name;
			return this;
		}

		public Builder param(String name, String value) {
			mParams.put(name, value);
			return this;
		}

		public Builder header(String name, String value) {
			mHeaders.put(name, value);
			return this;
		}

		public RestCall build() {
			RestCall restCall = new RestCall(this);
			return restCall;
		}

	}

	private Builder mBuilder;

	private RestCall(Builder builder) {
		mAuthToken = builder.mAuthToken;
		mAuthTokenHeader = builder.mAuthTokenHeader;
		mEndpoint = builder.mEndpoint;
		mHeaders = new HashMap<String, String>(builder.mHeaders);
		mParams = new HashMap<String, String>(builder.mParams);
		mBuilder = builder;
	}

	private URL mEndpoint;
	private Map<String, String> mHeaders;
	private Map<String, String> mParams;
	private String mResource;
	private String mBody;
	private ErrorHandler mErrorHandler;
	private Integer mExpectedReturnCode;
	private String mAuthTokenHeader;
	private String mAuthToken;

	public RestCall expectedHttpSuccessCode(Integer code) {
		mExpectedReturnCode = code;
		return this;
	}

	public RestCall errorHandler(ErrorHandler errorHandler) {
		mErrorHandler = errorHandler;
		return this;
	}

	public RestCall resource(String resource) {
		mResource = resource;
		return this;
	}

	public RestCall body(String body) {
		mBody = body;
		return this;
	}

	public RestCall param(String name, String value) {
		mParams.put(name, value);
		return this;
	}

	public RestCall header(String name, String value) {
		mHeaders.put(name, value);
		return this;
	}

	private String call(MethodType method) {
		if (mEndpoint == null) {
			throw new AssertionError("no endpoint given!");
		}
		return http(method, mEndpoint, mResource, mBody, mHeaders, mParams,
				mExpectedReturnCode, mErrorHandler, mAuthTokenHeader,
				mAuthToken, mBuilder);
	}

	public String post() {
		return call(MethodType.post);
	}

	public String put() {
		return call(MethodType.put);
	}

	public String get() {
		return call(MethodType.get);
	}

	public String delete() {
		return call(MethodType.delete);
	}

	private static String http(MethodType method, URL apiEndpoint,
			String resource, String body, Map<String, String> headers,
			Map<String, String> params, Integer expectedReturnCode,
			ErrorHandler errorHandler, String authTokenHeader,
			String authToken, Builder builder) {
		try {

			resource = addGetParameter(method, resource, params);

			URL endpoint = new URL(apiEndpoint, resource);

			HttpURLConnection conn = (HttpURLConnection) endpoint
					.openConnection();
			String methodString = method.name().toUpperCase();
			conn.setRequestMethod(methodString);

			if (headers != null) {
				for (Entry<String, String> entry : headers.entrySet()) {
					conn.addRequestProperty(entry.getKey(), entry.getValue());
				}
			}

			if (authToken != null) {
				conn.addRequestProperty(authTokenHeader, authToken);
			}

			addRequestBody(method, body, params, endpoint, conn);
			return handleResponse(expectedReturnCode, errorHandler, conn,
					authTokenHeader, builder);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static String handleResponse(Integer expectedReturnCode,
			ErrorHandler errorHandler, HttpURLConnection conn,
			String authTokenHeader, Builder builder) throws IOException {

		Integer code = conn.getResponseCode();

		builder.mAuthToken = conn.getHeaderField(authTokenHeader);

		String responseBody;

		boolean codeAsExpected = expectedReturnCode != null
				&& code.equals(expectedReturnCode);
		boolean isSuccessCode = code >= 200 && code < 300;
		//TODO:ben: test this
		if (codeAsExpected || (!codeAsExpected && isSuccessCode)) {
			responseBody = IoHelper.readStream(conn.getInputStream())
					.toString();
		} else {
			try {
				responseBody = IoHelper.readStream(conn.getInputStream())
						.toString();
			} catch (IOException e) {
				responseBody = null;
			}

			if (errorHandler == null) {
				//TODO not authorizedexception and other stuff, because not all exceptions can be handled through status codes.
				throw new RuntimeException("Error: " + conn.getResponseCode()
						+ ", " + conn.getResponseMessage()
						+ ", response body: " + responseBody);
			} else {

				HttpError error = new HttpError(code,
						conn.getResponseMessage(), responseBody);
				errorHandler.onError(error);
			}
		}
		return responseBody;
	}

	private static String addGetParameter(MethodType method, String resource,
			Map<String, String> params) throws UnsupportedEncodingException {
		if (method == MethodType.get && params != null && !params.isEmpty()) {
			resource = resource + "?" + makeParameterString(params);
		}
		return resource;
	}

	private static void addRequestBody(MethodType method, String body,
			Map<String, String> params, URL endpoint, HttpURLConnection conn)
			throws IOException, UnsupportedEncodingException,
			MalformedURLException {
		if (method == MethodType.post || method == MethodType.put) {
			conn.setDoOutput(true);
			conn.setUseCaches(false);

			if (params != null && !params.isEmpty()) {
				PrintWriter out = new PrintWriter(conn.getOutputStream());
				out.print(makeParameterString(params));
				out.close();
			} else if (body != null) {
				PrintWriter out = new PrintWriter(conn.getOutputStream());
				out.print(body);
				out.close();
			}
		}

		if (method == MethodType.get) {
			if (params != null && !params.isEmpty()) {
				endpoint = new URL(endpoint, makeParameterString(params));
			}
		}
	}

	private static String makeParameterString(Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
		boolean isFirstParam = true;
		for (Entry<String, String> param : params.entrySet()) {
			if (!isFirstParam) {
				sb.append("&");
			}
			sb.append(param.getKey()).append("=")
					.append(URLEncoder.encode(param.getValue(), "UTF-8"));
			isFirstParam = false;
		}
		return sb.toString();
	}

}
