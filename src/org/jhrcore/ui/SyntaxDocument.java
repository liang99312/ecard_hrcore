package org.jhrcore.ui;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import javax.swing.event.UndoableEditListener;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Element;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import org.jhrcore.comm.CodeManager;
import org.jhrcore.entity.Code;

/**
 * Copyright (c) Ontos AG (http://www.ontosearch.com). This class is part of
 * JAPE Debugger component for GATE (Copyright (c) "The University of Sheffield"
 * see http://gate.ac.uk/) <br>
 * 
 * @author Oleg Mishenko // create
 * @author wangzhenhua // modifed by wangzhenhua 2006-09-30
 */
public class SyntaxDocument extends DefaultStyledDocument {

    private static final long serialVersionUID = 1L;

    public static void main(String args[]) {
//		/**final JFrame fm = new JFrame("test code editor");
//		fm.setSize(200, 200);
//		final SyntaxDocument sd = new SyntaxDocument();
//
//		sd.getKeyword_groups().put("王振华", "人员");
//		sd.getKeyword_groups().put("张秋菊", "人员");
//		List<String> list = new ArrayList<String>();
//		list.add("王振华");
//		list.add("张秋菊");
//		sd.getLookups2().put("人员", list);
//
//		final JTextPane txtPane = new JTextPane(sd);
//		fm.getContentPane().add(new JScrollPane(txtPane));
//		JButton btn = new JButton("123");
//
//		fm.addWindowListener(new WindowAdapter() {
//            @Override
//			public void windowClosing(WindowEvent arg0) {
//				System.exit(0);
//			}
//
//		});
//		txtPane.addMouseMotionListener(new MouseMotionAdapter() {
//            @Override
//            public void mouseMoved(MouseEvent e) {
//				/*
//				 * int caret_p = txtPane.getCaretPosition(); Point p2 = null;
//				 * for (Point p : sd.getKeywordPositions()) { if (caret_p > p.x &&
//				 * caret_p <= p.y) { p2 = p; break; } } if (p2 != null) {
//				 * txtPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR) ); }
//				 * else txtPane.setCursor(Cursor.getDefaultCursor());
//				 */
//			}
//		});
//
//		txtPane.addMouseListener(new MouseAdapter() {
//
//			public void mouseClicked(MouseEvent arg0) {
//				int caret_p = txtPane.getCaretPosition();
//				Point p2 = null;
//				for (Point p : sd.getKeywordPositions()) {
//					if (caret_p > p.x && caret_p <= p.y) {
//						p2 = p;
//						break;
//					}
//				}
//				if (p2 != null) {
//					txtPane.setSelectionStart(p2.x);
//					txtPane.setSelectionEnd(p2.y);
//					try {
//						String s = txtPane.getText(p2.x, p2.y - p2.x);
//						String group = sd.getKeyword_groups().get(s);
//						List<String> list = sd.getLookups2().get(group);
//						final Point p3 = p2;
//						final JList tmpList = new JList(list.toArray());
//						tmpList.setSelectedIndex(list.indexOf(s));
//						final JDialog dlg = new JDialog((JFrame) null, "请选择");
//						tmpList.addMouseListener(new MouseAdapter() {
//
//							public void mouseClicked(MouseEvent arg0) {
//								// if (arg0.getClickCount() != 2)
//								// return;
//								String tmp = (String) tmpList
//										.getSelectedValue();
//								dlg.dispose();
//								if (tmp == null)
//									return;
//
//								txtPane.replaceSelection(tmp);
//							}
//
//						});
//						dlg.getContentPane().add(new JScrollPane(tmpList),
//								BorderLayout.CENTER);
//						dlg.setLocationRelativeTo(fm);
//						dlg.setLocation(fm.getLocation().x + arg0.getX(), fm
//								.getLocation().y
//								+ arg0.getY());
//						dlg.setSize(200, 200);
//						dlg.setModal(true);
//						dlg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//						dlg.setVisible(true);
//						// System.out.println(group);
//					} catch (BadLocationException e) {
//						e.printStackTrace();
//					}
//
//				} // else
//				// System.out.println("aaaangzhen");
//
//				/*
//				 * String s = "ppp:"; for (Point p : sd.getKeywordPositions()){
//				 * s = s + p.x + "," + p.y + ";"; } fm.setTitle(s);
//				 */
//			}
//
//		});
//
//		btn.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				System.out.println(txtPane.getText());
//			}
//		});
//		fm.getContentPane().add(btn, BorderLayout.SOUTH);
//		fm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//		fm.setVisible(true);
//**/
    }
    private DefaultStyledDocument doc;
    private Element rootElement;
    private boolean multiLineComment;
    private MutableAttributeSet normal;
    private MutableAttributeSet keyword;
    private MutableAttributeSet keyword2;
    private MutableAttributeSet comment;
    private MutableAttributeSet quote;
    private Hashtable<String, Object> keywords;
    // 中文关键字,需要区分中文关键字的原因主要是:英文比较好分词(空格分割),中文比较困难
    //private java.util.List<String> c_keywords;
    private Hashtable<String, String> k_keywords = new Hashtable<String, String>();
    private Hashtable<String, List> lookups = new Hashtable<String, List>();
    private Hashtable<String, String> keyword_groups = new Hashtable<String, String>();
//	public void putChineseKeyWord(String word) {
//		c_keywords.add(word);
//	}

