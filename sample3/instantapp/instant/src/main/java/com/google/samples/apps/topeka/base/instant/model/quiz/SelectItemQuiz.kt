/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.topeka.base.instant.model.quiz

data class SelectItemQuiz(
        override val question: String,
        override val answer: IntArray,
        override var options: Array<String>,
        override var solved: Boolean
) : OptionsQuiz<String>(question, answer, options, solved) {

    override val type get() = QuizType.SINGLE_SELECT

    override val stringAnswer get() = getAnswer(answer, options)

}