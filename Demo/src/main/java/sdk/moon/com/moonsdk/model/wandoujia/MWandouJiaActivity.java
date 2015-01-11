package sdk.moon.com.moonsdk.model.wandoujia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.imageaware.ImageAware;

import java.util.ArrayList;
import java.util.List;

import sdk.moon.com.moonsdk.R;
import sdk.moon.com.moonsdk.abst.MBaseActivity;
import sdk.moon.com.moonsdk.adapter.MWandAdapter;
import sdk.moon.com.moonsdk.util.MComUtil;

/**
 * Created by gyzhong on 15/1/10.
 */
public class MWandouJiaActivity extends MBaseActivity implements AbsListView.OnScrollListener {
    private LinearLayout customBar;
//    private RelativeLayout wandoujiaHeader;
    private LinearLayout homeBtn, actionBtns, mstBtn,wandoujiaHeader,headerTop;
    private boolean isDo = false;
    private float homeBtnWidth, btnsHeight, msgBtnWidth, headerHeight,wandoujiaHeader1,wandoujiaheader2;
    private ListView listView;
    private MWandAdapter adapter;
    private List<String> data;
    private View header,wdjHeader1, wdjHeader2;
    private View emptyHeader;
    private ImageView icNormalApk,icNormalGame,icNormalVideo,icNormalMusic,icNormalMore;
    private ImageView icColorApk,icColorGame,icColorVideo,icColorMusic,icColorMore;
    private float normalApkX,colorApkX,normalGameX,colorGameX,normalVideoX,
                colorVideoX,normalMusicX,colorMusicX,normalMoreX,colorMoreX,
                apkHeight,startTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wandoujia);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        data = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            data.add("钟光燕" + i);
        }
    }

    @Override
    public void initView() {
        actionBar.hide();
        emptyHeader = new View(this);
//        AbsListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, MComUtil.dp2px(this, 95));
//        emptyHeader.setLayoutParams(params);
        header = View.inflate(this, R.layout.wandoujia_header, null);
        customBar = findView(R.id.wandoujiaActionBar);
        homeBtn = findView(R.id.homeBtn);
        actionBtns = findView(R.id.actionBtns);
        mstBtn = findView(R.id.actionMsg);
        listView = findView(R.id.listViewWan);
        icNormalApk = findView(R.id.apk_btn);
        icNormalGame = findView(R.id.gameBtn) ;
        icNormalVideo = findView(R.id.videoBtn) ;
        icNormalMore = findView(R.id.moreBtn) ;
        icColorApk = findView(R.id.colorApk);
        icColorGame = findView(R.id.colorGame);
        icColorVideo = findView(R.id.colorVideo);
        icColorMusic = findView(R.id.colorMusic);
        icColorMore = findView(R.id.colorMore);
//        listView.addHeaderView(emptyHeader);
        listView.addHeaderView(header);
        adapter = new MWandAdapter(this, data);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(this);
        wandoujiaHeader = findView(R.id.activityHead);
        headerTop = (LinearLayout)wandoujiaHeader.findViewById(R.id.headerTop) ;
        adapter.notifyDataSetChanged();
        Log.v("zgy", "============getMeasuredWidth===========" + homeBtn.getMeasuredWidth());
        customBar.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                customBar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.v("zgy", "============getMeasuredWidth===========" + homeBtn.getMeasuredWidth());
                        if (!isDo) {
                            isDo = true;
                            measureView();
                            homeBtn.setVisibility(View.VISIBLE);
                            homeBtn.setTranslationX(-homeBtnWidth);
                            actionBtns.setTranslationY(btnsHeight);
                            mstBtn.setTranslationX(msgBtnWidth);
                            customBar.setAlpha(0);
                        }
                    }
                }, 200);
            }
        });
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        Log.v("zgy", "=====================firstVisibleItem " + firstVisibleItem +
                ",top=" + view.getTop());
        View viewChild = view.getChildAt(firstVisibleItem);
