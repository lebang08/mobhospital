package com.tiantanhehe.yidongchafang.views.activities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.capricorn.ArcMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnClosedListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.tiantanhehe.tiantanrdp.RdpMainActivity;
import com.tiantanhehe.yidongchafang.GlobalInfoApplication;
import com.tiantanhehe.yidongchafang.R;
import com.tiantanhehe.yidongchafang.common.CookieHelper;
import com.tiantanhehe.yidongchafang.common.ResourceHelper;
import com.tiantanhehe.yidongchafang.common.RunTimeState;
import com.tiantanhehe.yidongchafang.common.SpeechApp;
import com.tiantanhehe.yidongchafang.conf.AppConf;
import com.tiantanhehe.yidongchafang.dao.db.DatabaseHelper;
import com.tiantanhehe.yidongchafang.dao.db.ZhuyuanHuanzheDao;
import com.tiantanhehe.yidongchafang.dao.db.wrapper.HuanzheWrapper;
import com.tiantanhehe.yidongchafang.dao.network.HttpHelper;
import com.tiantanhehe.yidongchafang.dao.network.IHandleHttpHelperResult;
import com.tiantanhehe.yidongchafang.features.bingcheng.BingChengJiluActivity;
import com.tiantanhehe.yidongchafang.features.jiancha.JianyanJianchaActivity;
import com.tiantanhehe.yidongchafang.features.overview.ChafangOverviewActivity;
import com.tiantanhehe.yidongchafang.features.overview.ChafangYindaoActivity;
import com.tiantanhehe.yidongchafang.features.overview.ChakanHuanzheActivity;
import com.tiantanhehe.yidongchafang.features.overview.ShijianShituActivity;
import com.tiantanhehe.yidongchafang.features.overview.ZhuyuanOverviewActivity;
import com.tiantanhehe.yidongchafang.features.tizhen.TiwenJiludanActivity;
import com.tiantanhehe.yidongchafang.features.xiezuo.ChafangGensuiActivity;
import com.tiantanhehe.yidongchafang.features.yizhu.YizhuChakanActivity;
import com.tiantanhehe.yidongchafang.features.zhenduan.ZhenduanActivity;
import com.tiantanhehe.yidongchafang.services.DataManager;
import com.tiantanhehe.yidongchafang.services.XiezuoService;
import com.tiantanhehe.yidongchafang.utils.SharedPreferencesUtils;
import com.tiantanhehe.yidongchafang.views.adapters.FenzuListAdapter;
import com.tiantanhehe.yidongchafang.views.adapters.HuanzheListAdapter;
import com.tiantanhehe.yidongchafang.views.adapters.LeftMenuListAdapter;
import com.tiantanhehe.yidongchafang.views.adapters.MultiMediaListAdapter;
import com.tiantanhehe.yidongchafang.views.fragments.BingChengJiluListFragment;
import com.tiantanhehe.yidongchafang.views.fragments.ChafangYindaoFragment;
import com.tiantanhehe.yidongchafang.views.fragments.HuanzheListFragment;
import com.tiantanhehe.yidongchafang.views.fragments.HuliJiluListFragment;
import com.tiantanhehe.yidongchafang.views.fragments.JianchaListFragment;
import com.tiantanhehe.yidongchafang.views.fragments.JianyanListFragment;
import com.tiantanhehe.yidongchafang.views.fragments.ShowOneBingchengReportFragment;
import com.tiantanhehe.yidongchafang.views.fragments.ShowOneJianchaReportFragment;
import com.tiantanhehe.yidongchafang.views.fragments.ShowOneJianyanReportFragment;
import com.tiantanhehe.yidongchafang.views.fragments.TimesAxisFragment;
import com.tiantanhehe.yidongchafang.views.fragments.TiwenJiluListFragment;
import com.tiantanhehe.yidongchafang.views.fragments.YiZhuChakanListFragment;
import com.tiantanhehe.yidongchafang.views.fragments.ZhuyuanZonglanFragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: YiDongYiHuActivity
 * @Description: TODO 移动医护产品相关基类，与医疗业务相关，大部分具体功能Activity的父类
 * @author Huke <huke@tiantanhehe.com>
 * @date 2016年4月5日 下午7:21:22
 * 
 */
public class YiDongYiHuActivity extends TiantanActivity {

	private TextView huanzhe_info;

	protected ActionBar actionBar;
	public SlidingMenu menu;
	public ArcMenu arcMenu;
	protected LinearLayout ll_left_window;
	protected LinearLayout ll_main_window;
	protected LinearLayout ll_common_left;
	protected LinearLayout left_content;
	protected LinearLayout user_info;
	protected LinearLayout ll_multimedia_list_content;
	protected LinearLayout ll_huanzhe_list_content;
	protected LinearLayout ll_xiaobianque_content;
	private ImageView image;
	private ProgressDialog qiehuaProDialog = null;
	public ListView right_content;
	public ListView lv_left_menu, poplist;
	public ListView lv_multimedia;
	public ExpandableListView right_huanzhe_list;
	public TextView text_zongrenshu;// 显示总人数
	public TextView text_teshuhuanze;// 显示特殊患者人数
	public TextView text_yijihuanzhe;// 显示一级患者人数
	public TextView text_erjihuanzhe;// 显示二级患者人数
	private List<String> huliList = new ArrayList<String>();
	protected LinearLayout contentView;
	protected LinearLayout contentViewDialog;
	public WebView contentWebView;
	protected WebView xiaobianqueWebView;
	protected int contentWebViewScale = 0;
	private PopupWindow teshuHuanzhePopupWindow;
	private boolean isOpenPop = false;
	// 协作相关
	public boolean reflesh = false;
	protected XiezuoService.ControllBinder xiezuoBinder = null;
	protected XiezuoServiceConnection xiezuoServiceConnection = null;

	public View view;

	private HuanzheListAdapter huanzheAdapter;
	private FenzuListAdapter fenzuAdapter;
	protected MultiMediaListAdapter multiMediaListAdapter;
	public Class<?> tiaoZhuangActivity = null;

	public boolean right_state;// 右侧菜单的打开状态
	public int right_content_state = 1;// 右侧内容显示状态
	public boolean is_onclick = false;
	public boolean isZhujiemian = false;
	public boolean left_state;// 左侧菜单的打开状态
	public boolean left_user_info;// 左侧用户信息打开状态
	public boolean left_feature_menu;// 左侧功能菜单打开状态
	public String item_type = ""; // 大模块的子类型
	public String riqi = "";
	public int zongrenshu = 0;
	public int teshu = 0;
	public int yiji = 0;
	public int erji = 0;
	public int sanji = 0;
	public int qita = 0;

