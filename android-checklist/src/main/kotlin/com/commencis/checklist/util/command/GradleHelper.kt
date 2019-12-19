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

import org.gradle.api.Project

/**
 * Helper class for gradle related methods
 */
class GradleHelper(private val commandExecutor: CommandExecutor) {

    /**
     * Returns dependencies of the given apk
     *
     * @param project project
     * @return dependencies of the apk
     */
    fun getApkDependencies(project: Project): String {
        return commandExecutor.executeCommand(
                project,
                "sh",
                "-c",
                "${project.rootDir}/gradlew :${project.name}:dependencies"
        )
    }

    /**
     * Returns Google Play dependencies of the given apk
     *
     * @param project project
     * @return Google Play dependencies of the apk
     */
    fun getPlayDependencies(project: Project): String {
        return commandExecutor.executeCommand(
                project,
                "sh",
                "-c",
                "${project.rootDir}/gradlew :${project.name}:dependencies | grep -- \"play-services:\" | cat"
        )
    }

    /**
     * Executes tests on the project and returns gradle output
     * @param project project
     * @return empty string if tests passed, error message otherwise
     */
    fun runTests(project: Project): String {
        return commandExecutor.executeCommand(
                project,
                "sh",
                "-c",
                "${project.rootDir}/gradlew -q :${project.name}:test --continue | cat"
        )
    }
}