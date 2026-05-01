# datamask-core

A Java library for data masking and sensitive information protection.

## Features

- **Multiple Masking Types**: FULL_MASKING, SIDE_MASKING, MIDDLE_MASKING
- **Email Support**: Masks email addresses while preserving the domain
- **Delimiter Support**: Handles formatted text like credit card numbers (e.g., `1234-5678-9012-3456`)
- **Customizable Masking Character**: Configure the character used for masking

## Masking Types

| Type | Description                 | Example Input | Example Output |
|------|-----------------------------|---------------|----------------|
| FULL_MASKING | Masks entire text           | `secret` | `******`       |
| SIDE_MASKING | Masks left/right characters | `1234567890` (left=2, right=2) | `**345678**`   |
| MIDDLE_MASKING | Masks middle portion        | `1234567890` (left=2, right=2) | `12******90`   |

## Quick Start

### Add Dependency

```xml
<dependency>
    <groupId>io.github.freewarelabs</groupId>
    <artifactId>datamask-core</artifactId>
    <version>1.0</version>
</dependency>
```

### Basic Usage

```java
// Initialize with default masking character (*)
DataMaskManager manager = new DataMaskManager();

// Or with custom character
DataMaskManager manager = new DataMaskManager("#");

// Create masking configuration
MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
        .maskType(MaskType.SIDE_MASKING)
        .dataFormatType(DataFormatType.TEXT)
        .leftCharacterCount(2)
        .rightCharacterCount(2).build();

// Mask text
String result = manager.maskText(maskInformationDTO, "1234567890");
// Result: "**345678**"
```

### Email Masking

```java
MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
        .maskType(MaskType.MIDDLE_MASKING)
        .dataFormatType(DataFormatType.EMAIL)
        .leftCharacterCount(1)
        .rightCharacterCount(1).build();

String result = manager.maskText(maskInformationDTO, "user@example.com");
// Result: "u**r@example.com"
```

### With Delimiters

```java
MaskInformationDTO maskInformationDTO = MaskInformationDTO.builder()
        .maskType(MaskType.SIDE_MASKING)
        .dataFormatType(DataFormatType.TEXT)
        .delimiter("-")
        .leftCharacterCount(4)
        .rightCharacterCount(4).build();

String result = manager.maskText(maskInformationDTO, "1234-5678-9012-3456");
// Result: "****-5678-9012-****"
```
### Affect of leftCharacterCount and rightCharacterCount on SIDE_MASKING and MIDDLE_MASKING
- **In case of SIDE_MASKING leftCharacterCount and rightCharacterCount represents the number of characters to be masked from left and right side
- **In case of MIDDLE_MASKING leftCharacterCount and rightCharacterCount represents the number of characters to be visible from left and right side

## Build & Test

```bash
# Compile and run tests
./gradlew build

# Run tests only
./gradlew test

# Build JAR
./gradlew jar
```

## License

Apache License 2.0 - see [LICENSE.txt](LICENSE.txt) for details.
