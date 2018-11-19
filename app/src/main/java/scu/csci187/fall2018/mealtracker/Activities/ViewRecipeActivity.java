package scu.csci187.fall2018.mealtracker.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import scu.csci187.fall2018.mealtracker.R;

public class ViewRecipeActivity extends AppCompatActivity {

    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        browser = findViewById(R.id.webView);
        browser.setWebViewClient(new WebViewClient());
        browser.loadUrl(getIntent().getExtras().getString("recipeURL"));
    }
}
