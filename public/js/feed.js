import { database, dbSDK } from "./firebase-config.js";
import { currentState } from "./app.js";
import { t } from "./translations.js";

// --- CROP RECOMMENDATION ACTIONS ---

window.triggerCropAnalysis = async function() {
    const btn = document.querySelector("#screen-crop-rec .btn-primary");
    const loader = btn.querySelector(".btn-loader");
    const text = btn.querySelector(".btn-text");
    const card = document.getElementById("crop-result-card");
    const placeholder = document.getElementById("crop-result-placeholder");

    // Get input values
    const n = document.getElementById("crop-n").value || "90";
    const p = document.getElementById("crop-p").value || "42";
    const k = document.getElementById("crop-k").value || "43";
    const ph = document.getElementById("crop-ph").value || "6.5";
    const temp = document.getElementById("crop-temp").value || "25";
    const hum = document.getElementById("crop-hum").value || "60";
    const rain = document.getElementById("crop-rain").value || "200";

    const state = document.getElementById("crop-state").value;
    const season = document.getElementById("crop-season").value;
    const soil = document.getElementById("crop-soil").value;

    loader.classList.remove("hidden");
    if (text) text.textContent = t("analyzing", currentState.currentLanguage);
    card.classList.add("hidden");
    if (placeholder) placeholder.classList.remove("hidden");

    // Save inputs to Firebase database (non-blocking & fail-safe)
    try {
        if (database && dbSDK) {
            const { ref, push } = dbSDK;
            const dbPath = currentState.user ? `soil_data/${currentState.user.uid}` : "soil_data/guest_user";
            push(ref(database, dbPath), {
                N: n, P: p, K: k, pH: ph, Temp: temp, Humidity: hum, Rainfall: rain,
                state: state, season: season, soil: soil,
                timestamp: new Date().toISOString()
            }).catch(err => {
                console.error("Database save failed", err);
            });
        }
    } catch (err) {
        console.error("Database save failed synchronously", err);
    }

    setTimeout(() => {
        loader.classList.add("hidden");
        if (text) text.textContent = t("analyze_btn", currentState.currentLanguage);

        // Populate Recommendation results
        document.getElementById("crop-result-title").textContent = `Best Match: Wheat (Malwa Shakti)`;
        document.getElementById("crop-result-score").textContent = `98.5% ${t("suitability", currentState.currentLanguage)}`;
        document.getElementById("crop-result-yield").textContent = `4.5 ${t("yield", currentState.currentLanguage).toLowerCase()}/ha`;
        document.getElementById("crop-result-water").textContent = `Medium`;
        document.getElementById("crop-result-season").textContent = season;

        if (placeholder) placeholder.classList.add("hidden");
        card.classList.remove("hidden");
    }, 1200);
};

// --- FERTILIZER PREDICTION ACTIONS ---

