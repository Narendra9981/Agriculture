const crops = [
    { name: "Wheat", price: "₹2,450", market: "Ludhiana Mandi", trend: "+2.4%", up: true },
    { name: "Rice (Basmati)", price: "₹3,200", market: "Patiala Mandi", trend: "-1.2%", up: false },
    { name: "Cotton", price: "₹6,800", market: "Bathinda Mandi", trend: "+3.1%", up: true },
    { name: "Soybean", price: "₹4,100", market: "Indore Mandi", trend: "-0.5%", up: false },
    { name: "Onion", price: "₹1,400", market: "Nashik Mandi", trend: "+5.2%", up: true },
    { name: "Mustard", price: "₹5,200", market: "Bharatpur Mandi", trend: "+1.8%", up: true },
    { name: "Potato", price: "₹1,100", market: "Agra Mandi", trend: "-2.1%", up: false },
    { name: "Maize", price: "₹2,150", market: "Gulabbagh Mandi", trend: "+0.9%", up: true },
    { name: "Tomato", price: "₹1,800", market: "Chandigarh", trend: "+4.4%", up: true },
    { name: "Tur Dal", price: "₹8,400", market: "Latur Mandi", trend: "+1.2%", up: true },
    { name: "Moong", price: "₹7,600", market: "Jaipur Mandi", trend: "-0.8%", up: false },
    { name: "Groundnut", price: "₹5,900", market: "Rajkot Mandi", trend: "+2.5%", up: true },
    { name: "Chana", price: "₹5,400", market: "Akola Mandi", trend: "+1.1%", up: true },
    { name: "Turmeric", price: "₹12,200", market: "Erode Mandi", trend: "+3.7%", up: true },
    { name: "Apple", price: "₹9,500", market: "Shimla Mandi", trend: "+0.5%", up: true },
    { name: "Banana", price: "₹2,800", market: "Jalgaon Mandi", trend: "-1.5%", up: false },
    { name: "Green Chilli", price: "₹3,400", market: "Azadpur Mandi", trend: "+2.2%", up: true },
    { name: "Garlic", price: "₹11,000", market: "Mandsaur Mandi", trend: "+4.1%", up: true },
    { name: "Cumin", price: "₹24,500", market: "Unjha Mandi", trend: "-3.2%", up: false },
    { name: "Sugarcane", price: "₹450", market: "Amritsar Mandi", trend: "+0.2%", up: true }
];

const schemes = [
    { name: "PM-Kisan Nidhi", desc: "₹6000 Yearly Support" },
    { name: "Kisan Credit Card", desc: "Low Interest Loans" },
    { name: "PM Fasal Bima", desc: "Crop Insurance" },
    { name: "Soil Health Card", desc: "Free Soil Testing" },
    { name: "PM-KUSUM", desc: "Solar Pump Subsidy" }
];

function showScreen(screenId) {
    document.querySelectorAll('.screen').forEach(s => s.classList.remove('active'));
    document.getElementById(screenId).classList.add('active');

    document.querySelectorAll('.nav-item').forEach(n => n.classList.remove('active'));
    // Visual logic for nav active state can be added here
}

function renderCrops(list) {
    const container = document.getElementById('crops-list');
    container.innerHTML = list.map(c => `
        <div class="price-card">
            <div style="font-size: 24px;">${c.name.includes('Rice') ? '🌾' : c.name.includes('Tomato') ? '🍅' : '🌱'}</div>
            <div class="crop-info">
                <h4>${c.name}</h4>
                <p>${c.market}</p>
            </div>
            <div class="price">
                <div class="amt">${c.price}</div>
                <div class="trend ${c.up ? 'up' : 'down'}">${c.up ? '▲' : '▼'} ${c.trend}</div>
            </div>
        </div>
    `).join('');
}

function renderSchemes() {
    const container = document.getElementById('schemes-list');
    container.innerHTML = schemes.map(s => `
        <div class="scheme-card">
            <div>
                <h4>${s.name}</h4>
                <p style="font-size:11px; margin:5px 0; color:#666;">${s.desc}</p>
            </div>
            <button class="btn-apply">APPLY</button>
        </div>
    `).join('');
}

function filterCrops(query) {
    const filtered = crops.filter(c => c.name.toLowerCase().includes(query.toLowerCase()));
    renderCrops(filtered);
}

// Init
renderCrops(crops);
renderSchemes();
