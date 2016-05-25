package com.example.huangyi.myanimation.utils;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by huangyi on 2015/10/27.
 * 自定义EdiText输入框 的输入事件
 */
public class OnMyTextChanged implements TextWatcher {
    EditText mEditText;//想要绑定精度的EdiText
    int precision;//想要精确到小数点后面第几位
    int pointIndex;//圆点的位置 从0开始
    /**
     * 从第几个字符开始增加字符(判断是否可以显示此字符)
     * 若保留两位则不能显示0.000
     */
    int startIndex;
    /**
     * 是否输入了小数点
     */
    boolean hasPoint;
    /**
     * 保留圆点在末尾的字符串
     * 若连续输入两个小数点(不合理) 则显示此字符串
     */
    String hasOnePoint;
    /**
     * 若保留两位小数  则0.00为最终字符串  继续输入显示此字符串
     */
    String SucesssStr;//最终字符串

    /**
     * @param precision 想要精确到小数点后面多少位
     * @param edt       想要绑定此输入精度的输入框
     */
    public OnMyTextChanged(int precision, EditText edt) {
        this.mEditText = edt;
        this.precision = precision;
    }

    /**
     * 在EditText改变之前
     * start从几个字符开始的
     * after=0表示删除字符   =1表示增加字符
     */
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        startIndex = start;
        //判断是否删除点
        if (after == 0 && s.charAt(s.length() - 1) == '.') {
            hasOnePoint = "";
            hasPoint = false;
        }

    }

    /**
     * 在Deittext改变之时
     * s 目前输入锁得到的字符串
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 1 && s.charAt(0) == '.') {
            changeInputAndCancleListen(mEditText, "0.");
            s = "0.";

        }

        if (s.length() > 0) {
            if (s.charAt(s.length() - 1) == '.') {
                if (hasPoint) {
                    changeInputAndCancleListen(mEditText, hasOnePoint);
                    return;
                } else {
                    pointIndex = s.length() - 1;
                }
                hasPoint = true;
                hasOnePoint = s.toString();
            }
        }
    else {
            hasOnePoint = s.toString();
            hasPoint = false;
        }
    }

    /**
     * 在Deittext改变之后
     */
    @Override
    public void afterTextChanged(Editable s) {
        if (hasPoint && s.toString().length() == pointIndex + precision + 1) {
            SucesssStr = s.toString();
        } else if (hasPoint && s.toString().length() > pointIndex + precision + 1) {
            changeInputAndCancleListen(mEditText, SucesssStr);
        }
    }

    /**
     * 改变字符串  暂时解绑监听事件  移动光标
     *
     * @param editText
     * @param str
     */
    private void changeInputAndCancleListen(EditText editText, String str) {
        editText.removeTextChangedListener(this);
        editText.setText(str);
        editText.setSelection(str.length());
        editText.addTextChangedListener(this);
    }
}
