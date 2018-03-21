package com.mytv.rtzhdj.mvp.ui.widget;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义验证码
 * 
 * @author 帽檐遮不住阳光
 * @Date 2016/5/11
 * 
 */
public class CodeView extends View {

	/**
	 * 验证码长度
	 */
	private final int CODELENGTH = 4;

	/**
	 * 验证码字体大小
	 */
	private final int TEXTZISE = 80;

	/**
	 * 干扰线条的数目
	 */
	private final int LINENUM = 5;

	/**
	 * 验证码X坐标
	 */
	private int CODE_X;

	/**
	 * 验证码Y坐标
	 */
	private int CODE_Y;

	/**
	 * 验证码每个字符X轴之间的间距
	 */
	private final int PADDING_X = 20;

	/**
	 * 验证码每个字符Y轴之间的间距
	 */
	private final int PADDING_Y = 25;

	/**
	 * 每次随机生成验证码时X轴最大值
	 */
	private final int RANDOM_MAX_X = 40;

	/**
	 * 每次随机生成验证码时Y轴最大值
	 */
	private final int RANDOM_MAX_Y = 60;

	/**
	 * 如果State为True,则随机生成验证码 如果State为False,则生成用户输入框输入的验证码
	 */
	private boolean state = true;

	/**
	 * 验证码背景框的宽
	 */
	private final int WIDTH = 160;

	/**
	 * 验证码背景框的高
	 */
	private final int HEIGHT = 100;

	/**
	 * 验证码
	 */
	private String myCode;

	Paint paint;
	private Random random = new Random();
	private static final char[] CODES = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
			'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z' };

	public CodeView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public CodeView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		paint = new Paint();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		CODE_X = 0;// 将x轴位置设为0
		paint.setTextSize(TEXTZISE);// 设置字体大小
		canvas.drawColor(Color.rgb(219, 204, 133));// 背景颜色

		// 画验证码

		if (state) {
			// 如果为True 则随机生成验证码
			myCode = createCode();
		}

		for (int i = 0; i < CODELENGTH; i++) {
			CODE_X += PADDING_X + random.nextInt(RANDOM_MAX_X);
			CODE_Y = PADDING_Y + Math.abs(random.nextInt(RANDOM_MAX_Y));
			paint.setColor(randomColor());
			paint.setFakeBoldText(random.nextBoolean()); // 随机粗体/非粗体
//			canvas.drawText(myCode.charAt(i) + "", CODE_X, CODE_Y, paint);
			canvas.drawText(myCode.charAt(i) + "", CODE_X, 80, paint);
		}

		// 画干扰线条
		for (int i = 0; i < LINENUM; i++) {
			drawLine(canvas, paint);
		}
	}

	/**
	 * 随机生成验证码
	 * 
	 * @return
	 */
	private String createCode() {
		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < CODELENGTH; i++) {
			buffer.append(CODES[random.nextInt(CODES.length)]);
		}
		return buffer.toString();
	}

	/***
	 * 画干扰线条
	 * 
	 * @param canvas
	 * @param paint
	 */
	private void drawLine(Canvas canvas, Paint paint) {
		int startX = random.nextInt(getWidth()); // 线条起始X坐标
		int startY = random.nextInt(getHeight()); // 线条起始Y坐标
		int stopX = random.nextInt(getWidth()); // 线条结束X坐标
		int stopY = random.nextInt(getHeight()); // 线条结束Y坐标
		paint.setStrokeWidth(2);// 设置线条的粗
		paint.setColor(randomColor());// 设置线条颜色
		canvas.drawLine(startX, startY, stopX, stopY, paint);// 画干扰线条
	}

	/***
	 * 随机生成RGB
	 * 
	 * @return
	 */
	private int randomColor() {
		int red = random.nextInt(256);
		int green = random.nextInt(256);
		int blue = random.nextInt(256);
		return Color.rgb(red, green, blue);
	}

	/**
	 * 刷新验证码
	 */
	public void refresh(boolean userState, String Code) {
		myCode = Code;
		state = userState;
		invalidate();
	}

	public String getCode() {
		return myCode;
	}

}
