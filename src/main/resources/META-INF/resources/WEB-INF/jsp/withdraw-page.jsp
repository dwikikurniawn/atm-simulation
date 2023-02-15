<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Withdraw - ATM</title>
</head>

<body>
    <h1>Withdraw</h1>
    <hr>
    <div class="">
        <h3 access="false" id="control-9987720">Current Balance: $ ${account.balance}</h3>
    </div>
    <div class="">
        <h3 access="false" id="control-6741689">Withdraw:</h3>
    </div>

    <div class="rendered-form">
        <div class="formbuilder-button form-group field-btnWithdraw10">
            <form action="/transaction/withdraw" method="post">
                <button type="submit" class="btn-default btn" name="amount" value="10" id="btnWithdraw10">$10</button>
                <button type="submit" class="btn-default btn" name="amount" value="50" id="btnWithdraw50">$50</button>
                <button type="submit" class="btn-default btn" name="amount" value="100"
                    id="btnWithdraw100">$100</button>
            </form>
            <form action="/transaction/other-withdraw-page" method="get">
                            <button type="submit" class="btn-default btn">Other Amount</button>
            </form>
        </div>
        </br>
        <div class="navigationbutton">
              <form action="/dashboard" method="get">
                <button type="submit" class="btn-default btn">Cancel</button>
              </form>
        </div>
    </div>
</body>

</html>