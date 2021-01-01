package com.example.gameify;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class findPage extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener{
    MyRecyclerViewAdapter adapter;
    TextView tv_username, tv_age;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_buddy);
        loadData();
        /*System.out.println("Usernames"+userNames.toString());
        RecyclerView recyclerView = findViewById(R.id.rv_buddies);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, userNames);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);*/
        User user = null;
        try {
            user = SharedPrefManager.getInstance(this).getUser();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_username = (TextView) findViewById(R.id.tv_userName_buddy);
        tv_age = (TextView) findViewById(R.id.tv_age_buddy);
        String age = String.valueOf(user.getAge());
        tv_username.setText("Your username: "+user.getUsername());
        tv_age.setText("Your age : "+age);

        getUserNames(user.getUsername());
    }


    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("userlist",null);
        Type type = new TypeToken<ArrayList<userAccount>>() {}.getType();
        userAccount.setUserAccountArrayList(gson.fromJson(json, type));
        String jsonAllUserData = sharedPreferences.getString("jsonAllUserData", null);
        Type typeSpecial = new TypeToken<ArrayList<gameData>>() {
        }.getType();
        gameData.setAllUserData(gson.fromJson(jsonAllUserData, typeSpecial));
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }


    private void getUserNames(String username) {
        String url="http://192.168.1.32:5000/getfriendsuggestion/"+username;
        System.out.println("starting method");
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        Log.d("Response", response);
                        try {
                            ArrayList<String> userNames = new ArrayList<>();
                            JSONObject obj = new JSONObject(response);
                            JSONArray users = obj.getJSONArray("Users");
                            System.out.println("users json array" + users.toString());
                            for(int i=0;i<users.length();i++){
                                System.out.println(users.getString(i));
                                userNames.add(users.getString(i));
                            }

                            System.out.println("Usernames"+userNames.toString());
                            RecyclerView recyclerView = findViewById(R.id.rv_buddies);
                            recyclerView.setLayoutManager(new LinearLayoutManager(findPage.this));
                            adapter = new MyRecyclerViewAdapter(findPage.this, userNames);
                            adapter.setClickListener(findPage.this);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response",error.getMessage());
                    }

                }

        );
        VolleySingleton.getInstance(this).addToRequestQueue(getRequest);
    }
}