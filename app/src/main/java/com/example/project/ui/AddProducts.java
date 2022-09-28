package com.example.project.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.formvalidation.FormValidation;
import com.example.project.models.ProductsModel;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddProducts#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddProducts extends Fragment {
    private static final int GALLERY_REQUEST = 1889;
    private static final int GALLERY_REQUEST1 = 1890;
    private static final int GALLERY_REQUEST2 = 1891;
    String productid ;
    EditText size, color, productname, price, description, Id;
    Uri selectedImage, selectedImage2,selectedImage3;
    Button sell;
    ViewPager images;
    FloatingActionButton fab1,fab2,fab3;
    ImageView pic1,pic2,pic3;
    FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference,databaseReference1;
    FirebaseStorage storage;
    FirebaseUser user;
    FirebaseAuth auth;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public AddProducts() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddProducts.
     */
    // TODO: Rename and change types and number of parameters
    public static AddProducts newInstance(String param1, String param2) {
        AddProducts fragment = new AddProducts();
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
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_add_products, container, false);
        size=v.findViewById(R.id.size);
        color=v.findViewById(R.id.color);
        productname=v.findViewById(R.id.Name);
        price=v.findViewById(R.id.price);
        description=v.findViewById(R.id.description);
//            Id=findViewById(R.id.id);
        sell=v.findViewById(R.id.sell_update);
        pic1=v.findViewById(R.id.imageView);
        pic2=v.findViewById(R.id.imageView2);
        pic3=v.findViewById(R.id.imageView3);
        fab1=v.findViewById(R.id.floatingActionButton);
        fab2=v.findViewById(R.id.floatingActionButton2);
        fab3=v.findViewById(R.id.floatingActionButton3);
        sell=v.findViewById(R.id.sell_update);

        pic1.setTag(R.drawable.shirt1);
        pic2.setTag(R.drawable.shirt1);
        pic3.setTag(R.drawable.shirt1);
        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        storage=FirebaseStorage.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
       // databaseReference=firebaseDatabase.getReference("products");
        databaseReference1=firebaseDatabase.getReference("userproductids");
        databaseReference=firebaseDatabase.getReference("products");
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery1();

            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery2();
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickFromGallery3();
            }
        });
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FormValidation formValidation=new FormValidation();
                if(formValidation.validateSize(size.getText().toString().toLowerCase(),getContext())
                        &&formValidation.validateColor(color.getText().toString(),getContext()) &&formValidation.validateColor(color.getText().toString(),getContext())
                        &&formValidation.validateName(productname.getText().toString(),getContext())
                        &&formValidation.validatePrice(price.getText().toString(),getContext())
                        &&formValidation.validateDescription(description.getText().toString(),getContext())
                )
                {
                    int drawableId = (Integer)pic1.getTag();
                    if(R.drawable.shirt1==drawableId){
                        Toast.makeText(getContext(), "please upload image1", Toast.LENGTH_SHORT).show();
                    }
                    int drawableId1 = (Integer)pic2.getTag();
                    if(R.drawable.shirt1==drawableId1){
                        Toast.makeText(getContext(), "please upload image2", Toast.LENGTH_SHORT).show();
                    }
                    int drawableId2 = (Integer)pic3.getTag();
                    if(R.drawable.shirt1==drawableId2){
                        Toast.makeText(getContext(), "please upload image3", Toast.LENGTH_SHORT).show();
                    }
                    if(R.drawable.shirt1!=drawableId&&R.drawable.shirt1!=drawableId1&&R.drawable.shirt1!=drawableId2) {
productid=databaseReference.push().getKey();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
//

                        final StorageReference reference=storage.getReference().child("seller").child("products").child(user.getUid()).child("pic1").child(formattedDate);
                reference.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                databaseReference.child(productid).child("pic1").setValue(uri.toString());
//                                databaseReference.child("pic1").push().setValue(uri.toString());
                            }
                        });
                    }
                });
                     //pic2
                        Calendar c1 = Calendar.getInstance();
                        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate1 = df1.format(c1.getTime());
                        final StorageReference reference1=storage.getReference().child("seller").child("products").child(user.getUid()).child("pic2").child(formattedDate1);
                        reference1.putFile(selectedImage2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                   databaseReference.child(productid).child("pic2").setValue(uri.toString());
//                                        databaseReference.child("pic2").push().setValue(uri.toString());
                                    }
                                });
                            }
                        });
                        //pic3

                        Calendar c2 = Calendar.getInstance();
                        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String formattedDate2 = df2.format(c2.getTime());
                        final StorageReference reference3=storage.getReference().child("seller").child("products").child(user.getUid()).child("pic3").child(formattedDate2);
                        reference3.putFile(selectedImage3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                reference3.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        databaseReference.child(productid).child("pic3").setValue(uri.toString());
//                                    databaseReference.push().child("pic3").setValue(uri.toString());
//                                    databaseReference.child("pic3").push().setValue(uri.toString());
                                    }
                                });
                            }
                        });
                        String userproductsid=databaseReference1.push().getKey();
                        databaseReference.child(productid).child("productname").setValue(productname.getText().toString()).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Toast.makeText(getContext(), "not uploaded successfully so delete the product from my products", Toast.LENGTH_LONG).show();

                            }

                        });
                        databaseReference.child(productid).child("price").setValue(price.getText().toString()).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Toast.makeText(getContext(), "not uploaded successfully so delete the product from my products", Toast.LENGTH_LONG).show();

                            }

                        });
                        databaseReference.child(productid).child("size").setValue(size.getText().toString()).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Toast.makeText(getContext(), "not uploaded successfully so delete the product from my products", Toast.LENGTH_LONG).show();

                            }

                        });
                        databaseReference.child(productid).child("color").setValue(color.getText().toString()).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Toast.makeText(getContext(), "not uploaded successfully so delete the product from my products", Toast.LENGTH_LONG).show();

                            }

                        });
                        databaseReference.child(productid).child("description").setValue(description.getText().toString()).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Toast.makeText(getContext(), "not uploaded successfully so delete the product from my products", Toast.LENGTH_LONG).show();

                            }

                        });
                        databaseReference1.child(userproductsid).child("productid").setValue(productid).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Toast.makeText(getContext(), "not uploaded successfully so delete the product from my products", Toast.LENGTH_LONG).show();

                            }

                        });
                        databaseReference1.child(userproductsid).child("userid").setValue(user.getUid()).addOnCanceledListener(new OnCanceledListener() {
                            @Override
                            public void onCanceled() {
                                Toast.makeText(getContext(), "not uploaded successfully so delete the product from my products", Toast.LENGTH_LONG).show();

                            }

                        });
                        Toast.makeText(getContext(), "product added successfully", Toast.LENGTH_SHORT).show();
                        productname.setText("");
                        price.setText("");
                        size.setText("");
                        color.setText("");
                        description.setText("");
                        pic1.setImageURI(Uri.parse(""));
                        pic2.setImageURI(Uri.parse(""));
                        pic3.setImageURI(Uri.parse(""));
                    }

