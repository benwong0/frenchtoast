package com.hivemapper.android.hivemapper.ui.views.frenchtoast;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.InvalidParameterException;

/**
 * Toast with customizable styles and durations.
 */
public class FrenchToast {

    private int duration = 10;

    private Context context;

    // UI
    private LinearLayout baseView;
    private TextView textView;

    /**
     * Create a new FrenchToast object.
     *
     * @param context The context to use. Usually your {@link android.app.Application}
     *                or {@link android.app.Activity} object.
     */
    public FrenchToast(Context context) {
        this.context = context;

        setupView();
    }

    /**
     * Set toast display duration.
     *
     * @param duration The display duration.
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Get toast display duration.
     *
     * @return The display duration.
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Set toast text
     *
     * @param text The text to display.
     */
    public void setText(CharSequence text) {
        textView.setText(text);
    }

    /**
     * Get toast text.
     *
     * @return The toast text.
     */
    public CharSequence getText() {
        return textView.getText();
    }

    /**
     * Set background color.
     *
     * @param backgroundColor Background color.
     */
    public void setBackgroundColor(int backgroundColor) {
        baseView.setBackgroundColor(backgroundColor);
    }

    /**
     * Set background resource.
     *
     * @param backgroundResource Resource ID.
     */
    public void setBackgroundResource(int backgroundResource) {
        baseView.setBackgroundResource(backgroundResource);
    }

    /**
     * Set toast text color.
     *
     * @param textColor Text color.
     */
    public void setTextColor(int textColor) {
        textView.setTextColor(textColor);
    }

    /**
     * Set toast text size.
     *
     * @param size The size of the text.
     */
    public void setTextSize(float size) {
        textView.setTextSize(size);
    }

    /**
     * Set padding around toast content.
     *
     * @param left   Left padding.
     * @param top    Top padding.
     * @param right  Right padding.
     * @param bottom Bottom padding.
     */
    public void setPadding(int left, int top, int right, int bottom) {
        baseView.setPadding(left, top, right, bottom);
    }

    /**
     * Show this toast on screen.
     */
    public void show() {
        Stack.getInstance().add(this);
    }

    /**
     * Remove this toast.
     */
    public void hide() {
        Stack.getInstance().remove(this);
    }

    /**
     * Helper method to create a toast.
     *
     * @param context  Toast context.
     * @param text     Toast text.
     * @param duration Display duration.
     * @return The FrenchToast object.
     */
    public static FrenchToast make(Context context, CharSequence text, int duration) {
        if (context == null) {
            throw new InvalidParameterException("Context cannot be null");
        }

        FrenchToast toast = new FrenchToast(context);
        toast.setText(text);
        toast.setDuration(duration);

        return toast;
    }

    /**
     * Set margin around main toast container. This is the margin surrounding all toasts.
     * This is a global setting.
     *
     * @param left   Left margin.
     * @param top    Top margin.
     * @param right  Right margin.
     * @param bottom Bottom margin.
     */
    public static void setMargins(int left, int top, int right, int bottom) {
        Stack.getInstance().setMargins(left, top, right, bottom);
    }

    /**
     * Clear all toast on screen.
     */
    public static void clear() {
        Stack.getInstance().clear();
    }

    /**
     * Get the context used by this toast, usually your {@link android.app.Application}
     * or {@link android.app.Activity} object.
     *
     * @return Context being used.
     */
    Context getContext() {
        return context;
    }

    /**
     * Get toast view.
     *
     * @return The toast UI view.
     */
    View getView() {
        return baseView;
    }

    private void setupView() {
        textView = new TextView(context);
        textView.setTypeface(null, Typeface.BOLD);

        baseView = new LinearLayout(context);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        baseView.addView(textView, layoutParams);
    }
}
