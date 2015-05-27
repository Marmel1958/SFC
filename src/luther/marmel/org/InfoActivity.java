package luther.marmel.org;

import java.util.GregorianCalendar;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

public class InfoActivity extends CustomActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String string = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
		string += "<title>";
		string += getString(R.string.info);
		string += "</title></head><body><h1>";
		string += getString(R.string.info) + (" ")
				+ getString(R.string.app_text);
		string = string.replace("ü", "&uuml;");
		string += "</h1><hr /><p>Version: ";
		try {
			string += getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			string += "????";
		}
		string += "</p><p>&copy; Copyright by Marmel, 2013";
		int i = (new GregorianCalendar()).get(1);
		if (2013 < i)
			string += " - " + Integer.toString(i);
		string += ". All rights reserved.<br /><br /><br /><br /></p>";
		string += getString(R.string.source);
		string += ": <a href='";
		string += getString(R.string.url);
		string += "'>Wittenberg Stadtmarketing</a><p></p><p>";
		string += getString(R.string.library);
		String string1 = string.replace("$1",
				"<a href='http://openstreetmap.org'>OpenStreetMap</a>");
		string = string1.replace("$2",
				"<a href='http://code.google.cm/p/osmdroid'>osmdroid</a>");
		string += "</p><p>";
		string += getString(R.string.library1);
		string1 = string
				.replace("$1", "<a href='http://mapquest'>MapQuest</a>");
		string = string1
				.replace("$2",
						"<a href='http://code.google.cm/p/osmbonuspack'>osmbonuspack</a>");
		string += "</p></body></html>";
		navigate(string);
		return;
	}

}
