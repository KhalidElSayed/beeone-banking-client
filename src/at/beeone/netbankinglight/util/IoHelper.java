package at.beeone.netbankinglight.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class IoHelper {


	public static StringBuilder readStream(InputStream inputStream)
			throws IOException {

		BufferedReader r = new BufferedReader(
				new InputStreamReader(inputStream));
		StringBuilder total = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			total.append(line);
		}
		return total;
	}

	private IoHelper() {
	}

}