    public SyntaxDocument() {
        doc = this;
        rootElement = doc.getDefaultRootElement();
        putProperty(DefaultEditorKit.EndOfLineStringProperty, "\n");

        normal = new SimpleAttributeSet();
        StyleConstants.setForeground(normal, Color.black);

        comment = new SimpleAttributeSet();
        StyleConstants.setForeground(comment, Color.gray);
        StyleConstants.setItalic(comment, true);

        keyword = new SimpleAttributeSet();
        StyleConstants.setForeground(keyword, Color.BLUE);
        keyword2 = new SimpleAttributeSet();
        StyleConstants.setForeground(keyword2, Color.red);

        quote = new SimpleAttributeSet();
        StyleConstants.setForeground(quote, Color.red);
        List<String> keys = new ArrayList<String>();
        //SQL
        keys.add("and");
        keys.add("asc");
        keys.add("avg");
        keys.add("call");
        keys.add("case");
        keys.add("cast");
        keys.add("count");
        keys.add("delete");
        keys.add("desc");
        keys.add("else");
        keys.add("end");
        keys.add("exists");
        keys.add("exec");
        keys.add("false");
        keys.add("float");
        keys.add("from");
        keys.add("having");
        keys.add("if");
        keys.add("in");
        keys.add("inner");
        keys.add("insert");
        keys.add("int");
        keys.add("is");
        keys.add("isnull");
        keys.add("join");
        keys.add("left");
        keys.add("like");
        keys.add("long");
        keys.add("ltrim");
        keys.add("max");
        keys.add("min");
        keys.add("not");
        keys.add("null");
        keys.add("nvl");
        keys.add("outer");
        keys.add("or");
        keys.add("right");
        keys.add("round");
        keys.add("rtrim");
        keys.add("select");
        keys.add("set");
        keys.add("sum");
        keys.add("then");
        keys.add("trim");
        keys.add("true");
        keys.add("update");
        keys.add("when");
        keys.add("where");
        keys.add("while");
        keys.add("group by");
        keys.add("order by");
        //JAVA
        keys.add("abstract");
        keys.add("boolean");
        keys.add("break");
        keys.add("byte");
        keys.add("byvalue");
        keys.add("catch");
        keys.add("char");
        keys.add("class");
        keys.add("const");
        keys.add("continue");
        keys.add("default");
        keys.add("do");
        keys.add("double");
        keys.add("extends");
        keys.add("final");
        keys.add("finally");
        keys.add("for");
        keys.add("future");
        keys.add("generic");
        keys.add("goto");
        keys.add("import");
        keys.add("implements");
        keys.add("Input");
        keys.add("instanceof");
        keys.add("interface");
        keys.add("Macro");
        keys.add("MultiPhase");
        keys.add("native");
        keys.add("new");
        keys.add("operator");
        keys.add("package");
        keys.add("Phase");
        keys.add("Priority");
        keys.add("private");
        keys.add("protected");
        keys.add("public");
        keys.add("rest");
        keys.add("return");
        keys.add("Rule");
        keys.add("short");
        keys.add("static");
        keys.add("super");
        keys.add("switch");
        keys.add("synchronized");
        keys.add("this");
        keys.add("throw");
        keys.add("throws");
        keys.add("transient");
        keys.add("try");
        keys.add("var");
        keys.add("void");
        keys.add("volatile");

        initKeyWords(keys);
    }

