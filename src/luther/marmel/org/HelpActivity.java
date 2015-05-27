package luther.marmel.org;

import java.util.Locale;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;

public class HelpActivity extends CustomActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String string = "";
		InputStream inputStream;
		try {
			if (Locale.getDefault().getLanguage().startsWith("de")) {
				inputStream = getAssets().open("help_de.html");
			} else {
				inputStream = getAssets().open("help.html");
			}
			String s1;
			byte abyte0[] = new byte[inputStream.available()];
			inputStream.read(abyte0);
			inputStream.close();
			s1 = new String(abyte0);
			string = s1;
			string = string.replace("$1", getString(R.string.url));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			string = "???";
		}
		navigate(string);
		return;
	}

}
