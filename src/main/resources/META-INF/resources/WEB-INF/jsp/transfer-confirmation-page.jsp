<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Transfer Confirmation Screen</title>
</head>

<body>
  <div class="">
    <h1 access="false" id="control-2198787">Transfer Confirmation</h1>
    <hr>
  </div>
  <div class="">
        <h3 access="false" id="control-2198787">Reference Number: ${referenceNumber}</h3>
      </div>
  <div class="">
      <h3 access="false" id="control-9987720">Recipient Account Number: ${recipientAccountNumber}</h3>
    </div>
  <div class="">
    <h3 access="false" id="control-9987720">Amount Transfer: ${amount}</h3>
  </div>
  <div class="">
    <h3 access="false" id="control-6741689">Menu:</h3>
  </div>
     <div class="navigationbutton">
    <form action="/transaction/transfer" method="post">
                <button type="navigationbutton" class="btn-default btn">Submit</button>
              </form>
        <form action="/dashboard" method="get">
            <button type="cancel" class="btn-default btn">Cancel</button>
          </form>
    </div>
</body>

</html>