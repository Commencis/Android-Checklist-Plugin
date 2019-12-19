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

package com.commencis.checklist.util.plugin.android

import com.android.build.gradle.internal.dsl.BuildType
import org.gradle.api.Project

interface IBuildTypeProvider {
    /**
     * Gets a certain build type from base extension.
     *
     * @param project Project.
     * @param buildType Build type that we are looking for.
     *
     * @return Found build type or throws [org.gradle.api.UnknownDomainObjectException]
     */
    fun getBuildType(project: Project, buildType: AndroidBuildType): BuildType
}