window.triggerFertilizerPrediction = async function() {
    const btn = document.querySelector("#screen-fertilizer-pred .btn-primary");
    const loader = btn ? btn.querySelector(".btn-loader") : null;
    const text = btn ? btn.querySelector(".btn-text") : null;
    const card = document.getElementById("pred-result-card");
    const placeholder = document.getElementById("pred-result-placeholder");

    const crop = document.getElementById("pred-crop-type").value;
    const soil = document.getElementById("pred-soil-type").value;
    const n = document.getElementById("pred-n").value || "50";
    const p = document.getElementById("pred-p").value || "30";
    const k = document.getElementById("pred-k").value || "40";
    const ph = document.getElementById("pred-ph").value || "6.2";
    const moisture = document.getElementById("pred-moisture").value || "45";
    const temp = document.getElementById("pred-temp").value || "26";

    if (loader) loader.classList.remove("hidden");
    if (text) text.textContent = "AI Predicting Best Fertilizer...";
    if (card) card.classList.add("hidden");
    if (placeholder) placeholder.classList.remove("hidden");

    // Save inputs to Firebase database (non-blocking & fail-safe)
    try {
        if (database && dbSDK) {
            const { ref, push } = dbSDK;
            const dbPath = currentState.user ? `soil_data/${currentState.user.uid}` : "soil_data/guest_user";
            push(ref(database, dbPath), {
                N: n, P: p, K: k, pH: ph, Moisture: moisture, Temp: temp,
                crop: crop, soil: soil, prediction: true,
                timestamp: new Date().toISOString()
            }).catch(err => {
                console.error("Database save failed", err);
            });
        }
    } catch (err) {
        console.error("Database save failed synchronously", err);
    }

    setTimeout(() => {
        if (loader) loader.classList.add("hidden");
        if (text) text.textContent = "Predict Fertilizer";

        const nInt = parseInt(n) || 50;
        const pInt = parseInt(p) || 30;
        const kInt = parseInt(k) || 40;

        let predictedFert = "NPK 19-19-19";
        if (nInt < 30) {
            predictedFert = "Urea (46% N) top-dress";
        } else if (pInt < 30) {
            predictedFert = "DAP 18-46-0 (High Phosphate)";
        } else if (kInt < 25) {
            predictedFert = "Muriate of Potash (MOP)";
        } else {
            const predictions = {
                "Rice": "NPK 10-26-26 + Zinc Sulphate",
                "Wheat": "DAP 18-46-0 (At sowing) & Urea top-dress",
                "Maize": "NPK 12-32-16 Complex",
                "Cotton": "NPK 15-15-15 + Magnesium Sulphate",
                "Sugarcane": "NPK 19-19-19 + Micronutrients",
                "Barley": "NPK 10-20-10 Basal",
                "Oats": "NPK 12-12-12 + Ammonium Nitrate",
                "Millet": "NPK 15-10-12 + Organic manure",
                "Sorghum": "NPK 14-14-14 + Zinc",
                "Soybean": "NPK 0-20-20 (No Nitrogen required)",
                "Groundnut": "SSP (Single Super Phosphate) + Gypsum",
                "Mustard": "NPK 12-32-16 + Bentonite Sulphur",
                "Potato": "NPK 10-10-20 + Soluble Potassium",
                "Tomato": "NPK 5-10-10 + Calcium Nitrate",
                "Onion": "NPK 15-15-15 + Elemental Sulphur",
                "Garlic": "NPK 10-10-10 + Micronutrients",
                "Ginger": "NPK 8-8-8 + Neem Cake",
                "Chili": "NPK 19-19-19 + Calcium Soluble",
                "Turmeric": "NPK 10-20-20 + Organic compost",
                "Chickpeas": "NPK 12-24-12 + Rhizobium inoculum"
            };
            predictedFert = predictions[crop] || "NPK 10-26-26";
        }

        const suitability = 85 + ((nInt + pInt + kInt) % 14);
        const quantity = 35 + ((nInt * 2 + pInt) % 25);
        const yieldIncrease = 12 + ((pInt + kInt) % 11);

        document.getElementById("pred-result-title").textContent = `Predicted: ${predictedFert}`;
        document.getElementById("pred-result-score").textContent = `${suitability}% Suitability`;
        document.getElementById("pred-result-qty").textContent = `${quantity} kg/acre`;
        
        const yieldMeta = document.getElementById("pred-result-card").querySelector(".metric-portal-item:nth-child(2) h4");
        if (yieldMeta) yieldMeta.textContent = `+${yieldIncrease}%`;

        if (placeholder) placeholder.classList.add("hidden");
        card.classList.remove("hidden");
    }, 1200);
};

// --- LEAF SCANNER ACTIONS ---

window.triggerImageSelect = function() {
    document.getElementById("camera-file-input").click();
};

window.loadScannerImage = function(e) {
    const file = e.target.files[0];
    if (!file) return;

    const filename = file.name.toLowerCase();
    if (!filename.includes("leaf") && !filename.includes("plant") && !filename.includes("crop")) {
        alert("Invalid file selection. Please upload a crop leaf image only to analyze!");
        e.target.value = "";
        return;
    }

    const reader = new FileReader();
    reader.onload = function(evt) {
        const img = document.getElementById("scanner-preview-img");
        img.src = evt.target.result;
        img.classList.remove("hidden");
        document.getElementById("scanner-preview-icon").classList.add("hidden");

        triggerLeafScanSimulation();
    };
    reader.readAsDataURL(file);
};

