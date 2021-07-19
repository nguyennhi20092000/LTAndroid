package com.example.appbanhangnew.controller.User_Admin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appbanhangnew.action.User.MainActivity;
import com.example.appbanhangnew.model.User;
import com.example.appbanhangnew.until.CheckConnection;
import com.example.appbanhangnew.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class FacebookController {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public FacebookController(Context context, SharedPreferences sharedPreferences, SharedPreferences.Editor editor) {
        this.context = context;
        this.sharedPreferences = sharedPreferences;
        this.editor = editor;
    }

    public void CheckIdFB(User user) {
        Log.d("LogFB",user.getSodienthoai());
        new CountCheckSDT(user).execute(Server.duongdancountCheckSDT);
    }

    class CountCheckSDT extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password


        User user;

        public CountCheckSDT(User user) {
            this.user = user;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("sodienthoai", String.valueOf(user.getSodienthoai())).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdancountCheckSDT).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //nhận dữ liệu;
            //gán layout;
            //Log.d("AAA",s);
            int count;

            try {
                JSONArray jsonArray= new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                count=(jsonObject.getInt("countuser"));
                if(count<1){
                    AddUser(user);
                }else {
                    new getIDofUser(user).execute(Server.duongdangetidOfSDT);
                }
                Log.d("LogFB",s);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("LogFB",e.toString());
            }

            super.onPostExecute(s);
        }
    }
    class getIDofUser extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        User user;

        public getIDofUser(User user) {
            this.user = user;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("sodienthoai", String.valueOf(user.getSodienthoai())).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdangetidOfSDT).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //nhận dữ liệu;
            //gán layout;
            //Log.d("AAA",s);
            int id;

            try {
                JSONArray jsonArray= new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                id=(jsonObject.getInt("iduser"));
                AccountController accountController= new AccountController(context);
                accountController.Account_Controller(2,user.getSodienthoai(),user.getPassword(),context);

            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("LogFB",e.toString());
            }

            super.onPostExecute(s);
        }
    }


    public void AddUser(User user) {
        Log.d("LogFB","12344");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdangetIdUser, new com.android.volley.Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Integer> count = new ArrayList<>();

                //bắt điều kiện nếu dl có thì th đọc dl, nếu k tb về
                if (requestQueue != null) {
                    //Loc từng Json object con.
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            count.add(jsonObject.getInt("iduser"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("LogFB",e.toString());
                        }
                    }
                    Log.d("LogFB",count.size()+"");
                    //Toast.makeText(context, count.size() + "", Toast.LENGTH_LONG).show();
                    if (count.size() < 1) {
                        new PostToserver(user).execute(Server.duongdaninsertuser);
                    } else {
                        for (int j = 1; j < count.get(count.size() - 1) + 2; j++) {
                            if (CheckId(count, j) == true) {
                                user.setIduser(j);
                                new PostToserver(user).execute(Server.duongdaninsertuser);
                                break;
                            }
                        }
                    }
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Xuất những lỗi ra
                CheckConnection.Show_Tost_Short(context, error.toString());
            }
        });
        //Cho việc lọc dl thực hiện,thì gọi lại truyền request muốn truyền lên server.
        requestQueue.add(jsonArrayRequest);

    }

    public boolean CheckId(ArrayList<Integer> checkid, int check) {
        for (int i = 0; i < checkid.size(); i++) {
            if (check == checkid.get(i)) {
                return false;
            }
        }
        return true;
    }


    class PostToserver extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        User user;

        public PostToserver(User user) {
            this.user = user;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;

            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("id_re", String.valueOf(user.getIduser()))
                    .addFormDataPart("id_per", String.valueOf(2))
                    .addFormDataPart("iduser", String.valueOf(user.getIduser()))
                    .addFormDataPart("sodienthoai", user.getSodienthoai())
                    .addFormDataPart("password", user.getPassword()).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdaninsertuser).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("LogFB",e.toString());
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //nhận dữ liệu;
            //gán layout;
            //Log.d("AAA",s);
            int success = 0;
            String message = "";

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                message = jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            if (success == 1) {
                //Intent intent= new Intent(context, UserActivity.class);
                //intent.putExtra("ID_USER",id);
                //context.startActivity(intent);
                new AddInformation(user).execute(Server.duongdansetInformation);
            } else {

            }
            super.onPostExecute(s);
        }
    }


    class AddInformation extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        User user;


        public AddInformation(User user) {
            this.user = user;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iduser", String.valueOf(user.getIduser()))
                    .addFormDataPart("Ten", user.getTen())
                    .addFormDataPart("Ngaysinh", user.getNgaysinh())
                    .addFormDataPart("Diachi", user.getDiachi())
                    .addFormDataPart("sodienthoai", user.getSodienthoai())
                    .addFormDataPart("Email", user.getEmail())
                    .addFormDataPart("image", user.getImage()).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdansetInformation).post(requestBody).build();
            try {
                //Trả giá trị dề; Thực hiện request;
                okhttp3.Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            //nhận dữ liệu;
            //gán layout;

            Log.d("AAA", s);

            int success = 0;
            String message = "";

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getInt("success");
                message = jsonObject.getString("message");

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            if (success == 1) {
                setSharedPreferences(true, user.getIduser(), user.getIduser(), user.getSodienthoai(), "", user.getTen(), user.getNgaysinh(), "", user.getEmail(), "");
                //if(id_per==1)
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            } else {

            }

            super.onPostExecute(s);
        }
    }

    private void setSharedPreferences(boolean status, int id_per, int iduser, String sodienthoai, String password, String ten, String ngaysinh, String diachi, String email, String image) {
        sharedPreferences = context.getSharedPreferences("USER_FILE", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if (status == false) {
            editor.clear();
        } else {
            editor.clear();
            editor.putInt("id_per", id_per);
            editor.putInt("iduser", iduser);
            editor.putString("sodienthoai", sodienthoai);
            editor.putString("password", password);
            editor.putString("ten", ten);
            editor.putString("ngaysinh", ngaysinh);
            editor.putString("diachi", diachi);
            editor.putString("email", email);
            editor.putString("image", image);
        }
        editor.commit();
    }
}
