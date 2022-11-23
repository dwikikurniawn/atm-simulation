<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Main Menu - ATM</title>
</head>

<body>
  <div class="">
    <h1 access="false" id="control-2198787">Welcome ${name}</h1>
    <hr>
  </div>
  <div class="">
    <h3 access="false" id="control-9987720">Current Balance: ${balance}</h3>
  </div>
  <div class="">
    <h3 access="false" id="control-6741689">Menu:</h3>
  </div>
    <div class="navigationbutton">
      <a href="/transaction/withdraw-page/${accountNumber}">
        <button value="1" type="button" class="btn btn-default" name="btnWithdraw" id="btnWithdraw">Withdraw</button>
      </a>
      <a href="/transaction/transfer-page/${accountNumber}">
        <button value="2" type="button" class="btn btn-default" name="btnTransfer" id="btnTransfer">Fund
          Transfer</button>
      </a>
      <a href="/transaction/history/${accountNumber}">
        <button value="3" type="button" class="btn btn-default" name="btnHistory" id="btnHistory">Transaction
          History</button>
      </a>
      <a href="/login">
        <button value="4" type="button" class="btn btn-default" name="btnExit" id="btnExit">Exit</button>
      </a>
    </div>
</body>

</html>