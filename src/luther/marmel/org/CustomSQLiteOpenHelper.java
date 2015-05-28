package luther.marmel.org;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.*;

public class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

	public CustomSQLiteOpenHelper(Context context) {
		super(context, "luther.db", null, 1);
	}

	public static void CreateDataBase(Context context, String path,String dbfile) {
		InputStream inputstream;
		FileOutputStream fileoutputstream;
		byte abyte0[];
		File filetest=new File(dbfile);
		if (filetest.exists()) filetest.delete();
		File file = new File(path);
		if (!file.exists())
			file.mkdirs();
		try {
			inputstream = context.getAssets().open("luther.db");
			fileoutputstream = new FileOutputStream(dbfile);
			abyte0 = new byte[1024];
			while (true) {
				int i = inputstream.read(abyte0);
				if (i <= 0) {
					inputstream.close();
					fileoutputstream.close();
					return;

				}
				fileoutputstream.write(abyte0, 0, i);
			}
		} catch (IOException e) {
		}

	}

	Cursor getAgendaCursor(int i) {
		SQLiteDatabase sqlitedatabase = getReadableDatabase();
		String s = "SELECT * FROM Agenda";
		if (i != -1)
			s = (new StringBuilder(String.valueOf(s)))
					.append(" WHERE Location=").append(String.valueOf(i))
					.toString();
		return sqlitedatabase.rawQuery((new StringBuilder(String.valueOf(s)))
				.append(" ORDER BY StartTime").toString(), null);
	}

	Cursor getAgendaValueCursor(int i) {
		SQLiteDatabase sqlitedatabase = getReadableDatabase();
		String s = "select agenda._id,Ort,Description,Starttime,Endtime From Agenda Join Location On agenda.location=location._id";
		if (i != -1)
			s = (new StringBuilder(String.valueOf(s)))
					.append(" WHERE agenda._id=").append(String.valueOf(i))
					.toString();
		return sqlitedatabase.rawQuery(s, null);
	}

	Cursor getLocationCursor() {
		SQLiteDatabase sqlitedatabase = getReadableDatabase();
		if (sqlitedatabase == null)
			return null;
		else
			return sqlitedatabase.rawQuery("SELECT * FROM Location WHERE visible='true'", null);
	}

	public void onCreate(SQLiteDatabase sqlitedatabase) {
	}

	public void onUpgrade(SQLiteDatabase sqlitedatabase, int i, int j) {
	}
}
