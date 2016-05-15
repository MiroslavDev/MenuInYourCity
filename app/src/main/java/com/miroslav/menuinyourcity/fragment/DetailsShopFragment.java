package com.miroslav.menuinyourcity.fragment;

import android.animation.ObjectAnimator;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.DetailImagePagerAdapter;
import com.miroslav.menuinyourcity.adapter.ShopFeedbackAdapter;
import com.miroslav.menuinyourcity.request.GetShops.BaseShopModel;
import com.miroslav.menuinyourcity.request.GetShops.GetShopRequest;
import com.miroslav.menuinyourcity.request.GetShops.ShopsModel;
import com.miroslav.menuinyourcity.request.GetShops.ShopsPhotosModel;
import com.miroslav.menuinyourcity.request.GetShops.ShopsReviewsModel;
import com.miroslav.menuinyourcity.view.HackyViewPager;
import com.miroslav.menuinyourcity.view.MyListView;
import com.miroslav.menuinyourcity.view.MySlider.Indicators.PagerIndicator;
import com.miroslav.menuinyourcity.view.MySlider.Tricks.InfinitePagerAdapter;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.Locale;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by apple on 4/12/16.
 */
public class DetailsShopFragment extends BaseFragment implements AdapterView.OnItemClickListener, PhotoViewAttacher.OnViewTapListener{

    public static final String SHOP_ID = "shop_id";
    public static final String CATEGORY_NAME_KEY = "category_name_key";

    private String categoryName;

    private Long parentId;
    private View dataLayout;
    private View rootHeaderLayout;
    private MyListView listView;
    private ShopFeedbackAdapter feedbackAdapter;
    private ShopsModel data;
    private HackyViewPager hackyViewPager;
    private TextView category;
    private TextView shopURL;
    private TextView shopAddress;
    private TextView shopTimeWork;
    private TextView description;
    private TextView rating;
    private ImageView likedImage;
    private ImageView phoneCallBtn;
    private View moreInformationBtn;
    private ProgressBar progressBar;
    private View.OnClickListener listener;
    private int statusBarHeight;

    private View addReviewButtonFooter;
    private View addReviewButtonLayout;
    private float startY = 0;
    private int headerHeight = 0;
    private Boolean isFirstVisible = false;

    private Double latitude = 0.0d;
    private Double longitude = 0.0d;
    private String address;
    private String phoneNumber;
    private String shopId;
    private Boolean isBlockedScrollView = false;
    private DetailImagePagerAdapter adapterPhotos;
    private Boolean isFullInformation = false;

    public static DetailsShopFragment newInstance(Long id, String categoryName) {
        DetailsShopFragment fr = new DetailsShopFragment();
        Bundle arg = new Bundle();
        arg.putLong(SHOP_ID, id);
        arg.putString(CATEGORY_NAME_KEY, categoryName);
        Log.d("parentId = ", id+"");
        fr.setArguments(arg);
        return fr;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentId = getArguments().getLong(SHOP_ID);
        categoryName = getArguments().getString(CATEGORY_NAME_KEY);

        Log.d("parentId = ", parentId+"");
        setupUI(view);

        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
    }