	public DatabaseHelper db;
	private List<Map<String, Object>> listdata;
	List<Map<String, String>> groups;
	List<List<Map<String, Object>>> childs;
	public ZhuyuanHuanzheDao mZhuyuanHuanzheDao;
	Intent mIntent;
	protected Button all_but, kangfu_zhiliao_but;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dataInit(); // 数据初始化
		viewInit(); // 界面相关初始化
		serviceInit();// 服务初始化，启动相关后台服务，包括但不限于android service

	}

	private void serviceInit() {

	}

	private void viewInit() {
		
	}

	private void dataInit() {
		mIntent = getIntent();

		if (current_application.data_manager == null) {
			current_application.data_manager = new DataManager(this);
		}
		db = DatabaseHelper.getInstance(context, AppConf.database_name);
		mZhuyuanHuanzheDao = new ZhuyuanHuanzheDao(context, current_application);

		for (String menuName : current_application.appConf.left_menu) {
			String resName = "leftmenu_"
					+ GlobalInfoApplication.getInstance().appConf.left_menu_lang_map
							.get(menuName) + "_selector";
			int resId = ResourceHelper.getInstance().getResourceIdByName(
					context, resName);
			RunTimeState.getInstance().leftmenuRes.put(menuName, resId);
		}

	}

	public void initSlidingMenu() {
		menu = new SlidingMenu(this);
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			menu.setMode(SlidingMenu.LEFT_RIGHT);
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN); // 设置触摸屏幕的模式
			menu.setMenu(R.layout.common_left); // 为侧滑菜单设置布局
			menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);

		} else {
			menu.setMode(SlidingMenu.RIGHT);
			menu.setBehindWidthRes(R.dimen.slidingmenu_width); // 设置滑动菜单视图的宽度
		}

		menu.setShadowWidthRes(R.dimen.shadow_width);

		menu.setFadeDegree(0.75f); // 设置渐入渐出效果的值
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT); // 设置ActionBar是否跟随打开或者关闭

		menu.setSecondaryMenu(R.layout.common_right);// 设置右侧菜单
		// 监听打开
		initLeftMenu();
		menu.setOnOpenListener(new OnOpenListener() {
			@Override
			public void onOpen() {
				if (is_onclick) {
					user_info.setVisibility(View.VISIBLE);
					left_content.setVisibility(View.GONE);

					setUserInfoData();

				} else {
					user_info.setVisibility(View.GONE);
					// 情景导航Activity
					setNavData();
				}
				right_state = true;
				left_state = true;
			}
		});
		menu.setOnOpenedListener(new OnOpenedListener() {
			@Override
			public void onOpened() {
				if (right_content_state == 1) {
					all_but = (Button) findViewById(R.id.all_but);
					kangfu_zhiliao_but = (Button) findViewById(R.id.kangfu_zhiliao_but);
					if (current_application.appConf.current_user_suoshu_group
							.equals("基层康复诊疗组")) {
						all_but.setVisibility(View.GONE);
					} else {
						all_but.setVisibility(View.VISIBLE);
					}
					// 患者列表显示相关
					if (RunTimeState.getInstance().getXuanzhongFenzu() < 1
							&& listdata.size() < 1) {
						if (current_application.appConf.current_user_suoshu_group
								.equals("基层康复诊疗组")) {
							getKangfuHuanzhe(view);
						} else {
							getAllHuanzhe();
						}
					} else {
						// 增加by Hooke@2015-8-25 切换至选中的患者
						if (current_application.appConf.current_user_suoshu_group
								.equals("基层康复诊疗组")) {
							getKangfuHuanzhe(view);
						} else {
							getAllHuanzhe();
						}

						qiehuanXuanzhongPosition();
						// 结束by Hooke@2015-8-25
					}
				} else if (right_content_state == 2) {
					// 处理多媒体显示相关
				} else if (right_content_state == 3) {
					// loadXiaobianque();
				}
			}
		});
		// 监听关闭
		menu.setOnClosedListener(new OnClosedListener() {
			@Override
			public void onClosed() {
				is_onclick = false;
				right_state = false;
				left_state = false;
			}
		});
		ll_left_window = (LinearLayout) findViewById(R.id.left_window);
		ll_common_left = (LinearLayout) findViewById(R.id.common_left);
		right_content = (ListView) findViewById(R.id.right_content);
		lv_multimedia = (ListView) findViewById(R.id.right_multimedia_list);
		listdata = new ArrayList<Map<String, Object>>();

		left_content = (LinearLayout) findViewById(R.id.left_content);
		user_info = (LinearLayout) findViewById(R.id.user_info);
		right_huanzhe_list = (ExpandableListView) findViewById(R.id.right_huanzhe_list);
		xiaobianqueWebView = (WebView) findViewById(R.id.right_xiaobianque);

		ll_multimedia_list_content = (LinearLayout) findViewById(R.id.ll_multimedia_list_content);
		ll_huanzhe_list_content = (LinearLayout) findViewById(R.id.ll_huanzhe_list_content);
		ll_xiaobianque_content = (LinearLayout) findViewById(R.id.ll_xiaobianque_content);

		ll_multimedia_list_content.setVisibility(View.GONE);
		ll_xiaobianque_content.setVisibility(View.GONE);
		// 设置患者分组点击事件
		initHuanzheFenzuView();
		// 预先加载数据
		// getRightHuanzheData("");
	}

	private void initHuanzheFenzuView() {
		RelativeLayout rl_qbhz = (RelativeLayout) findViewById(R.id.rl_qbhz);

		image = (ImageView) findViewById(R.id.iv_qbhz);
		rl_qbhz.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				changPopState(v);
				poplist.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						getRightTeshuHuanzheData(huliList.get(position)
								.toString());

						if (teshuHuanzhePopupWindow != null) {
							teshuHuanzhePopupWindow.dismiss();

						}
					}
				});
			}
		});
	}

	
	public void changPopState(View v) {

		isOpenPop = !isOpenPop;
		if (isOpenPop) {
			image.setBackgroundResource(R.drawable.common_up_white);
			createTeshuHuanzhePopupWindow(v);

		} else {
			image.setBackgroundResource(R.drawable.common_down_white);
			if (teshuHuanzhePopupWindow != null) {
				teshuHuanzhePopupWindow.dismiss();

			}
		}
	}



	private void createTeshuHuanzhePopupWindow(View parent) {

		if (teshuHuanzhePopupWindow == null) {
			LayoutInflater lay = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = lay.inflate(R.layout.pop, null);
			poplist = (ListView) v.findViewById(R.id.pop_list);
			getHuliList();
			PopAdapter adapter = new PopAdapter();
			poplist.setAdapter(adapter);
			poplist.setItemsCanFocus(false);
			poplist.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

			teshuHuanzhePopupWindow = new PopupWindow(v, parent.getWidth(),
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);

		}
		teshuHuanzhePopupWindow.setBackgroundDrawable(new ColorDrawable(
				getResources().getColor(android.R.color.transparent)));
		teshuHuanzhePopupWindow.setOutsideTouchable(true);
		teshuHuanzhePopupWindow.setFocusable(true);

		teshuHuanzhePopupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				isOpenPop = false;
				image.setBackgroundResource(R.drawable.common_down_white);
			}
		});
		teshuHuanzhePopupWindow.update();

		teshuHuanzhePopupWindow.showAsDropDown(parent);
	}


	private void getHuliList(){
		String[] huliarray = current_application.appConf.huanzhe_shaixuan_peizhi;
		if (huliarray.length > 0) {
			for (int i = 0; i < huliarray.length; i++) {
				huliList.add(huliarray[i].toString());
			}
		}

}

	class PopAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return huliList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return huliList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vHolder;
		if (convertView==null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.pipwindow_item, null);
				vHolder = new ViewHolder();
				vHolder.tv = (TextView) convertView.findViewById(R.id.yi);
				convertView.setTag(vHolder);
			} else {
				vHolder = (ViewHolder) convertView.getTag();
		}
			String renshu = huoQuJiBieRenShu(huliList.get(position).toString());
			vHolder.tv.setText(huliList.get(position).toString() + ":"
 + renshu);

			return convertView;
	}

		class ViewHolder {
			TextView tv;
		}
	}
	
	/**
	 * @Title: initLeftMenu
	 * @Description: 初始化左侧菜单
	 * @author: Huke <Huke@tiantanhehe.com>
	 * @date: 2016年4月11日 上午12:06:34
	 */
	private void initLeftMenu() {
		final String[] leftMenu = current_application.appConf.left_menu;
		List<Map<String, Object>> listdata = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < leftMenu.length; i++) {
			Map<String, Object> leftMenuMapValue = new HashMap<String, Object>();
			leftMenuMapValue.put("menu_name", leftMenu[i]);
			listdata.add(leftMenuMapValue);
		}

		LeftMenuListAdapter adapter = new LeftMenuListAdapter(context, listdata);
		lv_left_menu = (ListView) findViewById(R.id.left_menu_listview);
		lv_left_menu.setAdapter(adapter);
		lv_left_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				navToMenu(leftMenu[position]);
				current_application.appConf.current_menu = leftMenu[position];

			}
		});
	}

	/**
	 * @Title: navToMenu
	 * @Description: TODO
	 * @author: Huke <Huke@tiantanhehe.com>
	 * @date: 2016年4月11日 上午12:26:24
	 * @param string
	 */
	protected void navToMenu(String string) {

		if (isZhujiemian || getString(R.string.yingyongjicheng).equals(string)
				|| getString(R.string.jianchayingxiang).equals(string)
				|| getString(R.string.baodaoxitong).equals(string)
				|| getString(R.string.dianzibingli).equals(string)) {
			jumpToMokuai(string);
		} else if (getString(R.string.chafangyindao).equals(string)
				|| getString(R.string.chakanhuanzhe).equals(string)
				|| getString(R.string.zhuyuanzonglan).equals(string)
				|| getString(R.string.chakanyizhu).equals(string)
				|| getString(R.string.binglijilu).equals(string)
				|| getString(R.string.hulijilu).equals(string)
				|| getString(R.string.shijianshitu).equals(string)
				|| getString(R.string.tiwenjiludan).equals(string)
 || getString(R.string.jianyanjilu).equals(string)
				|| getString(R.string.jianchajilu).equals(string) || getString(R.string.ruyuanjilu).equals(string)
				|| getString(R.string.chuyuanjilu).equals(string)) {
			if (contentWebView != null) {
				contentWebView.setVisibility(View.GONE);
				contentView.setVisibility(View.VISIBLE);
				setNewFragment(string);
			}
		} else {
			loadMokuai(string);
			contentWebView.setVisibility(View.VISIBLE);
			contentView.setVisibility(View.GONE);
		}
	}

	public void setNewFragment(String name) {
		current_application.appConf.current_mokuai = name;
		current_application.appConf.current_menu = name;
		if (lv_left_menu != null) {
			LeftMenuListAdapter adapter = (LeftMenuListAdapter) lv_left_menu
					.getAdapter();
			adapter.notifyDataSetChanged();
		}
		current_application.appConf.dangqian_mokuai = name;
		Bundle bundle = new Bundle();
		bundle.putString("server_url", current_application.appConf.server_url);
		bundle.putString("current_user_department",
				current_application.appConf.current_user_department);
		bundle.putString("current_user_number",
				current_application.appConf.current_user_number);
		bundle.putString("current_patient_zhuyuan_id",
				current_application.appConf.current_patient_zhuyuan_id);
		bundle.putString("current_patient_zhuyuan_id_show",
				current_application.appConf.current_patient_zhuyuan_id_show);
		bundle.putString("current_patient_id",
				current_application.appConf.current_patient_id);
		bundle.putString("current_patient_bingchuang_hao",
				current_application.appConf.current_patient_bingchuang_hao);
		bundle.putString("current_patient_nianling",
				current_application.appConf.current_patient_nianling);
		bundle.putString("current_patient_xingbie",
				current_application.appConf.current_patient_xingbie);
		bundle.putString("current_patient_xingming",
				current_application.appConf.current_patient_xingming);
		bundle.putString("current_user_suoshu_group",
				current_application.appConf.current_user_suoshu_group);
		bundle.putBoolean("kaifuzhiliao_kaiguan",
				current_application.appConf.kaifuzhiliao_kaiguan);

		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		if (getString(R.string.chakanhuanzhe).equals(name)) {
			HuanzheListFragment chakanhuanzhe = new HuanzheListFragment();
			chakanhuanzhe.setArguments(bundle);
			fragmentTransaction
					.replace(R.id.contentView, chakanhuanzhe, "left");
		} else if (getString(R.string.zhuyuanzonglan).equals(name)) {
			ZhuyuanZonglanFragment zhuyuanzonglan = new ZhuyuanZonglanFragment();
			zhuyuanzonglan.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentView, zhuyuanzonglan,
					"left");
		} else if (getString(R.string.chafangyindao).equals(name)) {
			ChafangYindaoFragment chafangyindao = new ChafangYindaoFragment();
			chafangyindao.setArguments(bundle);
			fragmentTransaction
					.replace(R.id.contentView, chafangyindao, "left");
		} else if (getString(R.string.chakanyizhu).equals(name)) {
			YiZhuChakanListFragment yizhuchakan = new YiZhuChakanListFragment();
			yizhuchakan.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentView, yizhuchakan, "left");
		} else if (getString(R.string.binglijilu).equals(name)) {
			BingChengJiluListFragment bingchengjilu = new BingChengJiluListFragment();
			bingchengjilu.setArguments(bundle);
			fragmentTransaction
					.replace(R.id.contentView, bingchengjilu, "left");
		} else if (getString(R.string.tiwenjiludan).equals(name)) {
			TiwenJiluListFragment tiwenjilu = new TiwenJiluListFragment();
			tiwenjilu.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentView, tiwenjilu, "left");
		} else if (getString(R.string.jianyanjilu).equals(name)) {
			JianyanListFragment jianyan = new JianyanListFragment();
			jianyan.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentView, jianyan, "left");
		} else if (getString(R.string.jianchajilu).equals(name)) {
			JianchaListFragment jiancha = new JianchaListFragment();
			jiancha.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentView, jiancha,
					"left");
		} else if (getString(R.string.ruyuanjilu).equals(name)) {
			ShowOneBingchengReportFragment ruyuanjilu = new ShowOneBingchengReportFragment();
			String tap = "入院";
			String url = current_application.appConf.server_url
					+ "Mobile/YidongChafangClientCommunication/getRuyuanJiluResultDataJson/zhuyuan_id/"
					+ current_application.appConf.current_patient_zhuyuan_id;
			bundle.putString("tap", tap);
			bundle.putString("ruyuanjiluUrl", url);
			ruyuanjilu.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentView, ruyuanjilu, "left");
		} else if (getString(R.string.chuyuanjilu).equals(name)) {
			ShowOneBingchengReportFragment chuyuanjilu = new ShowOneBingchengReportFragment();
			String tap = "出院";
			String url = current_application.appConf.server_url
					+ "Mobile/YidongChafangClientCommunication/getRuyuanJiluResultDataJson/zhuyuan_id/"
					+ current_application.appConf.current_patient_zhuyuan_id;
			bundle.putString("tap", tap);
			bundle.putString("chuyuanjiluUrl", url);
			chuyuanjilu.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentView, chuyuanjilu, "left");
		} else if (getString(R.string.hulijilu).equals(name)) {
			HuliJiluListFragment hulijilu = new HuliJiluListFragment();
			hulijilu.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentView, hulijilu, "left");
		}
		else if (getString(R.string.shijianshitu).equals(name)) {
			TimesAxisFragment axisFragment = new TimesAxisFragment();
			axisFragment.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentView, axisFragment, "left");
		} 
		else {
			Toast.makeText(this, "未找到这个模块", Toast.LENGTH_SHORT).show();
		}
		fragmentTransaction.commit();
	}

	protected void setOneFragment(String name,Map<String, String> map) {
		current_application.appConf.dangqian_mokuai = name;
		Bundle bundle = new Bundle();
		bundle.putString("server_url", current_application.appConf.server_url);
		bundle.putString("current_user_department",
				current_application.appConf.current_user_department);
		bundle.putString("current_user_number",
				current_application.appConf.current_user_number);
		bundle.putString("current_patient_zhuyuan_id",
				current_application.appConf.current_patient_zhuyuan_id);
		bundle.putString("current_patient_zhuyuan_id_show",
				current_application.appConf.current_patient_zhuyuan_id_show);
		bundle.putString("current_patient_id",
				current_application.appConf.current_patient_id);
		bundle.putString("current_patient_bingchuang_hao",
				current_application.appConf.current_patient_bingchuang_hao);
		bundle.putString("current_patient_nianling",
				current_application.appConf.current_patient_nianling);
		bundle.putString("current_patient_xingbie",
				current_application.appConf.current_patient_xingbie);
		bundle.putString("current_patient_xingming",
				current_application.appConf.current_patient_xingming);
		bundle.putString("current_user_suoshu_group",
				current_application.appConf.current_user_suoshu_group);
		bundle.putBoolean("kaifuzhiliao_kaiguan",
				current_application.appConf.kaifuzhiliao_kaiguan);
		if(map.size() > 0)
		{
			for (String key : map.keySet()) {  
			    bundle.putString(key,map.get(key));
			}
		}
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();
		if (getString(R.string.type_jianyanjilu).equals(name)) {
			ShowOneJianyanReportFragment JianyanReport = new ShowOneJianyanReportFragment();
			JianyanReport.setArguments(bundle);
			fragmentTransaction
					.replace(R.id.contentViewDialog, JianyanReport, "left");
		} else if (getString(R.string.type_jianchajilu).equals(name)) {
			ShowOneJianchaReportFragment JianchaReport = new ShowOneJianchaReportFragment();
			JianchaReport.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentViewDialog, JianchaReport,
					"left");
		} else if (getString(R.string.type_bingchengjilu).equals(name)) {
			ShowOneBingchengReportFragment BingchengReport = new ShowOneBingchengReportFragment();
			BingchengReport.setArguments(bundle);
			fragmentTransaction
					.replace(R.id.contentViewDialog, BingchengReport, "left");
		} else if (getString(R.string.type_ruyuanjilu).equals(name)) {
			ShowOneBingchengReportFragment ruyuanjilu = new ShowOneBingchengReportFragment();
			String tap = "入院";
			String url = current_application.appConf.server_url
					+ "Mobile/YidongChafangClientCommunication/getRuyuanJiluResultDataJson/zhuyuan_id/"
					+ current_application.appConf.current_patient_zhuyuan_id;
			bundle.putString("tap", tap);
			bundle.putString("ruyuanjiluUrl", url);
			ruyuanjilu.setArguments(bundle);
			fragmentTransaction.replace(R.id.contentViewDialog, ruyuanjilu, "left");
		} 
		fragmentTransaction.commit();
	}

	@SuppressLint("HandlerLeak")
	private final Handler myHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			current_application.appConf.current_patient_id = "";
			current_application.appConf.current_patient_zhuyuan_id = "";
			current_application.appConf.current_patient_zhuyuan_bingqu = "";
			current_application.appConf.current_patient_bingchuang_hao = "";
			current_application.appConf.current_patient_xingming = "";
			current_application.appConf.current_patient_xingbie = "";
			current_application.appConf.current_patient_nianling = "";
			current_application.appConf.current_patient_huli_jibie = "";
			current_application.appConf.current_patient_zhenduan = "";

			listdata.clear();
			getRightHuanzheData("");
			qiehuaProDialog.dismiss();
			Intent intent = new Intent(YiDongYiHuActivity.this,
					MainActivity.class);
			finish();
			startActivity(intent);
			Toast.makeText(getApplicationContext(), "当前科室切换成功。",
					Toast.LENGTH_LONG).show();
		}

	};

	/**
	 * 
	 * /** 显示主界面顶端患者信息 变量： user_icon为用户图标 user_huli_icon 为病人护理级别图标
	 * Arno修改_2015.09.21
	 * 
	 */
	public void setDingbuHuanZheXinXi() {
		ImageView userIcon = (ImageView) findViewById(R.id.usericon);

		// 判断患者性别
		if (current_application.appConf.current_patient_xingbie
				.equals(getString(R.string.nv))) {// "女"
			// 判断患者护理级别
			if (current_application.appConf.current_patient_huli_jibie
					.equals(getString(R.string.yijihuli))) {// "一级护理"
				userIcon.setImageResource(R.drawable.common_woman_yi);
			} else if (current_application.appConf.current_patient_huli_jibie
					.equals(getString(R.string.erjihuli))) {// "二级护理"
				userIcon.setImageResource(R.drawable.common_woman_er);
			} else if (current_application.appConf.current_patient_huli_jibie
					.equals(getString(R.string.tejihuli))) {// "特级护理"
				userIcon.setImageResource(R.drawable.common_woman_te);
			} else {
				userIcon.setImageResource(R.drawable.common_woman_san);
			}

		} else {
			// 判断患者护理级别
			if (current_application.appConf.current_patient_huli_jibie.equals(getString(R.string.yijihuli))) {
				userIcon.setImageResource(R.drawable.common_man_yi);
			} else if (current_application.appConf.current_patient_huli_jibie.equals(getString(R.string.erjihuli))) {
				userIcon.setImageResource(R.drawable.common_man_er);
			} else if (current_application.appConf.current_patient_huli_jibie.equals(getString(R.string.tejihuli))) {
				userIcon.setImageResource(R.drawable.common_man_te);
			} else {
				userIcon.setImageResource(R.drawable.common_man_san);
			}
		}



		// 设置图片格式
		// userIcon.setBounds(0, 0, user_icon.getMinimumWidth(),
		// user_icon.getMinimumHeight());

		// 显示患者信息
		huanzhe_info = (TextView) findViewById(R.id.header_huanzhe_info);
		huanzhe_info.setText(" "
				+ current_application.appConf.current_patient_xingming + " "
				+ current_application.appConf.current_patient_nianling + " "
				+ current_application.appConf.current_patient_bingchuang_hao
				+ "床");
		// 设置UserIcon
		 if
		 (current_application.appConf.current_patient_zhuyuan_id.equals("")) {
		 userIcon.setVisibility(View.GONE);
		 } else {
		 userIcon.setVisibility(View.VISIBLE);
		 }
		// huanzhe_info.setCompoundDrawables(user_icon, null, null, null);
	}

	// 如果患者信息为空填充患者信息
	public void initHuanZheData() {
		if (current_application.appConf.current_patient_zhuyuan_id.equals("")) {
			HuanzheWrapper huanzhe = mZhuyuanHuanzheDao.getFirstHuanzhe();
			if (huanzhe != null) {
				huanzhe.setGlobalData(current_application);
			}
		}
	}

	// 初始化，显示人数的控件。
	protected void initRenShuView() {
		text_zongrenshu = (TextView) findViewById(R.id.text_zongrenshu);
		text_teshuhuanze = (TextView) findViewById(R.id.text_teshuhuanze);
		text_yijihuanzhe = (TextView) findViewById(R.id.text_yijihuanzhe);
		text_erjihuanzhe = (TextView) findViewById(R.id.text_erjihuanzhe);
	}

	// 当点击一大组和二大组是调用此方法，隐藏控件。
	private void yingcangkongjian() {
		text_zongrenshu.setVisibility(View.GONE);
		text_teshuhuanze.setVisibility(View.GONE);
		text_yijihuanzhe.setVisibility(View.GONE);
		text_erjihuanzhe.setVisibility(View.GONE);
	}

	// 打开左侧用户信息MENU
	public void openUserInfoMenu(View v) {
		is_onclick = true;
		// if (state) {
		// menu.toggle();
		// } else {
		// menu.showMenu(true);
		// }
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			if (left_state) {
				left_content.setVisibility(View.VISIBLE);
				user_info.setVisibility(View.GONE);
				left_state = false;

			} else {
				left_content.setVisibility(View.GONE);
				user_info.setVisibility(View.VISIBLE);
				left_state = true;
			}
		} else {
			if (left_state) {
				menu.toggle();
				left_state = false;
			} else {
				menu.showMenu();
				left_state = true;
			}
		}
		setUserInfoData();
	}

	// 打开左侧用户信息
	public void openLeftUserInfo(View v) {
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			if (left_user_info) {
				ll_left_window.setVisibility(View.GONE);
				left_user_info = false;

			} else {
				ll_left_window.setVisibility(View.VISIBLE);
				left_content.setVisibility(View.GONE);
				user_info.setVisibility(View.VISIBLE);
				left_user_info = true;

			}
			left_feature_menu = false;
		} else {
			if (left_user_info) {
				menu.toggle();
				left_user_info = false;
			} else {
				menu.showMenu();
				left_content.setVisibility(View.GONE);
				user_info.setVisibility(View.VISIBLE);
				left_user_info = true;
			}
			left_feature_menu = false;
		}
		setUserInfoData();
	}

	// 打开左侧功能菜单
	public void openLeftFeatureMenu(View v) {
		if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			if (left_feature_menu) {
				ll_left_window.setVisibility(View.GONE);
				left_feature_menu = false;

			} else {
				ll_left_window.setVisibility(View.VISIBLE);
				left_content.setVisibility(View.VISIBLE);
				user_info.setVisibility(View.GONE);
				left_feature_menu = true;
			}
			left_user_info = false;
		} else {
			if (left_feature_menu) {
				menu.toggle();
				left_feature_menu = false;
			} else {
				menu.showMenu();
				left_content.setVisibility(View.VISIBLE);
				user_info.setVisibility(View.GONE);
				left_feature_menu = true;
			}
			left_user_info = false;
		}
		// setUserInfoData();
	}

	// 打开右侧患者列表
	public void openHuanzheList(View v) {
		//int size = listdata.size();
		if (right_state) {
			menu.showContent();
			right_state = false;
		} else {
			right_content_state = 1;
			switchRightContent();
			menu.showSecondaryMenu();
			// 增加by Hooke@2015-8-25 切换至选中的患者

			qiehuanXuanzhongPosition();
			// 结束by Hooke@2015-8-25

			right_state = true;
		}

	}

	/**
	 * @Title: switchRightContent
	 * @Description: TODO
	 * @author: Huke <Huke@tiantanhehe.com>
	 * @date: 2016年4月18日 下午12:01:47
	 */
	public void switchRightContent() {

		ll_huanzhe_list_content.setVisibility(View.GONE);
		ll_multimedia_list_content.setVisibility(View.GONE);
		ll_xiaobianque_content.setVisibility(View.GONE);

		switch (right_content_state) {
		case 1:

			ll_huanzhe_list_content.setVisibility(View.VISIBLE);
			if (RunTimeState.getInstance().getXuanzhongFenzu() < 1
					&& listdata.size() < 1) {
				if (current_application.appConf.current_user_suoshu_group
						.equals("基层康复诊疗组")) {
					getKangfuHuanzhe(view);
				} else {
					getAllHuanzhe();
				}
			}
			break;
		case 2:
			ll_multimedia_list_content.setVisibility(View.VISIBLE);
			break;
		case 3:
			ll_xiaobianque_content.setVisibility(View.VISIBLE);
			break;
		default:
			break;
		}
	}

	public void getAllHuanzhe() {
		// if (all_but != null && kangfu_zhiliao_but != null) {
		// all_but.setBackgroundColor(getResources().getColor(
		// R.color.qianshenlanse));
		// kangfu_zhiliao_but.setBackgroundColor(getResources().getColor(
		// R.color.qianlanse));
		// }
		right_content.setVisibility(View.VISIBLE);
		right_huanzhe_list.setVisibility(View.GONE);
		getRightHuanzheData("");
		text_zongrenshu.setVisibility(View.GONE);
		text_teshuhuanze.setVisibility(View.GONE);
		text_yijihuanzhe.setVisibility(View.GONE);
		text_erjihuanzhe.setVisibility(View.GONE);
	}

	public void getKangfuHuanzhe(View v) {
		if (all_but != null && kangfu_zhiliao_but != null) {
			all_but.setBackgroundColor(getResources().getColor(
					R.color.qianlanse));
			kangfu_zhiliao_but.setBackgroundColor(getResources().getColor(
					R.color.qianshenlanse));
		}
		getRightHuanzheData("康复治疗");
		right_content.setVisibility(View.VISIBLE);
		right_huanzhe_list.setVisibility(View.GONE);
		text_zongrenshu.setVisibility(View.GONE);
		text_teshuhuanze.setVisibility(View.GONE);
		text_yijihuanzhe.setVisibility(View.GONE);
		text_erjihuanzhe.setVisibility(View.GONE);
	}

	public void getAllHuanzhe(View v) {
		// if (all_but != null && kangfu_zhiliao_but != null) {
		// all_but.setBackgroundColor(getResources().getColor(
		// R.color.qianshenlanse));
		// kangfu_zhiliao_but.setBackgroundColor(getResources().getColor(
		// R.color.qianlanse));
		// }
		// 增加by Hooke@2015-8-25
		RunTimeState.getInstance().setXuanzhongFenzu(0);
		// 结束by Hooke@2015-8-25
		right_content.setVisibility(View.VISIBLE);
		right_huanzhe_list.setVisibility(View.GONE);
		getRightHuanzheData("");
	}

	public void getHuanzheFenzu(View v) {
		String[] fenzu = null;
		if (v.getId() == R.id.fenzu_one_but) {
			fenzu = new String[] { "第一小组", "第二小组", "第三小组" };
			// 增加by Hooke@2015-8-25
			RunTimeState.getInstance().setXuanzhongFenzu(1);
			// 结束by Hooke@2015-8-25
			yingcangkongjian();
		} else if (v.getId() == R.id.fenzu_two_but) {
			fenzu = new String[] { "第四小组", "第五小组", "第六小组" };
			// 增加by Hooke@2015-8-25
			RunTimeState.getInstance().setXuanzhongFenzu(2);
			// 结束by Hooke@2015-8-25
			yingcangkongjian();
		}

		right_content.setVisibility(View.GONE);
		right_huanzhe_list.setVisibility(View.VISIBLE);
		groups = new ArrayList<Map<String, String>>();
		childs = new ArrayList<List<Map<String, Object>>>();
		for (int i = 0; i < fenzu.length; i++) {
			String chuangweisql = "SELECT * FROM yiyuan_bingchuanghao where bingchuang_fenzu = '"
					+ fenzu[i] + "' ORDER BY CAST(bingchuang_hao AS INT) ASC ";
			Cursor chuangweiData = this.db.rawQuery(chuangweisql,
					new String[] {});
			List<String> chuanghao_list = new ArrayList<String>();
			while (chuangweiData.moveToNext()) {
				chuanghao_list.add(chuangweiData.getString(chuangweiData
						.getColumnIndex("bingchuang_hao")));
			}
			chuangweiData.close();
			List<HuanzheWrapper> huanzhe_list = mZhuyuanHuanzheDao
					.getHuanzheListByChuanghao(chuanghao_list);

			int xiaoxi_number = 0;
			Map<String, String> group = new HashMap<String, String>();
			group.put("fenzu_name", fenzu[i]);
			group.put("number", Integer.toString(huanzhe_list.size()));
			List<Map<String, Object>> child = new ArrayList<Map<String, Object>>();
			for (HuanzheWrapper huanzhe : huanzhe_list) {
				int temp = Integer.parseInt(getTixingNumber(huanzhe
						.getZhuyuan_id()));
				xiaoxi_number = xiaoxi_number + temp;
				Map<String, Object> map = huanzhe.transToMap();
				map.put("xiaoxi_number", temp + "");
				// map.put("xiaoxi_number", );

				// 增加by Hooke@2015-8-25
				if (current_application.appConf.current_patient_id
						.equals(huanzhe.getPatient_id())) {
					RunTimeState.getInstance().setFenzhuGroup(groups.size());
					RunTimeState.getInstance().setFenzhuChild(child.size());
				}
				// 结束by Hooke@2015-8-25
				child.add(map);
				//
			}

			group.put("xiaoxi_number", xiaoxi_number + "");
			groups.add(group);
			childs.add(child);
		}

		// 修改by Hooke@2015-8-26
		fenzuAdapter = new FenzuListAdapter(this.context, groups, childs);
		right_huanzhe_list.setAdapter(fenzuAdapter);
		// 结束by Hooke@2015-8-26

		right_huanzhe_list.expandGroup(0);
		right_huanzhe_list.setGroupIndicator(null);
		right_huanzhe_list.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView arg0, View arg1,
					int arg2, int arg3, long arg4) {
				// arg1.setBackgroundColor(getResources().getColor(R.color.huise));
				current_application.appConf.current_patient_id = childs
						.get(arg2).get(arg3).get("patient_id").toString();
				current_application.appConf.current_patient_zhuyuan_id = childs
						.get(arg2).get(arg3).get("zhuyuan_id").toString();
				current_application.appConf.current_patient_zhuyuan_bingqu = childs
						.get(arg2).get(arg3).get("zhuyuan_bingqu").toString();
				current_application.appConf.current_patient_bingchuang_hao = childs
						.get(arg2).get(arg3).get("bingchuang_hao").toString();
				current_application.appConf.current_patient_xingming = childs
						.get(arg2).get(arg3).get("xingming").toString();
				current_application.appConf.current_patient_xingbie = childs
						.get(arg2).get(arg3).get("xingbie").toString();
				current_application.appConf.current_patient_nianling = childs
						.get(arg2).get(arg3).get("nianling").toString();
				current_application.appConf.current_patient_huli_jibie = childs
						.get(arg2).get(arg3).get("hulijibie").toString();
				current_application.appConf.current_patient_zhenduan = childs
						.get(arg2).get(arg3).get("zhenduan").toString();
				menu.showContent(); // 关闭右侧菜单
				right_state = false;
				if (tiaoZhuangActivity == MainActivity.class) {
					// 设置主界面顶端患者信息
					setDingbuHuanZheXinXi();
				}

				if (!isZhujiemian) {
					goTiaozhuan();
				}

				// 增加by Hooke@2015-8-25 保存选取的Item位置
				RunTimeState.getInstance().setXuanzhongFenzu(1);
				RunTimeState.getInstance().setFenzhuGroup(arg2);
				RunTimeState.getInstance().setFenzhuChild(arg3);

				// 结束by Hooke@2015-8-25
				return false;
			}
		});
		// 增加by Hooke@2015-8-25
		qiehuanXuanzhongPosition();
		// 结束by Hooke@2015-8-25
	}

	public void getOneHuanzhe(View v) {
		EditText sousuocontent = (EditText) findViewById(R.id.sousuocontent);
		getRightHuanzheData(sousuocontent.getText().toString());
		if (current_application.appConf.current_user_suoshu_group
				.equals("基层康复诊疗组")) {
			right_content.setVisibility(View.VISIBLE);
			right_huanzhe_list.setVisibility(View.GONE);
			text_zongrenshu.setVisibility(View.GONE);
			text_teshuhuanze.setVisibility(View.GONE);
			text_yijihuanzhe.setVisibility(View.GONE);
			text_erjihuanzhe.setVisibility(View.GONE);
		}

	}

	public void getRightTeshuHuanzheData(String string) {

		if (string.equals("总患者")) {
			getRightHuanzheData("");

			return;
		}

		List<HuanzheWrapper> fenzu_list = mZhuyuanHuanzheDao
				.getYizhuBySqlCondition(" and hulijibie='" + string + "'");
		if (fenzu_list.isEmpty()) {
			tipDialog(YiDongYiHuActivity.this, "提示", "没有当前等级的患者");
			return;
		}

		if (listdata != null) {
			listdata.clear();
		}

		right_content.setVisibility(View.VISIBLE);
		right_huanzhe_list.setVisibility(View.GONE);

		// 巡视结果(获取一天内的巡查次数)
		String str = new SimpleDateFormat("yyyy年MM月dd日 HH:mm")
				.format(new java.util.Date());

		str = str.replace("年", "-");
		str = str.replace("月", "-");
		str = str.replace("日", "");

		String xunshiLishi_sql = "SELECT aa.zhuyuan_id,count(DISTINCT aa.id) as xiaoxi_number FROM (SELECT DISTINCT a.zhuyuan_id as zhuyuan_id,a.id FROM zhuyuan_xuncha_jilu_lishi a,zhuyuan_xuncha_jilu_detail_lishi b WHERE b.xuncha_item_type = '巡查事件' AND b.xuncha_jilu_id = a.id AND a.xuncha_time >= '"
				+ str
				+ "'AND a.xuncha_time < '"
				+ str
				+ "23:59' UNION all SELECT DISTINCT c.zhuyuan_id as zhuyuan_id,c.id FROM zhuyuan_xuncha_jilu c,zhuyuan_xuncha_jilu_detail d WHERE d.xuncha_item_type = '巡查事件' AND d.xuncha_jilu_id = c.id AND c.xuncha_time >= '"
				+ str
				+ "'AND c.xuncha_time < '"
				+ str
				+ "23:59')aa GROUP BY aa. zhuyuan_id";
		Cursor xunshiCursor = this.db
				.rawQuery(xunshiLishi_sql, new String[] {});
		Map<String, Object> xunshiMap = new HashMap<String, Object>();
		while (xunshiCursor.moveToNext()) {
			xunshiMap.put(xunshiCursor.getString(xunshiCursor
					.getColumnIndex("zhuyuan_id")), xunshiCursor
					.getString(xunshiCursor.getColumnIndex("xiaoxi_number")));
		}
		xunshiCursor.close();

		Map<String, Object> tizhengMap = new HashMap<String, Object>();
		Map<String, Object> zuigaoTiwenMap = new HashMap<String, Object>();

		String biaoben_sql = "SELECT zhuyuan_id,count(distinct tiaoma) as xiaoxi_number FROM zhuyuan_fuzhujiancha WHERE jiancha_zhuangtai = '未核对' group by zhuyuan_id";
		Cursor biaobenSqlData = this.db.rawQuery(biaoben_sql, new String[] {});
		Map<String, Object> biaobenValueMap = new HashMap<String, Object>();
		if (biaobenSqlData != null && biaobenSqlData.getCount() > 0) {
			while (biaobenSqlData.moveToNext()) {
				biaobenValueMap.put(biaobenSqlData.getString(biaobenSqlData
						.getColumnIndex("zhuyuan_id")), biaobenSqlData
						.getString(biaobenSqlData
								.getColumnIndex("xiaoxi_number")));
			}
		}
		biaobenSqlData.close();
		for (HuanzheWrapper huanzhe : fenzu_list) {
			Map<String, Object> valueMap = huanzhe.transToMap();

			int tixingNumber = 0;
			String temp_zhuyuan_id = huanzhe.getZhuyuan_id().toString();
			int temp_yizhu_number = 0;
			int temp_biaoben_number = 0;
			// 巡视次数
			int temp_xunshi_number = 0;

			String tiwen_fenji_name = "";
			String tiwen_luru_number = "";
			String tiwen_xianshi_yanse = "";

			try {
				temp_biaoben_number = Integer.parseInt(biaobenValueMap.get(
						temp_zhuyuan_id).toString());

			} catch (Throwable e) {

			}
			// 得到巡视次数
			try {
				temp_xunshi_number = Integer.parseInt(xunshiMap.get(
						temp_zhuyuan_id).toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
			// if ("".equals(huanzhe.getYizhu_count())) {
			// tixingNumber = temp_biaoben_number;
			// } else {
			// tixingNumber = Integer.parseInt(huanzhe.getYizhu_count())
			// + temp_biaoben_number;
			// }

			valueMap.put("xiaoxi_number", tixingNumber);
			// valueMap.put("tiwen_fenji_name", tiwen_fenji_name);
			// valueMap.put("tiwen_luru_number", tiwen_luru_number);
			// valueMap.put("tiwen_xianshi_yanse", tiwen_xianshi_yanse);

			if (current_application.appConf.current_patient_id.equals(huanzhe
					.getPatient_id())) {
				RunTimeState.getInstance().setFenzhuChild(listdata.size());
			}

			listdata.add(valueMap);
			if (huanzheAdapter != null) {
				huanzheAdapter.notifyDataSetChanged();
			}
		}

		right_content.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				JSONObject curJsonObject = new JSONObject(listdata.get(arg2));

				String[] patientParams = { "patient_id", "zhuyuan_id",
						"zhuyuan_bingqu", "bingchuang_hao", "xingming",
						"xingbie", "nianling", "hulijibie", "zhenduan" };

				current_application.setConfValue(curJsonObject, patientParams);

				// 判断开关，true为开启语音提示功能，false为关闭语音提示功能
				if (!current_application.appConf.yuyin_tishi) {
					// 默认值为false 语音提示功能关闭
				} else {
					// true 语音提示功能开启
					// 讯飞语音
					SpeechApp.getInstance(YiDongYiHuActivity.this).speak(
							listdata.get(arg2).get("xingming").toString());
				}
				menu.showContent(); // 关闭右侧菜单
				right_state = false;
				if (tiaoZhuangActivity == MainActivity.class) {
					// 设置主界面顶端患者信息
					setDingbuHuanZheXinXi();
				}

				if (!isZhujiemian) {
					goTiaozhuan();
				}

				RunTimeState.getInstance().setXuanzhongFenzu(0);
				RunTimeState.getInstance().setFenzhuChild(arg2);
			}
		});
		qiehuanXuanzhongPosition();

	}

	// 用来获取当前等级患者的人数。
	public String huoQuJiBieRenShu(String jibie) {
		int result = 0;
		if (jibie.equals("总患者")) {
			result = zongrenshu;
		}
		if (jibie.equals("特级护理")) {
			result = teshu;
		}
		if (jibie.equals("一级护理")) {
			result = yiji;
		}
		if (jibie.equals("二级护理")) {
			result = erji;
		}
		if (jibie.equals("三级护理")) {
			result = sanji;
		}
		if (jibie.equals("其他")) {
			result = qita;
		}

		if (result < 10) {
			return " " + result;
		} else {
			return "" + result;
		}

	}

	// 以搜索方式，获取右侧患者列表
	public void getRightHuanzheData(String guanjianci) {
		teshu = 0;
		yiji = 0;
		erji = 0;
		sanji = 0;
		List<HuanzheWrapper> huanzhe_list = null;
		if (guanjianci.equals("")) {
			huanzhe_list = mZhuyuanHuanzheDao.getAllHuanzheList();
		} else {
			// 1. 优先以床位号完成匹配
			// 首先精确查找
			List<HuanzheWrapper> oneHuanzhe = mZhuyuanHuanzheDao
					.getOneHuanzheByChuanghao(guanjianci);
			if (oneHuanzhe == null || oneHuanzhe.isEmpty()) {
				// 若未找到再进行模糊查找
				List<HuanzheWrapper> res = mZhuyuanHuanzheDao
						.getHuanzheByChuanghao(guanjianci);
				if (res == null || res.isEmpty()) {
					huanzhe_list = mZhuyuanHuanzheDao
							.getHuanzheListByKeyWord(guanjianci);
				} else {
					huanzhe_list = res;
				}
			} else {
				huanzhe_list = oneHuanzhe;
			}
		}
		if (huanzhe_list.isEmpty() && guanjianci.equals("")) {
			return;
		}
		else if (huanzhe_list.isEmpty()) {
			tipDialog(YiDongYiHuActivity.this, "提示", "该患者不存在");
			return;
		}
		//
		if (listdata != null) {
			listdata.clear();
		}
		if (huanzheAdapter != null) {
			huanzheAdapter.notifyDataSetChanged();
		}
		right_content.setVisibility(View.VISIBLE);
		right_huanzhe_list.setVisibility(View.GONE);
		String yizhu_sql = "SELECT yizhu_id,zhuyuan_id,count(distinct zuhao) as xiaoxi_number FROM yizhu_info WHERE (zhixing_state = '待配液' or zhixing_state = '已配液' or zhixing_state = '已校对' or zhixing_state = '开始执行') and meiri_cishu > wancheng_cishu and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-"
				+ current_application.featureConf.linshi_yizhu_xianshi
				+ " Hour', 'localtime'))) GROUP BY zhuyuan_id";
		Cursor yizhuSqlData = this.db.rawQuery(yizhu_sql, new String[] {});
		Map<String, Object> yizhuValueMap = new HashMap<String, Object>();
		while (yizhuSqlData.moveToNext()) {
			yizhuValueMap.put(yizhuSqlData.getString(yizhuSqlData
					.getColumnIndex("zhuyuan_id")), yizhuSqlData
					.getString(yizhuSqlData.getColumnIndex("xiaoxi_number")));
		}
		yizhuSqlData.close();
		String biaoben_sql = "SELECT zhuyuan_id,count(distinct tiaoma) as xiaoxi_number FROM zhuyuan_fuzhujiancha WHERE jiancha_zhuangtai = '未核对' group by zhuyuan_id";
		Cursor biaobenSqlData = this.db.rawQuery(biaoben_sql, new String[] {});
		Map<String, Object> biaobenValueMap = new HashMap<String, Object>();
		while (biaobenSqlData.moveToNext()) {
			biaobenValueMap.put(biaobenSqlData.getString(biaobenSqlData
					.getColumnIndex("zhuyuan_id")), biaobenSqlData
					.getString(biaobenSqlData.getColumnIndex("xiaoxi_number")));
		}
		biaobenSqlData.close();
		for (HuanzheWrapper huanzhe : huanzhe_list) {
			Map<String, Object> valueMap = huanzhe.transToMap();
			/*
			 * valueMap.put("xiaoxi_number",
			 * getTixingNumber(huanzhe.getZhuyuan_id()));
			 */

			int tixingNumber = 0;
			String temp_zhuyuan_id = huanzhe.getZhuyuan_id().toString();
			int temp_yizhu_number = 0;
			int temp_biaoben_number = 0;
			try {
				temp_yizhu_number = Integer.parseInt(yizhuValueMap.get(
						temp_zhuyuan_id).toString());
				temp_biaoben_number = Integer.parseInt(biaobenValueMap.get(
						temp_zhuyuan_id).toString());
				// tixingNumber =
				// Integer.parseInt(yizhuValueMap.get(temp_zhuyuan_id).toString())
				// +
				// Integer.parseInt(biaobenValueMap.get(temp_zhuyuan_id).toString());
			} catch (Throwable e) {

			}
			tixingNumber = temp_yizhu_number + temp_biaoben_number;
			valueMap.put("xiaoxi_number", tixingNumber);
			// 增加by Hooke@2015-8-25
			if (current_application.appConf.current_patient_id.equals(huanzhe
					.getPatient_id())) {
				RunTimeState.getInstance().setFenzhuChild(listdata.size());
			}
			// 结束by Hooke@2015-8-25

			listdata.add(valueMap);
			if (huanzheAdapter != null) {
				huanzheAdapter.notifyDataSetChanged();
			}
			// 通过HuanzheWrapper来取出护理级别的值，通过判断，有对应的值就加一，
			if (huanzhe.getHulijibie().equals("特级护理")) {
				teshu++;
			} else if (huanzhe.getHulijibie().equals("一级护理")) {
				yiji++;
			} else if (huanzhe.getHulijibie().equals("二级护理")) {
				erji++;
			} else if (huanzhe.getHulijibie().equals("三级护理")) {
				sanji++;
			} else if (huanzhe.getHulijibie().equals("其他")) {
				qita++;
			}
		}

		zongrenshu = listdata.size();
		huanzheAdapter = new HuanzheListAdapter(this, listdata,
				current_application.appConf.current_user_suoshu_group);
		right_content.setAdapter(huanzheAdapter);
		// if (listdata != null) {
		// if (listdata.size() > 0) {
		// text_zongrenshu.setVisibility(View.VISIBLE);
		// text_zongrenshu.setText(getString(R.string.zonghuanzhe)
		// + listdata.size() + getString(R.string.ren));//
		// 总人数是通过，listdata中元素的个数来当做总人数。
		// }
		// }
		// // 点击“全部”按钮，显示控件，并赋值。
		// if (text_teshuhuanze != null) {
		// text_teshuhuanze.setVisibility(View.VISIBLE);
		// text_teshuhuanze.setText(getString(R.string.tejihuanzhe) + teshu
		// + getString(R.string.ren));
		// }
		// if (text_yijihuanzhe != null) {
		// text_yijihuanzhe.setVisibility(View.VISIBLE);
		// text_yijihuanzhe.setText(getString(R.string.yijihuanzhe) + yiji
		// + getString(R.string.ren));
		// }
		// if (text_erjihuanzhe != null) {
		// text_erjihuanzhe.setVisibility(View.VISIBLE);
		// text_erjihuanzhe.setText(getString(R.string.erjihuanzhe) + erji
		// + getString(R.string.ren));
		// }
		right_content.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				JSONObject curJsonObject = new JSONObject(listdata.get(arg2));

				String[] patientParams = { "patient_id", "zhuyuan_id", "zhuyuan_id_show", "zhuyuan_bingqu", "bingchuang_hao", "xingming",
						"xingbie", "nianling", "hulijibie", "zhenduan" };

				current_application.setConfValue(curJsonObject, patientParams);

				/**
				 * 2016-03-01 Benny 新加语音功能
				 */
				// 判断开关，true为开启语音提示功能，false为关闭语音提示功能
				if (!current_application.appConf.yuyin_tishi) {
					// 默认值为false 语音提示功能关闭
				} else {
					// true 语音提示功能开启
					// 讯飞语音
					SpeechApp.getInstance(YiDongYiHuActivity.this).speak(listdata.get(arg2).get("xingming").toString());
				}
				menu.showContent(); // 关闭右侧菜单
				right_state = false;
				if (tiaoZhuangActivity == MainActivity.class) {
					// 设置主界面顶端患者信息
					setDingbuHuanZheXinXi();
				}

				if (!isZhujiemian) {
					goTiaozhuan();
				}

				// 增加by Hooke@2015-8-25 保存选取的Item位置
				RunTimeState.getInstance().setXuanzhongFenzu(0);
				RunTimeState.getInstance().setFenzhuChild(arg2);
				
				if (!current_application.appConf.dangqian_mokuai.equals("")) {
					contentWebView.setVisibility(View.GONE);
					contentView.setVisibility(View.VISIBLE);
					if(current_application.appConf.dangqian_mokuai.equals(getString(R.string.chakanhuanzhe)))
					{
						setNewFragment(getString(R.string.zhuyuanzonglan));
					}
					else
					{
						setNewFragment(current_application.appConf.dangqian_mokuai);
					}
				}

				// 结束by Hooke@2015-8-25
			}
		});

		// 增加by Hooke@2015-8-25 切换至选中的患者
		qiehuanXuanzhongPosition();
		// 结束by Hooke@2015-8-25
	}

	// 获取代办事项次数
	public String getTixingNumber(String zhuyuanId) {
		int tixing = 0;
		// 查询医嘱剩余次数
		String yizhu_sql = "SELECT yizhu_id FROM yizhu_info WHERE zhuyuan_id = '"
				+ zhuyuanId
				+ "' and (zhixing_state = '待配液' or zhixing_state = '开始执行') and meiri_cishu > wancheng_cishu and (yizhu_type = '长期' or (yizhu_type = '临时' and start_time > strftime('%Y-%m-%d %H:%M:%S','now', '-"
				+ current_application.featureConf.linshi_yizhu_xianshi
				+ " Hour', 'localtime'))) GROUP BY zuhao ";
		Cursor yizhuData = this.db.rawQuery(yizhu_sql, new String[] {});
		tixing = yizhuData.getCount();
		yizhuData.close();
		// 查询未核对标本剩余数
		String biaoben_sql = "SELECT * FROM zhuyuan_fuzhujiancha WHERE zhuyuan_id = '"
				+ zhuyuanId
				+ "' and jiancha_zhuangtai = '未核对' group by tiaoma ";
		Cursor biaobenData = this.db.rawQuery(biaoben_sql, new String[] {});
		tixing = tixing + biaobenData.getCount();
		biaobenData.close();
		return tixing + "";
	}

	// 增加by Hooke@2015-8-25 切换至选中的患者方法
	public void qiehuanXuanzhongPosition() {

		if (huanzheAdapter != null) {
			huanzheAdapter.notifyDataSetChanged();
		}

		if (fenzuAdapter != null) {
			fenzuAdapter.notifyDataSetChanged();
		}

		switch (RunTimeState.getInstance().getXuanzhongFenzu()) {
		case 0:
			if (right_content.getAdapter() == null) {
				getAllHuanzhe();
			}

			right_content.setVisibility(View.VISIBLE);
			right_huanzhe_list.setVisibility(View.GONE);
			if (RunTimeState.getInstance().getFenzhuChild() >= 0
					&& right_content != null) {
				if (RunTimeState.getInstance().getFenzhuChild() >= 2) {
					right_content.setSelection(RunTimeState.getInstance()
							.getFenzhuChild() - 2);
				} else {
					right_content.setSelection(RunTimeState.getInstance()
							.getFenzhuChild());
				}
			}
			break;
		case 1:
			if (right_huanzhe_list.getAdapter() == null) {
				getHuanzheFenzu(findViewById(R.id.fenzu_one_but));
			}

		case 2:
			if (right_huanzhe_list.getAdapter() == null) {
				getHuanzheFenzu(findViewById(R.id.fenzu_two_but));
			}

			right_content.setVisibility(View.GONE);
			right_huanzhe_list.setVisibility(View.VISIBLE);
			if (RunTimeState.getInstance().getFenzhuGroup() >= 0
					&& RunTimeState.getInstance().getFenzhuChild() >= 0
					&& right_huanzhe_list != null) {
				right_huanzhe_list.expandGroup(RunTimeState.getInstance()
						.getFenzhuGroup());
				if (RunTimeState.getInstance().getFenzhuChild() >= 2) {
					right_huanzhe_list.setSelectedChild(RunTimeState
							.getInstance().getFenzhuGroup(), RunTimeState
							.getInstance().getFenzhuChild() - 2, true);
				} else {
					right_huanzhe_list.setSelectedChild(RunTimeState
							.getInstance().getFenzhuGroup(), RunTimeState
							.getInstance().getFenzhuChild(), true);
				}

			}
			break;
		default:

		}

		// 护士已经扫描患者床头卡
		current_application.appConf.huanzhe_yijing_saomiaochuangtouka = false;
	}



	// 结束by Hooke@2015-8-25

	public void goTiaozhuan() {
		Intent intent = new Intent(this, tiaoZhuangActivity);
		if (this instanceof YiDongYiHuBrowserActivity) {
			// 记录所浏览的页面
			// SharedPreferences preferences =
			// getSharedPreferences("yidongyihubrowser", Activity.MODE_PRIVATE);
			//
			// if (mIntent.getStringExtra("loadUrl") != null) {
			// SharedPreferences.Editor editor = preferences.edit();
			// editor.putString("request_url",
			// mIntent.getStringExtra("loadUrl"));
			// editor.commit();
			// }
			setDingbuHuanZheXinXi();
			if (contentWebView != null) {
				loadMokuai(getString(R.string.shijianshitu));
			}
		} else {

			this.setContentView(R.layout.activity_blank_view);
			this.finish();
			startActivity(intent);
		}
	}

	// 返回主界面
	public void goMain(View v) {

		Intent intent = new Intent(this, MainActivity.class);
		// 用来判断mainactivity中开屏事件是否是从其他页面跳转触发的
		intent.putExtra("jumpFrom", "activity");
		finish();
		startActivity(intent);
		overridePendingTransition(android.R.anim.slide_in_left,
				android.R.anim.slide_out_right);
	}

	public void goMain(String mokuai) {

		Intent intent = new Intent(this, MainActivity.class);
		finish();
		startActivity(intent);
	}

	public void loadBingquganlan(View v) {
		// loadMokuai("病区概览");
		if (contentWebView != null) {
			contentWebView.setVisibility(View.GONE);
			contentView.setVisibility(View.VISIBLE);
			// setFragment(contentView, 0);
			setNewFragment(getString(R.string.chakanhuanzhe));
		}
	}

	/**
	 * @Title: loadMokuai
	 * @Description: 载入模块内容
	 * @author: Huke <Huke@tiantanhehe.com>
	 * @date: 2016年4月19日 下午4:43:55
	 * @param string
	 */
	protected void loadMokuai(String mokuaiName) {
		current_application.appConf.current_mokuai = mokuaiName;
		current_application.appConf.current_menu = mokuaiName;
		if (lv_left_menu != null) {
			LeftMenuListAdapter adapter = (LeftMenuListAdapter) lv_left_menu
					.getAdapter();
			adapter.notifyDataSetChanged();
		}

		String url = "";
		reflesh = false;
		// if (toast != null) {
		// PersistToast.hide(toast);
		// }

		if (mokuaiName.equals(getString(R.string.chakanhuanzhe))) {// "查看患者"

			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/Patient/showPatientListPad/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.chafangoverview))
				|| mokuaiName.equals("综合查房视图")) {// "查房概览"
			// url = current_application.appConf.server_url
			// + "ZhuyuanYishi/Patient/showPatientZongheChafang/zhuyuan_id/";
			jumpToMokuai("查房概要");
			return;
		} else if (mokuaiName.equals(getString(R.string.chakanyizhu))) {// "查看医嘱"
			url = current_application.appConf.server_url
					+ "Common/Yizhuguanli/showChangqi/state/开始执行/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.bingchengjilu))) {// "病程记录"
			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/BingchengJilu/showView/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.jianyanjiancha))) {// "检验检查"
			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/Jiancha/showList/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.tiwenjiludan))) {// "三测单"
			url = current_application.appConf.server_url
					+ "Common/TiwenJiludan/showList/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.shijianshitu))) {// "时间视图"
			url = current_application.appConf.server_url
					+ "Common/Shijianzhou/showShijianShitu/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.chafangyindao))) {// "查房引导"
			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/Patient/chafangYindao/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.zhenduanmenu))) {// "诊断"
			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/Zhenduan/showRuyuanZhenduan/zhixing_type/住院/zhixing_id/";
		} else if (mokuaiName.equals("住院总览")) {// "住院信息总览"
			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/Patient/showPatientZhuyuanDetailPad/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.hulijilu))) {// "护理记录"
			url = current_application.appConf.server_url
					+ "HuliJilu/showList/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.binglijilu))) {// "病历记录"
			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/Bingli/bingchengJiluShowList/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.bingquganlan))) {// "病区概览"
			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/Patient/showPatientListPad/";
		} else if (mokuaiName.equals(getString(R.string.binglibianji))) {// "病区编辑"
			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/BingchengJilu/showView/zhuyuan_id/";
		} else if (mokuaiName.equals(getString(R.string.chafanggensui))) {// "查房演示"
			url = current_application.appConf.server_url
					+ "ZhuyuanYishi/ZhuyuanXiezuo/showXiezuoDefault/";
			reflesh = true;
			// toast = PersistToast.build(this, "您目前处于查房同步模式");
			// Toast.makeText(this, "您目前处于查房同步模式", Toast.LENGTH_LONG).show();
			// PersistToast.show(toast);
		}

		// 保存访问的页面信息
		SharedPreferencesUtils.setWebviewUrl(context, "yidongyihubrowser", url);

		loadWebview(url);
		// setContentInitialScale(url);
		guanbiLeftRightMenu();

	}

	protected void loadWebview(String url) {
		// CookieHelper.getInstance(context).syncUrlCookies(url);

		CookieHelper.getInstance(context).setUrlCookies(url);

		if (contentWebView != null) {
			contentWebView.loadUrl(url
					+ current_application.appConf.current_patient_zhuyuan_id);
		}

		current_application.featureConf.now_url = url
				+ current_application.appConf.current_patient_zhuyuan_id;
	}

	protected void jumpToMokuai(String mokuaiName) {
		current_application.appConf.current_mokuai = mokuaiName;
		current_application.appConf.current_menu = mokuaiName;
		if (lv_left_menu != null) {
			LeftMenuListAdapter adapter = (LeftMenuListAdapter) lv_left_menu
					.getAdapter();
			adapter.notifyDataSetChanged();
		}

		if (mokuaiName.equals(getString(R.string.chakanhuanzhe))) {// "查看患者"
			goChakanhuanzhe(view);
		} else if (mokuaiName.equals(getString(R.string.chafangoverview))
				|| mokuaiName.equals("综合查房视图")) {// "查房概览"
			goChafangOverview(view);
		} else if (mokuaiName.equals(getString(R.string.chakanyizhu))) {// "查看医嘱"
			goYizhuChakan(view);
		} else if (mokuaiName.equals(getString(R.string.binglijilu))) {// "病程记录"
			goBinglijilu(view);
		} else if (mokuaiName.equals(getString(R.string.jianyanjiancha))) {// "检验检查"
			goJianyanjiancha(view);
		} else if (mokuaiName.equals(getString(R.string.tiwenjiludan))) {// "三测单"
			goTiwenjiludan(view);
		} else if (mokuaiName.equals(getString(R.string.shijianshitu))) {// "时间视图"
			goShijianShitu(view);
		} else if (mokuaiName.equals(getString(R.string.chafangyindao))) {// "查房引导"
			goChafangYindao(view);
		} else if (mokuaiName.equals(getString(R.string.zhenduanmenu))) {// "诊断"
			goZhenduan(view);
		} else if (mokuaiName.equals("住院总览")) {// "住院信息总览"
			goZhuyuanOverview(view);
		} else if (mokuaiName.equals(getString(R.string.hulijilu))) {// "护理记录"
			goHulijilu(view);
		} else if (mokuaiName.equals(getString(R.string.chafanggensui))) {// "护理记录"
			goChafangGensui(view);
		} else if (mokuaiName.equals(getString(R.string.yingyongjicheng))
				|| mokuaiName.equals(getString(R.string.jianchayingxiang))
				|| mokuaiName.equals(getString(R.string.baodaoxitong))
				|| mokuaiName.equals(getString(R.string.dianzibingli))) {// "应用集成"
			goRDP(mokuaiName);
		}

	}

	public void goChakanhuanzhe(View v) {
		Intent intent = new Intent(this, ChakanHuanzheActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "ZhuyuanYishi/Patient/showPatientListPad/zhuyuan_id/");
		startActivity(intent);
	}

	public void goChafangOverview(View v) {
		Intent intent = new Intent(this, ChafangOverviewActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "ZhuyuanYishi/Patient/showPatientZongheChafang/zhuyuan_id/");
		startActivity(intent);

	}

	public void goBingchengjilu(View v) {
		Intent intent = new Intent(this, BingChengJiluActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "ZhuyuanYishi/Bingli/bingchengJiluShowList/zhuyuan_id/");
		startActivity(intent);

	}

	public void goJianyanjiancha(View v) {
		Intent intent = new Intent(this, JianyanJianchaActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "ZhuyuanYishi/Jiancha/showList/zhuyuan_id/");
		startActivity(intent);
	}

	public void goYizhuChakan(View v) {
		Intent intent = new Intent(this, YizhuChakanActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "Common/Yizhuguanli/showChangqi/state/开始执行/zhuyuan_id/");
		intent.putExtra("zhuangtai", "长期");
		startActivity(intent);
	}

	public void goTiwenjiludan(View v) {
		Intent intent = new Intent(this, TiwenJiludanActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "Common/TiwenJiludan/showList/zhuyuan_id/");
		startActivity(intent);

	}

	public void goHulijilu(View v) {
		Intent intent = new Intent(this, TiwenJiludanActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "HuliJilu/showList/zhuyuan_id/");
		startActivity(intent);

	}

	public void goBinglijilu(View v) {
		Intent intent = new Intent(this, BingChengJiluActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "ZhuyuanYishi/Bingli/bingchengJiluShowList/zhuyuan_id/");
		startActivity(intent);

	}

	public void goBingqugailan(View v) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "ZhuyuanYishi/Patient/showPatientListPad/");
		startActivity(intent);

	}

	public void goZhuyuanOverview(View v) {
		Intent intent = new Intent(this, ZhuyuanOverviewActivity.class);
		intent.putExtra(
				"loadUrl",
				current_application.appConf.server_url
						+ "ZhuyuanYishi/Patient/showPatientZhuyuanDetailPad/zhuyuan_id/");
		startActivity(intent);

	}

	/**
	 * @Title: goShijianShitu
	 * @Description: TODO
	 * @author: Huke <Huke@tiantanhehe.com>
	 * @date: 2016年4月13日 下午7:58:26
	 * @param view2
	 */
	public void goShijianShitu(View v) {

		Intent intent = new Intent(this, ShijianShituActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "Common/Shijianzhou/showShijianShitu/zhuyuan_id/");
		startActivity(intent);

	}

	public void goChafangYindao(View v) {

		Intent intent = new Intent(this, ChafangYindaoActivity.class);
		intent.putExtra("loadUrl", current_application.appConf.server_url
				+ "ZhuyuanYishi/Patient/chafangYindao/zhuyuan_id/");
		startActivity(intent);

	}

	public void goZhenduan(View v) {

		Intent intent = new Intent(this, ZhenduanActivity.class);
		intent.putExtra(
				"loadUrl",
				current_application.appConf.server_url
						+ "ZhuyuanYishi/Zhenduan/showRuyuanZhenduan/zhixing_type/住院/zhixing_id/");
		startActivity(intent);

	}

	/**
	 * @Title: goXiezuo
	 * @Description: 跳转到区域协作界面并传递用户名密码相关参数
	 * @author: Huke <Huke@tiantanhehe.com>
	 * @date: 2016年6月15日 上午9:22:40
	 * @param v
	 */
	public void goXiezuo(View v) {
		Intent i = new Intent();
		i.setClassName("csdn.shimiso.eim",
				"csdn.shimiso.eim.activity.LoginActivity");
		Bundle bundle = new Bundle();
		// i.putExtras(bundle);
		bundle.putString("user_number",
				current_application.appConf.current_user_number);
		bundle.putString("user_password",
				current_application.appConf.current_user_password);
		bundle.putString("emr_server_ip", current_application.appConf.server_ip);
		bundle.putString("xmpp_server_ip",
				current_application.featureConf.xmpp_server_ip);
		bundle.putString("xmpp_server_port",
				current_application.featureConf.xmpp_server_port);
		bundle.putString("xmpp_server_name",
				current_application.featureConf.xmpp_server_name);

		i.putExtras(bundle);
		// ComponentName cn = new ComponentName("csdn.shimiso.eim",
		// "csdn.shimiso.eim.activity.LoginActivity");
		// i.setComponent(cn);
		try {
			startActivity(i);
		} catch (Exception e) {
			Toast.makeText(this, "未安装医信通应用，无法启动该模块", Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	/**
	 * @Title: goRDP
	 * @Description: 跳转至远程左面并传递相关参数
	 * @author: Huke <Huke@tiantanhehe.com>
	 * @date: 2016年6月15日 上午9:23:31
	 * @param v
	 */
	public void goRDP(String app) {
		final Intent i = new Intent(this,RdpMainActivity.class);
		//final Intent i = new Intent();
		//i.setClassName("com.tiantanhehe.tiantanrdp","com.tiantanhehe.tiantanrdp.MainActivity");

		final Bundle bundle = new Bundle();
		bundle.putBoolean("mask_switch",
				current_application.featureConf.rdp_mask_switch);
		bundle.putString("appName", "yidongchafang");
		bundle.putString("host", current_application.appConf.RDP_server_ip);
		bundle.putString("user_number",
				current_application.appConf.RDP_user_name);
		bundle.putString("user_password",
				current_application.appConf.RDP_password);
		bundle.putString("port", current_application.appConf.RDP_port);

		if (getString(R.string.yingyongjicheng).equals(app)) {
			bundle.putInt("resolution_width",
					current_application.featureConf.his_rdp_resolution_width);
			bundle.putInt("resolution_height",
					current_application.featureConf.his_rdp_resolution_height);
			bundle.putString("remoteapp_name",
					current_application.featureConf.his_remote_app_name);
			bundle.putString("remote_work_dir",
					current_application.featureConf.his_remote_work_dir);
			bundle.putSerializable(
					"rdp_macro_command",
					(Serializable) current_application
							.getRdpInitMacroCommand(current_application.featureConf.his_init_macro_command,"his"));

		} else if (getString(R.string.dianzibingli).equals(app)) {
			bundle.putInt("resolution_width",
					current_application.featureConf.emr_rdp_resolution_width);
			bundle.putInt("resolution_height",
					current_application.featureConf.emr_rdp_resolution_height);
			bundle.putString("remoteapp_name",
					current_application.featureConf.emr_remote_app_name);
			bundle.putSerializable(
					"rdp_macro_command",
					(Serializable) current_application
							.getRdpInitMacroCommand(current_application.featureConf.emr_init_macro_command,"emr"));

		} else if (getString(R.string.jianchayingxiang).equals(app)) {
			bundle.putInt("resolution_width",
					current_application.featureConf.pacs_rdp_resolution_width);
			bundle.putInt("resolution_height",
					current_application.featureConf.pacs_rdp_resolution_height);
			bundle.putString("remoteapp_name",
					current_application.featureConf.pacs_remote_app_name);
			bundle.putSerializable(
					"rdp_macro_command",
					(Serializable) current_application
							.getRdpInitMacroCommand(current_application.featureConf.pacs_init_macro_command,"pacs"));

		} else if (getString(R.string.baodaoxitong).equals(app)) {
			bundle.putInt("resolution_width",
					current_application.featureConf.bdxt_rdp_resolution_width);
			bundle.putInt("resolution_height",
					current_application.featureConf.bdxt_rdp_resolution_height);
			bundle.putString("remoteapp_name",
					current_application.featureConf.bdxt_remote_app_name);
			bundle.putSerializable(
					"rdp_macro_command",
					(Serializable) current_application
							.getRdpInitMacroCommand(current_application.featureConf.bdxt_init_macro_command,"his"));

		}

		Map<String, String> map = new HashMap<String, String>();
		String url = current_application.appConf.server_url
				+ "Mobile/YidongChafangClientCommunication/getWelComeInfo";
		new HttpHelper(null, new IHandleHttpHelperResult() {
			@Override
			public void handleResult(final List<Map<String, Object>> httpData) {
				if (httpData != null && httpData.size() > 0) {
					bundle.putString("welcome_info", (String) httpData.get(0)
							.get("content"));

					Log.i("tiantan", "get welcome info is :"
							+ (String) httpData.get(0).get("content"));
				}

				i.putExtras(bundle);
				// ComponentName cn = new ComponentName("csdn.shimiso.eim",
				// "csdn.shimiso.eim.activity.LoginActivity");
				// i.setComponent(cn);
				try {
					startActivity(i);
				} catch (Exception e) {
					Toast.makeText(YiDongYiHuActivity.this,
							"未安装应用集成模块，无法启动该模块", Toast.LENGTH_LONG).show();
					e.printStackTrace();
				}

			}
		}).getDataFromServerNoTip(url, map);

		// bundle.putString("conRef", "MBMID/1");

	}

	/**
	 * @Title: goZhuyuanOverview
	 * @Description: TODO
	 * @author: Huke <Huke@tiantanhehe.com>
	 * @date: 2016年5月11日 上午10:22:42
	 * @param v
	 */
	public void goChafangGensui(View v) {
		Intent intent = new Intent(this, ChafangGensuiActivity.class);
		// intent.putExtra("loadUrl", current_application.appConf.server_url +
		// "ZhuyuanYishi/Patient/showPatientZhuyuanDetailPad/zhuyuan_id/");
		startActivity(intent);

	}

	public void setUserInfoData() {
		TextView hushiID = (TextView) findViewById(R.id.hushiID);
		hushiID.setText(current_application.appConf.current_user_number);
		TextView hushixingming = (TextView) findViewById(R.id.hushixingming);
		hushixingming.setText(current_application.appConf.current_user_name);
		TextView suoshukeshi = (TextView) findViewById(R.id.suoshukeshi);
		suoshukeshi
				.setText(current_application.appConf.current_user_department);
		TextView suoshuzhiwu = (TextView) findViewById(R.id.suoshuzhiwu);
		suoshuzhiwu
				.setText(current_application.appConf.current_user_department_position);

		final String[] keshiAttar = current_application.appConf.current_user_suoshu_department
				.split("\\,");
		final ArrayList<HashMap<String, String>> keshiList = new ArrayList<HashMap<String, String>>();
		for (int i = 0; i < keshiAttar.length; i++) {
			String[] tempKeshi = keshiAttar[i].split("\\|");
			if (tempKeshi[0].equals("")) {
				continue;
			}
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("name", tempKeshi[0]);
			String tempKeshiId = "";
			try {
				tempKeshiId = tempKeshi[1];
			} catch (Exception e) {

			} finally {
				map.put("keshi_id", tempKeshiId);
			}
			keshiList.add(map);
		}
		ListView keshi_list = (ListView) findViewById(R.id.keshi_list);

		ViewGroup.LayoutParams params = keshi_list.getLayoutParams();
		if (keshiList.size() >= 2) {
			params.height = 130;
			keshi_list.setLayoutParams(params);
		}
		SimpleAdapter keshiAdapter = new SimpleAdapter(YiDongYiHuActivity.this,
				keshiList, R.layout.left_keshi_item, new String[] { "name" },
				new int[] { R.id.left_item_keshi_name });
		keshi_list.setAdapter(keshiAdapter);
		keshi_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == 0) {
					return;
				}
				String checkKeshi = keshiList.get(arg2).get("name").toString();
				String tempSuoshuKeshi = "";
				for (int i = 0; i < keshiAttar.length; i++) {
					String[] tempKeshi = keshiAttar[i].split("\\|");
					if (!tempKeshi[0].equals(checkKeshi)) {
						tempSuoshuKeshi += tempKeshi[0] + ",";
					}
				}
				current_application.appConf.current_user_suoshu_department = checkKeshi
						+ "," + tempSuoshuKeshi;
				current_application.appConf.current_user_department = checkKeshi;
				current_application.appConf.current_user_department_id = keshiList
						.get(arg2).get("keshi_id").toString();
				current_application.data_manager.qingkongChuangjian();

				try {
					current_application.data_manager
							.syncDataWithServerLogin(YiDongYiHuActivity.this);
				} catch (Exception e) {

				} finally {

				}
				qiehuaProDialog = new ProgressDialog(YiDongYiHuActivity.this);
				qiehuaProDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				qiehuaProDialog.setMessage("正在切换科室，请稍后...");
				qiehuaProDialog.setIndeterminate(false);
				qiehuaProDialog.setCancelable(true);
				qiehuaProDialog.show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(10000);
							myHandler.sendMessage(myHandler.obtainMessage());
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
				/*
				 * Intent intent = new Intent(TiantanActivity.this,
				 * MainActivity.class); finish(); startActivity(intent);
				 * Toast.makeText( getApplicationContext(), "当前科室切换成功。",
				 * Toast.LENGTH_LONG).show();
				 */
			}
		});
	}

	public void setNavData() {
		NavigationActivity navigationActivity = new NavigationActivity();

		// 获取患者基本信息
		initHuanZheData();

		if (tiaoZhuangActivity == MainActivity.class) {
			// 设置主界面顶端患者信息
			setDingbuHuanZheXinXi();
		}

		// 设置默认护理级别
		if (current_application.appConf.current_patient_huli_jibie == null
				|| current_application.appConf.current_patient_huli_jibie
						.equals("")) {
			current_application.appConf.current_patient_huli_jibie = getString(R.string.sanjihuli);// "三级护理"
		}

		// 显示护理级别
		// TextView navBarHuliJibieTextView = (TextView)
		// findViewById(R.id.navBarHuliJibieTextView);
		// navBarHuliJibieTextView.setText(getString(R.string.dangqian_hulijibie)
		// +
		// current_application.appConf.current_patient_huli_jibie);

		// 设置诊疗助手相关内容
		// setZhenliaoZhushou();

		left_content.setVisibility(View.VISIBLE);
	}

	/**
	 * @Title: setContentInitialScale
	 * @Description: TODO
	 * @author: Huke <Huke@tiantanhehe.com>
	 * @date: 2016年4月28日 下午8:44:01
	 * @param request_url2
	 */
	protected void setContentInitialScale(String request_url) {
		if (request_url.indexOf("ZhuyuanYishi/Patient/showPatientListPad") != -1) {
			contentWebView.setInitialScale(contentWebViewScale * 120 / 100); // 170
																				// //90
		} else {
			contentWebView.setInitialScale(contentWebViewScale); // 120 //70
		}

	}

	protected void loadXiaobianque() {
		if (xiaobianqueWebView != null) {
			xiaobianqueWebView
					.loadUrl(current_application.appConf.xiaobianque_url);
		}
	}

	public void guanbiLeftRightMenu() {
		menu.showContent();
		right_state = false;
	}

	public void openLeftQingjingDaohang(View v) {
		menu.toggle();
	}

	public class XiezuoServiceConnection implements ServiceConnection

	{

		// 当绑定服务成功的时候会调用此方法

		public void onServiceConnected(ComponentName name, IBinder service)

		{

			// 得到MyService.MyBinder对象，我们通过这个对象来操作服务中的方法

			xiezuoBinder = (XiezuoService.ControllBinder) service;

			Log.d("tiantan", "xiezuoService connect");
		}

		public void onServiceDisconnected(ComponentName name)

		{
			Log.d("tiantan", "xiezuoService disconnect");
		}

	}

	protected void clearInfo() {
		// 清除浏览记录
		SharedPreferences preferences = getSharedPreferences(
				"yidongyihubrowser", Activity.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		editor.clear();
		editor.commit();

		// 清除webview缓存

		if (contentWebView != null) {
			contentWebView.clearCache(true);
		}
		if (xiaobianqueWebView != null) {
			xiaobianqueWebView.clearCache(false);
		}
	}

	protected String isBelongTo(String url) {
		if (url == null || "".equals(url)) {
			return "";
		}
		String mokuai = "";
		if (url.contains("ZhuyuanYishi/Patient/showPatientListPad")) {
			mokuai = "查看患者";
		} else if (url
				.contains("ZhuyuanYishi/Patient/showPatientZongheChafang")) {
			mokuai = "查房概览";
		} else if (url.contains("Common/Yizhuguanli/showChangqi")
				|| url.contains("Common/Yizhuguanli/showLinshi")) {
			mokuai = "查看医嘱";
		} else if (url.contains("ZhuyuanYishi/BingchengJilu/showView")) {
			mokuai = "病历编辑";
		} else if (url.contains("ZhuyuanYishi/Jiancha/showList")
				|| url.contains("ZhuyuanYishi/Jiancha/editPad")) {
			mokuai = "检验检查";
		} else if (url.contains("Common/TiwenJiludan/showList")
				|| url.contains("TiwenJiludan/showSancedan")) {
			mokuai = "三测单";
		} else if (url.contains("Common/Shijianzhou/showShijianShitu")) {
			mokuai = "时间视图";
		} else if (url.contains("ZhuyuanYishi/Patient/chafangYindao")) {
			mokuai = "查房引导";
		} else if (url.contains("ZhuyuanYishi/Zhenduan/showRuyuanZhenduan")) {
			mokuai = "诊断";
		} else if (url
				.contains("ZhuyuanYishi/Patient/showPatientZhuyuanDetailPad")) {
			mokuai = "住院总览";
		} else if (url.contains("HuliJilu/showList")
				|| url.contains("HuliJilu/showJilu")) {
			mokuai = "护理记录";
		} else if (url.contains("ZhuyuanYishi/Bingli")
				|| url.contains("ZhuyuanYishi/RuyuanJilu")
				|| url.contains("ZhuyuanYishi/BinganShouye")) {
			mokuai = "病历记录";
		} else if (url.contains("ZhuyuanYishi/Patient/showPatientListPad")) {
			mokuai = "病区概览";
		} else if (url.contains("ZhuyuanYishi/ZhuyuanXiezuo/showXiezuoDefault")) {
			mokuai = "查房跟随";
		}

		return mokuai;
	}

	public void qiehuanHuanzhe(String zhuyuanhao) {
		HuanzheWrapper huanzhe = mZhuyuanHuanzheDao
				.getHuanzheByZhuyuanID(zhuyuanhao);
		if (null == huanzhe) {
			tipDialog(context, getString(R.string.jianyan_tishi),
					getString(R.string.huanzhe_bucunzai));
			return;
		}
		huanzhe.setGlobalData(current_application);
		setNewFragment(getString(R.string.zhuyuanzonglan));
		// 设置主界面顶端患者信息
		setDingbuHuanZheXinXi();
	}
	
	public void qiehuanHuanzhe(Map<String, Object> huanzhe) {
		JSONObject curJsonObject = new JSONObject(huanzhe);

		String[] patientParams = { "patient_id", "zhuyuan_id", "zhuyuan_id_show", "zhuyuan_bingqu", "bingchuang_hao", "xingming",
				"xingbie", "nianling", "hulijibie", "zhenduan" };

		current_application.setConfValue(curJsonObject, patientParams);
		
		setNewFragment(getString(R.string.zhuyuanzonglan));
		// 设置主界面顶端患者信息
		setDingbuHuanZheXinXi();
	}

	public void jiazaiWebJiemian(String url) {
		contentWebView.setVisibility(View.VISIBLE);
		contentView.setVisibility(View.GONE);
		// 保存访问的页面信息
		SharedPreferencesUtils.setWebviewUrl(context, "yidongyihubrowser", url);

		loadWebview(url);
		// setContentInitialScale(url);
		guanbiLeftRightMenu();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if (lv_left_menu != null) {
			LeftMenuListAdapter adapter = (LeftMenuListAdapter) lv_left_menu
					.getAdapter();
			if (adapter != null) {
				adapter.getListData().clear();
			}
		}

		super.onDestroy();
	}
}