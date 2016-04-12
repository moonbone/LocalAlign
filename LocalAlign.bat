@echo off
java -Xmx15000M -cp "bin;lib/*" edu.tau.compbio.BEARBenchmark %*
@echo on