package com.example.loginscreen;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickLogin(View view) {
        EditText et_mail = (EditText) findViewById(R.id.mail);
        EditText et_password = (EditText) findViewById(R.id.password);
        String mail = et_mail.getText().toString();
        String password = et_password.getText().toString();
        GetResult(mail, password);
    }

    private void GetResult(String mail, String password) {
        //url to api
        String url = "https://api.cinquex.com/api/internshala/login";
        //RequestQueue to transport request
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JSONObject data = new JSONObject();
        //adding data in JSONObject
        try {
            data.put("email", mail);
            data.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //process the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, data, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(MainActivity.this,
                                    response.getString("message"),
                                    Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //get status code
                        final int statusCode = error.networkResponse.statusCode;
                        if (statusCode == 403) {
                            Toast.makeText(MainActivity.this, "Wrong Credentials",
                                    Toast.LENGTH_SHORT).show();
                        } else if (statusCode == 400) {
                            Toast.makeText(MainActivity.this, "Validation Error",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // unhandled status code
                            Toast.makeText(MainActivity.this, error.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                });
        requestQueue.add(jsonObjectRequest);
    }


}