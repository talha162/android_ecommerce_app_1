package com.example.project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WriteReviews extends AppCompatActivity {
    EditText comments;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,databaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writereviews);
        Bundle b=getIntent().getExtras();
//        comments= (EditText) view.findViewById(R.id.comment);
//        String str=setArguments().getString("val");

        final RatingBar simpleRatingBar = (RatingBar) findViewById(R.id.fratingbar);
        Button submitButton = (Button) findViewById(R.id.submit);
        comments=findViewById(R.id.fcomments);
        simpleRatingBar.setNumStars(5);
        simpleRatingBar.setRating(5);
        String productid=b.getString("productid");
        Log.d("productid3",productid);
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("reviews").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(productid);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!comments.getText().toString().isEmpty()){
                    Float rating =  simpleRatingBar.getRating();
                    Toast.makeText(getApplicationContext(), rating + "\n" + rating, Toast.LENGTH_LONG).show();
                    databaseReference.child("rating").setValue(rating);
                    databaseReference.child("comments").setValue(comments.getText().toString());
                    Intent i=new Intent(WriteReviews.this,MainUsers.class);
                    startActivity(i);
                }
                else if(comments.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "pls add something in comment", Toast.LENGTH_LONG).show();
                }
                //                Intent i=new Intent(getContext(),MainActivity.class);

            }
        });
//        RatingBar simpleRatingBar = (RatingBar) view.findViewById(R.id.fratingbar); // initiate a rating bar

//        int numberOfStars = simpleRatingBar.getNumStars(); // get total number of stars of rating bar
//        test=view.findViewById(R.id.textView);
//        test.setText(Integer.toString(numberOfStars));

    }
}