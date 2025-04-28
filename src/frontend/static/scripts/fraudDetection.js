document.addEventListener("DOMContentLoaded", function () {
  const sendButton = document.getElementById("fraud-check-button");
  const paymentForm = document.getElementById("payment-form");
  const fraudStatusMessage = document.getElementById("fraud-status-message");
  const paymentAmountInput = document.getElementById("payment-amount");
  const userBalance = sessionStorage.getItem("balance");

  let fraudCheckPassed = false;

  sendButton.addEventListener("click", async function (e) {
    if (!fraudCheckPassed) {
      const amount = parseFloat(paymentAmountInput.value);

      if (!amount || amount <= 0) {
        fraudStatusMessage.innerHTML = '<span style="color: red;">Please enter a valid amount.</span>';
        return;
      }

      sendButton.disabled = true;
      sendButton.innerHTML = "Analyzing...";
      fraudStatusMessage.innerHTML = '<span style="color: orange;">⏳ Analyzing fraud risk of transaction...</span>';

      try {
        const response = await fetch("https://vertexai-app-12904656032.us-central1.run.app/api/predict/model", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
          body: JSON.stringify({
            amount: parseFloat(amount).toFixed(2),
            current_balance: parseFloat(userBalance).toFixed(2),
          }),
        });

        const data = await response.json();

        if (data.predictions && data.predictions[0] === 1) {
          // Fraudulent
          fraudStatusMessage.innerHTML = '<span style="color: red;">⚠️ This transaction has been flagged as fraudulent. If you still want to proceed, click Send again.</span>';
        } else if (data.predictions && data.predictions[0] === 0) {
          // Cleared
          fraudStatusMessage.innerHTML = '<span style="color: green;">✅ This transaction has been cleared and does not seem fraudulent. If you want to proceed, click Send again.</span>';
        } else {
          fraudStatusMessage.innerHTML = '<span style="color: red;">Unexpected response from fraud check. You can still proceed with the transaction if you want.</span>';
        }

        // Fraud check complete, allow form submission next click
        fraudCheckPassed = true;
        sendButton.disabled = false;
        sendButton.innerHTML = "Send";
        sendButton.type = "submit";
      } catch (error) {
        console.error("Fraud check error:", error);
        fraudStatusMessage.innerHTML = '<span style="color: red;">⚠️ Error analyzing fraud risk. You can still proceed with the transaction if you want.</span>';

        fraudCheckPassed = true;
        sendButton.disabled = false;
        sendButton.innerHTML = "Send";
        sendButton.type = "submit";
      }
    } else {
      paymentForm.submit();
    }
  });
});
