# Zopa Technical Test

There is a need for an application to find a quote from Zopa’s market of lenders for 36-month 
loans that apply interest on a monthly basis.

Each lender in the market offers an amount of money to lend and the annual interest rate
they expect in return. The table below provides an example of market data:

| Lender | Rate  | Available |
|--------|-------|-----------|
| Jane   | 0.075 | 1200      |
| John   | 0.081 | 350       |

To ensure that Zopa's quotes are competitive, select a combination of lenders’ offers which
gives the lowest possible rate. The monthly repayment and the total repayment amounts
should be shown in addition to the amount requested and the annual interest rate for the
quote.

Repayment amounts should be displayed to two decimal places and the annual interest rate
displayed to one decimal place.

A quote may be requested in any £100 increment between £1000 and £15000 inclusive. 
If the market does not have enough offers to fulfil the request, then the application should
output “It is not possible to provide a quote.”

**The application should take one argument:**

```[loan_amount]```

**And write to standard output in the format:**

```
Requested amount: £XXXX
Annual Interest Rate: X.X%
Monthly repayment: £XXXX.XX
Total repayment: £XXXX.XX
```

## Remarks

* We recommend you choose the language most suitable to the role you are applying for.
* The monthly repayments should spread the total repayment cost over the term of the loan. 
Here is an explanation of how to calculate this: 
https://en.wikipedia.org/wiki/Amortization_calculator#The_formula
* We will review your code and run it against further test cases to see how it handles them.
* The list of lenders’ offers may be hard-coded, a static resource, or taken from an external 
source (e.g. a CSV file).
* We’re interested in discovering more about your engineering skills and approach when 
solving problems. It is acceptable to submit your program if it returns an answer that does
not exactly match the example test cases provided below.
* If you have any questions, then do not hesitate to contact us.

## Example test cases

### Example 1

| Lender | Rate  | Available |
|--------|-------|-----------|
| Jane   | 0.069 | 480       |
| Fred   | 0.071 | 520       |

**Input**
```
> $ ./zopa-rate 1000
```
**Output**
```
> Requested amount: £1000
> Annual Interest Rate: 7.0% 
> Monthly repayment: £30.78 
> Total repayment: £1108.10
```

### Example 2

| Lender | Rate  | Available |
|--------|-------|-----------|
| Jane   | 0.069 | 480       |
| Fred   | 0.071 | 520       |

**Input**
```
> $ ./zopa-rate 1700
```
**Output**
```
> It is not possible to provide a quote.
```

### Example 3

| Lender | Rate  | Available |
|--------|-------|-----------|
| Bob    | 0.075 | 640       |
| Jane   | 0.069 | 480       |
| Fred   | 0.071 | 520       |
| Mary   | 0.104 | 170       |
| John   | 0.081 | 320       |
| Dave   | 0.074 | 140       |
| Angela | 0.071 | 60        |

**Input**
```
> $ ./zopa-rate 1700
```
**Output**
```
> Requested amount: £1700
> Annual Interest Rate: 7.2% 
> Monthly repayment: £52.46 
> Total repayment: £1888.55
```

## Structure
The implementation about finding a quote according to a list of lenders is all
located in the `quote` folder at the root of the project. More specifically,
the very logic, technology-ignorant, is located in `quote/domain`. The rest of
the modules are about certain technology-specific concerns such as:
- the quote api is able to provide a quote reading from a csv file.
- the quote api is exposed in a console manner, in stdout.

The `util` module contains helpers to model the quote domain in a more functional
approach:
- a `Result` model similar to Java's `Either`.
- functional helpers to compose functions, either by partial application or by 
piping return type into the next function's argument.

The `app` module contains all of that together in a main that makes it easy
to run the console app, modifying things such as: the loan amount and the input
csv file containing all the lenders with their respective details.

## How to use
Simply, open the `app` module and its main.

The main looks like this:
```kotlin
fun main() {
    /**
     * Files are located in resources folder of this very module.
     */
    val example1 = getResources("/example1.csv")
    val example3 = getResources("/example3.csv")

    val findQuoteLogic = ::findQuoteLogic bind fetchLendersFromCsv(example1) bind LoanProperties(36)
    val findQuoteConsole = ::quoteConsoleOutput bind FindQuote(findQuoteLogic)

    findQuoteConsole(1000)
}
```
Two csv files are located in the `resources` folder of the `app` module.
Both files are loaded in variables, so that they are easy to change.

If we want to change the csv file used to run the application, we can just point
the other created file in the `fetchLendersFromCsv` operation. Changing the loan amount
is just changing the argument in `findQuoteConsole` operation.

The application just prints the result according to what the exercise above defines.

Also, modifiable is the length of the loan; i.e. the number of repayments. This can be
changed in `LoanProperties` when building the `findQuoteLogic` operation.

## Testing
We have defined a single test base, a contract, in which we run against the api
built in different ways:
- quote api in-memory, against hardcoded list of lenders.
- quote api fetching lenders from a csv file.
- quote api exposed in a console application.

