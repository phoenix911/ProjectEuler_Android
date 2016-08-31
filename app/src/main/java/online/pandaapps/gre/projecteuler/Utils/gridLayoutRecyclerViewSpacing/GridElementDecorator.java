package online.pandaapps.gre.projecteuler.Utils.gridLayoutRecyclerViewSpacing;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.DimenRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sangeet on 31/08/16.
 */
public class GridElementDecorator extends RecyclerView.ItemDecoration {

    private int itemOffset;

    public GridElementDecorator(int itemOffset){
        this.itemOffset = itemOffset;
    }

    public GridElementDecorator(@NonNull Context context, @DimenRes int itemOffsetId) {
        this(context.getResources().getDimensionPixelSize(itemOffsetId));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.set(itemOffset, itemOffset, itemOffset, itemOffset);
    }
}
