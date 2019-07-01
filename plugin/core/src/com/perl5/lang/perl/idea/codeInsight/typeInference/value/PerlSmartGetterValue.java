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

package com.perl5.lang.perl.idea.codeInsight.typeInference.value;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import static com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlValues.UNKNOWN_VALUE;
import static com.perl5.lang.perl.idea.codeInsight.typeInference.value.PerlValuesManager.SMART_GETTER_ID;

/**
 * Value that handles smart getters: returning value without argument and {@code $_[0]} with one
 */
public class PerlSmartGetterValue extends PerlOperationValue {

  private PerlSmartGetterValue(@NotNull PerlValue realValue) {
    super(realValue);
  }

  PerlSmartGetterValue(@NotNull PerlValueDeserializer deserializer) throws IOException {
    super(deserializer);
  }

  @NotNull
  @Override
  protected PerlValue computeResolve(@NotNull PerlValue resolvedRealValue, @NotNull PerlValueResolver resolver) {
    LOG.assertTrue(resolver instanceof PerlSubValueResolver);
    PerlValue resolvedArguments = resolver.resolve(PerlValues.ARGUMENTS_VALUE);
    if(!(resolvedArguments instanceof PerlArrayValue)){
      return UNKNOWN_VALUE;
    }
    List<PerlValue> argumentElements = ((PerlArrayValue)resolvedArguments).getElements();
    if( argumentElements.isEmpty()){
      return UNKNOWN_VALUE;
    }
    return argumentElements.size() == 1 ? resolvedRealValue: argumentElements.get(0);
  }

  @Override
  protected int getSerializationId() {
    return SMART_GETTER_ID;
  }

  @Override
  public String toString() {
    return "Smart getter: " + getBaseValue();
  }

  public static PerlValue create(@NotNull PerlValue realValue){
    return PerlValuesManager.intern(new PerlSmartGetterValue(realValue));
  }
}
