## Spore
Spore (Spog Core) is a library containing commonly used classes frequently copy and pasted into all of my projects, So I made an API for them.

## Features
- Message class ~ Easier creation and formatting of TextComponent class using legacy components, and different built in fonts
- Random class ~ Easy RNG related utilities
- IntFormat class ~ Easy formatting of second/millisecond integers to duration strings mm:ss
- ParticleShape class ~ Specify different particles/colours and make a shape of them
- BlockArea class ~ Useful for getting large quantities of blocks without manually defining a shape out of relative block positions

## Usage
### Maven
[Jar](https://repo.spog.dev/#/releases/dev/spog/spore)
#### Dependency
```xml
<dependency>
    <groupId>dev.spog</groupId>
    <artifactId>spore</artifactId>
    <version>1.1.1</version>
    <scope>provided</scope>
</dependency>
```
### Kotlin Gradle
```java
implementation("dev.spog:spore:1.1.1")
```
