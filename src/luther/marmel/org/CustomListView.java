package luther.marmel.org;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

public class CustomListView extends ListView {

	private float oldDist;
	private boolean scrollable;
	private int touchmode;

	public CustomListView(Context context) {
		super(context);
		oldDist = 1.0F;
		scrollable = true;
		touchmode = 0;
	}

	public CustomListView(Context context, AttributeSet attributeset) {
		super(context, attributeset);
		oldDist = 1.0F;
		scrollable = true;
		touchmode = 0;
	}

	public CustomListView(Context context, AttributeSet attributeset, int i) {
		super(context, attributeset, i);
		oldDist = 1.0F;
		scrollable = true;
		touchmode = 0;
	}

	private float spacing(MotionEvent motionevent) {
		float f = motionevent.getX(0) - motionevent.getX(1);
		float f1 = motionevent.getY(0) - motionevent.getY(1);
		return (float) Math.sqrt(f * f + f1 * f1);
	}

	public boolean dispatchTouchEvent(MotionEvent motionevent) {
		if (!scrollable && motionevent.getAction() == 2) {
			return true;
		} else {
			onTouch(this, motionevent);
			return super.dispatchTouchEvent(motionevent);
		}
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public boolean onTouch(View view, MotionEvent motionevent) {
		switch (0xff & motionevent.getAction()) {
		case 3: // '\003'
		case 4: // '\004'
		default:
			break;

		case 2: // '\002'
			if (touchmode == 1) {
				float f = spacing(motionevent);
				if (f > 10F) {
					float f1 = f / oldDist;
					if (f1 > 1.0F) {
						// AgendaFragment.aa.zoom(false);
						return false;
					}
					if (f1 < 1.0F) {
						// AgendaFragment.aa.zoom(true);
						return false;
					}
				}
			}
			break;

		case 6: // '\006'
			touchmode = 0;
			return false;

		case 5: // '\005'
			oldDist = spacing(motionevent);
			if (oldDist > 10F) {
				touchmode = 1;
				return false;
			}
			break;
		}
		return false;
	}

	public void setScrollable(boolean flag) {
		scrollable = flag;
	}
}
