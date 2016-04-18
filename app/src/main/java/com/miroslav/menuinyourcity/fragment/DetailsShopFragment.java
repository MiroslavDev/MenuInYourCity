package com.miroslav.menuinyourcity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.Utils;
import com.miroslav.menuinyourcity.adapter.ShopFeedbackAdapter;
import com.miroslav.menuinyourcity.request.GetShops.BaseShopModel;
import com.miroslav.menuinyourcity.request.GetShops.GetShopRequest;
import com.miroslav.menuinyourcity.request.GetShops.GetShopsModel;
import com.miroslav.menuinyourcity.request.GetShops.ShopsPhotosModel;
import com.miroslav.menuinyourcity.request.GetShops.ShopsReviewsModel;
import com.miroslav.menuinyourcity.request.URLHelper;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;

/**
 * Created by apple on 4/12/16.
 */
public class DetailsShopFragment extends BaseFragment implements AdapterView.OnItemClickListener{

    public  static final String PARENT_ID = "parent_id";
    public  static final String CATEGORY_NAME_KEY = "category_name_key";

    private String categoryName;

    private Long parentId;
    private ListView listView;
    private GetShopsModel data;
    private SliderLayout imageSlaider;
    private TextView title;
    private TextView category;
    private TextView shopURL;
    private TextView shopAddress;
    private TextView shopTimeWork;
    private TextView description;
    private TextView rating;


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
        if(data == null) {
            categoriesRequest();
        } else {
            updaateAdapterData(data);
        }
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
            }

            @Override
            public void onRequestSuccess(BaseShopModel data) {
                if (!data.getError()) {
                    updaateAdapterData(data.getShop());
                } else {
                    Toast.makeText(getContext(), data.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(GetShopsModel data) {
        this.data = data;
        ShopFeedbackAdapter adapter = (ShopFeedbackAdapter) listView.getAdapter();
        adapter.clear();
        adapter.addAll(data.getReviews());
        adapter.notifyDataSetChanged();
        Utils.setListViewHeightBasedOnChildren(listView);

        title.setText(data.getTitle());
        category.setText(categoryName);
        shopURL.setText(data.getPhone());
        shopAddress.setText(data.getStreet());
        shopTimeWork.setText(data.getTime());
        description.setText(data.getDescription());
        rating.setText(getContext().getString(R.string.rating) + " " + data.getRating() + "/10");

        imageSlaider.removeAllSliders();
        for(final ShopsPhotosModel promsModel : data.getPhotos()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .image(URLHelper.imageDomain + promsModel.getImage())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
//                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
//                        @Override
//                        public void onSliderClick(BaseSliderView slider) {
//                            if(promsModel.getUrl() != null && !promsModel.getUrl().isEmpty()) {
//                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(promsModel.getUrl()));
//                                startActivity(browserIntent);
//                            }
//                        }
//                    })
                    ;

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString(promsModel.getImage(), promsModel.getImage());

            imageSlaider.addSlider(textSliderView);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        ShopFeedbackAdapter adapter = (ShopFeedbackAdapter) listView.getAdapter();
//        BaseFragment fr = ShopListFragment.newInstance(adapter.getItem(position).getId(), adapter.getItem(position).getName());
//        ((MainActivity) getActivity()).replaceFragment(fr);
    }

    private void setupUI(View view) {
        listView = (ListView) view.findViewById(R.id.frg_details_shop_list_view);
        listView.setAdapter(new ShopFeedbackAdapter(getContext(), new ArrayList<ShopsReviewsModel>()));
        listView.setOnItemClickListener(this);

        imageSlaider = (SliderLayout) view.findViewById(R.id.frg_details_shop_img);
        rating = (TextView) view.findViewById(R.id.frg_details_shop_review_rating_text);
        title = (TextView) view.findViewById(R.id.frg_details_shop_title);
        category = (TextView) view.findViewById(R.id.frg_details_shop_category_name);
        shopURL = (TextView) view.findViewById(R.id.frg_details_shop_layout_text_url);
        shopAddress = (TextView) view.findViewById(R.id.frg_details_shop_layout_text_address);
        shopTimeWork = (TextView) view.findViewById(R.id.frg_details_shop_layout_text_open_time);
        description = (TextView) view.findViewById(R.id.frg_details_shop_description);
    }
}
