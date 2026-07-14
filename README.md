# Coffee Order CLI

## Overview
A small Java CLI for ordering coffee drinks with sizes, milk types, toppings, and a running total.
The running total updates on a background thread to keep input responsive.

## Run
```powershell
mvn -q -DskipTests compile
mvn -q exec:java -Dexec.mainClass=src.Main
```

## Test
```powershell
mvn -q test
```

## Usage Flow
- Choose base drink, size, milk type.
- Add toppings (repeatable) or enter 0 to finish.
- Enter x at any drink prompt to finish and print the receipt.

## Notes
- Pricing is in VND.
- Menu choices are numeric for minimal input steps.

