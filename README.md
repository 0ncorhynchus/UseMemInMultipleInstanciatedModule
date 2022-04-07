# UseMemInMultipleInstanciatedModule

A test fails.

```sbt
sbt:UseMemInMultipleInstanciatedModule> test
memFile : /tmp/MyMemoryTest2936389383300140346.hex
memFile0: /tmp/UseMyMemoryTest11607143854205725066.hex
memFile1: /tmp/UseMyMemoryTest15853167624741005827.hex
memFile0: /tmp/UseTwoMemsTest10064626088937068014.hex
memFile1: /tmp/UseTwoMemsTest5796118710673717661.hex
[info] UseTwoMemsTest:
[info] UseMyMemoryTest:
[info] MyMemoryTest:
[info] - should work *** FAILED ***
[info] - should work
[info]   io_data1=4116 (0x1014) did not equal expected=31280 (0x7a30) (lines in UseMyMemoryTest.scala: 52, 49) (UseMyMemoryTest.scala:59)
[info] - should work
[info] Run completed in 3 seconds, 977 milliseconds.
[info] Total number of tests run: 3
[info] Suites: completed 3, aborted 0
[info] Tests: succeeded 2, failed 1, canceled 0, ignored 0, pending 0
[info] *** 1 TEST FAILED ***
[error] Failed tests:
[error]         UseMyMemoryTest
[error] (Test / test) sbt.TestsFailedException: Tests unsuccessful
[error] Total time: 5 s, completed Apr 7, 2022, 6:42:58 PM
```

But, it succeeds after adding a print statement.

```sh
$ sed -i -e 's;// \(.*XXX\);\1;g' src/main/scala/MyMemory.scala
```

```sbt
sbt:UseMemInMultipleInstanciatedModule> test

...

[info] UseTwoMemsTest:
[info] - should work
[info] MyMemoryTest:
[info] - should work
[info] UseMyMemoryTest:
[info] - should work
[info] Run completed in 2 seconds, 254 milliseconds.
[info] Total number of tests run: 3
[info] Suites: completed 3, aborted 0
[info] Tests: succeeded 3, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 7 s, completed Apr 7, 2022, 6:51:00 PM
```
