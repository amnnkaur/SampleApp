package com.example.aman.pgapp.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.aman.pgapp.Adapter.UserAdapter;
import com.example.aman.pgapp.Model.User;
import com.example.aman.pgapp.Model.Util;
import com.example.aman.pgapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {


    RequestQueue requestQueue;
    StringRequest stringRequest;

    ArrayList<User> userList;

    @BindView(R.id.listview)
    ListView listView;

    UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        requestQueue = Volley.newRequestQueue(this);

        retrieveFromServer();
    }

    void retrieveFromServer(){

        stringRequest = new StringRequest(Request.Method.GET, Util.RETRIEVE_ENDPOINT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    if(success == 1){
                        Toast.makeText(HomeActivity.this,message,Toast.LENGTH_LONG).show();

                        JSONArray jsonArray = jsonObject.getJSONArray("users");

                        int id = 0;
                        String n="",e="",p="";

                        userList = new ArrayList<>();

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            id = jObj.getInt("uid"); // String Inputs are the column names of your table
                            n = jObj.getString("name");
                            p = jObj.getString("email");
                            e = jObj.getString("password");

                            User user = new User(id,n,e,p);
                            userList.add(user);
                        }

                        userAdapter = new UserAdapter(HomeActivity.this,R.layout.list_item,userList);
                        listView.setAdapter(userAdapter);

                    }else{
                        Toast.makeText(HomeActivity.this,message,Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(HomeActivity.this,"Some Exception: "+e,Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(HomeActivity.this,"Some VolleyError: "+error,Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest);

    }
}
