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
            var companyId = document.getElementById("companyId").value;
            console.log(companyId);
            window.location.href = "/verifypayment?token="+payload.token+"&amount="+payload.amount+"&companyid="+companyId;
//            verifyPayment(payload);
        },
        onError (error) {
            console.log(error);
        },
        onClose () {
            console.log('widget is closing');
        }
    }
};

var checkout = new KhaltiCheckout(config);
var btn = document.getElementById("payment-button");
btn.onclick = function () {
          var amountInput = document.getElementById("paymentAmount");
          
            var amount = amountInput.value*100;
            if (amount) {
                 // Convert amount from rupees to paisa
                config.amount = amount;
                btn.innerHTML = "Pay " + amount + " Rupees"; // Update button text
                checkout.show({popupMobile: true});
            } else {
                alert("Please enter the amount.");
            }
       
}
var fund = document.getElementById("fund-management");
var paymentOptions = document.getElementById("payment-options");

fund.addEventListener("click", () => {
  paymentOptions.style.display = (paymentOptions.style.display === "block") ? "none" : "block";
  console.log(paymentOptions.style.display);
});

function verifyPayment(payload) {
    let verifyUrl = 'https://cors-anywhere.herokuapp.com/'+'https://khalti.com/api/v2/payment/verify/';
    let token = payload.token;
    let amount = payload.amount;
    let data = {token: token, amount: amount};

    axios.get( verifyUrl, 
                data, 
                {'Authorization':'Key test_secret_key_4e85bb4e70114300844afa1e59abfb00'})
        .then(function(response) {
            console.log(response);
        })
        .catch(function(error) {
            console.log(error);
        });
}

var fundManagement = document.getElementById("fund-management");
var paymentOptions = document.getElementById("payment-options");

fundManagement.addEventListener("click", () => {
  paymentOptions.style.display = (paymentOptions.style.display === "block") ? "none" : "block";
  console.log(paymentOptions.style.display);
});