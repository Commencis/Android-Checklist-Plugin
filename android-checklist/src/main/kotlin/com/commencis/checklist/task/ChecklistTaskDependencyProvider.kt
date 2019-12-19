/*
 * Copyright (C) 2019 Commencis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.commencis.checklist.task

import com.commencis.checklist.util.command.AaptHelper
import com.commencis.checklist.util.command.ApkAnalyzerHelper
import com.commencis.checklist.util.command.GitHelper
import com.commencis.checklist.util.command.GradleHelper
import com.commencis.checklist.util.file.XmlParser
import com.commencis.checklist.util.plugin.android.IBuildTypeProvider

interface ChecklistTaskDependencyProvider {
    val gradleHelper: GradleHelper

    val apkAnalyzerHelper: ApkAnalyzerHelper

    val aaptHelper: AaptHelper

    val gitHelper: GitHelper

    val xmlParser: XmlParser

    val buildTypeProvider: IBuildTypeProvider
}