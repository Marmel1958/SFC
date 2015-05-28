package luther.marmel.org;

import java.util.ArrayList;
import java.util.List;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.DefaultResourceProxyImpl;
import org.osmdroid.ResourceProxy;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MapFragment extends Fragment {
	private static final String ARG_SECTION_NUMBER = "section_number";
	private boolean autoLocation = false;
	private boolean visible = false;
	private GeoPoint lastGeoPoint = null;

	public GeoPoint getLastGeoPoint() {
		return lastGeoPoint;
	}

	private void setLastGeoPoint(GeoPoint lastGeoPoint) {
		this.lastGeoPoint = lastGeoPoint;
	}

	public boolean isAutoLocation() {
		return autoLocation;
	}

	public void setAutoLocation(boolean autolocation) {
		this.autoLocation = autolocation;
	}

	public void setEventLocations(Cursor cursor) {
		if (cursor.moveToFirst()) {
			do {
				OverlayItem oItem = new OverlayItem(cursor.getString(cursor
						.getColumnIndex("Ort")), cursor.getString(cursor
						.getColumnIndex("Ort")), new GeoPoint(
						cursor.getDouble(cursor.getColumnIndex("Latitude")),
						cursor.getDouble(cursor.getColumnIndex("Longitude"))));
				overlayItemArray.add(oItem);
			} while (cursor.moveToNext());
		}
	}
	Context context;
	public static MapFragment newInstance(int sectionNumber) {
		MapFragment fragment = new MapFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public MapFragment() {
	}

	public static MapView map;
	private MapController myMapController;

	LocationManager locationManager;

	ArrayList<OverlayItem> overlayItemArray = new ArrayList<OverlayItem>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.maps, container, false);
		context=this.getActivity();
		overlayItemArray.add(new OverlayItem("", "", new GeoPoint(0, 0)));
		map = (MapView) rootView.findViewById(R.id.mapview);
		map.setTileSource(TileSourceFactory.MAPNIK);
		map.setBuiltInZoomControls(true);
		map.setMultiTouchControls(true);
		myMapController = (MapController) map.getController();
		myMapController.setZoom(15);
		createAutoCurrent();

		setLocation(51.866323, 12.64354);
		// Add Scale Bar
		ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(
				this.getActivity());
		map.getOverlays().add(myScaleBarOverlay);
		return rootView;
	}

	public void setLocation(double lat, double lon) {
		setLastGeoPoint(new GeoPoint(lat, lon));
		myMapController.setCenter(new GeoPoint(lat, lon));
	}

	private void createAutoCurrent() {
		DefaultResourceProxyImpl defaultResourceProxyImpl = new DefaultResourceProxyImpl(
				this.getActivity());

		MyItemizedIconOverlay myItemizedIconOverlay = new MyItemizedIconOverlay(
				overlayItemArray, null, defaultResourceProxyImpl);
		map.getOverlays().add(myItemizedIconOverlay);
		locationManager = (LocationManager) this.getActivity()
				.getSystemService(Context.LOCATION_SERVICE);

		// for demo, getLastKnownLocation from GPS only, not from NETWORK
		setSelfLocation();
	}

	private void setSelfLocation() {
		setSelfLocation(false);
	}

	public void setSelfLocation(boolean visible) {
		if (visible) {
			if (isAutoLocation()) {
				this.visible = false;
			} else {
				setAutoLocation(visible);
				this.visible = visible;
			}
		}
		Location lastLocation = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (lastLocation != null) {
			updateLoc(lastLocation);
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		if (locationManager != null) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		if (locationManager != null) {
			locationManager.removeUpdates(myLocationListener);
		}
	}

	private void updateLoc(Location loc) {
		GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(),
				loc.getLongitude());
		if (isAutoLocation() || visible) {
			setLastGeoPoint(locGeoPoint);
			myMapController.setCenter(locGeoPoint);
		}
		setOverlayLoc(loc);

		map.invalidate();
		if (visible) {
			setAutoLocation(false);
			visible = false;

		}
	}

	private void setOverlayLoc(Location overlayloc) {
		GeoPoint overlocGeoPoint = new GeoPoint(overlayloc);
		// ---

		OverlayItem newMyLocationItem = new OverlayItem(
				getString(R.string.menu_selfpoint),
				getString(R.string.menu_selfpoint), overlocGeoPoint);
		overlayItemArray.set(0, newMyLocationItem);
		// ---
	}

	private LocationListener myLocationListener = new LocationListener() {

		@Override
		public void onLocationChanged(Location location) {
			updateLoc(location);
		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

		}

	};

	private class MyItemizedIconOverlay extends

	ItemizedIconOverlay<OverlayItem> {

		public MyItemizedIconOverlay(
				List<OverlayItem> pList,
				org.osmdroid.views.overlay.ItemizedIconOverlay.OnItemGestureListener<OverlayItem> pOnItemGestureListener,
				ResourceProxy pResourceProxy) {
			super(pList, pOnItemGestureListener, pResourceProxy);
		}

		@Override
		public void draw(Canvas canvas, MapView mapview, boolean arg2) {
			super.draw(canvas, mapview, arg2);

			if (!overlayItemArray.isEmpty()) {

				// overlayItemArray have only ONE element only, so I hard code
				// to get(0)
				GeoPoint in = overlayItemArray.get(0).getPoint();

				Point out = new Point();
				mapview.getProjection().toPixels(in, out);

				Bitmap bm = BitmapFactory.decodeResource(getResources(),
						R.drawable.ic_menu_mylocation);
				canvas.drawBitmap(bm, out.x - bm.getWidth() / 2, // shift
																	// the
																	// bitmap
																	// center
						out.y - bm.getHeight() / 2, // shift the bitmap
													// center
						null);

			}
		}

		@Override protected boolean onTap(int index){
			Toast.makeText(context, overlayItemArray.get(index).getTitle(), Toast.LENGTH_LONG).show();
			return true;
		}
	}

}