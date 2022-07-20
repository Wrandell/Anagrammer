package com.example.anagrammer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class listView extends AppCompatActivity {

    DatabaseReference mUserReference;

    private List<finalScoreGame> finalScoreGameList = new ArrayList<>();

    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    ImageView backToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        mUserReference = FirebaseDatabase.getInstance().getReference("Score");

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        backToHome = (ImageView) findViewById(R.id.backToHome);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //firebaseUserSearch();
            }*/


        backToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(listView.this, home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Score"), Model.class)
                        .build();

        mainAdapter = new MainAdapter(options);
        recyclerView.setAdapter(mainAdapter);

        fillExample();
    }

    private void fillExample() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.stopListening();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search here...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }*/

    /*private void firebaseUserSearch(){
        FirebaseRecyclerAdapter<finalScoreGame, UsersViewHolder> FirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<finalScoreGame, UsersViewHolder>(finalScoreGame.class,R.layout.activity_list_view,UsersViewHolder.class,mUserReference) {
            @Override
            protected void onBindViewHolder(@NonNull UsersViewHolder holder, int position, @NonNull finalScoreGame model) {

            }

            @NonNull
            @Override
            public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return null;
            }
        }
    }*/

    /*public class UsersViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }
    }*/

    @Override
    public void onBackPressed() {
        startActivity(new Intent(listView.this, home.class));
    }
}
