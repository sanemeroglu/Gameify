package com.example.gameify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_AGE = "keyage";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_SURNAME = "keysurname";
    private static final String KEY_GAMEDATA = "keygamedata";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the user login
    //this method will store the user data in shared preferences
    public void userLogin(User user) throws JSONException {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_AGE, user.getAge());
        editor.putString(KEY_USERNAME, user.getUsername());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_SURNAME, user.getSurname());
        Set<String> game_set= new HashSet<String>();
        game_set.add(user.getGamedata().get(0).getString("game"));
        game_set.add(user.getGamedata().get(0).getString("gametype"));
        game_set.add(user.getGamedata().get(0).getString("level"));
        game_set.add(user.getGamedata().get(0).getString("score"));
        editor.putStringSet(KEY_GAMEDATA,game_set);
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public User getUser() throws JSONException {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ArrayList<JSONObject> gamedata = new ArrayList<>();
        JSONObject game = new JSONObject();
        Set<String> game_set=sharedPreferences.getStringSet(KEY_GAMEDATA,null);
        game.put("game",get_nth_element_from_set(0,game_set));
        game.put("gametype",get_nth_element_from_set(1,game_set));
        game.put("level",get_nth_element_from_set(2,game_set));
        game.put("score",get_nth_element_from_set(3,game_set));
        gamedata.add(game);
        return new User(
                sharedPreferences.getInt(KEY_AGE, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_SURNAME, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                gamedata
        );
    }
    public String get_nth_element_from_set(int n,Set<String> game_set){
        int index = 0;
        for(String element : game_set){
            if(index == n){
                return element;
            }
            index++;
        }
        return null;
    }
    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, MainActivity.class));
    }
}
