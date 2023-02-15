<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Other Withdraw - ATM</title>
</head>

<body>
  <h1>Custom Withdraw - ATM</h1>
  <hr>
  <div class="">
    <h3 access="false" id="control-9987720">Current Balance: $ ${account.balance}</h3>
  </div>
  <div class="">
    <h3 access="false" id="control-6741689">Other Withdraw</h3>
  </div>

  <form action="/transaction/withdraw" method="post">
    <div class="formbuilder-text form-group field-field-pin-number">
      <label for="field-pin-number" class="formbuilder-text-label">Withdraw Amount</label> </br>
      <input type="number" class="form-control" name="amount" maxlength="4" id="amount" placeholder="$">
      <button type="submit" class="btn-default btn" name="btnSubmit" id="btn-submit">Submit</button>
    </div>
  </form>
</body>

</html>