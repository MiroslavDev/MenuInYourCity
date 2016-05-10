package com.miroslav.menuinyourcity.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.DetailImagePagerAdapter;
import com.miroslav.menuinyourcity.adapter.ShopFeedbackAdapter;
import com.miroslav.menuinyourcity.request.GetShops.BaseShopModel;
import com.miroslav.menuinyourcity.request.GetShops.GetShopRequest;
import com.miroslav.menuinyourcity.request.GetShops.ShopsModel;
import com.miroslav.menuinyourcity.request.GetShops.ShopsPhotosModel;
import com.miroslav.menuinyourcity.request.GetShops.ShopsReviewsModel;
import com.miroslav.menuinyourcity.request.URLHelper;
import com.miroslav.menuinyourcity.view.HackyViewPager;
import com.miroslav.menuinyourcity.view.ListViewOnFullScreen;
import com.miroslav.menuinyourcity.view.MySlider.Indicators.PagerIndicator;
import com.miroslav.menuinyourcity.view.MySlider.SliderTypes.BaseSliderView;
import com.miroslav.menuinyourcity.view.MySlider.SliderTypes.TextSliderView;
import com.miroslav.menuinyourcity.view.MySlider.Tricks.InfinitePagerAdapter;
import com.miroslav.menuinyourcity.view.MySlider.Tricks.ViewPagerEx;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by apple on 4/12/16.
 */
public class DetailsShopFragment extends BaseFragment implements AdapterView.OnItemClickListener, PhotoViewAttacher.OnViewTapListener{

    public static final String PARENT_ID = "parent_id";
    public static final String CATEGORY_NAME_KEY = "category_name_key";

    private String categoryName;

    private Long parentId;
    private ListViewOnFullScreen listView;
    private ShopFeedbackAdapter feedbackAdapter;
    private ShopsModel data;
    private HackyViewPager hackyViewPager;
    private TextView title;
    private TextView category;
    private TextView shopURL;
    private TextView shopAddress;
    private TextView shopTimeWork;
    private TextView description;
    private TextView rating;
    private ImageView likedImage;
    private ScrollView rootScrollView;
    private View addReviewButton;
    private ImageView phoneCallBtn;
    private View moreInformationBtn;
    private ProgressBar progressBar;
    private View.OnClickListener listener;

    private Double latitude = 0.0d;
    private Double longitude = 0.0d;
    private String address;
    private String shopId;
    private Boolean isBlockedScrollView = false;
    private DetailImagePagerAdapter adapterPhotos;
    private Boolean isFullInformation = false;

