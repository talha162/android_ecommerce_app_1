package com.example.project.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserAccountsInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserAccountsInfo extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    ProgressDialog progressDialog,progressDialog1;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int GALLERY_REQUEST = 1889;
    FirebaseStorage storage;
    FirebaseAuth mauth;
    FirebaseDatabase firebaseDatabase;
    FloatingActionButton fab;
    ImageView profilepic;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView profilepage,favoritepage,orderhistory,role,personname;
    Button logout;
    FirebaseUser user;

    private Target img = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            profilepic.setImageBitmap(bitmap);
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    public UserAccountsInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user.
     */
    // TODO: Rename and change types and number of parameters
    public static UserAccountsInfo newInstance(String param1, String param2) {
        UserAccountsInfo fragment = new UserAccountsInfo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("uploading...");
        progressDialog1=new ProgressDialog(getContext());
        progressDialog1.setMessage("loading...");
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_user, container, false);
profilepic=v.findViewById(R.id.profile_picture);
fab=v.findViewById(R.id.floatingActionButton);
profilepage=v.findViewById(R.id.profilepage);
personname=v.findViewById(R.id.person_name);
role=v.findViewById(R.id.role);
favoritepage=v.findViewById(R.id.favorite);
orderhistory=v.findViewById(R.id.ordershistory);
logout=v.findViewById(R.id.user_logout);
firebaseDatabase=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
mauth=FirebaseAuth.getInstance();

logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        mauth.signOut();
        Intent i=new Intent(getContext(),LoginActivity.class);
        startActivity(i);
    }
});
favoritepage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getContext(),Favorite.class);
        startActivity(i);
    }
});
profilepage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getContext(),Profilepage.class);
        startActivity(i);
    }
});


orderhistory.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent i=new Intent(getContext(), OrdersHistory.class);
        startActivity(i);
    }
});
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery();
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference dbreference=firebaseDatabase.getReference("users");
        dbreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    User user1=ds.getValue(User.class);
                    if (user1.getEmail().equals(user.getEmail())) {
                        personname.setText(ds.child("name").getValue(String.class));
                        role.setText(ds.child("role").getValue(String.class));
                        String image=ds.child("profilepic").getValue(String.class);
                        if(image!=null){
                            Picasso.get().load(image).into(img);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return v;
    }

    private void pickFromGallery(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST);
    }


    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
        switch (requestCode){
                case GALLERY_REQUEST:
                    FirebaseUser u=FirebaseAuth.getInstance().getCurrentUser();
                    Uri selectedImage = data.getData();
                    final StorageReference reference=storage.getReference().child("user").child("profile").child(u.getUid());
                    reference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    firebaseDatabase.getReference().child("users").child(u.getUid()).child("profilepic").setValue(uri.toString());
                                }
                            });
                        }
                    });
                    profilepic.setImageURI(selectedImage);
                    break;
            }
    }

}