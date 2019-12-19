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
import java.io.ByteArrayOutputStream

/**
 * Helper class to execute system commands
 */
class CommandExecutor {

    /**
     * Executes given command and returns its output
     *
     * Given cmd parameter should have the command as its first element
     * and options should be another element
     *
     * @param project project
     * @param cmd command to be executed.
     */
    fun executeCommand(project: Project, vararg cmd: String): String {
        val byteArrayOS = ByteArrayOutputStream()
        project.exec {
            it.setCommandLine(*cmd)
            it.standardOutput = byteArrayOS
        }
        return byteArrayOS.toString()
    }
}
