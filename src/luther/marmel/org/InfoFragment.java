package luther.marmel.org;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

@SuppressWarnings("deprecation")
public class InfoFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";
	private WebView mWebView;

	public static InfoFragment newInstance(int sectionNumber) {
		InfoFragment fragment = new InfoFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public InfoFragment() {
	}

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.info, container, false);
		CookieSyncManager.createInstance(this.getActivity());
		CookieSyncManager.getInstance().startSync();
		mWebView = (WebView) rootView.findViewById(R.id.webinfoweb);
		mWebView.setWebViewClient(new WebViewClient());
		mWebView.getSettings().setBuiltInZoomControls(true);
		mWebView.getSettings().setSupportZoom(true);
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setDomStorageEnabled(true);
		mWebView.getSettings().setDatabaseEnabled(true);
		mWebView.getSettings().setLoadWithOverviewMode(true);
		mWebView.getSettings().setUseWideViewPort(true);
		loadUrl(this.getActivity().getString(R.string.url));
		return rootView;
	}

	public void loadUrl(String Url) {
		mWebView.loadUrl(Url);
	}

}