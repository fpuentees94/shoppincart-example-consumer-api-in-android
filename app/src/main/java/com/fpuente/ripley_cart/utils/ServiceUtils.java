package com.fpuente.ripley_cart.utils;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;

import android.telephony.TelephonyManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.fpuente.ripley_cart.R;

import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.UUID;



public class ServiceUtils {



    public static String deviceUUID(final Context ctx) {
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        else {
            String uuid = tm.getDeviceId();
            if (uuid == null || uuid.equals("000000000000000")) {
                return shortUUID();
            }
            return uuid;
        }
    }

    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();

        long lsb = uuid.getLeastSignificantBits();
        long msb = uuid.getMostSignificantBits();

        byte[] uuidBytes = ByteBuffer.allocate(16).putLong(msb).putLong(lsb).array();

        return Base64.encodeToString(uuidBytes, Base64.DEFAULT)
                .substring(0, 22)
                .replace('/', '_')
                .replace('+', '-');
    }

    public static Drawable convertLayoutToImage(Context mContext, int count, int drawableId) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.badge_icon_layout, null);
        ((ImageView)view.findViewById(R.id.icon_badge)).setImageResource(drawableId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(mContext.getResources(), bitmap);
    }

}
