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

package com.perl5.lang.perl.moduleBuild.run;

import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.openapi.module.Module;
import com.perl5.lang.perl.idea.run.prove.PerlAbstractTestRunConfigurationProducer;
import com.perl5.lang.perl.moduleBuild.PerlModuleBuildUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PerlModuleBuildTestRunConfigurationProducer
  extends PerlAbstractTestRunConfigurationProducer<PerlModuleBuildTestRunConfiguration> {
  @Override
  public @NotNull ConfigurationFactory getConfigurationFactory() {
    return PerlModuleBuildTestRunConfigurationType.getInstance().getTestConfigurationFactory();
  }

  @Override
  protected boolean isOurModule(@Nullable Module module) {
    return super.isOurModule(module) && PerlModuleBuildUtil.isModuleBuildAvailable(module);
  }

  public static @NotNull PerlModuleBuildTestRunConfigurationProducer getInstance() {
    return getInstance(PerlModuleBuildTestRunConfigurationProducer.class);
  }
}