function triggerLeafScanSimulation() {
    const frame = document.getElementById("scanner-frame-box");
    const overlay = document.getElementById("scanner-analyzing-overlay");
    const resultCard = document.getElementById("scanner-result-card");
    const placeholder = document.getElementById("scanner-result-placeholder");
    
    if (frame) frame.classList.add("scanning");
    resultCard.classList.add("hidden");
    if (placeholder) placeholder.classList.remove("hidden");
    overlay.classList.remove("hidden");

    setTimeout(() => {
        if (frame) frame.classList.remove("scanning");
        overlay.classList.add("hidden");
        if (placeholder) placeholder.classList.add("hidden");
        resultCard.classList.remove("hidden");
    }, 1800);
}

window.resetScanner = function() {
    document.getElementById("scanner-preview-img").classList.add("hidden");
    document.getElementById("scanner-preview-img").src = "";
    document.getElementById("scanner-preview-icon").classList.remove("hidden");
    document.getElementById("scanner-result-card").classList.add("hidden");
    
    const placeholder = document.getElementById("scanner-result-placeholder");
    if (placeholder) placeholder.classList.remove("hidden");
    
    document.getElementById("camera-file-input").value = "";
};

// --- FERTILIZER GUIDE ---

window.calculateFertilizerRecommend = function() {
    const crop = document.getElementById("fert-crop-type").value;
    const soil = document.getElementById("fert-soil-type").value;
    const card = document.getElementById("fert-result-card");
    const placeholder = document.getElementById("fert-result-placeholder");

    const fertData = {
        "Rice": { fert: "Urea", dose: "50 kg / Acre", stage: "Basal Application" },
        "Wheat": { fert: "DAP (Diammonium Phosphate)", dose: "55 kg / Acre", stage: "At Sowing Time" },
        "Maize": { fert: "NPK 12-32-16 Complex", dose: "60 kg / Acre", stage: "Knee High Stage" },
        "Cotton": { fert: "SSP (Single Super Phosphate)", dose: "45 kg / Acre", stage: "Early Vegetative Stage" },
        "Sugarcane": { fert: "MOP (Muriate of Potash)", dose: "80 kg / Acre", stage: "Tillering Stage" },
        "Barley": { fert: "Urea & Ammonium Phosphate", dose: "40 kg / Acre", stage: "Basal Dressing" },
        "Oats": { fert: "Calcium Ammonium Nitrate", dose: "35 kg / Acre", stage: "Leaf Development" },
        "Millet": { fert: "NPK 15-15-15", dose: "30 kg / Acre", stage: "Basal Dressing" },
        "Sorghum": { fert: "Ammonium Sulphate", dose: "45 kg / Acre", stage: "Vegetative Phase" },
        "Soybean": { fert: "Super Phosphate", dose: "50 kg / Acre", stage: "Pre-flowering" },
        "Groundnut": { fert: "Gypsum & NPK", dose: "100 kg / Acre", stage: "Pegging Stage" },
        "Mustard": { fert: "Bentonite Sulphur & DAP", dose: "25 kg / Acre", stage: "Rosette Stage" },
        "Potato": { fert: "Potassium Chloride & DAP", dose: "120 kg / Acre", stage: "Tuber Initiation" },
        "Tomato": { fert: "Calcium Nitrate", dose: "30 kg / Acre", stage: "Fruit Setting" },
        "Onion": { fert: "Sulphur & Urea", dose: "40 kg / Acre", stage: "Bulb Development" },
        "Garlic": { fert: "Organics & NPK", dose: "35 kg / Acre", stage: "Bulb Formation" },
        "Ginger": { fert: "Neem Cake & NPK", dose: "60 kg / Acre", stage: "Rhizome Development" },
        "Chili": { fert: "NPK 19-19-19 Soluble", dose: "20 kg / Acre", stage: "Flowering Stage" },
        "Turmeric": { fert: "Potash & Compost", dose: "75 kg / Acre", stage: "Tillering Stage" },
        "Chickpeas": { fert: "DAP & Rhizobium Culture", dose: "40 kg / Acre", stage: "Seed Inoculation & Sowing" }
    };

    const data = fertData[crop] || { fert: "Urea", dose: "50 kg / Acre", stage: "Basal Application" };

    document.getElementById("fert-result-name").textContent = data.fert;
    document.getElementById("fert-result-dose").textContent = data.dose;
    document.getElementById("fert-result-time").textContent = data.stage;
    
    if (placeholder) placeholder.classList.add("hidden");
    card.classList.remove("hidden");
};