    @Override
    public void onResume() {
        super.onResume();
        categoriesRequest();
        ((MainActivity)getActivity()).setOnButtonBackListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            if(getActivity() != null)
                ((MainActivity) getActivity()).setOnButtonBackListener(null);
        } catch (Exception e) {}
    }

    private void setupAB(String shopName) {
        ((MainActivity) getActivity()).setTitleActBar(shopName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootHeaderLayout = LayoutInflater.from(getContext()).inflate(R.layout.header_frg_details_shop, null);
        return inflater.inflate(R.layout.frg_details_shop, container, false);
    }

    private void categoriesRequest() {
        Log.d("parentId = ", parentId+"");
        GetShopRequest request = new GetShopRequest(parentId);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseShopModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRequestSuccess(BaseShopModel data) {
                progressBar.setVisibility(View.GONE);
                if (!data.getError()) {
                    updaateAdapterData(data.getShop());
                    dataLayout.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(ShopsModel data) {
        this.data = data;
        feedbackAdapter.clear();
        feedbackAdapter.addAll(data.getReviews());
        feedbackAdapter.notifyDataSetChanged();

        setupAB(data.getTitle());
        category.setText(categoryName);
        shopURL.setText(data.getUrl());
        shopAddress.setText(data.getStreet());
        shopTimeWork.setText(data.getTime());
        description.setText(data.getDescription());
        String ratingS = String.format( Locale.US, "%.2f", data.getRating());
        rating.setText(getContext().getString(R.string.rating) + " " + ratingS + "/10");
        address = data.getStreet();
        phoneNumber = data.getPhone();
        try {
            latitude = Double.parseDouble(data.getLatitude());
        } catch (NumberFormatException e) {
            latitude = 0.0d;
        }
        try {
            longitude = Double.parseDouble(data.getLongitude());
        } catch (NumberFormatException e) {
            longitude = 0.0d;
    }
        likedImage.setImageResource(isInLikedList() ? R.drawable.ic_star_enable : R.drawable.ic_star_inactive);
        shopId = data.getId().toString();

        description.post(new Runnable() {
            @Override
            public void run() {
                if(description.getLineCount() <= 4 )
                    moreInformationBtn.setVisibility(View.GONE);
                else
                    description.setMaxLines(4);
            }
        });

        adapterPhotos.addAll(data.getPhotos());
        adapterPhotos.notifyDataSetChanged();

        setupListeners();

        addReviewButtonLayout.post(new Runnable() {
            @Override
            public void run() {
                startY = addReviewButtonLayout.getY();
            }
        });
    }

    public void setAllCenterCrop(boolean isCenterCrop) {
        adapterPhotos.setFullInformation(isCenterCrop);
        adapterPhotos.notifyDataSetChanged();
    }

    private void onImageClick() {
        if(!isBlockedScrollView) {
            isBlockedScrollView = true;
            hackyViewPager.getLayoutParams().height = getView().getHeight() + ((MainActivity) getActivity()).getActBarHeight() + statusBarHeight;
            hackyViewPager.requestLayout();
            hackyViewPager.requestFocus();
            listView.removeFooterView(addReviewButtonFooter);
            addReviewButtonFooter.setVisibility(View.GONE);
            addReviewButtonLayout.setVisibility(View.GONE);
            likedImage.setVisibility(View.GONE);
            phoneCallBtn.setVisibility(View.GONE);
            category.setVisibility(View.GONE);
            ((MainActivity) getActivity()).hideActBar();
            setAllCenterCrop(true);

            if (Build.VERSION.SDK_INT < 16) {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            } else {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
            }
        } else {
            addReviewButtonFooter.setVisibility(View.VISIBLE);
            addReviewButtonLayout.setVisibility(View.VISIBLE);
            ((MainActivity) getActivity()).showActBar();
            isBlockedScrollView = false;
            //listView.setBlockedScrollView(isBlockedScrollView);
            //adapterPhotos.setBlockedScrollView(isBlockedScrollView);
            hackyViewPager.getLayoutParams().height = (int) getContext().getResources().getDimension(R.dimen.height_present_images);
            hackyViewPager.requestLayout();
            listView.addFooterView(addReviewButtonFooter);
            likedImage.setVisibility(View.VISIBLE);
            phoneCallBtn.setVisibility(View.VISIBLE);
            category.setVisibility(View.VISIBLE);
            setAllCenterCrop(false);

            if (Build.VERSION.SDK_INT < 16) {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            } else {
                getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        }
    }

    private void setupListeners() {
        likedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikedImageClick();
            }
        });

        addReviewButtonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(AddReviewFragment.newInstance(shopId));
            }
        });

        phoneCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
            }
        });

        shopAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(MapFragment.newInstance(latitude, longitude, address));
            }
        });


        shopURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(shopURL.getText().toString()));
                try {
                    startActivity(browserIntent);
                } catch (Exception e) {}
            }
        });


        moreInformationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description.setMaxLines(Integer.MAX_VALUE);
                moreInformationBtn.setVisibility(View.GONE);
                isFullInformation = true;
            }
        });

        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFullInformation) {
                    description.setMaxLines(4);
                    moreInformationBtn.setVisibility(View.VISIBLE);
                    isFullInformation = false;
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    private void setupUI(View view) {
        dataLayout =  view.findViewById(R.id.frg_details_shop_data_layout);
        dataLayout.setVisibility(View.GONE);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        addReviewButtonFooter = LayoutInflater.from(getContext()).inflate(R.layout.add_review_button, null);

        addReviewButtonLayout = view.findViewById(R.id.frg_details_shop_give_feedback);
        listView = (MyListView) view.findViewById(R.id.frg_details_shop_list_view);
        if(feedbackAdapter == null)
            feedbackAdapter = new ShopFeedbackAdapter(getContext(), new ArrayList<ShopsReviewsModel>(), view.getHandler());
        listView.addHeaderView(rootHeaderLayout);
        listView.addFooterView(addReviewButtonFooter);
        listView.setAdapter(feedbackAdapter);
        listView.setOnItemClickListener(this);

        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        hackyViewPager = (HackyViewPager) rootHeaderLayout.findViewById(R.id.frg_details_shop_img);
        adapterPhotos = new DetailImagePagerAdapter(getContext(), new ArrayList<ShopsPhotosModel>(), this, view.getMeasuredWidth(), view.getMeasuredHeight());
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(adapterPhotos);
        hackyViewPager.setAdapter(wrappedAdapter);

        PagerIndicator pagerIndicator = (PagerIndicator) rootHeaderLayout.findViewById(R.id.default_center_bottom_indicator);
        pagerIndicator.setViewPager(hackyViewPager);

        moreInformationBtn = rootHeaderLayout.findViewById(R.id.frg_details_shop_description_more);
        rating = (TextView) rootHeaderLayout.findViewById(R.id.frg_details_shop_review_rating_text);
        category = (TextView) rootHeaderLayout.findViewById(R.id.frg_details_shop_category_name);
        shopURL = (TextView) rootHeaderLayout.findViewById(R.id.frg_details_shop_layout_text_url);
        shopAddress = (TextView) rootHeaderLayout.findViewById(R.id.frg_details_shop_layout_text_address);
        shopTimeWork = (TextView) rootHeaderLayout.findViewById(R.id.frg_details_shop_layout_text_open_time);
        description = (TextView) rootHeaderLayout.findViewById(R.id.frg_details_shop_description);
        likedImage = (ImageView) rootHeaderLayout.findViewById(R.id.frg_details_shop_ic_star);
        phoneCallBtn = (ImageView) rootHeaderLayout.findViewById(R.id.frg_details_shop_ic_phone);

        Rect rectangle= new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        statusBarHeight= rectangle.top;

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBlockedScrollView) {
                    addReviewButtonFooter.setVisibility(View.VISIBLE);
                    addReviewButtonLayout.setVisibility(View.VISIBLE);
                    ((MainActivity) getActivity()).showActBar();
                    isBlockedScrollView = false;
                    listView.addFooterView(addReviewButtonFooter);
                    hackyViewPager.getLayoutParams().height = (int) getContext().getResources().getDimension(R.dimen.height_present_images);
                    hackyViewPager.requestLayout();
                    likedImage.setVisibility(View.VISIBLE);
                    phoneCallBtn.setVisibility(View.VISIBLE);
                    category.setVisibility(View.VISIBLE);
                    setAllCenterCrop(false);

                    if (Build.VERSION.SDK_INT < 16) {
                        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
                    } else {
                        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                } else {
                    ((MainActivity)getActivity()).popBackStackSupportFragmentManager();
                }
            }
        };

        addReviewButtonLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        headerHeight = addReviewButtonLayout.getMeasuredHeight();

        listView.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if(!isFirstVisible) {
                    if (scrollState == 0)
                        ObjectAnimator.ofFloat(addReviewButtonLayout, "y", startY).setDuration(200).start();
                    else
                        ObjectAnimator.ofFloat(addReviewButtonLayout, "y", startY + headerHeight).setDuration(200).start();
                } else {
                    ObjectAnimator.ofFloat(addReviewButtonLayout, "y", startY).setDuration(200).start();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isFirstVisible = (firstVisibleItem + visibleItemCount == totalItemCount);

                if(isBlockedScrollView) {
                    listView.getChildAt(0).setTop(0);
                    rootHeaderLayout.setY(0);
                }
            }
        });
    }

    public void onLikedImageClick() {
        SQLiteDatabase db = ((MainActivity) getActivity()).getDbHelper().getWritableDatabase();
        if(!isInLikedList()) {
            likedImage.setImageResource(R.drawable.ic_star_enable);
            ContentValues cv = new ContentValues();

            cv.put("shop_id", data.getId());
            cv.put("category_id", data.getCategoryId());
            cv.put("city_id", data.getCityId());
            cv.put("title", data.getTitle());
            cv.put("description", data.getDescription());
            cv.put("time", data.getTime());
            cv.put("lat", data.getLatitude());
            cv.put("lon", data.getLongitude());
            cv.put("street", data.getStreet());
            cv.put("phone", data.getPhone());
            cv.put("date_start", data.getDataStart());
            cv.put("date_stop", data.getDataStop());
            cv.put("updated_at", data.getUpdatedData());
            cv.put("rating", data.getRating());
            cv.put("imageURL", data.getPhotos().size() > 0 ? data.getPhotos().get(0).getImage() : "");
            cv.put("category_name", categoryName);

            db.insert("likedList", null, cv);
        } else {
            likedImage.setImageResource(R.drawable.ic_star_inactive);
            db.execSQL("DELETE FROM likedList WHERE shop_id=" + data.getId() + ";");
        }

        db.close();
    }

    private boolean isInLikedList() {
        SQLiteDatabase db = ((MainActivity) getActivity()).getDbHelper().getWritableDatabase();
        Cursor c = db.query("likedList", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("shop_id");

            do {
                if(c.getLong(idColIndex) == data.getId()) {
                    c.close();
                    return true;
                }
            } while (c.moveToNext());
        }
        c.close();
        return false;
    }

    @Override
    public void onViewTap(View view, float v, float v1) {
        onImageClick();
    }
}
