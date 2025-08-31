## How to set up
1. Build
```bash
./gradlew build
```
2. Run
```bash
java -cp build/classes/java/main com.amartha.sample.Main
```

## How to use
```bash
----
Menu
----
1. get loans
2. get bills
3. get outstanding
4. make payment
5. is delinquent

0. exit
Choose:
```

Notes:
For simplicity, a loan is already pre-created with the following specs:
* tenure: 50 weeks
* loan amount: 5,000,000
* annual rate (fixed): 10%
