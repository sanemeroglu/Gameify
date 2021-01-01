package com.example.gameify;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class afterLoginMainPage extends AppCompatActivity {


    Button but_find;
    TextView tv_logout, tv_welcome, tv_username, tv_gamedata;

    private static String name_bf;
    private static String username_bf;
    private static int age_bf;
    public static String total_gamedata = "Your game data:\n";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_login_main_page);
        but_find = (Button) findViewById(R.id.but_find_button_afterLogPage);

        tv_logout = (TextView) findViewById(R.id.tv_logout_afterLogPage);
        tv_welcome = (TextView) findViewById(R.id.tv_welcome_afterLogPage);
        tv_username = (TextView) findViewById(R.id.tv_username_afterLogPage);



        loadData();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
        User user = null;
        try {
            user = SharedPrefManager.getInstance(this).getUser();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        name_bf = user.getName();
        username_bf = user.getUsername();
        age_bf = user.getAge();

        if (!gameData.getUsernameArrayList().contains(username_bf)){
            gameData.putUserArrayList(username_bf);
        }

        tv_welcome.setText("Welcome, " + name_bf + "!!");
        tv_username.setText("Your username: "+username_bf);
        //tv_gamedata.setText(total_gamedata+user.getGamedata().toString());
        setGameData(user.getUsername());
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(afterLoginMainPage.this, MainActivity.class);
                Toast.makeText(afterLoginMainPage.this, "Log out Successful", Toast.LENGTH_SHORT).show();
                afterLoginMainPage.this.finish();
                startActivity(intent);
            }
        });

        but_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                Intent intent = new Intent(afterLoginMainPage.this, findPage.class);
                intent.putExtra("usernameToGameRank", username_bf);
                intent.putExtra("ageToGameRank", age_bf);
                startActivity(intent);
            }
        });



}

    private void setGameData(String username) {
        String url="http://192.168.1.32:5000/getusergameinfo/"+username;
        System.out.println("starting method");
        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // display response
                        Log.d("Response", response);
                        try {
                            JSONObject obj = new JSONObject(response);
                            tv_gamedata = (TextView) findViewById(R.id.tv_game_data_afterLogPage);
                            tv_gamedata.setText("Your Game Data \n Game Name:"+obj.getString("game")+
                                    "\n Game Type: "+obj.getString("gametype") + "\n Level: "+obj.getString("level"));
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

    private String outputSet(String csgoRank, String lolRank, String r6Rank, String gtaRank) {
        String output = "Your game data:\n";
        if (csgoRank == null && lolRank == null && r6Rank == null && gtaRank == null) {
            output = output;
        } else {
            if (!csgoRank.equalsIgnoreCase("") || csgoRank != null) {
                output = output + "CS-GO\t\t Rank: " + csgoRank + "\n";
            }
            if (!lolRank.equalsIgnoreCase("") || lolRank != null) {
                output = output + "LOL\t\t\t\t\t Rank: " + lolRank + "\n";
            }
            if (!r6Rank.equalsIgnoreCase("") || r6Rank != null) {
                output = output + "R6\t\t\t\t\t\t\t Rank: " + r6Rank + "\n";
            }
            if (!gtaRank.equalsIgnoreCase("") || gtaRank != null) {
                output = output + "GTA\t\t\t\t\t Rank: " + gtaRank + "\n";
            }
        }
        return output;
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

    private int allDataUsernameChecker(String username) {
        int a = 0;
        ArrayList<gameData> temp = gameData.getAllUserData();
        if (temp != null) {
            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).getUsername().equalsIgnoreCase(username)) {
                    a = i;
                    break;
                }
            }
        }
        return a;
    }


}
