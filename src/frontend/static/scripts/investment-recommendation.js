document.addEventListener("DOMContentLoaded", async () => {
  const app = document.getElementById("app");
  const loading = document.createElement("p");
  loading.innerText = "Loading investment recommendations...";
  loading.style.color = "#007BFF";
  loading.style.fontWeight = "bold";
  loading.style.textAlign = "center";
  app.appendChild(loading);

  try {
    const accountBalance = sessionStorage.getItem("balance");

    const response = await fetch("http://35.232.140.138/investment-recommendations", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        amount: accountBalance,
      }),
    });

    const data = await response.json();

    if (data) {
      loading.remove();
      renderInvestmentData(data);
    } else {
      loading.innerText = "Failed to load data. Please try again.";
    }
  } catch (error) {
    loading.innerText = "Failed to load data. Please try again.";
    console.error("Fetch error:", error);
  }
});

function renderInvestmentData(data) {
  const app = document.getElementById("app");

  const section = (title, content) => `
          <div class="card">
            <h2 class="section-title">${title}</h2>
            <p>${content}</p>
          </div>
        `;

  app.innerHTML += section("Overview", data.analysis.overview);
  app.innerHTML += section("Savings Potential", data.analysis.savingsPotential);
  app.innerHTML += section("Risk Profile", data.analysis.riskProfile);
  app.innerHTML += section("Time Horizon", data.analysis.timeHorizon);

  const investments = data.investmentOptions
    .map(
      (inv) => `
          <div class="card">
            <h3>${inv.name}</h3>
            <div class="investMentAndRisk">
              <div class="tag">${inv.type}</div>
              <div class="tag">${inv.riskLevel} Risk</div>
            </div>
            <p><strong>Expected Returns:</strong> ${inv.expectedReturns}</p>
            <p><strong>Time Frame:</strong> ${inv.timeFrame}</p>
            <p>${inv.description}</p>
          </div>
        `
    )
    .join("");
  app.innerHTML += `<h2>Investment Options</h2><div class="grid">${investments}</div>`;

  const strategies = data.savingsStrategies
    .map(
      (s) => `
          <div class="card">
            <h3>${s.strategy}</h3>
            <p>${s.description}</p>
            <p><strong>Benefit:</strong> ${s.expectedBenefit}</p>
          </div>
        `
    )
    .join("");
  app.innerHTML += `<h2>Savings Strategies</h2><div class="grid">${strategies}</div>`;

  const breakdown = data.diversificationRecommendation.breakdown
    .map(
      (b) => `
          <div class="card">
            <h3>${b.category}</h3>
            <p><strong>Allocation:</strong> ${b.percentage}</p>
            <p>${b.rationale}</p>
          </div>
        `
    )
    .join("");
  app.innerHTML += `<h2>Diversification Breakdown</h2><div class="grid">${breakdown}</div>`;

  const notes = data.cautionaryNotes.map((note) => `<li>${note}</li>`).join("");
  app.innerHTML += `<div class="card">
                            <h2 class="section-title red">Cautionary Notes</h2><ul class="notes">${notes}</ul>
                        </div>`;

  const button = document.getElementById("downloadBtn");
  button.addEventListener("click", () => {
    window.print();
  });
}
