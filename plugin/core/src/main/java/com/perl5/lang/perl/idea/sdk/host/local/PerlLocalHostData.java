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

package com.perl5.lang.perl.idea.sdk.host.local;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.PathEnvironmentVariableUtil;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.util.containers.ContainerUtil;
import com.perl5.lang.perl.idea.execution.PerlCommandLine;
import com.perl5.lang.perl.idea.sdk.host.PerlSimpleHostData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;

class PerlLocalHostData extends PerlSimpleHostData<PerlLocalHostData, PerlLocalHostHandler> {

  public PerlLocalHostData(@NotNull PerlLocalHostHandler handler) {
    super(handler);
  }

  @Override
  protected @NotNull Process createProcess(@NotNull PerlCommandLine commandLine) throws ExecutionException {
    return commandLine.createProcess();
  }

  @Override
  public @Nullable File findFileByName(@NotNull String fileName) {
    return ContainerUtil.getFirstItem(PathEnvironmentVariableUtil.findAllExeFilesInPath(fileName));
  }

  @Override
  public @NotNull String doGetLocalPath(@NotNull String remotePath) {
    return remotePath;
  }

  @Override
  public @NotNull String doGetRemotePath(@NotNull String localPath) {
    return localPath;
  }

  @Override
  protected @NotNull PerlLocalHostData self() {
    return this;
  }

  @Override
  public @NotNull String expandUserHome(@NotNull String remotePath) {
    return FileUtil.expandUserHome(remotePath);
  }
}
