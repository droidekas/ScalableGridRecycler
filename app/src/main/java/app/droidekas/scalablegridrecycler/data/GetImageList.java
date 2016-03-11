package app.droidekas.scalablegridrecycler.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Satyarth on 11/03/16.
 */
public class GetImageList extends AsyncTask<Void, Void, Void> {
    ContentResolver resolver;
    private Context context;
    private int maxItems = 2000;
    private List<Uri> tiles = new ArrayList<>();
    private GotImages mGotImages;


    public GetImageList(Context context, GotImages mGotImages) {
        this.context = context;
        this.mGotImages = mGotImages;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Add local images, in descending order of date taken
        String[] projection = new String[]{
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATA,
                MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                MediaStore.Images.ImageColumns.DATE_TAKEN,
                MediaStore.Images.ImageColumns.MIME_TYPE
        };
        resolver = context.getContentResolver();

        final Cursor cursor = resolver
                .query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null,
                        null, MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");

        if (cursor != null) {
            int count = 0;
            while (cursor.moveToNext() && count < maxItems) {
                String imageLocation = cursor.getString(1);
                File imageFile = new File(imageLocation);
                if (imageFile.exists()) {
                    tiles.add(Uri.fromFile(imageFile));
                }
                ++count;
            }
            cursor.close();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        mGotImages.gotImageList(tiles);
    }

    public interface GotImages {
        void gotImageList(List<Uri> imageUriList);
    }
}
