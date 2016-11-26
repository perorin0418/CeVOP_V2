package org.net.perorin.cevop2.parts.stringLimitation;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.StyleConstants;

public class TabCharLimitation extends PlainDocument {
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		// 文字列が入ってきていないのにメソッドが呼ばれた場合は何もしない。
		if (str == null)
			return;

		// IMEの変換中の文字列はそのままinsertする
		if ((a != null) && (a.isDefined(StyleConstants.ComposedTextAttribute))) { // <-----ここ
			super.insertString(offs, str, a);
			return;
		}

		// 実際に挿入をするオフセット
		int realOffs = offs;

		// 入力文字列を一文字ずつ判定
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (c != '\t') {
				super.insertString(realOffs, String.valueOf(c), a);
				realOffs++;
			} else {
				break;
			}
		}
	}
}
