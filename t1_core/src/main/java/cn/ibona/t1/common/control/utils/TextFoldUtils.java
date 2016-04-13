package cn.ibona.t1.common.control.utils;

import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

/**
 * Created by Administrator on 2015/10/19.
 */
public class TextFoldUtils {

    public static void makeTextViewFloding(final TextView tv, final TextView floding_tv,final int maxLine, final String expandText, final boolean viewMore) {
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
                if (tv.getLineCount()>maxLine){
                    floding_tv.setVisibility(View.VISIBLE);
                    floding_tv.setText(expandText);
                    if (viewMore){//显示全部内容
                        tv.setMaxLines(2);
                        tv.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
                        floding_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makeTextViewFloding(tv,floding_tv, 2, "收起∧", false);
                            }
                        });
                    }else {//收起
                        tv.setEllipsize(null);
                        tv.setMaxLines(10);
                        floding_tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                makeTextViewFloding(tv,floding_tv, 2, "显示全部内容∨", true);
                            }
                        });
                    }
                }else {
                    floding_tv.setVisibility(View.GONE);
                }
            }
        });


    }

    public static void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {
                if (tv!=null){
                    ViewTreeObserver obs = tv.getViewTreeObserver();
                    obs.removeGlobalOnLayoutListener(this);
                    if (maxLine == -1) {
                        int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                        String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    } else if (maxLine > 0 && tv.getLineCount() > maxLine) {
                        int lineEndIndex = tv.getLayout().getLineEnd(maxLine-1);
                        String text = tv.getText().subSequence(0, lineEndIndex - expandText.length()-1) + "...  " + expandText;
                        tv.setText(text);
                        tv.setMovementMethod(LinkMovementMethod.getInstance());
                        tv.setText(
                                addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                        viewMore), TextView.BufferType.SPANNABLE);
                    } else {
                        tv.setText(tv.getText());
                    }
                }

            }
        });

    }

    private static SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                            final int maxLine, final String spanableText, final boolean viewMore) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {

                    if (viewMore) {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, -1, "收起∧", false);
                    } else {
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        makeTextViewResizable(tv, 2, "查看更多∨", true);
                    }

                }
                public void updateDrawState(TextPaint ds) {
//                    ds.setColor(0xffa8a8a8);
                    ds.setColor(ds.linkColor);
                    ds.setUnderlineText(false);    //去除超链接的下划线
                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }
}
