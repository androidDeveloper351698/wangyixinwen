package com.meetu.db;

import com.meetu.R;
import com.meetu.fragment.Miliaofragment;
import com.meetu.fragment.Myfragment;
import com.meetu.fragment.Shouyefragment;
import com.meetu.fragment.Xiaoxifragment;



public class TabDb {
	public static String[] getTabsTxt(){
		String[] tabs={"首页","觅聊","消息"," 我"};
		return tabs;
	}
	public static int[] getTabsImg(){
		int[] ids={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
		return ids;
	}
	public static int[] getTabsImgLight(){
		int[] ids={R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
		return ids;
	}
	public static Class[] getFragments(){
		Class[] clz={Shouyefragment.class,Miliaofragment.class,Xiaoxifragment.class,Myfragment.class};
		return clz;
	}

}