//        View viewChild = view.getChildCount() ;
        if (viewChild != null) {
            if (firstVisibleItem == 0) {
                exeAnim(viewChild.getTop());
            }else if(firstVisibleItem == 1){

            }
            Log.v("zgy", "=========viewChild============" + viewChild.getTop());
        }
    }

    private void measureView(){
        homeBtnWidth = homeBtn.getMeasuredWidth()+10;
        wandoujiaHeader1 = btnsHeight = actionBtns.getMeasuredHeight();
        wandoujiaheader2 = headerTop.getMeasuredHeight() ;
        msgBtnWidth = mstBtn.getMeasuredWidth();
        headerHeight = header.getMeasuredHeight();
        int positionXY[] = new int[2]  ;
        icNormalApk.getLocationOnScreen(positionXY);
        normalApkX = positionXY[0] - icNormalApk.getMeasuredWidth()/4;
        icNormalGame.getLocationOnScreen(positionXY);
        normalGameX = positionXY[0]- icNormalGame.getMeasuredWidth()/4 ;
        icNormalVideo.getLocationOnScreen(positionXY);
        normalVideoX = positionXY[0] - icNormalVideo.getMeasuredWidth()/4;
        icNormalMore.getLocationOnScreen(positionXY);
        normalMoreX = positionXY[0] - icNormalMore.getMeasuredWidth()/4;
        icColorApk.getLocationOnScreen(positionXY);
        colorApkX = positionXY[0] ;
        icColorGame.getLocationOnScreen(positionXY);
        colorGameX = positionXY[0] ;
        icColorVideo.getLocationOnScreen(positionXY);
        colorVideoX = positionXY[0] ;
        icColorMusic.getLocationOnScreen(positionXY);
        colorMusicX = positionXY[0] ;
        icColorMore.getLocationOnScreen(positionXY);
        colorMoreX = positionXY[0] ;
        apkHeight = icNormalApk.getMeasuredHeight() ;
        startTop = (btnsHeight-apkHeight)/2.0f ;
        Log.v("zgy","=========normalApkX=============="+normalApkX);
        Log.v("zgy","=========normalApkX=====x========="+colorApkX);

    }

    private void exeAnim(int top) {
        float tranY = top + wandoujiaHeader1 ;
        float showActionBar = top +wandoujiaheader2 ;
        if(tranY < 0){
            wandoujiaHeader.setTranslationY(tranY);
            tranY = Math.abs(tranY);
            if(tranY > wandoujiaheader2-wandoujiaHeader1)
                tranY = wandoujiaheader2-wandoujiaHeader1;
            float alpha = 1.0f * (tranY) / (wandoujiaheader2-wandoujiaHeader1);
            customBar.setAlpha(alpha);

            icAnim(tranY) ;
        }
        if(showActionBar<0){
            showActionBar = Math.abs(showActionBar);
            actionBarShow(showActionBar);

        }


    }

    private void actionBarShow(float value){

        float homeBtnTransX = -homeBtnWidth*(wandoujiaHeader1-value)/wandoujiaHeader1 ;
        float actionBtnsTranY = btnsHeight*(wandoujiaHeader1-value)/wandoujiaHeader1 ;
        float msgBtnTranX = msgBtnWidth*(wandoujiaHeader1-value)/wandoujiaHeader1 ;
        homeBtn.setTranslationX(homeBtnTransX);
        actionBtns.setTranslationY(actionBtnsTranY);
        mstBtn.setTranslationX(msgBtnTranX);
    }

    private void icAnim(float value){
        icColorApk.setTranslationX((normalApkX - colorApkX)*value/(wandoujiaheader2-wandoujiaHeader1));
        icColorGame.setTranslationX((normalGameX - colorGameX)*value/(wandoujiaheader2-wandoujiaHeader1));
        icColorVideo.setTranslationX((normalVideoX - colorVideoX)*value/(wandoujiaheader2-wandoujiaHeader1));
        icColorMusic.setTranslationX((normalMoreX - colorMusicX)*value/(wandoujiaheader2-wandoujiaHeader1));
        icColorMore.setTranslationX((normalMoreX - colorMoreX)*value/(wandoujiaheader2-wandoujiaHeader1));
    }

    private void activityHeaderUp() {

    }
}
