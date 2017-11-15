package com.example.aman.pgapp.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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

import static com.android.volley.Request.*;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.editTextFirstName)
    EditText etxtFirstName;

    @BindView(R.id.editTextLastName)
    EditText etxtLastName;

    @BindView(R.id.editTextPhone)
    EditText etxtPhone;

    @BindView(R.id.editTextEmail)
    EditText etxtEmail;

    @BindView(R.id.editTextPassword)
    EditText etxtPassword;

    @BindView(R.id.buttonRegister)
    Button btnRegister;

    @BindView(R.id.spinnerAddress)
    Spinner spinner;

    User user;

    RequestQueue requestQueue;
    StringRequest stringRequest;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);

        //volleyRqst
        requestQueue = Volley.newRequestQueue(this);

        //preferencesRqst
        sharedPreferences=getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();

        btnRegister=(Button)findViewById(R.id.buttonRegister);

        btnRegister.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                user.setFirstname(etxtFirstName.getText().toString().trim());
                user.setLastname(etxtLastName.getText().toString().trim());
                user.setPhone(etxtPhone.getText().toString().trim());
                user.setEmail(etxtEmail.getText().toString().trim());
                user.setPassword(etxtPassword.getText().toString().trim());

                registerUserOnServer();

            }
        });

    }

    void registerUserOnServer(){

        stringRequest = new StringRequest(Method.POST, Util.REGISTER_ENDPOINT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject();
                    int success=jsonObject.getInt("success");
                    String message=jsonObject.getString("message");

                    Toast.makeText(RegisterActivity.this,message,Toast.LENGTH_LONG).show();

                    if(success==1){
                        editor.putBoolean(Util.KEY_LOGREG,true);
                        editor.commit();

                        Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                }
                catch (Exception e){

                    Toast.makeText(RegisterActivity.this,"Exception:"+e,Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RegisterActivity.this,"Error:"+error,Toast.LENGTH_LONG).show();

            }
        })
        {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map= new HashMap<>();
                map.put("firstName",user.getFirstname());
                map.put("lastName",user.getLastname());
                map.put("name",user.getPhone());
                map.put("email",user.getEmail());
                map.put("password",user.getPassword());

                return map;
            }
        };

        //executeRqst
        requestQueue.add(stringRequest);
    }

}
