package com.visionet.fengniao.activity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.visionet.fengniao.Data;
import com.visionet.fengniao.R;
import com.visionet.fengniao.core.activity.ActionBarBaseActivity;


public class NoticeActivity extends ActionBarBaseActivity {
    @Override
    public View createContentView() {
        View view = getLayoutInflater().inflate(R.layout.activity_notice,null);
        TextView tvNotice = (TextView) view.findViewById(R.id.tv_notice);
        TextView tvNoticeTitle = (TextView) view.findViewById(R.id.tv_notice_title);
        setTitle(getIntent().getStringExtra("title"));
        Data.Notice notice = getIntent().getParcelableExtra("notice");
        tvNoticeTitle.setText(notice.title);
        tvNotice.setText(notice.content);
        return view;
    }
    public static void toNoticeActivity(Activity activity, Data.Notice notice,String title){
        Intent intent = new Intent(activity, NoticeActivity.class);
        intent.putExtra("notice",notice);
        intent.putExtra("title",title);
        activity.startActivity(intent);
    }
}