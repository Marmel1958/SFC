package luther.marmel.org;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AgendaFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static AgendaFragment newInstance(int sectionNumber) {
		AgendaFragment fragment = new AgendaFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public AgendaFragment() {
	}

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.agenda, container, false);
		CustomSQLiteOpenHelper sql=new CustomSQLiteOpenHelper(this.getActivity());
		Cursor c=sql.getLocationCursor();
		SimpleCursorAdapter sca=new SimpleCursorAdapter(this.getActivity(), android.R.layout.simple_spinner_item, c, new String[]{"Ort"},new int[]{android.R.id.text1});
		final CustomListView listView=(CustomListView) rootView.findViewById(R.id.listview);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				CustomSQLiteOpenHelper sql=new CustomSQLiteOpenHelper(view.getContext());
				Cursor ca=sql.getAgendaValueCursor((int) id);
				ca.moveToFirst();
				String text=ca.getString(ca.getColumnIndex("Ort"));
				text+=":\n"+ca.getString(ca.getColumnIndex("Description"));
				String start=ca.getString(ca.getColumnIndex("StartTime"));
				String ende=ca.getString(ca.getColumnIndex("EndTime"));
		 		        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		        try {
					Date date =  simpledateformat.parse(start);
			        Date date2 = simpledateformat.parse(ende);
			        start=DateFormat.format("dd.MM.yy HH:mm",date).toString();
			        ende=DateFormat.format("HH:mm",date2).toString();
				} catch (ParseException e) {
				}
				Toast.makeText(view.getContext(), start+" - "+ende+"\n"+text, Toast.LENGTH_LONG).show();
				ca.close();
			}});
	
		Spinner spinner=(Spinner)rootView.findViewById(R.id.alertcheck);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				CustomSQLiteOpenHelper sql=new CustomSQLiteOpenHelper(view.getContext());
				Cursor ca=sql.getAgendaCursor((int) id);
				CustomCursorAdapter cca=new CustomCursorAdapter(view.getContext(),ca);
				listView.setAdapter(cca);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				
			}

			});
		sca.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spinner.setAdapter(sca);
		return rootView;
	}


}
