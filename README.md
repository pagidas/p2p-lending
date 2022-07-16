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



