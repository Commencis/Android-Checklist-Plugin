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

package com.commencis.checklist.util.project.resolver

import org.gradle.api.plugins.PluginContainer
import org.gradle.api.tasks.TaskContainer

interface ProjectResolver {

    interface AndroidProjectResolver {

        /**
         * Determines if project is an android project.
         */
        fun isAndroid(pluginContainer: PluginContainer): Boolean

        /**
         * Determines if project is android application.
         *
         * @return true if android application, Otherwise false.
         */
        fun isAndroidApplication(pluginContainer: PluginContainer): Boolean

        /**
         * Determines if project is android library.
         *
         * @return true if android library, Otherwise false.
         */
        fun isAndroidLibrary(pluginContainer: PluginContainer): Boolean

        /**
         * Determines if project is android feature.
         *
         * @return true if android feature, Otherwise false.
         */
        fun isAndroidFeature(pluginContainer: PluginContainer): Boolean

    }

}