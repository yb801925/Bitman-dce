package cn.dagongniu.bitman.assets.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.dagongniu.bitman.R;
import cn.dagongniu.bitman.customview.popwindow.BasePopupWindow;
import cn.dagongniu.bitman.utils.DensityUtils;

/**
 * 红包更多
 */
public class RedMorePopWind extends BasePopupWindow {


    public RedMorePopWind(Context context,OnSendClickListener onSendClickListener,OnReceiveClickListener onReceiveClickListener) {
        super(context);

        View view = LayoutInflater.from(context).inflate(R.layout.popwindow_red_more_layout, null);

        setContentView(view);
        RelativeLayout rl_set_red = view.findViewById(R.id.rl_set_red);
        RelativeLayout rl_set_get = view.findViewById(R.id.rl_set_get);

        int[] wg = DensityUtils.getWidthHeight(context);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ButterKnife.bind(this, view);

        rl_set_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSendClickListener.OnSend();
            }
        });

        rl_set_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReceiveClickListener.OnReceive();
            }
        });

    }


    public void showPop(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        showAtLocation(view, Gravity.LEFT | Gravity.BOTTOM, 0, -location[1]);
    }

    @OnClick({R.id.rl_cancel})
    public void onClicked(View view) {
        this.dismiss();
    }

    /**
     * 发出的红包
     */
    public interface OnSendClickListener {
        public void OnSend();
    }

    /**
     * 收到的红包
     */
    public interface OnReceiveClickListener {
        public void OnReceive();
    }
}
