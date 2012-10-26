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