    private void initKeyWords(List<String> keys) {
        Object dummyObject = new Object();
        keywords = new Hashtable<String, Object>();
        for (String key : keys) {
            keywords.put(key, dummyObject);
        }
    }
    /*
     * Override to apply syntax highlighting after the document has been updated
     */

    public void insertString(int offset, String str, AttributeSet a)
            throws BadLocationException {
        /*if (str.equals("\n")){
        int startLine = rootElement.getElementIndex(offset);
        String tmp = doc.getText(rootElement.getElement(startLine).getStartOffset(), rootElement.getElement(startLine).getEndOffset());
        System.out.println("tmp===" + tmp);
        if (tmp.trim().startsWith("when")){
        str = "\n";
        int m =  tmp.indexOf("when");
        for (int i = 0; i < m; i++){
        str = str + " ";
        }
        str = str + "when then";
        }
        } else */
//        int skip_len = 0;
        if (str.equals("\t")) {
            str = "    ";
        } else if (str.equals("{")) {
            str = addMatchingBrace(offset);
        } else if (str.equals("(")) {
            str = "()";
        } else if (str.equals("e") && (offset >= 3) && doc.getText(offset - 3, 3).equals("cas")) {
            str = "e\n" + "    when  then \n" + "    else \n" + "end";
        }
//            else if (str.equals("f") && (offset >= 1) && doc.getText(offset - 1, 1).equals("i")) {
//                str = "f\n" + "    then \n" + "    else \n" + "end";
////            skip_len = ("f\n" + "    then ").length();
//            }
        super.insertString(offset, str, null);//a);
        processChangedLines(offset, str.length());
    }

    /*
     * Override to apply syntax highlighting after the document has been updated
     */
    public void remove(int offset, int length) throws BadLocationException {
        super.remove(offset, length);
//        System.out.println("kkkkkkkk:"+offset);
        processChangedLines(offset, 0);
    }

    /*
     * Determine how many lines have been changed, then apply highlighting to
     * each line
     */
    private void processChangedLines(int offset, int length)
            throws BadLocationException {
        keywordPositions.clear();
        UndoableEditListener[] listeners = doc.getUndoableEditListeners();
        for (UndoableEditListener listener : listeners) {
            doc.removeUndoableEditListener(listener);
        }
        String content = doc.getText(0, doc.getLength());

        // The lines affected by the latest document update

        int startLine = rootElement.getElementIndex(offset);
        int endLine = rootElement.getElementIndex(offset + length);

        // Make sure all comment lines prior to the start line are commented
        // and determine if the start line is still in a multi line comment

        setMultiLineComment(commentLinesBefore(content, startLine));

        // Do the actual highlighting

        // for (int i = startLine; i <= endLine; i++) {
        // applyHighlighting(content, i);
        // }
        applyHighlighting(content, 0);

        // Resolve highlighting to the next end multi line delimiter

        if (isMultiLineComment()) {
            commentLinesAfter(content, endLine);
        } else {
            highlightLinesAfter(content, endLine);
        }
        for (UndoableEditListener listener : listeners) {
            doc.addUndoableEditListener(listener);
        }
    }

    /*
     * Highlight lines when a multi line comment is still 'open' (ie. matching
     * end delimiter has not yet been encountered)
     */
    private boolean commentLinesBefore(String content, int line) {
        int offset = rootElement.getElement(line).getStartOffset();

        // Start of comment not found, nothing to do

        int startDelimiter = lastIndexOf(content, getStartDelimiter(),
                offset - 2);

        if (startDelimiter < 0) {
            return false;
        }

        // Matching start/end of comment found, nothing to do

        int endDelimiter = indexOf(content, getEndDelimiter(), startDelimiter);

        if (endDelimiter < offset & endDelimiter != -1) {
            return false;
        }

        // End of comment not found, highlight the lines

        doc.setCharacterAttributes(startDelimiter, offset - startDelimiter + 1,
                comment, false);
        return true;
    }

