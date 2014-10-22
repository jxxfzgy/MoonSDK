package sdk.moon.com.moonsdk.custom;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import sdk.moon.com.moonsdk.R;

/**
 * Created by moon.zhong on 2014/10/22.
 * 创建自定义dialog
 */
public class MDialogBuilder extends AlertDialog.Builder {
    private Context mContext ;
    public MDialogBuilder(Context context) {
        super(context);
        mContext = context ;
    }

    public MDialogBuilder(Context context, int theme) {
        super(context, theme);
        mContext = context ;
    }

    @Override
    public AlertDialog show() {
        AlertDialog dialog = super.show() ;
        final int titleId = mContext.getResources().getIdentifier("alertTitle","id","android") ;
        if(titleId != 0){
            final TextView textTitle = (TextView)dialog.findViewById(titleId) ;
            textTitle.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        final int dividerId = mContext.getResources().getIdentifier("titleDivider", "id", "android") ;
        if(dividerId != 0){
            final View view = dialog.findViewById(dividerId) ;
            view.setBackgroundResource(R.color.green);
        }
        final int parentPanelId = mContext.getResources().getIdentifier("parentPanel", "id", "android") ;
        if(parentPanelId != 0){
            final View view = dialog.findViewById(parentPanelId) ;
        }
        final int topPanelId = mContext.getResources().getIdentifier("topPanel", "id", "android") ;
        if(topPanelId != 0){
            final View view = dialog.findViewById(topPanelId) ;
        }
        return dialog;
    }


/*    <?xml version="1.0" encoding="utf-8"?>
    <!--
*//* //device/apps/common/res/layout/alert_dialog.xml
**
** Copyright 2006, The Android Open Source Project
**
** Licensed under the Apache License, Version 2.0 (the "License");
** you may not use this file except in compliance with the License.
** You may obtain a copy of the License at
**
**     http://www.apache.org/licenses/LICENSE-2.0
**
** Unless required by applicable law or agreed to in writing, software
** distributed under the License is distributed on an "AS IS" BASIS,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the License for the specific language governing permissions and
** limitations under the License.
*//*
            -->

    <LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/parentPanel"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:paddingTop="9dip"
    android:paddingBottom="3dip"
    android:paddingStart="3dip"
    android:paddingEnd="1dip">

    <LinearLayout android:id="@+id/topPanel"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:minHeight="54dip"
    android:orientation="vertical">
    <LinearLayout android:id="@+id/title_template"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:gravity="center_vertical"
    android:layout_marginTop="6dip"
    android:layout_marginBottom="9dip"
    android:layout_marginStart="10dip"
    android:layout_marginEnd="10dip">
    <ImageView android:id="@+id/icon"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="top"
    android:paddingTop="6dip"
    android:paddingEnd="10dip"
    android:src="@drawable/ic_dialog_info" />
    <com.android.internal.widget.DialogTitle android:id="@+id/alertTitle"
    style="?android:attr/textAppearanceLarge"
    android:singleLine="true"
    android:ellipsize="end"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textAlignment="viewStart" />
    </LinearLayout>
    <ImageView android:id="@+id/titleDivider"
    android:layout_width="match_parent"
    android:layout_height="1dip"
    android:visibility="gone"
    android:scaleType="fitXY"
    android:gravity="fill_horizontal"
    android:src="@android:drawable/divider_horizontal_dark" />
    <!-- If the client uses a customTitle, it will be added here. -->
    </LinearLayout>

    <LinearLayout android:id="@+id/contentPanel"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_weight="1"
    android:orientation="vertical">
    <ScrollView android:id="@+id/scrollView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingTop="2dip"
    android:paddingBottom="12dip"
    android:paddingStart="14dip"
    android:paddingEnd="10dip"
    android:overScrollMode="ifContentScrolls">
    <TextView android:id="@+id/message"
    style="?android:attr/textAppearanceMedium"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="5dip" />
    </ScrollView>
    </LinearLayout>

    <FrameLayout android:id="@+id/customPanel"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_weight="1">
    <FrameLayout android:id="@+android:id/custom"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:paddingTop="5dip"
    android:paddingBottom="5dip" />
    </FrameLayout>

    <LinearLayout android:id="@+id/buttonPanel"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:minHeight="54dip"
    android:orientation="vertical" >
    <LinearLayout
    style="?android:attr/buttonBarStyle"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:paddingTop="4dip"
    android:paddingStart="2dip"
    android:paddingEnd="2dip"
    android:measureWithLargestChild="true">
    <LinearLayout android:id="@+id/leftSpacer"
    android:layout_weight="0.25"
    android:layout_width="0dip"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:visibility="gone" />
    <Button android:id="@+id/button1"
    android:layout_width="0dip"
    android:layout_gravity="start"
    android:layout_weight="1"
    style="?android:attr/buttonBarButtonStyle"
    android:maxLines="2"
    android:layout_height="wrap_content" />
    <Button android:id="@+id/button3"
    android:layout_width="0dip"
    android:layout_gravity="center_horizontal"
    android:layout_weight="1"
    style="?android:attr/buttonBarButtonStyle"
    android:maxLines="2"
    android:layout_height="wrap_content" />
    <Button android:id="@+id/button2"
    android:layout_width="0dip"
    android:layout_gravity="end"
    android:layout_weight="1"
    style="?android:attr/buttonBarButtonStyle"
    android:maxLines="2"
    android:layout_height="wrap_content" />
    <LinearLayout android:id="@+id/rightSpacer"
    android:layout_width="0dip"
    android:layout_weight="0.25"
    android:layout_height="wrap_content"
    android:orientation="horizontal"
    android:visibility="gone" />
    </LinearLayout>
    </LinearLayout>
    </LinearLayout>*/


}
