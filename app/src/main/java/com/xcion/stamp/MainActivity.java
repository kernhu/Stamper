package com.xcion.stamp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.xcion.stamper.StampCoordinate;
import com.xcion.stamper.StampType;
import com.xcion.stamper.StampUtils;
import com.xcion.stamper.StampWatcher;
import com.xcion.stamper.Stamper;

public class MainActivity extends AppCompatActivity {

    private TextView mBtnImage;
    private TextView mBtnText;
    private TextView mBtnTeleText;
    private ImageView mShowImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnImage = findViewById(R.id.btn_image);
        mBtnText = findViewById(R.id.btn_text);
        mBtnTeleText = findViewById(R.id.btn_teletext);
        mShowImage = findViewById(R.id.show_image);
        mBtnImage.setOnClickListener(mOnClickListener);
        mBtnText.setOnClickListener(mOnClickListener);
        mBtnText.setOnClickListener(mOnClickListener);
        mBtnTeleText.setOnClickListener(mOnClickListener);

        findViewById(R.id.root_layout).setOnClickListener(mOnClickListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Stamper.onDestroy();
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.btn_text:

                    Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.sample_plot_3);
                    Stamper.with()
                            .setLabel("National Geography")
                            .setLabelColor(getResources().getColor(R.color.theme))//Color.rgb(255, 60, 70)
                            .setLabelSize(60)
                            .setMasterBitmap(bitmap3)
                            .setStampType(StampType.TEXT)
                            .setStampTextCoordinate(new StampCoordinate(0, 0))
                            .setStampWatcher(mStampWatcher)
                            .setRequestId(1001)
                            .build();

                    break;

                case R.id.btn_image:

                    mShowImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sample_plot_4));

                    Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.sample_plot_4);
                    Bitmap watermark = BitmapFactory.decodeResource(getResources(), R.drawable.ic_watermark);

                    int x = StampUtils.getImageWidthAndHeight(bitmap4)[0] - StampUtils.getImageWidthAndHeight(watermark)[0];
                    int y = StampUtils.getImageWidthAndHeight(bitmap4)[1] - StampUtils.getImageWidthAndHeight(watermark)[1];

                    Stamper.with()
                            .setMasterBitmap(bitmap4)
                            .setWatermark(watermark)
                            .setStampType(StampType.IMAGE)
                            .setStampImageCoordinate(new StampCoordinate(x, y))
                            .setStampWatcher(mStampWatcher)
                            .setRequestId(1002)
                            .build();

                    break;

                case R.id.btn_teletext:

                    mShowImage.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.sample_plot_4));

                    Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.sample_plot_4);
                    Bitmap watermark5 = BitmapFactory.decodeResource(getResources(), R.drawable.ic_watermark);

                    int textX = StampUtils.getImageWidthAndHeight(bitmap5)[0] / 2 - StampUtils.getTextWidthAndHeight("National Geography")[0] / 2;
                    int textY = StampUtils.getImageWidthAndHeight(bitmap5)[1] / 2 - StampUtils.getTextWidthAndHeight("National Geography")[1] / 2;
                    int imageX = StampUtils.getImageWidthAndHeight(bitmap5)[0] - StampUtils.getImageWidthAndHeight(watermark5)[0];
                    int imageY = StampUtils.getImageWidthAndHeight(bitmap5)[1] - StampUtils.getImageWidthAndHeight(watermark5)[1] + StampUtils.getTextWidthAndHeight("National Geography")[1];

                    Stamper.with()
                            .setLabel("National Geography")
                            .setLabelColor(getResources().getColor(R.color.theme))//Color.rgb(255, 60, 70)
                            .setLabelSize(60)
                            .setMasterBitmap(bitmap5)
                            .setWatermark(watermark5)
                            .setStampType(StampType.TELETEXT)
                            .setStampTextCoordinate(new StampCoordinate(textX, textY))
                            .setStampImageCoordinate(new StampCoordinate(imageX, imageY))
                            .setStampWatcher(mStampWatcher)
                            .setRequestId(1003)
                            .build();

                    break;

                default:

                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);


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
                case 1003:
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
                case 1003:
                    //the result of image stamper

                    Toast.makeText(MainActivity.this, "error:" + error, Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };
}
