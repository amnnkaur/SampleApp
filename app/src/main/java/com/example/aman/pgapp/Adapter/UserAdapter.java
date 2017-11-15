package com.example.aman.pgapp.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.aman.pgapp.Model.User;
import com.example.aman.pgapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aman on 05-10-2017.
 */

public class UserAdapter extends ArrayAdapter<User> {

    Context context;
    int resource;
    ArrayList<User> list;

    public UserAdapter( Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        list=objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        view = LayoutInflater.from(context).inflate(resource,parent,false);
        TextView txtName = (TextView)view.findViewById(R.id.textViewName);
        TextView txtEmail = (TextView)view.findViewById(R.id.textViewEmail);

        User user = list.get(position);

        txtName.setText(user.getFirstname());
        txtEmail.setText(user.getEmail());

        return  view;
    }
}
