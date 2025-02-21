Run as with standard spring boot - i.e. run TinyLedgerApplication

I've provided two get mappings, one to see the transactions, one to see ledger balance

I've provided two post mappings, one to withdraw from the ledger, one to deposit

I've added swagger (personal preference) so when running, you should be able to view this via:

http://localhost:8080/swagger-ui/index.html#


Assumptions:

- That no negative value or zero can be passed into withdraw/deposit
- Values with more than two decimal places will be rounded back to 2
- You can't withdraw more money that the total amount in ledger
- I've made it single threaded - but could easily remove locks if this wasnt something we wanted
- Due to not wanting to use a DB in this example, I've made a dummy repo class and initialised it with some values as if they were already in place in the DB

EXAMPLE:

By default if you run the get mapping of transactions you should get:

[
  "DEPOSITING 2000.00",
  "WITHDRAWING 500.00",
  "WITHDRAWING 500.00"
]

and the get mapping of balance you should get:

1000.00

These are the dummy values I setup.

If you were to then do transactions withdraw post mapping for a value 10.10, THEN ran the get mapping of transactions you should get:

[
  "DEPOSITING 2000.00",
  "WITHDRAWING 500.00",
  "WITHDRAWING 500.00",
  "WITHDRAWING 10.10"
]

and the get mapping of balance you should get:

989.90

if you were to then do transactions deposit post mapping for a value of 123.45, THEN ran the get mapping of transactions you should get:

[
  "DEPOSITING 2000.00",
  "WITHDRAWING 500.00",
  "WITHDRAWING 500.00",
  "WITHDRAWING 10.10",
  "DEPOSITING 123.45"
]

and the get mapping of balance you should get:

1113.35
