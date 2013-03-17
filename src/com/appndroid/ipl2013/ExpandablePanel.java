package com.appndroid.ipl2013;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ExpandablePanel extends LinearLayout {

	private final int mHandleId;
	private final int mContentId;
	private final int mplayerListId;
	private LayoutInflater mInflater;

	private View mHandle;
	private View mContent;
	private View mPlayerList;

	public static boolean mExpanded = true;
	private int mCollapsedHeight = 0;
	private int mContentHeight = 0;

	ListView list;
	private String[] playerName = null;
	private String[] playerRole = null;
	private int[] to = null;
	private Cursor cur;

	Context mContext;

	public ExpandablePanel(Context context) {
		this(context, null);
	}

	public ExpandablePanel(Context context, AttributeSet attrs) {
		super(context, attrs);

		mContext = context;

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ExpandablePanel, 0, 0);
		if( Utils.db == null )
          Utils.getDB( mContext );

		// How high the content should be in "collapsed" state
		mCollapsedHeight = (int) a.getDimension(
				R.styleable.ExpandablePanel_collapsedHeight, 0.0f);

		int handleId = a.getResourceId(R.styleable.ExpandablePanel_handle, 0);
		if (handleId == 0) {
			throw new IllegalArgumentException(
					"The handle attribute is required and must refer "
							+ "to a valid child.");
		}

		int contentId = a.getResourceId(R.styleable.ExpandablePanel_content, 0);
		if (contentId == 0) {
			throw new IllegalArgumentException(
					"The content attribute is required and must refer "
							+ "to a valid child.");
		}

		int contentId2 = a.getResourceId(R.styleable.ExpandablePanel_list, 0);
		if (contentId2 == 0) {
			throw new IllegalArgumentException(
					"The content attribute is required and must refer "
							+ "to a valid child.");
		}

		mHandleId = handleId;
		mContentId = contentId;
		mplayerListId = contentId2;

		a.recycle();

	}

	public void fillList() {

		String tableName = FlipperTeamList.selectedTeam;

		cur = Utils.db.query(tableName, null, null, null, null,
				null, null);

		int counter = cur.getCount();

		playerName = new String[counter];
		playerRole = new String[counter];

		to = new int[] { R.id.title };

		cur.moveToFirst();
		while (cur.isAfterLast() == false) {

			int currentposotion = cur.getPosition();
			String player = cur.getString(1).trim();
			String role = cur.getString(2);
			
			if(role != null && !role.equals("") )
				role = role.trim();
			playerName[currentposotion] = player;
			playerRole[currentposotion] = role;
			cur.moveToNext();
		}

		cur.close();

		IconicAdapter adapter = new IconicAdapter(mContext, playerName,
				playerRole);
		list = (ListView) findViewById(mplayerListId);
		list.setAdapter(adapter);

		// ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
		// R.layout.img_txt_layout, R.id.title, playerListData);
		//
		// list = (ListView) findViewById(mplayerListId);
		// list.setAdapter(adapter);

	}

	private LayoutInflater getSystemService(String layoutInflaterService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		mHandle = findViewById(mHandleId);
		if (mHandle == null) {
			throw new IllegalArgumentException(
					"The handle attribute is must refer to an"
							+ " existing child.");
		}

		mContent = findViewById(mContentId);
		if (mContent == null) {
			throw new IllegalArgumentException(
					"The content attribute is must refer to an"
							+ " existing child.");
		}

		mPlayerList = findViewById(mplayerListId);
		if (mPlayerList == null) {
			throw new IllegalArgumentException(
					"The content attribute is must refer to an"
							+ " existing child.");
		}

		mHandle.setOnClickListener(new PanelToggler());

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mContentHeight == 0) {
			// First, measure how high content wants to be
			mContent.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
			mContentHeight = mContent.getMeasuredHeight();
		}

		// Then let the usual thing happen
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	public class PanelToggler implements OnClickListener {
		@Override
		public void onClick(View v) {
			accordionPanel();
		}

		public void accordionPanel() {
			Animation a;
			
			ImageView arrow = (ImageView) findViewById(mHandleId);
			
			if (mExpanded) {
				fillList();
				arrow.setBackgroundResource(R.drawable.down);
;				mPlayerList.setVisibility(View.VISIBLE);
				a = new ExpandAnimation(mContentHeight, mCollapsedHeight);
			} else {
				arrow.setBackgroundResource(R.drawable.up);
				mPlayerList.setVisibility(View.GONE);
				a = new ExpandAnimation(mCollapsedHeight, mContentHeight);
			}
			a.setDuration(500);
			mContent.startAnimation(a);
			mExpanded = !mExpanded;
		}
	}

	private class ExpandAnimation extends Animation {
		private final int mStartHeight;
		private final int mDeltaHeight;

		public ExpandAnimation(int startHeight, int endHeight) {
			mStartHeight = startHeight;
			mDeltaHeight = endHeight - startHeight;
		}

		@Override
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			android.view.ViewGroup.LayoutParams lp = mContent.getLayoutParams();
			lp.height = (int) (mStartHeight + mDeltaHeight * interpolatedTime);
			mContent.setLayoutParams(lp);
		}

		@Override
		public boolean willChangeBounds() {
			// TODO Auto-generated method stub
			return true;
		}
	}
}