    /*
     * Highlight comment lines to matching end delimiter
     */
    private void commentLinesAfter(String content, int line) {
        int offset = rootElement.getElement(line).getEndOffset();

        // End of comment not found, nothing to do

        int endDelimiter = indexOf(content, getEndDelimiter(), offset);

        if (endDelimiter < 0) {
            return;
        }

        // Matching start/end of comment found, comment the lines

        int startDelimiter = lastIndexOf(content, getStartDelimiter(),
                endDelimiter);

        if (startDelimiter < 0 || startDelimiter <= offset) {
            doc.setCharacterAttributes(offset, endDelimiter - offset + 1,
                    comment, false);
        }
    }

    /*
     * Highlight lines to start or end delimiter
     */
    private void highlightLinesAfter(String content, int line) {
        int offset = rootElement.getElement(line).getEndOffset();

        // Start/End delimiter not found, nothing to do

        int startDelimiter = indexOf(content, getStartDelimiter(), offset);
        int endDelimiter = indexOf(content, getEndDelimiter(), offset);

        if (startDelimiter < 0) {
            startDelimiter = content.length();
        }

        if (endDelimiter < 0) {
            endDelimiter = content.length();
        }

        int delimiter = Math.min(startDelimiter, endDelimiter);

        if (delimiter < offset) {
            return;
        }

        // Start/End delimiter found, reapply highlighting

        int endLine = rootElement.getElementIndex(delimiter);

        for (int i = line + 1; i < endLine; i++) {
            Element branch = rootElement.getElement(i);
            Element leaf = doc.getCharacterElement(branch.getStartOffset());
            AttributeSet as = leaf.getAttributes();

            if (as.isEqual(comment)) {
                applyHighlighting(content, i);
            }
        }
    }

    /*
     * Parse the line to determine the appropriate highlighting
     */
    private void applyHighlighting(String content, int line) {
        int startOffset = rootElement.getElement(line).getStartOffset();
        int endOffset = rootElement.getElement(line).getEndOffset() - 1;

        int lineLength = endOffset - startOffset;
        int contentLength = content.length();

        if (endOffset >= contentLength) {
            endOffset = contentLength - 1;
        }

        // check for multi line comments
        // (always set the comment attribute for the entire line)

        if (endingMultiLineComment(content, startOffset, endOffset)
                || isMultiLineComment()
                || startingMultiLineComment(content, startOffset, endOffset)) {
            doc.setCharacterAttributes(startOffset,
                    endOffset - startOffset + 1, comment, false);
            return;
        }

        // set normal attributes for the line

        doc.setCharacterAttributes(startOffset, lineLength, normal, true);

        // check for single line comment

        int index = content.indexOf(getSingleLineDelimiter(), startOffset);

        if ((index > -1) && (index < endOffset)) {
            doc.setCharacterAttributes(index, endOffset - index + 1, comment,
                    false);
            endOffset = index - 1;
        }

        // check for tokens

        checkForTokens(content, startOffset, endOffset);
    }

    /*
     * public String getText(int offset, int length) throws BadLocationException {
     * return
     * DebugController.getInstance().getRuleController().getJapeText().substring(offset,
     * offset + length); }
     */

    /*
     * Does this line contain the start delimiter
     */
    private boolean startingMultiLineComment(String content, int startOffset,
            int endOffset) {
        int index = indexOf(content, getStartDelimiter(), startOffset);

        if ((index < 0) || (index > endOffset)) {
            return false;
        } else {
            setMultiLineComment(true);
            return true;
        }
    }

    /*
     * Does this line contain the end delimiter
     */
    private boolean endingMultiLineComment(String content, int startOffset,
            int endOffset) {
        int index = indexOf(content, getEndDelimiter(), startOffset);

        if ((index < 0) || (index > endOffset)) {
            return false;
        } else {
            setMultiLineComment(false);
            return true;
        }
    }

    /*
     * We have found a start delimiter and are still searching for the end
     * delimiter
     */
    private boolean isMultiLineComment() {
        return multiLineComment;
    }

    private void setMultiLineComment(boolean value) {
        multiLineComment = value;
    }

