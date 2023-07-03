function khaltiConfig(){
  var config = {
      // replace the publicKey with yours
      "publicKey": "test_public_key_46b3052e00e145b5b2d988d17359b3a4",
      "transactionID": "1234567890",
      "productIdentity": "10",
      "productName": "Company Name",
      "productUrl": "http://localhost:8080/",
      "paymentPreference": [
          "KHALTI",
          "EBANKING",
          "MOBILE_BANKING",
          "CONNECT_IPS",
          "SCT",
          ],
      "eventHandler": {
          onSuccess (payload) {
              // hit merchant api for initiating verfication
              let companyId = document.getElementById("company-id").value;
              console.log(companyId);
              window.location.href = "/verifypayment?token="+payload.token+"&amount="+payload.amount+"&companyId="+companyId;
          },
          onError (error) {
              let errorMsg = document.getElementById("payment-error-message");
//              errorMsg.innerHTML = error['token']
          },
          onClose () {
              console.log('widget is closing');
          }
      }
  };

  return config;
}

let config = khaltiConfig();
var checkout = new KhaltiCheckout(config);
var paymentBtn = document.getElementById("payment-button");
paymentBtn.onclick = function () {
          var amountInput = document.getElementById("total");
          
            var amount = parseFloat(amountInput.innerText)*100;

            if (!amount){
              console.log("amount is null");
              amount = amountInput.value * 100;
            }
            if (amount) {
                 // Convert amount from rupees to paisa
                config.amount = amount;
                checkout.show({popupMobile: true});
                console.log(amount);
            } else {
                alert("Please enter the amount.");
            }
       
}


var fundManagement = document.getElementById("fund-management");
var paymentOptions = document.getElementById("payment-options");

fundManagement.addEventListener("click", () => {
  paymentOptions.style.display = (paymentOptions.style.display === "block") ? "none" : "block";
  console.log(paymentOptions.style.display);
});