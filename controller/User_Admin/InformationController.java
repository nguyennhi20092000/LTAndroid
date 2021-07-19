package com.example.appbanhangnew.controller.User_Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.appbanhangnew.until.APIUtils;
import com.example.appbanhangnew.until.DataClient;
import com.example.appbanhangnew.until.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.spec.ECField;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationController {
    public InformationController() {
    }


    public void EditInformation(boolean setimage,String linkgetImage,int id,String hoten, String ngaysinh, String email, String Diachi, String sodienthoai,String password,Context context,String realpath){
        if (hoten.length() > 0 && ngaysinh.length() > 0 && email.length() > 0 && Diachi.length() > 0) {
            //Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT);
            if(setimage==true){
                try {
                    File file = new File(realpath);
                    //đường dẩn
                    String file_path = file.getAbsolutePath();
                    String[] mangtenfile = file_path.split("\\.");

                    file_path = mangtenfile[0] + System.currentTimeMillis() + "." + mangtenfile[1];
                    Log.d("FilePath", file_path);

                    //Kiểu dữ liệu của file
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/from-data"), file);

                    MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file", file_path, requestBody);
                    DataClient dataClient = APIUtils.getData();
                    retrofit2.Call<String> callback = dataClient.UploadImage(body);

                    //Khi sữ dụng dạng call trả về 2 gt là thành công và thất bạo
                    callback.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(retrofit2.Call<String> call, Response<String> response) {
                            if (response != null) {
                                String message = response.body();
                                Log.d("BBBB", message.toString());
                                //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                                if (message.length() > 0) {
                                    Log.d("BBBB", "true");
                                    //gửi những interface của dataClient lên server.
                                    //Sau khi dữ liệu đã có sẽ tạo ra đối tượng object sẽ nhận dl bên trong class dataClient.

                                    //up hình ảnh từ thư mục: lấy ip + server+ image+ tên file
                                    String duongdan = Server.duongdanSaveImageToDatabase + "image/" + message;
                                    new PostToserver(hoten, ngaysinh, Diachi, sodienthoai, email, duongdan,id ,password,context).execute(Server.duongdansetInformation);
                                }


                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            //Toast.makeText(getApplicationContext(),call.toString(),Toast.LENGTH_SHORT).show();
                            Log.d("BBB4", t.getMessage());
                        }
                    });
                }catch (Exception e){
                    new PostToserver(hoten, ngaysinh, Diachi, sodienthoai, email, linkgetImage,id ,password,context).execute(Server.duongdansetInformation);

                }

            }else {
                new PostToserver(hoten, ngaysinh, Diachi, sodienthoai, email, linkgetImage,id ,password,context).execute(Server.duongdansetInformation);
            }


        } else {
            Toast.makeText(context, "Thông tin chưa nhập đủ", Toast.LENGTH_SHORT);
        }
    }
    class PostToserver extends AsyncTask<String, Void, String> {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();

        //Truyền dữ liệu user và password
        String hoten, ngaysinh, diachi, sodienthoai, email, image,password;
        int iduser;
        Context context;

        public PostToserver(String hoten, String ngaysinh, String diachi, String sodienthoai, String email, String image, int iduser,String password,Context context) {
            this.hoten = hoten;
            this.ngaysinh = ngaysinh;
            this.diachi = diachi;
            this.sodienthoai = sodienthoai;
            this.email = email;
            this.image = image;
            this.iduser = iduser;
            this.context=context;
            this.password=password;
        }

        @Override
        protected String doInBackground(String... strings) {
            // gửi dl lên server;
            RequestBody requestBody = new MultipartBody.Builder()
                    //gửi lên server qua key;
                    .addFormDataPart("iduser", String.valueOf(iduser))
                    .addFormDataPart("Ten", hoten)
                    .addFormDataPart("Ngaysinh", ngaysinh)
                    .addFormDataPart("Diachi", diachi)
                    .addFormDataPart("sodienthoai", sodienthoai)
                    .addFormDataPart("Email", email)
                    .addFormDataPart("image", image).
                            setType(MultipartBody.FORM)
                    .build();
            //Gọi request; chọn tv của okhttp3;
            Request request = new Request.Builder()
                    //Truyền url;
                    .url(Server.duongdanEditInformation).post(requestBody).build();
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
                SharedPreferences sharedPreferences;
                SharedPreferences.Editor editor;
                sharedPreferences=context.getSharedPreferences("USER_FILE", context.MODE_PRIVATE);
                editor=sharedPreferences.edit();
                setSharedPreferences(editor,iduser,sodienthoai,password,hoten,ngaysinh,diachi,email,image);

            } else {

            }

            super.onPostExecute(s);
        }
    }
    private void setSharedPreferences(SharedPreferences.Editor editor,int iduser, String sodienthoai, String password, String ten, String ngaysinh, String diachi,String email, String image){
        editor.clear();
        editor.commit();
        editor.putInt("iduser",iduser);

        editor.putString("sodienthoai",sodienthoai);
        editor.putString("password",password);
        editor.putString("ten",ten);
        editor.putString("ngaysinh",ngaysinh);
        editor.putString("diachi",diachi);
        editor.putString("email",email);
        editor.putString("image",image);
        editor.commit();
    }
}
