package com.example.com.a24city;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.onclicklisn.exit_oncl;
import com.example.onclicklisn.exit_quxiao;
import com.example.utils.LeUtils;

import network.HttpRequestWrap;
import network.OnResponseHandler;
import network.RequestHandler;
import network.RequestStatus;

public class MainActivity extends AppCompatActivity {
    private String[] texts = {"首页","设计师", "搜户型", "我的"};
    public String SEARCH_HISTORY = "search_fenlei";
    public SharedPreferences sp;
    public FragmentManager fragmentManager;
    SharedPreferences.Editor editor;
    private int[] icons = {
            R.drawable.tab_item_baike,
            R.drawable.tab_item_search,
            R.drawable.tab_item_shouye,
            R.drawable.tab_item_wode
    };
    private Class[] fragments = new Class[]{
            Home_activity_align.class,
            Search_Auther_acty.class,
            Search_Huxing_acty.class,
            User_acty.class
    };
    private HttpRequestWrap httpRequest;
    FragmentTabHost tabHost;

    public static final int TAB_COUNT = 4;
    private StringBuilder sb = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        httpRequest = new HttpRequestWrap(MainActivity.this);
        sp = this.getSharedPreferences(SEARCH_HISTORY, 0);
        fragmentManager = this.getSupportFragmentManager();
        editor  =  sp.edit();
        tabHost();
//        doRequest();
    }


    public void dailog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("温馨提示");
        dialog.setMessage("是否退出");
        dialog.setPositiveButton("确定", new exit_oncl(MainActivity.this));
        dialog.setNegativeButton("取消", new exit_quxiao(MainActivity.this));
        dialog.create().show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            dailog();
        }
        return false;
    }

    /**
     * 为FragmentTagHost添加TabSpec
     */
    protected void tabHost(){
        tabHost = (FragmentTabHost) findViewById(
                android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(),
                R.id.real_content);
        //添加TabSpec
        for(int i = 0; i < TAB_COUNT; i ++){
            tabHost.addTab(createTabSpec(tabHost, i),
                    fragments[i], null);
//    		if(info.fragment==null){
//    	        info.fragment = Fragment.instantiate(mContext,
//    	                info.clss.getName(), info.args);
//    	        FragmentTransaction ft = mFragmentManager.beginTransaction();
//    	        ft.add(mContainerId, info.fragment, info.tag);
//    	        ft.hide(info.fragment);
//    	        ft.commit();
//    	    }
        }
        //去掉分割线
        tabHost.getTabWidget().setDividerDrawable(null);

        //注册监听器
        tabHost.setOnTabChangedListener(new TabChangeListener());
        tabHost.setCurrentTab(0);
    }
//    private void doRequest(){
//
//        httpRequest.setMethod(HttpRequestWrap.POST);
//        // 设置回调
//        httpRequest.setCallBack(new RequestHandler(MainActivity.this,
//                new OnResponseHandler()
//                {
//                    @Override
//                    public void onResponse(String result,
//                                           RequestStatus status)
//                    {
//                        JSONObject obj = JSON.parseObject(result);
//                        if (obj != null)
//                        {
//                            JSONArray data = obj.getJSONArray("data");
//                            if(data!=null){
//                                if(data.size()>0){
//                                    sp.edit().remove(SEARCH_HISTORY).commit();
//                                    for(int i=0;i<data.size();i++){
//                                        int id = data.getJSONObject(i).getInteger("categoryId");
//                                        String nm = data.getJSONObject(i).getString("categoryName");
//                                        sb.append(nm+"_"+id+",");
//                                    }
//                                    editor.putString(SEARCH_HISTORY, sb.toString()).commit();
//                                }else{
//                                }
//                            }
//
//                        } else
//                        {
//                            Toast.makeText(MainActivity.this, "数据请求失败，请检查网格" + status,
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }));
////        httpRequest
////                .send(LeUtils.SERVICE_URLADSS+LeUtils.FENLEI_ACTION);
//    }


    /**
     * 创建TabSpec
     */
    protected TabHost.TabSpec createTabSpec(
            FragmentTabHost tabHost, int index){
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(texts[index]);
        tabSpec.setIndicator(createTabItem(index));
        return tabSpec;
    }

    /**
     * 创建TabSpec需要的View
     * @param index
     * @return
     */
    protected View createTabItem(int index){
        View root =
                LayoutInflater.from(this)
                        .inflate(R.layout.tab_item, null);
        ImageView icon = (ImageView) root.findViewById(R.id.icon);
        TextView text = (TextView) root.findViewById(R.id.text);

        icon.setImageResource(icons[index]);
        text.setText(texts[index]);

        if(index == 0){
            text.setTextColor(Color.parseColor("#0288D1"));
        }else{
            text.setTextColor(Color.parseColor("#898989"));
        }

        return root;
    }


    /**
     * 切换Tab的事件监听器
     * @author 韬睿科技：李赞红
     *
     */
    class TabChangeListener implements TabHost.OnTabChangeListener {
        /**
         * TabSpec tabSpec = tabHost.newTabSpec(texts[index]);
         * @param tag Tag
         */
        @Override
        public void onTabChanged(String tag) {
            Log.d(MainActivity.class.getSimpleName(), tag);

            //获得
            TabWidget widget = tabHost.getTabWidget();

            for(int i = 0; i < TAB_COUNT; i ++){
                TextView curText = (TextView) widget
                        .getChildTabViewAt(i)
                        .findViewById(R.id.text);
                if(curText.getText().equals(tag)){
                    curText.setTextColor(Color.parseColor("#0288D1"));
                }else{
                    curText.setTextColor(Color.parseColor("#898989"));
                }
            }
        }

    }

    /*在fragment的管理类中，我们要实现这部操作，而他的主要作用是，当D这个activity回传数据到
    这里碎片管理器下面的fragnment中时，往往会经过这个管理器中的onActivityResult的方法。*/
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//    	switch (requestCode)
//		{
//		case 0:
//			break;
//		case 1:
//			int u_id = data.getIntExtra("userId", 0);
//			if(u_id!=0){
//				Toast.makeText(MainActivity.this, "uid位=="+u_id, Toast.LENGTH_SHORT).show();
//			}
//			break;
//		default:
//			break;
//		}
        if(data!=null){
            super.onActivityResult(requestCode, resultCode, data);
            /*在这里，我们通过碎片管理器中的Tag，就是每个碎片的名称，来获取对应的fragment*/
            Fragment f = fragmentManager.findFragmentByTag("我的");
            if(f==null){
                Toast.makeText(MainActivity.this, "没有获取到碎片"+User_acty.class.getSimpleName(), Toast.LENGTH_SHORT).show();
            }else{
            	/*然后在碎片中调用重写的onActivityResult方法*/
                f.onActivityResult(requestCode, resultCode, data);
            }

        }

    }
}
