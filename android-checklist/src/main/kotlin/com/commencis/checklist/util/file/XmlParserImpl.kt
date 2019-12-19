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

package com.commencis.checklist.util.file

import groovy.util.XmlSlurper
import groovy.util.slurpersupport.NodeChild

class XmlParserImpl : XmlParser {

    private val slurper: XmlSlurper by lazy { XmlSlurper() }

    override fun <T> findAttribute(xml: String, attributeName: String): T? {
        return findAttributeWithinNode(xml, attributeName, null)
    }

    override fun <T> findAttributeWithinNode(xml: String, attributeName: String, nodeName: String?): T? {
        slurper.parseText(xml).depthFirst().forEach {
            val node = it as? NodeChild
            if (node != null && (nodeName == null || node.name() == nodeName)) {
                val attributes = node.attributes()

                if (attributes.containsKey(attributeName)) {
                    return attributes[attributeName] as T
                }
            }
        }

        return null
    }
}