package xyz.no87.mywallpaper.api;

import android.content.Context;
import android.os.SystemClock;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpRequest;
import com.loopj.android.http.ResponseHandlerInterface;

import cz.msebera.android.httpclient.client.methods.HttpUriRequest;
import cz.msebera.android.httpclient.impl.client.AbstractHttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HttpContext;
import xyz.no87.mywallpaper.base.BaseApplication;
import xyz.no87.mywallpaper.utils.Network;

/**
 * Created by linchen on 2017/11/7.
 */

public class ApiHttpClient {

    static class ApiAsyncHttpClient extends AsyncHttpClient {

        @Override
        protected AsyncHttpRequest newAsyncHttpRequest(DefaultHttpClient client, HttpContext httpContext, HttpUriRequest uriRequest, String contentType, ResponseHandlerInterface responseHandler, Context context) {
            return new CheckNetAsyncHttpRequest(client, httpContext, uriRequest, responseHandler);
        }
    }

    static class CheckNetAsyncHttpRequest extends AsyncHttpRequest {
        public CheckNetAsyncHttpRequest(AbstractHttpClient client, HttpContext context, HttpUriRequest request, ResponseHandlerInterface responseHandler) {
            super(client, context, request, responseHandler);
        }

        @Override
        public void run() {
            // 如果网络本身有问题则直接取消
            if (Network.isAvailable(BaseApplication.getInstance().getApplicationContext())) {
                new Thread() {
                    @Override
                    public void run() {
                        // 延迟一秒
                        try {
                            SystemClock.sleep(1000);
                            cancel(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
            super.run();
        }
    }
}
