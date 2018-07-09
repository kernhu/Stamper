# Stamper
Stamper is a tool for stamping a pattern into a picture,it likes a watermark.

Stamper是一个给图片打水印的工具，支持图片水印和文字水印，水印位置可以任意调节。



![](https://github.com/KernHu/Stamper/raw/master/screenshot/screenshot1.png)  
![](https://github.com/KernHu/Stamper/raw/master/screenshot/screenshot2.png)  

##  I: How to use Stamper.
### 1.use it stamp a text;

```
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
```

### 2.use it stamp a image;

```
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
```
### 3.the callback

```
StampWatcher mStampWatcher = new StampWatcher() {
        @Override
        protected void onSuccess(Bitmap bitmap, int requestId) {
            super.onSuccess(bitmap, requestId);
			
            switch (requestId) {

                case 1001:
                    //the result of text stamper

                    break;
                case 1002:
                    //the result of image stamper

                    break;
            }
        }

        @Override
        protected void onError(String error, int requestId) {
            super.onError(error, requestId);

            switch (requestId) {

                case 1001://

                    break;
                case 1002://

                    break;
            }
        }
    };

```

## II: Add Stamper to your project

### Step 1. Add the JitPack repository to your build file; Add it in your root build.gradle at the end of repositories:
```
	allprojects {
        repositories {
        ...
        maven { url 'https://jitpack.io' }
        }
        }
```
### Step 2. Add the dependency
```
	dependencies {
	         implementation 'com.github.KernHu:Stamper:1.1'
	}
```
## III: Contact me

Email: vsky580@gmail.com  
Facebook: https://www.facebook.com/kern.hu.580

QQ群：43447852

I'm kern....

If it helps you,please give me a star.如果有帮助到你，请给我一个小星星。

