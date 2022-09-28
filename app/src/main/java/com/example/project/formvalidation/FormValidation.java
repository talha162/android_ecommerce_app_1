package com.example.project.formvalidation;

import static android.os.Build.VERSION_CODES.S;

import android.content.Context;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidation {
    public FormValidation(){}

    public boolean validateName(final String name, Context context) {
        Pattern pattern;
        Matcher matcher;
        final String NAME_PATTERN = "^[a-zA-Z ]*$";
        pattern = Pattern.compile(NAME_PATTERN);
        matcher = pattern.matcher(name);
        boolean matchfound = matcher.find();
        if (name.isEmpty()){
            Toast.makeText(context, "empty name", Toast.LENGTH_SHORT).show();
        }
        else if (!matchfound){
            Toast.makeText(context, "invalid name", Toast.LENGTH_SHORT).show();
        }
        return matchfound;
    }
    public boolean validateSize(final String size, Context context) {
        Pattern pattern;
        Matcher matcher;
        if (size.isEmpty()){
            Toast.makeText(context, "empty size", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (size.equals("small")||size.equals("s")
                ||size.equals("medium")||size.equals("m")
                ||size.equals("large")||size.equals("l")
                ||size.equals("extra large")||size.equals("xl")
        ){

            return true;
        }
        else{
            Toast.makeText(context, "invalid size", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateEmail(final String email,Context context) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        boolean matchfound = matcher.find();
        if (email.isEmpty()){
            Toast.makeText(context, "empty email", Toast.LENGTH_SHORT).show();
        }
        else if  (!matchfound) {
            Toast.makeText(context, "invalid email", Toast.LENGTH_SHORT).show();
        }
        return matchfound;
    }

    public boolean validatePassword(final String password, Context context) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*(_|[^\\w])).{8,20}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        boolean matchfound = matcher.find();
        if (password.isEmpty()){
            Toast.makeText(context, "empty password", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if  (matchfound) {
            return true;
        }
        else {
            Toast.makeText(context, "invalid password", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateConfirmPassword(final String confirmpassword,final String password, Context context) {
        Pattern pattern;
        Matcher matcher;
        if (confirmpassword.isEmpty()){
            Toast.makeText(context, "empty confirm password", Toast.LENGTH_SHORT).show();
        }
             else if(password.equals(confirmpassword)){
            return true;
            }
            else if(!password.equals(confirmpassword)){
                Toast.makeText(context, "password does not match", Toast.LENGTH_SHORT).show();
            }
        return  false;
    }

    public boolean validatePhone ( final String phone, Context context){
            Pattern pattern;
            Matcher matcher;
            final String PHONE_PATTERN = "^[0-9]{4,13}$";
            pattern = Pattern.compile(PHONE_PATTERN);
            matcher = pattern.matcher(phone);
            boolean matchfound = matcher.find();
        if (phone.isEmpty()){
            Toast.makeText(context, "empty phone", Toast.LENGTH_SHORT).show();
        }
           else if (!matchfound) {
                Toast.makeText(context, "invalid phone number", Toast.LENGTH_SHORT).show();
            }
            return matchfound;
        }
    public boolean validatePostalCode(final String postalcode, Context context) {
        Pattern pattern;
        Matcher matcher;
        final String POSTALCODE_PATTERN = "^[0-9]{4,8}$";
        pattern = Pattern.compile(POSTALCODE_PATTERN);
        matcher = pattern.matcher(postalcode);
        boolean matchfound = matcher.find();
        if (postalcode.isEmpty()){
            Toast.makeText(context, "empty postal code", Toast.LENGTH_SHORT).show();
        }
        else if (!matchfound) {
            Toast.makeText(context, "invalid postal code", Toast.LENGTH_SHORT).show();
        }
        return matchfound;
    }

    public boolean validateCityAndState(final String cityandstate, Context context) {
        Pattern pattern;
        Matcher matcher;
        final String CITYANDSTATE_PATTERN = "^[A-Za-z]+$";
//        New york, NY true
        pattern = Pattern.compile(CITYANDSTATE_PATTERN);
        matcher = pattern.matcher(cityandstate);
        boolean matchfound = matcher.find();
        if (cityandstate.isEmpty()){
            Toast.makeText(context, "empty city", Toast.LENGTH_SHORT).show();
        }
        else if (!matchfound) {
            Toast.makeText(context, "invalid city or state", Toast.LENGTH_SHORT).show();
        }
        return matchfound;
    }
    public boolean validateColor(final String color, Context context) {
        Pattern pattern;
        Matcher matcher;
        final String color_pattern = "^[A-Za-z]+$";
        pattern = Pattern.compile(color_pattern);
        matcher = pattern.matcher(color);
        boolean matchfound = matcher.find();
        if (color.isEmpty()){
            Toast.makeText(context, "empty color", Toast.LENGTH_SHORT).show();
        }
        else if (!matchfound) {
            Toast.makeText(context, "invalid color", Toast.LENGTH_SHORT).show();
        }
        return matchfound;
    }

    public boolean validateAddress(final String address, Context context) {
        Pattern pattern;
        Matcher matcher;
        final String ADDRESS_PATTERN = "^[a-zA-Z0-9 ]*$";
        pattern = Pattern.compile(ADDRESS_PATTERN);
        matcher = pattern.matcher(address);
        boolean matchfound = matcher.find();
        if (address.isEmpty()){
            Toast.makeText(context, "empty address", Toast.LENGTH_SHORT).show();
        }
        else if (!matchfound) {
            Toast.makeText(context, "invalid address", Toast.LENGTH_SHORT).show();
        }
        return matchfound;
    }

    public boolean validateDescription(final String description, Context context) {
        Pattern pattern;
        Matcher matcher;
        final String Description_PATTERN = "^[a-zA-Z0-9 ]*$";
        pattern = Pattern.compile(Description_PATTERN);
        matcher = pattern.matcher(description);
        boolean matchfound = matcher.find();
        if (description.isEmpty()){
            Toast.makeText(context, "empty description", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!matchfound) {
            Toast.makeText(context, "invalid description", Toast.LENGTH_SHORT).show();
            return false;
        }
        return matchfound;
    }

    public boolean validatePrice(final String price, Context context) {
        Pattern pattern;
        Matcher matcher;
        final String PRICE_PATTERN = "\\$[0-9]+";
        pattern = Pattern.compile(PRICE_PATTERN);
        matcher = pattern.matcher(price);
        boolean matchfound = matcher.find();
        if (price.isEmpty()){
            Toast.makeText(context, "empty price", Toast.LENGTH_SHORT).show();
        }
        else if (!matchfound) {
            Toast.makeText(context, "invalid price", Toast.LENGTH_SHORT).show();
        }
        return matchfound;
    }
}