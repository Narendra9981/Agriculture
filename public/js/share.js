import { database, dbSDK } from "./firebase-config.js";
import { currentState, navigateTo } from "./app.js";

// --- GOVERNMENT WELFARE SCHEMES ---

const schemesData = [
    { name: "PM-Kisan Nidhi", desc: "₹6000 Yearly Support", eligibility: ["Small & Marginal Farmers", "Indian Resident citizen", "Valid land title registration"], doc: "Provides ₹6,000 yearly financial support in three equal installments direct to banks." },
    { name: "Kisan Credit Card", desc: "Low Interest Loans", eligibility: ["All active farmers/cultivators", "Joint borrowers allowed", "Tenants / sharecroppers eligible"], doc: "Access short-term agricultural credit facilities at highly subsidized interest rates of 4% per annum." },
    { name: "PM Fasal Bima", desc: "Crop Insurance", eligibility: ["Farmers growing notified crops", "Compulsory for loanee farmers", "Optional for non-loanee farmers"], doc: "Comprehensive insurance cover against crop failures due to natural calamities, pests & diseases." },
    { name: "Soil Health Card", desc: "Free Soil Testing", eligibility: ["All active farmers across India", "Provides organic carbon, N, P, K details"], doc: "Assists farmers in identifying nutrient deficiencies and recommends customized fertilizer dosages." },
    { name: "PM-KUSUM", desc: "Solar Pump Subsidy", eligibility: ["Individual farmers", "Cooperatives / Water associations", "Subsidizes up to 60% of pump cost"], doc: "Set up solar-powered grid-connected agricultural irrigation water pumps." }
];

export function renderSchemesList() {
    const container = document.getElementById("schemes-list-content");
    if (!container) return;

    container.innerHTML = schemesData.map(s => `
        <div class="scheme-card-item btn-ripple" onclick="viewSchemeDetail('${s.name}')">
            <div class="scheme-info">
                <h4>${s.name}</h4>
                <p>${s.desc}</p>
            </div>
            <button class="btn btn-outline btn-xs" onclick="event.stopPropagation(); window.selectSchemeAndApply('${s.name}')" data-t="apply">APPLY</button>
        </div>
    `).join('');
}

// Make it accessible for dynamic loads
window.renderSchemesList = renderSchemesList;

window.filterSchemes = function(query) {
    const container = document.getElementById("schemes-list-content");
    if (!container) return;

    const filtered = schemesData.filter(s => s.name.toLowerCase().includes(query.toLowerCase()));
    container.innerHTML = filtered.map(s => `
        <div class="scheme-card-item btn-ripple" onclick="viewSchemeDetail('${s.name}')">
            <div class="scheme-info">
                <h4>${s.name}</h4>
                <p>${s.desc}</p>
            </div>
            <button class="btn btn-outline btn-xs" onclick="event.stopPropagation(); window.selectSchemeAndApply('${s.name}')" data-t="apply">APPLY</button>
        </div>
    `).join('');
};

window.viewSchemeDetail = function(name) {
    const scheme = schemesData.find(s => s.name === name);
    if (!scheme) return;

    currentState.selectedScheme = scheme;
    document.getElementById("scheme-card-name").textContent = scheme.name;
    document.getElementById("scheme-card-desc").textContent = scheme.doc;

    const elList = document.getElementById("scheme-eligibility-list");
    elList.innerHTML = scheme.eligibility.map(e => `<li>${e}</li>`).join('');

    navigateTo("screen-scheme-detail");
};

window.selectSchemeAndApply = function(name) {
    const scheme = schemesData.find(s => s.name === name);
    if (!scheme) return;

    currentState.selectedScheme = scheme;
    
    // Pre-populate details screen elements just in case the user views it later
    document.getElementById("scheme-card-name").textContent = scheme.name;
    document.getElementById("scheme-card-desc").textContent = scheme.doc;
    const elList = document.getElementById("scheme-eligibility-list");
    if (elList) elList.innerHTML = scheme.eligibility.map(e => `<li>${e}</li>`).join('');

    window.showApplyModal();
};

// Application Modal operations
window.showApplyModal = function() {
    const form = document.getElementById("scheme-apply-form");
    if (form) form.reset();
    document.getElementById("scheme-apply-modal").classList.remove("hidden");
    setTimeout(() => document.getElementById("scheme-apply-modal").classList.add("active"), 10);
};

window.closeApplyModal = function() {
    document.getElementById("scheme-apply-modal").classList.remove("active");
    setTimeout(() => document.getElementById("scheme-apply-modal").classList.add("hidden"), 300);
};

window.submitSchemeApplication = async function(e) {
    e.preventDefault();
    closeApplyModal();

    const schemeName = currentState.selectedScheme ? currentState.selectedScheme.name : "Welfare Scheme";
    const aadhaar = document.getElementById("scheme-apply-aadhaar").value;
    const bank = document.getElementById("scheme-apply-bank").value;
    const land = document.getElementById("scheme-apply-land").value;

    if (currentState.user) {
        try {
            if (database && dbSDK) {
                const { ref, push: dbPush } = dbSDK;
                dbPush(ref(database, `applications/${currentState.user.uid}`), {
                    schemeName: schemeName,
                    aadhaarNumber: aadhaar,
                    bankDetails: bank,
                    landSize: land,
                    appliedDate: new Date().toISOString(),
                    status: "Pending Review"
                }).catch(err => {
                    console.error("Save application failed", err);
                });
            }
        } catch (err) {
            console.error("Save application failed synchronously", err);
        }
    }

    alert(`Application for ${schemeName} Submitted Successfully!`);
    navigateTo("screen-schemes");
};
