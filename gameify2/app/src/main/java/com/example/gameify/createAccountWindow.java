package com.example.gameify;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class createAccountWindow extends AppCompatActivity {

    private EditText et_name_accPage, et_surname_accPage;
    private EditText et_email_accPage;
    private EditText et_username_accPage, et_password_accPage, et_re_password_accPage;
    Button but_create_button_accPage;
    Spinner sp_age_accPage;
    private String[] age_data_array = new String[66];
    private String age_output_fromSpinner = "";
    private String game_output_fromSpinner = "";
    private String level_output_fromSpinner = "";
    private RequestQueue queue;
    private Spinner sp_game, sp_level;
    String[] games = {"Games","CS-GO", "PUBG", "GTA V", "LOL" , "Metin 2", "Rocket League", "Teamfight Tactics",
            "Dota 2","Smite","WOW", "TERA", "FIFA 21", "Dota Auto Chess" ,"Tom Clancy's Rainbow Six Siege","Valorant"};
    private String[] level_data = new String[102];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        queue = Volley.newRequestQueue(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account_window);

        int age = 10;
        age_data_array[0] = "Age";
        for (int i=1 ; i<=65 ; i++){
            age_data_array[i] = String.valueOf(age);
            age++;
        }

        int level = 0;
        level_data[0] = "Level";
        for (int i=1 ; i<level_data.length ;i++){
            level_data[i] = String.valueOf(level);
            level++;
        }

        sp_age_accPage = (Spinner) findViewById(R.id.sp_age_accPage);
        et_name_accPage = (EditText) findViewById(R.id.et_name_accPage);
        et_surname_accPage = (EditText) findViewById(R.id.et_surname_accPage);
        et_email_accPage = (EditText) findViewById(R.id.et_email_accPage);
        et_username_accPage = (EditText) findViewById(R.id.et_username_accPage);
        et_password_accPage = (EditText) findViewById(R.id.et_password_accPage);
        et_re_password_accPage = (EditText) findViewById(R.id.et_re_password_accPage);
        but_create_button_accPage = (Button) findViewById(R.id.but_create_button_accPage);
        sp_game = (Spinner) findViewById(R.id.sp_game);
        sp_level= (Spinner) findViewById(R.id.sp_level);

        ArrayAdapter aa_age = new ArrayAdapter(this, android.R.layout.simple_spinner_item, age_data_array);
        aa_age.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_age_accPage.setAdapter(aa_age);

        ArrayAdapter aa_game = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, games);
        aa_game.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_game.setAdapter(aa_game);

        ArrayAdapter aa_level = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, level_data);
        aa_level.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_level.setAdapter(aa_level);


        sp_age_accPage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if (position == 0){
                    tv.setTextColor(Color.GRAY);
                    age_output_fromSpinner = "";
                }else{
                    tv.setTextColor(Color.BLACK);
                    age_output_fromSpinner = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_game.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                TextView tv = (TextView) view;
                if (position == 0){
                    tv.setTextColor(Color.GRAY);
                    game_output_fromSpinner = "";
                }else{
                    tv.setTextColor(Color.BLACK);
                    game_output_fromSpinner = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sp_level.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                if (position == 0){
                    tv.setTextColor(Color.GRAY);
                    level_output_fromSpinner = "";
                }else{
                    tv.setTextColor(Color.BLACK);
                    level_output_fromSpinner = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        but_create_button_accPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SQLiteDatabase mDataBase = SQLiteDatabase.openDatabase("mydb_backup", null, SQLiteDatabase.CREATE_IF_NECESSARY);
                //RequestQueue requestQueue = Volley.newRequestQueue();
                String name = et_name_accPage.getText().toString().trim();
                String surname = et_surname_accPage.getText().toString().trim();
                String age = age_output_fromSpinner;
                String email = et_email_accPage.getText().toString().trim();
                String username = et_username_accPage.getText().toString().trim();
                String password = et_password_accPage.getText().toString().trim();
                String re_password = et_re_password_accPage.getText().toString().trim();
                String game = game_output_fromSpinner;
                String level = level_output_fromSpinner;
                Map<String,String> parameters = new HashMap<String, String>();
                parameters.put("name", name);
                parameters.put("surname",surname);
                parameters.put("username", username);
                parameters.put("mail",email);
                parameters.put("age", age);
                parameters.put("password",password);
                parameters.put("game",game);
                parameters.put("level",level);

                /*try {
                    URL obj = new URL("http://192.168.1.32:5000");
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    con.setRequestMethod("GET");
                    int responseCode = con.getResponseCode();

                    System.out.println("GET Response Code :: " + responseCode);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                */
                //requestQueue.add(jsObjRequest);

                boolean status = check(name,surname,age,email,username,password,re_password);

                if (status){
                    if (password.equalsIgnoreCase(re_password)){
                        final String[] result = {""};
                        HttpManager httpManager = new HttpManager();
                        StringRequest postrequest = httpManager.send_post_request("http://192.168.1.32:5000/adduser",parameters);
                        System.out.println("result is " + result[0]);
                        queue.add(postrequest);
                        userAccount new_user = new userAccount(name, surname, age, email, username, password, re_password);
                        //userAccount.getUserAccountArrayList().add(new_user);
                        gameData temp = new gameData(username, age, "0", "0", "0", "0");
                       //gameData.getAllUserData().add(temp);


                        //DataBaseHelper dataBaseHelper = new DataBaseHelper(createAccountWindow.this);
                        //dataBaseHelper.addData(new_user, temp);


                        createAccountWindow.this.finish();
                    }else{
                        Toast.makeText(createAccountWindow.this, "Please check your passwords..", Toast.LENGTH_SHORT).show();
                        et_password_accPage.setText("");
                        et_re_password_accPage.setText("");
                    }
                }else {
                    Toast.makeText(createAccountWindow.this, "Please fill all information!!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
    private boolean check(String name, String surname, String age, String email, String username, String password, String re_password) {
        return !name.isEmpty() && !surname.isEmpty() && !age.isEmpty() && !email.isEmpty() && !username.isEmpty() && !password.isEmpty() && !re_password.isEmpty();
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
}