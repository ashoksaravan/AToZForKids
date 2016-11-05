package com.ashoksm.atozforkids.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
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
    private Context context;
    private List<ItemsDTO> items;
    private SparseArray<Bitmap> bitmapCache;

    public GridPagerAdapter(Context contextIn, List<ItemsDTO> itemsIn) {
        this.context = contextIn;
        this.items = itemsIn;
        mLayoutInflater =
                (LayoutInflater) contextIn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        bitmapCache = new SparseArray<>();
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
        final int number = position + 1;

        //set name
        final TextView name = (TextView) itemView.findViewById(R.id.name);
        name.setText(items.get(position).getItemName());

        int size;
        if (context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT && !isLargeScreen()) {
            size = 2000;
            if (number == 1) {
                size = 700;
            } else if (number == 2) {
                size = 1400;
            }
        } else {
            size = 1000;
            if (number == 1) {
                size = 300;
            } else if (number == 2) {
                size = 600;
            }
        }
        Bitmap imageBitmap = bitmapCache.get(number);
        if (imageBitmap == null) {
            imageBitmap = DecodeSampledBitmapFromResource
                    .execute(context.getResources(), items.get(number - 1)
                            .getImageResource(), size / number, size / number);
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
        } else {
            List<Palette.Swatch> swatches = palette.getSwatches();
            if (swatches.size() > 0) {
                Palette.Swatch swatch = swatches.get(0);
                name.setBackgroundColor(swatch.getRgb());
                name.setTextColor(swatch.getTitleTextColor());
                items.get(position).setVibrantColor(swatch.getRgb());
            }
        }

        //create image grid
        final RecyclerView gridView = (RecyclerView) itemView.findViewById(R.id.gridView);
        gridView.setHasFixedSize(true);
        final GridLayoutManager manager = getGridLayoutManager(number);
        gridView.setLayoutManager(manager);
        gridView.setAdapter(new GridAdapter(number, size));


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
        private int size;

        GridAdapter(int countIn, int sizeIn) {
            this.count = countIn;
            this.size = sizeIn;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v =
                    LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.grid_image, parent, false);
            // set the view's size, margins, padding's and layout parameters
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.titleImg.setImageBitmap(bitmapCache.get(count));
            holder.titleImg.getLayoutParams().height = size / count;
            holder.titleImg.getLayoutParams().width = size / count;
            holder.titleImg.setScaleType(ImageView.ScaleType.FIT_XY);
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

    private boolean isLargeScreen() {
        return (context.getResources().getConfiguration().screenLayout & Configuration
                .SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE ||
                context.getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_LANDSCAPE;
    }
}
