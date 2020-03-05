package com.bba.playdate1;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by learntodrill on 31/05/18.
 */

public class YouApplication extends Application
{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
