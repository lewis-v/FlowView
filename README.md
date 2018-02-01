# FlowView
轮播ViewPager
此控件对ViewPager循环轮播的实现进行封装,适用于一种View轮播及多种不同的View一起轮播,并在后期进行完善


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
	compile 'com.github.lewis-v:FlowView:1.0.2'
	}
 ```
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
 
## 若有问题请向作者反馈:(www.605788229@qq.com)
