# StringFunc
to generate a self-contained jar file:
```
./gradlew jmhClasses jmhJar
```

to run a specific microbench
```
java -jar ./build/libs/StringFunc-jmh.jar com.amazon.jdkteam.brownbag.SplitAndPick
```

to run a microbench with GC profiling 
```
java -jar ./build/libs/StringFunc-jmh.jar com.amazon.jdkteam.brownbag.SplitAndPick -prof gc
```
