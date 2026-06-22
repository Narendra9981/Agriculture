import { database, dbSDK } from "./firebase-config.js";
import { currentState, navigateTo, compileTranslations } from "./app.js";

// --- PROFILE ACTIONS ---

export function updateUIProfileValues() {
    // Update labels inside profile screen
    const nameLabel = document.getElementById("profile-name-val");
    if (nameLabel) nameLabel.textContent = currentState.profile.name || "Farmer";
    const locLabel = document.getElementById("profile-loc-val");
    if (locLabel) locLabel.textContent = currentState.profile.location || currentState.profile.state || "Ludhiana, Punjab";
    const sizeLabel = document.getElementById("profile-size-val");
    if (sizeLabel) sizeLabel.textContent = `${currentState.profile.farmSize || "5.0"} Acres`;
    const soilLabel = document.getElementById("profile-soil-val");
    if (soilLabel) soilLabel.textContent = currentState.profile.soilType || "Alluvial";
    const cropsLabel = document.getElementById("profile-crops-val");
    if (cropsLabel) cropsLabel.textContent = currentState.profile.crops || "Wheat";

    // Update footer name
    const footerName = document.getElementById("footer-username");
    if (footerName) footerName.textContent = currentState.profile.name || "Farmer";

    // Update edit inputs
    const editName = document.getElementById("edit-profile-name");
    if (editName) editName.value = currentState.profile.name || "";
    const editLoc = document.getElementById("edit-profile-loc");
    if (editLoc) editLoc.value = currentState.profile.location || currentState.profile.state || "";
    const editSize = document.getElementById("edit-profile-size");
    if (editSize) editSize.value = currentState.profile.farmSize || "";
    const editSoil = document.getElementById("edit-profile-soil");
    if (editSoil) editSoil.value = currentState.profile.soilType || "";
    const editCrops = document.getElementById("edit-profile-crops");
    if (editCrops) editCrops.value = currentState.profile.crops || "";
}

// Make it accessible for dynamic loads
window.updateUIProfileValues = updateUIProfileValues;

window.saveProfileChanges = async function(e) {
    e.preventDefault();
    const name = document.getElementById("edit-profile-name").value.trim();
    const loc = document.getElementById("edit-profile-loc").value.trim();
    const size = document.getElementById("edit-profile-size").value;
    const soil = document.getElementById("edit-profile-soil").value.trim();
    const crops = document.getElementById("edit-profile-crops").value.trim();

    currentState.profile = {
        name: name,
        location: loc,
        state: loc,
        farmSize: size,
        soilType: soil,
        crops: crops
    };

    if (currentState.user) {
        try {
            if (database && dbSDK) {
                const { ref, update: dbUpdate } = dbSDK;
                dbUpdate(ref(database, `users/${currentState.user.uid}`), currentState.profile).catch(err => {
                    console.error("Save profile to database failed", err);
                });
            }
        } catch (err) {
            console.error("Save profile to database failed synchronously", err);
        }
    }

    updateUIProfileValues();
    compileTranslations();
    alert("Profile Updated Successfully!");
    navigateTo("screen-profile");
};
