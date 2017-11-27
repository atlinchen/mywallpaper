package xyz.no87.mywallpaper;

import org.litepal.LitePal;

import xyz.no87.mywallpaper.base.BaseApplication;

/**
 * Created by 林晨 on 2017/11/27.
 */

public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    protected void init() {
        super.init();

        LitePal.initialize(this);
    }
}
