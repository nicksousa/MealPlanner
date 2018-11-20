package scu.csci187.fall2018.mealtracker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;

import scu.csci187.fall2018.mealtracker.Classes.UserPreferences;
import scu.csci187.fall2018.mealtracker.R;

public class FiltersFragment extends Fragment {
    private EditText calorieLow, calorieHigh, maxTimeInMinutes;
    private RadioGroup radioDietLabels;
    private int selectedRadioId = -1;
    private CheckBox vegetarian, vegan, pescatarian, kosher, gluten, paleo, shellfish,
            dairy, treenut, peanut, egg;
    int lowCalorie, highCalorie, maxTime;
    Button buttonSavePrefs;

    private FiltersFragmentListener mCallback;

    public FiltersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_filters, container, false);
        bindViews(view);
        addUIListeners();

        //populatePreferencesFromDB();

        return view;
    }

    // Ensures that Activity has implemented FiltersFragmentListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FiltersFragmentListener) {
            mCallback = (FiltersFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement FiltersFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    private void bindViews(View view) {
        calorieLow = view.findViewById(R.id.sfCalorieLow);
        calorieHigh = view.findViewById(R.id.sfCalorieHigh);
        maxTimeInMinutes = view.findViewById(R.id.sfTimeHigh);
        radioDietLabels = view.findViewById(R.id.sfRadioDietLabels);
        vegetarian = view.findViewById(R.id.sfCheckboxVegetarian);
        vegan = view.findViewById(R.id.sfCheckboxVegan);
        pescatarian = view.findViewById(R.id.sfCheckboxPescatarian);
        kosher = view.findViewById(R.id.sfCheckboxKosher);
        gluten = view.findViewById(R.id.sfCheckboxGluten);
        paleo = view.findViewById(R.id.sfCheckboxPaleo);
        shellfish = view.findViewById(R.id.sfCheckboxShellfish);
        dairy = view.findViewById(R.id.sfCheckboxDairy);
        treenut = view.findViewById(R.id.sfCheckboxTreenut);
        peanut = view.findViewById(R.id.sfCheckboxPeanut);
        egg = view.findViewById(R.id.sfCheckboxEgg);
        buttonSavePrefs = view.findViewById(R.id.sfButtonDone);
    }

    private void addUIListeners() {
        addRadioListener();
        addButtonDoneListener();
    }

    private void addRadioListener() {
        final RadioGroup radio = radioDietLabels;
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radio.findViewById(checkedId);
                selectedRadioId = radio.indexOfChild(radioButton);
            }
        });
    }

    private void addButtonDoneListener() {
        buttonSavePrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFiltersToSearch();
            }
        });
    }

    private void saveFiltersToSearch() {
        String tempMinCal, tempMaxCal, tempMaxTime;

        tempMinCal = calorieLow.getText().toString();
        tempMaxCal = calorieHigh.getText().toString();
        tempMaxTime = maxTimeInMinutes.getText().toString();

        lowCalorie = (tempMinCal.isEmpty()) ? 0 : Integer.parseInt(tempMinCal);
        highCalorie = (tempMaxCal.isEmpty()) ? 0 : Integer.parseInt(tempMaxCal);
        maxTime = (tempMaxTime.isEmpty()) ? 0 : Integer.parseInt(tempMaxTime);

        UserPreferences filters = new UserPreferences(lowCalorie, highCalorie, maxTime,
                selectedRadioId, vegetarian.isChecked(), vegan.isChecked(), pescatarian.isChecked(),
                kosher.isChecked(), gluten.isChecked(), paleo.isChecked(), shellfish.isChecked(),
                dairy.isChecked(), treenut.isChecked(), peanut.isChecked(), egg.isChecked());

        //send filters to search fragment, go back to search
        mCallback.sendFiltersToSearch(filters);
    }

    public interface FiltersFragmentListener {
        void sendFiltersToSearch(UserPreferences filters);
    }

}