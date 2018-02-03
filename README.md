# FlowView
轮播ViewPager
此控件对ViewPager循环轮播的实现进行封装,适用于一种View轮播及多种不同的View一起轮播,并在后期进行完善

![这里写图片描述](http://img.blog.csdn.net/20180201231008421?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvcXFfMzg3MTMzOTY=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

# 使用方法
## 添加依赖
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
		}
	}
 
 dependencies {
	compile 'com.github.lewis-v:FlowView:1.0.4'
	}
 ```
# YWFlowViewPager 循环滚动控件
## 布局添加
 ```
 <com.lewis_v.widget.viewflow.YWFlowViewPager
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
```

## 初始化
 ```
        flowview.setFlowView(imageViews);//添加显示的控件列表
        flowview.setFlowTime(3000);//设置轮播间隔ms,默认为5秒
        flowview.start(true);//开始轮播
        //设置对每页的点击事件
        flowview.setOnPageClickListener(new YWFlowViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(View view, int position) {
                Log.i(TAG, String.valueOf(position));
            }
        });
 ```
 # YWFlowView 对循环滚动控件进一步封装,添加了滚动的小圆点
 ## 布局添加
 ```
  <com.lewis_v.widget.viewflow.YWFlowView
        android:id="@+id/viewpager"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
 ```
 ## 初始化(与YWVFlowViewPager一样)
 ```
 viewpager.addFlowView(imageView).addFlowView(imageView1)//添加显示View,也可使用setFlowViewList()设置View列表
                .setPointGravity(Gravity.RIGHT)//小圆点的位置
		.setPointLayoutBackground(R.color.blue)//设置圆点布局的背景颜色
		.setPointLayoutHeight(400)//设置圆点布局的背景高度
		.setPointSize(20,20)//设置圆点大小,宽与高
		.setPointMargins(20)//设置圆点间隔
		//以上的设置都有相应的默认设置,一般不需要在进行配置
		.start(this,true);//开始滚动,this为context的引用,true为是否滚动
 ```
 
 ## 使用建议
 在生命周期内调用对应方法,用于暂停与恢复轮播
 ```
  @Override
    public void onResume() {
        super.onResume();
        viewpager.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewpager.pause();
    }
```
 
## 若有问题请向作者反馈:(605788229@qq.com)

