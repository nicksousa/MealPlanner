package scu.csci187.fall2018.mealtracker.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import scu.csci187.fall2018.mealtracker.R;
import scu.csci187.fall2018.mealtracker.Classes.UserPreferences;

public class PreferencesFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private EditText calorieLow, calorieHigh, maxTimeInMinutes;
    private RadioGroup radioDietLabels;
    private RadioButton radioButton;
    private int selectedRadioId = -1;
    private CheckBox vegetarian, vegan, pescatarian, kosher, gluten, paleo, shellfish,
                        dairy, treenut, peanut, egg;
    int lowCalorie, highCalorie, maxTime;
    boolean boolVegetarian, boolVegan, boolPescatarian, boolKosher, boolGluten, boolPaleo,
                boolShellfish, boolDairy, boolTreenut, boolPeanut, boolEgg;
    Button buttonSavePrefs;

    public PreferencesFragment() {
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
        View view = inflater.inflate(R.layout.preferences_layout, container, false);

        calorieLow = view.findViewById(R.id.calorieLow);
        calorieHigh = view.findViewById(R.id.calorieHigh);

        maxTimeInMinutes = view.findViewById(R.id.timeHigh);

        radioDietLabels = view.findViewById(R.id.radioDietLabels);

        vegetarian = view.findViewById(R.id.checkboxVegetarian);
        vegan = view.findViewById(R.id.checkboxVegan);
        pescatarian = view.findViewById(R.id.checkboxPescatarian);
        kosher = view.findViewById(R.id.checkboxKosher);
        gluten = view.findViewById(R.id.checkboxGluten);
        paleo = view.findViewById(R.id.checkboxPaleo);
        shellfish = view.findViewById(R.id.checkboxShellfish);
        dairy = view.findViewById(R.id.checkboxDairy);
        treenut = view.findViewById(R.id.checkboxTreenut);
        peanut = view.findViewById(R.id.checkboxPeanut);
        egg = view.findViewById(R.id.checkboxEgg);

        buttonSavePrefs = view.findViewById(R.id.buttonSavePrefs);

        addUIListeners();

        populatePreferencesFromDB();

        return view;
    }

    public void populatePreferencesFromDB(){
        UserPreferences prefFromDb; // = someDBCall
        /*
            TODO: GET FROM DATABASE
         */
    }

    public void savePreferencesToDB() {
        lowCalorie = Integer.parseInt(calorieLow.getText().toString());
        highCalorie = Integer.parseInt(calorieHigh.getText().toString());
        maxTime = Integer.parseInt(maxTimeInMinutes.getText().toString());
        UserPreferences newPreferences = new UserPreferences(lowCalorie, highCalorie, maxTime,
                selectedRadioId, vegetarian.isChecked(), vegan.isChecked(), pescatarian.isChecked(),
                kosher.isChecked(), gluten.isChecked(), paleo.isChecked(), shellfish.isChecked(),
                dairy.isChecked(), treenut.isChecked(), peanut.isChecked(), egg.isChecked());

        /*
            TODO: SAVE TO DATABASE
         */
    }

    public void addUIListeners() {
        addRadioListener();
        //addCheckboxListeners();
        addSavePreferencesButtonListener();
    }

    public void addRadioListener() {
        final RadioGroup radio = radioDietLabels;
        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radio.findViewById(checkedId);
                selectedRadioId = radio.indexOfChild(radioButton);
            }
        });
    }
/*  MAY BE UNNECESSARY
    public void addCheckboxListeners() {
        vegetarian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolVegetarian = vegetarian.isChecked() ? true : false;
            }
        });
        vegan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolVegan = vegan.isChecked() ? true : false;
            }
        });
        pescatarian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolPescatarian = pescatarian.isChecked() ? true : false;
            }
        });
        kosher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolKosher = kosher.isChecked() ? true : false;
            }
        });
        gluten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolGluten = gluten.isChecked() ? true : false;
            }
        });
        paleo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolPaleo = paleo.isChecked() ? true : false;
            }
        });
        shellfish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolShellfish = shellfish.isChecked() ? true : false;
            }
        });
        dairy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolDairy = dairy.isChecked() ? true : false;
            }
        });
        treenut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolTreenut = treenut.isChecked() ? true : false;
            }
        });
        peanut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolPeanut = peanut.isChecked() ? true : false;
            }
        });
        egg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolEgg = egg.isChecked() ? true : false;
            }
        });
    }
    */

    public void addSavePreferencesButtonListener() {
        buttonSavePrefs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePreferencesToDB();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String id);
    }
}
