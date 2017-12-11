package com.top.gamehandle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.InputStream;

/**
 * Created by Administrator on 2016/10/8.
 */

public class decodeAnimFromImageArray {

    private FrameAnimation []spriteAnimations;
    private int row = 4;
    private int col = 4;

    private int spriteWidth = 0;
    private int spriteHeight = 0;



    public FrameAnimation[] initResources(Context mContext) {

        Bitmap[][] spriteImgs = generateBitmapArray(mContext, R.drawable.sprite, row, col);
        spriteAnimations = new FrameAnimation[row];
        for(int i = 0; i < row; i ++) {
            Bitmap []spriteImg = spriteImgs[i];
            FrameAnimation spriteAnimation = new FrameAnimation(spriteImg,new int[]{150,150,150,150},true);
            spriteAnimations[i] = spriteAnimation;
        }
        return spriteAnimations;
    }

    public Bitmap decodeBitmapFromRes(Context context, int resourseId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resourseId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public Bitmap createBitmap(Context context, Bitmap source, int row, int col, int rowTotal, int colTotal) {
        Bitmap bitmap = Bitmap.createBitmap(source,
                (col - 1) * source.getWidth() / colTotal,
                (row - 1) * source.getHeight() / rowTotal, source.getWidth()
                        / colTotal, source.getHeight() / rowTotal);
        return bitmap;
    }

    public Bitmap[][] generateBitmapArray(Context context, int resourseId, int row, int col) {
        Bitmap bitmaps[][] = new Bitmap[row][col];
        Bitmap source = decodeBitmapFromRes(context, resourseId);
        this.spriteWidth = source.getWidth() / col;
        this.spriteHeight = source.getHeight() / row;
        for (int i = 1; i <= row; i++) {
            for (int j = 1; j <= col; j++) {
                bitmaps[i - 1][j - 1] = createBitmap(context, source, i, j,
                        row, col);
            }
        }
        if (source != null && !source.isRecycled()) {
            source.recycle();
            source = null;
        }
        return bitmaps;
    }
}
