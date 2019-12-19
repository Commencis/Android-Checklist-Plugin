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
import com.commencis.checklist.util.file.XmlParser
import org.gradle.api.Project
import java.io.File

/**
 * Helper class for apkanalyzer related methods
 */
class ApkAnalyzerHelper(private val commandExecutor: CommandExecutor) {


    /**
     * Returns minSDK version from the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return minSdk version
     */
    fun getMinSdkVersion(project: Project, apkPath: String): Int {
        val output = commandExecutor.executeCommand(
                project,
                getApkAnalyzerPath(project),
                "manifest",
                "min-sdk",
                apkPath
        )
        return output.trim().toInt()
    }

    /**
     * Returns targetSDK version from the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return targetSdk version
     */
    fun getTargetSdkVersion(project: Project, apkPath: String): Int {
        val output = commandExecutor.executeCommand(
                project,
                getApkAnalyzerPath(project),
                "manifest",
                "target-sdk",
                apkPath
        )
        return output.trim().toInt()
    }

    /**
     * Returns application id from the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return appId
     */
    fun getApplicationId(project: Project, apkPath: String): String {
        val output = commandExecutor.executeCommand(
                project,
                getApkAnalyzerPath(project),
                "manifest",
                "application-id",
                apkPath
        )
        return output.trim()
    }

    /**
     * Returns if the given apk is debuggable
     *
     * @param project project
     * @param apkPath path to apk
     * @return true if apk is debuggable, false otherwise
     */
    fun isApkDebuggable(project: Project, apkPath: String): Boolean {
        val output = commandExecutor.executeCommand(
                project,
                getApkAnalyzerPath(project),
                "manifest",
                "debuggable",
                apkPath
        )
        return output.trim().toBoolean()
    }

    /**
     * Returns compileSDK version from the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return compileSdk version
     */
    fun getCompileSdkVersion(project: Project, apkPath: String, xmlParser: XmlParser): Int {
        val output = getManifest(project, apkPath)
        return xmlParser.findAttributeWithinNode<String>(
                output,
                "android:compileSdkVersion",
                "manifest"
        )?.trim()?.toInt() ?: -1
    }


    /**
     * Returns Android Manifest of the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return Manifest
     */
    fun getManifest(project: Project, apkPath: String): String {
        return commandExecutor.executeCommand(
                project,
                getApkAnalyzerPath(project),
                "manifest",
                "print",
                apkPath
        )
    }

    /**
     * Returns version name from the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return version name
     */
    fun getVersionName(project: Project, apkPath: String, xmlParser: XmlParser): String {
        val output = getManifest(project, apkPath)
        return xmlParser.findAttributeWithinNode<String>(
                output,
                "android:versionName",
                "manifest"
        )?.trim() ?: ""
    }

    /**
     * Returns version code from the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return version code
     */
    fun getVersionCode(project: Project, apkPath: String, xmlParser: XmlParser): Int {
        val output = getManifest(project, apkPath)
        return xmlParser.findAttributeWithinNode<String>(
                output,
                "android:versionCode",
                "manifest"
        )?.trim()?.toInt() ?: -1
    }

    /**
     * Returns install location value from the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return install location value
     */
    fun getInstallLocation(project: Project, apkPath: String, xmlParser: XmlParser): Int {
        val output = getManifest(project, apkPath)
        return xmlParser.findAttributeWithinNode<String>(
                output,
                "android:installLocation",
                "application"
        )?.trim()?.toInt() ?: -1
    }

    /**
     * Returns classes inside the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return classes inside the apk
     */
    fun getClasses(project: Project, apkPath: String): String {
        return commandExecutor.executeCommand(
                project,
                getApkAnalyzerPath(project),
                "dex",
                "packages",
                apkPath
        )
    }

    /**
     * Returns files inside the given apk
     *
     * @param project project
     * @param apkPath path to apk
     * @return files inside the apk
     */
    fun getFiles(project: Project, apkPath: String): String {
        return commandExecutor.executeCommand(
                project,
                getApkAnalyzerPath(project),
                "files",
                "list",
                apkPath
        )
    }

    /**
     * Returns output size
     *
     * @param project project
     * @param apkPath path to apk
     * @return output size
     */
    fun getOutputSize(project: Project, apkPath: String): Int {
        return commandExecutor.executeCommand(
                project,
                getApkAnalyzerPath(project),
                "apk",
                "file-size",
                apkPath
        ).trim().toInt()
    }

    private fun getApkAnalyzerPath(project: Project) =
            "${project.extensions.getByType(BaseExtension::class.java).sdkDirectory}" +
                    "${File.separator}tools${File.separator}bin${File.separator}apkanalyzer"

}
