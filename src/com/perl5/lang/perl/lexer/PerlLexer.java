/*
 * Copyright 2015 Alexandr Evstigneev
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/* The following code was generated by JFlex 1.4.3 on 03.05.15 13:19 */

package com.perl5.lang.perl.lexer;


import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.perl5.lang.embedded.lexer.EmbeddedPerlLexer;
import gnu.trove.THashMap;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PerlLexer extends PerlLexerGenerated
{
	public static final String STRING_UNDEF = "undef";
	public static final Pattern ASCII_IDENTIFIER_PATTERN = Pattern.compile("[_a-zA-Z][_\\w]*");
	public static final Pattern IDENTIFIER_PATTERN = Pattern.compile("[_\\p{L}][_\\p{L}\\d]*");
	public static final Pattern ASCII_BARE_STRING_PATTERN = Pattern.compile("[-+]*[_a-zA-Z][_\\w]*");
	public static final Pattern POSIX_CHAR_CLASS_PATTERN = Pattern.compile("\\[\\[:\\^?\\w*:\\]\\]");
	public static final Map<IElementType, String> ALLOWED_REGEXP_MODIFIERS = new THashMap<>();
	// pattern for getting marker
	public static final Pattern HEREDOC_OPENER_PATTERN = Pattern.compile("<<(.+?)");
	public static final Pattern HEREDOC_OPENER_PATTERN_DQ = Pattern.compile("<<(\\s*)(\")(.*?)\"");
	public static final Pattern HEREDOC_OPENER_PATTERN_SQ = Pattern.compile("<<(\\s*)(\')(.*?)\'");
	public static final Pattern HEREDOC_OPENER_PATTERN_XQ = Pattern.compile("<<(\\s*)(`)(.*?)`");
	public static final String TR_MODIFIERS = "cdsr";
	public static final Map<String, IElementType> RESERVED_TOKEN_TYPES = new THashMap<>();
	public static final Map<String, IElementType> CUSTOM_TOKEN_TYPES = new THashMap<>();
	private static final List<IElementType> DQ_TOKENS = Arrays.asList(QUOTE_DOUBLE_OPEN, LP_STRING_QQ, QUOTE_DOUBLE_CLOSE);
	private static final List<IElementType> SQ_TOKENS = Arrays.asList(QUOTE_SINGLE_OPEN, STRING_CONTENT, QUOTE_SINGLE_CLOSE);
	private static final List<IElementType> XQ_TOKENS = Arrays.asList(QUOTE_TICK_OPEN, LP_STRING_XQ, QUOTE_TICK_CLOSE);
	private static final List<IElementType> QW_TOKENS = Arrays.asList(QUOTE_SINGLE_OPEN, LP_STRING_QW, QUOTE_SINGLE_CLOSE);
	// tokens that preceeds regexp opener or file <FH>
	public static TokenSet BARE_REGEX_PREFIX_TOKENSET = TokenSet.EMPTY;
	public static TokenSet RESERVED_TOKENSET;
	public static TokenSet CUSTOM_TOKENSET;

	static
	{
		ALLOWED_REGEXP_MODIFIERS.put(RESERVED_S, "nmsixpodualgcer");
		ALLOWED_REGEXP_MODIFIERS.put(RESERVED_M, "nmsixpodualgc");
		ALLOWED_REGEXP_MODIFIERS.put(RESERVED_QR, "nmsixpodual");
	}

	/**
	 * Quote-like, transliteration and regexps common part
	 */
	public boolean allowSharpQuote = true;
	public boolean isEscaped = false;
	public int sectionsNumber = 0;    // number of sections one or two
	/**
	 * Regex processor qr{} m{} s{}{}
	 **/
	protected IElementType regexCommand = null;
	private boolean myFormatWaiting = false;

	public PerlLexer(@Nullable Project project)
	{
		super(null);
	}

	public static void initReservedTokensMap()
	{
		RESERVED_TOKEN_TYPES.clear();
		// reserved
	}

	public static void initReservedTokensSet()
	{
		RESERVED_TOKENSET = TokenSet.create(RESERVED_TOKEN_TYPES.values().toArray(new IElementType[RESERVED_TOKEN_TYPES.values().size()]));
		CUSTOM_TOKENSET = TokenSet.create(CUSTOM_TOKEN_TYPES.values().toArray(new IElementType[CUSTOM_TOKEN_TYPES.values().size()]));
	}


	/**
	 * Choosing closing character by opening one
	 *
	 * @param charOpener - char with which sequence started
	 * @return - ending char
	 */
	public static char getQuoteCloseChar(char charOpener)
	{
		if (charOpener == '<')
		{
			return '>';
		}
		else if (charOpener == '{')
		{
			return '}';
		}
		else if (charOpener == '(')
		{
			return ')';
		}
		else if (charOpener == '[')
		{
			return ']';
		}
		else
		{
			return charOpener;
		}
	}


	@Override
	public int yystate()
	{
		return preparsedTokensList.isEmpty() &&
				!myFormatWaiting &&
				heredocQueue.isEmpty() &&
				myBracesStack.isEmpty() &&
				myBracketsStack.isEmpty() &&
				myParensStack.isEmpty()
				? super.yystate() : PREPARSED_ITEMS;
	}

	/**
	 * Lexers perlAdvance method. Parses some thing here, or just invoking generated flex parser
	 *
	 * @return next token type
	 */
	public IElementType perlAdvance() throws IOException
	{

		CharSequence buffer = getBuffer();
		int tokenStart = getTokenEnd();
		int bufferEnd = getBufferEnd();

		if (bufferEnd == 0 || tokenStart >= bufferEnd)
		{
			return super.perlAdvance();
		}
		else
		{
			int currentState = getRealLexicalState();
			char currentChar = buffer.charAt(tokenStart);

			// capture heredoc
			if (waitingHereDoc() && (tokenStart == 0 || currentChar == '\n'))
			{
				return captureHereDoc(false);
			}
			// capture format
			else if (myFormatWaiting && (tokenStart == 0 || buffer.charAt(tokenStart - 1) == '\n'))
			{
				IElementType tokenType = captureFormat();
				myFormatWaiting = false;
				if (tokenType != null)    // got something
				{
					return tokenType;
				}
			}
			else if (isOpeningQuoteFor(currentState, currentChar, QUOTE_LIKE_OPENER_Q, QUOTE_LIKE_OPENER_QQ, QUOTE_LIKE_OPENER_QX, QUOTE_LIKE_OPENER_QW))
			{
				return captureString();
			}
			else if (isOpeningQuoteFor(currentState, currentChar, REGEX_OPENER))
			{
				return parseRegex(tokenStart);
			}
			else if (isOpeningQuoteFor(currentState, currentChar, TRANS_OPENER))
			{
				return parseTr();
			}
			// capture line comment
			else if (currentChar == '#' &&
					currentState != REPLACEMENT_REGEX &&
					currentState != MATCH_REGEX &&
					currentState != STRING_QQ &&
					currentState != STRING_QX &&
					currentState != STRING_LIST &&
					currentState != ANNOTATION &&
					currentState != ANNOTATION_KEY
					)
			{
				// comment may end on newline or ?>
				int currentPosition = tokenStart;
				setTokenStart(tokenStart);

				while (currentPosition < bufferEnd && !isLineCommentEnd(currentPosition))
				{
					currentPosition++;
				}

				setTokenEnd(currentPosition);
				// catching annotations #@
				if (tokenStart + 1 < bufferEnd && buffer.charAt(tokenStart + 1) == '@')
				{
					return COMMENT_ANNOTATION;
				}

				return COMMENT_LINE;
			}
		}
		return super.perlAdvance();
	}

	public boolean isOpeningQuoteFor(int currentState, char currentChar, int... states)
	{
		for (int state : states)
		{
			if (state == currentState)
			{
				return !Character.isWhitespace(currentChar)
						&& (currentChar != '#' || allowSharpQuote);
			}
		}

		return false;
	}

	private List<IElementType> getStringTokens()
	{
		int currentState = getRealLexicalState();

		if (currentState == QUOTE_LIKE_OPENER_Q)
		{
			return SQ_TOKENS;
		}
		if (currentState == QUOTE_LIKE_OPENER_QQ)
		{
			return DQ_TOKENS;
		}
		if (currentState == QUOTE_LIKE_OPENER_QX)
		{
			return XQ_TOKENS;
		}
		if (currentState == QUOTE_LIKE_OPENER_QW)
		{
			return QW_TOKENS;
		}

		throw new RuntimeException("Unknown lexical state for string token " + currentState);
	}

	/**
	 * Captures string token from current position according to the current lexical state
	 *
	 * @return string token
	 */
	public IElementType captureString()
	{
		CharSequence buffer = getBuffer();
		int currentPosition = getTokenEnd();
		int bufferEnd = getBufferEnd();

		char openQuote = buffer.charAt(currentPosition);
		char closeQuote = getQuoteCloseChar(openQuote);
		boolean quotesDiffer = openQuote != closeQuote;

		boolean isEscaped = false;
		int quotesDepth = 0;    // for using with different quotes

		List<IElementType> stringTokens = getStringTokens();
		pushPreparsedToken(currentPosition++, currentPosition, stringTokens.get(0));

		int contentStart = currentPosition;

		while (currentPosition < bufferEnd)
		{
			char currentChar = buffer.charAt(currentPosition);

			if (!isEscaped && quotesDepth == 0 && currentChar == closeQuote)
			{
				break;
			}

			//noinspection Duplicates
			if (!isEscaped && quotesDiffer)
			{
				if (currentChar == openQuote)
				{
					quotesDepth++;
				}
				else if (currentChar == closeQuote)
				{
					quotesDepth--;
				}
			}

			isEscaped = !isEscaped && currentChar == '\\';

			currentPosition++;
		}

		if (currentPosition > contentStart)
		{
			pushPreparsedToken(contentStart, currentPosition, stringTokens.get(1));
		}

		if (currentPosition < bufferEnd)    // got close quote
		{
			pushPreparsedToken(currentPosition++, currentPosition, stringTokens.get(2));
		}
		popState();
		return getPreParsedToken();
	}

	/**
	 * Checking if comment is ended. Implemented for overriding in {@link EmbeddedPerlLexer#isLineCommentEnd(int)} }
	 *
	 * @param currentPosition current position to check
	 * @return checking result
	 */
	public boolean isLineCommentEnd(int currentPosition)
	{
		return getBuffer().charAt(currentPosition) == '\n';
	}

	public boolean captureInterpolatedCode()
	{
		int seekStart = getTokenEnd();
		int seekEnd = getBufferEnd();
		int currentPos = seekStart;
		CharSequence buffer = getBuffer();

		int braceLevel = 0;
		boolean isEscaped = false;

		while (currentPos < seekEnd)
		{
			char currentChar = buffer.charAt(currentPos);

			if (!isEscaped)
			{
				if (currentChar == '{')
				{
					braceLevel++;
				}
				else if (currentChar == '}')
				{
					braceLevel--;
					if (braceLevel == 0)
					{
						pushPreparsedToken(seekStart, currentPos + 1, LP_CODE_BLOCK);
						return true;
					}
				}
			}

			isEscaped = !isEscaped && currentChar == '\\';
			currentPos++;
		}
		return false;
	}


	/**
	 * Captures HereDoc document and returns appropriate token type
	 *
	 * @param afterEmptyCloser - this here-doc being captured after empty closer, e.g. sequentional <<"", <<""
	 * @return Heredoc token type
	 */
	public IElementType captureHereDoc(boolean afterEmptyCloser)
	{
		final PerlHeredocQueueElement heredocQueueElement = heredocQueue.remove(0);
		final CharSequence heredocMarker = heredocQueueElement.getMarker();

		IElementType tokenType = heredocQueueElement.getTargetElement();

		CharSequence buffer = getBuffer();
		int tokenStart = getTokenEnd();

		if (!afterEmptyCloser)
		{
			pushPreparsedToken(tokenStart++, tokenStart, TokenType.NEW_LINE_INDENT);
		}

		int bufferEnd = getBufferEnd();

		int currentPosition = tokenStart;
		int linePos = currentPosition;


		while (true)
		{
			while (linePos < bufferEnd && buffer.charAt(linePos) != '\n' && buffer.charAt(linePos) != '\r')
			{
				linePos++;
			}
			int lineContentsEnd = linePos;

			if (linePos < bufferEnd && buffer.charAt(linePos) == '\r')
			{
				linePos++;
			}
			if (linePos < bufferEnd && buffer.charAt(linePos) == '\n')
			{
				linePos++;
			}

			// reached the end of heredoc and got end marker

			if (heredocMarker.length() == 0 && lineContentsEnd == currentPosition && linePos > lineContentsEnd)
			{
				// non-empty heredoc and got the end
				if (currentPosition > tokenStart)
				{
					pushPreparsedToken(tokenStart, currentPosition, tokenType);
				}
				pushPreparsedToken(currentPosition, lineContentsEnd + 1, HEREDOC_END);

				if (!heredocQueue.isEmpty() && bufferEnd > lineContentsEnd + 1)
				{
					setTokenEnd(lineContentsEnd + 1);
					return captureHereDoc(true);
				}
				else
				{
					return getPreParsedToken();
				}
			}
			else if (StringUtil.equals(heredocMarker, buffer.subSequence(currentPosition, lineContentsEnd)))
			{
				// non-empty heredoc and got the end
				if (currentPosition > tokenStart)
				{
					pushPreparsedToken(tokenStart, currentPosition, tokenType);
				}
				pushPreparsedToken(currentPosition, lineContentsEnd, HEREDOC_END);
				return getPreParsedToken();
			}
			// reached the end of file
			else if (linePos == bufferEnd)
			{
				// non-empty heredoc and got the end of file
				if (linePos > tokenStart)
				{
					pushPreparsedToken(tokenStart, linePos, tokenType);
				}
				return getPreParsedToken();
			}
			currentPosition = linePos;
		}
	}

	/**
	 * Captures format; fixme refactor with captureHeredoc got common parts
	 *
	 * @return Heredoc token type
	 */
	public IElementType captureFormat()
	{
		CharSequence buffer = getBuffer();
		int tokenStart = getTokenEnd();
		setTokenStart(tokenStart);
		int bufferEnd = getBufferEnd();

		int currentPosition = tokenStart;
		int linePos = currentPosition;

		while (true)
		{
			while (linePos < bufferEnd && buffer.charAt(linePos) != '\n' && buffer.charAt(linePos) != '\r')
			{
				linePos++;
			}
			int lineContentsEnd = linePos;

			if (linePos < bufferEnd && buffer.charAt(linePos) == '\r')
			{
				linePos++;
			}
			if (linePos < bufferEnd && buffer.charAt(linePos) == '\n')
			{
				linePos++;
			}

			// reached the end of format and got end marker
			if (lineContentsEnd == currentPosition + 1 && buffer.charAt(currentPosition) == '.')
			{
				preparsedTokensList.clear();
				preparsedTokensList.add(new CustomToken(currentPosition, lineContentsEnd, FORMAT_TERMINATOR));

				// non-empty heredoc and got the end
				if (currentPosition > tokenStart)
				{
					setTokenStart(tokenStart);
					setTokenEnd(currentPosition);
					return FORMAT;
				}
				// empty format and got the end
				else
				{
					return getPreParsedToken();
				}
			}
			// reached the end of file
			else if (linePos == bufferEnd)
			{
				// non-empty format and got the end of file
				if (currentPosition > tokenStart)
				{
					setTokenStart(tokenStart);
					setTokenEnd(currentPosition);
					return FORMAT;
				}
				// empty heredoc and got the end of file
				else
				{
					return null;
				}
			}
			currentPosition = linePos;
		}
	}

	public void reset(CharSequence buf, int start, int end, int initialState)
	{
		super.reset(buf, start, end, initialState);
//		System.err.println(String.format("Lexer re-set to %d - %d, %d of %d", start, end, end - start, buf.length()));
	}

	// guess if this is a OPERATOR_DIV or regex opener
	public IElementType startRegexp()
	{
		allowSharpQuote = true;
		isEscaped = false;
		regexCommand = RESERVED_M;
		sectionsNumber = 1;
		pushState();
		return parseRegex(getTokenStart());
	}

	/**
	 * Sets up regex parser
	 */
	public void processRegexOpener(IElementType tokenType)
	{
		allowSharpQuote = true;
		isEscaped = false;
		regexCommand = tokenType;

		if (regexCommand == RESERVED_S)    // two sections s
		{
			sectionsNumber = 2;
		}
		else                        // one section qr m
		{
			sectionsNumber = 1;
		}

		pushState();
		yybegin(REGEX_OPENER);
	}

	/**
	 * Parsing tr/y content
	 *
	 * @return first token
	 */
	public IElementType parseTr()
	{
		popState();
		yybegin(AFTER_VALUE);
		CharSequence buffer = getBuffer();
		int currentOffset = getTokenEnd();
		int bufferEnd = getBufferEnd();

		// search block
		char openQuote = buffer.charAt(currentOffset);
		char closeQuote = getQuoteCloseChar(openQuote);
		boolean quotesDiffer = openQuote != closeQuote;
		pushPreparsedToken(currentOffset++, currentOffset, REGEX_QUOTE_OPEN);

		currentOffset = parseTrBlockContent(currentOffset, openQuote, closeQuote);

		// close quote
		if (currentOffset < bufferEnd)
		{
			pushPreparsedToken(currentOffset++, currentOffset, quotesDiffer ? REGEX_QUOTE_CLOSE : REGEX_QUOTE);
		}

		// between blocks
		if (quotesDiffer)
		{
			currentOffset = lexWhiteSpacesAndComments(currentOffset);
		}

		// second block
		if (currentOffset < bufferEnd)
		{
			if (quotesDiffer)
			{
				openQuote = buffer.charAt(currentOffset);
				closeQuote = getQuoteCloseChar(openQuote);
				pushPreparsedToken(currentOffset++, currentOffset, REGEX_QUOTE_OPEN);
			}

			currentOffset = parseTrBlockContent(currentOffset, openQuote, closeQuote);
		}

		// close quote
		if (currentOffset < bufferEnd)
		{
			pushPreparsedToken(currentOffset++, currentOffset, REGEX_QUOTE_CLOSE);
		}


		// trans modifiers
		if (currentOffset < bufferEnd)
		{
			int blockStart = currentOffset;
			while (currentOffset < bufferEnd && StringUtil.containsChar(TR_MODIFIERS, buffer.charAt(currentOffset)))
			{
				currentOffset++;
			}

			if (blockStart < currentOffset)
			{
				pushPreparsedToken(blockStart, currentOffset, REGEX_MODIFIER);
			}
		}

		return getPreParsedToken();
	}

	/**
	 * Parsing tr block content till close quote
	 *
	 * @param currentOffset start offset
	 * @param closeQuote    close quote character
	 * @return next offset
	 */
	int parseTrBlockContent(int currentOffset, char openQuote, char closeQuote)
	{
		int blockStartOffset = currentOffset;
		CharSequence buffer = getBuffer();
		int bufferEnd = getBufferEnd();
		boolean isEscaped = false;
		boolean isQuoteDiffers = openQuote != closeQuote;
		int quotesLevel = 0;

		while (currentOffset < bufferEnd)
		{
			char currentChar = buffer.charAt(currentOffset);

			if (!isEscaped && quotesLevel == 0 && currentChar == closeQuote)
			{
				if (currentOffset > blockStartOffset)
				{
					pushPreparsedToken(blockStartOffset, currentOffset, STRING_CONTENT);
				}
				break;
			}
			//noinspection Duplicates
			if (isQuoteDiffers && !isEscaped)
			{
				if (currentChar == openQuote)
				{
					quotesLevel++;
				}
				else if (currentChar == closeQuote)
				{
					quotesLevel--;
				}
			}

			isEscaped = (currentChar == '\\' && !isEscaped);
			currentOffset++;
		}

		return currentOffset;
	}

	/**
	 * Lexing empty spaces and comments between regex/tr blocks and adding tokens to the target list
	 *
	 * @param currentOffset start offset
	 * @return new offset
	 */
	protected int lexWhiteSpacesAndComments(int currentOffset)
	{
		CharSequence buffer = getBuffer();
		int bufferEnd = getBufferEnd();
		while (currentOffset < bufferEnd)
		{
			char currentChar = buffer.charAt(currentOffset);

			if (currentChar == '\n')
			{
				// fixme check heredocs ?
				pushPreparsedToken(currentOffset++, currentOffset, TokenType.NEW_LINE_INDENT);
			}
			else if (Character.isWhitespace(currentChar))    // white spaces
			{
				int whiteSpaceStart = currentOffset;
				while (currentOffset < bufferEnd && Character.isWhitespace(currentChar = buffer.charAt(currentOffset)) && currentChar != '\n')
				{
					currentOffset++;
				}
				pushPreparsedToken(whiteSpaceStart, currentOffset, TokenType.WHITE_SPACE);
			}
			else if (currentChar == '#')    // line comment
			{
				int commentStart = currentOffset;
				while (currentOffset < bufferEnd && buffer.charAt(currentOffset) != '\n')
				{
					currentOffset++;
				}
				pushPreparsedToken(getCustomToken(commentStart, currentOffset, COMMENT_LINE));
			}
			else
			{
				break;
			}
		}

		return currentOffset;
	}

	public int getRegexBlockEndOffset(int startOffset, char openingChar, boolean isSecondBlock)
	{
		char closingChar = getQuoteCloseChar(openingChar);
		CharSequence buffer = getBuffer();
		int bufferEnd = getBufferEnd();

		boolean isEscaped = false;
		boolean isCharGroup = false;
		boolean isQuotesDiffers = closingChar != openingChar;

		int braceLevel = 0;
		int parenLevel = 0;
		int delimiterLevel = 0;

		int currentOffset = startOffset;

		while (true)
		{
			if (currentOffset >= bufferEnd)
			{
				break;
			}

			char currentChar = buffer.charAt(currentOffset);

			if (delimiterLevel == 0 && braceLevel == 0 && !isCharGroup && !isEscaped && parenLevel == 0 && closingChar == currentChar)
			{
				return currentOffset;
			}

			if (!isSecondBlock)
			{
				if (!isEscaped && !isCharGroup && currentChar == '[')
				{
					Matcher m = POSIX_CHAR_CLASS_PATTERN.matcher(buffer.subSequence(currentOffset, bufferEnd));
					if (m.lookingAt())
					{
						currentOffset += m.toMatchResult().group(0).length();
						continue;
					}
					else
					{
						isCharGroup = true;
					}
				}
				else if (!isEscaped && isCharGroup && currentChar == ']')
				{
					isCharGroup = false;
				}
			}

			if (!isEscaped && isQuotesDiffers && !isCharGroup)
			{
				if (currentChar == openingChar)
				{
					delimiterLevel++;
				}
				else if (currentChar == closingChar && delimiterLevel > 0)
				{
					delimiterLevel--;
				}
			}

			isEscaped = !isEscaped && closingChar != '\\' && currentChar == '\\';

			currentOffset++;
		}
		return currentOffset;
	}


	/**
	 * Parses regexp from the current position (opening delimiter) and preserves tokens in preparsedTokensList
	 * REGEX_MODIFIERS = [msixpodualgcer]
	 *
	 * @return opening delimiter type
	 */
	public IElementType parseRegex(int currentOffset)
	{
		popState();
		yybegin(AFTER_VALUE);
		CharSequence buffer = getBuffer();
		int bufferEnd = getBufferEnd();

		char firstBlockOpeningQuote = buffer.charAt(currentOffset);
		pushPreparsedToken(currentOffset++, currentOffset, REGEX_QUOTE_OPEN);

		// find block 1
		int firstBlockEndOffset = getRegexBlockEndOffset(currentOffset, firstBlockOpeningQuote, false);
		CustomToken firstBlockToken = null;
		if (firstBlockEndOffset > currentOffset)
		{
			firstBlockToken = new CustomToken(currentOffset, firstBlockEndOffset, LP_REGEX);
			pushPreparsedToken(firstBlockToken);
		}

		currentOffset = firstBlockEndOffset;

		// find block 2
		CustomToken secondBlockOpeningToken = null;
		CustomToken secondBlockToken = null;

		if (currentOffset < bufferEnd)
		{
			if (sectionsNumber == 1)
			{
				pushPreparsedToken(currentOffset++, currentOffset, REGEX_QUOTE_CLOSE);
			}
			else // should have second part
			{
				char secondBlockOpeningQuote = firstBlockOpeningQuote;
				if (firstBlockOpeningQuote == getQuoteCloseChar(firstBlockOpeningQuote))
				{
					secondBlockOpeningToken = new CustomToken(currentOffset++, currentOffset, REGEX_QUOTE);
					pushPreparsedToken(secondBlockOpeningToken);
				}
				else
				{
					pushPreparsedToken(currentOffset++, currentOffset, REGEX_QUOTE_CLOSE);
					currentOffset = lexWhiteSpacesAndComments(currentOffset);

					if (currentOffset < bufferEnd)
					{
						secondBlockOpeningQuote = buffer.charAt(currentOffset);
						secondBlockOpeningToken = new CustomToken(currentOffset++, currentOffset, REGEX_QUOTE_OPEN);
						pushPreparsedToken(secondBlockOpeningToken);
					}
				}

				if (currentOffset < bufferEnd)
				{
					int secondBlockEndOffset = getRegexBlockEndOffset(currentOffset, secondBlockOpeningQuote, true);

					if (secondBlockEndOffset > currentOffset)
					{
						secondBlockToken = new CustomToken(currentOffset, secondBlockEndOffset, LP_REGEX_REPLACEMENT);
						pushPreparsedToken(secondBlockToken);
						currentOffset = secondBlockEndOffset;
					}
				}

				if (currentOffset < bufferEnd)
				{
					pushPreparsedToken(currentOffset++, currentOffset, REGEX_QUOTE_CLOSE);
				}
			}
		}

		// check modifiers for x
		assert regexCommand != null;
		String allowedModifiers = ALLOWED_REGEXP_MODIFIERS.get(regexCommand);

		while (currentOffset < bufferEnd)
		{
			char currentChar = buffer.charAt(currentOffset);
			if (!StringUtil.containsChar(allowedModifiers, currentChar))    // unknown modifier
			{
				break;
			}
			else if (currentChar == 'x')    // mark as extended
			{
				if (firstBlockToken != null)
				{
					firstBlockToken.setTokenType(LP_REGEX_X);
				}
			}
			else if (currentChar == 'e')    // mark as evaluated
			{
				if (secondBlockOpeningToken != null)
				{
					IElementType secondBlockOpeningTokenType = secondBlockOpeningToken.getTokenType();
					if (secondBlockOpeningTokenType == REGEX_QUOTE_OPEN || secondBlockOpeningTokenType == REGEX_QUOTE_OPEN_E)
					{
						secondBlockOpeningToken.setTokenType(REGEX_QUOTE_OPEN_E);
					}
					else if (secondBlockOpeningTokenType == REGEX_QUOTE || secondBlockOpeningTokenType == REGEX_QUOTE_E)
					{
						secondBlockOpeningToken.setTokenType(REGEX_QUOTE_E);
					}
					else
					{
						throw new RuntimeException("Bug, got: " + secondBlockOpeningTokenType);
					}
				}
				if (secondBlockToken != null)
				{
					secondBlockToken.setTokenType(LP_CODE_BLOCK);
				}
			}

			pushPreparsedToken(currentOffset++, currentOffset, REGEX_MODIFIER);
		}

		return getPreParsedToken();
	}

	/**
	 * Transliteration processors tr y
	 **/
	public void processTransOpener()
	{
		allowSharpQuote = true;
		pushState();
		yybegin(TRANS_OPENER);
	}

	/**
	 * Quote-like string procesors
	 **/
	public void processQuoteLikeStringOpener(IElementType tokenType)
	{
		allowSharpQuote = true;
		isEscaped = false;
		pushState();
		if (tokenType == RESERVED_Q)
		{
			yybegin(QUOTE_LIKE_OPENER_Q);
		}
		else if (tokenType == RESERVED_QQ)
		{
			yybegin(QUOTE_LIKE_OPENER_QQ);
		}
		else if (tokenType == RESERVED_QX)
		{
			yybegin(QUOTE_LIKE_OPENER_QX);
		}
		else if (tokenType == RESERVED_QW)
		{
			yybegin(QUOTE_LIKE_OPENER_QW);
		}
		else
		{
			throw new RuntimeException("Unable to switch state by token " + tokenType);
		}
	}

	public boolean waitingHereDoc()
	{
		return !heredocQueue.isEmpty();
	}

	/**
	 * Bareword parser, resolves built-ins and runs additional processings where it's necessary
	 *
	 * @return token type
	 */
	public IElementType getIdentifierToken()
	{
		String tokenText = yytext().toString();
		IElementType tokenType;

		if ((tokenType = RESERVED_TOKEN_TYPES.get(tokenText)) == null &&
				(tokenType = CUSTOM_TOKEN_TYPES.get(tokenText)) == null
				)
		{
			tokenType = IDENTIFIER;
		}

		yybegin(BARE_REGEX_PREFIX_TOKENSET.contains(tokenType) ? YYINITIAL : AFTER_IDENTIFIER);

		return tokenType;
	}

	@Override
	public void resetInternals()
	{
		super.resetInternals();
		heredocQueue.clear();
	}

	@Override
	public IElementType advance() throws IOException
	{
		boolean wasPreparsed = !preparsedTokensList.isEmpty();

		IElementType tokenType = super.advance();

		if (!wasPreparsed && preparsedTokensList.isEmpty())
		{

			if (tokenType == TokenType.NEW_LINE_INDENT || tokenType == TokenType.WHITE_SPACE)
			{
				allowSharpQuote = false;
			}
			else if (tokenType == RESERVED_QW || tokenType == RESERVED_Q || tokenType == RESERVED_QQ || tokenType == RESERVED_QX)
			{
				processQuoteLikeStringOpener(tokenType);
			}
			else if (tokenType == RESERVED_S || tokenType == RESERVED_M || tokenType == RESERVED_QR)
			{
				processRegexOpener(tokenType);
			}
			else if (tokenType == RESERVED_TR || tokenType == RESERVED_Y)
			{
				processTransOpener();
			}
			else if (tokenType == RESERVED_FORMAT)
			{
				myFormatWaiting = true;
			}
		}

		return tokenType;
	}

}
