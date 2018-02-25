package com.example.android.mybooklistingapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    //global variables
    private static final String GOOGLE_BOOKS_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int ITEM_LOADER_ID = 1;
    private EditText editText;
    private ImageView imageView;
    private TextView emptyTextView;
    private RecyclerView recyclerView;
    private ItemRecyclerAdapter itemRecyclerAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        emptyTextView = (TextView) findViewById(R.id.emptyView);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        editText = (EditText) findViewById(R.id.searchBar);
        imageView = (ImageView) findViewById(R.id.buttonSearch);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // adapter for the list of books
                itemRecyclerAdapter = new ItemRecyclerAdapter(MainActivity.this, new ArrayList<Book>(), new ItemRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Book book) {
                        String url = book.getURL();
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });

                recyclerView.setAdapter(itemRecyclerAdapter);

                final ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                // check connection
                if (networkInfo != null && networkInfo.isConnected()) {
                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.restartLoader(ITEM_LOADER_ID, null, MainActivity.this);
                } else {
                    recyclerView.setVisibility(View.GONE);
                    emptyTextView.setVisibility(View.VISIBLE);
                    emptyTextView.setText(R.string.no_connection);
                }


            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle bundle) {

        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(GONE);
        String input = editText.getText().toString();
        input = input.replace(" ", "+");
        String query = GOOGLE_BOOKS_REQUEST_URL + input;
        Uri uri = Uri.parse(query);
        Uri.Builder builder = uri.buildUpon();
        return new ItemLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> book) {
        //preliminary operations
        emptyTextView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        itemRecyclerAdapter.clear();
        //check results
        if (book != null && !book.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.GONE);
            itemRecyclerAdapter.addAll(book);
        } else {
            emptyTextView.setText(R.string.five_o_five);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        itemRecyclerAdapter.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}