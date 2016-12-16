package com.ashoksm.atozforkids.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashoksm.atozforkids.R;
import com.ashoksm.atozforkids.dto.ItemsDTO;
import com.ashoksm.atozforkids.utils.DecodeSampledBitmapFromResource;

import java.util.List;

public class GridPagerAdapter extends PagerAdapter {

    private LayoutInflater mLayoutInflater;
    private Activity context;
    private List<ItemsDTO> items;
    private SparseArray<Bitmap> bitmapCache;

    public GridPagerAdapter(Activity contextIn, List<ItemsDTO> itemsIn) {
        this.context = contextIn;
        this.items = itemsIn;
        this.mLayoutInflater =
                (LayoutInflater) contextIn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bitmapCache = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        final View itemView = mLayoutInflater.inflate(R.layout.slider_grid_view, container, false);

        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x - 10;
        int height = size.y - getStatusBarHeight() - (getActionBarHeight() * 2);
        int number = Integer.parseInt(items.get(position).getItemNumber());

        switch (number) {
            case 2:
                height = height / 2;
                break;
            case 3:
                height = height / 2;
                width = width / 2;
                break;
            case 4:
            case 5:
                height = height / 3;
                width = width / 2;
                break;
            case 6:
                height = height / 4;
                width = width / 2;
                break;
            case 7:
            case 8:
            case 9:
                height = height / 5;
                width = width / 2;
                break;
            case 10:
                height = height / 6;
                width = width / 2;
                break;
        }

        //set name
        final TextView name = (TextView) itemView.findViewById(R.id.name);
        name.setText(items.get(position).getItemName());

        Bitmap imageBitmap = bitmapCache.get(number);
        if (imageBitmap == null) {
            imageBitmap = DecodeSampledBitmapFromResource
                    .execute(context.getResources(), items.get(number - 1)
                            .getImageResource(), width, height);
            bitmapCache.put(number, imageBitmap);
        }

        Palette palette = Palette.from(imageBitmap).generate();
        TextView numTextView = (TextView) itemView.findViewById(R.id.number);
        TextView numBottom = (TextView) itemView.findViewById(R.id.number_bottom);
        numTextView.setText(String.valueOf(number));
        numBottom.setText(String.valueOf(number));
        int vibrantColor = palette.getVibrantColor(0x000000);
        if (vibrantColor == 0x000000) {
            vibrantColor = palette.getMutedColor(Color.parseColor("#212121"));
        }
        numTextView.setTextColor(vibrantColor);
        numBottom.setTextColor(vibrantColor);

        Palette.Swatch vibrantSwatch = palette.getVibrantSwatch();
        if (vibrantSwatch != null) {
            name.setBackgroundColor(vibrantSwatch.getRgb());
            name.setTextColor(vibrantSwatch.getTitleTextColor());
            items.get(position).setVibrantColor(vibrantSwatch.getRgb());
            items.get(position)
                    .setColorStateList(ColorStateList.valueOf(vibrantSwatch.getTitleTextColor()));
        } else {
            List<Palette.Swatch> swatches = palette.getSwatches();
            if (swatches.size() > 0) {
                Palette.Swatch swatch = swatches.get(0);
                name.setBackgroundColor(swatch.getRgb());
                name.setTextColor(swatch.getTitleTextColor());
                items.get(position).setVibrantColor(swatch.getRgb());
                items.get(position)
                        .setColorStateList(ColorStateList.valueOf(swatch.getTitleTextColor()));
            }
        }

        //create image grid
        final RecyclerView gridView = (RecyclerView) itemView.findViewById(R.id.gridView);
        gridView.setHasFixedSize(true);
        final GridLayoutManager manager = getGridLayoutManager(number);
        gridView.setLayoutManager(manager);
        gridView.setAdapter(new GridAdapter(number, width, height));


        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((FrameLayout) object);
        unbindDrawables((View) object);
        System.gc();
    }

    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }

    class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

        private int count;
        private int width;
        private int height;

        GridAdapter(int countIn, int widthIn, int heightIn) {
            this.count = countIn;
            this.width = widthIn;
            this.height = heightIn;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.grid_image, parent, false);
            // set the view's width, margins, padding's and layout parameters
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.titleImg.setImageBitmap(bitmapCache.get(count));
            holder.titleImg.getLayoutParams().height = height;
            holder.titleImg.getLayoutParams().width = width;
        }

        @Override
        public int getItemCount() {
            return count;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView titleImg;

            ViewHolder(View v) {
                super(v);
                this.titleImg = (ImageView) v.findViewById(R.id.grid_image);
            }
        }
    }

    @NonNull
    private GridLayoutManager getGridLayoutManager(final int number) {
        final GridLayoutManager manager = new GridLayoutManager(context, 2);
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (number == 1 || number == 2) {
                    return 2;
                } else if (number == 3) {
                    if (position == 0) {
                        return 2;
                    } else {
                        return 1;
                    }
                } else if (number == 5) {
                    if (position == 2) {
                        return 2;
                    } else {
                        return 1;
                    }
                } else if (number == 4 || number == 6) {
                    if (position == 0 || position == 3) {
                        return 2;
                    } else {
                        return 1;
                    }
                } else if (number == 7) {
                    if (position == 0 || position == 3 || position == 6) {
                        return 2;
                    } else {
                        return 1;
                    }
                } else if (number == 8) {
                    if (position == 2 || position == 5) {
                        return 2;
                    } else {
                        return 1;
                    }
                } else if (number == 9) {
                    if (position == 4) {
                        return 2;
                    } else {
                        return 1;
                    }
                } else if (number == 10) {
                    if (position == 2 || position == 7) {
                        return 2;
                    } else {
                        return 1;
                    }
                }
                return 1;
            }
        });
        return manager;
    }

    private int getActionBarHeight() {
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources()
                    .getDisplayMetrics());
        }
        return actionBarHeight;
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
