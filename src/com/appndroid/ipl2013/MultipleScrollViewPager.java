package com.appndroid.ipl2013;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Gallery;

public class MultipleScrollViewPager extends ViewPager
{
	private int mSignaturePanel = 0;
	
	public MultipleScrollViewPager(Context context)
	{
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void setSignaturePanel(int nChildId)
	{
		mSignaturePanel = nChildId;
	}
	
	public MultipleScrollViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent event)
	{
		//there exists a signature view on this page
		if(mSignaturePanel > 0 )
		{
			View signatureView = findViewById(mSignaturePanel);
			
			if((signatureView != null))
			{
				Rect rect = new Rect();
				//Hit rectangle is the rectangle where the touch events on that view are noted for scrolling
				signatureView.getHitRect(rect);		
				
				//Now check if our touch co-ordinates fall in the hit rectangle of the gallery view
				//If Yes: Disable page scrolling
				//IF No: Let the page scroll
				if(rect.contains((int)event.getX(), (int)event.getY()))
				{
					return false;
				}
			}
		}
	
		return super.onInterceptTouchEvent(event);
	}
	
	@Override
	protected boolean canScroll(View arg0, boolean arg1, int arg2, int arg3, int arg4)
	{
		if(arg0 instanceof Gallery)
			return true;
		else
			return super.canScroll(arg0, arg1, arg2, arg3, arg4);
	}

}
