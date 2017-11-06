# BlackCard


OkHttpClient client = new OkHttpClient();
RequestBody requestbody = new FormBody.Builder()
	.add("uid","21334")
        .add("token","40471dd5da41ad1d9db9f7294e9db92a")
        .build();
Request request = new Request.Builder().url(url).build();
Response response = client.newCall(request).execute();
String responseData = response.body().string();
Log.d(TAG,"返回值result = " + responseData);
        



登录账号密码（后来不用了，直接通过网络请求）
86263
b59c67bf196a4758191e42f76670ceba



10月11日下午：  尝试封装一下Volley请求，失败，，，首页数据Volley请求不下来，换用OkHttp，
		依旧失败，最后换回Volley，post参数改为自己的账号密码，就能把数据请求下来了，但是又解析不出来了。getPrivilege == 0；




从网络请求下来一个url地址，把URL地址传给adapter，通过glide显示图片？yes



ArrayList里面是Object，通过Object的字段来进行冒泡排序。从大到小或者从小到大。yes。


10月17日：ViewPager显示网络下载的图片（欢迎页），传给Adapter的参数是一个List<map<string,object>>，其中的object分别是网络图片的URL和一个new ImageV（this），
		在Adapter中，通过glide把图片显示出来，显示在一起传过去View上。
		博客：CSDN：不倒翁的今生今世；简书：PingerOne。



10月18上午：解决了CLUBfragment切换之后ViewPager数据不加载的问题。把GetFragmentManager换为getChildFragment，因为↓
	    						 	/**
	        	     					  *  getFragmentManager()是所在fragment 父容器的碎片管理，
		           					  *  getChildFragmentManager()是在fragment 里面子容器的碎片管理。
	           						  */

开始做显示不同布局的Recyclerview  Item。


分割线，是一个View，0.5dp，布局可以嵌套，修改起来好改，
relativeLayout，尽量不用marginTop来控制布局，那样只是看起来对齐了，屏幕适配的话会有问题。


2017/10/20：Club页面的图片，两个横着的LinearLayout，一个最多显示三张图片，一个最多显示两张图片，在Adapter里面动态设置image的高度和宽度。


2017/10/23：Volley请求的onResponse方法，已经回到主线程了，不需要通过handler更改UI，onBindViewHolder也是在主线程，
					在Adapter中把RecyclerviewHead的ViewPager的图片数据传给ViewPager的Adapter
	    Recyclerview上拉加载更多，把viewpagerAdapter改一下



2017/10/24：Club页面，推荐页面的creamlist到24的时候会闪退，把加载圆形头像的Picasso换成Glide就好了，不知道为啥。
		列表数据最多加载到29 or 30？
		
		


过程中用到的一些博客：
	
	RelativeLayout布局常用属性介绍：http://blog.csdn.net/jianghuiquan/article/details/8298687

	Android(4)布局方法、px/dp/dpi/ps/内边距与外边距：http://blog.csdn.net/u013225534/article/details/49388641
	
	Picasso加载圆角图片：    http://blog.csdn.net/ivenes/article/details/53455214

	Android中使用Picasso将图片直接转换为圆形：http://blog.csdn.net/codezjx/article/details/51319048

	Recyclerview下拉刷新控件：BGARefreshLayout

	Volley封装，一行代码搞定http请求：http://blog.csdn.net/ganklun/article/details/43372355

	Android智能下拉刷新框架-SmartRefreshLayout：https://segmentfault.com/a/1190000010066071

	教你上传本地代码到github：http://blog.csdn.net/hanhailong726188/article/details/46738929

	Android控件的隐藏与显示：http://blog.csdn.net/breaker892902/article/details/17790151

	Android:TabLayout向上滑动停留页面顶部：http://blog.csdn.net/zheng_jiao/article/details/76186637

	CoordinatorLayout使用中的坑（还没研读明白，唉。。。）：http://blog.csdn.net/bigggfish/article/details/53585783

	CoordinatorLayout使用：http://www.jianshu.com/p/e8f14a1f16a3

	解决 RecyclerView AppBarLayout 滑动不顺畅的问题：http://www.jianshu.com/p/079fc98dd739

	自定义Behavior实现AppBarLayout越界弹性效果（妙极）：http://blog.csdn.net/gundumw100/article/details/70001472

	ScrollView中嵌套ViewPager时导致ViewPage内容页不显示：http://blog.sina.com.cn/s/blog_5da93c8f0102x5kx.html

