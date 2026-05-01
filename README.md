# datamask-core

A Java library for data masking and sensitive information protection.

## Features

- **Multiple Masking Types**: FULL_MASKING, SIDE_MASKING, MIDDLE_MASKING
- **Email Support**: Masks email addresses while preserving the domain
- **Delimiter Support**: Handles formatted text like credit card numbers (e.g., `1234-5678-9012-3456`)
- **Annotation-Based**: Use `@Mask` annotation on fields for declarative masking
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
    <groupId>com.mindforge</groupId>
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
MaskInformationDTO config = new MaskInformationDTO();
config.setMaskType(MaskType.SIDE_MASKING);
config.setLeftCharacterCount(2);
config.setRightCharacterCount(2);
config.setDataFormatType(DataFormatType.TEXT);

// Mask text
String result = manager.maskText(config, "1234567890");
// Result: "**345678**"
```

### Email Masking

```java
MaskInformationDTO config = new MaskInformationDTO();
config.setMaskType(MaskType.MIDDLE_MASKING);
config.setLeftCharacterCount(1);
config.setRightCharacterCount(1);
config.setDataFormatType(DataFormatType.EMAIL);

String result = manager.maskText(config, "user@example.com");
// Result: "u**r@example.com"
```

### With Delimiters

```java
MaskInformationDTO config = new MaskInformationDTO();
config.setMaskType(MaskType.SIDE_MASKING);
config.setLeftCharacterCount(4);
config.setRightCharacterCount(4);
config.setDelimiter("-");

String result = manager.maskText(config, "1234-5678-9012-3456");
// Result: "****-5678-9012-****"
```

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
