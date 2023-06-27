var config = {
    // replace the publicKey with yours
    "publicKey": "test_public_key_ee8f489a141a44c0b7922037df48d668",
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

            window.location.href = "/verifypayment?token="+payload.token+"&amount="+payload.amount;
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
    // minimum transaction amount must be 10, i.e 1000 in paisa.
    checkout.show({amount: 1000});
}

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