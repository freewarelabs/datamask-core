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
package io.github.freewarelabs.datamask.core;

import io.github.freewarelabs.datamask.core.exception.DataMaskException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DataMaskManagerUTest {
    private DataMaskManager dataMaskManagerWithDefaultMaskingCharacter;
    private DataMaskManager dataMaskManagerWithCustomMaskingCharacter;

    @BeforeEach
    public void setUp() {
        dataMaskManagerWithDefaultMaskingCharacter = new DataMaskManager();
        dataMaskManagerWithCustomMaskingCharacter = new DataMaskManager("%");
    }

    @Test
    public void testMaskingForNullText() {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(0)
                .rightCharacterCount(0).build();

        assertThrows(DataMaskException.class, ()->{
            dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, null);
        });
    }

    @Test
    public void testMaskingForEmptyText() {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(0)
                .rightCharacterCount(0).build();

        assertThrows(DataMaskException.class, ()->{
            dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "");
        });
    }

    @Test
    public void testMaskingForBlankText() {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(0)
                .rightCharacterCount(0).build();

        assertThrows(DataMaskException.class, ()->{
            dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "      ");
        });
    }

    @Test
    public void testMaskingForNegativeLeftCharacterCount() {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(-3)
                .rightCharacterCount(3).build();

        assertThrows(DataMaskException.class, ()->{
            dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello");
        });
    }

    @Test
    public void testMaskingForNegativeRightCharacterCount() {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(3)
                .rightCharacterCount(-3).build();

        assertThrows(DataMaskException.class, ()->{
            dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello");
        });
    }

    @Test
    public void testMaskingForSingleCharacter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(3)
                .rightCharacterCount(3).build();

        assertEquals("*", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "h"));
    }

    @Test
    public void testFullMasking() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(0)
                .rightCharacterCount(0).build();

        assertEquals("*****", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello"));
    }

    @Test
    public void testFullMaskingIgnoreCharacterCount() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(1)
                .rightCharacterCount(1).build();

        assertEquals("*****", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello"));
    }

    @Test
    public void testFullMaskingWithDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("-")
                .leftCharacterCount(1)
                .rightCharacterCount(1).build();

        assertEquals("****-****-****-****", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "1111-2222-3333-4444"));
    }

    @Test
    public void testFullMaskingWithNullDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter(null)
                .leftCharacterCount(0)
                .rightCharacterCount(0).build();

        assertEquals("*****", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello"));
    }

    @Test
    public void testFullMaskingForEmail() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.EMAIL)
                .delimiter("")
                .leftCharacterCount(2)
                .rightCharacterCount(2).build();

        assertEquals("********@gmail.com", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "john.doe@gmail.com"));
    }

    @Test
    public void testFullMaskingForEmailWithNullDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.EMAIL)
                .delimiter(null)
                .leftCharacterCount(2)
                .rightCharacterCount(2).build();

        assertEquals("********@gmail.com", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "john.doe@gmail.com"));
    }

    @Test
    public void testFullMaskingForEmailWithDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.EMAIL)
                .delimiter("-")
                .leftCharacterCount(2)
                .rightCharacterCount(2).build();

        assertEquals("****-***@gmail.com", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "john-doe@gmail.com"));
    }

    @Test
    public void testSideMaskingWithTextHavingEvenCharacters1() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(2)
                .rightCharacterCount(2).build();

        assertEquals("**somn**", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "insomnia"));
    }

    @Test
    public void testSideMaskingWithTextHavingEvenCharacters2() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(4)
                .rightCharacterCount(4).build();

        assertEquals("********", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "insomnia"));
    }

    @Test
    public void testSideMaskingWithTextHavingOddCharacters1() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(2)
                .rightCharacterCount(1).build();

        assertEquals("**ll*", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello"));
    }

    @Test
    public void testSideMaskingWithTextHavingOddCharacters2() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(2)
                .rightCharacterCount(3).build();

        assertEquals("*****", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello"));
    }

    @Test
    public void testSideMaskingWithDelimiter1() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("-")
                .leftCharacterCount(4)
                .rightCharacterCount(4).build();

        assertEquals("****-2222-3333-****", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "1111-2222-3333-4444"));
    }

    @Test
    public void testSideMaskingWithDelimiter2() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("-")
                .leftCharacterCount(6)
                .rightCharacterCount(6).build();

        assertEquals("****-**22-33**-****", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "1111-2222-3333-4444"));
    }

    @Test
    public void testSideMaskingWithDelimiter3() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("-")
                .leftCharacterCount(5)
                .rightCharacterCount(7).build();

        assertEquals("****-*222-3***-****", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "1111-2222-3333-4444"));
    }

    @Test
    public void testSideMaskingForEmailWithoutDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.EMAIL)
                .delimiter("")
                .leftCharacterCount(2)
                .rightCharacterCount(4).build();

        assertEquals("**hn****@gmail.com", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "john.doe@gmail.com"));
    }

    @Test
    public void testSideMaskingForEmailNullDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.EMAIL)
                .delimiter(null)
                .leftCharacterCount(2)
                .rightCharacterCount(4).build();

        assertEquals("**hn****@gmail.com", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "john.doe@gmail.com"));
    }

    @Test
    public void testSideMaskingForEmailWithDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.EMAIL)
                .delimiter("-")
                .leftCharacterCount(2)
                .rightCharacterCount(2).build();

        assertEquals("**hn-d**@gmail.com", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "john-doe@gmail.com"));
    }

    @Test
    public void testSideMaskingWithZeroCharacterCount() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(0)
                .rightCharacterCount(0).build();

        assertEquals("insomnia", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "insomnia"));
    }

    @Test
    public void testSideMaskingWithNullDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter(null)
                .leftCharacterCount(2)
                .rightCharacterCount(2).build();

        assertEquals("**somn**", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "insomnia"));
    }

    @Test
    public void testSideMaskingForInvalidCharacterCount() {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.SIDE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(3)
                .rightCharacterCount(3).build();

        assertThrows(DataMaskException.class, ()->{
            dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello");
        });
    }

    @Test
    public void testMiddleMaskingWithTextHavingEvenCharacters1() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(2)
                .rightCharacterCount(2).build();

        assertEquals("in****ia", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "insomnia"));
    }

    @Test
    public void testMiddleMaskingWithTextHavingEvenCharacters2() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(4)
                .rightCharacterCount(4).build();

        assertEquals("insomnia", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "insomnia"));
    }

    @Test
    public void testMiddleMaskingWithTextHavingOddCharacters1() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(2)
                .rightCharacterCount(1).build();

        assertEquals("he**o", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello"));
    }

    @Test
    public void testMiddleMaskingWithTextHavingOddCharacters2() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(3)
                .rightCharacterCount(2).build();

        assertEquals("hello", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello"));
    }

    @Test
    public void testMiddleMaskingWithDelimiter1() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("-")
                .leftCharacterCount(4)
                .rightCharacterCount(4).build();

        assertEquals("1111-****-****-4444", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "1111-2222-3333-4444"));
    }

    @Test
    public void testMiddleMaskingWithDelimiter2() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("-")
                .leftCharacterCount(6)
                .rightCharacterCount(6).build();

        assertEquals("1111-22**-**33-4444", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "1111-2222-3333-4444"));
    }

    @Test
    public void testMiddleMaskingWithDelimiter3() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("-")
                .leftCharacterCount(5)
                .rightCharacterCount(7).build();

        assertEquals("1111-2***-*333-4444", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "1111-2222-3333-4444"));
    }

    @Test
    public void testMiddleMaskingForEmailWithoutDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.EMAIL)
                .delimiter("")
                .leftCharacterCount(2)
                .rightCharacterCount(4).build();

        assertEquals("jo**.doe@gmail.com", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "john.doe@gmail.com"));
    }

    @Test
    public void testMiddleMaskingForEmailWithNullDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.EMAIL)
                .delimiter(null)
                .leftCharacterCount(2)
                .rightCharacterCount(4).build();

        assertEquals("jo**.doe@gmail.com", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "john.doe@gmail.com"));
    }

    @Test
    public void testMiddleMaskingForEmailWithDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.EMAIL)
                .delimiter("-")
                .leftCharacterCount(2)
                .rightCharacterCount(2).build();

        assertEquals("jo**-*oe@gmail.com", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "john-doe@gmail.com"));
    }

    @Test
    public void testMiddleMaskingWithZeroCharacterCount() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(0)
                .rightCharacterCount(0).build();

        assertEquals("********", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "insomnia"));
    }

    @Test
    public void testMiddleMaskingWithNullDelimiter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter(null)
                .leftCharacterCount(2)
                .rightCharacterCount(2).build();

        assertEquals("in****ia", dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "insomnia"));
    }

    @Test
    public void testMiddleMaskingForInvalidCharacterCount() {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.MIDDLE_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(3)
                .rightCharacterCount(3).build();

        assertThrows(DataMaskException.class, ()->{
            dataMaskManagerWithDefaultMaskingCharacter.maskText(maskInformationDTO, "hello");
        });
    }

    @Test
    public void testMaskingWithCustomMaskingCharacter() throws Exception {
        MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
                .maskType(MaskType.FULL_MASKING)
                .dataFormatType(DataFormatType.TEXT)
                .delimiter("")
                .leftCharacterCount(0)
                .rightCharacterCount(0).build();

        assertEquals("%%%%%", dataMaskManagerWithCustomMaskingCharacter.maskText(maskInformationDTO, "hello"));
    }
}
