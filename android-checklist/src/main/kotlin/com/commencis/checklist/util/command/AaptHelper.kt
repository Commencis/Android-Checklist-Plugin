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

package com.commencis.checklist.util.command

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import java.io.File

/**
 * Helper class for aapt related methods
 */
class AaptHelper(private val commandExecutor: CommandExecutor) {

    /**
     * Returns general summary of the apk/aar
     * @param project project
     * @param apkPath path of the apk
     * @return summary of the apk
     */
    fun getApkSummary(project: Project, apkPath: String): String {
        return commandExecutor.executeCommand(
                project,
                getAaptPath(project),
                "d",
                "badging",
                apkPath
        )
    }

    /**
     * Returns permissions of the apk/aar
     * @param project project
     * @param apkPath path of the apk
     * @return permissions
     */
    fun getPermissions(project: Project, apkPath: String): String {
        return commandExecutor.executeCommand(
                project,
                getAaptPath(project),
                "d",
                "permissions",
                apkPath
        ).replace("\n", "<br/>")
    }

    private fun getAaptPath(project: Project): String {
        val extension = project.extensions.getByType(BaseExtension::class.java)
        return "${extension.sdkDirectory}" +
                "${File.separator}build-tools${File.separator}${extension.buildToolsVersion}${File.separator}aapt"
    }
}