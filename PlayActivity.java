package com.yiqu.networktv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.safety.Cleaner;

import com.mstar.android.tvapi.common.TvManager;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumVideoArcType;
import com.mstar.android.storage.MStorageManager;

import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnHoverListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.mstar.android.tv.TvAudioManager;
import com.mstar.android.tvapi.common.vo.EnumSpdifType;
import com.togic.mediacenter.player.PlayerCallback;
import com.togic.mediacenter.player.VideoView;

@SuppressLint({ "SimpleDateFormat", "HandlerLeak" })
public class PlayActivity extends Activity implements View.OnKeyListener,
		View.OnClickListener, DownLoadingImpl ,PlayerCallback{

	private static String TAG = "StreamVideo.PlayActivity";
	private final static boolean LOGD = false;

	private static int OSD_STATE_NORMAL = 1;
	private static int OSD_STATE_OPTION = 2;
	private static int OSD_STATE_BROWSER = 3;

	private static int CHANNEL_LIST_NORMAL = 1;
	private static int CHANNEL_LIST_DECODE = 2;
	private static int CHANNEL_LIST_DELRECENT = 3;

	private static int DIALOG_LOADING = 1;
	private static int DIALOG_INFO = 2;
	private static int DIALOG_PLAY_FAIL_INFO = 3;
	private static int DIALOG_PLAY_EXIT_INFO = 4;
	private static int DIALOG_CHANNEL_DEL_INFO = 5;
	private static int DIALOG_NET_WORK_LOST = 6;
	private static int DIALOG_LIST_GET_FAIL = 7;

	private static final int EPG_SHOW_TIMEOUT = 5000; // 5 second
	private static final int PROGRESS_CHECK_INTERVAL = 60 * 1000; // 1 minute
	private static final int SOURCE_SWITCH_TIMEOUT = 1000; // 1 second
	private static final int USER_INPUT_NUMBER_TIMEOUT = 2000; // 2 second
	private static final int SOURCE_PLAY_TIMEOUT = 10000; // 3 sencode
	private static final int CHANNEL_CHANGE_TIMEOUT = 1000; // 1 second

	private int osdViewState = OSD_STATE_NORMAL;
	private boolean isshowdot = false;

	public final static int MSG_HIDE_OSD = 1;
	public final static int MSG_UPDATE_SPEED = 2;
	public final static int MSG_SEND_VDF = 3;
	public final static int MSG_UPDATE = 4;


	private boolean isshowdialognet = false;
	int count = 0;

	String playUrl = null;
//	StreamVideoPlayer player = null;

	/* Video view */
	private VideoView player;
	/* OSD view */
	FrameLayout osdFrame = null;
	LinearLayout channelInfoBottom = null;
	RelativeLayout channelOsd = null;
	ListView channelList = null;
	private TextView channelNameTop;
	private TextView channelNameBottom;
	private ImageView channelLogoTop;
	private ImageView channelLogoBottom;
	private Bitmap channelLogoBitmap;
	private TextView channelGroupName;
	private TextView playingProgram;
	private TextView nextProgram;
	private SeekBar progressBar;

	LinearLayout channelInfoTop = null;
	LinearLayout optionView = null;
	TextView optionViewTitle = null;
	ListView optionList = null;

	LinearLayout customUnlockView = null;
	PassWordTextView password_1 = null;
	PassWordTextView password_2 = null;
	PassWordTextView password_3 = null;
	PassWordTextView password_4 = null;

	// For select channel by number key
	LinearLayout numberPrompt = null;
	TextView numberText = null;
	TextView nameText = null;
	// network speed
	TextView speedText = null;

	private TextView sourcetext = null;
	private View sourcebg = null;
	private TextView source = null;
	private TextView sourceloading = null;

	// Channel group change arrow
	TextView rateinfo = null;
	ImageView arrowLeft = null;
	ImageView arrowRight = null;

	ChannelContentProvider provider;

	private int curPlayGroupIdx, curViewGroupIdx, groupCount;
	private int curPlayChannelIdx, curViewChannelIdx, channelCount;
	private int curPlaySourceIdx, curViewSourceIdx, sourceCount;
	private int playerrSourceIdx = 0;
	private int curVieoRatioIdx, curViewVideoRatioIdx, videoRatioCount = 2;
	private int curClearUserIdex, curViewClearUserIdx, clearUserCount = 2;
	private int curDiffUserIdx, curViewDiffUserIdx, diffUserCount = 2;
	private boolean isShowNetworkSpeed = true;
	private boolean channelIsFavorite = false;
	private int passwordFocusIdx = 0, passwordCount = 4;
	private int channelListState = CHANNEL_LIST_NORMAL;
	private int mOptionSelectd = 0;
	private int playErrorCount = 0;
	private int mediadecodetype = 0;

	private int lastchannel, tempchannel;

	private String playRequestOriginal = "request_all";
	private ChannelGroup mSelectedGroup, lastgroup, tempgroup;
	private ChannelInfo mSelectedChannel;
	private ChannelSource mSelectedSource;

	private boolean isOsdShow = false;

	private int currentStartMinute; // current program start minute
	private int nextStartMinute; // next program start minute;
	private boolean isLoadingDialongShow = false;
	private boolean isConfirmDialogShow = false;
	private LoadingImageView loadingImage = null;

	private String infoMsg = null;
	private boolean finishSelf = false;

	private int[] userNumber = { 0, 0, 0 }; // 999
	private int userNumberCount = 0;
	private int userSelectIndex = -1;
	private ChannelInfo userSelectChannel = null;

	private ChannelInfo recentSelectchannel = null;
	private Timer mTimer = null;
	List<NetworkFile> downloadingFiles = new ArrayList<NetworkFile>();
	List<DownLoadAsync> mDownLoadAsynclist = new ArrayList<DownLoadAsync>();
	private static final String playlistUrl = "http://update.egreatworld.com:8080/software/update/M6/livetv2.xml";

	// User playlist
	private int optionListMode = OptionAdapter.MODE_FUNCTION;
	private List<File> userXmlFiles = null;
	private RelativeLayout optionKeyGuide = null;

	private boolean isNeedUserSelect = false;
	// network speed
	private long preRxBytes = 0;
	private int mediaUid = 1013;
	
	private ChannelInfo selectedUserChannel = null;

	private long playSourceStartTime = 0;

	private boolean finished = false;

	public int screenWidth, screenHeight;

	//更新需要用到的参数
	private MsgReceiver msgReceiver = null;
	private AppInfo appinfo = null;

	// private TvAudioManager tvAudioManager = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 Intent intent = getIntent();
		 playRequestOriginal = intent.getStringExtra("play_request");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
				WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.play_view);
		setupOsdView();

		player = (VideoView)findViewById(R.id.surface);
		
		registerBroadcase();
		DownLoadAsync mDownLoadAsync = new DownLoadAsync(this, playlistUrl);
		showLoadingDialog();
		mDownLoadAsync.execute();
		
		copyLogFile();
		chackUpdateHandler();
	}
	private String appgetPackageInfo() {
		String apppackage = null;
		apppackage = this.getPackageName();
		return apppackage;
	}
	private String getVersionCode() {
		int code = 0;
		try {
			code = super.getPackageManager().getPackageInfo(
					this.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return String.valueOf(code);
	}
	
	public void showUpdateDg(final Context context) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);
		dialog.setTitle("版本更新")
				.setMessage(appinfo.info)
				.setPositiveButton("立即更新",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String apppath = context
										.getApplicationContext().getCacheDir()
										.getAbsolutePath()
										+ "/" + appinfo.appname + ".apk";
								Runtime runtime = Runtime.getRuntime();
								try {
									runtime.exec("chmod 777 " + apppath);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								new AppInstall(context).installApk(new File(
										apppath));
							}
						})
				.setNegativeButton("以后再说",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).show();

	}
	
	public class MsgReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int progress = intent.getIntExtra("progress",
					UpdateService.DOWNLOADFAIL);
			if (progress == UpdateService.DOWNLOADSUCCECC) {
				showUpdateDg(PlayActivity.this);
				unregisterReceiver(msgReceiver);
			} else if (progress == UpdateService.DOWNLOADFAIL) {
				unregisterReceiver(msgReceiver);
			}

		}

	}
	
	private void registerBroadcase() {
		msgReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.yiqu.networktv.RECEIVER");
		registerReceiver(msgReceiver, intentFilter);
	}
	
	//从Json数据中获取更新数据
	
	private void getDateFromJson() throws Exception{
		new Thread(){
			public void run(){
				ParseJsonData parse = new ParseJsonData();
				try {
					appinfo = parse.getAppInfoFromJson();
					mHandler.sendEmptyMessage(MSG_UPDATE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}
	
	//初始化更新数据
	private void initCheckUpdate(){
		try {
			getDateFromJson();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	//检查更新线程
	private void chackUpdateHandler(){
		appinfo = new AppInfo();
		initCheckUpdate();
	}
	
	

	@Override
	protected void onDestroy() {
		player.stopPlay();
//		player.stopPlayHandle();
		super.onDestroy();
		if (provider != null) {
			provider.releaseResource();
		}
		
		Intent intent = new Intent();
		intent.setAction("com.yiqu.networktv.UpdateService");
		stopService(intent);
		
		closeLoadingDialog();
		closePlayFailInfoDialog();
		System.exit(0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		finished = false;
	}

	Runnable downloadRun = new Runnable() {
		@SuppressWarnings("deprecation")
		public void run() {
			downPlaylist();
		}
	};

	void downPlaylist() {
		showLoadingDialog();

		// NetworkFile file = new NetworkFile(this, playlistUrl);
		// if (file.isCached()) {
		// //file.checkUpdate();
		// // always download, we check update by version in the file
		// file.downLoadFile();
		// }
	}

	String getVersion(String fileStr) {
		int index = fileStr.indexOf("ver='");
		if (index < 0)
			return null;
		System.out.println("Index of (ver='_ = " + index);
		int start = index + 5;
		System.out.println("start = " + start);
		int end = fileStr.indexOf('\'', start);
		if (end < 0)
			return null;
		System.out.println("end = " + end);
		return fileStr.substring(start, end);
	}

	private void onPlaylistDownloaed(File originFile) {
		try {
		if (originFile.length() == 0) {
			onPlaylistConverted(null);
			return;
		}
		String newStr = FileUtils.file2String(originFile);
		String path = originFile.getParent();
		String name = originFile.getName();
		String fullPath = path + "/" + "converted_" + name;
		File savedFile = new File(fullPath);
			if (savedFile.exists()) {
				String oldStr = FileUtils.file2String(savedFile);
				if (oldStr != null) {
					String oldVer = getVersion(oldStr);
					String newVer = getVersion(newStr);
					if ((oldVer == null) || (newVer == null)
							|| (newStr.compareTo(oldStr) > 0)) {
						newStr = newStr.replaceAll("&", "&amp;");
						savedFile = FileUtils.string2File(newStr, fullPath);
					} else {
					}
				}
			} else {
				newStr = newStr.replaceAll("&", "&amp;");
				savedFile = FileUtils.string2File(newStr, fullPath);
			}
			onPlaylistConverted(savedFile);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// private void onPlaylistDownloadFail(File originFile) {
	// String path = originFile.getParent();
	// String name = originFile.getName();
	// String fullPath = path + "/" + "converted_" + name;
	// File previousSaveFile = new File(fullPath);
	// if (previousSaveFile.exists()) {
	// onPlaylistConverted(null);
	// } else {
	// }
	// onPlaylistConverted(null);
	// }

	/**
	 * Playlist has converted, start to parse it
	 * 
	 * @param file
	 *            the converted file, if null parse xml from resource
	 */
	private void onPlaylistConverted(File file) {

		boolean isOk = prepareResource(playRequestOriginal, file);
		if (!isOk) {
			String msg;
			if (playRequestOriginal.equals("request_user")
					|| playRequestOriginal
							.equals(ChannelContentProvider.GROUP_NAME_USER)) {
				msg = getResources().getString(R.string.no_user_channel);
			} else {
				msg = getResources().getString(R.string.prepare_res_fail);
			}
			/*
			 * Toast.makeText(PlayActivity.this, msg,
			 * Toast.LENGTH_SHORT).show();
			 */
			// showInfoDialog(msg, true);
			return;
		} else {
			playSelectedChannel();

			if (mSelectedChannel != null) {
				switchToNormalOsdView();
			} else {
				switchToChannelListView();
			}
			if (!isGroupLock())
				updateChannelInfo();
		}
	}

	void playSelectedChannel() {
		if (mSelectedChannel != null) {
			// save to DB for recent
			provider.saveRecent(mSelectedChannel);
			playErrorCount = 0;
			this.source.setText("");
			this.sourcetext.setText(getResources().getString(R.string.loadingsource));
			onSourceSelected(mSelectedSource);
			sendChannelNumberBroadcast();
		}
	}

	String getUserPlaylistCachePath() {
		String path = this.getFilesDir().getAbsolutePath() + "/user_tv";
		// String path = FileUtils.getSdcardPath() + "/user_tv";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdir();
		}
		return path;
	}

	List<ChannelGroup> parseUserGroupFromFile(File xmlFile) {
		String filename = xmlFile.getName();
		String inUserXml = getUserPlaylistCachePath() + "/" + "converted_"
				+ filename;

		File inFile = null;
		if (xmlFile.exists()) {
			String str = FileUtils.file2String(xmlFile);
			if (str != null) {
				str = str.replaceAll("&", "&amp;");
				inFile = FileUtils.string2File(str, inUserXml);
			} else {
				Log.w(TAG, "convert " + filename + " fail!");
			}
		} else {
			// check converted file
			inFile = new File(inUserXml);
			if (!inFile.exists()) {
				inFile = null;
			}
		}

		List<ChannelGroup> groups = provider.parseFromFile(inFile,
				ChannelContentProvider.XML_FROM_USER);
		if (groups == null || groups.size() == 0) {
			if (inFile != null)
				inFile.delete();
		}

		return groups;
	}

	List<ChannelGroup> getDefaultUserPlaylist() {
		String extUserXml = FileUtils.getUsbPath() + "/" + "mytv.xml";
		File extFile = new File(extUserXml);
		if (!extFile.exists()) {
			return null;
		}
		return parseUserGroupFromFile(extFile);
	}

	List<File> getSavedUserPlaylistFile(boolean skipDefault) {
		// search previous selected file
		String searchPath = getUserPlaylistCachePath();
		File searchRoot = new File(searchPath);

		List<File> xmlFiles = new ArrayList<File>();

		File[] srcFiles = searchRoot.listFiles();
		File file;
		for (int i = 0; i < srcFiles.length; i++) {
			file = srcFiles[i];
			if (file.isFile() && file.getName().endsWith("xml")) {
				if (skipDefault && file.getName().equals("converted_mytv.xml")) {
					continue;
				}
				Log.d(TAG, "get save user file : " + file.getName());
				xmlFiles.add(file);
			}
		}

		if (xmlFiles.size() == 0)
			return null;

		return xmlFiles;
	}

	void addSavedUserPlaylist() {
		// check default first
		List<ChannelGroup> groups = getDefaultUserPlaylist();
		if (groups != null)
			provider.addUserGroups(groups);

		List<File> xmlFiles = getSavedUserPlaylistFile(true);
		if (xmlFiles == null || xmlFiles.size() == 0)
			return;

		for (int i = 0; i < xmlFiles.size(); i++) {
			groups = provider.parseFromFile(xmlFiles.get(i),
					ChannelContentProvider.XML_FROM_USER);
			if (groups != null)
				provider.addUserGroups(groups);
		}
	}

	void removeSavedUserPlaylistFile() {
		List<File> xmlFiles = getSavedUserPlaylistFile(false);
		if (xmlFiles == null || xmlFiles.size() == 0)
			return;
		for (int i = 0; i < xmlFiles.size(); i++) {
			xmlFiles.get(i).delete();
		}
	}
	
	@SuppressWarnings("deprecation")
	boolean prepareResource(String playRequest, File xmlFile) {
		List<ChannelGroup> groups = null;
		provider = new ChannelContentProvider(PlayActivity.this);
		if(xmlFile == null){
			closeLoadingDialog();
			if(!isNetworkAvailable()){
				isshowdialognet = true;
				showDialog(DIALOG_NET_WORK_LOST);
				return false;
			}else{
				if(count == 5){
					showDialog(DIALOG_LIST_GET_FAIL);
					count = 0;
					return false;
				}
			}
		}else{
			try {
				groups = provider.parseFromFile(xmlFile,
						ChannelContentProvider.XML_FROM_NETWORK);
			} catch (Exception e) {
				
			}
		}
		if (groups == null) {
			count ++;
			DownLoadAsync mDownLoadAsync = new DownLoadAsync(PlayActivity.this, playlistUrl);
			showLoadingDialog();
			mDownLoadAsync.execute();
			return false;
		} else {
			provider.collectChannelGroups(groups);
			addSavedUserPlaylist();

			if (playRequest.equals(ChannelContentProvider.GROUP_NAME_USER)
					|| playRequest.equals("request_user")) {
				ChannelGroup group = provider
						.getGroupByName(ChannelContentProvider.GROUP_NAME_USER);
				if (group == null) {
					Log.d(TAG, "User playlist is empty!");
					return false;
				}
			}

			groupCount = provider.getGroupCount();
			if (groupCount == 0) {
				Log.e(TAG, "empty group");
				return false;
			}

			boolean requestChannel = false;
			String epg = null;
			if (playRequest.equals(ChannelContentProvider.CHANNEL_NAME_CCTV1)
					|| playRequest.equals("request_cctv1")) {
				curPlayGroupIdx = provider
						.getGroupIndexByName(ChannelContentProvider.GROUP_NAME_CCTV);
				requestChannel = true;
				epg = "cctv1";
			} else if (playRequest
					.equals(ChannelContentProvider.CHANNEL_NAME_HUNANTV1)
					|| playRequest.equals("request_hunantv1")) {
				curPlayGroupIdx = provider
						.getGroupIndexByName(ChannelContentProvider.GROUP_NAME_SATELLITE);
				requestChannel = true;
				epg = "HUNANTV1";
			} else {
				String groupName = provider.getGroupNameByRequest(playRequest);
				if (groupName == null) {
					Log.e(TAG, "Bad request : " + playRequest);
					return false;
				}
				curPlayGroupIdx = provider.getGroupIndexByName(groupName);
			}

			if (curPlayGroupIdx < 0) {
				Log.e(TAG, "No group!");
				return false;
			}

			mSelectedGroup = provider.getGroupByIndex(curPlayGroupIdx);
			curViewGroupIdx = curPlayGroupIdx;

			boolean isLock = mSelectedGroup.getIsLock();
			List<ChannelInfo> channels = mSelectedGroup.getChannelInfo();
			channelCount = (channels != null) ? channels.size() : 0;

			// Get current playing channel index
			if (isLock || channelCount <= 0) {
				mSelectedChannel = null;
				curViewChannelIdx = curPlayChannelIdx = 0;
			} else {
				if (requestChannel) {
					curPlayChannelIdx = provider.getChannelIdxByEpg(channels,
							epg);
				} else {
					curPlayChannelIdx = provider
							.getGroupLastPlayIndex(mSelectedGroup);
				}

				mSelectedChannel = mSelectedGroup.getChannelInfo().get(
						curPlayChannelIdx);
				curViewChannelIdx = curPlayChannelIdx;
			}

			if (mSelectedChannel != null) {
				List<ChannelSource> sources = mSelectedChannel.getSources();
				sourceCount = (sources != null) ? sources.size() : 0;
				int index = provider.getLastPlaySourceIndex(mSelectedChannel);
				if (index >= 0 && index < sourceCount)
					curViewSourceIdx = curPlaySourceIdx = index;
				else
					curViewSourceIdx = curPlaySourceIdx = 0;
				mSelectedSource = mSelectedChannel.getSources().get(
						curPlaySourceIdx);
				mSelectedChannel.setCurSourceIndex(curPlaySourceIdx);
			} else {
				sourceCount = 0;
				curViewSourceIdx = curPlaySourceIdx = 0;
				mSelectedSource = null;
			}

			curVieoRatioIdx = curViewVideoRatioIdx = getVideoRatio();
			videoRatioCount = OptionAdapter.VIDEO_RATIO.length;

			curClearUserIdex = curViewClearUserIdx = 0; //
			curDiffUserIdx = curViewDiffUserIdx = getDiffUserSetting(); //
			isShowNetworkSpeed = getShowNetworkSpeedSetting();
			if (isShowNetworkSpeed) {
				showNetworkSpeed();
			}
			return true;
		}
	}

	void setupOsdView() {
//		surfaceView = (SurfaceView) findViewById(R.id.surface);
		osdFrame = (FrameLayout) findViewById(R.id.osdview);
		osdViewState = OSD_STATE_NORMAL;

		channelInfoTop = (LinearLayout) osdFrame
				.findViewById(R.id.channel_info_top);
		channelInfoBottom = (LinearLayout) osdFrame
				.findViewById(R.id.channel_info_bottom);
		channelOsd = (RelativeLayout) osdFrame.findViewById(R.id.channel_osd);
		channelList = (ListView) osdFrame.findViewById(R.id.channel_list);
		channelList.setOnKeyListener(this);
		channelNameTop = (TextView) channelInfoTop
				.findViewById(R.id.channel_name_top);
		channelNameBottom = (TextView) channelInfoBottom
				.findViewById(R.id.channel_name_bottom);
		playingProgram = (TextView) channelInfoBottom
				.findViewById(R.id.playing_program);
		progressBar = (SeekBar) channelInfoBottom
				.findViewById(R.id.video_progress_bar);
		nextProgram = (TextView) channelInfoBottom
				.findViewById(R.id.next_program);
		channelLogoTop = (ImageView) channelInfoTop
				.findViewById(R.id.channel_logo_top);
		channelLogoBottom = (ImageView) channelInfoBottom
				.findViewById(R.id.channel_logo_bottom);
		channelGroupName = (TextView) channelOsd
				.findViewById(R.id.channel_group);

		// Option list
		optionView = (LinearLayout) osdFrame
				.findViewById(R.id.channel_option_view);
		optionViewTitle = (TextView) optionView
				.findViewById(R.id.channel_option_title);
		optionList = (ListView) optionView.findViewById(R.id.option_list);
		optionList.setOnKeyListener(this);
		optionKeyGuide = (RelativeLayout) optionView
				.findViewById(R.id.option_key_guide);

		// Custom unlock view
		customUnlockView = (LinearLayout) channelOsd
				.findViewById(R.id.custom_unlock_view);
		password_1 = (PassWordTextView) customUnlockView
				.findViewById(R.id.custom_password_1);
		password_2 = (PassWordTextView) customUnlockView
				.findViewById(R.id.custom_password_2);
		password_3 = (PassWordTextView) customUnlockView
				.findViewById(R.id.custom_password_3);
		password_4 = (PassWordTextView) customUnlockView
				.findViewById(R.id.custom_password_4);

		// user select channel number prompt
		numberPrompt = (LinearLayout) osdFrame.findViewById(R.id.number_prompt);
		numberText = (TextView) numberPrompt.findViewById(R.id.channel_number);
		nameText = (TextView) numberPrompt.findViewById(R.id.channel_name);

		// network speed
		speedText = (TextView) osdFrame.findViewById(R.id.network_speed);
		rateinfo = (TextView) osdFrame.findViewById(R.id.network_rate);
		sourcetext = (TextView) osdFrame.findViewById(R.id.tipTextView);
		sourcebg = (RelativeLayout) osdFrame.findViewById(R.id.sourcetipsbg);
		source = (TextView) osdFrame.findViewById(R.id.source);
		sourceloading = (TextView) osdFrame.findViewById(R.id.tipTextView);
		rateinfo.setText(getResources().getString(R.string.rate)
				+ OptionAdapter.VIDEO_RATIO[getVideoRatio()]);

		sourceloading.setVisibility(View.INVISIBLE);
		sourcetext.setVisibility(View.INVISIBLE);
		sourcebg.setVisibility(View.INVISIBLE);
		source.setVisibility(View.INVISIBLE);
		channelInfoTop.setVisibility(View.INVISIBLE);
		channelInfoBottom.setVisibility(View.INVISIBLE);
		channelOsd.setVisibility(View.INVISIBLE);
		optionView.setVisibility(View.INVISIBLE);
		customUnlockView.setVisibility(View.INVISIBLE);
		numberPrompt.setVisibility(View.INVISIBLE);

		arrowLeft = (ImageView) channelOsd.findViewById(R.id.arrow_left);
		arrowRight = (ImageView) channelOsd.findViewById(R.id.arrow_right);
		arrowLeft.setOnClickListener(this);
		arrowRight.setOnClickListener(this);
		// Set channel list check mode
		channelList.setItemsCanFocus(true);
		// channelList.setSelector(R.drawable.tv_channel_list_selected_bg);
		channelList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		// channelList.sets
		channelList.setSelection(curPlayChannelIdx);

		//
		optionList.setSelector(R.drawable.tv_option_list_selected_bg);

		loadingImage = (LoadingImageView) osdFrame
				.findViewById(R.id.loading_image);
		loadingImage.setVisibility(View.INVISIBLE);

		channelOsd.setOnHoverListener(new OnHoverListener() {
			public boolean onHover(View v, MotionEvent event) {
				int what = event.getAction();
				switch (what) {
				case MotionEvent.ACTION_HOVER_ENTER:
					// Log.d(TAG, "ACTION_HOVER_ENTER");
					mHandler.removeCallbacks(hideOsdHandler);
					break;
				case MotionEvent.ACTION_HOVER_MOVE:
					// Log.d(TAG, "ACTION_HOVER_MOVE");
					mHandler.removeCallbacks(hideOsdHandler);
					break;
				case MotionEvent.ACTION_HOVER_EXIT:
					// Log.d(TAG, "ACTION_HOVER_EXIT");
					mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT);
					break;
				}
				return false;
			}
		});
	}

	private void updateChannelInfo() {
		if (mSelectedChannel != null) {
			channelNameTop.setText(mSelectedChannel.getName());
			String channelNumberStr = Integer.toString(mSelectedChannel
					.getChannelNumber());
			String bottomStr = channelNumberStr + " "
					+ mSelectedChannel.getName();
			channelNameBottom.setText(bottomStr);
			channelNameBottom.getPaint().setFakeBoldText(true);
		}
		updateChannelLogo();
		updateChannelProgramInfo();
		showOsdView();
		mHandler.removeCallbacks(hideOsdHandler);
		mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT);
	}

	private void updateChannelLogo() {
		if (mSelectedChannel != null) {
			
			channelLogoBottom.setImageBitmap(channelLogoBitmap);
			channelLogoTop.setImageBitmap(channelLogoBitmap);
			
			String logoUrl = mSelectedChannel.getLogoUrl();
			if (logoUrl == null || logoUrl.equals("")) {
			} else {
				DownLoadAsync mDownLoadAsync = new DownLoadAsync(
						PlayActivity.this, logoUrl);
				synchronized (mDownLoadAsynclist) {	
					mDownLoadAsynclist.add(mDownLoadAsync);
					mDownLoadAsync.execute();
				}
				// if (file.isCached()) {
				// updateChannelLogo(file);
				// } else {
				//
				// }
			}

			// if (queryDownlingFile(mSelectedChannel.getLogoUrl())) {
			// // wait download success
			// } else {
			// String logoUrl = mSelectedChannel.getLogoUrl();
			// if (logoUrl == null || logoUrl.equals("")) {
			// } else {
			// NetworkFile file = new NetworkFile(PlayActivity.this,
			// logoUrl);
			// file.setExtraObject((Object) mSelectedChannel);
			// if (file.isCached()) {
			// updateChannelLogo(file);
			// } else {
			// // Add to downloading file
			// synchronized (downloadingFiles) {
			// downloadingFiles.add(file);
			// }
			// }
			// }
			// }
		} else {
			channelLogoTop.setImageBitmap(channelLogoBitmap);
		}
	}

	private void updateChannelLogo(File filelogol) {
		// ChannelInfo info = (ChannelInfo)networkfile.getExtraObject();
		// if (info != mSelectedChannel) {
		// return;
		// }
		Bitmap bmp = getBitmapFromFile(filelogol);
		Log.v("lili","================bmp================"+bmp);
		if (bmp != null) {
			channelLogoTop.setImageBitmap(bmp);
			channelLogoBottom.setImageBitmap(bmp);
			if (channelLogoBitmap != null) {
				channelLogoBitmap.recycle();
				channelLogoBitmap = bmp;
			}
		}else{
			channelLogoBottom.setImageBitmap(channelLogoBitmap);
			channelLogoTop.setImageBitmap(channelLogoBitmap);
		}
	}
	
	private void updateInfo(File f){
		Log.v("zwm","=======f=================="+f.getName());
		if (f.length() == 0) {
			String str = getResources().getString(R.string.current_program)
					+ getResources().getString(R.string.loading_info_err);
			playingProgram.setText(str);
			nextProgram.setText(str);
		}else{
			Log.v("zwm","=======f=================="+f.getName());
			ChannelProgram programInfo;
			try {
				programInfo = HtmlParserUtils.parser(f);
				ChannelInfo channel = mSelectedChannel;
				channel.setProgramInfo(programInfo);
			} catch (Exception e) {
				// TODO: handle exception
//				Log.v("nihao","=======Exception=================="+f.getName());
				e.printStackTrace();
			}
			updateChannelProgramInfo();
		}
	}
	
	private void startDownloadProgramInfo(ChannelInfo channel) {
		String url = "http://www.tvmao.com/ext/q_tv.jsp?c="
				+ mSelectedChannel.getEpg();
		DownLoadAsync mDownLoadAsync = new DownLoadAsync(PlayActivity.this, url);
		synchronized (mDownLoadAsynclist) {	
			mDownLoadAsynclist.add(mDownLoadAsync);
			mDownLoadAsync.execute();
		}
	}

	Runnable programCheck = new Runnable() {
		public void run() {
			Time t = new Time(); // or Time t=new Time("GMT+8"); 
			t.setToNow(); // 
			int hour = t.hour; // 0-23
			int min = t.minute;
			int minutes = hour * 60 + min;
			if (hour == 0 && min == 0) {
				// new day
				updateChannelProgramInfo();
			} else if (minutes > nextStartMinute) {
				try {
					// next program
					reflashChannelProgram(mSelectedChannel.getProgramInfo());
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				int percent = (minutes - currentStartMinute) * 100
						/ (nextStartMinute - currentStartMinute);
				// Log.d(TAG, "percent = " + percent);
				progressBar.setProgress(percent);
				mHandler.postDelayed(programCheck, PROGRESS_CHECK_INTERVAL);
			}
		};
	};

	void reflashChannelProgram(ChannelProgram info) {
		Time t = new Time(); // or Time t=new Time("GMT+8"); ����Time Zone���ϡ�
		t.setToNow(); //
		int hour = t.hour; // 0-23
		int min = t.minute;
		int minutes = hour * 60 + min;
		ChannelProgram.ProgramItem[] programs = info.getProgramInfo(minutes);
		if (programs != null) {
			currentStartMinute = ChannelProgram.getMinutes(programs[0].time);
			nextStartMinute = ChannelProgram.getMinutes(programs[1].time);

			String str = getResources().getString(R.string.current_program)
					+ programs[0].program;
			playingProgram.setText(str);
			str = getResources().getString(R.string.next_program)
					+ programs[1].time + " " + programs[1].program;
			nextProgram.setText(str);

			int percent = (minutes - currentStartMinute) * 100
					/ (nextStartMinute - currentStartMinute);
			progressBar.setProgress(percent);

			mHandler.postDelayed(programCheck, PROGRESS_CHECK_INTERVAL);
		} else {
			String str = getResources().getString(R.string.current_program)
					+ getResources().getString(R.string.loading_info);
			playingProgram.setText(str);
			nextProgram.setText(str);
		}
	}

	void updateChannelProgramInfo() {
		String str1 = getResources().getString(R.string.current_program)
				+ getResources().getString(R.string.loading_info);
		playingProgram.setText(str1);
		nextProgram.setText(str1);
		
		if (mSelectedChannel != null) {
			ChannelProgram programInfo = mSelectedChannel.getProgramInfo();
			if (programInfo != null) {
				// check date
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				Date curDate = new Date(System.currentTimeMillis());
				String str = formatter.format(curDate);
				if (str.compareTo(programInfo.getDate()) <= 0) {
					try {
						reflashChannelProgram(programInfo);
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else {
					// different date
					Log.d(TAG, "updateChannelProgramInfo: " + str + " != "
							+ programInfo.getDate());
					startDownloadProgramInfo(mSelectedChannel);
				}
			} else {
				startDownloadProgramInfo(mSelectedChannel);
			}
		}
	}

	Bitmap getBitmapFromFile(File file) {
		if (file == null)
			return null;

		InputStream is = null;
		Bitmap bitmap = null;
		try {
			is = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(is);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bitmap;
	}

	/**
	 * Query the downloading file
	 * 
	 * @return true if downloading, false or else
	 */
	private boolean queryDownlingFile(String url) {
		if (downloadingFiles.size() == 0)
			return false;
		int hashCode = url.hashCode();
		synchronized (downloadingFiles) {
			for (int i = 0; i < downloadingFiles.size(); i++) {
				NetworkFile file = downloadingFiles.get(i);
				if (hashCode == file.getHashCode()) {
					return true;
				}
			}
		}
		return false;
	}

	void switchToNormalOsdView() {
		int oldOsdIndex = osdViewState;

		if (oldOsdIndex == OSD_STATE_BROWSER) {
			channelOsd.setVisibility(View.INVISIBLE);
		} else if (oldOsdIndex == OSD_STATE_OPTION) {
			optionView.setVisibility(View.INVISIBLE);
			channelInfoTop.setVisibility(View.INVISIBLE);
		}

		osdViewState = OSD_STATE_NORMAL;
	}

	private boolean firstShow = true;

	private void switchToChannelListView() {
		if (mSelectedGroup == null) {
			return;
		}
		int oldOsdIndex = osdViewState;
		if (oldOsdIndex == OSD_STATE_NORMAL) {
			channelInfoBottom.setVisibility(View.INVISIBLE);
		}
		List<ChannelInfo> viewItems = mSelectedGroup.getChannelInfo();
		ChannelAdapter adapter = (ChannelAdapter) channelList.getAdapter();
		if (adapter == null) {
			adapter = new ChannelAdapter(PlayActivity.this, viewItems);
			channelList.setAdapter(adapter);
			channelList.setOnItemClickListener(channelOnClickListener);
		} else {
			adapter.setViewItems(viewItems);
		}

		boolean diffUser = (getDiffUserSetting() == 1);
		adapter.setDiffUser(diffUser);
		if (provider.isGroupLock(mSelectedGroup)) {
			adapter.setLock(true);
		} else {
			adapter.setLock(false);
		}
		if (diffUser
				&& mSelectedGroup.getFrom() == ChannelContentProvider.XML_FROM_USER) {
			channelGroupName.setTextColor(getResources().getColor(
					R.color.userTextColor));
		} else {
			channelGroupName.setTextColor(getResources().getColor(
					R.color.offWhite));
		}
		curPlayGroupIdx = curViewGroupIdx = provider
				.getGroupIndexByName(mSelectedGroup.getName());
		channelGroupName.setText(mSelectedGroup.getName());
		channelList.setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {
					if (firstShow) {
						channelList.setSelection(curPlayChannelIdx);
						firstShow = false;
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		adapter.notifyDataSetChanged();
		channelList.setSelection(curPlayChannelIdx);
		firstShow = true;
		osdViewState = OSD_STATE_BROWSER;
		channelListState = CHANNEL_LIST_NORMAL;
		channelOsd.setVisibility(View.VISIBLE);
	}

	void switchToChannelOptionView() {
		int oldOsdIndex = osdViewState;

		if (oldOsdIndex == OSD_STATE_NORMAL) {
			channelInfoBottom.setVisibility(View.INVISIBLE);
		} else if (oldOsdIndex == OSD_STATE_BROWSER) {
			channelOsd.setVisibility(View.INVISIBLE);
		}

		if (mSelectedChannel == null) {
			Log.d(TAG, "swith to option view, get null selected channel");
			String toastStr = getResources().getString(
					R.string.toast_no_channel);
			// Toast.makeText(PlayActivity.this, toastStr,
			// Toast.LENGTH_LONG).show();
			showInfoDialog(toastStr, false);
			return;
		}
		channelIsFavorite = mSelectedChannel.getIsFavorite();
//		curVieoRatioIdx = curViewVideoRatioIdx = getVideoRatio();
		curVieoRatioIdx = curViewVideoRatioIdx = player.mCurrentMode;
		Log.v("zwm", "================curVieoRatioIdx==============="+curVieoRatioIdx);
		curPlaySourceIdx = curViewSourceIdx = mSelectedChannel
				.getCurSourceIndex();
		mediadecodetype = player.mDecoderType;
		curClearUserIdex = curViewClearUserIdx = 0; //
		curDiffUserIdx = curViewDiffUserIdx = getDiffUserSetting(); //
		isShowNetworkSpeed = getShowNetworkSpeedSetting();

		OptionAdapter adapter = (OptionAdapter) optionList.getAdapter();
		if (adapter == null) {
			adapter = new OptionAdapter(PlayActivity.this, mSelectedChannel,
					curPlaySourceIdx, curVieoRatioIdx, channelIsFavorite,mediadecodetype);
			optionList.setAdapter(adapter);
			optionList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					Log.d(TAG, "option onItemClick");
					onOptionConfirm();
				}

			});
			adapter.setClearUserIndex(curClearUserIdex);
			adapter.setDiffUserIndex(curDiffUserIdx);
			adapter.setDiffUser(getDiffUserSetting() == 1);
			adapter.setShowSpeed(isShowNetworkSpeed);
		} else {
			adapter.setChannelInfo(mSelectedChannel);
			adapter.setSourceIndex(curPlaySourceIdx);
			adapter.setRatioIndex(curVieoRatioIdx);
			adapter.setIsFavorite(channelIsFavorite);
			adapter.setClearUserIndex(curClearUserIdex);
			adapter.setDiffUserIndex(curDiffUserIdx);
			adapter.setDiffUser(getDiffUserSetting() == 1);
			adapter.setShowSpeed(isShowNetworkSpeed);
			adapter.notifyDataSetChanged();
		}

		optionListMode = OptionAdapter.MODE_FUNCTION;
		optionViewTitle.setText(getResources().getString(
				R.string.option_setting));
		optionKeyGuide.setVisibility(View.VISIBLE);

		adapter.setListMode(optionListMode);

		optionList.setSelection(0);

		osdViewState = OSD_STATE_OPTION;
		optionView.setVisibility(View.VISIBLE);
		channelInfoTop.setVisibility(View.VISIBLE);
	}

	void optionSwitchToFilelist() {
		optionListMode = OptionAdapter.MODE_FILELIST;
		optionViewTitle.setText(getResources().getString(
				R.string.impor_user_list_title));
		optionKeyGuide.setVisibility(View.GONE);

		OptionAdapter adapter = (OptionAdapter) optionList.getAdapter();
		adapter.setFiles(userXmlFiles);
		optionList.setSelection(0);

		adapter.setListMode(optionListMode);
		adapter.notifyDataSetChanged();
	}

	void optionSwitchToChannelList() {
		optionListMode = OptionAdapter.MODE_CHANNELLIST;
		optionViewTitle
				.setText(getResources().getString(R.string.clearup_user));
		optionKeyGuide.setVisibility(View.GONE);

		ChannelGroup group = provider
				.getGroupByName(ChannelContentProvider.GROUP_NAME_USER);
		List<ChannelInfo> channels = group.getChannelInfo();

		OptionAdapter adapter = (OptionAdapter) optionList.getAdapter();
		adapter.setChannels(channels);
		optionList.setSelection(0);

		adapter.setListMode(optionListMode);
		adapter.notifyDataSetChanged();
	}

	boolean isGroupLock() {
		ChannelGroup group = provider.getGroupByIndex(curViewGroupIdx);
		return provider.isGroupLock(group);
	}

	OnItemClickListener channelOnClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View v, int position,
				long id) {
			// Log.d(TAG, "onItemClick : position = " + position);
			// Log.d(TAG, "onItemClick : channelListState = " +
			// channelListState);
			if (channelListState == CHANNEL_LIST_DECODE) {
				decodeOnKey(KeyEvent.KEYCODE_ENTER);
			} else {
				// Normal state
				if (isGroupLock()) {
					channelListSwitchToDecode();
				} else {

					lastchannel = curPlayChannelIdx;
					lastgroup = mSelectedGroup;

					
					if (!provider.getGroupByIndex(curPlayGroupIdx)
							.getChannelInfo().toString().equals("[]"))
						if (provider
								.getGroupByIndex(curPlayGroupIdx)
								.getChannelInfo()
								.get(curPlayChannelIdx)
								.getName()
								.equals(provider
										.getGroupByIndex(curViewGroupIdx)
										.getChannelInfo().get(position)
										.getName())) {
							switchToNormalOsdView();
							return;
						}
					// update index
					curViewChannelIdx = position;
					curPlayGroupIdx = curViewGroupIdx;
					curPlayChannelIdx = curViewChannelIdx;
					// update channel list view

					mSelectedGroup = provider.getGroupByIndex(curPlayGroupIdx);
					ChannelInfo info = mSelectedGroup.getChannelInfo().get(
							curPlayChannelIdx);
					onChannelSelected(info);
				}
			}
		}

	};

	TextView getDecodeTextView(int index) {
		TextView passwordTextViews[] = { password_1, password_2, password_3,
				password_4 };
		return passwordTextViews[index];
	}

	/**
	 * @param type
	 *            0 set all to no active, 1 set current index to active
	 */
	void decodeTextViewSetActive(int type) {
		for (int i = 0; i < passwordCount; i++) {
			PassWordTextView tv = (PassWordTextView) getDecodeTextView(i);
			if (type == 1) {
				if (i == passwordFocusIdx) {
					tv.setIsActive(true);
				} else {
					tv.setIsActive(false);
				}
			} else if (type == 0) {
				tv.setIsActive(false);
			}
		}
	}

	void resetPassword() {
		PassWordTextView tv;
		for (int i = 0; i < passwordCount; i++) {
			tv = (PassWordTextView) getDecodeTextView(i);
			tv.setPassWordNumber(0);
		}
		passwordFocusIdx = 0;
	}

	void channelListSwitchToDecode() {
		resetPassword();
		decodeTextViewSetActive(1);

		channelListState = CHANNEL_LIST_DECODE;
		// channelList.setFocusable(false); // FIXME : need??
		// customUnlockView.setFocusable(true);
		customUnlockView.setVisibility(View.VISIBLE);
	}

	void channelListSwitchToNormal(boolean match, boolean showMsg) {
		passwordFocusIdx = 0;
		decodeTextViewSetActive(0);

		if (!match) {
			if (showMsg) {
				// TODO : show un match message
				String str = getResources().getString(
						R.string.password_not_match);
				// Toast.makeText(PlayActivity.this, str,
				// Toast.LENGTH_LONG).show();
				showInfoDialog(str, false);
			}
		} else {
			// update list
			ChannelGroup group = provider.getGroupByIndex(curViewGroupIdx);
			ChannelAdapter adapter = (ChannelAdapter) channelList.getAdapter();
			adapter.setLock(false);
			adapter.setViewItems(group.getChannelInfo());
			adapter.notifyDataSetChanged();
		}

		channelListState = CHANNEL_LIST_NORMAL;
		// channelList.setFocusable(true); // FIXME : need??
		// customUnlockView.setFocusable(false);
		customUnlockView.setVisibility(View.INVISIBLE);
	}

	void decodeOnKey(int keyCode) {
		// Log.d(TAG, "decodeOnKey : " + keyCode);
		if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
			PassWordTextView tv = (PassWordTextView) getDecodeTextView(passwordFocusIdx++);
			// tv.passwrodPrevious();
			tv.setPassWordNumber(1);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
			PassWordTextView tv = (PassWordTextView) getDecodeTextView(passwordFocusIdx++);
			// tv.passwordNext();
			tv.setPassWordNumber(2);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			// int next = moveIndex(-1, passwordFocusIdx, passwordCount);
			// passwordFocusIdx = next;
			// decodeTextViewSetActive(1);
			PassWordTextView tv = (PassWordTextView) getDecodeTextView(passwordFocusIdx++);
			tv.setPassWordNumber(4);
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			// int next = moveIndex(1, passwordFocusIdx, passwordCount);
			// passwordFocusIdx = next;
			// decodeTextViewSetActive(1);
			PassWordTextView tv = (PassWordTextView) getDecodeTextView(passwordFocusIdx++);
			tv.setPassWordNumber(8);
		} else if (keyCode == KeyEvent.KEYCODE_ENTER) {
			/*
			 * int p1 = password_1.getPassWordNumber(); int p2 =
			 * password_2.getPassWordNumber(); int p3 =
			 * password_3.getPassWordNumber(); int p4 =
			 * password_4.getPassWordNumber(); int password = p1 * 1000 + p2 *
			 * 100 + p3 * 10 + p4; ChannelGroup group =
			 * provider.getGroupByIndex(curViewGroupIdx); boolean match =
			 * provider.checkPassword(group, password); if (match) { int index =
			 * provider.addLockGroup(group); Log.d(TAG,
			 * "decodeOnKey: oldindex = " + curViewGroupIdx); Log.d(TAG,
			 * "decodeOnKey: newindex = " + index); if (index !=
			 * curViewGroupIdx) { // index change, user group add merge to
			 * system, change condition curViewGroupIdx = index; groupCount =
			 * provider.getGroupCount(); } } channelListSwitchToNormal(match,
			 * true);
			 */
		}

		decodeTextViewSetActive(1);
		if (passwordFocusIdx == passwordCount) {
			int p1 = password_1.getPassWordNumber();
			int p2 = password_2.getPassWordNumber();
			int p3 = password_3.getPassWordNumber();
			int p4 = password_4.getPassWordNumber();
			int password = p1 * 1000 + p2 * 100 + p3 * 10 + p4;
			String passStr = Integer.toString(password);
			ChannelGroup group = provider.getGroupByIndex(curViewGroupIdx);
			boolean match = provider.checkPassword(group, passStr);

			if (match) {
				int index = provider.addLockGroup(group);
				Log.d(TAG, "decodeOnKey: oldindex = " + curViewGroupIdx);
				Log.d(TAG, "decodeOnKey: newindex = " + index);
				if (index != curViewGroupIdx) {
					// index change, user group add merge to system, change
					// condition
					curViewGroupIdx = index;
					groupCount = provider.getGroupCount();
				}
			}
			channelListSwitchToNormal(match, true);
		}
	}

	void reflashChannelListData() {
		ChannelAdapter adapter = (ChannelAdapter) channelList.getAdapter();
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	void reflashOptionListData() {
		OptionAdapter adapter = (OptionAdapter) optionList.getAdapter();
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
	}

	public void onChannelSelected(ChannelInfo info) {
		if (info == null) {
			Log.e(TAG, "select null channel");
			return;
		}
		mSelectedChannel = info;
		sourceCount = mSelectedChannel.getSources().size();
		int index = provider.getLastPlaySourceIndex(mSelectedChannel);
		if (index >= 0 && index < sourceCount)
			curViewSourceIdx = curPlaySourceIdx = index;
		else
			curViewSourceIdx = curPlaySourceIdx = mSelectedChannel
					.getCurSourceIndex();
		ChannelSource source = mSelectedChannel.getSources().get(
				curPlaySourceIdx);
		mSelectedChannel.setCurSourceIndex(curPlaySourceIdx);

		playErrorCount = 0;
		onSourceSelected(source);
		sendChannelNumberBroadcast();
		osdViewState = OSD_STATE_NORMAL;
		channelOsd.setVisibility(View.INVISIBLE);
		updateChannelInfo();

		// save to DB for recent
		provider.saveRecent(info);
		reflashChannelListData();
		Log.v("zhong", "===========onChannelSelected======end=========");
	}

	private Runnable playSourceTimeOut = new Runnable() {
		@SuppressWarnings("deprecation")
		@Override
		public void run() {
			Log.d(TAG, "play timeout!");
			synchronized (PlayActivity.this) {
				if (finished) {
					Log.d(TAG, "playSourceTimeOut, activity finished");
					return;
				}
				if(!isNetworkAvailable()){
					isshowdialognet = true;
					closeLoadingDialog();
					showDialog(DIALOG_NET_WORK_LOST);
				}else{
					onPlayErrorHandle();
				}
			}
		}
	};
	


	// 判断网络
	public boolean isNetworkAvailable() {
		ConnectivityManager cManager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cManager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			// 能联网
			return true;
		} else {
			// 不能联网
			return false;
		}
	}

//	Runnable playSource = new Runnable() {
//		public void run() {
//			showLoadingDialog();
//			sourcetext.setVisibility(View.VISIBLE);
//			source.setVisibility(View.VISIBLE);
//			mHandler.removeCallbacks(playSourceTimeOut);
//			mHandler.postDelayed(playSourceTimeOut, SOURCE_PLAY_TIMEOUT);
//			player.setPlayUrl(mSelectedSource.getUrl());
//			playSourceStartTime = System.currentTimeMillis();
//		}
//	};
	
	private void playSource(){
		showLoadingDialog();
		sourcebg.setVisibility(View.VISIBLE);
		sourcetext.setVisibility(View.VISIBLE);
		source.setVisibility(View.VISIBLE);
		mHandler.removeMessages(MSG_SEND_VDF);
		mHandler.sendEmptyMessage(MSG_SEND_VDF);
		player.setPlayUrl(mSelectedSource.getUrl());
		mHandler.removeCallbacks(playSourceTimeOut);
		mHandler.postDelayed(playSourceTimeOut, SOURCE_PLAY_TIMEOUT);
		playSourceStartTime = System.currentTimeMillis();
	}

	public void onSourceSelected(ChannelSource source) {
		if (source == null)
			return;
		mSelectedSource = source;
		
		rateinfo.setText(getResources().getString(R.string.play1_source) + (curPlaySourceIdx + 1) + "/" + sourceCount);
		mHandler.sendEmptyMessage(VideoView.PLAYERSTART);
	}

	void showOsdView() {
		if (isOsdShow)
			return;

		if (osdViewState == OSD_STATE_NORMAL) {
			Animation showAni = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 2.0f,
					Animation.RELATIVE_TO_SELF, 0.0f);
			showAni.setDuration(600);
			channelInfoBottom.startAnimation(showAni);
			channelInfoBottom.setVisibility(View.VISIBLE);
		}
		isOsdShow = true;
	}

	void hideOsdView() {
		if (!isOsdShow) {
			return;
		}

		if (osdViewState == OSD_STATE_NORMAL) {
			Animation closeAni = new TranslateAnimation(
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0.0f,
					Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
					2.0f);
			closeAni.setDuration(600);
			channelInfoBottom.startAnimation(closeAni);
			channelInfoBottom.setVisibility(View.INVISIBLE);
		}

		isOsdShow = false;
	}

	Runnable hideOsdHandler = new Runnable() {
		public void run() {
			if (osdViewState == OSD_STATE_NORMAL) {
				hideOsdView();
			} else {
				if (isConfirmDialogShow) {
					if (osdViewState != OSD_STATE_NORMAL
							&& channelListState != CHANNEL_LIST_DECODE
							&& channelListState != CHANNEL_LIST_DELRECENT) {
						mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT);
					}
				} else {
					if (channelListState != CHANNEL_LIST_DECODE
							&& channelListState != CHANNEL_LIST_DELRECENT)
						switchToNormalOsdView();
				}
			}
		};
	};
	Runnable hideEPG = new Runnable() {
		public void run() {
			hideOsdView();
		};
	};

	long getNetworkRxBytes() {
		if (mediaUid < 0) {
			return 0;
		}
		long rxBytes = TrafficStats.getUidRxBytes(mediaUid);
		if (rxBytes == TrafficStats.UNSUPPORTED) {
			rxBytes = TrafficStats.getTotalRxBytes();
		}
		return rxBytes;
	}

	void updateNetworkSpeed() {
		if (preRxBytes == 0) {
			Log.d(TAG, "update speed: Network in not availale??");
			return;
		}

		long curRxBytes = getNetworkRxBytes();
		// Log.d(TAG, "current rx byte : " + curRxBytes);
		long bytes = curRxBytes - preRxBytes;
		preRxBytes = curRxBytes;
		int kb = (int) Math.floor(bytes / 1024 + 0.5);
		String prefix = getResources().getString(R.string.speed_summery);
		String speed = prefix + ": " + Integer.toString(kb) + " K/S";
		// Log.d(TAG, "Network speed : " + speed);
		speedText.setText(speed);
		speedText.setVisibility(View.VISIBLE);
	}

	void showNetworkSpeed() {
		preRxBytes = getNetworkRxBytes();
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		if (mTimer == null) {
			mTimer = new Timer();
			mTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					// Log.d(TAG, "TimerTask run");
					Message msg = new Message();
					msg.what = MSG_UPDATE_SPEED;
					mHandler.sendMessage(msg);
				}
			}, 1000, 1000);
		}
	}

	void closeNetworkSpeed() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		speedText.setText("");
		speedText.setVisibility(View.INVISIBLE);
	}
	
	private void playStop(){
		mHandler.sendEmptyMessage(VideoView.PLAYERSTOP);
	}
	private void playStopAsyn(){
		player.stopPlay();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (PlayActivity.this.finished) {
				return;
			}
			
			switch(msg.what){
			case PlayActivity.MSG_HIDE_OSD:
				hideOsdView();
				break;
			case PlayActivity.MSG_UPDATE_SPEED:
				updateNetworkSpeed();
				break;
			case PlayActivity.MSG_SEND_VDF:
				sendVdfHandler();
				break;
			case PlayActivity.MSG_UPDATE:
				checkUpdateHandler();
				break;
			case VideoView.PLAYERSTART:
				playSource();
				break;
			case VideoView.PLAYERSTOP:
				playStopAsyn();
				break;
			case PlayerCallback.ONPLAYSURFACECREATE:
				onPlaySurfaceCreateHandle();
				break;
			case PlayerCallback.ONPLAYSURFACEDESTROY:
				onPlaySurfaceDestroyHandle();
				break;
			case PlayerCallback.ONPLAYPREPARE:
				onPlayPrepareHandle();
				break;
			case PlayerCallback.ONPLAYFAIL:
				onPlayErrorHandle();
				break;
			case PlayerCallback.ONPLAYBUFFERINGSTART:
				onPlayBufferingstartHandle();
				break;
			case PlayerCallback.ONPLAYBUFFERINGEND:
				onPlayBufferingendHandle();
				break;
			}
		};
	};

	public void sendMessage(Message msg) {
		mHandler.sendMessage(msg);
	}

	@Override
	protected void onStart() {
//		 try{
//		 TvManager.getInstance().getPictureManager().setAspectRatio(EnumVideoArcType.E_16x9);
//		 }catch(TvCommonException e){
//		 e.printStackTrace();
//		 }
//		setVideoRatio(0);
//		isFirst_yyc = true;
		super.onStart();
	}

	@Override
	protected void onStop() {
//		mHandler.removeCallbacks(userInputNumberRunnable);
//		mHandler.removeCallbacks(channelSelectedRun);
//		player.stopPlayHandle();
//		closeNetworkSpeed();
//		doFinish();
		
		
		player.stopPlay();
		doFinish();
		
		super.onStop();
	}

	@Override
	protected void onPause() {
//		player.stopPlay();
		player.stopPlayHandle();
		closeNetworkSpeed();
		super.onPause();
	}

	/**
	 * Move index to next
	 * 
	 * @param direction
	 *            decrease if (<0), increase if (>0)
	 * @param current
	 * @param count
	 * @return
	 */
	int moveIndex(int direction, int current, int count) {
		if (count == 1)
			return current;

		int next = current;
		if (direction < 0) {
			if (current == 0) {
				next = count - 1;
			} else {
				next = current - 1;
			}
		} else if (direction > 0) {
			if (current == count - 1) {
				next = 0;
			} else {
				next = current + 1;
			}
		}
		return next;
	}

	int moveIndexfordel(int direction, int current, int count) {
		if (count == 1)
			return current;

		int next = current;
		if (direction < 0) {
			if (current == 0) {
				next = count - 1;
			} else {
				next = current - 1;
			}
		} else if (direction > 0) {
			next = current + 1;
		}
		return next;
	}

	/**
	 * Group move
	 * 
	 * @param direction
	 *            decrease if (<0), increase if (>0)
	 */
	public void onGroupMove(int direction) {
		if (groupCount == 1)
			return;

		int nextIndex = moveIndex(direction, curViewGroupIdx, groupCount);
		Log.d(TAG, "group move from " + curViewGroupIdx + " to " + nextIndex);
		curViewGroupIdx = nextIndex;

		ChannelGroup viewGroup = provider.getGroupByIndex(curViewGroupIdx);

		if ((getDiffUserSetting() == 1)
				&& viewGroup.getFrom() == ChannelContentProvider.XML_FROM_USER) {
			channelGroupName.setTextColor(getResources().getColor(
					R.color.userTextColor));
		} else {
			channelGroupName.setTextColor(getResources().getColor(
					R.color.offWhite));
		}

		channelGroupName.setText(viewGroup.getName());
		List<ChannelInfo> channels = viewGroup.getChannelInfo();
		channelCount = (channels != null) ? channels.size() : 0;

		if (viewGroup == mSelectedGroup) {
			curViewChannelIdx = curPlayChannelIdx;
		} else {
			curViewChannelIdx = 0;
		}

		if (curViewChannelIdx >= channelCount) {
			curViewChannelIdx = 0;
		}

		channelList.setSelection(curViewChannelIdx);
		ChannelAdapter adapter = (ChannelAdapter) channelList.getAdapter();
		adapter.setViewItems(viewGroup.getChannelInfo());

		if (isGroupLock()) {
			adapter.setLock(true);
		} else {
			adapter.setLock(false);
		}

		adapter.notifyDataSetChanged();
	}

	/**
	 * Channel move
	 * 
	 * @param direction
	 *            decrease if (<0), increase if (>0)
	 */
	public void onChannelMove(int direction) {
		if (channelCount <= 1)
			return;
		if (direction < 0 && curViewChannelIdx == 0) {
			return;
		}
		if (direction > 0 && (curViewChannelIdx == channelCount - 1)) {
			return;
		}
		int nextIndex = moveIndex(direction, curViewChannelIdx, channelCount);
		Log.d(TAG, "channel move from " + curViewChannelIdx + " to "
				+ nextIndex);
		curViewChannelIdx = nextIndex;

		channelList.setSelection(curViewChannelIdx);
	}

	/**
	 * Channel change
	 * 
	 * @param direction
	 *            decrease if (<0), increase if (>0)
	 * @param sync
	 *            play directly if true
	 */
	public void onChannelChange(int direction, boolean sync) {
		
		if (mSelectedGroup == null) {
			return;
		}
		channelCount = mSelectedGroup.getChannelInfo().size();
		if (channelCount <= 1) {
			Log.d(TAG, "Channel count <= 1");
			return;
		}
		channelCount = mSelectedGroup.getChannelInfo().size();

		int nextIndex = moveIndex(direction, curPlayChannelIdx, channelCount);
		lastchannel = curPlayChannelIdx;
		lastgroup = mSelectedGroup;
		curPlayChannelIdx = nextIndex;
		curViewChannelIdx = nextIndex;
		channelList.setSelection(curViewChannelIdx);
		if (sync) {
			ChannelInfo info = mSelectedGroup.getChannelInfo().get(
					curPlayChannelIdx);
			onChannelSelected(info);
		} else {
			if (osdViewState != OSD_STATE_NORMAL) {
				switchToNormalOsdView();
			}
			mHandler.removeCallbacks(channelSelectedRun);
			mHandler.postDelayed(channelSelectedRun, CHANNEL_CHANGE_TIMEOUT);
		}

		showNumberPrompt();
	}

	Runnable channelSelectedRun = new Runnable() {
		@Override
		public void run() {
			if (osdViewState != OSD_STATE_NORMAL) {
				switchToNormalOsdView();
			}
			ChannelInfo info = mSelectedGroup.getChannelInfo().get(
					curPlayChannelIdx);
			onChannelSelected(info);
		}

	};

	void onFavoriteKey() {
		int selected = channelList.getSelectedItemPosition();
		Log.d(TAG, "onFavoriteKey select : " + selected);
		if (selected < 0) {
			return;
		}
		ChannelGroup group = provider.getGroupByIndex(curViewGroupIdx);
		ChannelInfo info = group.getChannelInfo().get(selected);

		if (group.getName().equals(
				provider.getGroupByName(provider.GROUP_NAME_RECENT).getName())) {
			channelListState = CHANNEL_LIST_DELRECENT;
			// groupName = group.getName() ;
			// channelNmae = info.getName() ;
			// mHandler.removeCallbacks(hideOsdHandler);
			recentSelectchannel = info;
			showDialog(DIALOG_CHANNEL_DEL_INFO);
		} else {
			Log.v("yanc", "=====info===========" + info.getName());
			boolean isFavorite = !info.getIsFavorite();
			info.setIsFavorite(isFavorite);
			// save to db
			provider.saveFavorite(info, isFavorite);
			// Notify ui change
			reflashChannelListData();
		}
	}
	
