package com.example.project.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.project.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
//pass upi->others->success@razorpay

public class CheckOut extends AppCompatActivity implements PaymentResultListener {
    String price;
    Button paybtn;
    TextView p,paystatus,total,paystatus2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_check_out);
        Checkout.preload(getApplicationContext());
        Intent intentData = getIntent();
        Bundle b=getIntent().getExtras();

        price = Integer.toString(b.getInt("totalprice"));
        paybtn=findViewById(R.id.paybtn);
        paystatus=findViewById(R.id.paystatus);
        paystatus2=findViewById(R.id.status1);
        total=findViewById(R.id.total);
        p=findViewById(R.id.price);
        p.setText("$"+price);
        int totalprice=Integer.parseInt(price)+10;
      total.setText("$"+Integer.toString(totalprice));
        paybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentNow(Integer.toString((totalprice*76)*100));
            }
        });
    }

    private void PaymentNow(String amount) {

        final Activity activity = this;

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_ToUvOvSrFDFmgs");
        checkout.setImage(R.drawable.ic_launcher_background);

        int finalAmount = Integer.parseInt(amount);

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Talha");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            // options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", finalAmount+"");//300 X 100
            options.put("prefill.email", "talhachattha162@gmail.com");
            options.put("prefill.contact","+923035703342");

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }

    }



    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(getApplicationContext(),"Payment Success!",Toast.LENGTH_SHORT).show();
paystatus.setText(s);
paystatus2.setText("paymend id:");
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),"Payment Failure!",Toast.LENGTH_SHORT).show();
        paystatus.setText(s);
    }
}