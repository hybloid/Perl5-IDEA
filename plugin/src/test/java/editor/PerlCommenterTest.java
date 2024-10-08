/*
 * Copyright 2015-2019 Alexandr Evstigneev
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

package editor;


import base.PerlLightTestCase;
import org.junit.Test;
public class PerlCommenterTest extends PerlLightTestCase {
  @Override
  protected String getBaseDataPath() {
    return "commenter/perl";
  }

  @Test
  public void testAll() {doTest();}

  @Test
  public void testIndentedRange() {doTest();}

  @Test
  public void testLineDoubleIndented() {doTest();}

  @Test
  public void testLineIndented() {doTest();}

  private void doTest() {
    doLineCommenterTest();
  }
}
