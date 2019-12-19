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

package com.commencis.checklist

import com.commencis.checklist.task.constants.InstallLocationValue

/**
 * Contains configurations for checklist tasks.
 */
open class ChecklistPluginExtension {

    /**
     * Names of the tasks to be skipped
     */
    var skippedTasks = mutableListOf<String>()

    /**
     * Resource configs to be checked
     */
    var resConfigs = listOf("en", "tr")

    /**
     * Git branch name
     */
    var branchPrefix = "version/"

    /**
     * Desired minimum sdk version
     */
    var minSDKVersion = 16

    /**
     * Desired target sdk version
     */
    var targetSDKVersion = 29

    /**
     * Desired compile sdk version
     */
    var compileSDKVersion = 29

    /**
     * Application id prefix
     */
    var applicationIdConvention = ""

    /**
     * Allowed install location values
     */
    var allowedInstallLocations: MutableList<InstallLocationValue> =
            mutableListOf(InstallLocationValue.AUTO, InstallLocationValue.INTERNAL_ONLY)

    /**
     * Maximum output file size
     */
    var maxOutputSize = 0

    /**
     * Name of the apk flavor
     */
    var flavorName = ""

    /**
     * Path of the checklist report
     */
    var reportPath = ""
}
