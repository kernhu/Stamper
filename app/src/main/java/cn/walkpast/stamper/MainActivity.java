package cn.walkpast.stamper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.walkpast.stamperlib.StampPadding;
import cn.walkpast.stamperlib.StampType;
import cn.walkpast.stamperlib.StampWatcher;
import cn.walkpast.stamperlib.Stamper;

public class MainActivity extends AppCompatActivity {
    private TextView mBtnImage;
    private TextView mBtnText;
    private ImageView mShowImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnImage = findViewById(R.id.btn_image);
        mBtnText = findViewById(R.id.btn_text);
        mShowImage = findViewById(R.id.show_image);
        mBtnImage.setOnClickListener(mOnClickListener);
        mBtnText.setOnClickListener(mOnClickListener);

    }


    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_text:

                    Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.sample_plot_3);
                    Stamper.with(MainActivity.this)
                            .setLabel("National Geography")
                            .setLabelColor(getResources().getColor(R.color.theme))//Color.rgb(255, 60, 70)
                            .setLabelSize(60)
                            .setMasterBitmap(bitmap3)
                            .setStampType(StampType.TEXT)
                            .setStampPadding(new StampPadding(bitmap3.getWidth() / 4, bitmap3.getHeight() / 6))
                            .setStampWatcher(mStampWatcher)
                            .setRequestId(1001)
                            .build();


                    break;

                case R.id.btn_image:

                    mShowImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sample_plot_4));

                    Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.sample_plot_4);
                    Bitmap watermark = BitmapFactory.decodeResource(getResources(), R.drawable.ic_watermark);
                    Stamper.with(MainActivity.this)
                            .setMasterBitmap(bitmap4)
                            .setWatermark(watermark)
                            .setStampType(StampType.IMAGE)
                            .setStampPadding(new StampPadding(bitmap4.getWidth() - watermark.getWidth() - 40, 40))
                            .setStampWatcher(mStampWatcher)
                            .setRequestId(1002)
                            .build();

                    break;
            }
        }
    };

    StampWatcher mStampWatcher = new StampWatcher() {
        @Override
        protected void onSuccess(Bitmap bitmap, int requestId) {
            super.onSuccess(bitmap, requestId);

            switch (requestId) {

                case 1001:
                    //the result of text stamper

                    mShowImage.setImageBitmap(bitmap);

                    break;
                case 1002:
                    //the result of image stamper

                    mShowImage.setImageBitmap(bitmap);

                    break;
            }
        }

        @Override
        protected void onError(String error, int requestId) {
            super.onError(error, requestId);

            switch (requestId) {

                case 1001://

                    Toast.makeText(MainActivity.this, "error:" + error, Toast.LENGTH_SHORT).show();

                    break;
                case 1002://

                    Toast.makeText(MainActivity.this, "error:" + error, Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };
}
