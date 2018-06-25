package mk.ukim.finki.mpip.helloworld.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import mk.ukim.finki.mpip.helloworld.R;
import mk.ukim.finki.mpip.helloworld.loaders.CountryLoader;
import mk.ukim.finki.mpip.helloworld.model.Country;

public class CountryDetailsText extends Fragment implements LoaderManager.LoaderCallbacks<Country> {

    private static final String COUNTRY_NAME = "param1";
    public static final String TAG = "CDTF";

    // TODO: Rename and change types of parameters
    private String countryName;

    private TextView countryNameView;
    private TextView countryCapitalView;
    private TextView countryRegionView;
    private WebView flagView;


    public CountryDetailsText() {
        // Required empty public constructor
    }

    public static CountryDetailsText newInstance(String countryName) {
        CountryDetailsText fragmentInstance = new CountryDetailsText();
        Bundle args = new Bundle();
        args.putString(COUNTRY_NAME, countryName);
        fragmentInstance.setArguments(args);
        return fragmentInstance;
    }


    /**
     * This method will be called first, even before onCreate(), letting us know that your fragment
     * has been attached to an activity. You are passed the Activity that will host your fragment
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            countryName = getArguments().getString(COUNTRY_NAME);
        }
        Log.d(TAG, "onCreate");
    }


    /**
     * The system calls this callback when it’s time for the fragment to draw its UI for the first
     * time. To draw a UI for the fragment, a View component must be returned from this method
     * which is the root of the fragment’s layout.
     * We can return null if the fragment does not provide a UI
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_country_details_text, container, false);
    }


    /**
     * This will be called after onCreateView(). This is particularly useful when inheriting the
     * onCreateView() implementation but we need to configure the resulting views,
     * such as with a ListFragment and when to set up an adapter
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated");
        initUi(view);
        loadCountry();
    }

    /**
     * This will be called after onCreate() and onCreateView(), to indicate that the activity’s
     * onCreate() has completed. If there is something that’s needed to be initialised in the
     * fragment that depends upon the activity’s onCreate() having completed its work then
     * onActivityCreated() can be used for that initialisation work
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated");
    }

    /**
     * The onStart() method is called once the fragment gets visible
     */
    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    /**
     * The system calls this method as the first indication that the user is leaving the fragment.
     * This is usually where you should commit any changes that should be persisted beyond the
     * current user session
     */
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    /**
     * Fragment going to be stopped by calling onStop()
     */
    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    /**
     * It’s called before onDestroy(). This is the counterpart to onCreateView() where we set up
     * the UI. If there are things that are needed to be cleaned up specific to the UI,
     * then that logic can be put up in onDestroyView()
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView");
    }

    /**
     * onDestroy() called to do final clean up of the fragment’s state but Not guaranteed
     * to be called by the Android platform.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    /**
     * It’s called after onDestroy(), to notify that the fragment has been disassociated from
     * its hosting activity
     */
    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach");
    }


    @Override
    public Loader<Country> onCreateLoader(int id, Bundle args) {
        return new CountryLoader(getContext(), countryName);
    }

    @Override
    public void onLoadFinished(Loader<Country> loader, Country data) {
        countryNameView.setText(data.name);
        countryCapitalView.setText(data.capital);
        countryRegionView.setText(data.region);
        flagView.loadUrl(data.flag);
    }

    @Override
    public void onLoaderReset(Loader<Country> loader) {

    }


    private void initUi(View root) {
        countryNameView = root.findViewById(R.id.country_name);
        countryCapitalView = root.findViewById(R.id.country_capital);
        countryRegionView = root.findViewById(R.id.country_region);
        flagView = root.findViewById(R.id.web_flag);
    }

    private void loadCountry() {
        getLoaderManager().initLoader(56, null, this).forceLoad();
    }

}
