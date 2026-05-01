# datamask-core

Java library for data masking. MaskType: FULL_MASKING, SIDE_MASKING, MIDDLE_MASKING. Supports email and delimited text (e.g., credit cards).

## Build & Test

```bash
./gradlew build      # compile + test
./gradlew test       # run tests only
./gradlew jar        # build JAR
```

## Key Files

- `src/main/java/com/mindforge/datamask/core/DataMaskManager.java` - core logic
- `src/main/java/com/mindforge/datamask/core/annotation/Mask.java` - field annotation
- `src/test/java/com/mindforge/datamask/core/DataMaskManagerUTest.java` - unit tests

## Code Review Rules

When doing code reviews, enforce the following rules:

- Every local variable must be declared `final` unless it is reassigned
- Every method parameter must be declared `final`
- Every class should have a corresponding test class (excluding enums and annotations)
- Every method should have at least one test method
- No System.out.println / printStackTrace - use proper logging instead
- No magic numbers - use constants instead

## Dependencies

- Apache Commons Lang3 (compile-time)
- Commons Validator (compile-time)
- Lombok (compile-time)
- JUnit 5 + Mockito (test only)
