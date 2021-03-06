package scu.csci187.fall2018.mealtracker.Activities;

import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.logging.Filter;

import scu.csci187.fall2018.mealtracker.Classes.UserPreferences;
import scu.csci187.fall2018.mealtracker.Fragments.FiltersFragment;
import scu.csci187.fall2018.mealtracker.Fragments.HomeFragment;
import scu.csci187.fall2018.mealtracker.Fragments.MealDetailFragment;
import scu.csci187.fall2018.mealtracker.Fragments.PreferencesFragment;
import scu.csci187.fall2018.mealtracker.Fragments.SearchFragment;
import scu.csci187.fall2018.mealtracker.Fragments.FavoritesFragment;
import scu.csci187.fall2018.mealtracker.Fragments.ShoppingListFragment;
import scu.csci187.fall2018.mealtracker.R;

public class MainActivity extends AppCompatActivity
        implements SearchFragment.SearchFragmentListener,
        FiltersFragment.FiltersFragmentListener,
        MealDetailFragment.MadeMealListener,
        MealDetailFragment.ScheduleMealListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    public static int navItemIndex = 0;

    private static final String TAG_HOME = "home";
    private static final String TAG_SEARCH = "search";
    private static final String TAG_FAVORITES = "favorites";
    private static final String TAG_SHOPPING = "shopping";
    private static final String TAG_PREFERENCES = "preferences";
    private static final String TAG_MEALDETAIL = "mealdetail";
    private static final String TAG_FILTERS = "filters";
    private Fragment fragment;
    public static String CURRENT_TAG = TAG_HOME;

    private FiltersFragment mFiltersFragment;
    private SearchFragment mSearchFragment;

    private boolean shouldLoadHomeFragOnBackPress = false;

    private String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav_drawer);

        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        setUpNavigationView();

        // Occurs when first starting app, don't add fragment to backstack
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            Fragment fragment = getHomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.add(R.id.frame, fragment, CURRENT_TAG);
            fragmentTransaction.commit();
        }
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.navI_home:
                                navItemIndex = 0;
                                CURRENT_TAG = TAG_HOME;
                                break;
                            case R.id.navI_search:
                                navItemIndex = 1;
                                CURRENT_TAG = TAG_SEARCH;
                                break;
                            case R.id.navI_favorites:
                                navItemIndex = 2;
                                CURRENT_TAG = TAG_FAVORITES;
                                break;
                            case R.id.navI_shopping:
                                navItemIndex = 3;
                                CURRENT_TAG = TAG_SHOPPING;
                                break;
                            case R.id.navI_settings:
                                navItemIndex = 4;
                                CURRENT_TAG = TAG_PREFERENCES;
                                break;
                            default:
                                navItemIndex = 0;
                        }

                        loadHomeFragment();

                        return true;
                    }
                });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void loadHomeFragment() {
        selectNavMenu();

        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        drawer.closeDrawers();
    }

    private Fragment getHomeFragment() {
        Fragment frag;
        switch (navItemIndex) {
            case 0:
                frag = (HomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_HOME);
                if(frag == null) {
                    HomeFragment homeFrag = new HomeFragment();

                    return homeFrag;
                }
                else
                    return frag;
            case 1:
                frag = (SearchFragment) getSupportFragmentManager().findFragmentByTag(TAG_SEARCH);
                if(frag == null) {
                    searchText = "";
                    return new SearchFragment();
                }
                else
                    return frag;
            case 2:
                frag = (FavoritesFragment) getSupportFragmentManager().findFragmentByTag(TAG_FAVORITES);
                if(frag == null)
                    return new FavoritesFragment();
                else
                    return frag;
            case 3:
                frag = (ShoppingListFragment) getSupportFragmentManager().findFragmentByTag(TAG_SHOPPING);
                if(frag == null)
                    return new ShoppingListFragment();
                else
                    return frag;
            case 4:
                frag = (PreferencesFragment) getSupportFragmentManager().findFragmentByTag(TAG_PREFERENCES);
                if(frag == null)
                    return new PreferencesFragment();
                else
                    return frag;
            default:
                return new HomeFragment();
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        if(CURRENT_TAG == TAG_FILTERS) {
            Toast a = Toast.makeText(this, "back press current tag filters", Toast.LENGTH_SHORT);
            a.setGravity(Gravity.CENTER,0,0);
            a.show();
            navItemIndex = 1;
            CURRENT_TAG = TAG_SEARCH;
            Fragment frag = (SearchFragment) getSupportFragmentManager().findFragmentByTag(TAG_SEARCH);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, frag, TAG_SEARCH);
            fragmentTransaction.commit();
        }
        else if(CURRENT_TAG == TAG_HOME && getSupportFragmentManager().getBackStackEntryCount() == 0)
            return;
        else
            super.onBackPressed();
    }

    // Implement listener for Search Fragment
    public void goToFilters(String inputString) {

        searchText = inputString;   // save search string if user inputted one before going to filters
        mFiltersFragment = new FiltersFragment();
        navToFragment(mFiltersFragment, TAG_FILTERS);

    }

    // Implement listener for Filters Fragment
    public void sendFiltersToSearch(UserPreferences filters) {
        Toast.makeText(this, "Search filters saved", Toast.LENGTH_SHORT).show();
        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag(TAG_SEARCH);

        mSearchFragment.applyFiltersToSearch(searchText, filters);
        navToFragment(mSearchFragment, TAG_SEARCH);
    }

    // Implementation of MadeMealListener interface
    public void madeMealUpdateHistory(int index) {
        /*
            TODO: UPDATE ALL DATA ARRAYS, WRITE TO DB
                remove item@index for upcoming
                add meal to History
         */
        HomeFragment homeFrag = (HomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_HOME);
        homeFrag.notifyAdaptersDataChanged(index);
        navToFragment(homeFrag, TAG_HOME);
    }

    // Implementation of ScheduleMealListener interface
    public void showHomeScreenAfterScheduleMeal() {
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);    // clears fragment backstack to prevent returning to scheduler
        HomeFragment homeFrag = new HomeFragment(); // reinitializes Home screen fragment (lists) from DB
        navToFragment(homeFrag, TAG_HOME);
    }

    public void navToFragment(Fragment fragment, String tag) {
        CURRENT_TAG = tag;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, tag);
        if(tag != TAG_FILTERS) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }



}