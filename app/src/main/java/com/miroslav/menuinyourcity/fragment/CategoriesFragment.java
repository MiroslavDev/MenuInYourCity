package com.miroslav.menuinyourcity.fragment;

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
import com.miroslav.menuinyourcity.view.GridViewOnFullScreen;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by apple on 4/5/16.
 */
public class CategoriesFragment extends com.miroslav.menuinyourcity.fragment.BaseFragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener{
    private static final String TAG = "CategoriesFragment";

    private GridViewOnFullScreen gridLayout;
    private GetCategoriesRequest request;
    private SliderLayout topSlider;
    private List<CategorieModel> categorieModelList;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((MainActivity)getActivity()).setVisibleSpinnerInActBar();
        ((MainActivity) getActivity()).setTitleActBar(Model.getInstance().currentCity);

        setupUI(view);
        createTestData();
    }

    private void setupUI(View view) {
        gridLayout = (GridViewOnFullScreen) view.findViewById(R.id.frg_categories_grid_layout);
        gridLayout.setAdapter(new MainCategoriesAdapter(getContext(), new ArrayList<CategorieModel>()));
        gridLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long parentId = ((CategorieModel) (gridLayout.getAdapter()).getItem(position)).getId();
                String nameSubcategory = ((CategorieModel) (gridLayout.getAdapter()).getItem(position)).getName();
                Log.d("parentId = ", parentId + "");
                ((MainActivity) getActivity()).replaceFragment(HostSubcategoriesFragment.newInstance(parentId, nameSubcategory));

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

            }
        });

    }

    private void createTestData() {
        HashMap<String,Integer> file_maps = new HashMap<>();
        file_maps.put("hannibal",R.drawable.hannibal);
        file_maps.put("Big Bang Theory",R.drawable.bigbang);
        file_maps.put("House of Cards",R.drawable.house);
        file_maps.put("Game of Thrones", R.drawable.game_of_thrones);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            topSlider.addSlider(textSliderView);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frg_categories, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        if(categorieModelList == null) {
            categoriesRequest();
        } else {
            updaateAdapterData(categorieModelList);
        }
    }

    private void categoriesRequest() {
        request = new GetCategoriesRequest();
        spiceManager.execute(request, request.getResourceUri(), request.getCacheExpiryDuration(), new RequestListener<BaseCategoriesModel>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {

            }

            @Override
            public void onRequestSuccess(BaseCategoriesModel baseCategoriesModel) {
                if (!baseCategoriesModel.getError()) {
                    updaateAdapterData(baseCategoriesModel.getCategorieList());
                } else {
                    Toast.makeText(getContext(), baseCategoriesModel.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updaateAdapterData(List<CategorieModel> data) {
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

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }



}
