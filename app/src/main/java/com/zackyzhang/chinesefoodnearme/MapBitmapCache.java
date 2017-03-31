package com.zackyzhang.chinesefoodnearme;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

/**
 * Created by lei on 3/29/17.
 */

public class MapBitmapCache extends LruCache<String, Bitmap> {
    private static final String TAG = "MapBitmapCache";
    private static final int DEFAULT_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
    public static final String KEY = "MAP_BITMAP_KEY";

    private static MapBitmapCache sInstance;

    /**
     * @param maxSize for caches that do not override {@link #sizeOf}, this is
     *                the maximum number of entries in the cache. For all other caches,
     *                this is the maximum sum of the sizes of the entries in this cache.
     */
    private MapBitmapCache(int maxSize) {
        super(maxSize);
    }

    public static MapBitmapCache instance() {
        if (sInstance == null) {
            sInstance = new MapBitmapCache(DEFAULT_CACHE_SIZE);
            Log.d(TAG, sInstance.toString());
            return sInstance;
        }
        Log.d(TAG, sInstance.toString());
        return sInstance;
    }

    public Bitmap getBitmap() {
        Log.d(TAG, "get bitmap in getBitmap: " + get(KEY));
        return get(KEY);
    }

    public void putBitmap(Bitmap bitmap) {
        put(KEY, bitmap);
        Log.d(TAG, "get bitmap after putBitmap: " + get(KEY));
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value == null ? 0 : value.getRowBytes() * value.getHeight() / 1024;
    }
}
