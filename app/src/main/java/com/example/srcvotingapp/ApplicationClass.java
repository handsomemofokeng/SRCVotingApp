package com.example.srcvotingapp;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.backendless.BackendlessUser;
import java.util.List;

public class ApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * This method clears text fields
     *
     * @param fields to be cleared
     */
    public static void clearFields(EditText... fields) {
        for (EditText field : fields) field.setText("");

    }

    /**
     * @param views to be hidden on given context
     */
    public static void hideViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.GONE);
        }
    }

    /**
     * @param views to be shown on given context
     */
    public static void showViews(View... views) {
        for (View view : views) {
            view.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method checks if the email provided is valid
     *
     * @param etEmail to be checked for validity
     * @return true if email is valid
     */
    public static boolean isEmailValid(EditText etEmail) {
        String email = etEmail.getText().toString().trim();

        boolean isValid = email.contains("@") && (email.endsWith(".com") || email.endsWith(".za"));
        if (!isValid) {
            etEmail.setError("Invalid email format");
        }

        return isValid;
    }

    /**
     * This method checks if the password is valid
     *
     * @param password to be checked
     * @return true if password matches the criteria
     */
    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 2;
    }

    /**
     * This method checks if all field provided in parameters have values
     *
     * @param fields to be checked for validity
     * @return True if all fields have values, False if not.
     */
    public static boolean isValidFields(EditText... fields) {
        boolean isValid = true;
        for (EditText field : fields) {
            if (field.getText().toString().trim().isEmpty()) {
                field.setError("This field is required!");
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * This method checks if both passwords provided by user match and sets error message if not
     *
     * @param etPassword field to be compared with
     * @param etConfirm  another field to be compared with
     * @return True if passwords match, False if not.
     */
    public static boolean isPasswordsMatching(EditText etPassword, EditText etConfirm) {
        boolean isMatching = etPassword.getText().toString().trim().equals(etConfirm.getText().toString().trim());
        if (!isMatching) {
            etPassword.setError("Passwords must match!");
            etConfirm.setError("Passwords must match!");
        } else {
            etPassword.setError(null);
            etConfirm.setError(null);
        }

        return isMatching;
    }

    /**
     * This method returns a detailed String of a Backendeless User object
     *
     * @param user Backendless User object
     * @return detailed string of a Backendless User object
     */
    public static String getUserString(BackendlessUser user) {

        String userStr = "Unidentified User";
        if (user != null)
            userStr = String.format("%s, %s (%s)", user.getProperty("name"), user.getProperty("surname"),
                    user.getEmail());
        return userStr;
    }

    /**
     * This method sets up an Action Bar for any given Activity
     *
     * @param actionBar to be shown on Activity
     * @param title     of the Activity
     * @param subtitle  shows additional details
     * @return inflated and customised action bar
     */
    public static ActionBar setupActionBar(ActionBar actionBar, String title, String subtitle) {

        /*
          Setup a new action bar
         */
        assert actionBar != null;
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setTitle(" " + title);
        actionBar.setSubtitle(" " + subtitle);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        return actionBar;
    }

    /**
     * A brief message to be displayed as feedback to user
     *
     * @param context   in which the toast will appear
     * @param toastView customised view of a toast
     * @param message   message to be displayed
     */
    public static void showCustomToast(Context context, View toastView, String message) {

        TextView tvToast = toastView.findViewById(R.id.tv_toast);
        tvToast.setText(message);
        Toast toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toast.setGravity(Gravity.TOP | Gravity.FILL_HORIZONTAL, 0, 0);
        toast.show();
    }

    /**
     * @param context in which the Activity is at
     * @return True if phone has active internet connection, False if not
     */
    public static boolean isPhoneConnected(Context context) {

        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                isConnected = true;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                isConnected = true;
            }
        }
        return isConnected;
    }

    /**
     * This method populates a spinner with items on a list parameter
     *
     * @param context in which the spinner is at
     * @param spinner to be populated with string items
     * @param list    that contains items to be added to the spinner
     */
    public static void loadSpinnerValues(Context context, Spinner spinner, List<String> list) {
        spinner.setAdapter((new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, list)));
    }

}