    // 检查是否有中文关键字,如果有,则高亮度显示
    private void checkKeyWord(String content, int startOffset,
            int endOffset) {
        int len = content.length();
        for (String word : keywords.keySet()) {
            int fromIndex = startOffset;
            int t = content.indexOf(word, fromIndex);
            int k_len = word.length();
            while (t != -1) {
                String k_word = "";
                if (t > 0) {
                    k_word = content.substring(t - 1, t);
                }
                k_word += word;
                if (t < (len - k_len)) {
                    k_word += content.substring(t + k_len, t + k_len + 1);
                }
                k_word = k_word.trim();
                if (k_word.equals(word) || k_word.startsWith(")") || k_word.endsWith("(")) {
                    doc.setCharacterAttributes(t, word.length(), keyword, false);
                }
                fromIndex = t + word.length();
                t = content.indexOf(word, fromIndex);
            }
        }
    }

    /*
     * Parse the line for tokens to highlight
     */
    private void checkForTokens(String content, int startOffset, int endOffset) {
        checkKeyWord(content, startOffset, endOffset);
        checkChineseKeyWord(content, startOffset, endOffset);/*
        while (startOffset <= endOffset) {
        // skip the delimiters to find the start of a new token
        
        while (isDelimiter(content.substring(startOffset, startOffset + 1))) {
        if (startOffset < endOffset)
        startOffset++;
        else
        return;
        }
        
        // Extract and process the entire token
        
        if (isQuoteDelimiter(content
        .substring(startOffset, startOffset + 1)))
        startOffset = getQuoteToken(content, startOffset, endOffset);
        else
        startOffset = getOtherToken(content, startOffset, endOffset);
        }*/
    }

    /*
     *
     */
    private int getQuoteToken(String content, int startOffset, int endOffset) {
        String quoteDelimiter = content.substring(startOffset, startOffset + 1);
        String escapeString = getEscapeString(quoteDelimiter);

        int index;
        int endOfQuote = startOffset;

        // skip over the escape quotes in this quote

        index = content.indexOf(escapeString, endOfQuote + 1);

        while ((index > -1) && (index < endOffset)) {
            endOfQuote = index + 1;
            index = content.indexOf(escapeString, endOfQuote);
        }

        // now find the matching delimiter

        index = content.indexOf(quoteDelimiter, endOfQuote + 1);

        if ((index < 0) || (index > endOffset)) {
            endOfQuote = endOffset;
        } else {
            endOfQuote = index;
        }

        doc.setCharacterAttributes(startOffset, endOfQuote - startOffset + 1,
                quote, false);

        return endOfQuote + 1;
    }
    private List<Point> keywordPositions = new ArrayList<Point>();

    // 检查是否有中文关键字,如果有,则高亮度显示
    private void checkChineseKeyWord(String content, int startOffset,
            int endOffset) {
        for (String word : k_keywords.keySet()) {
            //for (String word : c_keywords) {
            int fromIndex = startOffset;
            int t = content.indexOf(word, fromIndex);
            while (t != -1) {
                keywordPositions.add(new Point(t, word.length() + t));
                doc.setCharacterAttributes(t, word.length(), keyword2, false);
                fromIndex = t + word.length();
                t = content.indexOf(word, fromIndex);
            }
        }
    }

    /*
     * Assume the needle will the found at the start/end of the line
     */
    private int indexOf(String content, String needle, int offset) {
        int index;

        while ((index = content.indexOf(needle, offset)) != -1) {
            String text = getLine(content, index).trim();

            if (text.startsWith(needle) || text.endsWith(needle)) {
                break;
            } else {
                offset = index + 1;
            }
        }

        return index;
    }

    /*
     * Assume the needle will the found at the start/end of the line
     */
    private int lastIndexOf(String content, String needle, int offset) {
        int index;

        while ((index = content.lastIndexOf(needle, offset)) != -1) {
            String text = getLine(content, index).trim();

            if (text.startsWith(needle) || text.endsWith(needle)) {
                break;
            } else {
                offset = index - 1;
            }
        }

        return index;
    }

    private String getLine(String content, int offset) {
        int line = rootElement.getElementIndex(offset);
        Element lineElement = rootElement.getElement(line);
        int start = lineElement.getStartOffset();
        int end = lineElement.getEndOffset();
        return content.substring(start, end - 1);
    }

