package luther.marmel.org;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class CustomCursorAdapter extends CursorAdapter {

	@SuppressWarnings("deprecation")
	public CustomCursorAdapter(Context context, Cursor c) {
		super(context, c);
}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		LayoutInflater layoutInflater=LayoutInflater.from(context) ;
		View retView=layoutInflater.inflate(R.layout.row, parent,false);
		return retView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		TextView timeText=(TextView) view.findViewById(R.id.timeText);
		TextView agendaText=(TextView) view.findViewById(R.id.agendaText);
		agendaText.setText(cursor.getString(cursor.getColumnIndex("Description")));
		String start=cursor.getString(cursor.getColumnIndex("StartTime"));
		String ende=cursor.getString(cursor.getColumnIndex("EndTime"));
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        int textColor=R.color.black;
        try {
			Date date =  simpledateformat.parse(start);
	        Date date2 = simpledateformat.parse(ende);
	        Date dn=new Date();
	        if(dn.before(date))
	        	textColor=R.color.green;
	        if(dn.after(date2))
	        	textColor=R.color.red;
	        start=DateFormat.format("dd.MM.yy\n\nHH:mm",date).toString();
	        ende=DateFormat.format("HH:mm",date2).toString();
		} catch (ParseException e) {
		}
        timeText.setText(start+" - "+ende);
        timeText.setTextColor(view.getResources().getColor(textColor));
        agendaText.setTextColor(view.getResources().getColor(textColor));
 	}

}
