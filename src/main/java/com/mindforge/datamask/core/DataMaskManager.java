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
package com.mindforge.datamask.core;

import com.mindforge.datamask.core.exception.DataMaskException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Set;

public class DataMaskManager {
    private String maskingCharacter;

    public static final Set<Class> ALLOWED_TYPES = Set.of(byte.class, int.class, short.class, long.class, float.class, double.class, char.class, boolean.class,
            String.class, Byte.class, Integer.class, Short.class, Long.class, Float.class, Double.class, Character.class, Boolean.class);

    public DataMaskManager() {
        this.maskingCharacter = "*";
    }

    public DataMaskManager(final String maskingCharacter) {
        this.maskingCharacter = maskingCharacter;
    }

    public String maskText(final MaskInformationDTO maskInformationDTO, final String text) throws DataMaskException {
        if(StringUtils.isEmpty(text) || StringUtils.isBlank(text)) {
            throw new DataMaskException(DataMaskException.INPUT_TEXT_MISSING_EXCEPTION);
        }

        if((maskInformationDTO.getLeftCharacterCount() < 0) || (maskInformationDTO.getRightCharacterCount() < 0)) {
            throw new DataMaskException(DataMaskException.INVALID_CHARACTER_COUNT_EXCEPTION);
        }

        final String inputText = text.trim();
        final DataFormatType dataFormatType = maskInformationDTO.getDataFormatType();
        if(DataFormatType.EMAIL.equals(dataFormatType)) {
            if(!EmailValidator.getInstance().isValid(inputText)) {
                throw new DataMaskException(DataMaskException.INVALID_EMAIL_ADDRESS_EXCEPTION);
            }
            return maskEmail(maskInformationDTO, inputText);
        } else {
            return maskGenericText(maskInformationDTO, inputText);
        }
    }

    private String maskEmail(final MaskInformationDTO maskInformationDTO, final String text) throws DataMaskException {
        String result = null;
        final String[] parts = text.split("@");
        final String emailPart = parts[0];
        final String domainPart = parts[1];
        final String delimiter = maskInformationDTO.getDelimiter();
        if(StringUtils.isEmpty(delimiter) || StringUtils.isBlank(delimiter)) {
            final String maskedText = mask(maskInformationDTO, emailPart);
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(maskedText).append("@").append(domainPart);
            result = stringBuilder.toString();
        } else {
            final String maskedText = maskWithDelimiter(maskInformationDTO, emailPart);
            final StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(maskedText).append("@").append(domainPart);
            result = stringBuilder.toString();
        }
        return result;
    }

    private String maskGenericText(final MaskInformationDTO maskInformationDTO, final String inputText) throws DataMaskException {
        String result = null;
        final String delimiter = maskInformationDTO.getDelimiter();
        if(StringUtils.isEmpty(delimiter) || StringUtils.isBlank(delimiter)) {
            result = mask(maskInformationDTO, inputText);
        } else {
            result = maskWithDelimiter(maskInformationDTO, inputText);
        }
        return result;
    }

    private String maskWithDelimiter(final MaskInformationDTO maskInformationDTO, final String text) throws DataMaskException {
        final String inputDelimiter = maskInformationDTO.getDelimiter().trim();
        final String rawInput = text.replaceAll(inputDelimiter, "");
        final String maskedRawInput = mask(maskInformationDTO, rawInput);
        final String[] textParts = text.split(inputDelimiter);
        final int partsCount = textParts.length;
        final StringBuilder stringBuilder = new StringBuilder();
        int offset = 0;

        for(int i = 0; i <= (partsCount - 2); i++) {
            int endIndex = offset + textParts[i].length();
            stringBuilder.append(maskedRawInput.substring(offset, endIndex)).append(inputDelimiter);
            offset = endIndex;
        }

        stringBuilder.append(maskedRawInput.substring(offset, offset + textParts[partsCount - 1].length()));
        return stringBuilder.toString();
    }

    private String mask(final MaskInformationDTO maskInformationDTO, final String text) throws DataMaskException {
        String result = null;
        final MaskType maskType = maskInformationDTO.getMaskType();
        final int leftCharacterCount = maskInformationDTO.getLeftCharacterCount();
        final int rightCharacterCount = maskInformationDTO.getRightCharacterCount();
        if(MaskType.FULL_MASKING.equals(maskType)) {
            result = maskFull(text);
        } else if(MaskType.SIDE_MASKING.equals(maskType)) {
            if((leftCharacterCount + rightCharacterCount) > text.length()) {
                String message = String.format(DataMaskException.INVALID_CHARACTER_COUNT_EXCEPTION, leftCharacterCount, rightCharacterCount, text.length());
                throw new DataMaskException(message);
            }
            result = sideMasking(text, leftCharacterCount, rightCharacterCount);
        } else {
            if((leftCharacterCount + rightCharacterCount) > text.length()) {
                String message = String.format(DataMaskException.INVALID_CHARACTER_COUNT_EXCEPTION, leftCharacterCount, rightCharacterCount, text.length());
                throw new DataMaskException(message);
            }
            result = middleMasking(text, leftCharacterCount, rightCharacterCount);
        }
        return result;
    }

    private String maskFull(final String text) throws DataMaskException {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.repeat(this.maskingCharacter, text.length());
        return stringBuilder.toString();
    }

    private String sideMasking(final String text, final int leftTextInVisibleCount, final int rightTextInVisibleCount) throws DataMaskException {
        if((leftTextInVisibleCount + rightTextInVisibleCount) == text.length()) {
            return maskFull(text);
        } else {
            final StringBuilder leftSideAsterikStringBuilder = new StringBuilder();
            leftSideAsterikStringBuilder.repeat(this.maskingCharacter, leftTextInVisibleCount);

            final StringBuilder rightSideAsterikStringBuilder = new StringBuilder();
            rightSideAsterikStringBuilder.repeat(this.maskingCharacter, rightTextInVisibleCount);

            final StringBuilder stringBuilder = new StringBuilder(text);
            stringBuilder.replace(0, leftTextInVisibleCount, leftSideAsterikStringBuilder.toString());
            stringBuilder.replace(text.length() - rightTextInVisibleCount, text.length(), rightSideAsterikStringBuilder.toString());
            return stringBuilder.toString();
        }
    }

    private String middleMasking(final String text, final int leftTextVisibleCount, final int rightTextVisibleCount) throws DataMaskException {
        if((leftTextVisibleCount + rightTextVisibleCount) == text.length()) {
            return text;
        } else {
            final int repeatCount = text.length() - (leftTextVisibleCount + rightTextVisibleCount);
            final StringBuilder asterikStringBuilder = new StringBuilder();
            asterikStringBuilder.repeat(this.maskingCharacter, repeatCount);

            final StringBuilder stringBuilder = new StringBuilder(text);
            stringBuilder.replace(leftTextVisibleCount, text.length() - rightTextVisibleCount, asterikStringBuilder.toString());
            return stringBuilder.toString();
        }
    }
}
