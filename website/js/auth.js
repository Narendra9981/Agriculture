import { auth, database, authSDK, dbSDK } from "./firebase-config.js";
import { currentState, navigateTo, compileTranslations, updateUIProfileValues } from "./app.js";

// --- AUTHENTICATION FLOWS ---

window.handleLogin = async function(e) {
    e.preventDefault();
    const email = document.getElementById("login-username").value.trim();
    const pass = document.getElementById("login-password").value;

    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const finalEmail = emailPattern.test(email) ? email : `${email.replace(/\+/g, "")}@agribot.com`;

    try {
        if (!auth || !authSDK) {
            // Local mock fallback login
            currentState.user = { uid: "guest_user", email: finalEmail, displayName: "Farmer" };
            
            // Set dynamic mock profile info based on user name/email if Narendra
            let profileData = {
                name: "Farmer",
                location: "Ludhiana, Punjab",
                farmSize: "5.0",
                soilType: "Alluvial",
                crops: "Wheat"
            };
            if (finalEmail.toLowerCase().includes("narendra")) {
                profileData = {
                    name: "Narendra",
                    location: "Ludhiana, Punjab",
                    farmSize: "7.2",
                    soilType: "Alluvial Soil",
                    crops: "Rice, Sugarcane"
                };
            }
            currentState.profile = profileData;
            updateUIProfileValues();
            compileTranslations();
            navigateTo("screen-dashboard");
            return;
        }
        const { signInWithEmailAndPassword } = authSDK;
        const userCred = await signInWithEmailAndPassword(auth, finalEmail, pass);
        currentState.user = userCred.user;
        fetchUserProfile(userCred.user.uid);
    } catch (err) {
        alert("Authentication Failed: " + err.message);
    }
};

window.handleRegistration = async function(e) {
    e.preventDefault();
    const name = document.getElementById("reg-name").value.trim();
    const email = document.getElementById("reg-email").value.trim();
    const phone = document.getElementById("reg-phone").value.trim();
    const pass = document.getElementById("reg-password").value;

    try {
        currentState.profile = {
            name: name,
            location: "Ludhiana, Punjab",
            state: "Ludhiana, Punjab",
            farmSize: "5.0",
            soilType: "Alluvial",
            crops: "Wheat"
        };

        if (!auth || !authSDK) {
            // Local mock fallback registration
            currentState.user = { uid: "guest_user", email: email, displayName: name };
            navigateTo("screen-otp");
            return;
        }

        const { createUserWithEmailAndPassword } = authSDK;
        const userCred = await createUserWithEmailAndPassword(auth, email, pass);
        currentState.user = userCred.user;
        
        // Save to Realtime Database (non-blocking & fail-safe)
        try {
            if (database && dbSDK) {
                const { ref, update: dbUpdate } = dbSDK;
                dbUpdate(ref(database, `users/${userCred.user.uid}`), currentState.profile).catch(err => {
                    console.error("Database register profile save failed", err);
                });
            }
        } catch (err) {
            console.error("Database register profile save failed synchronously", err);
        }
        
        // Go to OTP screen mock
        navigateTo("screen-otp");
    } catch (err) {
        alert("Registration Failed: " + err.message);
    }
};

window.loginWithGoogleMock = function() {
    window.openGoogleChooser();
};

window.openGoogleChooser = function() {
    const modal = document.getElementById("google-login-modal");
    if (modal) {
        modal.classList.remove("hidden");
        modal.offsetHeight; // force reflow
        modal.classList.add("active");
    }
};

window.closeGoogleChooser = function() {
    const modal = document.getElementById("google-login-modal");
    if (modal) {
        modal.classList.remove("active");
        setTimeout(() => {
            modal.classList.add("hidden");
        }, 300);
    }
};

