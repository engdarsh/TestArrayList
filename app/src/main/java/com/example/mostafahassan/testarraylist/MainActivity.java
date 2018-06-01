package com.example.mostafahassan.testarraylist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView blogList;
    private DatabaseReference databaseRef;
    private DatabaseReference databaseRefCurrentUser;
    private FirebaseAuth.AuthStateListener authStateListener;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseRef = FirebaseDatabase.getInstance().getReference().child("Incubators");
        databaseRef.keepSynced(true);

        blogList = (RecyclerView) findViewById(R.id.blogList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        blogList.setHasFixedSize(true);
        blogList.setLayoutManager(layoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Blog, BlogViewHolder>(
                        Blog.class,
                        R.layout.blog_row,
                        BlogViewHolder.class,
                        databaseRef
                ) {
                    @Override
                    protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {

                        final String postKey = getRef(position).getKey();

                        viewHolder.setTitle(model.getTitle());

                        viewHolder.view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent singleBlogIntent = new Intent(MainActivity.this, BlogSingleActivity.class);
                                singleBlogIntent.putExtra("postKey", postKey);
                                Toast.makeText(MainActivity.this,"Welcome",Toast.LENGTH_SHORT).show();
                                startActivity(singleBlogIntent);
                            }
                        });
                    }
                };

        blogList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {

        View view;

        public BlogViewHolder(View itemView) {
            super(itemView);
            view = itemView;

        }

        void setTitle(String Incubator) {
            TextView postDescription = (TextView) view.findViewById(R.id.IncNum);
            postDescription.setText(Incubator);
        }
//        void setDesc(String Incubator) {
//            TextView postDescription = (TextView) view.findViewById(R.id.IncNum);
//            postDescription.setText(Incubator);
//        }

    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_add) {
            startActivity(new Intent(MainActivity.this, PostActivity.class));
        }
        if (item.getItemId() == R.id.refresh) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
