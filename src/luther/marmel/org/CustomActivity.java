package luther.marmel.org;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressWarnings("deprecation")
public class CustomActivity extends Activity {

	private WebView mWebView;

	public CustomActivity() {
		mWebView = null;
	}

	public void navigate(String s) {
		mWebView.loadData(s, "text/html", "utf8");
	}

	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.info);
		CookieSyncManager.createInstance(this);
		CookieSyncManager.getInstance().startSync();
		mWebView = (WebView) findViewById(R.id.webinfoweb);
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setDatabaseEnabled(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setUseWideViewPort(true);
	}

	protected void onDestroy() {
		super.onDestroy();
	}

	protected void onStart() {
		super.onStart();
	}
}