window.loginWithSelectedGoogleAccount = async function(email) {
    let profileData = {
        name: "Arjun Singh",
        location: "Ludhiana, Punjab",
        state: "Ludhiana, Punjab",
        farmSize: "5.5",
        soilType: "Clay Loam",
        crops: "Wheat, Rice"
    };

    if (email === "kanamalanarendra1162.sse@saveeth.com") {
        profileData = {
            name: "Narendra",
            location: "Ludhiana, Punjab",
            state: "Ludhiana, Punjab",
            farmSize: "7.2",
            soilType: "Alluvial Soil",
            crops: "Rice, Sugarcane"
        };
    }

    currentState.profile = profileData;
    
    const cleanEmailKey = email.replace(/[^a-zA-Z0-9]/g, "_");
    const mockUid = "google_mock_" + cleanEmailKey;
    
    currentState.user = {
        uid: mockUid,
        email: email,
        displayName: profileData.name
    };

    // Save profile to database without blocking redirection (fail-safe)
    try {
        if (database && dbSDK) {
            const { ref, set: dbSet } = dbSDK;
            dbSet(ref(database, `users/${mockUid}`), profileData).catch(err => {
                console.error("Firebase profile sync failed", err);
            });
        }
    } catch (err) {
        console.error("Firebase profile sync failed synchronously", err);
    }

    updateUIProfileValues();
    compileTranslations();
    window.closeGoogleChooser();
    navigateTo("screen-dashboard");
};

window.moveOtpFocus = function(current, nextId) {
    if (current.value.length >= 1) {
        document.getElementById(nextId).focus();
    }
};

window.verifyOtpMock = function() {
    alert("Phone OTP Verification Successful!");
    updateUIProfileValues();
    navigateTo("screen-dashboard");
};

window.logoutUser = async function() {
    try {
        if (auth && authSDK) {
            const { signOut } = authSDK;
            await signOut(auth).catch(err => {
                console.warn("Signout rejected or offline:", err);
            });
        }
    } catch (err) {
        console.error("Signout Error", err);
    }
    currentState.user = null;
    currentState.profile = {
        name: "Arjun Singh",
        location: "Ludhiana, Punjab",
        farmSize: "5.5",
        soilType: "Clay Loam",
        crops: "Wheat, Rice"
    };
    updateUIProfileValues();
    compileTranslations();
    navigateTo("screen-welcome");
};

// Fetch profile from RTDB
export async function fetchUserProfile(uid) {
    try {
        if (database && dbSDK) {
            const { ref, get: dbGet, set: dbSet } = dbSDK;
            const getPromise = dbGet(ref(database, `users/${uid}`));
            const timeoutPromise = new Promise((_, reject) => 
                setTimeout(() => reject(new Error("Timeout")), 2500)
            );
            const snapshot = await Promise.race([getPromise, timeoutPromise]);
            if (snapshot.exists()) {
                currentState.profile = snapshot.val();
            } else {
                dbSet(ref(database, `users/${uid}`), currentState.profile).catch(err => {
                    console.error("Firebase default profile set failed", err);
                });
            }
        }
    } catch (err) {
        console.error("Fetch profile failed, using local profile", err);
    }
    updateUIProfileValues();
    compileTranslations();
    navigateTo("screen-dashboard");
}

let authListenerRegistered = false;

// Check auth state listener
function registerAuthListener() {
    if (authListenerRegistered) return;

    if (auth && authSDK) {
        authListenerRegistered = true;
        const { onAuthStateChanged } = authSDK;
        onAuthStateChanged(auth, (firebaseUser) => {
            if (firebaseUser) {
                currentState.user = firebaseUser;
                fetchUserProfile(firebaseUser.uid).then(() => {
                    const isPublicScreen = ["screen-splash", "screen-welcome", "screen-onboarding-1", "screen-onboarding-2", "screen-onboarding-3", "screen-login", "screen-register", "screen-otp"].includes(currentState.currentScreen);
                    if (isPublicScreen) {
                        navigateTo("screen-dashboard");
                    }
                });
            } else {
                currentState.user = null;
                currentState.profile = {
                    name: "Arjun Singh",
                    location: "Ludhiana, Punjab",
                    farmSize: "5.5",
                    soilType: "Clay Loam",
                    crops: "Wheat, Rice"
                };
                updateUIProfileValues();
                compileTranslations();
                // Do not force redirect if we are already on onboarding or login/register/otp
                const isPublicScreen = ["screen-splash", "screen-welcome", "screen-onboarding-1", "screen-onboarding-2", "screen-onboarding-3", "screen-login", "screen-register", "screen-otp"].includes(currentState.currentScreen);
                if (!isPublicScreen) {
                    navigateTo("screen-welcome");
                }
            }
        });
    } else {
        import("./firebase-config.js").then(m => {
            if (m.firebaseConfigPromise) {
                m.firebaseConfigPromise.then(() => {
                    if (auth && authSDK) {
                        registerAuthListener();
                    } else {
                        console.warn("Firebase not initialized. Running in local mock mode.");
                    }
                });
            }
        });
    }
}
registerAuthListener();