    public static DetailsShopFragment newInstance(Long id, String categoryName) {
        DetailsShopFragment fr = new DetailsShopFragment();
        Bundle arg = new Bundle();
        arg.putLong(PARENT_ID, id);
        arg.putString(CATEGORY_NAME_KEY, categoryName);
        Log.d("parentId = ", id+"");
        fr.setArguments(arg);
        return fr;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        parentId = getArguments().getLong(PARENT_ID);
        categoryName = getArguments().getString(CATEGORY_NAME_KEY);

        Log.d("parentId = ", parentId+"");
        setupUI(view);
        setupAB();
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
            ((MainActivity) getActivity()).setOnButtonBackListener(null);
        } catch (Exception e) {}
    }

    private void setupAB() {
        ((MainActivity) getActivity()).setVisibleButtonBackInActBar();
        ((MainActivity) getActivity()).setTitleActBar(categoryName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                    rootScrollView.setVisibility(View.VISIBLE);
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

        rootScrollView.post(new Runnable() {
            @Override
            public void run() {
                rootScrollView.setScrollY(0);
            }
        });

        rootScrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return isBlockedScrollView;
            }
        });

        title.setText(data.getTitle());
        category.setText(categoryName);
        shopURL.setText(data.getPhone());
        shopAddress.setText(data.getStreet());
        shopTimeWork.setText(data.getTime());
        description.setText(data.getDescription());
        String ratingS = String.format( Locale.US, "%.2f", data.getRating());
        rating.setText(getContext().getString(R.string.rating) + " " + ratingS + "/10");
        address = data.getStreet();
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
    }

    public void setAllCenterCrop(boolean isCenterCrop) {
        adapterPhotos.setFullInformation(isCenterCrop);
        adapterPhotos.notifyDataSetChanged();
    }

    private void onImageClick() {
        if(!isBlockedScrollView) {
            isBlockedScrollView = true;
            hackyViewPager.getLayoutParams().height = getView().getHeight() + ((MainActivity) getActivity()).getActBarHeight();
            addReviewButton.setVisibility(View.GONE);
            ((MainActivity) getActivity()).hideActBar();
            setAllCenterCrop(true);
        } else {
            addReviewButton.setVisibility(View.VISIBLE);
            ((MainActivity) getActivity()).showActBar();
            isBlockedScrollView = false;
            hackyViewPager.getLayoutParams().height = (int) getContext().getResources().getDimension(R.dimen.height_present_images);
            setAllCenterCrop(false);
        }
    }

    private void setupListeners() {
        likedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLikedImageClick();
            }
        });

        addReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(AddReviewFragment.newInstance(shopId));
            }
        });

        phoneCallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + shopURL.getText()));
                startActivity(intent);
            }
        });

        shopAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(MapFragment.newInstance(latitude, longitude, address));
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
        rootScrollView = (ScrollView) view.findViewById(R.id.frg_details_shop_root_sroll_view);
        rootScrollView.setVisibility(View.GONE);

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        addReviewButton = LayoutInflater.from(getContext()).inflate(R.layout.add_review_button, null);

        listView = (ListViewOnFullScreen) view.findViewById(R.id.frg_details_shop_list_view);
        if(feedbackAdapter == null)
            feedbackAdapter = new ShopFeedbackAdapter(getContext(), new ArrayList<ShopsReviewsModel>(), view.getHandler());
        listView.setAdapter(feedbackAdapter);
        listView.setOnItemClickListener(this);
        listView.addFooterView(addReviewButton);

        hackyViewPager = (HackyViewPager) view.findViewById(R.id.frg_details_shop_img);
        adapterPhotos = new DetailImagePagerAdapter(getContext(), new ArrayList<ShopsPhotosModel>(), this);
        PagerAdapter wrappedAdapter = new InfinitePagerAdapter(adapterPhotos);
        hackyViewPager.setAdapter(wrappedAdapter);

        PagerIndicator pagerIndicator = (PagerIndicator) view.findViewById(R.id.default_center_bottom_indicator);
        pagerIndicator.setViewPager(hackyViewPager);

        moreInformationBtn = view.findViewById(R.id.frg_details_shop_description_more);
        rating = (TextView) view.findViewById(R.id.frg_details_shop_review_rating_text);
        title = (TextView) view.findViewById(R.id.frg_details_shop_title);
        category = (TextView) view.findViewById(R.id.frg_details_shop_category_name);
        shopURL = (TextView) view.findViewById(R.id.frg_details_shop_layout_text_url);
        shopAddress = (TextView) view.findViewById(R.id.frg_details_shop_layout_text_address);
        shopTimeWork = (TextView) view.findViewById(R.id.frg_details_shop_layout_text_open_time);
        description = (TextView) view.findViewById(R.id.frg_details_shop_description);
        likedImage = (ImageView) view.findViewById(R.id.frg_details_shop_ic_star);
        phoneCallBtn = (ImageView) view.findViewById(R.id.frg_details_shop_ic_phone);

        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isBlockedScrollView) {
                    addReviewButton.setVisibility(View.VISIBLE);
                    ((MainActivity) getActivity()).showActBar();
                    isBlockedScrollView = false;
                    hackyViewPager.getLayoutParams().height = (int) getContext().getResources().getDimension(R.dimen.height_present_images);
                    setAllCenterCrop(false);
                } else {
                    ((MainActivity)getActivity()).popBackStackSupportFragmentManager();
                }
            }
        };
    }

    public void onLikedImageClick() {
        SQLiteDatabase db = MainActivity.rootAcvitityInstance.getDbHelper().getWritableDatabase();
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
            cv.put("city_id", Model.getInstance().currentCityId);

            db.insert("likedList", null, cv);
        } else {
            likedImage.setImageResource(R.drawable.ic_star_inactive);
            db.execSQL("DELETE FROM likedList WHERE shop_id=" + data.getId() + ";");
        }

        db.close();
    }

    private boolean isInLikedList() {
        SQLiteDatabase db = MainActivity.rootAcvitityInstance.getDbHelper().getWritableDatabase();
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