//	private void optionChangeHandler(){
//		if (curPlaySourceIdx != curViewSourceIdx) {
//			curPlaySourceIdx = curViewSourceIdx;
//			mSelectedChannel.setCurSourceIndex(curPlaySourceIdx);
//			ChannelSource source = mSelectedChannel.getSources().get(
//					curPlaySourceIdx);
//			playErrorCount = 0;
//			onSourceSelected(source);
//		}
//	}

	Runnable optionChangeHandler = new Runnable() {
		public void run() {
			if (curPlaySourceIdx != curViewSourceIdx) {
				curPlaySourceIdx = curViewSourceIdx;
				mSelectedChannel.setCurSourceIndex(curPlaySourceIdx);
				ChannelSource source = mSelectedChannel.getSources().get(
						curPlaySourceIdx);
				playErrorCount = 0;
				onSourceSelected(source);
			}
		};
	};

	void onOptionChange(int direction) {
		if (optionListMode == OptionAdapter.MODE_FILELIST
				|| optionListMode == OptionAdapter.MODE_CHANNELLIST) {
			return;
		}

		int selected = optionList.getSelectedItemPosition();
		if (selected < 0 || selected >= OptionAdapter.OPTION_COUNT) {
			return;
		}

		mOptionSelectd = selected;
		if (selected == OptionAdapter.OPTION_ITEM_SOURCE) { /* Play source */
			if (sourceCount == 1)
				return;
			int next = moveIndex(direction, curViewSourceIdx, sourceCount);
			((OptionAdapter) optionList.getAdapter()).setSourceIndex(next);
			curViewSourceIdx = next;
			mHandler.removeCallbacks(optionChangeHandler);
			mHandler.postDelayed(optionChangeHandler, SOURCE_SWITCH_TIMEOUT);
		} else if (selected == OptionAdapter.OPTION_ITEM_RATIO) { /* ratio, */
			/* HACK: only tow now */
//			int next = moveIndex(direction, curViewVideoRatioIdx,
//					OptionAdapter.VIDEO_RATIO.length);
			int next = player.swicthDisplayMode();
			curViewVideoRatioIdx = next;
			((OptionAdapter) optionList.getAdapter())
					.setRatioIndex(curViewVideoRatioIdx);
			curVieoRatioIdx = curViewVideoRatioIdx;
			mSelectedChannel.setVideoRatioIndex(curVieoRatioIdx);
			if(player.mDecoderType == 1){
				Log.v("lol","=========type.mCurrentMode===========");
				applyVideoRatio(player.mCurrentMode);
			}
			rateinfo.setText(getResources().getString(R.string.rate)
					+ OptionAdapter.VIDEO_RATIO[curViewVideoRatioIdx]);
//			setVideoRatio(curVieoRatioIdx);
		} 
		else if (selected == OptionAdapter.OPTION_ITEM_MEDIATYPE) { /* favorite */
			showLoadingDialog();
			int next = player.swicthMediaDecodeMode();
			this.mediadecodetype = next;
			((OptionAdapter) optionList.getAdapter())
			.setMediaType(mediadecodetype);
		} 
		else if (selected == OptionAdapter.OPTION_ITEM_FAVORITE) { /* favorite */
			channelIsFavorite = !channelIsFavorite;
			((OptionAdapter) optionList.getAdapter())
			.setIsFavorite(channelIsFavorite);
			mSelectedChannel.setIsFavorite(channelIsFavorite);
			// save status
			provider.saveFavorite(mSelectedChannel, channelIsFavorite);
			// update channel list
			reflashChannelListData();
		} 
		
		else if (selected == OptionAdapter.OPTION_ITEM_CLEARUSER) { /*
																	 * Clear
																	 * user
																	 */
			/*
			 * if (curViewClearUserIdx == 0) { curViewClearUserIdx = 1; } else
			 * if (curViewClearUserIdx == 1){ curViewClearUserIdx = 0; }
			 * ((OptionAdapter
			 * )optionList.getAdapter()).setClearUserIndex(curViewClearUserIdx);
			 */
		} else if (selected == OptionAdapter.OPTION_ITEM_DIFFUSER) { /* Diff user */
			if (curViewDiffUserIdx == 0) {
				curViewDiffUserIdx = 1;
			} else if (curViewDiffUserIdx == 1) {
				curViewDiffUserIdx = 0;
			}
			curDiffUserIdx = curViewDiffUserIdx;
			setDiffUserSetting(curDiffUserIdx);
			OptionAdapter optionAdapter = (OptionAdapter) optionList
					.getAdapter();
			if (optionAdapter != null) {
				optionAdapter.setDiffUserIndex(curDiffUserIdx);
				optionAdapter.setDiffUser(curDiffUserIdx == 1);
				optionAdapter.notifyDataSetChanged();
			}

			ChannelAdapter listAdapter = (ChannelAdapter) channelList
					.getAdapter();
			if (listAdapter != null) {
				listAdapter.setDiffUser(curDiffUserIdx == 1);
				listAdapter.notifyDataSetChanged();
			}

			return;
		} else if (selected == OptionAdapter.OPTION_ITEM_SHOWSPEED) {
			boolean isShow = !isShowNetworkSpeed;
			setShowNetworkSpeedSetting(isShow);
			((OptionAdapter) optionList.getAdapter()).setShowSpeed(isShow);
			if (isShow) {
				showNetworkSpeed();
			} else {
				closeNetworkSpeed();
			}
			isShowNetworkSpeed = isShow;
		}

		((BaseAdapter) optionList.getAdapter()).notifyDataSetChanged();
	}

	void optionOnClearUserSelected() {
		Log.d(TAG, "Clear user select");
		if (hasUserChannel()) {
			provider.removeUserGroup();

			removeSavedUserPlaylistFile();

			boolean changeChannel = false;
			// reset channel list
			groupCount = provider.getGroupCount();
			if (mSelectedGroup.getFrom() == ChannelContentProvider.XML_FROM_USER) {
				// Change to groupAll
				curViewGroupIdx = curPlayGroupIdx = provider
						.getGroupIndexByName(ChannelContentProvider.GROUP_NAME_ALL);
				mSelectedGroup = provider.getGroupByIndex(curViewGroupIdx);
				curPlayChannelIdx = curViewChannelIdx = 0;
				mSelectedChannel = mSelectedGroup.getChannelInfo().get(
						curPlayChannelIdx);
				channelCount = mSelectedGroup.getChannelInfo().size();
				changeChannel = true;
			} else {
				channelCount = mSelectedGroup.getChannelInfo().size();
				if (mSelectedChannel.getFrom() == ChannelContentProvider.XML_FROM_USER) {
					// get the first channel which is not user
					int i;
					ChannelInfo channel;
					for (i = 0; i < mSelectedGroup.getChannelInfo().size(); i++) {
						channel = mSelectedGroup.getChannelInfo().get(i);
						if (channel.getFrom() != ChannelContentProvider.XML_FROM_USER) {
							break;
						}
					}

					curPlayChannelIdx = curViewChannelIdx = i;
					mSelectedChannel = mSelectedGroup.getChannelInfo().get(
							curPlayChannelIdx);
					changeChannel = true;
				} else {
					// update index
					// curPlayChannelIdx = curViewChannelIdx =
					// provider.getChannelIndexById(mSelectedGroup,
					// mSelectedChannel.getIdNumber());
					curPlayChannelIdx = curViewChannelIdx = provider
							.getChannelIndexByNumber(mSelectedGroup,
									mSelectedChannel.getChannelNumber());
				}
			}

			sourceCount = mSelectedChannel.getSources().size();
			if (changeChannel) {
				int index = provider.getLastPlaySourceIndex(mSelectedChannel);
				if (index >= 0 && index < sourceCount)
					curViewSourceIdx = curPlaySourceIdx = index;
				else
					curViewSourceIdx = curPlaySourceIdx = mSelectedChannel
							.getCurSourceIndex();
				mSelectedSource = mSelectedChannel.getSources().get(
						curPlaySourceIdx);
				mSelectedChannel.setCurSourceIndex(curPlaySourceIdx);
				playSelectedChannel();
				updateChannelInfo();
			}
		} else {
			String str = getResources().getString(R.string.no_user_channel);
			showInfoDialog(str, false);
		}
	}

	void onOptionConfirm() {
		int selected = optionList.getSelectedItemPosition();

		if (optionListMode == OptionAdapter.MODE_FUNCTION) {
			if (selected == OptionAdapter.OPTION_ITEM_CLEARUSER) {
				optionOnClearUserSelected();
			} else if (selected == OptionAdapter.OPTION_ITEM_CLEARUPUSER) {
				Log.d(TAG, "Clear up user select");
				if (hasUserChannel()) {
					optionSwitchToChannelList();
					return;
				} else {
					String str = getResources().getString(
							R.string.no_user_channel);
					showInfoDialog(str, false);
				}
				return;
			} else if (selected == OptionAdapter.OPTION_ITEM_IMPORTUSER) {
				Log.d(TAG, "Import user select");
				if (onImportUserOptionSelected()) {
					optionSwitchToFilelist();
					return;
				} else {
					switchToNormalOsdView();
					showInfoDialog(
							getResources().getString(R.string.no_user_file),
							false);
				}
			}
		} else if (optionListMode == OptionAdapter.MODE_FILELIST) {
			if (userXmlFiles == null || userXmlFiles.size() == 0) {
				Log.w(TAG, "User xml files empty!!??");
				switchToNormalOsdView();
				return;
			}

			File file = userXmlFiles.get(selected);
			String msg;
			if (onUserXmlFileSelected(file)) {
				msg = getResources().getString(R.string.import_success);
			} else {
				msg = getResources().getString(R.string.import_fail);
			}
			showInfoDialog(msg, false);
		} else if (optionListMode == OptionAdapter.MODE_CHANNELLIST) {
			selectedUserChannel = provider
					.getGroupByName(ChannelContentProvider.GROUP_NAME_USER)
					.getChannelInfo().get(selected);
			showUserSelectDialog("delete_user");
		}

		switchToNormalOsdView();
	}

	void onDeleteUserChannel() {
		provider.deleteUserChannel(selectedUserChannel);
		groupCount = provider.getGroupCount();
		boolean changeChannel = false;
		if (provider.getUserGroupIndex(mSelectedGroup) < 0) {
			// current playing group is removed
			// Move to groupAll
			curViewGroupIdx = curPlayGroupIdx = provider
					.getGroupIndexByName(ChannelContentProvider.GROUP_NAME_ALL);
			mSelectedGroup = provider.getGroupByIndex(curViewGroupIdx);
			curPlayChannelIdx = curViewChannelIdx = 0;
			mSelectedChannel = mSelectedGroup.getChannelInfo().get(
					curPlayChannelIdx);
			channelCount = mSelectedGroup.getChannelInfo().size();
			changeChannel = true;
		} else {
			channelCount = mSelectedGroup.getChannelInfo().size();
			if (selectedUserChannel.getNameHashCode() == mSelectedChannel
					.getNameHashCode()) {
				// current playing channel is removed
				// move to next
				if (curPlayChannelIdx >= channelCount) {
					curPlayChannelIdx = curViewChannelIdx = channelCount - 1;
				}

				mSelectedChannel = mSelectedGroup.getChannelInfo().get(
						curPlayChannelIdx);
				changeChannel = true;
			} else {
				// curPlayChannelIdx = curViewChannelIdx =
				// provider.getChannelIndexById(mSelectedGroup,
				// mSelectedChannel.getIdNumber());
				curPlayChannelIdx = curViewChannelIdx = provider
						.getChannelIndexByNumber(mSelectedGroup,
								mSelectedChannel.getChannelNumber());
			}
		}

		sourceCount = mSelectedChannel.getSources().size();
		if (changeChannel) {
			int index = provider.getLastPlaySourceIndex(mSelectedChannel);
			if (index >= 0 && index < sourceCount)
				curViewSourceIdx = curPlaySourceIdx = index;
			else
				curViewSourceIdx = curPlaySourceIdx = mSelectedChannel
						.getCurSourceIndex();
			mSelectedSource = mSelectedChannel.getSources().get(
					curPlaySourceIdx);
			mSelectedChannel.setCurSourceIndex(curPlaySourceIdx);

			playSelectedChannel();
			updateChannelInfo();
		}

		selectedUserChannel = null;
	}

	boolean hasUserChannel() {
		ChannelGroup group = provider
				.getGroupByName(ChannelContentProvider.GROUP_NAME_USER);
		if (group == null || group.getChannelInfo() == null) {
			return false;
		}

		if (group.getChannelInfo().size() == 0)
			return false;

		return true;
	}

	boolean onImportUserOptionSelected() {
		String usbPath = FileUtils.getUsbPath();
		Log.v("test","======================="+usbPath);
		File usbRoot = new File(usbPath);
		if (!usbRoot.exists()) {
			Log.v("test","====!usbRoot.exists()===================");
			return false;
		}

		userXmlFiles = new ArrayList<File>();

		File[] srcFiles = usbRoot.listFiles();
		File file;
		for (int i = 0; i < srcFiles.length; i++) {
			file = srcFiles[i];
			if (file.isFile() && file.getName().endsWith("xml")) {
				userXmlFiles.add(file);
			}
		}
		Log.v("test","====userXmlFiles.size()==================="+userXmlFiles.size());
		if (userXmlFiles.size() == 0)
			return false;

		return true;
	}

	boolean onUserXmlFileSelected(File xmlFile) {
		List<ChannelGroup> groups = parseUserGroupFromFile(xmlFile);
		if (groups == null || groups.size() == 0) {
			return false;
		}

		provider.addUserGroups(groups);
		groupCount = provider.getGroupCount();

		return true;
	}

	void doFinish() {
//		synchronized (PlayActivity.this) {
			finished = true;
			finish();
//		}
	}

	private void selectPosition() {

		int selectposition = 0;
		int listcount = 0;
		ChannelGroup viewGroup = provider.getGroupByIndex(curViewGroupIdx);
		selectposition = channelList.getSelectedItemPosition();
		listcount = viewGroup.getChannelInfo().size();

		listcount = listcount - 1;

		if (listcount == selectposition) {
			channelList.setSelection(0);
		} else if (0 == selectposition) {
			channelList.setSelection(viewGroup.getChannelInfo().size() - 1);
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean handled = false;
		if (mSelectedGroup == null && keyCode != KeyEvent.KEYCODE_BACK) {
			return false;
		}
		switch(keyCode){
    	case KeyEvent.KEYCODE_BACK:
    		if (osdViewState == OSD_STATE_NORMAL) {
				showDialog(DIALOG_PLAY_EXIT_INFO);
				return true;
			} else if (osdViewState == OSD_STATE_BROWSER) {
				if (channelListState == CHANNEL_LIST_DECODE) {
					channelListSwitchToNormal(false, false);
				} else {
					switchToNormalOsdView();
				}
				handled = true;
			} else if (osdViewState == OSD_STATE_OPTION) {
				switchToNormalOsdView();
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_MENU:
    		if (osdViewState == OSD_STATE_NORMAL) {
				switchToChannelOptionView();
				handled = true;
			} else if (osdViewState == OSD_STATE_BROWSER) {
				onFavoriteKey();
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_DPAD_UP:
    		
    		if (osdViewState == OSD_STATE_BROWSER) {
				if (channelListState == CHANNEL_LIST_DECODE) {
					decodeOnKey(keyCode);
					handled = true;
				} else {
					selectPosition();
				}
			} else if (osdViewState == OSD_STATE_NORMAL) {
				
				onChannelChange(1, false);
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_DPAD_DOWN:
    		if (osdViewState == OSD_STATE_BROWSER) {
				if (channelListState == CHANNEL_LIST_DECODE) {
					decodeOnKey(keyCode);
					handled = true;
				} else {
					selectPosition();
				}
			} else if (osdViewState == OSD_STATE_NORMAL) {
				onChannelChange(-1, false);
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_DPAD_LEFT:
    		if (osdViewState == OSD_STATE_BROWSER) {
				if (channelListState == CHANNEL_LIST_DECODE) {
					decodeOnKey(keyCode);
					handled = true;
				} else {
					onGroupMove(-1);
					handled = true;
				}
			} else if (osdViewState == OSD_STATE_OPTION) {
				onOptionChange(-1);
				handled = true;
			} else if (osdViewState == OSD_STATE_NORMAL) {
				adjustVoice(keyCode);
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_DPAD_RIGHT:
			if (osdViewState == OSD_STATE_BROWSER) {
				if (channelListState == CHANNEL_LIST_DECODE) {
					decodeOnKey(keyCode);
					handled = true;
				} else {
					onGroupMove(1);
					handled = true;
				}
			} else if (osdViewState == OSD_STATE_OPTION) {
				onOptionChange(1);
				handled = true;
			} else if (osdViewState == OSD_STATE_NORMAL) {
				adjustVoice(keyCode);
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_ENTER:
    	case KeyEvent.KEYCODE_DPAD_CENTER:
    		if (osdViewState == OSD_STATE_BROWSER) {
				if (channelListState == CHANNEL_LIST_DECODE) {
					decodeOnKey(keyCode);
				}
				handled = true;
			} else if (osdViewState == OSD_STATE_NORMAL) {
				switchToChannelListView();
				handled = true;
			} else if (osdViewState == OSD_STATE_OPTION) {
				onOptionConfirm();
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_F9:
    		if (osdViewState == OSD_STATE_NORMAL) {
				changeScanl();
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_F10:
    		if (osdViewState == OSD_STATE_NORMAL) {
				changeSources(1);
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_NUMPAD_DOT:
    		if(!isshowdot){
    			if (osdViewState == OSD_STATE_NORMAL) {
    				showOsdView();
    				isshowdot = true;
    				mHandler.removeCallbacks(hideEPG);
    				handled = true;
    			}
    		}else{
    			hideOsdView();
    			isshowdot = false;
    		}
    		break;
    	case KeyEvent.KEYCODE_DEL:
    		if (osdViewState == OSD_STATE_NORMAL) {
				playlastchannel();
				handled = true;
			}
    		break;
    	case KeyEvent.KEYCODE_0:
    	case KeyEvent.KEYCODE_1:
    	case KeyEvent.KEYCODE_2:
    	case KeyEvent.KEYCODE_3:
    	case KeyEvent.KEYCODE_4:
    	case KeyEvent.KEYCODE_5:
    	case KeyEvent.KEYCODE_6:
    	case KeyEvent.KEYCODE_7:
    	case KeyEvent.KEYCODE_8:
    	case KeyEvent.KEYCODE_9:
    		if (osdViewState == OSD_STATE_NORMAL) {
				handled = onNumberKey(keyCode);
			}
    		break;
		}
		
		
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if (osdViewState == OSD_STATE_NORMAL) {
//				showDialog(DIALOG_PLAY_EXIT_INFO);
//				return true;
//			} else if (osdViewState == OSD_STATE_BROWSER) {
//				if (channelListState == CHANNEL_LIST_DECODE) {
//					channelListSwitchToNormal(false, false);
//				} else {
//					switchToNormalOsdView();
//				}
//				handled = true;
//			} else if (osdViewState == OSD_STATE_OPTION) {
//				switchToNormalOsdView();
//				handled = true;
//			}
//		} else if (keyCode == KeyEvent.KEYCODE_MENU) {
//			if (osdViewState == OSD_STATE_NORMAL) {
//				switchToChannelOptionView();
//				handled = true;
//			} else if (osdViewState == OSD_STATE_BROWSER) {
//				onFavoriteKey();
//				handled = true;
//			}
//		} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) { // Up key
//			if (osdViewState == OSD_STATE_BROWSER) {
//				if (channelListState == CHANNEL_LIST_DECODE) {
//					decodeOnKey(keyCode);
//					handled = true;
//				} else {
//					selectPosition();
//				}
//			} else if (osdViewState == OSD_STATE_NORMAL) {
//				onChannelChange(1, false);
//				handled = true;
//			}
//		} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) { // Down Key
//			if (osdViewState == OSD_STATE_BROWSER) {
//				if (channelListState == CHANNEL_LIST_DECODE) {
//					decodeOnKey(keyCode);
//					handled = true;
//				} else {
//					selectPosition();
//				}
//			} else if (osdViewState == OSD_STATE_NORMAL) {
//				onChannelChange(-1, false);
//				handled = true;
//			}
//		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
//			if (osdViewState == OSD_STATE_BROWSER) {
//				if (channelListState == CHANNEL_LIST_DECODE) {
//					decodeOnKey(keyCode);
//					handled = true;
//				} else {
//					onGroupMove(-1);
//					handled = true;
//				}
//			} else if (osdViewState == OSD_STATE_OPTION) {
//				onOptionChange(-1);
//				handled = true;
//			} else if (osdViewState == OSD_STATE_NORMAL) {
//				adjustVoice(keyCode);
//				handled = true;
//			}
//		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
//			if (osdViewState == OSD_STATE_BROWSER) {
//				if (channelListState == CHANNEL_LIST_DECODE) {
//					decodeOnKey(keyCode);
//					handled = true;
//				} else {
//					onGroupMove(1);
//					handled = true;
//				}
//			} else if (osdViewState == OSD_STATE_OPTION) {
//				onOptionChange(1);
//				handled = true;
//			} else if (osdViewState == OSD_STATE_NORMAL) {
//				adjustVoice(keyCode);
//				handled = true;
//			}
//		} else if (keyCode == KeyEvent.KEYCODE_ENTER) {
//			if (osdViewState == OSD_STATE_BROWSER) {
//				if (channelListState == CHANNEL_LIST_DECODE) {
//					decodeOnKey(keyCode);
//				}
//				handled = true;
//			} else if (osdViewState == OSD_STATE_NORMAL) {
//				switchToChannelListView();
//				handled = true;
//			} else if (osdViewState == OSD_STATE_OPTION) {
//				onOptionConfirm();
//				handled = true;
//			}
//		} else if (keyCode >= KeyEvent.KEYCODE_0
//				&& keyCode <= KeyEvent.KEYCODE_9) {
//			if (osdViewState == OSD_STATE_NORMAL) {
//				handled = onNumberKey(keyCode);
//			}
//		} else if (keyCode == KeyEvent.KEYCODE_F9) {
//			if (osdViewState == OSD_STATE_NORMAL) {
//				// scanl
//				// showToast(this,"正在切换视频源！");
//				changeScanl();
//				// showToast(this,"比例切换成功！");
//				handled = true;
//			}
//
//		} else if (keyCode == KeyEvent.KEYCODE_F10) {
//
//			if (osdViewState == OSD_STATE_NORMAL) {
//				changeSources(1);
//				handled = true;
//			}
//		} else if (keyCode == KeyEvent.KEYCODE_NUMPAD_DOT) {
//
//			if (osdViewState == OSD_STATE_NORMAL) {
//				showOsdView();
//				mHandler.removeCallbacks(hideEPG);
//				handled = true;
//			}
//		} else if (keyCode == KeyEvent.KEYCODE_DEL) {
//
//			if (osdViewState == OSD_STATE_NORMAL) {
//				playlastchannel();
//				handled = true;
//			}
//		}

		if (osdViewState != OSD_STATE_NORMAL
				&& channelListState != CHANNEL_LIST_DECODE
				&& channelListState != CHANNEL_LIST_DELRECENT) {
			mHandler.removeCallbacks(hideOsdHandler);
		}

		if (handled)
			return true;

		return super.onKeyDown(keyCode, event);
	}

	Runnable runableshownumberPrompt = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			numberPrompt.setVisibility(View.INVISIBLE);
		}
	};

	private void playlastchannel() {

		mHandler.removeCallbacks(runableshownumberPrompt);
		mHandler.removeCallbacks(hideNumberPromptRunnable);
		numberText.setText("返回上一个频道");
		numberText.setTextSize(28);
		nameText.setText("");
		numberPrompt.setVisibility(View.VISIBLE);

		mHandler.postDelayed(runableshownumberPrompt, 3000L);

		tempgroup = mSelectedGroup;
		tempchannel = curPlayChannelIdx;
		if (lastgroup == null) {
			numberText.setText("无可返回的频道");
			return;
		}
		mSelectedGroup = lastgroup;
		curPlayChannelIdx = lastchannel;
		lastgroup = tempgroup;
		lastchannel = tempchannel;
		ChannelInfo info = mSelectedGroup.getChannelInfo().get(
				curPlayChannelIdx);
		onChannelSelected(info);
	}

	/**
	 * 
	 * @method showToast
	 * @version 1.0
	 * @param context
	 * @param text
	 * @return_type void
	 * @function
	 * @updateInfo
	 * @createTime 2013-10-17,上午11:23:01
	 * @updateTime 2013-10-17,上午11:23:01
	 * @createAuthor 剑圣 BM
	 * @updateAuthor 剑圣 BM
	 * 
	 */
	public void showToast(Context context, String text) {
		Toast toast = null;
		toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
		// toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0,
		// DensityUtil.dip2px(context, 80));
		toast.show();
	}

	/**
	 * 
	 * @method changeScanl
	 * @version 1.0
	 * @return_type void
	 * @function
	 * @updateInfo
	 * @createTime 2013-10-17,上午11:23:08
	 * @updateTime 2013-10-17,上午11:23:08
	 * @createAuthor 剑圣 BM
	 * @updateAuthor 剑圣 BM
	 * 
	 */
	private void changeScanl() {
		if (mSelectedGroup == null) {
			return;
		}
		int next = player.swicthDisplayMode();
		curViewVideoRatioIdx = next;

		curVieoRatioIdx = curViewVideoRatioIdx;
		mSelectedChannel.setVideoRatioIndex(curVieoRatioIdx);
		
		if(player.mDecoderType == 1){
			Log.v("lol","=========type.mCurrentMode===========");
			applyVideoRatio(player.mCurrentMode);
		}
		
		rateinfo.setText(getResources().getString(R.string.rate)
				+ OptionAdapter.VIDEO_RATIO[curViewVideoRatioIdx]);
		showrateView();
		setVideoRatio(curVieoRatioIdx);
		mHandler.removeCallbacks(hideRateView);
		mHandler.postDelayed(hideRateView, 3000L);
	}

	/**
	 * 
	 * @method showrateView
	 * @version 1.0
	 * @return_type void
	 * @function
	 * @updateInfo
	 * @createTime 2013-10-17,下午7:22:46
	 * @updateTime 2013-10-17,下午7:22:46
	 * @createAuthor 剑圣 BM
	 * @updateAuthor 剑圣 BM
	 * 
	 */
	private void showrateView() {
		if (rateinfo.getVisibility() == View.INVISIBLE
				|| rateinfo.getVisibility() == View.GONE) {
			rateinfo.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * 
	 * @method hiderateView
	 * @version 1.0
	 * @return_type void
	 * @function
	 * @updateInfo
	 * @createTime 2013-10-17,下午7:23:13
	 * @updateTime 2013-10-17,下午7:23:13
	 * @createAuthor 剑圣 BM
	 * @updateAuthor 剑圣 BM
	 * 
	 */
	private void hiderateView() {
		if (rateinfo.getVisibility() == View.VISIBLE) {
			rateinfo.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * 
	 * @method changeSources
	 * @version 1.0
	 * @param direction
	 * @return_type void
	 * @function
	 * @updateInfo
	 * @createTime 2013-10-17,上午11:21:03
	 * @updateTime 2013-10-17,上午11:21:03
	 * @createAuthor 剑圣 BM
	 * @updateAuthor 剑圣 BM
	 * 
	 */
	private void changeSources(int direction) {
		if (mSelectedGroup == null) {
			return;
		}
		if (sourceCount == 1) {
			return;
		}
		int next = moveIndex(direction, curViewSourceIdx, sourceCount);
		curViewSourceIdx = next;
		showrateView();
		mHandler.removeCallbacks(optionChangeHandler);
		mHandler.postDelayed(optionChangeHandler, SOURCE_SWITCH_TIMEOUT);
		int temp = curPlaySourceIdx + 2;
		if (temp > sourceCount) {
			temp = 1;
		}
		rateinfo.setText(getResources().getString(R.string.play1_source) + temp
				+ "/" + sourceCount);
		mHandler.removeCallbacks(hideRateView);
		mHandler.postDelayed(hideRateView, 3000L);
	}

	boolean onNumberKey(int keyCode) {
		synchronized (PlayActivity.this) {
			mHandler.removeCallbacks(userInputNumberRunnable);
			if ((userNumberCount + 1) <= 3) {
				userNumber[userNumberCount++] = keyCode - KeyEvent.KEYCODE_0;
			} else {
				userNumberCount = 0;
				userNumber[userNumberCount++] = keyCode - KeyEvent.KEYCODE_0;
			}
			Log.d(TAG, "userNumberCount = " + userNumberCount);
			int number = 0;
			if (userNumberCount == 1) {
				number = userNumber[0];
			} else if (userNumberCount == 2) {
				number = userNumber[0] * 10 + userNumber[1];
			} else if (userNumberCount == 3) {
				number = userNumber[0] * 100 + userNumber[1] * 10
						+ userNumber[2];
			}

			Log.d(TAG, "User input number = " + number);
			ChannelGroup groupAll = provider
					.getGroupByName(ChannelContentProvider.GROUP_NAME_ALL);
			// userSelectIndex = provider.getChannelIndexById(groupAll, number);
			userSelectIndex = provider
					.getChannelIndexByNumber(groupAll, number);
			numberText.setText(Integer.toString(number));
			if (userSelectIndex >= 0 && groupAll.getChannelInfo() != null) {
				ChannelInfo channel = groupAll.getChannelInfo().get(
						userSelectIndex);
				userSelectChannel = channel;
				nameText.setText(channel.getName());
			} else {
				userSelectChannel = null;
				nameText.setText("");
			}
			mHandler.removeCallbacks(runableshownumberPrompt);
			numberPrompt.setVisibility(View.VISIBLE);

			mHandler.postDelayed(userInputNumberRunnable,
					USER_INPUT_NUMBER_TIMEOUT);
			return true;
		}
	}

	Runnable userInputNumberRunnable = new Runnable() {
		public void run() {
			synchronized (PlayActivity.this) {
				userNumberCount = 0;
				numberPrompt.setVisibility(View.INVISIBLE);
				// rateinfo.setVisibility(View.INVISIBLE);
				if (userSelectChannel != null) {

					lastchannel = curPlayChannelIdx;
					lastgroup = mSelectedGroup;

					Log.d(TAG,
							"userInputNumberRunnable : "
									+ userSelectChannel.getName());
					mSelectedGroup = provider
							.getGroupByName(ChannelContentProvider.GROUP_NAME_ALL);
					curPlayGroupIdx = provider
							.getGroupIndexByName(ChannelContentProvider.GROUP_NAME_ALL);
					curViewGroupIdx = curPlayGroupIdx;

					if (mSelectedGroup.getChannelInfo() == null)
						return;
					mSelectedChannel = mSelectedGroup.getChannelInfo().get(
							userSelectIndex);
					curPlayChannelIdx = curViewChannelIdx = userSelectIndex;
					if (mSelectedChannel.getSources() == null)
						return;
					sourceCount = mSelectedChannel.getSources().size();
					int index = provider
							.getLastPlaySourceIndex(mSelectedChannel);
					if (index >= 0 && index < sourceCount)
						curViewSourceIdx = curPlaySourceIdx = index;
					else
						curViewSourceIdx = curPlaySourceIdx = mSelectedChannel
								.getCurSourceIndex();
					mSelectedSource = mSelectedChannel.getSources().get(
							curPlaySourceIdx);
					mSelectedChannel.setCurSourceIndex(curPlaySourceIdx);

					updateChannelInfo();
					playSelectedChannel();
				}
			}
		};
	};

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		// Log.d(TAG, "KeyUp : osdViewState = " + osdViewState +
		// ", channelListState = " + channelListState);
		if (osdViewState != OSD_STATE_NORMAL
				&& channelListState != CHANNEL_LIST_DECODE) {
			mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT);
		}

		mHandler.postDelayed(hideEPG, EPG_SHOW_TIMEOUT);

		// FIXME : ignore key up event
		return true;
	}

	private Runnable hideRateView = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			hiderateView();
		}
	};

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
		Log.d(TAG, "onTouchEvent: x = " + x);
		Log.d(TAG, "onTouchEvent: y = " + y);
		if (action == MotionEvent.ACTION_DOWN) {
			if (osdViewState == OSD_STATE_NORMAL) {
				if (x < 326.0f) {
					switchToChannelListView();
				}
			}
			if (osdViewState != OSD_STATE_NORMAL
					&& channelListState != CHANNEL_LIST_DECODE) {
				mHandler.removeCallbacks(hideOsdHandler);
			}
		} else if (action == MotionEvent.ACTION_UP) {
			if (osdViewState != OSD_STATE_NORMAL
					&& channelListState != CHANNEL_LIST_DECODE) {
				mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT);
			}
		}

		/*
		 * if (osdViewState == OSD_STATE_NORMAL) { if (event.getAction() ==
		 * MotionEvent.ACTION_DOWN) { mHandler.removeCallbacks(hideOsdHandler);
		 * showOsdView(); } else if (event.getAction() == MotionEvent.ACTION_UP)
		 * { mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT); } }
		 */
		return super.onTouchEvent(event);
	}
	private void sendVdfHandler(){
		String vfdId = Integer.toString(mSelectedChannel.getChannelNumber());
		Log.v("zgy","=======vfdId========"+vfdId);
		if(vfdId.length() < 2){
			vfdId = "   "+vfdId;
		}
		else if(vfdId.length() < 3){
			vfdId = "  "+vfdId;
		}
		else if(vfdId.length() < 4){
			vfdId = " "+vfdId;
		}
		vfdId = "   CH:"+vfdId;
		sendVFDCommand(vfdId);
	}
	private void checkUpdateHandler(){
		String appname = appinfo.appname + ".apk";
		try {
			if (appinfo.apppackage.equals(appgetPackageInfo())) {
				if (!appinfo.versioncode.equals(getVersionCode())) {
					Intent intent = new Intent();
					intent.putExtra("url", appinfo.url);
					intent.putExtra("localpath", appname);
					intent.setAction("com.yiqu.networktv.UpdateService");
					this.startService(intent);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	private void onPlayBufferingstartHandle(){
		showLoadingDialog();
	}
	
	private void onPlayBufferingendHandle(){
		closeLoadingDialog();
	}
	private void onPlaySurfaceDestroyHandle(){
		player.stopPlay();
//		player.stopPlayHandle();
	}
	
	private void onPlaySurfaceCreateHandle(){
		playSelectedChannel();
		applyVideoRatio(player.mCurrentMode);
	}
	
	private void onPlayPrepareHandle(){
		mHandler.removeCallbacks(playSourceTimeOut);
		if (player.mCurrentMode == 1)
			applyVideoRatio(player.mCurrentMode);
		closeLoadingDialog();
		if (provider != null)
			provider.saveLastPlaySourceIndex(mSelectedChannel, curPlaySourceIdx);
		mSelectedChannel.setCurSourceIndex(curPlaySourceIdx);
		mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT);
	}
//	yyc
//	sendCommand_yyc

	private void onPlayErrorHandle() {
		if (mSelectedChannel == null) {
			return;
		}
		Log.v("egreat","=============onPlayErrorHandle============");
		mHandler.removeCallbacks(playSourceTimeOut);
		long duration = (System.currentTimeMillis() - playSourceStartTime);
		saveFailedSourceLog(mSelectedChannel, curViewSourceIdx, duration);
		if (playErrorCount < sourceCount) {
			playerrSourceIdx = playErrorCount;
			curPlaySourceIdx = playerrSourceIdx;
			ChannelSource source = mSelectedChannel.getSources().get(
					curPlaySourceIdx);
//			playerrSourceIdx = moveIndex(1, playerrSourceIdx, sourceCount);
//			curPlaySourceIdx = playerrSourceIdx;
			this.source.setText("[" + (curPlaySourceIdx + 1) + "/" + sourceCount
					+ "]");
			this.sourcetext.setText(getResources().getString(R.string.tiaoxuansource));
			onSourceSelected(source);
			playErrorCount++;
		} else {
			closeLoadingDialog();
			player.stopPlayHandle();
			String fromStr = getResources().getString(R.string.play_fail);
			if (mSelectedChannel == null) {
				return;
			}
			String msg = mSelectedChannel.getName() + fromStr;
			if (osdViewState != OSD_STATE_NORMAL) {
				switchToNormalOsdView();
			}
			if (!isshowdialognet) {
				try {
					showPlayFailInfoDialog(msg);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}

	private boolean isFirst_yyc = true;

//	public void onPlayerCallback(int what) {
//		synchronized (PlayActivity.this) {
//			if (PlayActivity.this.finished) {
//				Log.d(TAG, "onPlayerCallback : activity finish");
//				return;
//			}
//
//			if (what == StreamVideoPlayer.PLAYER_CB_SURFACE_CREATE) {
//				playSelectedChannel();
//				applyVideoRatio();
//			} else if (what == StreamVideoPlayer.PLAYER_CB_SURFACE_DESTROY) {
//				player.stopPlay();
////				player.stopPlayHandle();
////				player.stopPlay();
//				// finish();
//			} else if (what == StreamVideoPlayer.PLAYER_CB_PLAYER_NG) {
//
//				Message msg = new Message();
//				msg.what = StreamVideoPlayer.PLAYER_CB_PLAYER_NG;
//				mHandler.sendMessage(msg);
//			} else if (what == StreamVideoPlayer.PLAYER_CB_PLAYER_PLAY_ONPREPARE) {
//				mHandler.removeCallbacks(playSourceTimeOut);
//				if (isFirst_yyc) {
////					player.stopPlay();
//					player.stopPlayHandle();
////					player.stopPlay();
//					player.setPlayUrl(mSelectedSource.getUrl());
//					isFirst_yyc = false;
//				} else {
//					player.startPlay();
//				}
//				provider.saveLastPlaySourceIndex(mSelectedChannel,
//						curPlaySourceIdx);
//				mSelectedChannel.setCurSourceIndex(curPlaySourceIdx);
//				// showOsdView();
//				closeLoadingDialog();
//				mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT);
//			} else if (what == StreamVideoPlayer.PLAYER_CB_PLAYER_BUFFERING_START) {
//				Message msg = new Message();
//				msg.what = StreamVideoPlayer.PLAYER_CB_PLAYER_BUFFERING_START;
//				mHandler.sendMessage(msg);
//			} else if (what == StreamVideoPlayer.PLAYER_CB_PLAYER_BUFFERING_END) {
//				Message msg = new Message();
//				msg.what = StreamVideoPlayer.PLAYER_CB_PLAYER_BUFFERING_END;
//				mHandler.sendMessage(msg);
//			}
//		}
//
//	}


	void showLoadingDialog() {
		if (loadingImage == null)
			return;
		if (!isLoadingDialongShow) {
			loadingImage.setVisibility(View.VISIBLE);
			isLoadingDialongShow = true;
		}
	}

	void closeLoadingDialog() {
		if (loadingImage == null)
			return;
		if (isLoadingDialongShow) {
			sourcetext.setVisibility(View.INVISIBLE);
			source.setVisibility(View.INVISIBLE);
			sourcebg.setVisibility(View.INVISIBLE);
			loadingImage.setVisibility(View.INVISIBLE);
			isLoadingDialongShow = false;
		}
	}

	void showInfoDialog(String msg, boolean finish) {
		infoMsg = msg;
		finishSelf = finish;
		showDialog(DIALOG_INFO);
		isConfirmDialogShow = true;
	}

	void closInfoDialog() {
		try {
			dismissDialog(DIALOG_INFO);
			removeDialog(DIALOG_INFO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		isConfirmDialogShow = false;
	}

	void showPlayFailInfoDialog(String msg) {
		infoMsg = msg;
		showDialog(DIALOG_PLAY_FAIL_INFO);
		isConfirmDialogShow = true;
	}

	void closePlayFailInfoDialog() {
		try {
			dismissDialog(DIALOG_PLAY_FAIL_INFO);
			removeDialog(DIALOG_PLAY_FAIL_INFO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		isConfirmDialogShow = false;
	}
	void closeLoadingFailInfoDialog() {
		try {
			dismissDialog(DIALOG_LIST_GET_FAIL);
			removeDialog(DIALOG_LIST_GET_FAIL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void closePlayExitInfoDialog() {
		try {
			dismissDialog(DIALOG_PLAY_EXIT_INFO);
			removeDialog(DIALOG_PLAY_EXIT_INFO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		isConfirmDialogShow = false;
	}

	void closeCannelDelInfoDialog() {
		try {
			dismissDialog(DIALOG_CHANNEL_DEL_INFO);
			removeDialog(DIALOG_CHANNEL_DEL_INFO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		channelListState = CHANNEL_LIST_NORMAL;
		mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT);
		isConfirmDialogShow = false;
	}

	void closenetworkDialog() {
		try {
			dismissDialog(DIALOG_NET_WORK_LOST);
			removeDialog(DIALOG_NET_WORK_LOST);
		} catch (Exception e) {
			e.printStackTrace();
		}
		doFinish();
		// channelListState = CHANNEL_LIST_NORMAL ;
		// mHandler.postDelayed(hideOsdHandler, EPG_SHOW_TIMEOUT);
		// isConfirmDialogShow = false;
	}

	protected Dialog onCreateDialog(int id) {
		if (id == DIALOG_LOADING) {
			return createLoadingDialog(null);
		} else if (id == DIALOG_INFO) {
			return createInfoDialog(infoMsg, finishSelf);
		} else if (id == DIALOG_PLAY_FAIL_INFO) {
			// return createPlayFailInfoDialog(infoMsg);
			return createPlayFailChannelInfoDialog(infoMsg);
		} else if (id == DIALOG_PLAY_EXIT_INFO) {
			return createPlayExitInfoDialog();
		} else if (id == DIALOG_CHANNEL_DEL_INFO) {
			return createChannelDelInfoDialog();
		} 
		else if (id == DIALOG_NET_WORK_LOST) {
			return createNetWorkDialog();
		}
		else if (id == DIALOG_LIST_GET_FAIL) {
			return createFailLoadingChannelDialog();
		}
		return null;
	};

	Dialog createLoadingDialog(String msg) {
		Context context = PlayActivity.this;
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.loading_dialog, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);

		ImageView spaceshipImage = (ImageView) v
				.findViewById(R.id.loading_image);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
		// animation
		Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
				context, R.anim.loading_animation);
		spaceshipImage.startAnimation(hyperspaceJumpAnimation);
		if (msg != null) {
			tipTextView.setText(msg);
		} else {
			tipTextView.setVisibility(View.INVISIBLE);
		}

		Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);

		loadingDialog.setCancelable(false);
		loadingDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				int action = event.getAction();
				if (action == KeyEvent.ACTION_DOWN) {
					/*
					 * if (keyCode == KeyEvent.KEYCODE_BACK) { if (osdViewState
					 * == OSD_STATE_NORMAL) { closeLoadingDialog();
					 * //player.stopVideo(); //finish(); return true; } }
					 */
					onKeyDown(keyCode, event);
				} else if (action == KeyEvent.ACTION_UP) {
					onKeyUp(keyCode, event);
				}
				return false;
			}
		});

		/*
		 * loadingDialog.setOnCancelListener(new
		 * DialogInterface.OnCancelListener() {
		 * 
		 * @Override public void onCancel(DialogInterface dialog) {
		 * isLoadingDialongShow = false; }
		 * 
		 * });
		 */
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));

		return loadingDialog;

	}

	Dialog createInfoDialog(String msg, final boolean finish) {
		Context context = PlayActivity.this;
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.info_dialog, null);
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);

		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);

		if (msg != null) {
			tipTextView.setText(msg);
		} else {
			tipTextView.setVisibility(View.INVISIBLE);
		}

		Dialog infoDialog = new Dialog(context, R.style.loading_dialog);

		infoDialog.setCancelable(false);
		infoDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				int action = event.getAction();
				if (action == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						closInfoDialog();
						if (finish) {
							doFinish();
						}
						if (isNeedUserSelect) {
							isNeedUserSelect = false;
							showUserSelectDialog("play_fail");
						}
						return true;
					}
				}
				return false;
			}
		});

		infoDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		return infoDialog;

	}

	int user_selected = 0;
	int dellight = 3;
	int delcount = 3;
	int del_select = 1;
	int user_highlight = 1;
	int user_count = 2;
	// CheckedTextView clear_all_channel;
	// CheckedTextView clear_this_channel;

	TextView confirmText;
	TextView delText;
	TextView fail_nextsources;
	TextView fail_nextchannel;
	int fail_loading_channel_highlight = 1;
	TextView fail_loading_exit;
	TextView fail_loading_retry;

	View.OnHoverListener failDialogOnhover = new OnHoverListener() {
		public boolean onHover(View v, MotionEvent event) {
			int what = event.getAction();
			switch (what) {
			case MotionEvent.ACTION_HOVER_ENTER:
				// if (v == clear_all_channel) {
				// user_highlight = 0;
				// } else if (v == clear_this_channel) {
				// user_highlight = 1;
				// } else if (v == confirmText) {
				// user_highlight = 2;
				// }
				failDialogUpdateHighlight();
				break;
			case MotionEvent.ACTION_HOVER_MOVE:

				break;
			case MotionEvent.ACTION_HOVER_EXIT:

				break;
			}
			return false;
		}
	};

	View.OnClickListener failDialogOnClick = new View.OnClickListener() {
		public void onClick(View v) {
			failDialogUpdateClick();
		}
	};

	void failDialogUpdateHighlight() {
		if (user_highlight == 0) {
			fail_nextchannel
					.setBackgroundResource(R.drawable.fail_play_channel_nextchannelbg);
			fail_nextsources
					.setBackgroundResource(R.drawable.fail_play_channel_changesourcesbg);
		} else if (user_highlight == 1) {
			fail_nextchannel
					.setBackgroundResource(R.drawable.fail_play_channel_nextchannel);
			fail_nextsources
					.setBackgroundResource(R.drawable.fail_play_channel_changesources);
		} else if (user_highlight == 2) {
		}
	}
	void failLoadingUpdateHighlight() {
		if (fail_loading_channel_highlight == 0) {
			fail_loading_retry
			.setBackgroundResource(R.drawable.fail_play_channel_nextchannelbg);
			fail_loading_exit
			.setBackgroundResource(R.drawable.fail_play_channel_changesourcesbg);
		} else if (fail_loading_channel_highlight == 1) {
			fail_loading_retry
			.setBackgroundResource(R.drawable.fail_play_channel_nextchannel);
			fail_loading_exit
			.setBackgroundResource(R.drawable.fail_play_channel_changesources);
		} else if (fail_loading_channel_highlight == 2) {
		}
	}

	void failDialogUpdatedellight() {
		if (dellight == 1) {
			Log.v("yanc", "======dellight=========" + dellight);
			// clear_all_channel.setBackgroundResource(R.drawable.tv_info_dialog_bg_normal);
			// clear_this_channel.setBackgroundResource(R.drawable.tv_info_dialog_ok_bg);
			fail_nextchannel
					.setBackgroundResource(R.drawable.fail_play_channel_nextchannelbg);
			fail_nextsources
					.setBackgroundResource(R.drawable.fail_play_channel_changesources);
		} else if (dellight == 2) {
			Log.v("yanc", "======dellight=========" + dellight);
			// clear_all_channel.setBackgroundResource(R.drawable.tv_info_dialog_ok_bg);
			// clear_this_channel.setBackgroundResource(R.drawable.tv_info_dialog_bg_normal);
			fail_nextchannel
					.setBackgroundResource(R.drawable.fail_play_channel_nextchannelbg);
			fail_nextsources
					.setBackgroundResource(R.drawable.fail_play_channel_changesources);
		} else if (dellight == 3) {
			Log.v("yanc", "======dellight=========" + dellight);
			// clear_all_channel.setBackgroundResource(R.drawable.tv_info_dialog_bg_normal);
			// clear_this_channel.setBackgroundResource(R.drawable.tv_info_dialog_bg_normal);
			fail_nextchannel
					.setBackgroundResource(R.drawable.fail_play_channel_nextchannel);
			fail_nextsources
					.setBackgroundResource(R.drawable.fail_play_channel_changesources);
		}
	}

	void failDialogUpdateClick() {
		if (dellight == 1) {
			// user_selected = 0;
			del_select = 1;
			// clear_all_channel.setChecked(false);
			// clear_this_channel.setChecked(true);
		} else if (dellight == 2) {
			// user_selected = 1;
			del_select = 2;
			// clear_all_channel.setChecked(true);
			// clear_this_channel.setChecked(false);
		} else if (dellight == 3) {
			// if (user_selected == 0) {
			// mHandler.removeCallbacks(hideOsdHandler);
			// switchToChannelOptionView();
			// } else if (user_selected == 1) {
			// onChannelChange(1, true);
			// }
			// closePlayFailInfoDialog();
		}
	}

	Dialog createPlayFailInfoDialog(String msg) {
		Context context = PlayActivity.this;
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.play_fail_info_dialog, null);

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
		tipTextView.setText(msg);

		// clear_all_channel = (CheckedTextView)v.findViewById(R.id.text1);
		// clear_this_channel = (CheckedTextView)v.findViewById(R.id.text2);
		confirmText = (TextView) v.findViewById(R.id.configbtn);
		user_selected = 0;
		user_highlight = 0;

		// clear_all_channel.setOnClickListener(failDialogOnClick);
		// clear_this_channel.setOnClickListener(failDialogOnClick);
		confirmText.setOnClickListener(failDialogOnClick);

		Dialog infoDialog = new Dialog(context, R.style.loading_dialog);
		infoDialog.setCancelable(false);
		// infoDialog.setOnKeyListener( new DialogInterface.OnKeyListener() {
		// @Override
		// public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent
		// event) {
		// int action = event.getAction();
		// if (action == KeyEvent.ACTION_DOWN) {
		// if (keyCode == KeyEvent.KEYCODE_ENTER) {
		// failDialogUpdateClick();
		// return true;
		// } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
		// int next = moveIndex(-1, user_highlight, user_count);
		// user_highlight = next;
		// failDialogUpdateHighlight();
		// return true;
		// } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
		// int next = moveIndex(1, user_highlight, user_count);
		// user_highlight = next;
		// failDialogUpdateHighlight();
		// return true;
		// }
		// }
		// return false;
		// }
		// });

		infoDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		return infoDialog;
	}

	Dialog createPlayFailChannelInfoDialog(String msg) {
		Context context = PlayActivity.this;
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.fail_play_channel_dialog, null);

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
		tipTextView.setText(msg);

		fail_nextsources = (TextView) v.findViewById(R.id.fail_next_source);
		fail_nextchannel = (TextView) v.findViewById(R.id.faile_next_channel);

		// user_selected = 0;
		user_highlight = 1;

		Dialog infoDialog = new Dialog(context, R.style.loading_dialog);
		infoDialog.setCancelable(false);
		infoDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				int action = event.getAction();
				if (action == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						// failDialogUpdateClick();
						if (user_highlight == 1) {
							onChannelChange(1, true);
						} else {
							mHandler.removeCallbacks(hideOsdHandler);
							switchToChannelOptionView();
						}

						closePlayFailInfoDialog();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
						if (user_highlight == 1) {
							int next = moveIndex(-1, user_highlight, user_count);
							user_highlight = next;
						}
						failDialogUpdateHighlight();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
						if (user_highlight == 0) {
							int next = moveIndex(1, user_highlight, user_count);
							user_highlight = next;
						}
						failDialogUpdateHighlight();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_BACK) {
						closePlayFailInfoDialog();
						switchToChannelListView();
						return true;
					}
				}
				return false;
			}
		});

		infoDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		return infoDialog;
	}
	Dialog createFailLoadingChannelDialog() {
		Context context = PlayActivity.this;
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.fail_loading_channel_dialog, null);
		
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		
		fail_loading_exit = (TextView) v.findViewById(R.id.fail_exit);
		fail_loading_retry = (TextView) v.findViewById(R.id.faile_retry);
		
		// user_selected = 0;
		user_highlight = 1;
		
		Dialog infoDialog = new Dialog(context, R.style.loading_dialog);
		infoDialog.setCancelable(false);
		infoDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				int action = event.getAction();
				if (action == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						// failDialogUpdateClick();
						if (fail_loading_channel_highlight == 1) {
							//重新下载
							DownLoadAsync mDownLoadAsync = new DownLoadAsync(PlayActivity.this, playlistUrl);
							showLoadingDialog();
							mDownLoadAsync.execute();
						} else {
							//退出
							player.stopPlayHandle();
							doFinish();
						}
						closeLoadingFailInfoDialog();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
						if (fail_loading_channel_highlight == 1) {
							int next = moveIndex(-1, fail_loading_channel_highlight, user_count);
							fail_loading_channel_highlight = next;
						}
						failLoadingUpdateHighlight();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
						if (fail_loading_channel_highlight == 0) {
							int next = moveIndex(1, fail_loading_channel_highlight, user_count);
							fail_loading_channel_highlight = next;
						}
						failLoadingUpdateHighlight();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_BACK) {
						closeLoadingFailInfoDialog();
						return true;
					}
				}
				return false;
			}
		});
		
		infoDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));
		
		return infoDialog;
	}

	Dialog createPlayExitInfoDialog() {
		Context context = PlayActivity.this;
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.exit_play_dialog, null);

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
		// tipTextView.setText(msg);

		fail_nextsources = (TextView) v.findViewById(R.id.fail_next_source);
		fail_nextchannel = (TextView) v.findViewById(R.id.faile_next_channel);

		// user_selected = 0;
		user_highlight = 1;

		Dialog infoDialog = new Dialog(context, R.style.loading_dialog);
		infoDialog.setCancelable(false);
		infoDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				int action = event.getAction();
				if (action == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						// failDialogUpdateClick();
						if (user_highlight == 1) {
							// onChannelChange(1, true);
							player.stopPlay();
							doFinish();
						} else {
							// mHandler.removeCallbacks(hideOsdHandler);
							// switchToChannelOptionView();
							closePlayExitInfoDialog();
						}

						// closePlayFailInfoDialog();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
						if (user_highlight == 1) {
							int next = moveIndex(-1, user_highlight, user_count);
							user_highlight = next;
						}
						failDialogUpdateHighlight();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
						if (user_highlight == 0) {
							int next = moveIndex(1, user_highlight, user_count);
							user_highlight = next;
						}
						failDialogUpdateHighlight();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_BACK) {
						closePlayExitInfoDialog();
						// switchToChannelListView();
						return true;
					}
				}
				return false;
			}
		});

		infoDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		return infoDialog;
	}

	Dialog createChannelDelInfoDialog() {
		Log.v("yanc", "===========createChannelDelInfoDialog=============");
		Context context = PlayActivity.this;
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.channel_del_dialog, null);

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		// TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);
		// tipTextView.setText(msg);

		fail_nextsources = (TextView) v.findViewById(R.id.fail_next_source);
		fail_nextchannel = (TextView) v.findViewById(R.id.faile_next_channel);
		// Log.v("yanc","===========del=====1========");
		delText = (TextView) v.findViewById(R.id.delTextView);
		// Log.v("yanc","===========del======2=======");
		// clear_this_channel = (CheckedTextView)v.findViewById(R.id.text2);
		// Log.v("yanc","===========del======3=======");
		// user_selected = 0;
		user_highlight = 1;
		dellight = 3;
		del_select = 1;
		Dialog infoDialog = new Dialog(context, R.style.loading_dialog);
		infoDialog.setCancelable(false);
		infoDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				int action = event.getAction();
				if (action == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
						// if(dellight != 3){
						// failDialogUpdateClick();
						// }else{
						if (user_highlight == 0) {
							curPlayChannelIdx = 0;
							provider.delRecent(recentSelectchannel, true);
						} else {
							provider.delRecent(recentSelectchannel, false);
							curPlayChannelIdx--;
							if (curPlayChannelIdx < 0)
								curPlayChannelIdx = 0;
							// if(del_select == 1){
							// }else if(del_select == 2){
							// curPlayChannelIdx = 0;
							// provider.delRecent(recentSelectchannel, true);
							//
							// }
						}
						reflashChannelListData();
						closeCannelDelInfoDialog();
						// }
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
						// user_highlight = 1;
						// if(dellight != 1){
						// int next = moveIndexfordel(-1, dellight, delcount);
						// dellight = next;
						// Log.v("yanc","======dellight========="+dellight);
						// }
						// failDialogUpdatedellight();
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
						// if(dellight != 3){
						// int next = moveIndexfordel(1, dellight, delcount);
						// dellight = next;
						// Log.v("yanc","======dellight========="+dellight);
						// }
						// failDialogUpdatedellight();
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
						// if(dellight == 3){
						if (user_highlight == 1) {
							int next = moveIndex(-1, user_highlight, user_count);
							user_highlight = next;
							// }
							failDialogUpdateHighlight();
						}
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
						// if(dellight == 3){
						if (user_highlight == 0) {
							int next = moveIndex(1, user_highlight, user_count);
							user_highlight = next;
							// }
							failDialogUpdateHighlight();
						}
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_BACK) {
						closeCannelDelInfoDialog();
						// switchToChannelListView();
						return true;
					}
				}
				return false;
			}
		});

		infoDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		return infoDialog;
	}

	Dialog createNetWorkDialog() {
		Context context = PlayActivity.this;
		LayoutInflater inflater = LayoutInflater.from(context);
		View v = inflater.inflate(R.layout.network_play_dialog, null);

		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);
		user_highlight = 1;
		dellight = 3;
		del_select = 1;
		Dialog infoDialog = new Dialog(context, R.style.loading_dialog);
		infoDialog.setCancelable(false);
		infoDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode,
					KeyEvent event) {
				int action = event.getAction();
				if (action == KeyEvent.ACTION_DOWN) {
					if (keyCode == KeyEvent.KEYCODE_ENTER) {
//						player.stopPlay();
						doFinish();
						closenetworkDialog();
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
						// if(user_highlight == 1){
						// int next = moveIndex(-1, user_highlight, user_count);
						// user_highlight = next;
						// failDialogUpdateHighlight();
						// }
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
						// if(user_highlight == 0){
						// int next = moveIndex(1, user_highlight, user_count);
						// user_highlight = next;
						// failDialogUpdateHighlight();
						// }
						return true;
					} else if (keyCode == KeyEvent.KEYCODE_BACK) {
						closenetworkDialog();
						// switchToChannelListView();
						return true;
					}
				}
				return false;
			}
		});

		infoDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT));

		return infoDialog;
	}

	private void pressEnterbut() {

	}

	int getDiffUserSetting() {
		final SharedPreferences sp = getSharedPreferences(
				ChannelContentProvider.PREFS_NAME, 0);
		return sp.getInt("diff_user", 1);
	}

	void setDiffUserSetting(int value) {
		final SharedPreferences sp = getSharedPreferences(
				ChannelContentProvider.PREFS_NAME, 0);
		SharedPreferences.Editor edit = sp.edit();
		edit.putInt("diff_user", value);
		edit.commit();
	}

	private void setShowNetworkSpeedSetting(boolean show) {
		final SharedPreferences sp = getSharedPreferences(
				ChannelContentProvider.PREFS_NAME, 0);
		SharedPreferences.Editor edit = sp.edit();
		edit.putBoolean("show_speed", show);
		edit.commit();
	}

	private boolean getShowNetworkSpeedSetting() {
		final SharedPreferences sp = getSharedPreferences(
				ChannelContentProvider.PREFS_NAME, 0);
		return sp.getBoolean("show_speed", true);
	}

	public void adjustVoice(int keyCode) {
		
		AudioManager audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		if (audiomanager != null) {
			int flags = AudioManager.FLAG_SHOW_UI | AudioManager.FLAG_VIBRATE;
			audiomanager
					.adjustVolume(
							keyCode == KeyEvent.KEYCODE_DPAD_RIGHT ? AudioManager.ADJUST_RAISE
									: AudioManager.ADJUST_LOWER, flags);
		}
		mHandler.removeMessages(MSG_SEND_VDF);
		mHandler.sendEmptyMessageDelayed(MSG_SEND_VDF,1000L);
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		int action = event.getAction();
		if (action == KeyEvent.ACTION_DOWN) {
			if (osdViewState != OSD_STATE_NORMAL
					&& channelListState != CHANNEL_LIST_DECODE) {
				mHandler.removeCallbacks(hideOsdHandler);
			}
			if (LOGD && v == channelList) {
				if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
					Log.d(TAG, "channellist keydown: up!");
				} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
					Log.d(TAG, "channellist keydown: down!");
				}
				if (mSelectedGroup != null) {
					int i = channelList.getSelectedItemPosition();
					List<ChannelInfo> channels = mSelectedGroup
							.getChannelInfo();
					int size = (channels == null) ? 0 : channels.size();
					if (i >= 0 && i < size) {
						Log.d(TAG, "channellist focus: "
								+ mSelectedGroup.getChannelInfo().get(i)
										.getName());
					}
				}
			}
		} else if (action == KeyEvent.ACTION_UP) {
			if (LOGD && v == channelList) {
				if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
					Log.d(TAG, "channelList keyup: up!");
				} else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
					Log.d(TAG, "channelList keyup: down!");
				}
				if (mSelectedGroup != null) {
					int i = channelList.getSelectedItemPosition();
					List<ChannelInfo> channels = mSelectedGroup
							.getChannelInfo();
					int size = (channels == null) ? 0 : channels.size();
					if (i >= 0 && i < size) {
						Log.d(TAG, "channellist focus: "
								+ mSelectedGroup.getChannelInfo().get(i)
										.getName());
					}
				}
			}
		}

		return false;
	}

	void showUserSelectDialog(final String request) {
		AlertDialog.Builder builder = new AlertDialog.Builder(PlayActivity.this);
		String title = null, msg = null, positiveStr = null, negativeStr = null;
		if (request.equals("play_fail")) {
			title = getResources().getString(R.string.select_play_fail_title);
			msg = getResources().getString(R.string.select_play_fail_msg);
			positiveStr = getResources().getString(
					R.string.select_change_channel);
			negativeStr = getResources().getString(
					R.string.select_change_source);
		} else if (request.equals("delete_user")) {
			if (selectedUserChannel == null) {
				switchToNormalOsdView();
				return;
			}
			title = getResources().getString(R.string.select_delete_user_title);
			msg = getResources().getString(R.string.select_delete_user_msg)
					+ selectedUserChannel.getName() + "?";
			positiveStr = getResources().getString(
					R.string.select_delete_user_yes);
			negativeStr = getResources().getString(
					R.string.select_delete_user_no);
		} else if (request.equals("exit_comfirm")) {
			title = getResources().getString(R.string.exit_comfirm_title);
			msg = getResources().getString(R.string.exit_comfirm_msg);
			positiveStr = getResources().getString(
					R.string.select_delete_user_yes);
			negativeStr = getResources().getString(
					R.string.select_delete_user_no);
		}

		builder.setTitle(title)
				.setMessage(msg)
				.setCancelable(false)
				.setPositiveButton(positiveStr,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								if (request.equals("play_fail")) {
									onChannelChange(1, true);
								} else if (request.equals("delete_user")) {
									onDeleteUserChannel();
									switchToNormalOsdView();
								} else if (request.equals("exit_comfirm")) {
									player.stopPlayHandle();
//									player.stopPlay();
									doFinish();
								}
							}
						})
				.setNegativeButton(negativeStr,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								if (request.equals("play_fail")) {
									mHandler.removeCallbacks(hideOsdHandler);
									switchToChannelOptionView();
								} else if (request.equals("delete_user")) {
									selectedUserChannel = null;
									switchToNormalOsdView();
								} else if (request.equals("exit_comfirm")) {
									// Do nothing
								}
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void saveFailedSourceLog(ChannelInfo channel, int inndex,
			long duration) {
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			Date curDate = new Date(System.currentTimeMillis());
			String dateTime = formatter.format(curDate);
			String sourceInfo = channel.getId() + "," + channel.getName() + ","
					+ Integer.toString(inndex) + ","
					+ Integer.toString((int) duration);
			String saveStr = dateTime + "," + sourceInfo + "\n";

			Log.d(TAG, "Log: " + saveStr);

			String logPath = getFilesDir().getAbsolutePath() + "/"
					+ "LiveTvTest.txt";
			File logFile = new File(logPath);
			if (logFile.exists()) {
				String logStr = FileUtils.file2String(logFile);

				// remove 3 day ago
				formatter = new SimpleDateFormat("yyyy/MM/dd");
				Date oldDate = new Date(
						System.currentTimeMillis() - 72 * 3600 * 1000);
				String oldDateStr = formatter.format(oldDate);
				Log.d(TAG, "oldDate : " + oldDateStr);
				int index = logStr.indexOf(oldDateStr);
				String newLogStr = null;
				if (index > 0) {
					newLogStr = logStr.substring(index);
					// Log.d(TAG, "newLogStr : " + newLogStr);
				} else {
					newLogStr = logStr;
				}

				newLogStr = newLogStr + saveStr;
				FileUtils.string2File(newLogStr, logPath);
			} else {
				logFile = FileUtils.string2File(saveStr, logPath);
			}
		} catch (Exception e) {
			return;
		}
	}

	private void copyLogFile() {
		String extLogPath = FileUtils.getUsbPath() + "/" + "LiveTvTest";
		File extLogPathFile = new File(extLogPath);
		if (!extLogPathFile.exists()) {
			return;
		}

		String logPath = getFilesDir().getAbsolutePath() + "/"
				+ "LiveTvTest.txt";
		File logFile = new File(logPath);
		if (!logFile.exists()) {
			return;
		}

		String extLogFilePath = FileUtils.getUsbPath() + "/" + "LiveTvTest"
				+ "/" + "LiveTvTest.txt";
		File extLogFile = new File(extLogFilePath);
		FileUtils.copyFile(logFile, extLogFile);
	}

	public void setVideoRatio(int value) {
		final SharedPreferences sp = getSharedPreferences(
				ChannelContentProvider.PREFS_NAME, 0);
		SharedPreferences.Editor edit = sp.edit();
		edit.putInt("video_ratio", value);
		edit.commit();
	}

	public int getVideoRatio() {
		final SharedPreferences sp = getSharedPreferences(
				ChannelContentProvider.PREFS_NAME, 0);
		return sp.getInt("video_ratio", 0);
	}

	private void applyVideoRatio(int type) {
				if (TvManager.getInstance() != null) {
					Log.v("lol","=========type.E_AUTO==========="+type);
					switch(type){
					case 0:
						try {
							TvManager.getInstance().getPictureManager()
									.setAspectRatio(EnumVideoArcType.E_16x9);
						} catch (Exception e) {
							// TODO: handle exception
						}
						break;
					case 1:
						try {
							TvManager.getInstance().getPictureManager()
									.setAspectRatio(EnumVideoArcType.E_16x9);
						} catch (Exception e) {
							// TODO: handle exception
						}
						break;
					case 2:
						try {
							TvManager.getInstance().getPictureManager()
									.setAspectRatio(EnumVideoArcType.E_4x3);
						} catch (Exception e) {
							// TODO: handle exception
						}
						break;
					case 3:
						try {
//							TvManager.getInstance().getPictureManager()
//									.setAspectRatio(EnumVideoArcType.E_AUTO);
//							TvManager.getInstance().getPictureManager()
//							.setAspectRatio(EnumVideoArcType.E_AUTO);
							Log.v("lol","=========EnumVideoArcType.E_AUTO===========");
						} catch (Exception e) {
							// TODO: handle exception
						}
						break;
					}
				}
	}

	private void sendChannelNumberBroadcast() {
		Intent intent = new Intent("android.send.COMMAND_ACTION");
		String str = String.format(",  CH:%4d",
				mSelectedChannel.getChannelNumber());
		intent.putExtra("commandName", str);
		sendBroadcast(intent);
	}

	private void showNumberPrompt() {
		if (mSelectedGroup == null)
			return;
		mHandler.removeCallbacks(hideNumberPromptRunnable);
		ChannelInfo channel = mSelectedGroup.getChannelInfo().get(
				curPlayChannelIdx);
		numberText.setTextSize(45);
		numberText.setText(Integer.toString(channel.getChannelNumber()));
		nameText.setText(channel.getName());
//		
//		String vfdId = Integer.toString(channel.getChannelNumber());
//		Log.v("zgy","=======vfdId========"+vfdId);
//		if(vfdId.length() < 2){
//			vfdId = "   "+vfdId;
//		}
//		else if(vfdId.length() < 3){
//			vfdId = "  "+vfdId;
//		}
//		else if(vfdId.length() < 4){
//			vfdId = " "+vfdId;
//		}
//		vfdId = "   CH:"+vfdId;
//		sendVFDCommand(vfdId);
		
		mHandler.removeCallbacks(runableshownumberPrompt);
		numberPrompt.setVisibility(View.VISIBLE);
		mHandler.postDelayed(hideNumberPromptRunnable,
				USER_INPUT_NUMBER_TIMEOUT);
	}

	Runnable hideNumberPromptRunnable = new Runnable() {
		public void run() {
			numberPrompt.setVisibility(View.INVISIBLE);
		}
	};

	@Override
	public void onClick(View v) {
		if (v == arrowLeft) {
			onGroupMove(-1);
		} else if (v == arrowRight) {
			onGroupMove(1);
		}
	}

	@Override
	public void downLoadChannelFile(File f) {
		// TODO Auto-generated method stub
		onPlaylistDownloaed(f);
	}

	@Override
	public void downLoadLogolFile(File f) {
		// TODO Auto-generated method stub
		updateChannelLogo(f);
	}

	@Override
	public void downLoadInfoFile(File f) {
		// TODO Auto-generated method stub
		updateInfo(f);

	}

	@Override
	public void onPlaySurfaceCreate() {
		mHandler.sendEmptyMessage(PlayerCallback.ONPLAYSURFACECREATE);
		Log.v("egreat","=============onPlaySurfaceCreate============");
	}

	@Override
	public void onPlaySurfaceDestory() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessage(PlayerCallback.ONPLAYSURFACECREATE);
		Log.v("egreat","=============onPlaySurfaceDestory============");
	}

	@Override
	public void onPlayPrepare() {
		// TODO Auto-generated method stub
		Log.v("egreat","=============onPlayPrepare============");
		mHandler.sendEmptyMessage(PlayerCallback.ONPLAYPREPARE);
	}

	@Override
	public void onPlayFail() {
		// TODO Auto-generated method stub
		Log.v("egreat","=============onPlayFail============");
		mHandler.sendEmptyMessage(PlayerCallback.ONPLAYFAIL);
	}
	
	@Override
	public void onPlayBufferingStart() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessage(PlayerCallback.ONPLAYBUFFERINGSTART);
	}

	@Override
	public void onPlayBufferingEnd() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessage(PlayerCallback.ONPLAYBUFFERINGEND);
	}


	private String[] volumes;
	private MStorageManager stm;
	private String usbPath = "";

	private String getUsbPath() {
		/* 获得StorageManager对象 */
		MStorageManager stm = MStorageManager.getInstance(this);
		volumes = stm.getVolumePaths();
		for (int i = 0; i < volumes.length; i++) {
			usbPath = volumes[i].toString();
		}
		Log.e("yyc", "usbPath=============" + usbPath);
		return usbPath;
	}
	
    private synchronized void sendVFDCommand(String data) {
    	String commandStr = " " + "," + data;
    	Intent intent = new Intent("android.send.COMMAND_ACTION");
    	intent.putExtra("commandName", commandStr);
    	this.sendBroadcast(intent);
    }

}
