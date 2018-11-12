package tm.fantom.siapp.ui.list;

import com.google.android.material.snackbar.Snackbar;

public class SnackHelper {


    public interface OnDissmissListener {
        void onDissmiss();
    }

    @SuppressWarnings("deprecation")
    public SnackHelper(Snackbar snackbar, final OnDissmissListener listener) {
        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (Snackbar.Callback.DISMISS_EVENT_SWIPE == event
                        || Snackbar.Callback.DISMISS_EVENT_TIMEOUT == event) {
                    listener.onDissmiss();
                }
                super.onDismissed(transientBottomBar, event);
            }

        });
    }
}
