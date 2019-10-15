package com.sagar.thumbnaildownloader.alerts;

import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.sagar.thumbnaildownloader.R;

public class MyToast {

    public enum Type {
        SUCCESS_SNACK_BAR,
        FAILURE_SNACK_BAR
    }

    public static void show(View parent, String message, Type type) {

        if (type == Type.SUCCESS_SNACK_BAR) {
            showSnackBar(parent, message, R.color.color_toast_green);
        } else if (type == Type.FAILURE_SNACK_BAR) {
            showSnackBar(parent, message, R.color.color_toast_red);
        }

    }

    private static void showSnackBar(View parent, String message, int color) {

        Snackbar snackbar = Snackbar.make(parent, message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(parent.getContext(), color));
        ViewGroup.LayoutParams layoutParams = snackBarView.getLayoutParams();
        if (!(layoutParams instanceof ViewGroup.MarginLayoutParams)) {
            return;
        }

        final ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layoutParams;

        params.setMargins(0,
                params.topMargin,
                0,
                0);

        snackBarView.setLayoutParams(params);
        snackbar.show();

    }

}
