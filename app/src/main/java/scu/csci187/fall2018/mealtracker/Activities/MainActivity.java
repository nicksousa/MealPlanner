package scu.csci187.fall2018.mealtracker.Activities;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
                                        FiltersFragment.FiltersFragmentListener {

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
    public static String CURRENT_TAG = TAG_HOME;

    private String[] activityTitles;

    private FiltersFragment mFiltersFragment;
    private SearchFragment mSearchFragment;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    private String searchText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav_drawer);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        mHandler = new Handler();

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    private void loadHomeFragment() {
        selectNavMenu();

        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        /*// Cross fade effect between fragments
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.addToBackStack(CURRENT_TAG);
                fragmentTransaction.commit();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
*/
        Fragment fragment = getHomeFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.addToBackStack(CURRENT_TAG);
        fragmentTransaction.commit();

        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new HomeFragment();
            case 1:
                searchText = "";
               return new SearchFragment();
            case 2:
                return new FavoritesFragment();
            case 3:
                return new ShoppingListFragment();
            case 4:
                return new PreferencesFragment();
            default:
                return new HomeFragment();
        }
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
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

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                // clear fragment backstack
                FragmentManager fm = getSupportFragmentManager();
                fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

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

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // Loads home fragment on back press if not currently in home fragment
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    // Implement listener for Search Fragment
    public void goToFilters(String inputString) {

        searchText = inputString;   // save search string if user inputted one before going to filters

        mFiltersFragment = (FiltersFragment) getSupportFragmentManager().findFragmentByTag(TAG_FILTERS);
        if(mFiltersFragment == null) {
            mFiltersFragment = new FiltersFragment();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, mFiltersFragment, "filters");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    // Implement listener for Filters Fragment
    public void sendFiltersToSearch(UserPreferences filters) {
        Toast.makeText(this, "Search filters saved", Toast.LENGTH_SHORT).show();
        mSearchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag(TAG_SEARCH);
        if(mSearchFragment == null) {
            mSearchFragment = new SearchFragment();
        }

        mSearchFragment.applyFiltersToSearch(searchText, filters);
        CURRENT_TAG = TAG_SEARCH;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, mSearchFragment, "search");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}