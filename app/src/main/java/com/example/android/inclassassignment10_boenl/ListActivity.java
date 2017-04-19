package com.example.android.inclassassignment10_boenl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Comment;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    TextView display;
    FirebaseDatabase database;
    DatabaseReference postRef;
    DatabaseReference postsRef;
    ArrayList<BlogPost> posts;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        database = FirebaseDatabase.getInstance();
        postRef = database.getReference("post");
        postsRef = database.getReference("posts");
        posts = new ArrayList<>();
        //display = (TextView) findViewById(R.id.display_status);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new MyAdapter(posts);
        mRecyclerView.setAdapter(mAdapter);
        postsRef.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(DataSnapshot dataSnapshot,String s){
                BlogPost post=dataSnapshot.getValue(BlogPost.class);
                posts.add(post);

                String results = "";
                for (BlogPost p:posts){
                    results += p + "\n";
                }
                mAdapter.notifyDataSetChanged();


            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Toast.makeText(ListActivity.this,dataSnapshot.getValue(BlogPost.class)+" has changed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Toast.makeText(ListActivity.this,dataSnapshot.getValue(BlogPost.class)+" is removed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
