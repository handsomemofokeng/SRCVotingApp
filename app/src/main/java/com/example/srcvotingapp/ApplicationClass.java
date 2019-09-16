package com.example.srcvotingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.service.autofill.RegexValidator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.persistence.DataQueryBuilder;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class ApplicationClass extends Application {

    public static final String APPLICATION_ID = "YOUR_APP_ID";
    public static final String API_KEY = "YOUR_API_KEY";
    public static final String SERVER_URL = "https://api.backendless.com",
            MY_SHARED_PREFERENCES_NAME = "com.example.srcvotingapp";
    public static final int REQUEST_PHONE = 123;

    public static String[] Portfolios = {"President", "Deputy President", "Secretary General",
            "Financial Officer", "Constitutional And Legal Affairs", "Sports Officer",
            "Public Relations Officer", "Health and Welfare Officer", "Projects and Campaign Officer",
            "Student Affairs", "Equity and Diversity Officer", "Transformation Officer"};

    //User Property Constants
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String GENDER = "gender";
    public static final String ETHNICITY = "ethnicity";
    public static final String COURSE = "course";
    public static final String HAS_VOTED = "hasVoted";
    public static final String IS_CANDIDATE = "isCandidate";
    public static final String ROLE = "role";

    //Party Property Constants
    public static final String PARTY_ID = "partyID";
    public static final String PARTY_NAME = "partyName";

    public static boolean isConfirmed;

    @Override
    public void onCreate() {
        super.onCreate();
        // TODO: 2019/08/29 Initialize Backendless

//        Backendless.setUrl(SERVER_URL);
//        Backendless.initApp(getApplicationContext(),
//                APPLICATION_ID,
//                API_KEY);


    }

    /**
     * This method creates a Query to find items by specified parameters
     *
     * @param property which property to look for in a table
     * @param value    to be used to filter
     * @param sortBy   to be used to arrange
     * @return query that specifies which specific items to return.
     */
    public static DataQueryBuilder selectQuery(String property, String value, String sortBy) {
        String whereClause = property + " = '" + value + "'";
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create().setWhereClause(whereClause);
        dataQueryBuilder.setPageSize(100).setOffset(0).setSortBy(sortBy);
        return dataQueryBuilder;
    }

    /**
     * This method selects all objects within any Table in Backendless, every @NonNull object has
     * a 'created' property hence the whereClause is "created != null"
     *
     * @param sortBy sorts the list according to the property within the specified table
     * @return a query to be passed on Backendless Async
     */
    public static DataQueryBuilder selectAllQuery(String sortBy) {
        String whereClause = "created != null";
        DataQueryBuilder dataQueryBuilder = DataQueryBuilder.create().setWhereClause(whereClause);
        dataQueryBuilder.setPageSize(100).setOffset(0).setSortBy(sortBy);
        return dataQueryBuilder;
    }

    /**
     * Switch visibility of Views
     *
     * @param showView to be shown
     * @param hideView to be hidden
     */
    public static void switchViews(View showView, View hideView) {
        showViews(showView);
        hideViews(hideView);
    }

    /**
     * This method reuses the Scan Barcode Activity
     *
     * @param activity in which the scan results will be returned.
     */
    public static void scanStudentCard(Activity activity) {
        IntentIntegrator integrator = new IntentIntegrator(activity);
        integrator.setCaptureActivity(Portrait.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan your student card...");
        integrator.initiateScan();
    }

    /**
     * This method resets the Radio Group
     *
     * @param radioGroup to be reset
     */
    public static void clearRadioGroup(RadioGroup radioGroup) {
        radioGroup.clearCheck();
    }

    /**
     * This method uncheck radio buttons
     *
     * @param radioButtons to be unchecked
     */
    public static void uncheckRadioButton(RadioButton... radioButtons) {
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isChecked()) {
                radioButton.setChecked(false);
            }
        }
    }

    /**
     * This method determines if a RadioButton is checked in a RadioGroup
     *
     * @param radioGroup to be checked
     * @return -1 if nothing was selected, or the ID of the RadioButton checked
     */
    public static boolean isRadioChecked(RadioButton... radioGroup) {

        boolean isChecked = false;

        for (RadioButton radioButton : radioGroup) {
            if (radioButton.isChecked()) {
                isChecked = true;
                break;
            }
        }

        return isChecked;
    }

    public static String getSelectedRadio(RadioButton... radioButtons) {
        String selectedRadio = "";
        for (RadioButton radioButton : radioButtons) {
            if (radioButton.isChecked()) {
                selectedRadio = radioButton.getText().toString();
                break;
            }

        }

        return selectedRadio;
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
        final Pattern EMAIL_REGEX = Pattern.compile("[a-z0-9!#$%&'*+/=?^_`{|}~-]" +
                "+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)" +
                "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", Pattern.CASE_INSENSITIVE);

        boolean isValid = EMAIL_REGEX.matcher(email).matches(); // email.contains("@") && (email.endsWith(".com") || email.endsWith(".za"));
        if (!isValid) {
            etEmail.setError("Invalid email format");
            etEmail.requestFocus();
        }
//         TODO: 2019/09/13 Delete for Testing Purposes
//        else{
//            if (!email.endsWith("stud.cut.ac.za")){
//                isValid = false;
//                etEmail.setError("Invalid CUT Student Email!");
//            }else{
//                isValid = true;
//                etEmail.setError(null);
//            }
//        }

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
        EditText view = null;
        for (int i = (fields.length - 1); i >= 0; i--) {
            EditText field = fields[i];
            if (field.getText().toString().trim().isEmpty()) {
                view = field;
                field.setError("This field is required!");
                isValid = false;
            }
        }
        if (view != null)
            view.requestFocus();
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

        boolean isMatching = false;

        if (isValidFields(etPassword, etConfirm)) {

            isMatching = etPassword.getText().toString().trim()
                    .equals(etConfirm.getText().toString().trim());

            if (!isMatching) {

                etPassword.setError("Passwords must match!");
                etConfirm.setError("Passwords must match!");

            } else {

                etPassword.setError(null);
                etConfirm.setError(null);
            }
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
            userStr = String.format("%s %s, %s", user.getProperty(NAME),
                    user.getProperty(SURNAME), user.getEmail());
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
        spinner.setAdapter((new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_dropdown_item, list)));
        spinner.setGravity(View.TEXT_ALIGNMENT_CENTER);
    }

    /**
     * This method sets the text of an editText to a selected spinner value
     *
     * @param spinner to extract a prefix text
     */
    public static String getSpinnerValue(Spinner spinner) {
        return spinner.getSelectedItem().toString();
    }

    /**
     * Checks if a spinner (dropdown list) option is selected
     *
     * @param spinners to be checked
     * @return true if a valid option is selected, false if not.
     */
    public static boolean isValidSpinner(Spinner... spinners) {
        boolean isValid = true;
        for (Spinner spn : spinners) {
            if (spn.getSelectedItemPosition() <= 0) {
                spn.requestFocus();
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Resets the spinner to its default value
     *
     * @param spinners to be reset.
     */
    public static void clearSpinners(Spinner... spinners) {
        for (Spinner spinner : spinners) {
            spinner.setSelection(0);
        }
    }

    /**
     * Creates a basis of an AlertDialog
     *
     * @param context in which the dialog will be shown
     * @param title   of the AlertDialog
     * @param message to be displayed in the AlertDialog
     * @return AlertDialog.Builder with specified parameters
     */
    public static AlertDialog.Builder buildAlertDialog(Context context, String title,
                                                       String message) {

        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(title);
        builder.setMessage(message);

        return builder;

    }

    /**
     * Checks validity of the email as user types it
     *
     * @param etEmail    EditText to be checked
     * @param ivScanCard ImageView to give an option
     * @param ivSearch
     */
    public static void validateEmailInput(final EditText etEmail, final ImageView ivScanCard,
                                          final ImageView ivSearch) {
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //showScanButton();
                switchViews(ivScanCard, ivSearch);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //showScanButton();
                switchViews(ivScanCard, ivSearch);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isEmailValid(etEmail)) {
                    //showSearchButton();
                    switchViews(ivSearch, ivScanCard);

                } else {
                    //showScanButton();
                    switchViews(ivScanCard, ivSearch);
                }
            }
        });
    }

    public static void validatePasswordInput(final EditText etPassword) {
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isPasswordValid(s.toString())) {
                    etPassword.setError("Password length must be > 2");
                } else {
                    etPassword.setError(null);
                }
            }
        });
    }

    /**
     * Hides or shows Navigation Buttons according to Selected Tab
     * @param next button that navigates to next Tab
     * @param previous button that navigates to previous Tab
     * @param viewPager to determine which tab is selected
     */
    public static void navigateTabs(final View next, final View previous, @NonNull final ViewPager viewPager) {

        hideViews(previous);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                if (viewPager.getCurrentItem() < 1) {
                    hideViews(previous);
                } else {
                    showViews(previous);
                }

                if (viewPager.getCurrentItem() >= Objects.requireNonNull(viewPager.getAdapter()).getCount() -1) {
                    hideViews(next);
                } else {
                    showViews(next);
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() < (Objects.requireNonNull(viewPager.getAdapter()).getCount() - 1)) {

                    showViews(previous);
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);

                    if (viewPager.getCurrentItem() == (Objects.requireNonNull(viewPager.getAdapter()).getCount() - 1)){
                        hideViews(next);
                    }

                } else {

                    hideViews(next);

                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewPager.getCurrentItem() > 0) {

                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
                    showViews(next);

                    if (viewPager.getCurrentItem() == 0){
                        hideViews(previous);
                    }

                }else{

                    hideViews(previous);

                }
            }
        });

//        if (viewPager.getCurrentItem() >= Objects.requireNonNull(viewPager.getAdapter()).getCount()) {
//            hideViews(next);
//        } else {
//            showViews(next);
//            if (viewPager.getCurrentItem() < (Objects.requireNonNull(viewPager.getAdapter()).getCount() - 1)) {
//                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
//            }
////            else {
////            }
//
//        }
//        // TODO: 2019/09/03 Go back
//        if (viewPager.getCurrentItem() <= 0) {
//            hideViews(previous);
//        } else {
//            showViews(previous);
//            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
//        }

    }


}