    /*
     * Override for other languages
     */
    protected boolean isDelimiter(String character) {
        String operands = ";:{}()[]+-/%<=>!&|^~*";

        if (Character.isWhitespace(character.charAt(0))
                || operands.indexOf(character) != -1) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Override for other languages
     */
    protected boolean isQuoteDelimiter(String character) {
        String quoteDelimiters = "\"'";

        if (quoteDelimiters.indexOf(character) < 0) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Override for other languages
     */
    protected boolean isKeyword(String token) {
        Object o = keywords.get(token);

        return o == null ? false : true;
    }

    /*
     * Override for other languages
     */
    protected String getStartDelimiter() {
        return "/*";
    }

    /*
     * Override for other languages
     */
    protected String getEndDelimiter() {
        return "*/";
    }

    /*
     * Override for other languages
     */
    protected String getSingleLineDelimiter() {
        return "//";
    }

    /*
     * Override for other languages
     */
    protected String getEscapeString(String quoteDelimiter) {
        return "\\" + quoteDelimiter;
    }

    /*
     *
     */
    protected String addMatchingBrace(int offset) throws BadLocationException {
        StringBuffer whiteSpace = new StringBuffer();
        int line = rootElement.getElementIndex(offset);
        int i = rootElement.getElement(line).getStartOffset();

        while (true) {
            String temp = doc.getText(i, 1);

            if (temp.equals(" ") || temp.equals("\t")) {
                whiteSpace.append(temp);
                i++;
            } else {
                break;
            }
        }

        return "{\n" + whiteSpace.toString() + "\t\n" + whiteSpace.toString()
                + "}";
    }

//	public java.util.List<String> getC_keywords() {
//		return c_keywords;
//	}
//
//	public void setC_keywords(java.util.List<String> c_keywords) {
//		this.c_keywords = c_keywords;
//	}
    public List<Point> getKeywordPositions() {
        return keywordPositions;
    }

    public Hashtable<String, List> getLookups2() {
        return lookups;
    }

//    public static void setLookups(Hashtable<String, List> alookups) {
//        lookups = alookups;
//    }
    public Hashtable<String, String> getKeyword_groups() {
        return keyword_groups;
    }

//    private static void setKeyword_groups(Hashtable<String, String> akeyword_groups) {
//        keyword_groups = akeyword_groups;
//    }
    public Hashtable<String, String> getK_keywords() {
        return k_keywords;
    }

//    private static void setK_keywords(Hashtable<String, String> ak_keywords) {
//        k_keywords = ak_keywords;
//    }
    public void revokeKeys(Hashtable<String, List> lookup, Hashtable<String, String> keyword_group, Hashtable<String, String> k_keyword) {
        lookups.clear();
        k_keywords.clear();
        keyword_groups.clear();
        Hashtable<String, Hashtable<String, Code>> sys_codes = CodeManager.getCodeManager().getHt_types();
        for (String tmp : sys_codes.keySet()) {
            List<Code> list = CodeManager.getCodeManager().getCodeListBy(tmp);
            lookups.put(tmp, list);
            for (Code c : list) {
                k_keywords.put("[" + tmp + "." + c.getCode_name() + "]", c.getCode_id());
                keyword_groups.put("[" + tmp + "." + c.getCode_name() + "]", tmp);
            }
        }
        if (lookup != null) {
            for (String key : lookup.keySet()) {
                lookups.put(key, lookup.get(key));
            }
        }
        if (k_keyword != null) {
            for (String key : k_keyword.keySet()) {
                k_keywords.put(key, k_keyword.get(key));
            }
        }
        if (keyword_group != null) {
            for (String key : keyword_group.keySet()) {
                keyword_groups.put(key, keyword_group.get(key));
            }
        }
    }
}

/*
 * import java.io.BufferedReader; import java.io.FileNotFoundException; import
 * java.io.FileReader; import java.io.FileWriter; import java.io.IOException;
 * import java.io.PrintWriter;
 * 
 * 
 * public class SyntaxDocument { public static void main(String args[]) { try {
 * BufferedReader reader = new BufferedReader(new FileReader("e:/123.txt"));
 * PrintWriter writer = new PrintWriter(new FileWriter("e:/321.txt"));
 * 
 * while(true) { String line = reader.readLine(); if (line == null) {
 * reader.close(); break; } writer.println(line.substring(3)); } writer.close(); }
 * catch (FileNotFoundException e) { e.printStackTrace(); } catch (IOException
 * e) { e.printStackTrace(); } } }
 */
