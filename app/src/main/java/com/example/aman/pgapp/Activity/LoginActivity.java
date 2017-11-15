package com.example.aman.pgapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aman.pgapp.Model.User;
import com.example.aman.pgapp.Model.Util;
import com.example.aman.pgapp.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.id;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.editTextEmail)
    EditText etxtEmail;

    @BindView(R.id.editTextPassword)
    EditText etxtPassword;

    @BindView(R.id.buttonLogin)
    Button btnLogin;

    @BindView(R.id.textViewlogreg)
    TextView txtNewUser;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(this);
        txtNewUser.setOnClickListener(this);

        preferences=getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor=preferences.edit();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.buttonLogin:

                user.setEmail(etxtEmail.getText().toString().trim());
                user.setPassword(etxtPassword.getText().toString().trim());

                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
                break;

            case R.id.textViewlogreg:

                Intent intent1= new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);

                break;
        }
    }
    void loginUser(){

        stringRequest=new StringRequest(Request.Method.POST,Util.LOGIN_ENDPOINT,new Response.Listener<String>(){
            public void onResponse(String response) {

                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();

                    if(success == 1){

                        editor.putBoolean(Util.KEY_LOGREG,true);
                        editor.commit();

                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this,"Some Exception",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(LoginActivity.this,"Some Volley Error",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();

                map.put("email",user.getEmail());
                map.put("password",user.getPassword());

                return map;
            }
        }
        ;

        requestQueue.add(stringRequest);
    }

}
