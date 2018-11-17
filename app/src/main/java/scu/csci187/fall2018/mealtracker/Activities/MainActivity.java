package scu.csci187.fall2018.mealtracker.Activities;

import android.support.annotation.NonNull;
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

import scu.csci187.fall2018.mealtracker.Fragments.HomeFragment;
import scu.csci187.fall2018.mealtracker.Fragments.MealDetailFragment;
import scu.csci187.fall2018.mealtracker.Fragments.PreferencesFragment;
import scu.csci187.fall2018.mealtracker.Fragments.SearchFragment;
import scu.csci187.fall2018.mealtracker.Fragments.FavoritesFragment;
import scu.csci187.fall2018.mealtracker.Fragments.ShoppingListFragment;
import scu.csci187.fall2018.mealtracker.R;

public class MainActivity extends AppCompatActivity
        implements HomeFragment.OnFragmentInteractionListener,
                    SearchFragment.OnFragmentInteractionListener,
                    FavoritesFragment.OnFragmentInteractionListener,
                    PreferencesFragment.OnFragmentInteractionListener,
                    MealDetailFragment.OnFragmentInteractionListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;

    public static int navItemIndex = 0;

    private static final String TAG_HOME = "home";
    private static final String TAG_SEARCH = "search";
    private static final String TAG_FAVORITES = "favorites";
    private static final String TAG_SHOPPING = "shopping";
    private static final String TAG_PREFERENCES = "preferences";
    private static final String TAG_HISTORY = "history";
    private static final String TAG_MEALDETAIL = "mealdetail";
    public static String CURRENT_TAG = TAG_HOME;

    private String[] activityTitles;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_nav_drawer);
        toolbar = findViewById(R.id.toolbar);

        mHandler = new Handler();

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

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

        /*
            TODO: clean code-ify this if statement
         */
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Cross fade effect between fragments
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                return new HomeFragment();
            case 1:
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

            // This method will trigger on item Click of navigation menu
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

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

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

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
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

    public void onFragmentInteraction(String id) {

    }
}