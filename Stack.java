package com.hivemapper.android.hivemapper.ui.views.frenchtoast;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.concurrent.ConcurrentHashMap;

/**
 * French toast stack
 */
class Stack {
    private static final String TAG = "Stack";
    private static Stack instance;

    private LinearLayout toastStackView;
    private ConcurrentHashMap<FrenchToast, Runnable> toastStack = new ConcurrentHashMap<>();

    private int[] margins = new int[]{0, 0, 0, 0};

    private Handler mainLoopHandler = new Handler(Looper.getMainLooper());

    public synchronized static Stack getInstance() {
        if (instance == null) instance = new Stack();
        return instance;
    }

    public void add(FrenchToast toast) {
        showToast(toast);
    }

    public void remove(final FrenchToast toast) {
        mainLoopHandler.post(new Runnable() {
            @Override
            public void run() {
                if (toastStack.containsKey(toast)) {
                    Log.d(TAG, "Removing");

                    Runnable runnable = toastStack.remove(toast);
                    View view = toast.getView();

                    toastStackView.removeView(view);

                    if (runnable != null) {
                        mainLoopHandler.removeCallbacks(runnable);
                    }

                    if (toastStackView != null && toastStackView.getChildCount() == 0) {
                        WindowManager windowManager = (WindowManager) toast.getContext().getSystemService(Context.WINDOW_SERVICE);
                        windowManager.removeView(toastStackView);
                        toastStackView = null;
                    }

                    Log.d(TAG, "Array Size: " + toastStack.size());
                }
            }
        });
    }

    public void clear() {
        mainLoopHandler.post(new Runnable() {
            @Override
            public void run() {
                if (toastStack.size() > 0) {
                    for (Runnable runnable : toastStack.values()) {
                        mainLoopHandler.removeCallbacks(runnable);
                    }
                    toastStack.clear();
                }

                if (toastStackView != null) {
                    for (int i = 0; i < toastStackView.getChildCount(); i++) {
                        toastStackView.removeView(toastStackView.getChildAt(i));
                    }

                    WindowManager windowManager = (WindowManager) toastStackView.getContext().getSystemService(Context.WINDOW_SERVICE);
                    windowManager.removeView(toastStackView);
                    toastStackView = null;
                }
            }
        });
    }

    public void setMargins(int left, int top, int right, int bottom) {
        margins[0] = left;
        margins[1] = top;
        margins[2] = right;
        margins[3] = bottom;

        if (toastStackView != null)
            toastStackView.setPadding(margins[0], margins[1], margins[2], margins[3]);
    }

    private void showToast(final FrenchToast toast) {
        mainLoopHandler.post(new Runnable() {
            @Override
            public void run() {
                Context context = toast.getContext();
                final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                int duration = toast.getDuration();
                final View contentView = toast.getView();

                createToastStackView(context, windowManager);

                // Clean up
                if (toastStack.containsKey(toast)) {
                    toastStackView.removeView(contentView);
                    mainLoopHandler.removeCallbacks(toastStack.get(toast));
                }

                // Add toast to screen
                final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
                layoutParams.setMargins(0, 10, 0, 10);
                toastStackView.addView(contentView, 0, layoutParams);

                // Remove toast after delay
                if (duration < 1) duration = 1;
                final int displayDuration = duration * 1000;
                Runnable removeCallback = new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Removing toast");
                        remove(toast);
                    }
                };
                toastStack.put(toast, removeCallback);
                mainLoopHandler.postDelayed(removeCallback, displayDuration);
            }
        });
    }

    private void createToastStackView(Context context, WindowManager windowManager) {
        if (toastStackView == null) {
            toastStackView = new LinearLayout(context);
            toastStackView.setOrientation(LinearLayout.VERTICAL);
            toastStackView.setPadding(margins[0], margins[1], margins[2], margins[3]);

            WindowManager.LayoutParams windowLayoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_PHONE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);

            windowLayoutParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
            windowManager.addView(toastStackView, windowLayoutParams);
        }
    }
}
