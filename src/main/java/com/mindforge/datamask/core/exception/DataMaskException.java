/*
 * Copyright 2026 MindForge
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
package com.mindforge.datamask.core.exception;

public class DataMaskException extends Exception {
    public static final String INVALID_CHARACTER_COUNT_EXCEPTION = "The sum of leftCharacterCount '%d' and rightCharacterCount '%d' is greater then input string length '%d'";
    public static final String INPUT_TEXT_MISSING_EXCEPTION = "The input text to be masked is missing";
    public static final String INVALID_EMAIL_ADDRESS_EXCEPTION = "The input email address is invalid";

    public DataMaskException(String message) {
        super(message);
    }
}
