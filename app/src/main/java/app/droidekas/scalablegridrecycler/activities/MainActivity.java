package app.droidekas.scalablegridrecycler.activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import java.util.List;

import app.droidekas.scalablegridrecycler.R;
import app.droidekas.scalablegridrecycler.adapter.GridAdapter;
import app.droidekas.scalablegridrecycler.data.GetImageList;
import app.droidekas.scalablegridrecycler.views.GridRecyclerView;
import butterknife.Bind;
import butterknife.ButterKnife;

import static app.droidekas.scalablegridrecycler.Logger.logger;

/**
 * Created by Satyarth on 11/03/16.
 */
public class MainActivity extends Activity {
    @Bind(R.id.image_grid_view)
    GridRecyclerView grv;
    GridLayoutManager glm;
    GridAdapter gridAdap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        new GetImageList(this, new GetImageList.GotImages() {
            @Override
            public void gotImageList(List<Uri> imageUriList) {
                logger(imageUriList.size());
                gridAdap.addData(imageUriList);

            }
        }).execute();
        grv = (GridRecyclerView) findViewById(R.id.image_grid_view);
        glm = new GridLayoutManager(this, GridAdapter.totalSpan);
        gridAdap = new GridAdapter(grv);
        grv.setLayoutManager(glm);
        glm.setSpanSizeLookup(gridAdap.getScalableSpanSizeLookUp());
        grv.setAdapter(gridAdap);

    }


}
