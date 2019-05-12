# How to run this application

First generation of documentation

## Prerequisites 

Required JDK and IDE for DEBUG this application

`NOTE:` You can use another version of JDK and IDE. But you should configure `Lombok plugin for IDE` and change Java version in maven pom.xml

## Java

`version:` 1.8

### How to install

1. Go to https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
2. Find `Java SE Development Kit 8u212` 
3. Choose needed distributive of from list (NOTE: you should `Accept Licence Agreement` before downloading)
4. Run distributive

## Intellij Idea

`version:` any

**NOTE:** You can donwload Ultimate version, if you have university email.

### How to install

1. Go to https://www.jetbrains.com/idea/
2. Click `donwload`
3. Choose needed version
4. Run distributive

## Project configuration

1. Get project (git cli or donwload it by zip)
2. Open project via `Intellij Idea`
3. Go to `File -> Settings -> Plugins` and search `Lombok'
4. Install `Lombok` and restart IDE

## Run application

You should prepare dataset like `dataset_example` before run

### Hole creation

1. Set required parameters in `properties -> configuration.properties`
2. **OPTIONAL:** Set specific parameters in `${dataset_path} -> descriptor.properties`
3. Go to src -> main -> java -> com.colorhole -> Runner.java
4. Just click right button and run it
5. See result at `${dataset_path}` (here will be: images with holes and meta-inf about it - `hole_statistic.csv`))

### Statistic

After inpainting and preparing of dataset with inpainted images

1. Go to src -> main -> java -> com.colorhole -> StaticalCharasteristicRunner.java
2. Just click right button and run it
3. See result at `${dataset_path} -> mse_statistic.csv`

**NOTE:** Static filenames can be configured via `descriptor.properties`

### Enjoy using application!
