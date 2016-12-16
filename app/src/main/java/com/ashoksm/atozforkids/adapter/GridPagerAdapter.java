package com.ashoksm.atozforkids.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
    private Activity context;
    private List<ItemsDTO> items;
    private SparseArray<Bitmap> bitmapCache;
    private double screenSize;

    public GridPagerAdapter(Activity contextIn, List<ItemsDTO> itemsIn) {
        this.context = contextIn;
        this.items = itemsIn;
        this.mLayoutInflater =
                (LayoutInflater) contextIn.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bitmapCache = new SparseArray<>();
        this.screenSize = getScreenSize();
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
        final int number = Integer.parseInt(items.get(position).getItemNumber());

        //set name
        final TextView name = (TextView) itemView.findViewById(R.id.name);
        name.setText(items.get(position).getItemName());

        int size = getImageSize(number);
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
        gridView.setAdapter(new GridAdapter(number, size));


        container.addView(itemView);
        return itemView;
    }

    private int getImageSize(int number) {
        int size;
        if (context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT && screenSize <= 4.0d) {
            size = 750;
            if (number == 1) {
                size = 400;
            } else if (number == 2) {
                size = 600;
            }
        } else if (context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT && screenSize > 4.0d && screenSize <= 5.0d) {
            size = 1500;
            if (number == 1) {
                size = 700;
            } else if (number == 2) {
                size = 1200;
            } else if (number >= 7) {
                size = 1800;
            }
        } else if (context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_LANDSCAPE && screenSize <= 4.0d) {
            size = 400;
            if (number == 1) {
                size = 250;
            } else if (number == 2) {
                size = 300;
            }
        } else if (context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_LANDSCAPE && screenSize > 4.0d && screenSize <= 5.0d) {
            size = 900;
            if (number == 1) {
                size = 600;
            }
        } else if (context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT && !isLargeScreen()) {
            size = 2000;
            if (number == 1) {
                size = 700;
            } else if (number == 2) {
                size = 1400;
            }
        } else if (context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_LANDSCAPE && !isLargeScreen()) {
            size = 1000;
            if (isLargeScreen()) {
                size = 750;
            }
            if (number == 1) {
                size = 300;
            } else if (number == 2) {
                size = 600;
            }
        } else if(isXLargeScreenAndPortrait()) {
            size = 1500;
            if (number == 1) {
                size = 600;
            } else if (number == 2) {
                size = 1000;
            }
        } else if(isXLargeScreenAndLandscape()) {
            size = 900;
            if (number == 1) {
                size = 500;
            } else if (number == 2) {
                size = 600;
            }
        } else if (context.getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT) {
            size = 1800;
            if (number == 1) {
                size = 800;
            } else if (number == 2) {
                size = 1400;
            }
        } else {
            size = 1200;
            if (number == 1) {
                size = 500;
            } else if (number == 2) {
                size = 800;
            }
        }
        return size;
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
            View v = LayoutInflater.from(parent.getContext())
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

    private boolean isXLargeScreenAndPortrait() {
        return (context.getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE &&
                context.getApplicationContext().getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_PORTRAIT;
    }

    private boolean isXLargeScreenAndLandscape() {
        return (context.getApplicationContext().getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE &&
                context.getApplicationContext().getResources().getConfiguration().orientation ==
                        Configuration.ORIENTATION_LANDSCAPE;
    }

    private double getScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int dens = dm.densityDpi;
        double wi = (double) width / (double) dens;
        double hi = (double) height / (double) dens;
        double x = Math.pow(wi, 2);
        double y = Math.pow(hi, 2);
        return Math.sqrt(x + y);
    }
}
