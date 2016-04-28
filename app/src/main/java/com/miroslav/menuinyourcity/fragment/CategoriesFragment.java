package com.miroslav.menuinyourcity.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.miroslav.menuinyourcity.MainActivity;
import com.miroslav.menuinyourcity.Model;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.adapter.MainCategoriesAdapter;
import com.miroslav.menuinyourcity.request.Categories.BaseCategoriesModel;
import com.miroslav.menuinyourcity.request.Categories.CategorieModel;
import com.miroslav.menuinyourcity.request.Categories.GetCategoriesRequest;
import com.miroslav.menuinyourcity.request.ChildrenCategories.BaseChildrenCategoriesModel;
import com.miroslav.menuinyourcity.request.ChildrenCategories.GetChildrenCategoriesModel;
import com.miroslav.menuinyourcity.request.ChildrenCategories.GetChildrenCategoriesRequest;
import com.miroslav.menuinyourcity.request.Proms.BasePromsModel;
import com.miroslav.menuinyourcity.request.Proms.PromsModel;
import com.miroslav.menuinyourcity.request.Proms.PromsRequest;
import com.miroslav.menuinyourcity.request.URLHelper;
import com.miroslav.menuinyourcity.view.GridViewOnFullScreen;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 4/5/16.
 */
public class CategoriesFragment extends com.miroslav.menuinyourcity.fragment.BaseFragment implements ViewPagerEx.OnPageChangeListener{
    private static final String TAG = "CategoriesFragment";

    private GridViewOnFullScreen gridLayout;
    private SliderLayout topSlider;
    private List<GetChildrenCategoriesModel> categorieModelList;
    private List<PromsModel> promsModelList;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).setVisibleSpinnerInActBar();
        ((MainActivity) getActivity()).setTitleActBar(Model.getInstance().currentCity);

        setupUI(view);
    }

    private void setupUI(View view) {
        gridLayout = (GridViewOnFullScreen) view.findViewById(R.id.frg_categories_grid_layout);
        gridLayout.setAdapter(new MainCategoriesAdapter(getContext(), new ArrayList<GetChildrenCategoriesModel>()));
        gridLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long parentId = ((GetChildrenCategoriesModel) (gridLayout.getAdapter()).getItem(position)).getId();
                String nameSubcategory = ((GetChildrenCategoriesModel) (gridLayout.getAdapter()).getItem(position)).getName();
                Boolean isFollow = ((GetChildrenCategoriesModel) (gridLayout.getAdapter()).getItem(position)).getUserFollow();
                Log.d("parentId = ", parentId + "");
                ((MainActivity) getActivity()).replaceFragment(HostSubcategoriesFragment.newInstance(parentId, nameSubcategory, isFollow));

            }
        });
        topSlider = (SliderLayout) view.findViewById(R.id.frg_categories_top_slider);


        view.findViewById(R.id.frg_categories_btn_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(NewsListFragment.newInstance());
            }
        });
        view.findViewById(R.id.frg_categories_btn_liked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).replaceFragment(LikedListFragment.newInstance());
            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_categories, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(categorieModelList == null) {
            categoriesRequest();
        } else {
            updaateAdapterData(categorieModelList);
        }

        if(promsModelList == null) {
            promsRequest();
        } else {
            updatePromos(promsModelList);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        categoriesRequest();
    }

    private void promsRequest() {
        PromsRequest request = new PromsRequest(Model.getInstance().currentCityId);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BasePromsModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BasePromsModel basePromsModel) {
                if (!basePromsModel.getError()) {
                    updatePromos(basePromsModel.getPromList());
                } else {
                    Toast.makeText(getContext(), basePromsModel.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updatePromos(List<PromsModel> promsModelList) {
        this.promsModelList = promsModelList;

        topSlider.removeAllSliders();
        for(final PromsModel promsModel : promsModelList){
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .image(URLHelper.imageDomain + promsModel.getImage())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            if(promsModel.getUrl() != null && !promsModel.getUrl().isEmpty()) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(promsModel.getUrl()));
                                startActivity(browserIntent);
                            }
                        }
                    });

            //textSliderView.bundle(new Bundle());
            //textSliderView.getBundle().putString(promsModel.getImage(), promsModel.getImage());

            topSlider.addSlider(textSliderView);
        }

    }

    private void categoriesRequest() {
        GetChildrenCategoriesRequest request = new GetChildrenCategoriesRequest(0l);
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseChildrenCategoriesModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseChildrenCategoriesModel baseCategoriesModel) {
                if (!baseCategoriesModel.getError()) {
                    updaateAdapterData(baseCategoriesModel.getCategorieList());
                } else {
                    Toast.makeText(getContext(), baseCategoriesModel.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<GetChildrenCategoriesModel> data) {
        categorieModelList = data;
        MainCategoriesAdapter adapter = (MainCategoriesAdapter) gridLayout.getAdapter();
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
