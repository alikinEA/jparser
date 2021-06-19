# Jparser
 json parser with minimum memory allocations

ToDo:
- array contains
- array iterator
- parse nested objects

Benchmark                                         Mode  Cnt        Score        Error  Units
PerformanceTest.jackson_containsByParamAndValue  thrpt    5   665869,997 ±  26466,298  ops/s
PerformanceTest.jackson_getValueByName           thrpt    5   675037,812 ±  21664,669  ops/s
PerformanceTest.jparser_containsByParamAndValue  thrpt    5  7292612,616 ± 458354,840  ops/s
PerformanceTest.jparser_getValueByName           thrpt    5  3586693,516 ± 239120,415  ops/s