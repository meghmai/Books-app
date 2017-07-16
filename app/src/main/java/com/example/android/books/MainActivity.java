package com.example.android.books;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Word>> {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int BOOK_LOADER_ID = 1;
    private static String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private bookAdapter mAdapter;
    private String queryTitle;
    private EditText name;
    private View spinner;
    private LoaderManager loaderManager;
    private TextView emptyStateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = (EditText) findViewById(R.id.search_view);
        String title = name.getText().toString();

        ListView bookListView = (ListView) findViewById(R.id.list);

        emptyStateView = (TextView) findViewById(R.id.empty);
        bookListView.setEmptyView(emptyStateView);

        ImageView searchButtonView = (ImageView) findViewById(R.id.search);

        mAdapter = new bookAdapter(this, new ArrayList<Word>());
        bookListView.setAdapter(mAdapter);

        spinner = findViewById(R.id.loading_spinner);
        spinner.setVisibility(View.GONE);

        if (isConnected()) {
            spinner.setVisibility(View.VISIBLE);
            loaderManager = getLoaderManager();
            Log.i(LOG_TAG, "test: calling initLoader()");
            loaderManager.initLoader(BOOK_LOADER_ID, null, MainActivity.this);
        } else {
            emptyStateView.setText(R.string.no_internet);
        }
        searchButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryTitle = name.getText().toString();
                Log.i(LOG_TAG, "queryTitle: " + queryTitle);

                if (isConnected()) {
                    spinner.setVisibility(View.VISIBLE);
                    emptyStateView.setText("");

                    Log.i(LOG_TAG, "test: calling initLoader()");
                    loaderManager.restartLoader(BOOK_LOADER_ID, null, MainActivity.this);
                } else {
                    emptyStateView.setText(R.string.no_books);
                }
            }
        });
        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Word currentword = mAdapter.getItem(position);
                Uri bookUri = Uri.parse(currentword.getMurl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);
                startActivity(websiteIntent);
            }
        });
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public Loader<List<Word>> onCreateLoader(int i, Bundle bundle) {
        if (queryTitle == null || queryTitle.isEmpty()) queryTitle = "book";
        String newUrl = BOOK_REQUEST_URL + queryTitle.toLowerCase() + "&maxResults=30";
        return new bookLoader(this, newUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Word>> loader, List<Word> books) {
        emptyStateView.setText("NO BOOKS FOUND");
        spinner.setVisibility(View.GONE);
        mAdapter.clear();
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Word>> loader) {
        mAdapter.clear();
    }
}