//                    databaseReference.child("productname").push().setValue( productname.getText().toString());
//                    databaseReference.child("price").push().setValue( price.getText().toString());
//                    databaseReference.child("size").push().setValue( size.getText().toString());
//                    databaseReference.child("color").push().setValue( color.getText().toString());
//                    databaseReference.child("description").push().setValue( description.getText().toString());

                }
            }

        });
        return  v;
    }
    private void pickFromGallery1(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST);
    }
    private void pickFromGallery2(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST1);
    }
    private void pickFromGallery3(){
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        startActivityForResult(intent,GALLERY_REQUEST2);
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Result code is RESULT_OK only if the user selects an Image
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST:
                    //data.getData returns the content URI for the selected Image
                    selectedImage = data.getData();
                    pic1.setTag(0);
                    pic1.setImageURI(selectedImage);
                    break;
                case GALLERY_REQUEST1:
                    //data.getData returns the content URI for the selected Image
                    selectedImage2 = data.getData();
                    pic2.setTag(0);
                    pic2.setImageURI(selectedImage2);
                    break;
                case GALLERY_REQUEST2:
                    //data.getData returns the content URI for the selected Image
                    selectedImage3 = data.getData();
                    pic3.setTag(0);
                    pic3.setImageURI(selectedImage3);
                    break;
            }
    }
}