require("dotenv").config();
const express = require("express");
const cors = require("cors");

const app = express();

const PORT = process.env.PORT || 8080;
const GEMINI_API_KEY = process.env.GEMINI_API_KEY;

app.use(cors());

app.use(express.json());

app.post("/investment-recommendations", async (req, res) => {
  const { amount } = req.body;

  if (!amount) {
    return res.status(400).json({ error: "Amount is required in request body" });
  }

  try {
    const prompt = `
Analyze the account balance and provide detailed savings and investment recommendations in the following format
{
  "analysis": {
    "overview": "General financial situation overview",
    "savingsPotential": "Analysis of savings potential",
    "riskProfile": "Suggested risk profile based on amount",
    "timeHorizon": "Suggested investment time horizons"
  },
  "investmentOptions": [
    {
      "name": "Investment option name",
      "type": "Type of investment",
      "riskLevel": "Low/Medium/High",
      "expectedReturns": "Expected ROI percentage range",
      "timeFrame": "Recommended holding period",
      "description": "Detailed description of this investment option"
    }
  ],
  "savingsStrategies": [
    {
      "strategy": "Strategy name",
      "description": "Detailed description of the strategy",
      "expectedBenefit": "Potential outcome from this strategy"
    }
  ],
  "diversificationRecommendation": {
    "breakdown": [
      {
        "category": "Investment category",
        "percentage": "Recommended allocation percentage",
        "rationale": "Why this allocation is recommended"
      }
    ]
  },
  "cautionaryNotes": [
    "Warning or caution point 1",
    "Warning or caution point 2"
  ]
}

Analyze this financial situation:
Account Balance: ${amount} Naira

Provide detailed analysis focusing on savings potential, investment opportunities, diversification strategy, and financial safety guidelines specific to the Nigerian market and economy.
Consider local investment options available in Nigeria, including government bonds, treasury bills, Nigerian stocks, mutual funds, and traditional savings.
Ensure the response follows the EXACT JSON format shown above
    `;

    const response = await fetch(`https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=${GEMINI_API_KEY}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        contents: [{ parts: [{ text: prompt }] }],
      }),
    });

    const apiResponse = await response.json();
    const cleanedData = extractInvestmentRecommendations(apiResponse);

    if (!cleanedData) {
      return res.status(500).json({ error: "Failed to extract valid response from Gemini" });
    }

    return res.status(200).json(cleanedData);
  } catch (error) {
    console.error("Error in /analyze:", error);
    return res.status(500).json({ error: "Server error" });
  }
});

function extractInvestmentRecommendations(apiResponse) {
  try {
    const rawText = apiResponse?.candidates?.[0]?.content?.parts?.[0]?.text;
    if (!rawText) return null;

    const jsonText = rawText.replace(/```json\n?|```/g, "").trim();
    return JSON.parse(jsonText);
  } catch (err) {
    console.error("JSON extraction error:", err);
    return null;
  }
}

// Basic route
app.get("/", (req, res) => {
  res.send("Hello World!");
});

app.listen(PORT, () => {
  console.log(`Server running at http://localhost:${PORT}`);
});
