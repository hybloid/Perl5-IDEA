// Generated by JFlex 1.9.2 http://jflex.de/  (tweaked for IntelliJ platform)
// source: grammar/Mojolicious.flex

package com.perl5.lang.mojolicious.lexer;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;


public class MojoliciousLexer extends MojoliciousBaseLexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int PERL_BLOCK = 2;
  public static final int PERL_EXPR_BLOCK = 4;
  public static final int PERL_LINE = 6;
  public static final int PERL_EXPR_LINE = 8;
  public static final int NON_CLEAR_LINE = 10;
  public static final int BLOCK_COMMENT = 12;
  public static final int CHECK_SPACE_CLEAR_LINE = 14;
  public static final int CHECK_SPACE = 16;
  public static final int AFTER_PERL_BLOCK = 18;
  public static final int AFTER_PERL_LINE = 20;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = {
     0,  0,  1,  1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7, 
     8,  8,  9,  9, 10, 10
  };

  /**
   * Top-level table for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_TOP = zzUnpackcmap_top();

  private static final String ZZ_CMAP_TOP_PACKED_0 =
    "\1\0\25\u0100\1\u0200\11\u0100\1\u0300\17\u0100\1\u0400\247\u0100"+
    "\10\u0500\u1020\u0100";

  private static int [] zzUnpackcmap_top() {
    int [] result = new int[4352];
    int offset = 0;
    offset = zzUnpackcmap_top(ZZ_CMAP_TOP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_top(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Second-level tables for translating characters to character classes
   */
  private static final int [] ZZ_CMAP_BLOCKS = zzUnpackcmap_blocks();

  private static final String ZZ_CMAP_BLOCKS_PACKED_0 =
    "\11\0\1\1\1\2\1\3\1\4\1\5\22\0\1\1"+
    "\2\0\1\6\1\0\1\7\26\0\1\10\1\11\1\12"+
    "\43\0\1\13\1\0\1\14\1\15\1\0\1\16\1\0"+
    "\1\17\4\0\1\20\26\0\1\3\32\0\1\21\u01df\0"+
    "\1\21\177\0\13\21\35\0\2\3\5\0\1\21\57\0"+
    "\1\21\240\0\1\21\377\0\u0100\22";

  private static int [] zzUnpackcmap_blocks() {
    int [] result = new int[1536];
    int offset = 0;
    offset = zzUnpackcmap_blocks(ZZ_CMAP_BLOCKS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackcmap_blocks(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /**
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\13\0\1\1\2\2\1\3\1\1\10\4\2\5\3\4"+
    "\2\6\1\7\1\1\3\10\1\11\1\12\1\11\1\13"+
    "\1\14\1\13\1\15\1\16\1\15\1\17\1\20\1\21"+
    "\1\22\1\23\3\0\1\24\5\0\3\25\1\26\1\0"+
    "\3\12\1\21\1\27\1\30\1\31\4\0\1\32\1\12"+
    "\1\31\4\0\3\33\4\0\3\34";

  private static int [] zzUnpackAction() {
    int [] result = new int[92];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /**
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\23\0\46\0\71\0\114\0\137\0\162\0\205"+
    "\0\230\0\253\0\276\0\321\0\344\0\367\0\u010a\0\u011d"+
    "\0\u0130\0\u0143\0\u0156\0\u0169\0\u017c\0\u018f\0\u01a2\0\u01b5"+
    "\0\u0130\0\u01b5\0\u01c8\0\u01db\0\u01ee\0\u0130\0\u01ee\0\u0201"+
    "\0\u0214\0\u0227\0\u023a\0\u024d\0\u0130\0\u0260\0\u0273\0\u0130"+
    "\0\u0286\0\u0299\0\u0130\0\u0299\0\u0299\0\u02ac\0\u0130\0\u02bf"+
    "\0\u02d2\0\u0130\0\u0143\0\u02e5\0\u02f8\0\u0130\0\u018f\0\u01b5"+
    "\0\u030b\0\u031e\0\u01ee\0\u0130\0\u01ee\0\u0331\0\u0130\0\u023a"+
    "\0\u0344\0\u0357\0\u036a\0\u0130\0\u0130\0\u0130\0\u037d\0\u0390"+
    "\0\u03a3\0\u03b6\0\u03c9\0\u0130\0\u0130\0\u0130\0\u03dc\0\u03ef"+
    "\0\u0402\0\u0415\0\u0130\0\u03c9\0\u0428\0\u043b\0\u044e\0\u0461"+
    "\0\u0474\0\u0130\0\u044e\0\u0487";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[92];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length() - 1;
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /**
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpacktrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\14\2\15\1\16\2\15\1\14\1\17\1\20\12\14"+
    "\7\21\1\22\1\21\1\23\1\21\1\24\1\21\1\25"+
    "\14\21\1\26\1\21\1\27\12\21\1\30\1\31\3\32"+
    "\5\21\1\33\1\21\1\34\3\21\1\30\2\21\1\35"+
    "\1\36\3\37\13\21\1\35\1\21\1\14\1\40\1\15"+
    "\1\16\1\40\1\15\1\14\1\41\1\20\12\14\7\42"+
    "\1\43\1\42\1\44\11\42\7\45\1\46\1\47\22\45"+
    "\1\47\12\45\1\50\1\51\2\52\1\51\1\52\15\50"+
    "\1\53\1\54\2\55\1\54\1\55\15\53\1\14\2\0"+
    "\1\14\2\0\1\14\2\0\12\14\1\0\5\15\15\0"+
    "\1\14\2\15\1\16\2\15\1\14\2\0\12\14\6\0"+
    "\1\56\1\57\1\0\1\60\20\0\1\61\50\0\1\62"+
    "\17\0\1\63\30\0\1\64\25\0\1\65\14\0\1\66"+
    "\17\0\1\67\14\0\1\70\1\31\3\32\13\0\1\70"+
    "\16\0\1\71\25\0\1\72\3\0\1\73\1\74\2\75"+
    "\1\76\13\0\1\73\2\0\1\40\2\15\1\40\1\15"+
    "\24\0\1\57\13\0\7\42\1\0\1\42\1\0\11\42"+
    "\12\0\1\77\17\0\1\100\21\0\1\101\2\0\1\102"+
    "\20\0\1\103\14\0\1\51\2\54\1\51\1\54\16\0"+
    "\5\54\15\0\2\56\4\0\14\56\12\0\1\104\17\0"+
    "\1\105\1\106\1\0\1\107\27\0\1\110\20\0\1\111"+
    "\24\0\1\112\20\0\1\113\7\0\1\73\1\114\2\75"+
    "\1\76\13\0\1\73\1\0\2\101\4\0\14\101\12\0"+
    "\1\115\17\0\1\115\2\0\1\102\22\0\1\116\30\0"+
    "\1\117\4\0\5\111\1\0\1\120\1\0\1\121\30\0"+
    "\1\122\4\0\1\113\2\123\1\124\1\125\35\0\1\126"+
    "\14\0\1\123\17\0\1\120\33\0\1\127\4\0\1\123"+
    "\21\0\5\126\1\0\1\130\1\0\1\131\12\0\1\127"+
    "\2\132\1\133\1\134\27\0\1\132\17\0\1\130\15\0"+
    "\1\132\20\0";

  private static int [] zzUnpacktrans() {
    int [] result = new int[1178];
    int offset = 0;
    offset = zzUnpacktrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpacktrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String[] ZZ_ERROR_MSG = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state {@code aState}
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\13\0\5\1\1\11\7\1\1\11\4\1\1\11\6\1"+
    "\1\11\2\1\1\11\2\1\1\11\3\1\1\11\2\1"+
    "\1\11\3\0\1\11\5\0\1\11\2\1\1\11\1\0"+
    "\3\1\3\11\1\1\4\0\3\11\4\0\1\11\2\1"+
    "\4\0\1\11\2\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[92];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private CharSequence zzBuffer = "";

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** Number of newlines encountered up to the start of the matched text. */
  @SuppressWarnings("unused")
  private int yyline;

  /** Number of characters from the last newline up to the start of the matched text. */
  @SuppressWarnings("unused")
  protected int yycolumn;

  /** Number of characters up to the start of the matched text. */
  @SuppressWarnings("unused")
  private long yychar;

  /** Whether the scanner is currently at the beginning of a line. */
  @SuppressWarnings("unused")
  private boolean zzAtBOL = true;

  /** Whether the user-EOF-code has already been executed. */
  @SuppressWarnings("unused")
  private boolean zzEOFDone;


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public MojoliciousLexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** Returns the maximum size of the scanner buffer, which limits the size of tokens. */
  private int zzMaxBufferLen() {
    return Integer.MAX_VALUE;
  }

  /**  Whether the scanner buffer can grow to accommodate a larger token. */
  private boolean zzCanGrow() {
    return true;
  }

  /**
   * Translates raw input code points to DFA table row
   */
  private static int zzCMap(int input) {
    int offset = input & 255;
    return offset == input ? ZZ_CMAP_BLOCKS[offset] : ZZ_CMAP_BLOCKS[ZZ_CMAP_TOP[input >> 8] | offset];
  }
	protected int bufferStart;

    public void setTokenStart(int position){zzCurrentPos = zzStartRead = position;}
    public void setTokenEnd(int position){zzMarkedPos = position;}
    public CharSequence getBuffer(){ return zzBuffer;}
    public int getBufferEnd() {return zzEndRead;}
    public int getNextTokenStart(){ return zzMarkedPos;}
    public boolean isLastToken(){ return zzMarkedPos == zzEndRead; }
	public int getBufferStart(){ return bufferStart;	}
 	public int getRealLexicalState() {return zzLexicalState;  }

 	public void pullback(int i)
 	{
 		int length = yylength();
 		if( i == length)
 		{
 			return;
 		}
 		assert i < length: "Pulling back for " + i + " of " + length + " for: " + yytext();
		yypushback(length - i);
 	}

  public final int getTokenStart() {
    return zzStartRead;
  }

  public final int getTokenEnd() {
    return zzMarkedPos;
  }

  public void reset(CharSequence buffer, int start, int end, int initialState) {
    zzBuffer = buffer;
    bufferStart = zzCurrentPos = zzMarkedPos = zzStartRead = start;
    zzAtEOF  = false;
    zzAtBOL = true;
    zzEndRead = end;
    yybegin(initialState);
    resetInternals();
  }

  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   *
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {
    return true;
  }


  /**
   * Returns the current lexical state.
   */
  public int yystate() {
    return packState(myPerlLexer.yystate(), isInitialState() ? zzLexicalState: 0xFFFF);
  }

  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final CharSequence yytext() {
    return zzBuffer.subSequence(zzStartRead, zzMarkedPos);
  }


  /**
   * Returns the character at position <tt>pos</tt> from the
   * matched text.
   *
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch.
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer.charAt(zzStartRead+pos);
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occurred while scanning.
   *
   * In a wellformed scanner (no or only correct usage of
   * yypushback(int) and a match-all fallback rule) this method
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }
    message += "; Real state is: " + zzLexicalState + "; yystate(): " + yystate();

    throw new Error(message);
  }


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public IElementType perlAdvance() throws java.io.IOException
  {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    CharSequence zzBufferL = zzBuffer;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;

      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {

          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMap(zzInput) ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            switch (zzLexicalState) {
            case BLOCK_COMMENT: {
              yybegin(YYINITIAL);return COMMENT_BLOCK;
            }  // fall though
            case 93: break;
            case CHECK_SPACE_CLEAR_LINE: {
              yybegin(YYINITIAL);return MOJO_TEMPLATE_BLOCK_HTML;
            }  // fall though
            case 94: break;
            case CHECK_SPACE: {
              yybegin(YYINITIAL);return MOJO_TEMPLATE_BLOCK_HTML;
            }  // fall though
            case 95: break;
            default:
        return null;
        }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1:
            { yybegin(NON_CLEAR_LINE);return MOJO_TEMPLATE_BLOCK_HTML;
            }
          // fall through
          case 29: break;
          case 2:
            { yybegin(CHECK_SPACE_CLEAR_LINE);
            }
          // fall through
          case 30: break;
          case 3:
            { yybegin(PERL_LINE);return MOJO_LINE_OPENER;
            }
          // fall through
          case 31: break;
          case 4:
            { return delegateLexing();
            }
          // fall through
          case 32: break;
          case 5:
            { yybegin(AFTER_PERL_LINE);return TokenType.WHITE_SPACE;
            }
          // fall through
          case 33: break;
          case 6:
            { yybegin(AFTER_PERL_LINE);endPerlExpression();return SEMICOLON;
            }
          // fall through
          case 34: break;
          case 7:
            { yybegin(CHECK_SPACE);
            }
          // fall through
          case 35: break;
          case 8:
            { 
            }
          // fall through
          case 36: break;
          case 9:
            { yypushback(1);yybegin(NON_CLEAR_LINE);return MOJO_TEMPLATE_BLOCK_HTML;
            }
          // fall through
          case 37: break;
          case 10:
            { pullback(0);yybegin(YYINITIAL);return TokenType.WHITE_SPACE;
            }
          // fall through
          case 38: break;
          case 11:
            { yypushback(1);yybegin(NON_CLEAR_LINE);
            }
          // fall through
          case 39: break;
          case 12:
            { yybegin(NON_CLEAR_LINE);return TokenType.WHITE_SPACE;
            }
          // fall through
          case 40: break;
          case 13:
            { yypushback(1);yybegin(YYINITIAL);
            }
          // fall through
          case 41: break;
          case 14:
            { yybegin(YYINITIAL);return TokenType.WHITE_SPACE;
            }
          // fall through
          case 42: break;
          case 15:
            { yybegin(NON_CLEAR_LINE);return COMMENT_LINE;
            }
          // fall through
          case 43: break;
          case 16:
            { yybegin(NON_CLEAR_LINE);return MOJO_LINE_OPENER_TAG;
            }
          // fall through
          case 44: break;
          case 17:
            { startPerlExpression();yybegin(PERL_EXPR_LINE);return MOJO_LINE_OPENER;
            }
          // fall through
          case 45: break;
          case 18:
            { yybegin(PERL_BLOCK);return MOJO_BLOCK_OPENER;
            }
          // fall through
          case 46: break;
          case 19:
            { yybegin(AFTER_PERL_BLOCK);return MOJO_BLOCK_CLOSER;
            }
          // fall through
          case 47: break;
          case 20:
            { yybegin(AFTER_PERL_BLOCK);endPerlExpression();return MOJO_BLOCK_EXPR_CLOSER;
            }
          // fall through
          case 48: break;
          case 21:
            // lookahead expression with fixed lookahead length
            zzMarkedPos = Character.offsetByCodePoints
                (zzBufferL, zzMarkedPos, -1);
            { return TokenType.WHITE_SPACE;
            }
          // fall through
          case 49: break;
          case 22:
            { yybegin(AFTER_PERL_BLOCK);return COMMENT_BLOCK;
            }
          // fall through
          case 50: break;
          case 23:
            { yybegin(BLOCK_COMMENT);
            }
          // fall through
          case 51: break;
          case 24:
            { yybegin(NON_CLEAR_LINE);return MOJO_BLOCK_OPENER_TAG;
            }
          // fall through
          case 52: break;
          case 25:
            { startPerlExpression();yybegin(PERL_EXPR_BLOCK);return MOJO_BLOCK_EXPR_OPENER;
            }
          // fall through
          case 53: break;
          case 26:
            // lookahead expression with fixed lookahead length
            zzMarkedPos = Character.offsetByCodePoints
                (zzBufferL, zzMarkedPos, -2);
            { return TokenType.WHITE_SPACE;
            }
          // fall through
          case 54: break;
          case 27:
            // lookahead expression with fixed base length
            zzMarkedPos = Character.offsetByCodePoints
                (zzBufferL, zzStartRead, 3);
            { return MOJO_END;
            }
          // fall through
          case 55: break;
          case 28:
            // lookahead expression with fixed base length
            zzMarkedPos = Character.offsetByCodePoints
                (zzBufferL, zzStartRead, 5);
            { return MOJO_BEGIN;
            }
          // fall through
          case 56: break;
          default:
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
