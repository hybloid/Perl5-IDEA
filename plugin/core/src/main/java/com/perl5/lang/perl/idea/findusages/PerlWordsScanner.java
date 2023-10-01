/*
 * Copyright 2015-2023 Alexandr Evstigneev
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

package com.perl5.lang.perl.idea.findusages;

import com.intellij.lang.cacheBuilder.DefaultWordsScanner;
import com.intellij.lang.cacheBuilder.WordOccurrence;
import com.intellij.psi.impl.source.tree.TreeUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.Processor;
import com.intellij.util.indexing.FileContent;
import com.perl5.lang.perl.PerlParserDefinition;
import com.perl5.lang.perl.lexer.PerlElementTypes;
import com.perl5.lang.perl.lexer.PerlLexingContext;
import com.perl5.lang.perl.lexer.adapters.PerlMergingLexerAdapter;
import org.jetbrains.annotations.NotNull;


public class PerlWordsScanner extends DefaultWordsScanner implements PerlElementTypes {

  private static final TokenSet COMMENTS_TOKENSET = TokenSet.orSet(PerlParserDefinition.COMMENTS, TokenSet.create(POD));
  private static final TokenSet IDENTIFIERS_TOKENSET = PerlParserDefinition.IDENTIFIERS;
  private static final TokenSet LITERALS_TOKENSET = PerlParserDefinition.LITERALS;

  public PerlWordsScanner() {
    super(createLexer(), IDENTIFIERS_TOKENSET, COMMENTS_TOKENSET, LITERALS_TOKENSET);
    setMayHaveFileRefsInLiterals(true);
  }

  private static @NotNull PerlMergingLexerAdapter createLexer() {
    return new PerlMergingLexerAdapter(PerlLexingContext.create(null).withEnforcedSublexing(true));
  }

  public void processWordsUsingPsi(@NotNull FileContent inputData, @NotNull Processor<? super WordOccurrence> processor) {
    var psiFile = inputData.getPsiFile();
    var fileNode = psiFile.getNode();
    var run = TreeUtil.findFirstLeaf(fileNode);
    if (run == null) {
      return;
    }
    var fileText = inputData.getContentAsText();
    WordOccurrence occurrence = new WordOccurrence(fileText, 0, 0, WordOccurrence.Kind.CODE); // shared occurrence
    while (run != null) {
      if (!processToken(fileText, processor, run.getElementType(), run.getStartOffset(), run.getStartOffset() + run.getTextLength(),
                        occurrence)) {
        return;
      }
      run = TreeUtil.nextLeaf(run);
    }
  }

  @Override
  public void processWords(final @NotNull CharSequence fileText, final @NotNull Processor<? super WordOccurrence> processor) {
    var lexer = createLexer();
    lexer.start(fileText);
    WordOccurrence occurrence = new WordOccurrence(fileText, 0, 0, WordOccurrence.Kind.CODE); // shared occurrence

    while (lexer.getTokenType() != null) {
      if (!processToken(fileText, processor, lexer.getTokenType(), lexer.getTokenStart(), lexer.getTokenEnd(), occurrence)) {
        return;
      }
      lexer.advance();
    }
  }

  @SuppressWarnings("BooleanMethodIsAlwaysInverted")
  protected static boolean processToken(@NotNull CharSequence fileText,
                                        @NotNull Processor<? super WordOccurrence> processor,
                                        @NotNull IElementType type,
                                        int start,
                                        int end,
                                        @NotNull WordOccurrence occurrence) {
    if (IDENTIFIERS_TOKENSET.contains(type)) {
      occurrence.init(fileText, start, end, WordOccurrence.Kind.CODE);
      return processor.process(occurrence);
    }
    else if (COMMENTS_TOKENSET.contains(type)) {
      return stripWords(processor, fileText, start, end, WordOccurrence.Kind.COMMENTS, occurrence, false);
    }
    else if (LITERALS_TOKENSET.contains(type)) {
      return stripWords(processor, fileText, start, end, WordOccurrence.Kind.LITERALS, occurrence, true);
    }
    return true;
  }
}
