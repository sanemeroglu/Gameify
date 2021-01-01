package com.example.gameify;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    TextView tv_gameify, tv_username, tv_user_password, tv_create_account;
    private EditText et_username, et_password;
    private CheckBox cb_robot;
    Button but_login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_gameify = (TextView) findViewById(R.id.tv_gameify);
        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_user_password = (TextView) findViewById(R.id.tv_user_password);
        tv_create_account = (TextView) findViewById(R.id.tv_create_account);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        cb_robot = (CheckBox) findViewById(R.id.cb_robot);
        but_login_button = (Button) findViewById(R.id.but_login_button);


        loadData();


        // Silinecek
        tv_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                Intent intent =  new Intent(MainActivity.this, afterLoginMainPage.class);
                int index = 0;
                intent.putExtra("index",index);
                startActivity(intent);

            }
        });

        tv_create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, createAccountWindow.class);
                startActivity(intent);
            }
        });

        but_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                userLogin();
                /*int index = 0;
                String username_from_et = et_username.getText().toString();
                String password_from_et = et_password.getText().toString();
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("username", username_from_et);
                parameters.put("password",password_from_et);
                HttpManager httpManager = new HttpManager();
                StringRequest postrequest = httpManager.send_post_request("http://192.168.1.32:5000/login",parameters);
                queue.add(postrequest);
                System.out.println("Result is " + HttpManager.result);
                if(HttpManager.result.equals("Successful")){
                    if (cb_robot.isChecked()) {
                        et_username.setText("");
                        et_password.setText("");
                        Toast.makeText(MainActivity.this, "Login Succesful!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, afterLoginMainPage.class);
                        intent.pText(MainActivity.this, "Entered password is not matched with username's password!!", Toast.LENGTH_SHORT).show();
                    }
                    utExtra("index", index);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Please verify you're human!!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.make
                if (userAccount.getUserAccountArrayList().size() == 0){
                    Toast.makeText(MainActivity.this, "There is no entered data!!", Toast.LENGTH_SHORT).show();
                }else {
                    for (int i = 0; i < userAccount.getUserAccountArrayList().size(); i++) {
                        if (username_from_et.equalsIgnoreCase(userAccount.getUserAccountArrayList().get(i).getUsername())) {
                            if (password_from_et.equalsIgnoreCase(userAccount.getUserAccountArrayList().get(i).getPassword())) {
                                index = i;
                                if (cb_robot.isChecked()) {
                                    et_username.setText("");
                                    et_password.setText("");
                                    Toast.makeText(MainActivity.this, "Login Succesful!!", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(MainActivity.this, afterLoginMainPage.class);
                                    intent.putExtra("index", index);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(MainActivity.this, "Please verify you're human!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Entered password is not matched with username's password!!", Toast.LENGTH_SHORT).show();
                                et_password.setText("");
                            }
                        }
                    }
                }*/
            }

        });
    }

    private void firstMethodofAdmin() {
        userAccount userAdmin = new userAccount("Admin","Admin","Admin","Admin","admin","123","123");
        userAccount.getUserAccountArrayList().add(userAdmin);
        gameData gd = new gameData("admin", "admin", "100", "100", "100", "100");
        gameData.getAllUserData().add(gd);
        saveData();
    }

    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userAccount.getUserAccountArrayList());
        editor.putString("userlist",json);
        String jsonAllUserData = gson.toJson(gameData.getAllUserData());
        editor.putString("jsonAllUserData", jsonAllUserData);
        editor.apply();

    }


    private void clearData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared_preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        /*
        editor.remove("userlist");
        editor.remove("jsonAllUserData");
         */
        editor.apply();

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
    private void userLogin() {
        //first getting the values
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(MainActivity.this, "Please enter your username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(MainActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.32:5000/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (obj.get("Login").equals("Successful")) {
                                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                JSONObject games = obj.getJSONObject("gameinfo");
                                ArrayList<JSONObject> gameList =  new ArrayList<>();
                                gameList.add(games);
                                //creating a new user object
                                User user = new User(
                                        userJson.getInt("age"),
                                        userJson.getString("name"),
                                        userJson.getString("surname"),
                                        userJson.getString("username"),
                                        userJson.getString("mail"),
                                        gameList


                                );
                                games.remove("score");
                                games.put("score","11");
                                user.addGame(games);
                                System.out.println(user.getGamedata());
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the profile activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), afterLoginMainPage.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Error Login", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }



}
