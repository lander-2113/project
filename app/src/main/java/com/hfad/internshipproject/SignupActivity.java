package com.hfad.internshipproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hfad.internshipproject.dbProperties.MYSQLJDBCutil;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SignupActivity extends AppCompatActivity {

    private EditText s_user_name, s_fname, s_lname, s_pswd, s_email, s_mobno;
    private Button sbtn;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        try {
            url = MYSQLJDBCutil.get(this, "url");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        s_user_name = findViewById(R.id.sign_user_name_text);
        s_fname = findViewById(R.id.signfnametext);
        s_lname = findViewById(R.id.sign_lnametext);
        s_pswd = findViewById(R.id.sign_pswdtext);
        s_email = findViewById(R.id.sign_emailtext);
        s_mobno = findViewById(R.id.sign_mnotext);
        sbtn = findViewById(R.id.btnsignup);

        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_user(s_user_name.getText().toString(),
                        s_fname.getText().toString(),
                        s_lname.getText().toString(),
                        s_pswd.getText().toString(),
                        s_email.getText().toString(),
                        s_mobno.getText().toString());
            }
        });


    }

    public void register_user(final String user, final String fname,
                              final String lname, final String pswd,
                              final String email, final String phone){
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                resetFields();

                // here the user can go to the login page or the dashboard.
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                resetFields();
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user", user);
                map.put("fname", fname);
                map.put("lname", lname);
                map.put("pswd", pswd);
                map.put("email", email);
                map.put("phone", phone);
                return map;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    public void resetFields(){
        s_user_name.setText("");
        s_fname.setText("");
        s_lname.setText("");
        s_pswd.setText("");
        s_email.setText("");
        s_mobno.setText("");
    }
}