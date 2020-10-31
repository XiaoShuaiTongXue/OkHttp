package com.shuai.okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shuai.okhttp.dormain.CommentItem;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public class MainActivity extends AppCompatActivity {

    private static final String BASEURL = "http://192.168.31.152:9102";
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getRequest(View view){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3000,TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .get()
                .url(BASEURL)
                .build();
        //用浏览器创建任务
        Call task = client.newCall(request);
        //异步请求
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG,"创建失败");
                Log.i(TAG,e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.i(TAG,"Code:"+code);
                ResponseBody body = response.body();
                Log.i(TAG,"body:"+body.string());
            }
        });
    }

    public void paramRequest(View v){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(3000,TimeUnit.MILLISECONDS)
                .build();
        Request request = new Request.Builder()
                .get()
                .url(BASEURL+"/get/param?keyword=abc&page=1&order=0")
                .build();
        //用浏览器创建任务
        Call task = client.newCall(request);
        //异步请求
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG,"创建失败");
                Log.i(TAG,e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.i(TAG,"Code:"+code);
                if (code == HttpURLConnection.HTTP_OK){
                    ResponseBody body = response.body();
                    if (body!=null){
                        Log.i(TAG,body.string());
                    }
                }
            }
        });
    }

    //上传文件
    public void fileUpload(View view){
        Toast.makeText(this, getFilesDir().toString(), Toast.LENGTH_SHORT).show();
        OkHttpClient client = new  OkHttpClient.Builder()
                .connectTimeout(3000,TimeUnit.MILLISECONDS)
                .build();
        File file = new File("/data/data/com.shuai.okhttp/files/音乐 光盘.png");
        MediaType mediaType = MediaType.parse("image/png");
        RequestBody fileBody = RequestBody.create(file,mediaType);
        RequestBody  requestBody = new MultipartBody.Builder()
                .addFormDataPart("file",file.getName(),fileBody)
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASEURL+"/file/upload")
                .build();
        Call task = client.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG,"失败了");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.i(TAG,"code:"+code);
                if (code == HttpURLConnection.HTTP_OK){
                    ResponseBody body = response.body();
                    if (body!=null){
                        Log.i(TAG,body.string());
                    }
                }
            }
        });
    }


    //用post方法传数据数据
    public void postRequest(View view){
        OkHttpClient client = new  OkHttpClient.Builder()
                .connectTimeout(3000,TimeUnit.MILLISECONDS)
                .build();
        CommentItem comment = new CommentItem(1,"这是第一条评论");
        Gson gson = new Gson();
        String jsonStr = gson.toJson(comment);
        Log.i(TAG,jsonStr);
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody requestBody = RequestBody.create(jsonStr,mediaType);

        Request request = new Request.Builder()
                .post(requestBody)
                .url(BASEURL+"/post/comment")
                .build();
        Call task = client.newCall(request);
        task.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.i(TAG,"失败了");
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                int code = response.code();
                Log.i(TAG,"code:"+code);
                if (code == HttpURLConnection.HTTP_OK){
                    ResponseBody body = response.body();
                    if (body!=null){
                        Log.i(TAG,body.string());
                    }
                }
            }
        });
    }
}